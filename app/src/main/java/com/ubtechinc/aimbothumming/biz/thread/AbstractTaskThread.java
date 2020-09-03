package com.ubtechinc.aimbothumming.biz.thread;

import com.ubtechinc.aimbothumming.utils.LogUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractTaskThread {

    private static final boolean DEBUG = true;

    private final AtomicBoolean mIsStarted;
    private final AtomicBoolean mIsStopped;
    private final String mTaskThreadName;
    private Runnable mTaskRunnable;
    private Thread mTaskThread;

    protected AbstractTaskThread(String taskThreadName) {
        mIsStarted = new AtomicBoolean(false);
        mIsStopped = new AtomicBoolean(true);
        mTaskThreadName = taskThreadName;
    }

    protected void setRunnable(Runnable runnable) {
        mTaskRunnable = runnable;
    }

    public final synchronized void start() {
        if (DEBUG) {
            LogUtils.dd(mTaskThreadName, "start() E");
        }
        // 任务只能开始执行一次，除非已经停止
        if (mIsStarted.get()) {
            if (DEBUG) {
                LogUtils.dd(mTaskThreadName, "start() is started already");
            }
            return;
        }
        mIsStarted.set(true);
        mIsStopped.set(false);

        // 创建任务线程
        mTaskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (DEBUG) {
                    LogUtils.dd(mTaskThreadName, "start() run E");
                }

                if (DEBUG) {
                    LogUtils.dd(mTaskThreadName, "onStart() E");
                }
                onStart();
                if (DEBUG) {
                    LogUtils.dd(mTaskThreadName, "onStart() X");
                }

                while (mIsStarted.get()) {
                    if (mTaskRunnable != null) {
                        mTaskRunnable.run();
                    }
                }
                mIsStopped.set(true);
                synchronized (AbstractTaskThread.this) {
                    AbstractTaskThread.this.notifyAll();
                }

                if (DEBUG) {
                    LogUtils.dd(mTaskThreadName, "start() run X");
                }
            }
        }, mTaskThreadName);


        // 开始执行任务线程
        mTaskThread.start();
        if (DEBUG) {
            LogUtils.dd(mTaskThreadName, "start() X");
        }
    }

    public final synchronized void stop() {
        if (DEBUG) {
            LogUtils.dd(mTaskThreadName, "stop() E");
        }
        // 任务已经停止
        if (mIsStopped.get() || !mIsStarted.get()) {
            if (DEBUG) {
                LogUtils.dd(mTaskThreadName, "stop() is stopped already");
            }
            return;
        }
        mIsStarted.set(false);
        mTaskThread.interrupt();

        stop0();

        // 等待任务结束
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onStop();

        if (DEBUG) {
            LogUtils.dd(mTaskThreadName, "stop() X");
        }
    }

    protected void onStart() {

    }

    protected void onStop() {

    }

    protected void stop0() {

    }
}
