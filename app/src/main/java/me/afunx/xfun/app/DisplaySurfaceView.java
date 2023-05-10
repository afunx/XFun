package me.afunx.xfun.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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

    private static final boolean FRAME_RATE_ENABLED = true;

    private boolean mIsDrawing;
    private DisplayFrameRateListener mFrameRateListener;

    private int mContent0Index = 0;

    private final int[] mContent0Ids;

    private Bitmap mContent0Bitmap;

    private final Matrix mContent0Matrix;

    private final Paint mPaint;

    private final List<DisplayParticle> mDisplayParticleList = new ArrayList<>();

    public DisplaySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mContent0Matrix = new Matrix();
        mContent0Matrix.postScale(3.75f, 3.75f);
        mContent0Ids = new int[386];
        mContent0Ids[0] = R.drawable.particle001;
        for (int i = 1; i < mContent0Ids.length; i++) {
            mContent0Ids[i] = mContent0Ids[0] + i;
        }
        initView();
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
        displayParticleCreator.createParticles(mDisplayParticleList);
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
            if (FRAME_RATE_ENABLED) {
                long current = SystemClock.elapsedRealtime();
                if (lastTimestamp == 0) {
                    lastTimestamp = current;
                } else {
                    if (current - lastTimestamp >= interval) {
                        ++frameCount;
                        if (mFrameRateListener != null) {
                            mFrameRateListener.onFrameRate(frameCount);
                        }
                        frameCount = -1;
                        lastTimestamp = current;
                    } else {
                        ++frameCount;
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
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, 1920, 1200, mPaint);
        drawParticles(canvas);
        //drawContent0(canvas);
    }

    private void drawContent0(@NonNull Canvas canvas) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inBitmap = mContent0Bitmap;

        mContent0Bitmap = BitmapFactory.decodeResource(getResources(), mContent0Ids[mContent0Index], options);
        canvas.drawBitmap(mContent0Bitmap, mContent0Matrix, mPaint);
        mContent0Index = (mContent0Index + 1) % mContent0Ids.length;
    }

    private void drawParticles(@NonNull Canvas canvas) {
        mPaint.setColor(Color.parseColor("#FF88FFC2"));
        long elapsedRealTime = SystemClock.elapsedRealtime();
        for (DisplayParticle particle : mDisplayParticleList) {
            particle.onDraw(elapsedRealTime, canvas, mPaint);
        }
    }

}
