package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.afunx.xfun.common.utils.LogUtils;

import java.util.Objects;

import me.afunx.xfun.app.R;

public class TrackSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public interface DisplayFrameRateListener {
        void onFrameRate(int frameRate);
    }

    private static final String TAG = "TrackSurfaceView";

    private static final boolean DEBUG_FRAME_RATE_ENABLED = true;

    private final Paint mPaint;

    private boolean mIsDrawing;

    private DisplayFrameRateListener mFrameRateListener;

    private final TrackLeftEye mLeftEye = new TrackLeftEye();

    private final TrackRightEye mRightEye = new TrackRightEye();

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
        long elapsedRealTime = SystemClock.elapsedRealtime();
        drawBackGround(canvas);
        drawEyes(elapsedRealTime, canvas, mPaint);
    }

    private void drawEyes(long elapsedRealTime, Canvas canvas, Paint paint) {
        mLeftEye.onDraw(elapsedRealTime, canvas, paint, getContext());
        mRightEye.onDraw(elapsedRealTime, canvas, paint, getContext());
    }

    private void drawBackGround(@NonNull Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }
}
