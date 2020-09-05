package com.ubtechinc.aimbothumming.network.monitor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ubtechinc.aimbothumming.App;
import com.ubtechinc.aimbothumming.utils.LogUtils;

public class NetworkMonitorImpl extends NetworkMonitor {

    private static final String TAG = "NetworkMonitorImpl";

    private volatile boolean networkAvailable = false;

    // TODO 仅在调试时候使用
    public void setNetworkAvailable(boolean networkAvailable) {
        LogUtils.ii(TAG, "networkAvailable: " + networkAvailable);
        this.networkAvailable = networkAvailable;
    }

    // TODO 使用真实的网络请求
    @Override
    protected boolean checkNetworkAvailable() {
        Context context = App.getApplication();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo!=null && networkInfo.isConnected();
    }
}
