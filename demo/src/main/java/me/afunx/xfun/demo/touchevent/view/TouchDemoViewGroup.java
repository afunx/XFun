package me.afunx.xfun.demo.touchevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.afunx.xfun.common.utils.LogUtils;

public class TouchDemoViewGroup extends RelativeLayout implements View.OnTouchListener {

    private static final String TAG = "TouchDemoViewGroup";

    public TouchDemoViewGroup(Context context) {
        super(context);
        init();
    }

    public TouchDemoViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchDemoViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TouchDemoViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.ee(TAG, "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        LogUtils.dd(TAG, "dispatchTouchEvent() event: " + event + " start");
        boolean result = super.dispatchTouchEvent(event);
        LogUtils.ii(TAG, "dispatchTouchEvent() event: " + event + ", result: " + result);
        LogUtils.ee(TAG, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.dd(TAG, "onTouchEvent() event: " + event + " start");
        boolean result = super.onTouchEvent(event);
        //result = true;
        LogUtils.ii(TAG, "onTouchEvent() event: " + event + ", result: " + result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        LogUtils.dd(TAG, "onInterceptTouchEvent() event: " + event + " start");
        boolean result = super.onInterceptTouchEvent(event);
        //result = true;
        LogUtils.ee(TAG, "onInterceptTouchEvent() event: " + event + ", result: " + result);
        return result;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        LogUtils.dd(TAG, "onTouch() v: " + v + ", event: " + event +" start");
        boolean result = true;
        LogUtils.ii(TAG, "onTouch() v: " + v + ", event: " + event + ", result: " + result);
        return result;
    }
}
