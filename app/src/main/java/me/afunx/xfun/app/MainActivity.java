package me.afunx.xfun.app;

import me.afunx.xfun.app.utils.FillUtils;
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
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_goto_demo).setOnClickListener(this);

        //testAllProgress();
        testParsePolygon();
    }

    private void testPolygonScan() {
        Point[] points = new Point[]{new Point(2, 2), new Point(5, 1), new Point(11, 3),
                new Point(11, 8), new Point(5, 5), new Point(2, 7)};
        Point[] points1 = new Point[]{new Point(2, 7), new Point(5, 5), new Point(11, 8),
                new Point(11, 3), new Point(5, 1), new Point(2, 2)};
        Point[] points2 = new Point[]{new Point(0, 0), new Point(2, 0), new Point(3, 3),
                new Point(0, 2)};
        Point[] points3 = new Point[]{new Point(6, 0), new Point(8, 1), new Point(2, 11),
                new Point(0, 10)};
        List<Point> pointList = new ArrayList<>();
        FillUtils.polygonScan(points, pointList);
        LogUtils.e(TAG, "testPolygonScan() " + pointList);
    }

    private void testParseFunction() {
        PointF p0 = new PointF(0, 0);
        PointF p1 = new PointF(1, 1);
        FunctionKB functionKB = LineUtils.parseFunctionKB(p0, p1);
        LogUtils.e(TAG, functionKB.toString());
        LogUtils.e(TAG, "" + functionKB.calculateY(10f));
        FunctionNormal functionNormal = LineUtils.parseFunctionNormal(functionKB.k, p1);
        LogUtils.e(TAG, functionNormal.toString());
        LogUtils.e(TAG, "" + functionNormal.calculateY(10f));
    }

    private void testParseLine() {
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1, 1);
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

    private void testParsePolygon() {
        final int expandRadius = 6;
        final boolean shrink = true;
        Point point0 = new Point(177,102);
        Point point1 = new Point(259,113);
        Point[] points = LineUtils.parsePolygon(point0, point1, shrink, expandRadius);
        LogUtils.e(TAG, Arrays.deepToString(points));
//        point0.x = 4;
//        point0.y = -2;
//        point1.x = -1;
//        point1.y = -2;
//        points = LineUtils.parsePolygon(point0, point1, shrink, expandRadius);
//        LogUtils.e(TAG, Arrays.deepToString(points));
//        point0.x = 0;
//        point0.y = 0;
//        point1.x = 2;
//        point1.y = 1;
//        points = LineUtils.parsePolygon(point0, point1, shrink, expandRadius);
//        LogUtils.e(TAG, Arrays.deepToString(points));
    }

    private void testAllProgress() {
        final int expandRadius = 6;
        final boolean shrink = true;
        final List<Point> pointList = new ArrayList<>();
        Point point0 = new Point(0,0);
        Point point1 = new Point(2,1);
        Point[] points = LineUtils.parsePolygon(point0, point1, shrink, expandRadius);
        LogUtils.e(TAG, "testAllProgress() points: " + Arrays.deepToString(points));
        FillUtils.polygonScan(points, pointList);
        LogUtils.e(TAG, "testAllProgress() pointList: " + pointList);
        pointList.clear();
        FillUtils.polygonScan(points, pointList);
        LogUtils.e(TAG, "testAllProgress() pointList: " + pointList);
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
