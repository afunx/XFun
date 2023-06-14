package me.afunx.xfun.app.track;

import android.graphics.Matrix;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

class TrackPosture {
    private static final boolean DEBUG = true;
    private static final String TAG = "TrackPosture";
    private final String mName;

    // scaleX  skewX   translateX  (0-2)
    // skewY   scaleY  translateY  (3-5)
    // 0       0       1           (6-8)
    private final Matrix mMatrix = new Matrix();

    private final TrackAnimation mTrackAnimation = new TrackAnimation();

    private final Rect mRect = new Rect();

    TrackPosture(@NonNull String name, int left, int top, int right, int bottom) {
        mName = name;
        LogUtils.i(TAG, name + "::TrackPosture() left: " + left + ", top: " + top + ", right: " + right + ", bottom: " + bottom);
        mRect.set(left, top, right, bottom);
        mMatrix.postTranslate(left, top);
    }

    TrackPosture(@NonNull String subName, @NonNull TrackPosture trackPosture) {
        mName = trackPosture.mName + "-" + subName;
        int left = trackPosture.mRect.left;
        int top = trackPosture.mRect.top;
        int right = trackPosture.mRect.right;
        int bottom = trackPosture.mRect.bottom;
        LogUtils.i(TAG, mName + "::TrackPosture() left: " + left + ", top: " + top + ", right: " + right + ", bottom: " + bottom);
        mRect.set(left, top, right, bottom);
        mMatrix.postTranslate(left, top);
    }

    Matrix getMatrix() {
        return mMatrix;
    }

    TrackAnimation getTrackAnimation() {
        return mTrackAnimation;
    }

    void scale(float scaleX, float scaleY) {
        if (mTrackAnimation.mScaleX == scaleX && mTrackAnimation.mScaleY == scaleY) {
            return;
        }
        if (DEBUG) {
            LogUtils.i(TAG, mName + "::scale() scaleX: " + scaleX + ", scaleY: " + scaleY);
        }
        final float tx = mRect.left + mTrackAnimation.mTranslateX;
        final float ty = mRect.top + mTrackAnimation.mTranslateY;
        final float px = mRect.width() / 2f;
        final float py = mRect.height() / 2f;
        mMatrix.postTranslate(-tx, -ty);
        mMatrix.postScale(scaleX / mTrackAnimation.mScaleX, scaleY / mTrackAnimation.mScaleY, px, py);
        mMatrix.postTranslate(tx, ty);
        mTrackAnimation.mScaleX = scaleX;
        mTrackAnimation.mScaleY = scaleY;
    }

    void translate(float translateX, float translateY) {
        if (mTrackAnimation.mTranslateX == translateX && mTrackAnimation.mTranslateY == translateY) {
            return;
        }
        if (DEBUG) {
            LogUtils.i(TAG, mName + "::translate() translateX: " + translateX + ", translateY: " + translateY);
        }
        mMatrix.postTranslate(translateX - mTrackAnimation.mTranslateX, translateY - mTrackAnimation.mTranslateY);
        mTrackAnimation.mTranslateX = translateX;
        mTrackAnimation.mTranslateY = translateY;
    }

    void translateDelta(float deltaTranslateX, float deltaTranslateY) {
        if (deltaTranslateX == 0 && deltaTranslateY == 0) {
            return;
        }
        if (DEBUG) {
            LogUtils.i(TAG, mName + "::translateDelta() deltaTranslateX: " + deltaTranslateX + ", deltaTranslateY: " + deltaTranslateY);
        }
        mMatrix.postTranslate(deltaTranslateX, deltaTranslateY);
        mTrackAnimation.mTranslateX += deltaTranslateX;
        mTrackAnimation.mTranslateY += deltaTranslateY;
        if (DEBUG) {
            LogUtils.e(TAG, mName + "::translateDelta() translateX: " + mTrackAnimation.mTranslateX + ", translateY: " + mTrackAnimation.mTranslateY);
        }
    }
}
