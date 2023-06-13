package me.afunx.xfun.app.track;

import android.graphics.Matrix;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

public class TrackPosture {
    private static final boolean DEBUG = true;
    private static final String TAG = "TrackPosture";
    private final String mName;

    // scaleX  skewX   translateX  (0-2)
    // skewY   scaleY  translateY  (3-5)
    // 0       0       1           (6-8)
    private final Matrix mMatrix = new Matrix();

    private float mTranslateX = 0.0f;
    private float mTranslateY = 0.0f;

    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;

    private final Rect mRect = new Rect();

    public TrackPosture(@NonNull String name, int left, int top, int right, int bottom) {
        mName = name;
        LogUtils.i(TAG, name + "::TrackPosture() left: " + left + ", top: " + top + ", right: " + right + ", bottom: " + bottom);
        mRect.set(left, top, right, bottom);
        mMatrix.postTranslate(left, top);
    }

    public Matrix getMatrix() {
        return mMatrix;
    }

    public void scale(float scaleX, float scaleY) {
        if (mScaleX == scaleX && mScaleY == scaleY) {
            return;
        }
        if (DEBUG) {
            LogUtils.i(TAG, mName + "::scale() scaleX: " + scaleX + ", scaleY: " + scaleY);
        }
        final float tx = mRect.left + mTranslateX;
        final float ty = mRect.top + mTranslateY;
        final float px = mRect.width() / 2f;
        final float py = mRect.height() / 2f;
        mMatrix.postTranslate(-tx, -ty);
        mMatrix.postScale(scaleX / mScaleX, scaleY / mScaleY, px, py);
        mMatrix.postTranslate(tx, ty);
        mScaleX = scaleX;
        mScaleY = scaleY;
    }

    public void translate(float translateX, float translateY) {
        if (mTranslateX == translateX && mTranslateY == translateY) {
            return;
        }
        if (DEBUG) {
            LogUtils.i(TAG, mName + "::translate() translateX: " + translateX + ", translateY: " + translateY);
        }
        mMatrix.postTranslate(translateX - mTranslateX, translateY - mTranslateY);
        mTranslateX = translateX;
        mTranslateY = translateY;
    }
}
