package me.afunx.xfun.app;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    private static Context sApplication;

    public static Context getAppContext() {
        if (sApplication == null) {
            throw new NullPointerException("sApplication is null");
        }
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = getApplicationContext();
    }
}
