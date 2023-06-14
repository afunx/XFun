package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

final class TrackFace {
    private final TrackLeftEye mLeftEye = new TrackLeftEye();
    private final TrackRightEye mRightEye = new TrackRightEye();
    private final TrackController mTrackController = new TrackController(mLeftEye, mRightEye);

    @NonNull
    TrackController getTrackController() {
        return mTrackController;
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     * @param context
     */
    void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint, @NonNull Context context) {
        mTrackController.onDraw(elapsedRealTime);
        mLeftEye.onDraw(elapsedRealTime, canvas, paint, context);
        mRightEye.onDraw(elapsedRealTime, canvas, paint, context);
    }
}
