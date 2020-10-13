package com.afunx.xfun.common.utils;

import android.view.View;

import java.lang.reflect.Method;

public class Utils {

    public static int getViewHeight(View v) {
        try {
            Method m = v.getClass().getDeclaredMethod("onMeasure", int.class,
                    int.class);
            m.setAccessible(true);
            m.invoke(v, View.MeasureSpec.makeMeasureSpec(
                    ((View) v.getParent()).getMeasuredWidth(),
                    View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED));
        } catch (Exception e) {
        }
        return v.getMeasuredHeight();
    }

}
