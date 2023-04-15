package me.afunx.xfun.app.utils;

import android.graphics.Point;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;

public class PointPool {

    private static final int MAX_POOL_SIZE = 1024;

    private final Pools.SynchronizedPool<Point> pool;

    private PointPool() {
        pool = new Pools.SynchronizedPool<>(MAX_POOL_SIZE);
    }

    private static class Singleton {
        private static final PointPool instance = new PointPool();
    }

    public static PointPool get() {
        return Singleton.instance;
    }

    public Point acquire() {
        try {
            Point point = pool.acquire();
            if (point == null) {
                point = new Point();
            }
            return point;
        } catch (Exception e) {
            e.printStackTrace();
            return new Point();
        }
    }

    public void release(@NonNull Point point) {
        try {
            pool.release(point);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
