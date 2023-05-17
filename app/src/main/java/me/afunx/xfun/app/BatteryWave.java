package me.afunx.xfun.app;

import static me.afunx.xfun.app.BatterySurfaceView.HOLLOW_WAVE_COLOR;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

public class BatteryWave {

    private static final String TAG = "BatteryWave";
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
    // 实心波浪线，解决绘制空隙问题
    private Path mSolidWaveLinePath = null;
    // 空心波浪线，解决绘制空隙问题
    private Path mHollowWaveLinePath = null;

    private volatile int mBatteryPercent = 0;

    public BatteryWave() {
        // 总共波浪厚度
        mBigHeight = BatteryMetrics.bigHeight();
        // 单层波浪厚度
        mWaveThickness = BatteryMetrics.thickness();
        // 波浪高度
        mWaveHeight = BatteryMetrics.waveHeight();
        // 波浪宽度
        mWaveWidth = BatteryMetrics.waveWidth();
        // 波浪速度
        mWaveSpeedPerSecond = BatteryMetrics.waveSpeedPerSecond();
        if (DEBUG) {
            LogUtils.i(TAG, "DisplayWave() mWaveHeight: " + mWaveHeight + ", mWaveWidth: " + mWaveWidth +
                    ", mWaveSpeedPerSecond: " + mWaveSpeedPerSecond);
        }
        final float width = BatteryMetrics.bigWidth();
        final float height = BatteryMetrics.bigHeight();
        float left = BatteryMetrics.bigLeftMargin();
        float top = BatteryMetrics.bigTopMargin();
        float right = left + width;
        float bottom = top + height;
        mWaveLeftRectF = new RectF(left, top + mWaveHeight, right, bottom - mWaveHeight);
        left += BatteryMetrics.bigBetweenMargin() + width;
        right += BatteryMetrics.bigBetweenMargin() + width;
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
        if (mSolidWaveLinePath == null) {
            mSolidWaveLinePath = new Path();
            mSolidWaveLinePath.addPath(getSolidLineLeftBottomPath());
            mSolidWaveLinePath.addPath(getSolidLineRightBottomPath());
        }
        if (mHollowWavePath == null) {
            mHollowWavePath = new Path();
            mHollowWavePath.addPath(getHollowLeftBottomPath());
            mHollowWavePath.addPath(getHollowRightBottomPath());
        }
        if (mHollowWaveLinePath == null) {
            mHollowWaveLinePath = new Path();
            mHollowWaveLinePath.addPath(getHollowLineLeftBottomPath());
            mHollowWaveLinePath.addPath(getHollowLineRightBottomPath());
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
        canvas.drawPath(mSolidWaveLinePath, paint);
        // 绘制空心波浪
        int color = paint.getColor();
        paint.setColor(HOLLOW_WAVE_COLOR);
        canvas.drawPath(mHollowWavePath, paint);
        canvas.drawPath(mHollowWaveLinePath, paint);
        paint.setColor(color);
        canvas.restore();
    }

    private Path getSolidLeftBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveLeftRectF.left - waveWidth, mWaveLeftRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rMoveTo(waveWidth / 2, 0);
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
            path.rMoveTo(waveWidth / 2, 0);
            path.rQuadTo(waveWidth / 4, -mWaveHeight * 2, waveWidth / 2, 0);
        }
        path.close();
        return path;
    }

    private Path getSolidLineLeftBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveLeftRectF.left - waveWidth, mWaveLeftRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rMoveTo(waveWidth / 2, 0);
            path.rMoveTo(0, -1);
            path.rLineTo(0, 2);
            path.rLineTo(waveWidth / 2, 0);
            path.rLineTo(0, -2);
            path.close();
            path.rMoveTo(0, 1);
        }
        return path;
    }

    private Path getSolidLineRightBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveRightRectF.left - waveWidth, mWaveRightRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rMoveTo(waveWidth / 2, 0);
            path.rMoveTo(0, -1);
            path.rLineTo(0, 2);
            path.rLineTo(waveWidth / 2, 0);
            path.rLineTo(0, -2);
            path.close();
            path.rMoveTo(0, 1);
        }
        return path;
    }

    private Path getHollowLeftBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveLeftRectF.left - waveWidth, mWaveLeftRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rQuadTo(waveWidth / 4, mWaveHeight * 2, waveWidth / 2, 0);
            path.rMoveTo(waveWidth / 2, 0);
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
            path.rMoveTo(waveWidth / 2, 0);
        }
        path.close();
        return path;
    }

    private Path getHollowLineLeftBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveLeftRectF.left - waveWidth, mWaveLeftRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rMoveTo(0, -1);
            path.rLineTo(0, 2);
            path.rLineTo(waveWidth / 2, 0);
            path.rLineTo(0, -2);
            path.close();
            path.rMoveTo(0, 1);
            path.rMoveTo(waveWidth / 2, 0);
        }
        return path;
    }

    private Path getHollowLineRightBottomPath() {
        Path path = new Path();
        final float waveWidth = mWaveWidth;
        path.moveTo(mWaveRightRectF.left - waveWidth, mWaveRightRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rMoveTo(0, -1);
            path.rLineTo(0, 2);
            path.rLineTo(waveWidth / 2, 0);
            path.rLineTo(0, -2);
            path.close();
            path.rMoveTo(0, 1);
            path.rMoveTo(waveWidth / 2, 0);
        }
        return path;
    }

    private float getWaveTranslateY(int percent) {
        return -mWaveThickness + mBigHeight * percent / 100.0f;
    }
}
