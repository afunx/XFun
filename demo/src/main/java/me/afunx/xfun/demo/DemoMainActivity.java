package me.afunx.xfun.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import me.afunx.xfun.demo.listview.DemoListViewActivity;

public class DemoMainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DemoMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_demo_main);
        findViewById(R.id.btn_goto_demo_list_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_goto_demo_list_view) {
            Intent intent = new Intent(this, DemoListViewActivity.class);
            startActivity(intent);
        }
    }
}
