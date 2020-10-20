package me.afunx.xfun.demo.animate.frame;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.afunx.xfun.common.base.BaseActivity;

import me.afunx.xfun.demo.R;

public class DemoAnimateFrameActivity extends BaseActivity {

    private AnimationDrawable mAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_animate_frame);

        ImageView imageView = findViewById(R.id.image);
        mAnimationDrawable = (AnimationDrawable) imageView.getBackground();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mAnimationDrawable.start();
        } else {
            mAnimationDrawable.stop();
        }
    }
}