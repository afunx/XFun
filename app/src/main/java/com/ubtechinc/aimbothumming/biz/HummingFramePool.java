package com.ubtechinc.aimbothumming.biz;

import androidx.core.util.Pools;

import com.ubtechinc.aimbothumming.utils.LogUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class HummingFramePool {

    private static final String TAG = "HummingFramePool";

    // 125ms/帧，8*10*60=4800为10分钟的容量
    private static final int MAX_POOL_SIZE = 8 * 10 * 60;
    // 用来DEBUG创建了多少个HummingFrame
    private final AtomicInteger mCount = new AtomicInteger(0);

    private Pools.SynchronizedPool<HummingFrame> pool;

    private HummingFramePool() {
        pool = new Pools.SynchronizedPool<>(MAX_POOL_SIZE);
    }

    private static class Singleton {
        private static HummingFramePool instance = new HummingFramePool();
    }

    public static HummingFramePool get() {
        return Singleton.instance;
    }

    public HummingFrame acquire() {
        try {
            HummingFrame hummingFrame = pool.acquire();
            if (hummingFrame == null) {
                hummingFrame = new HummingFrame();
                LogUtils.dd(TAG, "acquire() new " + mCount.addAndGet(1));
            }
            return hummingFrame;
        } catch (Exception e) {
            LogUtils.ee(TAG, "acquire() e: " + e.getMessage());
            return new HummingFrame();
        }
    }

    public void release(HummingFrame hummingFrame) {
        try {
            pool.release(hummingFrame);
        } catch (Exception e) {
            LogUtils.ee(TAG, "release() e: " + e.getMessage());
        }
    }
}
