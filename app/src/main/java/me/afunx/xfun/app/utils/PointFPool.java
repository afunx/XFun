package me.afunx.xfun.app.utils;

import android.graphics.PointF;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;

public class PointFPool {

    private static final int MAX_POOL_SIZE = 1024;

    private final Pools.SynchronizedPool<PointF> pool;

    private PointFPool() {
        pool = new Pools.SynchronizedPool<>(MAX_POOL_SIZE);
    }

    private static class Singleton {
        private static final PointFPool instance = new PointFPool();
    }

    public static PointFPool get() {
        return Singleton.instance;
    }

    public PointF acquire() {
        try {
            PointF point = pool.acquire();
            if (point == null) {
                point = new PointF();
            }
            return point;
        } catch (Exception e) {
            e.printStackTrace();
            return new PointF();
        }
    }

    public void release(@NonNull PointF point) {
        try {
            pool.release(point);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
