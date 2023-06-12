package me.afunx.xfun.app.util;

// AE的px与Android的px转换关系：
// x,y为AE上的坐标，单位：像素
// f(x),f(y)为Android上的坐标，单位：像素
// f(x) = (x-(1920-1154)/2)*1920/1154 = (x-383) * 1920 / 1154
// f(y) = (y-(1200-722)/2)*1200/722 = (y-239) * 1200 / 722
public class Ae2AndroidDimenUtil {

    public static float ae2androidX(float ae) {
        return (ae - 383) * 1920 / 1154;
    }

    public static float ae2androidY(float ae) {
        return (ae - 239) * 1200 / 722;
    }

}
