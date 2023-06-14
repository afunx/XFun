package me.afunx.xfun.app.track;

import androidx.annotation.NonNull;

class TrackAnimation {
    float mTranslateX = 0.0f;
    float mTranslateY = 0.0f;
    float mScaleX = 1.0f;
    float mScaleY = 1.0f;

    void reset() {
        this.mTranslateX = 0.0f;
        this.mTranslateY = 0.0f;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
    }

    void copy(@NonNull TrackAnimation trackAnimation) {
        this.mTranslateX = trackAnimation.mTranslateX;
        this.mTranslateY = trackAnimation.mTranslateY;
        this.mScaleX = trackAnimation.mScaleX;
        this.mScaleY = trackAnimation.mScaleY;
    }

    float distance(@NonNull TrackAnimation trackAnimation) {
        return (float) Math.sqrt((this.mTranslateX - trackAnimation.mTranslateX) * (this.mTranslateX - trackAnimation.mTranslateX) + (this.mTranslateY - trackAnimation.mTranslateY) * (this.mTranslateY - trackAnimation.mTranslateY));
    }
}
