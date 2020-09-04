package com.ubtechinc.aimbothumming.network.monitor;

import com.ubtechinc.aimbothumming.utils.LogUtils;

public abstract class NetworkMonitor {

    private static final String TAG = "NetworkMonitor";

    private static final long CHECK_NETWORK_INTERVAL_MILLI = 100L;
    private volatile boolean isAvailable = true;
    private volatile boolean isInterrupted = false;

    /**
     * 查询当前网络是否可用
     *
     * @return  当前网络是否可用
     */
    public synchronized boolean isNetworkAvailable() {
        return isAvailable;
    }

    /**
     * 查询是否被中断
     *
     * @return  是否被中断
     */
    public synchronized boolean isInterrupted() {
        return isInterrupted;
    }

    /**
     * 清楚中断标志位
     */
    public synchronized void clearInterrupted() {
        isInterrupted = false;
    }

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

    public synchronized void interrupt() {
        LogUtils.dd(TAG, "interrupt()");
        isInterrupted = true;
        this.notify();
    }

    private synchronized void notifyNetworkAvailable() {
        LogUtils.dd(TAG, "notifyNetworkAvailable()");
        this.notify();
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
