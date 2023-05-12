package me.afunx.xfun.app;

import static me.afunx.xfun.app.DisplayParticle.BLACK_HOLE_VISIBLE;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

public class DisplayRoundRect {

    private final RectF mBigLeftRectF;
    private final RectF mBigRightRectF;
    private final float mBigRadius;
    private final RectF mSmallLeftRectF;
    private final RectF mSmallRightRectF;
    private final float mSmallRadius;

    public DisplayRoundRect() {
        // 大
        final float bigWidth = 459.9f;
        final float bigHeight = 485f;
        float left = 302;
        float top = 348;
        float right = left + bigWidth;
        float bottom = top + bigHeight;
        mBigLeftRectF = new RectF(left, top, right, bottom);
        left += 396.1f + bigWidth;
        right += 396.1f + bigWidth;
        mBigRightRectF = new RectF(left, top, right, bottom);
        mBigRadius = 166.58f;

        // 小
        final float smallDiff = 40f;
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

        mSmallRadius = 132f;
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
        if (BLACK_HOLE_VISIBLE) {
            // 正在绘制黑洞，不必绘制small圆角矩形
            return;
        }
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(mSmallLeftRectF, mSmallRadius, mSmallRadius, paint);
        canvas.drawRoundRect(mSmallRightRectF, mSmallRadius, mSmallRadius, paint);
    }

}
