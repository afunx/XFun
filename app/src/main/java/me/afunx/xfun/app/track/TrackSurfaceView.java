package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.afunx.xfun.common.utils.LogUtils;
public class TrackSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public interface DisplayFrameRateListener {
        void onFrameRate(int frameRate);
    }

    private static final String TAG = "TrackSurfaceView";

    private static final boolean DEBUG_FRAME_RATE_ENABLED = true;
    private boolean mIsDrawing;

    private DisplayFrameRateListener mFrameRateListener;


    public TrackSurfaceView(Context context) {
        this(context, null);
    }

    public TrackSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LogUtils.i(TAG, "initView()");
        getHolder().addCallback(this);
    }

    public void setFrameRateListener(DisplayFrameRateListener frameRateListener) {
        mFrameRateListener = frameRateListener;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.i(TAG, "surfaceCreated()");
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtils.i(TAG, "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.i(TAG, "surfaceDestroyed()");
        mIsDrawing = false;
    }

    @Override
    public void run() {
        final long interval = 1000;
        long lastTimestamp = 0;
        int frameCount = -1;

        LogUtils.i(TAG, "run() E");
        while (mIsDrawing) {
            if (DEBUG_FRAME_RATE_ENABLED) {
                long current = SystemClock.elapsedRealtime();
                if (lastTimestamp == 0) {
                    lastTimestamp = current;
                } else {
                    ++frameCount;
                    if (current - lastTimestamp >= interval) {
                        if (mFrameRateListener != null) {
                            mFrameRateListener.onFrameRate(frameCount);
                        }
                        frameCount = -1;
                        lastTimestamp = current;
                    }
                }
            }

            Canvas canvas = null;
            try {
                canvas = this.getHolder().lockCanvas();
                if (canvas == null) {
                    continue;
                }
                drawContent(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
        LogUtils.i(TAG, "run() X");
    }

    private void drawContent(Canvas canvas) {
    }
}
