package me.afunx.xfun.app;

import android.content.Context;

import androidx.annotation.NonNull;
public class BatteryMetrics {

    public static void init(@NonNull Context context) {
        initDensity(context);
        initBigWidth();
        initBigHeight();
        initBigLeftMargin();
        initBigTopMargin();
        initBigBetweenMargin();
        initBigRadius();
        initThickness();
        initSmallTriangleSide();
        initSmallRadius();
        initWaveHeight();
        initWaveWidth();
        initWaveSpeedPerSecond();
    }

    private static float sDensity = 0;
    private static void initDensity(Context context) {
        sDensity = context.getResources().getDisplayMetrics().density;
    }

    private static final float BIG_WIDTH_DP = 229.95f;
    private static float sBigWidth = 0;
    private static void initBigWidth() {
        sBigWidth = dp2px(BIG_WIDTH_DP);
    }
    public static float bigWidth() {
        if (sBigWidth == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sBigWidth;
    }

    private static final float BIG_HEIGHT_DP = 242.5f;
    private static float sBigHeight = 0;
    private static void initBigHeight() {
        sBigHeight = dp2px(BIG_HEIGHT_DP);
    }
    public static float bigHeight() {
        if (sBigHeight == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sBigHeight;
    }

    private static final float BIG_LEFT_MARGIN_DP = 151f;
    private static float sBigLeftMargin = 0;
    private static void initBigLeftMargin() {
        sBigLeftMargin = dp2px(BIG_LEFT_MARGIN_DP);
    }
    public static float bigLeftMargin() {
        if (sBigLeftMargin == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sBigLeftMargin;
    }

    private static final float BIG_TOP_MARGIN_DP = 174f;
    private static float sBigTopMargin = 0;
    private static void initBigTopMargin() {
        sBigTopMargin = dp2px(BIG_TOP_MARGIN_DP);
    }
    public static float bigTopMargin() {
        if (sBigTopMargin == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sBigTopMargin;
    }

    private static final float BIG_BETWEEN_MARGIN_DP = 198.05f;
    private static float sBigBetweenMargin = 0;
    private static void initBigBetweenMargin() {
        sBigBetweenMargin = dp2px(BIG_BETWEEN_MARGIN_DP);
    }
    public static float bigBetweenMargin() {
        if (sBigBetweenMargin == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sBigBetweenMargin;
    }

    private static final float BIG_RADIUS_DP = 83.29f;
    private static float sBigRadius = 0;
    private static void initBigRadius() {
        sBigRadius = dp2px(BIG_RADIUS_DP);
    }
    public static float bigRadius() {
        if (sBigRadius == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sBigRadius;
    }

    private static final float THICKNESS_DP = 20f;
    private static float sThickness = 0;
    private static void initThickness() {
        sThickness = dp2px(THICKNESS_DP);
    }
    public static float thickness() {
        if (sThickness == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sThickness;
    }

    // 由于机器性能原因，故只能采用最原始的绘制覆盖方式实现波浪的遮罩。
    // 该尺寸为覆盖时，小的矩形四个角缺的四个等腰直角三角形边长。
    private static final float SMALL_TRIANGLE_SIDE_DP = 20f;
    private static float sSmallTriangleSide = 0;
    private static void initSmallTriangleSide() {
        sSmallTriangleSide = dp2px(SMALL_TRIANGLE_SIDE_DP);
    }
    public static float smallTriangleSide() {
        if (sSmallTriangleSide == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sSmallTriangleSide;
    }

    private static final float SMALL_RADIUS_DP = 66f;
    private static float sSmallRadius = 0;
    private static void initSmallRadius() {
        sSmallRadius = dp2px(SMALL_RADIUS_DP);
    }
    public static float smallRadius() {
        if (sSmallRadius == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sSmallRadius;
    }

    private static final float WAVE_HEIGHT_DP = 18.75f;
    private static float sWaveHeight = 0;
    private static void initWaveHeight() {
        sWaveHeight = dp2px(WAVE_HEIGHT_DP);
    }
    public static float waveHeight() {
        if (sWaveHeight == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sWaveHeight;
    }

    private static final float WAVE_WIDTH_DP = 146.25f;
    private static float sWaveWidth = 0;
    private static void initWaveWidth() {
        sWaveWidth = dp2px(WAVE_WIDTH_DP);
    }
    public static float waveWidth() {
        if (sWaveWidth == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sWaveWidth;
    }

    private static final float WAVE_SPEED_DP_PER_SECOND = 135f;
    private static float sWaveSpeedPerSecond = 0;
    private static void initWaveSpeedPerSecond() {
        sWaveSpeedPerSecond = dp2px(WAVE_SPEED_DP_PER_SECOND);
    }
    public static float waveSpeedPerSecond() {
        if (sWaveSpeedPerSecond == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return sWaveSpeedPerSecond;
    }

    public static float dp2px(float dp) {
        if (sDensity == 0) {
            throw new IllegalStateException("Please call init(Context) first");
        }
        return dp * sDensity;
    }
}
