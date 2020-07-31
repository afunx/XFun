package me.afunx.xfun.app;

import androidx.appcompat.app.AppCompatActivity;
import me.afunx.xfun.demo.DemoMainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
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
