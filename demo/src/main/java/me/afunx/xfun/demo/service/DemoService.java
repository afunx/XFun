package me.afunx.xfun.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.afunx.xfun.common.utils.LogUtils;

import java.util.Random;

public class DemoService extends Service {

    private static final String TAG = "DemoService";

    private final IBinder binder = new LocalBinder();

    private final Random mGenerator = new Random();

    public DemoService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.ii(TAG, "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.ii(TAG, "onBind()");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtils.ii(TAG, "onRebind()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.ii(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.ii(TAG, "onUnbind()");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.ii(TAG, "onDestroy()");
    }

    public class LocalBinder extends Binder {
        public DemoService getService() {
            return DemoService.this;
        }
    }

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}