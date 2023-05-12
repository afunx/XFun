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

    public DisplayRoundRect() {
        final Context context = MainApplication.getAppContext();

        // 大
        final float bigWidth = MetricsFUtils.dp2px(context, 229.95f);
        final float bigHeight = MetricsFUtils.dp2px(context, 242.5f);
        float left = MetricsFUtils.dp2px(context, 151f);
        float top = MetricsFUtils.dp2px(context, 174f);
        float right = left + bigWidth;
        float bottom = top + bigHeight;
        mBigLeftRectF = new RectF(left, top, right, bottom);
        left += MetricsFUtils.dp2px(context, 198.05f) + bigWidth;
        right += MetricsFUtils.dp2px(context, 198.05f) + bigWidth;
        mBigRightRectF = new RectF(left, top, right, bottom);
        mBigRadius = MetricsFUtils.dp2px(context, 83.29f);

        // 小
        final float smallDiff = MetricsFUtils.dp2px(context, 20f);
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

        mSmallRadius = MetricsFUtils.dp2px(context, 66f);
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
