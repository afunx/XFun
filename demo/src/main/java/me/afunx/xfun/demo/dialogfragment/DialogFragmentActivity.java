package me.afunx.xfun.demo.dialogfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import me.afunx.xfun.demo.R;

public class DialogFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment);

        DemoDialogFragment dialogFragment = new DemoDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "DemoDialogFragment");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000L);
    }
}