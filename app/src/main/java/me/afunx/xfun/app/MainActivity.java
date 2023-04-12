package me.afunx.xfun.app;

import me.afunx.xfun.app.utils.FillUtils;
import me.afunx.xfun.app.utils.Point;
import me.afunx.xfun.demo.DemoMainActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.afunx.xfun.common.base.BaseActivity;
import com.afunx.xfun.common.utils.LogUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_goto_demo).setOnClickListener(this);
        Point[] points = new Point[]{new Point(2,2),new Point(5,1),new Point(11,3),
                new Point(11,8),new Point(5,5),new Point(2,7)};
        Point[] points1 = new Point[]{new Point(2,7),new Point(5,5),new Point(11,8),
                new Point(11,3),new Point(5,1), new Point(2,2)};
        Point[] points2 = new Point[]{new Point(0,0), new Point(2, 0), new Point(3, 3),
        new Point(0, 2)};
        Point[] points3 = new Point[]{new Point(0, 0), new Point(5,0), new Point(5, 1),
                new Point(0, 1)};
        FillUtils.polygonScan(points3);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_goto_demo) {
            Intent intent = new Intent(this, DemoMainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //LogUtils.e(TAG, "onTouchEvent() x: " + event.getX() + ", y: " + event.getY());
        return super.onTouchEvent(event);
    }
}
