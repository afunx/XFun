package me.afunx.xfun.app.util;

public class TimeDiffUtil {

    private static long sLastTimestamp = 0;

    public static void start() {
        if (sLastTimestamp != 0) {
            throw new IllegalStateException("sLastTimestamp: " + sLastTimestamp + " should be 0");
        }
        sLastTimestamp = System.currentTimeMillis();
    }

    public static long end() {
        if (sLastTimestamp == 0) {
            throw new IllegalStateException("sLastTimestamp: " + sLastTimestamp + " shouldn't be 0");
        }
        long consume = System.currentTimeMillis() - sLastTimestamp;
        sLastTimestamp = 0;
        return consume;
    }

}
