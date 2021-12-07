package me.afunx.xfun.demo.memoryleak;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import me.afunx.xfun.demo.R;

public class MemoryLeakMainActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    private static Context sMemoryLeakContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // memory leak
        sMemoryLeakContext = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak_main);

        findViewById(R.id.btn_goto_demo_sub_memory_leak_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_goto_demo_sub_memory_leak_activity) {
            Intent intent = new Intent(this, MemoryLeakSubActivity.class);
            startActivity(intent);
            finish();
        }
    }
}