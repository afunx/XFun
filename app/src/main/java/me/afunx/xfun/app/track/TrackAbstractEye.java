package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import java.util.Arrays;

public abstract class TrackAbstractEye {
    private static final float FLOAT_PRECISION = 1e-6f;
    private static final float EYE_OUT_SIZE = 600;
    private static final float EYE_IN_SIZE = 420;

    private static final float EYE_BOTTOM_WIDTH = 400;
    private static final float EYE_BOTTOM_HEIGHT = 100;

    /**
     * 获取眼睛中间中点x坐标，单位：像素
     *
     * @return 眼睛中间中点x坐标，单位：像素
     */
    protected abstract float eyeCenterX();

    /**
     * 获取眼睛中间中点y坐标，单位：像素
     *
     * @return 眼睛中间中点y坐标，单位：像素
     */
    protected abstract float eyeCenterY();

    /**
     * 获取眼睛底部中点x坐标，单位：像素
     *
     * @return 眼睛底部中点x坐标，单位：像素
     */
    protected abstract float eyeBottomX();

    /**
     * 获取眼睛底部中点y坐标，单位：像素
     *
     * @return 眼睛底部中点y坐标，单位：像素
     */
    protected abstract float eyeBottomY();

    /**
     * 获取眼睛底部图片
     *
     * @param context
     * @return 眼睛底部图片
     */
    protected abstract Bitmap eyeBitmapBottom(@NonNull Context context);

    /**
     * 获取眼睛外部图片
     *
     * @param context
     * @return 眼睛外部图片
     */
    protected abstract Bitmap eyeBitmapOut(@NonNull Context context);

    /**
     * 获取眼睛内部图片
     *
     * @param context
     * @return 眼睛内部图片
     */
    protected abstract Bitmap eyeBitmapIn(@NonNull Context context);

    private final float mEyeCenterX;
    private final float mEyeCenterY;
    private final float mEyeBottomX;
    private final float mEyeBottomY;

    public TrackAbstractEye() {
        mEyeCenterX = eyeCenterX();
        mEyeCenterY = eyeCenterY();
        mEyeBottomX = eyeBottomX();
        mEyeBottomY = eyeBottomY();

        mOutRect = new Rect((int) (mEyeCenterX - EYE_OUT_SIZE / 2), (int) (mEyeCenterY - EYE_OUT_SIZE / 2),
                (int) (mEyeCenterX + EYE_OUT_SIZE / 2), (int) (mEyeCenterY + EYE_OUT_SIZE / 2));
        mInRect = new Rect((int) (mEyeCenterX - EYE_IN_SIZE / 2), (int) (mEyeCenterY - EYE_IN_SIZE / 2),
                (int) (mEyeCenterX + EYE_IN_SIZE / 2), (int) (mEyeCenterY + EYE_IN_SIZE / 2));
        mBottomRect = new Rect((int) (mEyeBottomX - EYE_BOTTOM_WIDTH / 2), (int) (mEyeBottomY - EYE_BOTTOM_HEIGHT / 2),
                (int) (mEyeBottomX + EYE_BOTTOM_WIDTH / 2), (int) (mEyeBottomY + EYE_BOTTOM_HEIGHT / 2));

        mMatrixEyeOut.postTranslate(mOutRect.left, mOutRect.top);
        mMatrixEyeIn.postTranslate(mInRect.left, mInRect.top);
        mMatrixEyeBottom.postTranslate(mBottomRect.left, mBottomRect.top);
    }

    private final Rect mOutRect;
    private final Rect mInRect;
    private final Rect mBottomRect;

    private Bitmap mBitmapEyeBottom = null;
    private Bitmap mBitmapEyeIn = null;
    private Bitmap mBitmapEyeOut = null;

    private final Matrix mMatrixEyeBottom = new Matrix();
    private final Matrix mMatrixEyeIn = new Matrix();
    private final Matrix mMatrixEyeOut = new Matrix();

    private final float[] mMatrixValues = new float[9];

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     * @param context
     */
    public final void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint, @NonNull Context context) {
        if (mBitmapEyeBottom == null) {
            mBitmapEyeBottom = eyeBitmapBottom(context);
        }
        if (mBitmapEyeOut == null) {
            mBitmapEyeOut = eyeBitmapOut(context);
        }
        if (mBitmapEyeIn == null) {
            mBitmapEyeIn = eyeBitmapIn(context);
        }
        // bottom
        canvas.drawBitmap(mBitmapEyeBottom, mMatrixEyeBottom, null);
        // scale(2f, 1f, mOutRect, mMatrixEyeOut, mMatrixValues);
        // out
        canvas.drawBitmap(mBitmapEyeOut, mMatrixEyeOut, null);
        // in
        canvas.drawBitmap(mBitmapEyeIn, mMatrixEyeIn, null);
    }

    private void scale(float scaleX, float scaleY, @NonNull Rect rect, @NonNull Matrix matrix, @NonNull float[] matrixValues) {
        // scaleX  skewX   translateX  (0-2)
        // skewY   scaleY  translateY  (3-5)
        // 0       0       1           (6-8)
        matrix.getValues(matrixValues);
        final float sx = matrixValues[0];
        final float sy = matrixValues[4];
        if (Math.abs(sx - scaleX) <= FLOAT_PRECISION && Math.abs(sy - scaleY) <= FLOAT_PRECISION) {
            return;
        }
        final float tx = matrixValues[2];
        final float ty = matrixValues[5];
        final float px = rect.width() / 2f;
        final float py = rect.height() / 2f;
        matrix.postTranslate(-tx, -ty);
        matrix.postScale(scaleX / sx, scaleY / sy, px, py);
        matrix.postTranslate(tx, ty);
    }

    private void translate(float translateX, float translateY,  @NonNull Rect rect, @NonNull Matrix matrix, @NonNull float[] matrixValues) {
        // sx  0   tx  (0-2)
        // 0   sy  ty  (3-5)
        // 0   0   1   (6-8)
    }

}
