package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import me.afunx.xfun.app.R;
import me.afunx.xfun.app.util.Ae2AndroidDimenUtil;

public class TrackRightEye {
    private static final float RIGHT_EYE_CENTER_X = Ae2AndroidDimenUtil.ae2androidX(1213.0f);
    private static final float RIGHT_EYE_CENTER_Y = Ae2AndroidDimenUtil.ae2androidY(608.0f);
    private static final float RIGHT_EYE_BOTTOM_X = Ae2AndroidDimenUtil.ae2androidX(1213.5f);
    private static final float RIGHT_EYE_BOTTOM_Y = Ae2AndroidDimenUtil.ae2androidY(790.5f);

    private static final float RIGHT_EYE_OUT_SIZE = 600;
    private static final float RIGHT_EYE_IN_SIZE = 420;

    private static final float RIGHT_EYE_BOTTOM_WIDTH = 400;
    private static final float RIGHT_EYE_BOTTOM_HEIGHT = 100;

    private Rect mRightOutRect = null;
    private Rect mRightInRect = null;
    private Rect mRightBottomRect = null;

    private Bitmap mBitmapEyeRightBottom;
    private Bitmap mBitmapEyeRightIn;
    private Bitmap mBitmapEyeRightOut;

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     * @param context
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint, @NonNull Context context) {
        if (mRightOutRect == null) {
            mRightOutRect = new Rect((int) (RIGHT_EYE_CENTER_X - RIGHT_EYE_OUT_SIZE / 2), (int) (RIGHT_EYE_CENTER_Y - RIGHT_EYE_OUT_SIZE / 2),
                    (int) (RIGHT_EYE_CENTER_X + RIGHT_EYE_OUT_SIZE / 2), (int) (RIGHT_EYE_CENTER_Y + RIGHT_EYE_OUT_SIZE / 2));
        }
        if (mRightInRect == null) {
            mRightInRect = new Rect((int) (RIGHT_EYE_CENTER_X - RIGHT_EYE_IN_SIZE / 2), (int) (RIGHT_EYE_CENTER_Y - RIGHT_EYE_IN_SIZE / 2),
                    (int) (RIGHT_EYE_CENTER_X + RIGHT_EYE_IN_SIZE / 2), (int) (RIGHT_EYE_CENTER_Y + RIGHT_EYE_IN_SIZE / 2));
        }
        if (mRightBottomRect == null) {
            mRightBottomRect = new Rect((int) (RIGHT_EYE_BOTTOM_X - RIGHT_EYE_BOTTOM_WIDTH / 2), (int) (RIGHT_EYE_BOTTOM_Y - RIGHT_EYE_BOTTOM_HEIGHT / 2),
                    (int) (RIGHT_EYE_BOTTOM_X + RIGHT_EYE_BOTTOM_WIDTH / 2), (int) (RIGHT_EYE_BOTTOM_Y + RIGHT_EYE_BOTTOM_HEIGHT / 2));
        }
        if (mBitmapEyeRightBottom == null) {
            mBitmapEyeRightBottom = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_bottom))).getBitmap();
        }
        canvas.drawBitmap(mBitmapEyeRightBottom, mRightBottomRect, mRightBottomRect, null);
        if (mBitmapEyeRightOut == null) {
            mBitmapEyeRightOut = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_out))).getBitmap();
        }
        canvas.drawBitmap(mBitmapEyeRightOut, mRightOutRect, mRightOutRect, null);
        if (mBitmapEyeRightIn == null) {
            mBitmapEyeRightIn = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_in))).getBitmap();
        }
        canvas.drawBitmap(mBitmapEyeRightIn, mRightInRect, mRightInRect, null);
    }
}
