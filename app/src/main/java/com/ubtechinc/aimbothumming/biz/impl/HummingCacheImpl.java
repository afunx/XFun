package com.ubtechinc.aimbothumming.biz.impl;

import androidx.annotation.NonNull;

import com.ubtechinc.aimbothumming.biz.HummingCache;
import com.ubtechinc.aimbothumming.biz.HummingFrame;
import com.ubtechinc.aimbothumming.biz.HummingFramePool;
import com.ubtechinc.aimbothumming.utils.LogUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;

public class HummingCacheImpl implements HummingCache {

    private static final String TAG = "HummingCacheImpl";

    private Deque<HummingFrame> mHummingFrames = new ArrayDeque<>();
    private HummingFramePool mFramePool = HummingFramePool.get();

    private volatile int cacheSize = 0;

    private final AtomicBoolean isInterrupted = new AtomicBoolean(false);

    @Override
    public synchronized void setCacheSize(int size) {
        LogUtils.ii(TAG, "setFrameSize() size: " + size);
        this.cacheSize = size;
    }

    @NonNull
    @Override
    public synchronized HummingFrame[] getAllFrames() {
        int size = mHummingFrames.size();
        HummingFrame[] allFrames = new HummingFrame[size];
        int i = 0;
        while (!mHummingFrames.isEmpty()) {
            allFrames[i++] = mHummingFrames.removeFirst();
        }
        return allFrames;
    }

    @Override
    public synchronized void interrupt() {
        LogUtils.ii(TAG, "interrupt()");
        isInterrupted.set(true);
        notifyAll();
    }

    @Override
    public synchronized void clearInterrupted() {
        LogUtils.ii(TAG, "clearInterrupted()");
        isInterrupted.set(false);
    }

    @Override
    public synchronized void putOneFrameAtTail(@NonNull HummingFrame hummingFrame) {
        // 删除冗余
        while (mHummingFrames.size() >= cacheSize) {
            HummingFrame removedFrame = mHummingFrames.removeFirst();
            LogUtils.dd(TAG, "putFramesAtTail() removeFirst release timestamp: " + hummingFrame.getTimestamp());
            mFramePool.release(removedFrame);
        }
        mHummingFrames.addLast(hummingFrame);
        notifyAll();
    }

    @Override
    public synchronized boolean waitFramesFromHead(@NonNull HummingFrame[] hummingFrames) {
        // 已经中断时候，直接返回null
        if (isInterrupted.get()) {
            LogUtils.ii(TAG, "waitFramesFromHead() gain null directly for interrupted already");
            return true;
        }
        int count = hummingFrames.length;
        int size = mHummingFrames.size();
        while (size < count) {
            LogUtils.d(TAG, "waitFramesFromHead() size: " + size + ", count: " + count);
            try {
                wait();
            } catch (InterruptedException ignore) {
            }
            // 已经停止时候，直接返回null
            if (isInterrupted.get()) {
                LogUtils.ii(TAG, "waitFramesFromHead() count: " + count + ", gain null for interrupted already");
                return true;
            }
            size = mHummingFrames.size();
        }

        for (int i = 0; i < count; i++) {
            hummingFrames[i] = mHummingFrames.removeFirst();
            if (hummingFrames[i] == null) {
                LogUtils.ee(TAG, "getTempCacheFramesFromHead() removeFirst i: " + i + ", count: " + count + " is null");
            }
            LogUtils.i(TAG, "getTempCacheFramesFromHead() removeFirst i: " + i + ", count: " + count + ", timestamp: " + hummingFrames[i].getTimestamp());
        }

        return isInterrupted.get();
    }

    @Override
    public synchronized boolean pollFramesFromHead(@NonNull HummingFrame[] hummingFrames) {
        if (mHummingFrames.size() < hummingFrames.length) {
            return false;
        }
        for (int i = 0; i < hummingFrames.length; i++) {
            hummingFrames[i] = mHummingFrames.removeFirst();
        }
        return true;
    }

    private void returnCacheFrames(HummingFrame[] hummingFrames, int offset, int count) {
        LogUtils.ii(TAG, "returnCacheFrames() offset: " + offset + ", count: " + count);
        for (int i = 0; i < count; i++) {
            if (mHummingFrames.size() < cacheSize) {
                LogUtils.i(TAG, "returnCacheFrames() addFirst i: " + i + ", offset: " + offset + ", count: " + count + ", timestamp: " + hummingFrames[i + offset].getTimestamp());
                mHummingFrames.addFirst(hummingFrames[i + offset]);
                hummingFrames[i + offset] = null;
            } else {
                LogUtils.i(TAG, "returnCacheFrames() release i: " + i + ", offset: " + offset + ", count: " + count + ", timestamp: " + hummingFrames[i + offset].getTimestamp());
                mFramePool.release(hummingFrames[i + offset]);
                hummingFrames[i + offset] = null;
            }
        }
    }

    private void releaseCacheFrames(HummingFrame[] hummingFrames, int offset, int count) {
        LogUtils.ii(TAG, "releaseCacheFrames() offset: " + offset + ", count: " + count);
        if (hummingFrames != null) {
            for (int i = 0; i < count; i++) {
                if (hummingFrames[i + offset] == null) {
                    continue;
                }
                LogUtils.i(TAG, "releaseCacheFrames() release i: " + i + ", offset: " + offset + ", count: " + count + ", timestamp: " + hummingFrames[i + offset].getTimestamp());
                mFramePool.release(hummingFrames[i + offset]);
                hummingFrames[i + offset] = null;
            }
        }
    }

    @Override
    public synchronized void returnFrames(@NonNull HummingFrame[] hummingFrames) {
        returnCacheFrames(hummingFrames,0, hummingFrames.length);
    }

    @Override
    public synchronized void releaseFrames(@NonNull HummingFrame[] hummingFrames) {
        releaseCacheFrames(hummingFrames, 0, hummingFrames.length);
    }
}
