package com.ubtechinc.aimbothumming.biz.impl;

import androidx.annotation.Nullable;

import com.ubtechinc.aimbothumming.biz.HummingCache;
import com.ubtechinc.aimbothumming.biz.HummingFrame;
import com.ubtechinc.aimbothumming.biz.HummingFramePool;
import com.ubtechinc.aimbothumming.biz.HummingFrameRouter;
import com.ubtechinc.aimbothumming.biz.mock.Location;
import com.ubtechinc.aimbothumming.utils.LogUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HummingFrameRouterImpl implements HummingFrameRouter {

    private static final String TAG = "HummingFrameRouterImpl";

    private final AtomicBoolean mIsStopped = new AtomicBoolean(true);
    private HummingFramePool mFramePool = HummingFramePool.get();

    private HummingFrame[] mHummingUploadFrames = new HummingFrame[UPLOAD_HUMMING_FRAME_COUNT];
    private final HummingCache mHummingCache = new HummingCacheImpl();

    private final AtomicInteger mCount = new AtomicInteger(0);

    private HummingFrameRouterImpl() {
        mHummingCache.setFrameSize(MAX_CACHE_SIZE);
    }

    private static class Singleton {
        private static HummingFrameRouter instance = new HummingFrameRouterImpl();
    }

    public static HummingFrameRouter get() {
        return Singleton.instance;
    }

    @Override
    public synchronized void start() {
        LogUtils.ii(TAG, "start()");
        mIsStopped.set(false);
        mHummingCache.clearInterrupted();
        mCount.set(0);
    }

    @Override
    public synchronized void stop() {
        LogUtils.ii(TAG, "stop()");
        mIsStopped.set(true);
        mHummingCache.interrupt();
    }

    @Override
    public synchronized boolean isStopped() {
        return mIsStopped.get();
    }

    @Override
    public void addOneFrame(byte[] oneFrameBytes, Location location, int detectType, String mapName) {
        long timestamp = System.currentTimeMillis();
        // 添加新Frame
        HummingFrame hummingFrame = mFramePool.acquire();
        hummingFrame.setTimestamp(timestamp);
        if (location == null) {
            hummingFrame.setLocationFlag(0);
            hummingFrame.setLocationX(Float.MIN_VALUE);
            hummingFrame.setLocationY(Float.MIN_VALUE);
        } else {
            hummingFrame.setLocationFlag(1);
            hummingFrame.setLocationX(location.getPosition().getX());
            hummingFrame.setLocationY(location.getPosition().getY());
        }
        hummingFrame.setDetectType(detectType);
        hummingFrame.setMapName(mapName);
        LogUtils.d(TAG, "addOneFrame() count: " + mCount.incrementAndGet() + ", hummingFrame: " + hummingFrame);
        mHummingCache.putOneFrameAtTail(hummingFrame);
    }

    @Nullable
    @Override
    public HummingFrame[] getUploadHummingFrames() {
        boolean interrupted = mHummingCache.waitFramesFromHead(mHummingUploadFrames);
        return interrupted ? null : mHummingUploadFrames;
    }

    @Override
    public void doUploadSuc() {
        LogUtils.ii(TAG, "doUploadSuc()");
        mHummingCache.releaseFrames(mHummingUploadFrames);
    }

    @Override
    public void doUploadFail() {
        LogUtils.ii(TAG, "doUploadFail()");
        mHummingCache.returnFrames(mHummingUploadFrames);
    }
}
