package me.afunx.xfun.app.track;

import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

class TrackFaceResult {

    private static final String TAG = "TrackFaceResult";

    private static final boolean DEBUG = true;

    // 动效和UI机器调试给的参数
    private static final int MAX_OUT_TRANSLATE_PX = 160;

    private static int sScreenWidth;
    private static int sScreenHeight;

    static void setScreenWidth(int screenWidth) {
        if (screenWidth == 0) {
            throw new IllegalArgumentException("screenWidth shouldn't be 0");
        }
        sScreenWidth = screenWidth;
    }

    static void setScreenHeight(int screenHeight) {
        if (screenHeight == 0) {
            throw new IllegalArgumentException("screenHeight shouldn't be 0");
        }
        sScreenHeight = screenHeight;
    }

    Rect rect;
    long timestamp;
    int bitmapWidth;
    int bitmapHeight;

    void copy(@NonNull TrackFaceResult faceResult) {
        this.rect = faceResult.rect;
        this.timestamp = faceResult.timestamp;
        this.bitmapWidth = faceResult.bitmapWidth;
        this.bitmapHeight = faceResult.bitmapHeight;
    }

    // 根据屏幕大小及人脸结果，映射出EyeOut的TranslateX
    int trackFaceOutTranslateX() {
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        int validWidth = bitmapWidth - rect.width();
        int realFaceOutX = (rect.centerX() - validWidth / 2) * MAX_OUT_TRANSLATE_PX / (validWidth / 2);
        int trackFaceOutX = (int) Math.sqrt(Math.abs(realFaceOutX) * MAX_OUT_TRANSLATE_PX);
        if (realFaceOutX < 0) {
            trackFaceOutX *= -1;
        }
        if (DEBUG) {
            LogUtils.i(TAG, "trackFaceOutTranslateX() rect: " + rect + ", rect.centerX(): " + rect.centerX()
                    + ", bitmapWidth: " + bitmapWidth + ", validWidth: " + validWidth + ", realFaceOutX: " + realFaceOutX
                    + ", trackFaceOutX: " + trackFaceOutX);
        }
        return trackFaceOutX;
    }

    // 根据屏幕大小及人脸结果，映射出EyeOut的TranslateY
    int trackFaceOutTranslateY() {
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "TrackFaceResult{" +
                "rect=" + rect +
                ", timestamp=" + timestamp +
                ", bitmapWidth=" + bitmapWidth +
                ", bitmapHeight=" + bitmapHeight +
                '}';
    }
}