package me.afunx.xfun.app;

import static me.afunx.xfun.app.DisplaySurfaceView.HOLLOW_WAVE_COLOR;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;
import com.afunx.xfun.common.utils.MetricsFUtils;

public class DisplayWave {

    private static final String TAG = "DisplayWave";
    private static final boolean DEBUG = false;
    private final float mBigHeight;
    private final float mWaveThickness;
    private final RectF mWaveLeftRectF;
    private final RectF mWaveRightRectF;
    private final RectF mPowerLeftRectF;
    private final RectF mPowerRightRectF;
    private final int mWaveCount;
    private final float mWaveHeight;
    private final float mWaveWidth;
    private final float mWaveSpeedPerSecond;
    private long mElapsedRealTime = 0;
    private float mWaveTranslateX = 0;
    private Path mSolidWavePath = null;
    private Path mHollowWavePath = null;
    private static final float WAVE_HEIGHT_DP = 18.75f;
    private static final float WAVE_WIDTH_DP = 146.25f;
    private static final float WAVE_SPEED_DP_PER_SECOND = 135f;

    private volatile int mBatteryPercent = 0;

    public DisplayWave() {
        final Context context = MainApplication.getAppContext();
        // 总共波浪厚度
        mBigHeight = DisplayMetrics.bigHeight();
        // 单层波浪厚度
        mWaveThickness = DisplayMetrics.thickness();
        // 波浪高度
        mWaveHeight = MetricsFUtils.dp2px(context, WAVE_HEIGHT_DP);
        // 波浪宽度
        mWaveWidth = MetricsFUtils.dp2px(context, WAVE_WIDTH_DP);
        // 波浪速度
        mWaveSpeedPerSecond = MetricsFUtils.dp2px(context, WAVE_SPEED_DP_PER_SECOND);
        if (DEBUG) {
            LogUtils.i(TAG, "DisplayWave() mWaveHeight: " + mWaveHeight + ", mWaveWidth: " + mWaveWidth +
                    ", mWaveSpeedPerSecond: " + mWaveSpeedPerSecond);
        }
        final float width = DisplayMetrics.bigWidth();
        final float height = DisplayMetrics.bigHeight();
        float left = DisplayMetrics.bigLeftMargin();
        float top = DisplayMetrics.bigTopMargin();
        float right = left + width;
        float bottom = top + height;
        mWaveLeftRectF = new RectF(left, top + mWaveHeight, right, bottom - mWaveHeight);
        left += DisplayMetrics.bigBetweenMargin() + width;
        right += DisplayMetrics.bigBetweenMargin() + width;
        mWaveRightRectF = new RectF(left, top + mWaveHeight, right, bottom - mWaveHeight);

        mWaveCount = (int) Math.ceil(width / mWaveWidth);

        mPowerLeftRectF = new RectF(mWaveLeftRectF.left, mWaveLeftRectF.bottom, mWaveLeftRectF.right, mWaveLeftRectF.bottom + height);
        mPowerRightRectF = new RectF(mWaveRightRectF.left, mWaveRightRectF.bottom, mWaveRightRectF.right, mWaveRightRectF.bottom + height);
    }

    /**
     * 更新电量百分比
     *
     * @param percent 电量百分比，有效值为[0,100]
     */
    public void updateBatteryPercent(int percent) {
        // 电量百分比的有效范围是[0,100]
        int batteryPercent = Math.max(0, percent);
        batteryPercent = Math.min(batteryPercent, 100);
        LogUtils.i(TAG, "updateBatteryPercent() percent: " + percent + ", batteryPercent: " + batteryPercent);
        mBatteryPercent = batteryPercent;
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        if (mElapsedRealTime == 0) {
            mElapsedRealTime = elapsedRealTime;
        }
        // 度过的时间
        long spentRealTime = elapsedRealTime - mElapsedRealTime;
        // 波浪走过的距离
        mWaveTranslateX += spentRealTime * mWaveSpeedPerSecond / 1000.0f;
        if (DEBUG) {
            LogUtils.i(TAG, "onDraw() spentRealTime: " + spentRealTime + " ms, waveTranslateX: " + mWaveTranslateX + " px");
        }
        if (mWaveTranslateX > mWaveWidth) {
            mWaveTranslateX -= mWaveWidth;
        }
        mElapsedRealTime = elapsedRealTime;
        if (mSolidWavePath == null) {
            mSolidWavePath = new Path();
            mSolidWavePath.addPath(getSolidLeftBottomPath());
            mSolidWavePath.addPath(getSolidRightBottomPath());
        }
        if (mHollowWavePath == null) {
            mHollowWavePath = new Path();
            mHollowWavePath.addPath(getHollowLeftBottomPath());
            mHollowWavePath.addPath(getHollowRightBottomPath());
        }

        final float waveTranslateY = -getWaveTranslateY(mBatteryPercent);

        // 绘制柱体
        canvas.save();
        canvas.translate(0, waveTranslateY);
        canvas.drawRect(mPowerLeftRectF, paint);
        canvas.drawRect(mPowerRightRectF, paint);
        canvas.restore();

        // 绘制波浪
        canvas.save();
        canvas.translate(mWaveTranslateX, waveTranslateY);
        // 绘制实心波浪
        canvas.drawPath(mSolidWavePath, paint);
        // 绘制空心波浪
        int color = paint.getColor();
        paint.setColor(HOLLOW_WAVE_COLOR);
        canvas.drawPath(mHollowWavePath, paint);
        paint.setColor(color);
        canvas.restore();
    }

    private Path getSolidLeftBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveLeftRectF.left - waveWidth, mWaveLeftRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rLineTo( waveWidth / 2, 0);
            path.rQuadTo(waveWidth / 4, -mWaveHeight * 2, waveWidth / 2, 0);
        }
        path.close();
        return path;
    }

    private Path getSolidRightBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveRightRectF.left - waveWidth, mWaveRightRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rLineTo(waveWidth / 2, 0);
            path.rQuadTo(waveWidth / 4, -mWaveHeight * 2, waveWidth / 2, 0);
        }
        path.close();
        return path;
    }

    private Path getHollowLeftBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveLeftRectF.left - waveWidth, mWaveLeftRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rQuadTo(waveWidth / 4, mWaveHeight * 2, waveWidth / 2, 0);
            path.rLineTo(waveWidth / 2, 0);
        }
        path.close();
        return path;
    }

    private Path getHollowRightBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveRightRectF.left - waveWidth, mWaveRightRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rQuadTo(waveWidth / 4, mWaveHeight * 2, waveWidth / 2, 0);
            path.rLineTo(waveWidth / 2, 0);
        }
        path.close();
        return path;
    }

    private float getWaveTranslateY(int percent) {
        return -mWaveThickness + mBigHeight * percent / 100.0f;
    }
}
