package me.afunx.xfun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity implements DisplaySurfaceView.DisplayFrameRateListener {

    private DisplaySurfaceView mDisplaySurfaceView;

    private TextView mTvFrameRate;

    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mTvFrameRate = findViewById(R.id.tv_frame_rate);

        mDisplaySurfaceView = findViewById(R.id.display_view);
        mDisplaySurfaceView.setFrameRateListener(this);
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