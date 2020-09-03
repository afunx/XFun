package com.ubtechinc.aimbothumming.network.monitor;

import com.ubtechinc.aimbothumming.utils.LogUtils;

public abstract class NetworkMonitor {

    private static final String TAG = "NetworkMonitor";

    private static final long CHECK_NETWORK_INTERVAL_MILLI = 100L;
    private volatile boolean isAvailable = true;

    /**
     * 设网络状态为不可用
     */
    public synchronized void setDisconnected() {
        LogUtils.ii(TAG, "setDisconnected()");
        isAvailable = false;
    }

    /**
     * 等待网络可用
     */
    public synchronized void waitAvailable() {
        if (!isAvailable) {
            LogUtils.dd(TAG, "waitAvailable() E");
            startNetworkCheckThread();
            try {
                wait();
            } catch (InterruptedException ignore) {
            }
            LogUtils.dd(TAG, "waitAvailable() X");
        }
    }

    private void notifyNetworkAvailable() {
        LogUtils.dd(TAG, "notifyNetworkAvailable()");
        synchronized (this) {
            this.notify();
        }
    }

    private void startNetworkCheckThread() {
        new Thread("NetworkCheckThread") {
            @Override
            public void run() {
                LogUtils.dd(TAG, "startNetworkCheckThread()");
                while (!checkNetworkAvailable()) {
                    try {
                        Thread.sleep(CHECK_NETWORK_INTERVAL_MILLI);
                    } catch (InterruptedException ignore) {
                        notifyNetworkAvailable();
                    }
                }
                notifyNetworkAvailable();
            }
        }.start();
    }

    /**
     * 检查网络是否可用
     *
     * @return  网络是否可用
     */
    protected abstract boolean checkNetworkAvailable();
}
