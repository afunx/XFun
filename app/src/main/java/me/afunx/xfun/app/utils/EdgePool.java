package me.afunx.xfun.app.utils;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;

import com.afunx.xfun.common.utils.LogUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class EdgePool {

    private static final String TAG = "EdgePool";

    private static final boolean DEBUG = false;

    private static final int MAX_POOL_SIZE = 1024;

    private final AtomicInteger count = new AtomicInteger(0);

    private final AtomicInteger created = new AtomicInteger(0);

    private final Pools.SynchronizedPool<Edge> pool;

    private EdgePool() {
        pool = new Pools.SynchronizedPool<>(MAX_POOL_SIZE);
    }

    private static class Singleton {
        private static final EdgePool instance = new EdgePool();
    }

    public static EdgePool get() {
        return Singleton.instance;
    }

    public Edge acquire() {
        try {
            Edge edge = pool.acquire();
            if (edge == null) {
                edge = new Edge();
                if (DEBUG) {
                    LogUtils.i(TAG, "acquire() edge: " + edge.hashCode() + ", created: " + created.incrementAndGet());
                }
            }
            if (DEBUG) {
                LogUtils.i(TAG, "acquire() edge: " + edge.hashCode() + ", count: " + count.incrementAndGet());
            }
            return edge;
        } catch (Exception e) {
            e.printStackTrace();
            return new Edge();
        }
    }

    public void release(@NonNull Edge edge) {
        if (DEBUG) {
            LogUtils.i(TAG, "release() edge: " + edge.hashCode() + ", count: " + count.decrementAndGet());
        }
        try {
            edge.next = null;
            pool.release(edge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
