package com.afunx.xfun.common.utils;

import android.content.Context;

public class MetricsUtils {

    private MetricsUtils() {
        throw new IllegalStateException("it can't be instantiated");
    }

    public static int dp2px(Context context, float dp) { ;
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int sp2px(Context context, float sp) {
        return (int) (sp * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }
}
