package me.afunx.xfun.app;

import static me.afunx.xfun.app.DisplayRoundRect.BIG_BETWEEN_MARGIN_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_HEIGHT_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_LEFT_MARGIN_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_RADIUS_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_TOP_MARGIN_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_WIDTH_DP;
import static me.afunx.xfun.app.DisplayRoundRect.THICKNESS_DP;

import android.animation.PointFEvaluator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;
import com.afunx.xfun.common.utils.MetricsFUtils;

import me.afunx.xfun.app.util.TimeDiffUtil;

public class DisplayParticle {
    private static final String TAG = "DisplayParticle";
    private static final boolean DEBUG = false;
    // 粒子初始位置点，单位：像素
    private final PointF mStartPoint;
    // 粒子最终位置点，单位：像素
    private final PointF mEndPoint;
    // 粒子半径，单位：像素
    private final float mRadius;

    // 粒子周期时间，单位：ms
    private final long mInterval;
    // 粒子开始时间，单位：ms
    private final long mStartTime;
    // 粒子结束时间，单位：ms
    private final long mEndTime;
    // 粒子进入时间，单位：ms
    private final long mEntranceTime;
    // 粒子退出时间，单位：ms
    private final long mExitTime;

    private long mElapsedRealTime = 0;
    // 是否被黑洞抓住，被黑洞抓住，则不再绘制
    private boolean mCaughtInBlackHole = false;
    // 上一次的realDuration
    private long mLastRealDuration;
    private static final RectF sLeftRectF;
    private static final RectF sRightRectF;
    private static final float sThickness;
    private static final float sBigRadius;

    int _idx;

    int _color = 0;

    int _oldColor = 0;

    private static final TimeInterpolator sTimeInterpolator;

    private static final PointFEvaluator sPointFEvaluator;

    static {
        final Context context = MainApplication.getAppContext();

        sTimeInterpolator = new AccelerateDecelerateInterpolator();
        sPointFEvaluator = new PointFEvaluator();

        sThickness = MetricsFUtils.dp2px(context, THICKNESS_DP);

        // 大
        final float bigWidth = MetricsFUtils.dp2px(context, BIG_WIDTH_DP);
        final float bigHeight = MetricsFUtils.dp2px(context, BIG_HEIGHT_DP);
        float left = MetricsFUtils.dp2px(context, BIG_LEFT_MARGIN_DP);
        float top = MetricsFUtils.dp2px(context, BIG_TOP_MARGIN_DP);
        float right = left + bigWidth;
        float bottom = top + bigHeight;
        RectF bigLeftRectF = new RectF(left, top, right, bottom);
        left += MetricsFUtils.dp2px(context, BIG_BETWEEN_MARGIN_DP) + bigWidth;
        right += MetricsFUtils.dp2px(context, BIG_BETWEEN_MARGIN_DP) + bigWidth;
        RectF bigRightRectF = new RectF(left, top, right, bottom);
        sBigRadius = MetricsFUtils.dp2px(context, BIG_RADIUS_DP);

        sLeftRectF = new RectF(bigLeftRectF.left + sThickness / 2, bigLeftRectF.top + sThickness / 2, bigLeftRectF.right - sThickness / 2, bigLeftRectF.bottom - sThickness / 2);
        sRightRectF = new RectF(bigRightRectF.left + sThickness / 2, bigRightRectF.top + sThickness / 2, bigRightRectF.right - sThickness / 2, bigRightRectF.bottom - sThickness / 2);
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        if (DEBUG) {
            TimeDiffUtil.start();
        }
        if (mElapsedRealTime == 0) {
            mElapsedRealTime = elapsedRealTime;
        }
        // 度过的时间
        long spentRealTime = elapsedRealTime - mElapsedRealTime;
        // 动画所处的时间(0<=durationTime<mInterval)
        long durationTime = spentRealTime % mInterval;
        // 去除进入时间后的时间
        long realDurationTime = durationTime - mEntranceTime;
        // 是否在有效时间内
        if (mStartTime <= realDurationTime && durationTime <= mExitTime && realDurationTime <= mEndTime) {
            float input = 1.0f * (realDurationTime - mStartTime) / (mEndTime - mStartTime);
            float fraction = sTimeInterpolator.getInterpolation(input);
            PointF current = sPointFEvaluator.evaluate(fraction, mStartPoint, mEndPoint);
            float left = current.x - mRadius;
            float top = current.y - mRadius;
            float right = left + mRadius * 2;
            float bottom = top + mRadius * 2;
            if (realDurationTime < mLastRealDuration) {
                mCaughtInBlackHole = false;
            }
            if (!mCaughtInBlackHole) {
                // 更换颜色，仅在调试中使用
                if (_color != 0) {
                    _oldColor = paint.getColor();
                    paint.setColor(_color);
                }
                canvas.drawOval(left, top, right, bottom, paint);
                if (_color != 0) {
                    paint.setColor(_oldColor);
                }
                // 第一次被黑洞捕捉时，显示一次先，所以mCaughtInBlackHole更新在显示之后
                mCaughtInBlackHole = caughtBlackHoles(current);
            }
            mLastRealDuration = realDurationTime;
        }
        // 绘制
        if (DEBUG) {
            long consume = TimeDiffUtil.end();
            LogUtils.d(TAG, "onDraw() consume: " + consume + " ms");
        }
    }

