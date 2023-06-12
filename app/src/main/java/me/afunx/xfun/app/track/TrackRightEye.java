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

public class TrackRightEye {

    private static final boolean DEBUG = true;
    private static final boolean CHECK = false;
    private static final boolean DEBUG_DRAW_LINE = false;

    private static final String TAG = "TrackRightEye";

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

    private Bitmap mBitmapEyeRightBottom = null;
    private Bitmap mBitmapEyeRightBottom2 = null;
    private Bitmap mBitmapEyeRightIn = null;
    private Bitmap mBitmapEyeRightIn2 = null;
    private Bitmap mBitmapEyeRightOut = null;
    private Bitmap mBitmapEyeRightOut2 = null;

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
        if (mRightOutRect == null) {
            mRightOutRect = new Rect((int) (RIGHT_EYE_CENTER_X - RIGHT_EYE_OUT_SIZE / 2), (int) (RIGHT_EYE_CENTER_Y - RIGHT_EYE_OUT_SIZE / 2),
                    (int) (RIGHT_EYE_CENTER_X + RIGHT_EYE_OUT_SIZE / 2), (int) (RIGHT_EYE_CENTER_Y + RIGHT_EYE_OUT_SIZE / 2));
            if (DEBUG) {
                Log.e(TAG, "RightOutRect left: " + mRightOutRect.left + ", top: " + mRightOutRect.top + ", width: " + mRightOutRect.width() + ", height: " + mRightOutRect.height());
            }
        }
        if (mRightInRect == null) {
            mRightInRect = new Rect((int) (RIGHT_EYE_CENTER_X - RIGHT_EYE_IN_SIZE / 2), (int) (RIGHT_EYE_CENTER_Y - RIGHT_EYE_IN_SIZE / 2),
                    (int) (RIGHT_EYE_CENTER_X + RIGHT_EYE_IN_SIZE / 2), (int) (RIGHT_EYE_CENTER_Y + RIGHT_EYE_IN_SIZE / 2));
            if (DEBUG) {
                Log.e(TAG, "RightInRect left: " + mRightInRect.left + ", top: " + mRightInRect.top + ", width: " + mRightInRect.width() + ", height: " + mRightInRect.height());
            }
        }
        if (mRightBottomRect == null) {
            mRightBottomRect = new Rect((int) (RIGHT_EYE_BOTTOM_X - RIGHT_EYE_BOTTOM_WIDTH / 2), (int) (RIGHT_EYE_BOTTOM_Y - RIGHT_EYE_BOTTOM_HEIGHT / 2),
                    (int) (RIGHT_EYE_BOTTOM_X + RIGHT_EYE_BOTTOM_WIDTH / 2), (int) (RIGHT_EYE_BOTTOM_Y + RIGHT_EYE_BOTTOM_HEIGHT / 2));
            if (DEBUG) {
                Log.e(TAG, "RightBottom left: " + mRightBottomRect.left + ", top: " + mRightBottomRect.top + ", width: " + mRightBottomRect.width() + ", height: " + mRightBottomRect.height());
            }
        }
        // bottom
        if (mBitmapEyeRightBottom == null) {
            mBitmapEyeRightBottom = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_bottom))).getBitmap();
        }
        if (mBitmapEyeRightBottom2 == null) {
            mBitmapEyeRightBottom2 = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_bottom2))).getBitmap();
        }
        matrix.reset();
        matrix.postTranslate(mRightBottomRect.left, mRightBottomRect.top);
        if (CHECK) {
            if (count % 2 == 0) {
                canvas.drawBitmap(mBitmapEyeRightBottom, mRightBottomRect, mRightBottomRect, null);
            } else {
                canvas.drawBitmap(mBitmapEyeRightBottom2, matrix, null);
            }
        } else {
            canvas.drawBitmap(mBitmapEyeRightBottom, mRightBottomRect, mRightBottomRect, null);
        }
        // out
        if (mBitmapEyeRightOut == null) {
            mBitmapEyeRightOut = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_out))).getBitmap();
        }
        if (mBitmapEyeRightOut2 == null) {
            mBitmapEyeRightOut2 = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_out2))).getBitmap();
        }
        matrix.reset();
        matrix.postTranslate(mRightOutRect.left, mRightOutRect.top);
        if (CHECK) {
            if (count % 2 == 0) {
                canvas.drawBitmap(mBitmapEyeRightOut, mRightOutRect, mRightOutRect, null);
            } else {
                canvas.drawBitmap(mBitmapEyeRightOut2, matrix, null);
            }
        } else {
            canvas.drawBitmap(mBitmapEyeRightOut, mRightOutRect, mRightOutRect, null);
        }
        // in
        if (mBitmapEyeRightIn == null) {
            mBitmapEyeRightIn = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_in))).getBitmap();
        }
        if (mBitmapEyeRightIn2 == null) {
            mBitmapEyeRightIn2 = ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_in2))).getBitmap();
        }
        matrix.reset();
        matrix.postTranslate(mRightInRect.left, mRightInRect.top);
        if (CHECK) {
            if (count % 2 == 0) {
                canvas.drawBitmap(mBitmapEyeRightIn, mRightInRect, mRightInRect, null);
            } else {
                canvas.drawBitmap(mBitmapEyeRightIn2, matrix, null);
            }
        } else {
            canvas.drawBitmap(mBitmapEyeRightIn, mRightInRect, mRightInRect, null);
        }
        if (DEBUG_DRAW_LINE) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(0xFF00FF00);
            canvas.drawRect(mRightOutRect, paint);
            canvas.drawRect(mRightInRect, paint);
            canvas.drawRect(mRightBottomRect, paint);
        }
    }
}
