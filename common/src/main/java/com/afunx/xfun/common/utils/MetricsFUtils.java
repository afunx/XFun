package com.afunx.xfun.common.utils;

import android.content.Context;

public class MetricsFUtils {

    private MetricsFUtils() {
        throw new IllegalStateException("it can't be instantiated");
    }

    public static float dp2px(Context context, float dp) { ;
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float sp2px(Context context, float sp) {
        return sp * context.getResources().getDisplayMetrics().scaledDensity;
    }
}
