package com.ubtechinc.aimbothumming.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import androidx.annotation.NonNull;

public class FileUtils {

    /**
     * 获取内部存储情况
     *
     * @param appContext    Application Context
     * @return              ActivityManager.MemoryInfo
     */
    public static ActivityManager.MemoryInfo getMemoryInfo(@NonNull Context appContext) {
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        return outInfo;
    }

    /**
     * 获取外部存储可用大小（单位：字节）
     *
     * @return              外部存储可用大小（单位：字节）
     */
    public static long getExternalStorageAvailableBytes() {
        final StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
    }
}
