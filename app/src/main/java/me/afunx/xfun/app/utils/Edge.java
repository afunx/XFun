package me.afunx.xfun.app.utils;

public class Edge {
    // 下端点x坐标
    public float x;
    // 斜率k的倒数
    public float dx;
    // 上端点y坐标
    public int yMax;
    // 下一条边
    public Edge next;

    @Override
    public String toString() {
        return "EdgeTable{" +
                "x=" + x +
                ", dx=" + dx +
                ", yMax=" + yMax +
                '}';
    }
}
