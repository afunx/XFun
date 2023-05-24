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

public class BatterySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public interface DisplayFrameRateListener {
        void onFrameRate(int frameRate);
    }

    private static final String TAG = "BatterySurfaceView";

    private static final boolean DEBUG_TOUCH_MODE = false;
    private static final boolean DEBUG_FRAME_RATE_ENABLED = false;

    private static final int PARTICLE_COLOR = 0xFF88FFC2;
    private static final int ROUND_RECT_RING_COLOR = 0xFF88FFC2;
    private static final int WAVE_COLOR = 0xFF88FFC2;
    private static final int MASK_COLOR = Color.BLACK;

    static final int HOLLOW_WAVE_COLOR = Color.BLACK;
    private boolean mIsDrawing;
    private DisplayFrameRateListener mFrameRateListener;

    private final Paint mPaint;

    private final List<BatteryParticle> mDisplayParticleList = new ArrayList<>();
    private final BatteryMask mBatteryMask;
    private final BatteryWave mBatteryWave;
    private final BatteryRoundRect mBatteryRoundRect;
    private volatile long mElapsedTime = -1;

    public BatterySurfaceView(Context context) {
        this(context, null);
    }
    public BatterySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        BatteryMetrics.init(context);
        mBatteryMask = new BatteryMask();
        mBatteryWave = new BatteryWave();
        mBatteryRoundRect = new BatteryRoundRect();
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
        BatteryParticleCreator.createParticles(mDisplayParticleList);
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
                canvas.translate(0, BatteryMetrics.contentTranslateY() / 2);
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

    /**
     * 更新电量百分比
     *
     * @param percent 电量百分比，有效值为[0,100]
     */
    public void updateBatteryPercent(int percent) {
        mBatteryWave.updateBatteryPercent(percent);
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

        drawBackGround(canvas);
        drawWave(elapsedRealTime, canvas);
        drawMask(elapsedRealTime, canvas);
        drawParticles(elapsedRealTime, canvas);
        drawRoundRectRing(elapsedRealTime, canvas);
    }

    private void drawBackGround(@NonNull Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    private void drawWave(long elapsedRealTime, @NonNull Canvas canvas) {
        mPaint.setColor(WAVE_COLOR);
        mBatteryWave.onDraw(elapsedRealTime, canvas, mPaint);
    }

    private void drawRoundRectRing(long elapsedRealTime, @NonNull Canvas canvas) {
        mPaint.setColor(ROUND_RECT_RING_COLOR);
        mBatteryRoundRect.onDraw(elapsedRealTime, canvas, mPaint);
    }

    private void drawParticles(long elapsedRealTime, @NonNull Canvas canvas) {
        mPaint.setColor(PARTICLE_COLOR);
        for (BatteryParticle particle : mDisplayParticleList) {
            particle.onDraw(elapsedRealTime, canvas, mPaint);
        }
    }

    private void drawMask(long elapsedRealTime, Canvas canvas) {
        mPaint.setColor(MASK_COLOR);
        mBatteryMask.onDraw(elapsedRealTime, canvas, mPaint);
    }
}
