package com.ubtechinc.aimbothumming.biz.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ubtechinc.aimbothumming.biz.HummingCache;
import com.ubtechinc.aimbothumming.biz.HummingFrame;
import com.ubtechinc.aimbothumming.biz.HummingFrameFilesPath;
import com.ubtechinc.aimbothumming.biz.HummingFramePool;
import com.ubtechinc.aimbothumming.biz.HummingFrameRouter;
import com.ubtechinc.aimbothumming.biz.mock.Location;
import com.ubtechinc.aimbothumming.network.monitor.NetworkMonitor;
import com.ubtechinc.aimbothumming.network.monitor.NetworkMonitorImpl;
import com.ubtechinc.aimbothumming.utils.LogUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HummingFrameRouterImpl implements HummingFrameRouter {

    private static final String TAG = "HummingFrameRouterImpl";

    private final AtomicBoolean mIsStopped = new AtomicBoolean(true);
    private HummingFramePool mFramePool = HummingFramePool.get();

    private NetworkMonitor mNetworkMonitor = new NetworkMonitorImpl();

    private HummingFrame[] mHummingUploadFrames = new HummingFrame[UPLOAD_HUMMING_FRAME_COUNT];
    private HummingFrame[] mHummingStorageFrames = new HummingFrame[MIN_STORAGE_SIZE];
    private final HummingCache mHummingCache = new HummingCacheImpl();

    private final AtomicInteger mCount = new AtomicInteger(0);

    private HummingFrameRouterImpl() {
        mHummingCache.setCacheSize(MAX_CACHE_SIZE);
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
        mNetworkMonitor.clearInterrupted();
    }

    @Override
    public synchronized void stop() {
        LogUtils.ii(TAG, "stop()");
        mIsStopped.set(true);
        mHummingCache.interrupt();
        mNetworkMonitor.interrupt();
        // 剩余
        HummingFrame[] remainedFrames = mHummingCache.getAllFrames();
        // 保存到本地
        saveHummingFrames(remainedFrames,false);
        mHummingCache.releaseFrames(remainedFrames);
    }

    @Override
    public synchronized boolean isStopped() {
        return mIsStopped.get();
    }

    @Override
    public synchronized void addOneFrame(byte[] oneFrameBytes, Location location, int detectType, String mapName) {
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

        /**
         * 如果HummingUploadThread运行在waitNetworkAvailable()，则两个地方pollFramesFromHead()互斥。
         * 如果HummingUploadThread运行在getUploadHummingFrames()，则尚未到达mHummingStorageFrames.length时，
         * getUploadHummingFrames()方法就会返回。
         */
        // 若未被中断，检查文件已达到被存储大小时，同步保存文件
        if (!mIsStopped.get() && mHummingCache.pollFramesFromHead(mHummingStorageFrames)) {
            saveHummingFrames(mHummingStorageFrames, true);
        }

        LogUtils.d(TAG, "addOneFrame() count: " + mCount.incrementAndGet() + ", hummingFrame: " + hummingFrame);
        mHummingCache.putOneFrameAtTail(hummingFrame);
    }

    @Override
    public void waitNetworkAvailable() {
        LogUtils.dd(TAG, "waitNetworkAvailable() E");
        mNetworkMonitor.waitAvailable();
        boolean interrupted = mNetworkMonitor.isInterrupted();
        if (!interrupted && mHummingCache.pollFramesFromHead(mHummingStorageFrames)) {
            saveHummingFrames(mHummingStorageFrames, true);
        }
        LogUtils.dd(TAG, "waitNetworkAvailable() X");
    }

    @Nullable
    @Override
    public HummingFrameFilesPath getFrameFilePath() {
        // TODO 待开发
        return null;
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
        // TODO 临时调试注释掉了
        mNetworkMonitor.setDisconnected();
    }

    private void saveHummingFrames(@NonNull HummingFrame[] hummingFrames, boolean sync) {
        if (hummingFrames.length == 0) {
            return;
        }
        LogUtils.ii(TAG, "saveHummingFrames() sync: " + sync);
        SaveHummingFramesRunnable saveHummingFramesRunnable = new SaveHummingFramesRunnable(hummingFrames);
        if (sync) {
            // 同步
            saveHummingFramesRunnable.run();
        } else {
            new Thread(saveHummingFramesRunnable,"SaveHummingFramesThread").start();
        }
        mHummingCache.releaseFrames(hummingFrames);
    }

    private static class SaveHummingFramesRunnable implements Runnable {

        private final HummingFrame[] hummingFrames;

        private SaveHummingFramesRunnable(HummingFrame[] hummingFrames) {
            // TODO 深拷贝
            this.hummingFrames = hummingFrames;
        }

        @Override
        public void run() {
            LogUtils.ii(TAG, "SaveHummingFramesRunnable save humming frames, size: " + hummingFrames.length);
        }
    }
}