    /**
     * 调试绘制回调
     *
     * @param canvas
     * @param paint
     */
    static void _onDebugDraw(@NonNull Canvas canvas, @NonNull Paint paint) {
        Paint.Style style = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFFFF0000);
        paint.setStrokeWidth(1);
        // 左边
        // 左圆
        float cx = sLeftRectF.left + sBigRadius - 0.3f * sThickness;
        float cy = sLeftRectF.bottom - sBigRadius + 0.3f * sThickness;
        float radius = sBigRadius;
        canvas.drawCircle(cx, cy, radius, paint);
        // 右圆
        float cx2 = sLeftRectF.right - sBigRadius + 0.3f * sThickness;
        canvas.drawCircle(cx2, cy, radius, paint);
        // 下矩形
        // canvas.drawRect(cx, cy + radius - 0.5f * sThickness, cx2, cy + radius, paint);

        // 右边
        // 左圆
        cx = sRightRectF.left + sBigRadius - 0.3f * sThickness;
        cy = sRightRectF.bottom - sBigRadius + 0.3f * sThickness;
        canvas.drawCircle(cx, cy, radius, paint);
        // 右圆
        cx2 = sRightRectF.right - sBigRadius + 0.3f * sThickness;
        canvas.drawCircle(cx2, cy, radius, paint);
        // 下矩形
        // canvas.drawRect(cx, cy + radius - 0.5f * sThickness, cx2, cy + radius, paint);
        paint.setStyle(style);
    }

    private final PointF _center = new PointF();

    /**
     * 由于无法绘制出完全拟合的图形，故采用近似拟合的图形判定黑洞捕捉情况。
     * 可调用{@link #_onDebugDraw(Canvas, Paint)}调试代码查看黑洞的范围。
     *
     * @param current 当前位置
     * @return 是否被黑洞捕捉
     */
    private boolean caughtBlackHoles(@NonNull PointF current) {
        // 左边
        // 左圆
        float cx = sLeftRectF.left + sBigRadius - 0.3f * sThickness;
        float cy = sLeftRectF.bottom - sBigRadius + 0.3f * sThickness;
        float radius = sBigRadius;
        _center.x = cx;
        _center.y = cy;
        if (distance(current, _center) <= mRadius + radius) {
            return true;
        }
        // canvas.drawCircle(cx, cy, radius, paint);
        // 右圆
        float cx2 = sLeftRectF.right - sBigRadius + 0.3f * sThickness;
        _center.x = cx2;
        if (distance(current, _center) <= mRadius + radius) {
            return true;
        }
        // canvas.drawCircle(cx2, cy, radius, paint);

        // 右边
        // 左圆
        cx = sRightRectF.left + sBigRadius - 0.3f * sThickness;
        cy = sRightRectF.bottom - sBigRadius + 0.3f * sThickness;
        _center.x = cx;
        _center.y = cy;
        if (distance(current, _center) <= mRadius + radius) {
            return true;
        }
        // canvas.drawCircle(cx, cy, radius, paint);
        // 右圆
        cx2 = sRightRectF.right - sBigRadius + 0.3f * sThickness;
        _center.x = cx2;
        if (distance(current, _center) <= mRadius + radius) {
            return true;
        }
        // canvas.drawCircle(cx2, cy, radius, paint);
        return false;
    }

    private DisplayParticle(float startX, float startY, float endX, float endY, float radius, long interval, long startTime, long endTime, long entranceTime, long exitTime) {
        mStartPoint = new PointF(startX, startY);
        mEndPoint = new PointF(endX, endY);
        mRadius = radius;
        mInterval = interval;
        mStartTime = startTime;
        mEndTime = endTime;
        mEntranceTime = entranceTime;
        mExitTime = exitTime;
        mLastRealDuration = 0;
    }

    private double distance(PointF p0, PointF p1) {
        return Math.sqrt((p1.y - p0.y) * (p1.y - p0.y) + (p1.x - p0.x) * (p1.x - p0.x));
    }

    public DisplayParticle clone(String entranceTime, String exitTime) {
        return new Builder()
                .setStartX(mStartPoint.x)
                .setStartY(mStartPoint.y)
                .setEndX(mEndPoint.x)
                .setEndY(mEndPoint.y)
                .setRadius(mRadius)
                .setInterval(mInterval)
                .setStartTime(mStartTime)
                .setEndTime(mEndTime)
                .setEntranceTime(entranceTime)
                .setExitTime(exitTime)
                .build();
    }

    public static class Builder {
        private float startX;
        private float startY;
        private float endX;
        private float endY;
        private float radius;
        private long interval;
        private long startTime;
        private long endTime;

        private long entranceTime;

        private long exitTime;

        private long clipStartTime = 0;

        public Builder setStartX(float startX) {
            this.startX = startX;
            return this;
        }

        public Builder setStartY(float startY) {
            this.startY = startY;
            return this;
        }

        public Builder setEndX(float endX) {
            this.endX = endX;
            return this;
        }

        public Builder setEndY(float endY) {
            this.endY = endY;
            return this;
        }

        public Builder setRadius(float radius) {
            this.radius = radius;
            return this;
        }

        public Builder setInterval(long interval) {
            this.interval = interval;
            return this;
        }

        public Builder setStartTime(String startTime) {
            this.startTime = parseTime(startTime);
            return this;
        }

        private Builder setStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(String endTime) {
            this.endTime = parseTime(endTime);
            return this;
        }

        private Builder setEndTime(long endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder setEntranceTime(String entranceTime) {
            this.entranceTime = parseTime(entranceTime);
            return this;
        }

        public Builder setExitTime(String exitTime) {
            this.exitTime = parseTime(exitTime);
            return this;
        }

        public Builder setClipStartTime(String clipStartTime) {
            this.clipStartTime = parseTime(clipStartTime);
            return this;
        }

        public DisplayParticle build() {
            if (this.startTime < 0) {
                throw new IllegalArgumentException("startTime: " + this.startTime + " < 0");
            }
            if (this.entranceTime < 0) {
                throw new IllegalArgumentException("entranceTime: " + this.entranceTime + " < 0");
            }
            if (this.exitTime > this.interval) {
                throw new IllegalArgumentException("exitTime: " + this.exitTime + " > interval: " + this.interval);
            }
            if (this.radius <= 0) {
                throw new IllegalArgumentException("radius: " + this.radius + " <= 0");
            }
            return new DisplayParticle(startX, startY, endX, endY, radius, interval, startTime - clipStartTime, endTime - clipStartTime, entranceTime, exitTime);
        }
    }

    // 15:12解析成(15+1.0f*12/24) * 1000L = 15 * 1000 + 12 * 1000/24
    public static long parseTime(String time) {
        if (time.length() != 5) {
            throw new IllegalArgumentException("time: " + time + " is invalid");
        }
        long t1 = Long.parseLong(time.substring(0, 2));
        long t2 = Long.parseLong(time.substring(3, 5));
        return t1 * 1000L + t2 * 1000L / 24;
    }

    @Override
    public String toString() {
        return "DisplayParticle{" +
                "mStartPoint=" + mStartPoint +
                ", mEndPoint=" + mEndPoint +
                ", mRadius=" + mRadius +
                ", mInterval=" + mInterval +
                ", mStartTime=" + mStartTime +
                ", mEndTime=" + mEndTime +
                ", mEntranceTime=" + mEntranceTime +
                ", mExitTime=" + mExitTime +
                ", mElapsedRealTime=" + mElapsedRealTime +
                '}';
    }
}
