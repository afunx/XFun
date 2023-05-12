package me.afunx.xfun.app;

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

import java.util.ArrayList;
import java.util.List;

public class DisplaySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public interface DisplayFrameRateListener {
        void onFrameRate(int frameRate);
    }

    private static final String TAG = "DisplaySurfaceView";

    private static final boolean DEBUG_TOUCH_MODE = false;
    private static final boolean DEBUG_FRAME_RATE_ENABLED = true;

    private static final int PARTICLE_COLOR = Color.parseColor("#FF88FFC2");
    private static final int ROUND_RECT_COLOR = Color.parseColor("#FF88FFC2");
    private boolean mIsDrawing;
    private DisplayFrameRateListener mFrameRateListener;

    private final Paint mPaint;

    private final List<DisplayParticle> mDisplayParticleList = new ArrayList<>();

    private final DisplayRoundRect mDisplayRoundRect = new DisplayRoundRect();
    private volatile long mElapsedTime = -1;

    public DisplaySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        initView();
    }

    public void updateElapsedTime(long elapsedTime) {
        mElapsedTime = elapsedTime;
    }

    public void setFrameRateListener(DisplayFrameRateListener frameRateListener) {
        mFrameRateListener = frameRateListener;
    }

    private void initView() {
        LogUtils.i(TAG, "initView()");
        addParticles();
        getHolder().addCallback(this);
    }

    private void addParticles() {
        DisplayParticleCreator.createParticles(mDisplayParticleList);
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
    }

    private void drawContent(@NonNull Canvas canvas) {
        long elapsedRealTime = SystemClock.elapsedRealtime();
        if (DEBUG_TOUCH_MODE) {
            if (mElapsedTime < 0) {
                return;
            } else {
                elapsedRealTime = mElapsedTime;
            }
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        drawParticles(elapsedRealTime, canvas);
        drawRoundRects(elapsedRealTime, canvas);
    }

    private void drawRoundRects(long elapsedRealTime, @NonNull Canvas canvas) {
        mPaint.setColor(ROUND_RECT_COLOR);
        mDisplayRoundRect.onDraw(elapsedRealTime, canvas, mPaint);
    }

    private void drawParticles(long elapsedRealTime, @NonNull Canvas canvas) {
        mPaint.setColor(PARTICLE_COLOR);
        for (DisplayParticle particle : mDisplayParticleList) {
            particle.onDraw(elapsedRealTime, canvas, mPaint);
        }
    }

}
