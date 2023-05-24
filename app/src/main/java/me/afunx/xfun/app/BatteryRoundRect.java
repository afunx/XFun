package me.afunx.xfun.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import androidx.annotation.NonNull;

public class BatteryRoundRect {
    private final float mBigRadius;
    private final float mThickness;
    private final RectF mLeftRectF;
    private final RectF mRightRectF;

    // 解决绘制空隙问题
    private Path mLinePath = null;

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
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(mThickness);
        canvas.drawRoundRect(mLeftRectF, mBigRadius, mBigRadius, paint);
        canvas.drawRoundRect(mRightRectF, mBigRadius, mBigRadius, paint);
        paint.setStyle(style);
        if (mLinePath == null) {
            mLinePath = getLinePath();
        }
        canvas.drawPath(mLinePath, paint);
    }

    private Path getLinePath() {
        Path path = new Path();
        final float thickness = BatteryMetrics.thickness();
        final float bigWidth = BatteryMetrics.bigWidth();
        final float bigHeight = BatteryMetrics.bigHeight();
        final float left = BatteryMetrics.bigLeftMargin();
        final float top = BatteryMetrics.bigTopMargin();
        final float bottom = top + bigHeight;
        final float cx = left + thickness;
        final float cy = top + bigHeight / 2.0f;
        final float length = (bottom - top) / 2.0f;
        path.moveTo(cx, cy - length / 2.0f);
        // 左圆左边
        path.rLineTo(-2, 0);
        path.rLineTo(4, 0);
        path.rLineTo(0, length);
        path.rLineTo(-4, 0);
        path.close();
        // 左圆右边
        path.addPath(path, bigWidth - 2.0f * thickness, 0);
        // 右圆左右两边
        path.addPath(path, BatteryMetrics.bigBetweenMargin() + bigWidth, 0);
        return path;
    }
}
