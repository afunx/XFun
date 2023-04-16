package me.afunx.xfun.app.utils;

import android.graphics.Point;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

public class FillUtils {

    private static final boolean DEBUG = false;

    private static final String TAG = "FillUtils";

    public static void polygonScan(@NonNull Point[] points) {
        int num = points.length;
        if (num < 3) {
            throw new IllegalArgumentException("num: " + num + " < 3");
        }
        // 计算最高点和最低点y坐标
        int minY = points[0].y;
        int maxY = points[0].y;
        for (int i = 1; i < num; i++) {
            minY = Math.min(minY, points[i].y);
            maxY = Math.max(maxY, points[i].y);
        }
        int height = maxY - minY + 1;
        // 初始化边表和活动边表
        Edge[] ET = new Edge[height + 1];
        for (int i = 0; i < ET.length; i++) {
            ET[i] = new Edge();
            ET[i].next = null;
        }
        Edge AET = new Edge();
        long time = System.currentTimeMillis();
        // 建立并扫描边表ET
        buildET(points, minY, maxY, ET);
        time = System.currentTimeMillis() - time;
        LogUtils.e(TAG, "polygonScan() consume: " + time + " ms");
        printET(ET);
        // 建立并更新活性边表AET
        buildAET(points, minY, maxY, ET, AET);
    }

    private static void buildAET(@NonNull Point[] points, int minY, int maxY, @NonNull Edge[] ET, @NonNull Edge AET) {
        LogUtils.e(TAG, "buildAET() minY: " + minY + ", maxY: " + maxY);
        Edge p;
        for (int i = minY; i <= maxY; i++) {
            if (DEBUG) {
                LogUtils.e(TAG, "buildAET() y(i): " + i);
            }
            if (DEBUG) {
                printAET(AET);
            }
            // 计算新的交点x，更新AET
            p = AET.next;
            while (p != null) {
                p.x += p.dx;
                p = p.next;
            }
            if (DEBUG) {
                printAET(AET);
            }
            // AET排序
            // 断表排序，不再开辟空间（按照x升序排序）
            Edge tq = AET;
            p = AET.next;
            tq.next = null;
            while (p != null) {
                while (tq.next != null && p.x >= tq.next.x) {
                    tq = tq.next;
                }
                Edge s = p.next;
                p.next = tq.next;
                tq.next = p;
                p = s;
                tq = AET;
            }
            if (DEBUG) {
                printAET(AET);
            }
            // （改进算法）先从AET表中删除ymax==i的结点
            Edge q = AET;
            p = q.next;
            // 非最后一个i，先删除ymax==i
            if (i != maxY) {
                while (p != null) {
                    if (p.yMax == i) {
                        q.next = p.next;
                        if (DEBUG) {
                            LogUtils.e(TAG, "buildAET() 111 delete: " + p);
                        }
                        // delete p
                    } else {
                        q = q.next;
                    }
                    p = q.next;
                }
            }

            // 将NET中的新点加入AET,并用插入法按X值递增排序
            p = ET[i].next;
            q = AET;
            while (p != null) {
                while (q.next != null && p.x >= q.next.x) {
                    q = q.next;
                }
                Edge s = p.next;
                p.next = q.next;
                q.next = p;
                p = s;
                q = AET;
            }
            // 配对填充颜色
            p = AET.next;
            if (p != null) {
                if (DEBUG) {
                    LogUtils.e(TAG, "buildAET() p.next: " + p.next);
                }
            }
            while (p != null && p.next != null) {
                for (float j = p.x; j <= p.next.x; j++) {
                    if (DEBUG) {
                        LogUtils.e(TAG, "buildAET() x: " + j + ", y: " + i);
                    }
                }
                p = p.next.next;
            }

            // 最后一个i，后删除ymax==i
            if (i == maxY) {
                while (p != null) {
                    if (p.yMax == i) {
                        q.next = p.next;
                        if (DEBUG) {
                            LogUtils.e(TAG, "buildAET() 222 delete: " + p);
                        }
                        // delete p
                    } else {
                        q = q.next;
                    }
                    p = q.next;
                }
            }
        }
    }

    private static void printAET(Edge AET) {
        Edge et = AET.next;
        while (et != null) {
            LogUtils.e(TAG, "printAET() et: " + et);
            et = et.next;
        }
    }

    private static void printET(@NonNull Edge[] ET) {
        for (int i = 0; i < ET.length; i++) {
            Edge et = ET[i].next;
            while (et != null) {
                LogUtils.e(TAG, "printET() i: " + i + ", et: " + et);
                et = et.next;
            }
        }
    }

    private static void buildET(@NonNull Point[] points, int minY, int maxY, @NonNull Edge[] ET) {
        for (int i = minY; i <= maxY; i++) {
            for (int j = 0; j < points.length; j++) {
                if (points[j].y == i) {
                    int j1 = (j - 1 + points.length) % points.length;
                    // 和前面一点形成的线段
                    if (points[j1].y > points[j].y) {
                        Edge edge = new Edge();
                        edge.x = points[j].x;
                        edge.yMax = points[j1].y;
                        edge.dx = 1.0f * (points[j1].x - points[j].x) / (points[j1].y - points[j].y);
                        edge.next = ET[i].next;
                        ET[i].next = edge;
                    }
                    int j2 = (j + 1 + points.length) % points.length;
                    // 和后面一点形成的线段
                    if (points[j2].y > points[j].y) {
                        Edge edge = new Edge();
                        edge.x = points[j].x;
                        edge.yMax = points[j2].y;
                        edge.dx = 1.0f * (points[j2].x - points[j].x) / (points[j2].y - points[j].y);
                        edge.next = ET[i].next;
                        ET[i].next = edge;
                    }
                }
            }
        }
    }

}
