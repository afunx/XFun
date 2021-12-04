package me.afunx.xfun.demo.touchevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.afunx.xfun.common.utils.LogUtils;

public class TouchDemoView extends androidx.appcompat.widget.AppCompatTextView implements View.OnTouchListener {

    private static final String TAG = "TouchDemoView";

    public TouchDemoView(Context context) {
        super(context);
        init();
    }

    public TouchDemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.dd(TAG, "dispatchTouchEvent() event: " + event + " start");
        boolean result = super.dispatchTouchEvent(event);
        LogUtils.ii(TAG, "dispatchTouchEvent() event: " + event + ", result: " + result);
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
    public boolean onTouch(View v, MotionEvent event) {
        LogUtils.dd(TAG, "onTouch() v: " + v + ", event: " + event +" start");
        boolean result = false;
        LogUtils.ii(TAG, "onTouch() v: " + v + ", event: " + event + ", result: " + result);
        return result;
    }
}
