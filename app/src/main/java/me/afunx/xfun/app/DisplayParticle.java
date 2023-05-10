package me.afunx.xfun.app;

import android.animation.PointFEvaluator;
import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

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

    private long mElapsedRealTime = 0;

    // 时间差值器
    private final TimeInterpolator mTimeInterpolator = new LinearInterpolator();
    // 位置估值器
    private final PointFEvaluator mPointFEvaluator = new PointFEvaluator();

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
        // 是否在有效时间内
        if (mStartTime <= durationTime && durationTime <= mEndTime) {
            float input = 1.0f * (durationTime - mStartTime) / (mEndTime - mStartTime);
            float fraction = mTimeInterpolator.getInterpolation(input);
            PointF current = mPointFEvaluator.evaluate(fraction, mStartPoint, mEndPoint);
            float left = current.x - mRadius / 2;
            float top = current.y - mRadius / 2;
            float right = left + mRadius;
            float bottom = top + mRadius;
            canvas.drawOval(left, top, right, bottom, paint);
        }
        if (DEBUG) {
            long consume = TimeDiffUtil.end();
            LogUtils.d(TAG, "onDraw() consume: " + consume + " ms");
        }
    }

    private DisplayParticle(float startX, float startY, float endX, float endY, float radius, long interval, long startTime, long endTime) {
        mStartPoint = new PointF(startX, startY);
        mEndPoint = new PointF(endX, endY);
        mRadius = radius;
        mInterval = interval;
        mStartTime = startTime;
        mEndTime = endTime;
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

        public Builder setStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(long endTime) {
            this.endTime = endTime;
            return this;
        }

        public DisplayParticle build() {
            if (this.startTime < 0) {
                throw new IllegalArgumentException("startTime: " + this.startTime + " < 0");
            }
            if (this.endTime > this.interval) {
                throw new IllegalArgumentException("endTime: " + this.endTime + " > interval: " + this.interval);
            }
            if (this.radius <= 0) {
                throw new IllegalArgumentException("radius: " + this.radius + " <= 0");
            }
            return new DisplayParticle(startX, startY, endX, endY, radius, interval, startTime, endTime);
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
}
