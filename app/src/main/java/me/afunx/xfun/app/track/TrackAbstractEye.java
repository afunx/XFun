package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

abstract class TrackAbstractEye {
    private static final float EYE_OUT_SIZE = 600;
    private static final float EYE_IN_SIZE = 420;

    private static final float EYE_BOTTOM_WIDTH = 400;
    private static final float EYE_BOTTOM_HEIGHT = 100;

    /**
     * 获取眼睛名字，如左眼、右眼
     *
     * @return  眼睛名字，如左眼、右眼
     */
    protected abstract String eyeName();

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

    @NonNull
    TrackPosture getOutPosture() {
        return mOutPosture;
    }

    @NonNull
    TrackPosture getInPosture() {
        return mInPosture;
    }

    @NonNull
    TrackPosture getBottomPosture() {
        return mBottomPosture;
    }

    private final float mEyeCenterX;
    private final float mEyeCenterY;
    private final float mEyeBottomX;
    private final float mEyeBottomY;

    public TrackAbstractEye() {
        mEyeCenterX = eyeCenterX();
        mEyeCenterY = eyeCenterY();
        mEyeBottomX = eyeBottomX();
        mEyeBottomY = eyeBottomY();

        mOutPosture = new TrackPosture(eyeName() + "Out", (int) (mEyeCenterX - EYE_OUT_SIZE / 2), (int) (mEyeCenterY - EYE_OUT_SIZE / 2),
                (int) (mEyeCenterX + EYE_OUT_SIZE / 2), (int) (mEyeCenterY + EYE_OUT_SIZE / 2));

        mInPosture = new TrackPosture(eyeName() + "In", (int) (mEyeCenterX - EYE_IN_SIZE / 2), (int) (mEyeCenterY - EYE_IN_SIZE / 2),
                (int) (mEyeCenterX + EYE_IN_SIZE / 2), (int) (mEyeCenterY + EYE_IN_SIZE / 2));

        mBottomPosture = new TrackPosture(eyeName() + "Bottom", (int) (mEyeBottomX - EYE_BOTTOM_WIDTH / 2), (int) (mEyeBottomY - EYE_BOTTOM_HEIGHT / 2),
                (int) (mEyeBottomX + EYE_BOTTOM_WIDTH / 2), (int) (mEyeBottomY + EYE_BOTTOM_HEIGHT / 2));
    }

    private final TrackPosture mOutPosture;
    private final TrackPosture mInPosture;
    private final TrackPosture mBottomPosture;

    private Bitmap mBitmapEyeBottom = null;
    private Bitmap mBitmapEyeIn = null;
    private Bitmap mBitmapEyeOut = null;

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
        canvas.drawBitmap(mBitmapEyeBottom, mBottomPosture.getMatrix(), null);
        // out
        canvas.drawBitmap(mBitmapEyeOut, mOutPosture.getMatrix(), null);
        // in
        canvas.drawBitmap(mBitmapEyeIn, mInPosture.getMatrix(), null);
    }

}
