package me.afunx.xfun.app.utils;

import android.graphics.Point;
import android.graphics.PointF;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

public class LineUtils {

    private static final boolean DEBUG = true;

    private static final String TAG = "LineUtils";

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
        FunctionKB functionKB = new FunctionKB(k, b);
        LogUtils.i(TAG, "parseFunctionKB() p0: " + p0 + ", p1: " + p1 + ", functionKB: " + functionKB);
        return functionKB;
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
        FunctionKB functionKB = new FunctionKB(k, b);
        LogUtils.i(TAG, "parseFunctionKB() p0: " + p0 + ", p1: " + p1 + ", functionKB: " + functionKB);
        return functionKB;
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
        FunctionNormal functionNormal = new FunctionNormal(k, p0.x, p0.y);
        LogUtils.i(TAG, "parseFunctionNormal() k: " + k + ", p0: " + p0 + ", functionNormal: " + functionNormal);
        return functionNormal;
    }

    /**
     * 解析法线方程FunctionNormal( y-y0 = (-1/k) * (x-x0) )
     *
     * @param k  斜率k
     * @param p0 点p0
     * @return 法线方程FunctionNormal(y - y0 = ( - 1 / k) * (x-x0))
     */
    @NonNull
    public static FunctionNormal parseFunctionNormal(float k, Point p0) {
        if (k == 0) {
            throw new IllegalArgumentException("k = 0");
        }
        FunctionNormal functionNormal = new FunctionNormal(k, p0.x, p0.y);
        LogUtils.i(TAG, "parseFunctionNormal() k: " + k + ", p0: " + p0 + ", functionNormal: " + functionNormal);
        return functionNormal;
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
        if (expandRadius < 0) {
            throw new IllegalArgumentException("expandRadius: " + expandRadius + " < 0");
        }
        LogUtils.d(TAG, "parsePolygon() point0: " + point0 + ", point1: " + point1
                + ", shrink: " + shrink + ", expandRadius: " + expandRadius);
        final PointPool pointPool = PointPool.get();
        final Point[] points = new Point[4];
        points[0] = pointPool.acquire();
        points[1] = pointPool.acquire();
        points[2] = pointPool.acquire();
        points[3] = pointPool.acquire();
        // 直线与y轴平行
        if (point0.x == point1.x) {
            points[0].x = point0.x - expandRadius;
            points[0].y = point0.y;
            points[1].x = point0.x + expandRadius;
            points[1].y = point0.y;
            points[2].x = point1.x + expandRadius;
            points[2].y = point1.y;
            points[3].x = point1.x - expandRadius;
            points[3].y = point1.y;
            LogUtils.i(TAG, "parsePolygon() points: " + Arrays.deepToString(points));
            return points;
        }
        // 直线与x轴平行
        if (point0.y == point1.y) {
            points[0].x = point0.x;
            points[0].y = point0.y - expandRadius;
            points[1].x = point0.x;
            points[1].y = point0.y + expandRadius;
            points[2].x = point1.x;
            points[2].y = point1.y + expandRadius;
            points[3].x = point1.x;
            points[3].y = point1.y - expandRadius;
            LogUtils.i(TAG, "parsePolygon() points: " + Arrays.deepToString(points));
            return points;
        }
        // y = kx + b
        FunctionKB functionKB = parseFunctionKB(point0, point1);
        if (DEBUG) {
            LogUtils.i(TAG, "parsePolygon() " + functionKB);
        }
        final float k = functionKB.k;
        final float b = functionKB.b;
        // y - y0 = (-1/k) * (x - x0)
        FunctionNormal functionNormal = parseFunctionNormal(k, point0);
        if (DEBUG) {
            LogUtils.i(TAG, "parsePolygon() " + functionNormal);
        }
        // (x-x0)^2 + (y-y0)^2 = r^2, y-y0 = (-1/k)*(x-x0)
        // 令m=x-x0,n=y-y0，则m^2+n^2=r^2, n=(-1/k)*m
        // 得: m = r / (1+1/k^2)^(1/2) (还有一个绝对值相等的负数解)
        float m = (float) (expandRadius / Math.sqrt((1 + 1 / (k * k))));
        float n = -m / k;
        if (DEBUG) {
            LogUtils.i(TAG, "parsePolygon() k: " + k + ", m: " + m + ", n: " + n);
        }
        // |k| <= 1 时，结果按照x收缩；否则，结果按照y收缩
        int x, y;
        if (Math.abs(k) <= 1.0f) {
            int x1 = (int) Math.floor(m + point0.x);
            int x2 = (int) Math.ceil(m + point0.x);
            if (Math.abs(x1 - point0.x) <= Math.abs(x2 - point0.x)) {
                x = shrink ? x1 : x2;
            } else {
                x = shrink ? x2 : x1;
            }
            y = Math.round(functionNormal.calculateY(x));
            if (DEBUG) {
                LogUtils.i(TAG, "parsePolygon() x1: " + x1 + ", x2: " + x2 + ", x: " + x + ", y: " + y);
            }
        } else {
            int y1 = (int) Math.floor(n + point0.y);
            int y2 = (int) Math.ceil(n + point0.y);
            if (Math.abs(y1 - point0.y) <= Math.abs(y2 - point0.y)) {
                y = shrink ? y1 : y2;
            } else {
                y = shrink ? y2 : y1;
            }
            x = Math.round(functionNormal.calculateX(y));
            if (DEBUG) {
                LogUtils.i(TAG, "parsePolygon() y1: " + y1 + ", y2: " + y2 + ", x: " + x + ", y: " + y);
            }
        }
        // 已知点P(x,y)求它经过中垂线的点Q。已知P、Q的中垂线经过point0和point1
        // 联立方程组: y=k*x+b, (y-y0)/(x-x0) = -1/k
        // (k*x+b-y0)/(x-x0)=-1/k，得:
        // x = (y0-b+x0/k)/(1+1/k)
        // (xq, pq)为PQ的中垂线与y=kx+b的交点
        float xq = (point0.y - b + point0.x / k) / (1 + 1 / k);
        float yq = functionKB.calculateX(xq);
        if (DEBUG) {
            LogUtils.i(TAG, "parsePolygon() xq: " + xq + ", yq: " + yq);
        }
        // 向量: x - xq
        int dx = Math.round(x - xq);
        int dy = Math.round(y - yq);
        // 向量: point1 - point0
        int x01 = point1.x - point0.x;
        int y01 = point1.y - point0.y;
        if (DEBUG) {
            LogUtils.i(TAG, "parsePolygon() dx: " + dx + ", dy: " + dy + ", x01: " + x01 + ", y01: " + y01);
        }
        points[0].x = point0.x + dx;
        points[0].y = point0.y + dy;
        points[1].x = points[0].x + x01;
        points[1].y = points[0].y + y01;
        points[2].x = points[1].x - 2 * dx;
        points[2].y = points[1].y - 2 * dy;
        points[3].x = points[2].x - x01;
        points[3].y = points[2].y - y01;
        LogUtils.i(TAG, "parsePolygon() points: " + Arrays.deepToString(points));
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
        final PointPool pointPool = PointPool.get();
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
                Point point = pointPool.acquire();
                point.x = point0.x;
                point.y = y;
                pointList.add(point);
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
                Point point = pointPool.acquire();
                point.x = x;
                point.y = point0.y;
                pointList.add(point);
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
                Point point = pointPool.acquire();
                point.x = x;
                point.y = Math.round(functionKB.calculateY(x));
                pointList.add(point);
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
                Point point = pointPool.acquire();
                point.x = Math.round(functionKB.calculateX(y));
                point.y = y;
                pointList.add(point);
            }
        }
    }
}
