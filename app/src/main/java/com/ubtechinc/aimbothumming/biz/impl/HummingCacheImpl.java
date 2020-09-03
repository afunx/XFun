package com.ubtechinc.aimbothumming.biz.impl;

import com.ubtechinc.aimbothumming.biz.HummingCache;
import com.ubtechinc.aimbothumming.biz.HummingFrame;
import com.ubtechinc.aimbothumming.biz.HummingFramePool;
import com.ubtechinc.aimbothumming.biz.mock.Location;
import com.ubtechinc.aimbothumming.utils.LogUtils;
//import com.ubtrobot.navigation.Location;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;

public class HummingCacheImpl implements HummingCache {

    private static final String TAG = "HummingCacheImpl";

    private Deque<HummingFrame> mHummingFrames = new ArrayDeque<>();
    private HummingFramePool mFramePool = HummingFramePool.get();
    private HummingFrame[] mTempCacheFrames = null;

    private final AtomicBoolean mIsStopped = new AtomicBoolean(true);

    // 125ms/帧，8*10*60=4800为10分钟的容量
    private static final int MAX_SIZE = 8 * 10 * 60;

    private volatile int mCount = 0;

    private static class Singleton {
        private static HummingCacheImpl instance = new HummingCacheImpl();
    }

    public static HummingCacheImpl get() {
        return Singleton.instance;
    }

    @Override
    public synchronized void start() {
        LogUtils.ii(TAG, "start()");
        mIsStopped.set(false);
        mCount = 0;
    }

    @Override
    public synchronized void stop() {
        LogUtils.ii(TAG, "stop()");
        mIsStopped.set(true);
        // 不要忘记释放
        releaseHummingFrames();
        releaseTempCacheFrames();
        notifyAll();
    }

    @Override
    public synchronized boolean isStopped() {
        return mIsStopped.get();
    }

    @Override
    public synchronized void putOneFrameAtTail(byte[] oneFrameBytes, Location location, int detectType, String mapName) {
        long timestamp = System.currentTimeMillis();
        LogUtils.dd(TAG, "putFramesAtTail() timestamp: " + timestamp + ", count: " + (++mCount));
        // 删除冗余
        while (mHummingFrames.size() >= MAX_SIZE) {
            HummingFrame hummingFrame = mHummingFrames.removeFirst();
            LogUtils.dd(TAG, "putFramesAtTail() removeFirst release timestamp: " + hummingFrame.getTimestamp());
            mFramePool.release(hummingFrame);
        }
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
        LogUtils.d(TAG, "putOneFrameAtTail() hummingFrame: " + hummingFrame);
        byte[] hummingFrameBytes = hummingFrame.getBytes();
        System.arraycopy(oneFrameBytes, 0, hummingFrameBytes, 0, oneFrameBytes.length);
        mHummingFrames.addLast(hummingFrame);
        notifyAll();
    }

    @Override
    public synchronized HummingFrame[] getTempCacheFramesFromHead(int count) {
        if (mTempCacheFrames == null) {
            mTempCacheFrames = new HummingFrame[count];
        }
        // 已经停止时候，直接返回null
        if (mIsStopped.get()) {
            LogUtils.ii(TAG, "getTempCacheFramesFromHead() count: " + count + ", gain null directly for stopped already");
            return null;
        }
        int size = mHummingFrames.size();
        while (size < count) {
            try {
                wait();
            } catch (InterruptedException ignore) {
            }
            size = mHummingFrames.size();
            // 已经停止时候，直接返回null
            if (mIsStopped.get()) {
                LogUtils.ii(TAG, "getTempCacheFramesFromHead() count: " + count + ", gain null for stopped already");
                return null;
            }
        }
        for (int i = 0; i < count; i++) {
            mTempCacheFrames[i] = mHummingFrames.removeFirst();
            if (mTempCacheFrames[i] == null) {
                LogUtils.ee(TAG, "getTempCacheFramesFromHead() removeFirst i: " + i + ", count: " + count + " is null");
            }
            LogUtils.i(TAG, "getTempCacheFramesFromHead() removeFirst i: " + i + ", count: " + count + ", timestamp: " + mTempCacheFrames[i].getTimestamp());
        }
        LogUtils.i(TAG, "getTempCacheFramesFromHead() tempCacheFrames.length: " + mTempCacheFrames.length);
        return mTempCacheFrames;
    }

    private void returnTempCacheFrames(int offset, int count) {
        LogUtils.ii(TAG, "returnTempCacheFrames() offset: " + offset + ", count: " + count);
        for (int i = 0; i < count; i++) {
            if (mHummingFrames.size() < MAX_SIZE) {
                LogUtils.i(TAG, "returnTempCacheFrames() addFirst i: " + i + ", offset: " + offset + ", count: " + count + ", timestamp: " + mTempCacheFrames[i + offset].getTimestamp());
                mHummingFrames.addFirst(mTempCacheFrames[i + offset]);
                mTempCacheFrames[i + offset] = null;
            } else {
                LogUtils.i(TAG, "returnTempCacheFrames() release i: " + i + ", offset: " + offset + ", count: " + count + ", timestamp: " + mTempCacheFrames[i + offset].getTimestamp());
                mFramePool.release(mTempCacheFrames[i + offset]);
                mTempCacheFrames[i + offset] = null;
            }
        }
    }

    private void releaseTempCacheFrames(int offset, int count) {
        LogUtils.ii(TAG, "releaseTempCacheFrames() offset: " + offset + ", count: " + count);
        if (mTempCacheFrames != null) {
            for (int i = 0; i < count; i++) {
                if (mTempCacheFrames[i + offset] == null) {
                    continue;
                }
                LogUtils.i(TAG, "releaseTempCacheFrames() release i: " + i + ", offset: " + offset + ", count: " + count + ", timestamp: " + mTempCacheFrames[i + offset].getTimestamp());
                mFramePool.release(mTempCacheFrames[i + offset]);
                mTempCacheFrames[i + offset] = null;
            }
        }
        mTempCacheFrames = null;
    }

    @Override
    public synchronized void returnTempCacheFrames() {
        if (mTempCacheFrames != null) {
            returnTempCacheFrames(0, mTempCacheFrames.length);
        }
    }

    @Override
    public synchronized void releaseTempCacheFrames() {
        if (mTempCacheFrames != null) {
            releaseTempCacheFrames(0, mTempCacheFrames.length);
        }
    }

    private void releaseHummingFrames() {
        while (mHummingFrames.size() > 0) {
            HummingFrame hummingFrame = mHummingFrames.removeFirst();
            LogUtils.i(TAG, "releaseHummingFrames() release timestamp: " + hummingFrame.getTimestamp());
            mFramePool.release(hummingFrame);
        }
        LogUtils.ii(TAG, "releaseHummingFrames() hummingFrames.size(): " + mHummingFrames.size());
    }

}