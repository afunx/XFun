package me.afunx.xfun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.widget.TextView;

import com.afunx.xfun.common.utils.LogUtils;

public class DisplayActivity extends AppCompatActivity implements DisplaySurfaceView.DisplayFrameRateListener {

    private static final String TAG = "DisplayActivity";

    private DisplaySurfaceView mDisplaySurfaceView;

    private TextView mTvFrameRate;

    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mTvFrameRate = findViewById(R.id.tv_frame_rate);

        mDisplaySurfaceView = findViewById(R.id.display_view);
        mDisplaySurfaceView.setFrameRateListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_UP) {
            mDisplaySurfaceView.updateElapsedTime(count * 100L);
            LogUtils.e(TAG, "DisplayParticle count: " + count + ", ev: " + ev);
            ++count;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onFrameRate(int frameRate) {
        updateFrameRate(frameRate);
    }

    private void updateFrameRate(int rate) {
        mMainHandler.removeCallbacksAndMessages(null);
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mTvFrameRate.setText(String.valueOf(rate));
            }
        });
    }
}