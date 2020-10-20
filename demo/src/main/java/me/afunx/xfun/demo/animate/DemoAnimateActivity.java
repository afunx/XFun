package me.afunx.xfun.demo.animate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.afunx.xfun.common.base.BaseActivity;

import me.afunx.xfun.demo.R;
import me.afunx.xfun.demo.animate.frame.DemoAnimateFrameActivity;

public class DemoAnimateActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_animate);

        findViewById(R.id.btn_goto_demo_animate_frame).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_goto_demo_animate_frame) {
            Intent intent = new Intent(this, DemoAnimateFrameActivity.class);
            startActivity(intent);
        }
    }
}