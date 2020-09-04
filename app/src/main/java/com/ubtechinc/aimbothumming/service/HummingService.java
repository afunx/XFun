package com.ubtechinc.aimbothumming.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.ubtechinc.aimbothumming.biz.HummingFrameRouter;
import com.ubtechinc.aimbothumming.biz.LocationSnapshot;
import com.ubtechinc.aimbothumming.biz.impl.HummingFrameRouterImpl;
import com.ubtechinc.aimbothumming.biz.thread.HummingRecorderThread;
import com.ubtechinc.aimbothumming.biz.thread.HummingUploadThread;
import com.ubtechinc.aimbothumming.utils.LogUtils;

import java.util.concurrent.atomic.AtomicInteger;


public class HummingService extends Service {

    private static final String TAG = "HummingService";


    private static final String TYPE = "type";
    public static final int TYPE_HUMMING = 0;
    public static final int TYPE_ALARMING = 1;
    private static final int TYPE_DEFAULT = -1;

    private static final String ENABLED = "enabled";
    private static final boolean ENABLED_DEFAULT = false;

    private LocationSnapshot mLocationSnapshot = LocationSnapshot.get();
    private HummingRecorderThread mHummingRecorder = HummingRecorderThread.get();
    private HummingFrameRouter mHummingFrameRouter = HummingFrameRouterImpl.get();
    private HummingUploadThread mHummingUploadThread;


    private static final int DETECT_TYPE_MASK = 0x03;
    // 0:不报, 1:蜂鸣音, 2报警音, 3蜂鸣音和报警音
    private AtomicInteger mDetectType = new AtomicInteger(0);

    public static void startService(@NonNull Context context, int type, boolean enabled) {
        Intent intent = new Intent(context, HummingService.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(ENABLED, enabled);
        context.startService(intent);
    }


    private void startBackgroundThread() {
        LogUtils.ii(TAG, "startBackgroundThread()");
        mHummingFrameRouter.start();
        mHummingUploadThread = new HummingUploadThread(getApplicationContext());
        mHummingUploadThread.start();
    }

    private void stopBackgroundThread() {
        LogUtils.ii(TAG, "stopBackgroundThread()");
        mHummingFrameRouter.stop();
        if (mHummingUploadThread != null) {
            mHummingUploadThread.stop();
            mHummingUploadThread = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.ii(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }
        int type = intent.getIntExtra(TYPE, TYPE_DEFAULT);
        boolean enabled = intent.getBooleanExtra(ENABLED, ENABLED_DEFAULT);
        int prevDetectType = mDetectType.get();
        int curDetectType;
        int delta = 1 << type;
        if (enabled) {
            curDetectType = prevDetectType | delta;
        } else {
            curDetectType = prevDetectType & ((~delta)&DETECT_TYPE_MASK);
        }
        mDetectType.set(curDetectType);
        mHummingRecorder.setDetectType(mDetectType.get());
        LogUtils.ii(TAG, "onStartCommand() type: " + type + ", enabled: " + enabled + ", prevDetectType: " + prevDetectType + ", curDetectType: " + curDetectType);
        if (curDetectType == 0) {
            LogUtils.ii(TAG, "onStartCommand() stopSelf");
            stopSelf();
            return START_NOT_STICKY;
        }

        if (prevDetectType == 0) {
            mLocationSnapshot.start();
            mHummingRecorder.start();
            startBackgroundThread();
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtils.ii(TAG, "onLowMemory()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtils.ii(TAG, "onTrimMemory() level: " + level);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.ii(TAG, "onDestroy()");
        mLocationSnapshot.stop();
        mHummingRecorder.stop();
        stopBackgroundThread();
    }
}
