package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import me.afunx.xfun.app.R;
import me.afunx.xfun.app.util.Ae2AndroidDimenUtil;

public class TrackLeftEye {

    private static final boolean DEBUG = true;
    private static final boolean CHECK = true;
    private static final boolean DEBUG_DRAW_LINE = false;

    private static final String TAG = "TrackLeftEye";

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
    private Bitmap mBitmapEyeLeftBottom2 = null;
    private Bitmap mBitmapEyeLeftIn = null;
    private Bitmap mBitmapEyeLeftIn2 = null;
    private Bitmap mBitmapEyeLeftOut = null;
    private Bitmap mBitmapEyeLeftOut2 = null;

    private int count = 0;

    private final Matrix matrix = new Matrix();

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     * @param context
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint, @NonNull Context context) {
        ++count;
        if (mLeftOutRect == null) {
            mLeftOutRect = new Rect((int) (LEFT_EYE_CENTER_X - LEFT_EYE_OUT_SIZE / 2), (int) (LEFT_EYE_CENTER_Y - LEFT_EYE_OUT_SIZE / 2),
                    (int) (LEFT_EYE_CENTER_X + LEFT_EYE_OUT_SIZE / 2), (int) (LEFT_EYE_CENTER_Y + LEFT_EYE_OUT_SIZE / 2));
            if (DEBUG) {
                Log.e(TAG, "LeftOutRect left: " + mLeftOutRect.left + ", top: " + mLeftOutRect.top + ", width: " + mLeftOutRect.width() + ", height: " + mLeftOutRect.height());
            }
        }
        if (mLeftInRect == null) {
            mLeftInRect = new Rect((int) (LEFT_EYE_CENTER_X - LEFT_EYE_IN_SIZE / 2), (int) (LEFT_EYE_CENTER_Y - LEFT_EYE_IN_SIZE / 2),
                    (int) (LEFT_EYE_CENTER_X + LEFT_EYE_IN_SIZE / 2), (int) (LEFT_EYE_CENTER_Y + LEFT_EYE_IN_SIZE / 2));
            if (DEBUG) {
                Log.e(TAG, "LeftInRect left: " + mLeftInRect.left + ", top: " + mLeftInRect.top + ", width: " + mLeftInRect.width() + ", height: " + mLeftInRect.height());
            }
        }
        if (mLeftBottomRect == null) {
            mLeftBottomRect = new Rect((int) (LEFT_EYE_BOTTOM_X - LEFT_EYE_BOTTOM_WIDTH / 2), (int) (LEFT_EYE_BOTTOM_Y - LEFT_EYE_BOTTOM_HEIGHT / 2),
                    (int) (LEFT_EYE_BOTTOM_X + LEFT_EYE_BOTTOM_WIDTH / 2), (int) (LEFT_EYE_BOTTOM_Y + LEFT_EYE_BOTTOM_HEIGHT / 2));
            if (DEBUG) {
                Log.e(TAG, "LeftBottom left: " + mLeftBottomRect.left + ", top: " + mLeftBottomRect.top + ", width: " + mLeftBottomRect.width() + ", height: " + mLeftBottomRect.height());
            }
        }
        // bottom
        if (mBitmapEyeLeftBottom == null) {
            mBitmapEyeLeftBottom = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_bottom))).getBitmap();
        }
        if (mBitmapEyeLeftBottom2 == null) {
            mBitmapEyeLeftBottom2 = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_bottom2))).getBitmap();
        }
        matrix.reset();
        matrix.postTranslate(mLeftBottomRect.left, mLeftBottomRect.top);
        if (CHECK) {
            if (count % 2 == 0) {
                canvas.drawBitmap(mBitmapEyeLeftBottom, mLeftBottomRect, mLeftBottomRect, null);
            } else {
                canvas.drawBitmap(mBitmapEyeLeftBottom2, matrix, null);
            }
        } else {
            canvas.drawBitmap(mBitmapEyeLeftBottom, mLeftBottomRect, mLeftBottomRect, null);
        }
        // out
        if (mBitmapEyeLeftOut == null) {
            mBitmapEyeLeftOut = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_out))).getBitmap();
        }
        if (mBitmapEyeLeftOut2 == null) {
            mBitmapEyeLeftOut2 = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_out2))).getBitmap();
        }
        matrix.reset();
        matrix.postTranslate(mLeftOutRect.left, mLeftOutRect.top);
        if (CHECK) {
            if (count % 2 == 0) {
                canvas.drawBitmap(mBitmapEyeLeftOut, mLeftOutRect, mLeftOutRect, null);
            } else {
                canvas.drawBitmap(mBitmapEyeLeftOut2, matrix, null);
            }
        } else {
            canvas.drawBitmap(mBitmapEyeLeftOut, mLeftOutRect, mLeftOutRect, null);
        }
        // in
        if (mBitmapEyeLeftIn == null) {
            mBitmapEyeLeftIn = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_in))).getBitmap();
        }
        if (mBitmapEyeLeftIn2 == null) {
            mBitmapEyeLeftIn2 = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_in2))).getBitmap();
        }
        matrix.reset();
        matrix.postTranslate(mLeftInRect.left, mLeftInRect.top);
        if (CHECK) {
            if (count % 2 == 0) {
                canvas.drawBitmap(mBitmapEyeLeftIn, mLeftInRect, mLeftInRect, null);
            } else {
                canvas.drawBitmap(mBitmapEyeLeftIn2, matrix, null);
            }
        } else {
            canvas.drawBitmap(mBitmapEyeLeftIn, mLeftInRect, mLeftInRect, null);
        }
        if (DEBUG_DRAW_LINE) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(0xFF00FF00);
            canvas.drawRect(mLeftOutRect, paint);
            canvas.drawRect(mLeftInRect, paint);
            canvas.drawRect(mLeftBottomRect, paint);
        }
    }
}
