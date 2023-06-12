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

public class TrackLeftEye {
    private static final float LEFT_EYE_CENTER_X = Ae2AndroidDimenUtil.ae2androidX(703.0f);
    private static final float LEFT_EYE_CENTER_Y = Ae2AndroidDimenUtil.ae2androidY(608.0f);
    private static final float LEFT_EYE_BOTTOM_X = Ae2AndroidDimenUtil.ae2androidX(703.5f);
    private static final float LEFT_EYE_BOTTOM_Y = Ae2AndroidDimenUtil.ae2androidY(790.5f);

    private static final float LEFT_EYE_OUT_SIZE = 600;
    private static final float LEFT_EYE_IN_SIZE = 420;

    private static final float LEFT_EYE_BOTTOM_WIDTH = 400;
    private static final float LEFT_EYE_BOTTOM_HEIGHT = 100;

    private Rect mLeftOutRect = null;
    private Rect mLeftInRect = null;
    private Rect mLeftBottomRect = null;

    private Bitmap mBitmapEyeLeftBottom = null;
    private Bitmap mBitmapEyeLeftIn = null;
    private Bitmap mBitmapEyeLeftOut = null;

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     * @param context
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint, @NonNull Context context) {
        if (mLeftOutRect == null) {
            mLeftOutRect = new Rect((int) (LEFT_EYE_CENTER_X - LEFT_EYE_OUT_SIZE / 2), (int) (LEFT_EYE_CENTER_Y - LEFT_EYE_OUT_SIZE / 2),
                    (int) (LEFT_EYE_CENTER_X + LEFT_EYE_OUT_SIZE / 2), (int) (LEFT_EYE_CENTER_Y + LEFT_EYE_OUT_SIZE / 2));
        }
        if (mLeftInRect == null) {
            mLeftInRect = new Rect((int) (LEFT_EYE_CENTER_X - LEFT_EYE_IN_SIZE / 2), (int) (LEFT_EYE_CENTER_Y - LEFT_EYE_IN_SIZE / 2),
                    (int) (LEFT_EYE_CENTER_X + LEFT_EYE_IN_SIZE / 2), (int) (LEFT_EYE_CENTER_Y + LEFT_EYE_IN_SIZE / 2));
        }
        if (mLeftBottomRect == null) {
            mLeftBottomRect = new Rect((int) (LEFT_EYE_BOTTOM_X - LEFT_EYE_BOTTOM_WIDTH / 2), (int) (LEFT_EYE_BOTTOM_Y - LEFT_EYE_BOTTOM_HEIGHT / 2),
                    (int) (LEFT_EYE_BOTTOM_X + LEFT_EYE_BOTTOM_WIDTH / 2), (int) (LEFT_EYE_BOTTOM_Y + LEFT_EYE_BOTTOM_HEIGHT / 2));
        }
        if (mBitmapEyeLeftBottom == null) {
            mBitmapEyeLeftBottom = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_bottom))).getBitmap();
        }
        canvas.drawBitmap(mBitmapEyeLeftBottom, mLeftBottomRect, mLeftBottomRect, null);
        if (mBitmapEyeLeftOut == null) {
            mBitmapEyeLeftOut = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_out))).getBitmap();
        }
        canvas.drawBitmap(mBitmapEyeLeftOut, mLeftOutRect, mLeftOutRect, null);
        if (mBitmapEyeLeftIn == null) {
            mBitmapEyeLeftIn = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_in))).getBitmap();
        }
        canvas.drawBitmap(mBitmapEyeLeftIn, mLeftInRect, mLeftInRect, null);
    }
}
