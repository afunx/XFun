package me.afunx.xfun.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

public class BatteryRoundRect {
    private final float mBigRadius;
    private final float mThickness;
    private final RectF mLeftRectF;
    private final RectF mRightRectF;

    public BatteryRoundRect() {
        mThickness = BatteryMetrics.thickness();

        // 大
        final float bigWidth = BatteryMetrics.bigWidth();
        final float bigHeight = BatteryMetrics.bigHeight();
        float left = BatteryMetrics.bigLeftMargin();
        float top = BatteryMetrics.bigTopMargin();
        float right = left + bigWidth;
        float bottom = top + bigHeight;
        RectF bigLeftRectF = new RectF(left, top, right, bottom);
        left += BatteryMetrics.bigBetweenMargin() + bigWidth;
        right += BatteryMetrics.bigBetweenMargin() + bigWidth;
        RectF bigRightRectF = new RectF(left, top, right, bottom);
        mBigRadius = BatteryMetrics.bigRadius();

        mLeftRectF = new RectF(bigLeftRectF.left + mThickness / 2, bigLeftRectF.top + mThickness / 2, bigLeftRectF.right - mThickness / 2, bigLeftRectF.bottom - mThickness / 2);
        mRightRectF = new RectF(bigRightRectF.left + mThickness / 2, bigRightRectF.top + mThickness / 2, bigRightRectF.right - mThickness / 2, bigRightRectF.bottom - mThickness / 2);
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        Paint.Style style = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mThickness);
        canvas.drawRoundRect(mLeftRectF, mBigRadius, mBigRadius, paint);
        canvas.drawRoundRect(mRightRectF, mBigRadius, mBigRadius, paint);
        paint.setStyle(style);
    }
}
