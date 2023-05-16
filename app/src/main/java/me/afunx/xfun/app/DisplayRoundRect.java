package me.afunx.xfun.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

public class DisplayRoundRect {
    private final float mBigRadius;
    private final float mThickness;
    private final RectF mLeftRectF;
    private final RectF mRightRectF;

    public DisplayRoundRect() {
        mThickness = DisplayMetrics.thickness();

        // 大
        final float bigWidth = DisplayMetrics.bigWidth();
        final float bigHeight = DisplayMetrics.bigHeight();
        float left = DisplayMetrics.bigLeftMargin();
        float top = DisplayMetrics.bigTopMargin();
        float right = left + bigWidth;
        float bottom = top + bigHeight;
        RectF bigLeftRectF = new RectF(left, top, right, bottom);
        left += DisplayMetrics.bigBetweenMargin() + bigWidth;
        right += DisplayMetrics.bigBetweenMargin() + bigWidth;
        RectF bigRightRectF = new RectF(left, top, right, bottom);
        mBigRadius = DisplayMetrics.bigRadius();

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
