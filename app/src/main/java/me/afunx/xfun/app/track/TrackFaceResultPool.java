package me.afunx.xfun.app.track;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;

class TrackFaceResultPool {
    private static final int MAX_POOL_SIZE = 16;

    private final Pools.SimplePool<TrackFaceResult> pool = new Pools.SimplePool<>(MAX_POOL_SIZE);

    TrackFaceResult acquire() {
        try {
            TrackFaceResult trackFaceResult = pool.acquire();
            if (trackFaceResult == null) {
                trackFaceResult = new TrackFaceResult();
            }
            return trackFaceResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new TrackFaceResult();
        }
    }

    void release(@NonNull TrackFaceResult trackFaceResult) {
        try {
            pool.release(trackFaceResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
