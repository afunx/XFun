package com.ubtechinc.aimbothumming;

import android.app.Application;

import com.ubtechinc.aimbothumming.utils.LogUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.setDebug(false);
    }

}
