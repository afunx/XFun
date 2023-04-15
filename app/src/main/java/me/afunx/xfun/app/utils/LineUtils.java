package me.afunx.xfun.app.utils;

import android.graphics.Point;
import android.graphics.PointF;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class LineUtils {
    /**
     * 解析直线方程FunctionKB(y = kx + b)
     *
     * @param p0 点p0
     * @param p1 点p1
     * @return 直线方程FunctionKB(y = kx + b)
     */
    @NonNull
    public static FunctionKB parseFunctionKB(@NonNull PointF p0, @NonNull PointF p1) {
        if (p0.x == p1.x) {
            throw new IllegalArgumentException("p0.x: " + p0.x + "==" + "p1.x: " + p1.x);
        }
        // k = (y0-y1)/(x0-x1)
        final float k = (p0.y - p1.y) / (p0.x - p1.x);
        // b = y0 - k*x0
        final float b = p0.y - k * p0.x;
        return new FunctionKB(k, b);
    }

    /**
     * 解析直线方程FunctionKB(y = kx + b)
     *
     * @param p0 点p0
     * @param p1 点p1
     * @return 直线方程FunctionKB(y = kx + b)
     */
    @NonNull
    public static FunctionKB parseFunctionKB(@NonNull Point p0, @NonNull Point p1) {
        if (p0.x == p1.x) {
            throw new IllegalArgumentException("p0.x: " + p0.x + "==" + "p1.x: " + p1.x);
        }
        // k = (y0-y1)/(x0-x1)
        final float k = 1.0f * (p0.y - p1.y) / (p0.x - p1.x);
        // b = y0 - k*x0
        final float b = p0.y - k * p0.x;
        return new FunctionKB(k, b);
    }

    /**
     * 解析法线方程FunctionNormal( y-y0 = (-1/k) * (x-x0) )
     *
     * @param k  斜率k
     * @param p0 点p0
     * @return 法线方程FunctionNormal(y - y0 = ( - 1 / k) * (x-x0))
     */
    @NonNull
    public static FunctionNormal parseFunctionNormal(float k, PointF p0) {
        if (k == 0) {
            throw new IllegalArgumentException("k = 0");
        }
        return new FunctionNormal(k, p0.x, p0.y);
    }

    /**
     * 解析起始位置点、终止位置点线段膨胀之后的四边形
     *
     * @param point0       起始位置点
     * @param point1       终止位置点
     * @param shrink       是否收缩（收缩指位置点无法满足像素点需求时，沿法线向p0、p1靠拢）
     * @param expandRadius 膨胀半径
     * @return 四维Point数组表示膨胀之后的四边形
     */
    public static Point[] parsePolygon(@NonNull Point point0, @NonNull Point point1, boolean shrink, int expandRadius) {
        final Point[] points = new Point[4];
        if (point0.x == point1.x) {
        }
        return points;
    }

    /**
     * 解析线段经过的PointList
     *
     * @param point0    起始位置点
     * @param point1    终止位置点
     * @param pointList 存储结果的线段列表
     */
    public static void parseLine(@NonNull Point point0, @NonNull Point point1, @NonNull List<Point> pointList) {
        if (!pointList.isEmpty()) {
            throw new IllegalStateException("pointList should be empty");
        }
        // 直线与y轴平行
        if (point0.x == point1.x) {
            final int minY;
            final int maxY;
            if (point0.y > point1.y) {
                minY = point1.y;
                maxY = point0.y;
            } else {
                minY = point0.y;
                maxY = point1.y;
            }
            for (int y = minY; y <= maxY; y++) {
                // TODO afunx 对象池
                pointList.add(new Point(point0.x, y));
            }
            return;
        }
        // 直线与x轴平行
        if (point0.y == point1.y) {
            final int minX;
            final int maxX;
            if (point0.x > point1.x) {
                minX = point1.x;
                maxX = point0.x;
            } else {
                minX = point0.x;
                maxX = point1.x;
            }
            for (int x = minX; x <= maxX; x++) {
                // TODO afunx 对象池
                pointList.add(new Point(x, point0.y));
            }
            return;
        }
        // 求解斜率方程
        final FunctionKB functionKB = parseFunctionKB(point0, point1);
        // |k| <= 1 时，结果按照x递增；否则，结果按照y递增
        if (Math.abs(functionKB.k) <= 1.0f) {
            final int minX;
            final int maxX;
            if (point0.x > point1.x) {
                minX = point1.x;
                maxX = point0.x;
            } else {
                minX = point0.x;
                maxX = point1.x;
            }
            for (int x = minX; x <= maxX; x++) {
                // TODO afunx 对象池
                pointList.add(new Point(x, Math.round(functionKB.calculateY(x))));
            }
        } else {
            final int minY;
            final int maxY;
            if (point0.y > point1.y) {
                minY = point1.y;
                maxY = point0.y;
            } else {
                minY = point0.y;
                maxY = point1.y;
            }
            for (int y = minY; y <= maxY; y++) {
                // TODO afunx 对象池
                pointList.add(new Point(Math.round(functionKB.calculateX(y)), y));
            }
        }
    }
}
