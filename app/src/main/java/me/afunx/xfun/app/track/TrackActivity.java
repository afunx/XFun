package me.afunx.xfun.app.track;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.widget.TextView;

import com.afunx.xfun.common.utils.LogUtils;

import me.afunx.xfun.app.R;

public class TrackActivity extends AppCompatActivity implements TrackSurfaceView.DisplayFrameRateListener {

    private TextView mTvFrameRate;

    private TrackSurfaceView mTrackSurfaceView;

    private final Handler mMainHandler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        mTvFrameRate = findViewById(R.id.tv_frame_rate);

        mTrackSurfaceView = findViewById(R.id.display_view);
        mTrackSurfaceView.setFrameRateListener(this);
        LogUtils.e("afunx", "mTrackSurfaceView: " + mTrackSurfaceView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_UP) {
            finish();
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