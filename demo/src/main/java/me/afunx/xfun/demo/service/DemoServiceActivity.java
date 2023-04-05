package me.afunx.xfun.demo.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.afunx.xfun.common.utils.LogUtils;

import me.afunx.xfun.demo.R;

public class DemoServiceActivity extends AppCompatActivity {

    private static final String TAG = "DemoServiceActivity";

    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    private void bindService() {
        Intent intent = new Intent(this, DemoService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        unbindService(connection);
    }

    private void startService() {
        Intent intent = new Intent(this, DemoService.class);
        startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(this, DemoService.class);
        stopService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_service);

        startService();
        bindService();
        unbindService();

        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bindService();
                unbindService();
            }
        }, 1000L);

        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bindService();
            }
        }, 2000L);
    }

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.ii(TAG, "onServiceConnected()");
            DemoService.LocalBinder binder = (DemoService.LocalBinder) service;
            DemoService demoService = binder.getService();
            int randomNumber = demoService.getRandomNumber();
            LogUtils.ii(TAG, "onServiceConnected() randomNumber: " + randomNumber);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.ii(TAG, "onServiceDisconnected()");
        }
    };
}