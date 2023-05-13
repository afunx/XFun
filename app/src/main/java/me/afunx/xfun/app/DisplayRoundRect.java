package me.afunx.xfun.app;

import static me.afunx.xfun.app.DisplayParticle.DEBUG_BLACK_HOLE_VISIBLE;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.MetricsFUtils;

public class DisplayRoundRect {

    private final RectF mBigLeftRectF;
    private final RectF mBigRightRectF;
    private final float mBigRadius;
    private final RectF mSmallLeftRectF;
    private final RectF mSmallRightRectF;
    private final float mSmallRadius;

    static final float BIG_WIDTH_DP = 229.95f;
    static final float BIG_HEIGHT_DP = 242.5f;
    static final float BIG_LEFT_MARGIN_DP = 151f;
    static final float BIG_TOP_MARGIN_DP = 174f;
    static final float BIG_BETWEEN_MARGIN_DP = 198.05f;
    static final float BIG_RADIUS_DP = 83.29f;

    private static final float THICKNESS_DP = 20f;
    private static final float SMALL_RADIUS_DP = 66f;

    public DisplayRoundRect() {
        final Context context = MainApplication.getAppContext();

        // 大
        final float bigWidth = MetricsFUtils.dp2px(context, BIG_WIDTH_DP);
        final float bigHeight = MetricsFUtils.dp2px(context, BIG_HEIGHT_DP);
        float left = MetricsFUtils.dp2px(context, BIG_LEFT_MARGIN_DP);
        float top = MetricsFUtils.dp2px(context, BIG_TOP_MARGIN_DP);
        float right = left + bigWidth;
        float bottom = top + bigHeight;
        mBigLeftRectF = new RectF(left, top, right, bottom);
        left += MetricsFUtils.dp2px(context, BIG_BETWEEN_MARGIN_DP) + bigWidth;
        right += MetricsFUtils.dp2px(context, BIG_BETWEEN_MARGIN_DP) + bigWidth;
        mBigRightRectF = new RectF(left, top, right, bottom);
        mBigRadius = MetricsFUtils.dp2px(context, BIG_RADIUS_DP);

        // 小
        final float smallDiff = MetricsFUtils.dp2px(context, THICKNESS_DP);
        left = mBigLeftRectF.left + smallDiff;
        top = mBigLeftRectF.top + smallDiff;
        right = mBigLeftRectF.right - smallDiff;
        bottom = mBigLeftRectF.bottom - smallDiff;
        mSmallLeftRectF = new RectF(left, top, right, bottom);
        left = mBigRightRectF.left + smallDiff;
        top = mBigRightRectF.top + smallDiff;
        right = mBigRightRectF.right - smallDiff;
        bottom = mBigRightRectF.bottom - smallDiff;
        mSmallRightRectF = new RectF(left, top, right, bottom);

        mSmallRadius = MetricsFUtils.dp2px(context, SMALL_RADIUS_DP);
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.drawRoundRect(mBigLeftRectF, mBigRadius, mBigRadius, paint);
        canvas.drawRoundRect(mBigRightRectF, mBigRadius, mBigRadius, paint);
        if (DEBUG_BLACK_HOLE_VISIBLE) {
            // 正在绘制黑洞，不必绘制small圆角矩形
            return;
        }
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(mSmallLeftRectF, mSmallRadius, mSmallRadius, paint);
        canvas.drawRoundRect(mSmallRightRectF, mSmallRadius, mSmallRadius, paint);
    }

}
