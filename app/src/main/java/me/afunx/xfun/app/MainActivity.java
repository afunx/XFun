package me.afunx.xfun.app;

import me.afunx.xfun.demo.DemoMainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.afunx.xfun.common.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_goto_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_goto_demo) {
            Intent intent = new Intent(this, DemoMainActivity.class);
            startActivity(intent);
        }
    }
}
