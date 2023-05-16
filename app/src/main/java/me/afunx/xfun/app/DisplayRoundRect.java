package me.afunx.xfun.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;

import androidx.annotation.NonNull;

public class DisplayRoundRect {

    private final RectF mBigLeftRectF;
    private final RectF mBigRightRectF;
    private final float mBigRadius;
    private final RectF mSmallLeftRectF;
    private final RectF mSmallRightRectF;
    private final float mSmallRadius;
    private final float mThickness;
    private final RectF mLeftRectF;
    private final RectF mRightRectF;
    private Picture mPictureRing = null;


    public DisplayRoundRect() {
        mThickness = DisplayMetrics.thickness();

        // 大
        final float bigWidth = DisplayMetrics.bigWidth();
        final float bigHeight = DisplayMetrics.bigHeight();
        float left = DisplayMetrics.bigLeftMargin();
        float top = DisplayMetrics.bigTopMargin();
        float right = left + bigWidth;
        float bottom = top + bigHeight;
        mBigLeftRectF = new RectF(left, top, right, bottom);
        left += DisplayMetrics.bigBetweenMargin() + bigWidth;
        right += DisplayMetrics.bigBetweenMargin() + bigWidth;
        mBigRightRectF = new RectF(left, top, right, bottom);
        mBigRadius = DisplayMetrics.bigRadius();

        // 小
        final float smallDiff = DisplayMetrics.thickness();
        left = mBigLeftRectF.left + smallDiff;
        top = mBigLeftRectF.top + smallDiff;
        right = mBigLeftRectF.right - smallDiff;
        bottom = mBigLeftRectF.bottom - smallDiff;
        mSmallLeftRectF = new RectF(left, top, right, bottom);
        left = mBigRightRectF.left + smallDiff;
        top = mBigRightRectF.top + smallDiff;
        right = mBigRightRectF.right - smallDiff;
        bottom = mBigRightRectF.bottom - smallDiff;
        mSmallRightRectF = new RectF(left, top, right, bottom);

        mSmallRadius = DisplayMetrics.smallRadius();

        mLeftRectF = new RectF(mBigLeftRectF.left + mThickness / 2, mBigLeftRectF.top + mThickness / 2, mBigLeftRectF.right - mThickness / 2, mBigLeftRectF.bottom - mThickness / 2);
        mRightRectF = new RectF(mBigRightRectF.left + mThickness / 2, mBigRightRectF.top + mThickness / 2, mBigRightRectF.right - mThickness / 2, mBigRightRectF.bottom - mThickness / 2);
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDrawOld(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        if (mPictureRing == null) {
            mPictureRing = new Picture();
            Canvas pictureCanvas = mPictureRing.beginRecording(canvas.getWidth(), canvas.getHeight());
            pictureCanvas.drawRoundRect(mBigLeftRectF, mBigRadius, mBigRadius, paint);
            pictureCanvas.drawRoundRect(mBigRightRectF, mBigRadius, mBigRadius, paint);

            paint.setColor(Color.BLACK);
            pictureCanvas.drawRoundRect(mSmallLeftRectF, mSmallRadius, mSmallRadius, paint);
            pictureCanvas.drawRoundRect(mSmallRightRectF, mSmallRadius, mSmallRadius, paint);
            mPictureRing.endRecording();
        }
        canvas.drawPicture(mPictureRing);
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

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDrawSmall(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.drawRoundRect(mSmallLeftRectF, mSmallRadius, mSmallRadius, paint);
        canvas.drawRoundRect(mSmallRightRectF, mSmallRadius, mSmallRadius, paint);
    }
}
