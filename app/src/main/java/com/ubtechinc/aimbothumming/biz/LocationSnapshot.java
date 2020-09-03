package com.ubtechinc.aimbothumming.biz;

import android.os.Looper;

import com.ubtechinc.aimbothumming.biz.mock.Location;
import com.ubtechinc.aimbothumming.utils.LogUtils;
//import com.ubtrobot.Robot;
//import com.ubtrobot.navigation.Location;
//import com.ubtrobot.navigation.NavigationManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Location快照存取：每隔10s更新一次位置坐标
 */
public class LocationSnapshot {

    private static final String TAG = "LocationSnapshot";

    // Location快照存取更新时间
    private static final long LOCATION_SNAPSHOT_INTERVAL = 10 * 1000;

    // 上次存取快照的Location
    private volatile Location mLastSnapshot;

//    private final NavigationManager mNavigationManager;

    private LocationThread mLocationThread;

    // TODO 获取当前机器人正在使用的地图
    // 当前使用地图
    private String mMapName = "fakeMapName";

    private static class Singleton {
        private static LocationSnapshot instance = new LocationSnapshot();
    }

    private LocationSnapshot() {
//        mNavigationManager = Robot.globalContext().getSystemService(NavigationManager.SERVICE);
    }

    public static LocationSnapshot get() {
        return Singleton.instance;
    }

    public void start() {
        LogUtils.ii(TAG, "start()");
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("it should be called on main thread");
        }

        if (mLocationThread == null) {
            mLocationThread = new LocationThread(LOCATION_SNAPSHOT_INTERVAL);
            mLocationThread.start();
        }
    }

    public void stop() {
        LogUtils.ii(TAG, "stop()");
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("it should be called on main thread");
        }

        if (mLocationThread != null) {
            mLocationThread.cancel();
            mLocationThread = null;
        }
    }

    public Location getCurrentLocation() {
        return mLastSnapshot;
    }

    public String getCurrentMapName() {
        return mMapName;
    }

    private class LocationThread extends Thread {

        private final AtomicBoolean isRunning = new AtomicBoolean(false);
        private final long interval;

        LocationThread(long interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            while (isRunning.get()) {
                try {
//                    mLastSnapshot = mNavigationManager.getCurrentLocation();
                } catch (Exception e) {
                    LogUtils.ee(TAG, "LocationThread run() e: " + e);
                    mLastSnapshot = null;
                }

                try {
                    sleep(interval);
                } catch (InterruptedException ignore) {
                }
            }
            mLastSnapshot = null;
        }

        @Override
        public void start() {
            LogUtils.ii(TAG, "LocationThread start()");
            isRunning.set(true);
            try {
//                mLastSnapshot = mNavigationManager.getCurrentLocation();
            } catch (Exception e) {
                LogUtils.ee(TAG, "LocationThread start() e: " + e);
            }
            super.start();
        }

        void cancel() {
            LogUtils.ii(TAG, "LocationThread cancel()");
            isRunning.set(false);
            interrupt();
        }
    }
}
