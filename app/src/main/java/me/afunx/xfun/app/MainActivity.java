package me.afunx.xfun.app;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.afunx.xfun.common.base.BaseActivity;
import com.ubtechinc.aimbothumming.receiver.HummingReceiver;
import com.ubtechinc.aimbothumming.utils.LogUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private static final String BROADCAST_HUMMING_ACTION = "com.ubtechinc.aimbothumming.action.enable";

    private static final String BROADCAST_HUMMING_ENABLED = "humming_enabled";

    private BroadcastReceiver mBroadcastReceiver = new HummingReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        registerReceiver(mBroadcastReceiver, new IntentFilter(BROADCAST_HUMMING_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            startHumming();
        } else if (v.getId() == R.id.btn_stop) {
            stopHumming();
        }
    }

    private void startHumming() {
        LogUtils.ee(TAG, "startHumming()");
        Intent intent = new Intent(BROADCAST_HUMMING_ACTION);
        intent.putExtra(BROADCAST_HUMMING_ENABLED, true);
        getApplicationContext().sendBroadcast(intent);

    }

    private void stopHumming() {
        LogUtils.ee(TAG, "stopHumming()");
        Intent intent = new Intent(BROADCAST_HUMMING_ACTION);
        intent.putExtra(BROADCAST_HUMMING_ENABLED, false);
        getApplicationContext().sendBroadcast(intent);
    }

}
