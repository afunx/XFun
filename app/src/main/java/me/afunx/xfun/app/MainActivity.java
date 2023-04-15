package me.afunx.xfun.app;

import me.afunx.xfun.app.utils.FunctionKB;
import me.afunx.xfun.app.utils.FunctionNormal;
import me.afunx.xfun.app.utils.LineUtils;
import me.afunx.xfun.demo.DemoMainActivity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.afunx.xfun.common.base.BaseActivity;
import com.afunx.xfun.common.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_goto_demo).setOnClickListener(this);
        Point[] points = new Point[]{new Point(2, 2), new Point(5, 1), new Point(11, 3),
                new Point(11, 8), new Point(5, 5), new Point(2, 7)};
        Point[] points1 = new Point[]{new Point(2, 7), new Point(5, 5), new Point(11, 8),
                new Point(11, 3), new Point(5, 1), new Point(2, 2)};
        Point[] points2 = new Point[]{new Point(0, 0), new Point(2, 0), new Point(3, 3),
                new Point(0, 2)};
        Point[] points3 = new Point[]{new Point(0, 0), new Point(5, 0), new Point(5, 1),
                new Point(0, 1)};
        //FillUtils.polygonScan(points3);
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1, 1);
        //FunctionKB functionKB = LineUtils.parseFunctionKB(p0, p1);
        //LogUtils.e(TAG, functionKB.toString());
        //LogUtils.e(TAG, "" + functionKB.calculateY(10f));
        //FunctionNormal functionNormal = LineUtils.parseFunctionNormal(functionKB.k, p1);
        //LogUtils.e(TAG, functionNormal.toString());
        //LogUtils.e(TAG, "" + functionNormal.calculateY(10f));
        p1.x = 10;
        p1.y = 0;
        List<Point> pointList = new ArrayList<>();
        LineUtils.parseLine(p0, p1, pointList);
        LogUtils.e(TAG, pointList.toString());
        p1.x = 0;
        p1.y = 10;
        pointList.clear();
        LineUtils.parseLine(p0, p1, pointList);
        LogUtils.e(TAG, pointList.toString());
        p1.x = 10;
        p1.y = 2;
        pointList.clear();
        LineUtils.parseLine(p0, p1, pointList);
        LogUtils.e(TAG, pointList.toString());
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
