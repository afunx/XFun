package com.ubtechinc.aimbothumming.utils;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "AimbotHumming";

    private static boolean sDebug = false;

    private LogUtils() {
        throw new IllegalStateException("it can't be instantiated!");
    }

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

    public static void v(String tag, String message) {
        if (sDebug) {
            Log.v(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
        }
    }

    public static void vv(String tag, String message) {
        Log.v(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
    }

    public static void d(String tag, String message) {
        if (sDebug) {
            Log.d(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
        }
    }

    public static void dd(String tag, String message) {
        Log.d(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
    }

    public static void i(String tag, String message) {
        if (sDebug) {
            Log.i(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
        }
    }

    public static void ii(String tag, String message) {
        Log.i(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
    }

    public static void w(String tag, String message) {
        if (sDebug) {
            Log.w(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
        }
    }

    public static void ww(String tag, String message) {
        Log.w(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
    }

    public static void e(String tag, String message) {
        if (sDebug) {
            Log.e(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
        }
    }

    public static void ee(String tag, String message) {
        Log.e(TAG, tag + "::" + Thread.currentThread().getName() + ": " + message);
    }
}
