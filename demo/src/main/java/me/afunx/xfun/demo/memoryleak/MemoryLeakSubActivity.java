package me.afunx.xfun.demo.memoryleak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import me.afunx.xfun.demo.R;

public class MemoryLeakSubActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak_sub);

        findViewById(R.id.btn_goto_demo_main_memory_leak_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_goto_demo_main_memory_leak_activity) {
            Intent intent = new Intent(this, MemoryLeakMainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}