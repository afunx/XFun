package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

public class TrackSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public interface DisplayFrameRateListener {
        void onFrameRate(int frameRate);
    }

    private static final String TAG = "TrackSurfaceView";
    private static final int MAX_FRAME = 60;

    private static final boolean DEBUG_FRAME_RATE_ENABLED = true;

    private final Paint mPaint;

    private boolean mIsDrawing;

    private DisplayFrameRateListener mFrameRateListener;

    private final TrackFace mTrackFace = new TrackFace();

    public TrackSurfaceView(Context context) {
        this(context, null);
    }

    public TrackSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        initView();
    }

    private void initView() {
        LogUtils.i(TAG, "initView()");
        getHolder().addCallback(this);
    }

    /**
     * 获取人脸跟踪控制器，控制器实时接收最新的人脸跟踪状态
     *
     * @return TrackController
     */
    @NonNull
    public TrackController getTrackController() {
        return mTrackFace.getTrackController();
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

        long lastFrameTimestamp = 0;

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

            // 控制帧率
            if (lastFrameTimestamp != 0) {
                // 如果帧率高于最大帧率，则进行休眠
                while (1000 / MAX_FRAME - (SystemClock.elapsedRealtime() - lastFrameTimestamp) > 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignore) {
                    }
                }
            }
            lastFrameTimestamp = SystemClock.elapsedRealtime();

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
        long elapsedRealTime = SystemClock.elapsedRealtime();
        drawBackGround(canvas);
        drawFace(elapsedRealTime, canvas, mPaint);
    }

    private void drawFace(long elapsedRealTime, Canvas canvas, Paint paint) {
        mTrackFace.onDraw(elapsedRealTime, canvas, paint, getContext());
    }

    private void drawBackGround(@NonNull Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }
}
