package com.ubtechinc.aimbothumming;

import android.app.Application;

import com.ubtechinc.aimbothumming.utils.LogUtils;

public class App extends Application {

    private static Application sApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.setDebug(false);
        sApplication = this;
    }

    public static Application getApplication() {
        return sApplication;
    }



}
