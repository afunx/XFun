package com.afunx.xfun.common.utils;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "XFun";

    private LogUtils() {
        throw new IllegalStateException("It can't be instantiated!");
    }

    private static boolean sDebug = false;

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static void v(String tag, String msg) {
        if (sDebug) {
            Log.v(TAG, tag + "::" + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (sDebug) {
            Log.d(TAG, tag + "::" + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (sDebug) {
            Log.i(TAG, tag + "::" + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (sDebug) {
            Log.w(TAG, tag + "::" + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (sDebug) {
            Log.e(TAG, tag + "::" + msg);
        }
    }

    public static void vv(String tag, String msg) {
        Log.v(TAG, tag + "::" + msg);
    }

    public static void dd(String tag, String msg) {
        Log.d(TAG, tag + "::" + msg);
    }

    public static void ii(String tag, String msg) {
        Log.i(TAG, tag + "::" + msg);
    }

    public static void ww(String tag, String msg) {
        Log.w(TAG, tag + "::" + msg);
    }

    public static void ee(String tag, String msg) {
        Log.e(TAG, tag + "::" + msg);
    }
}
