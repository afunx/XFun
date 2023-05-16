package me.afunx.xfun.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
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
    private final float mThickness;
    private final RectF mLeftRectF;
    private final RectF mRightRectF;

    static final float BIG_WIDTH_DP = 229.95f;
    static final float BIG_HEIGHT_DP = 242.5f;
    static final float BIG_LEFT_MARGIN_DP = 151f;
    static final float BIG_TOP_MARGIN_DP = 174f;
    static final float BIG_BETWEEN_MARGIN_DP = 198.05f;
    static final float BIG_RADIUS_DP = 83.29f;

    static final float THICKNESS_DP = 20f;
    // 由于机器性能原因，故只能采用最原始的绘制覆盖方式实现波浪的遮罩。
    // 该尺寸为覆盖时，小的矩形四个角缺的四个等腰直角三角形边长。
    static final float SMALL_TRIANGLE_SIDE_DP = 20f;
    static final float SMALL_RADIUS_DP = 66f;
    private Picture mPictureRing = null;


    public DisplayRoundRect() {
        final Context context = MainApplication.getAppContext();

        mThickness = MetricsFUtils.dp2px(context, THICKNESS_DP);

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

        mLeftRectF = new RectF(mBigLeftRectF.left + mThickness / 2, mBigLeftRectF.top + mThickness / 2, mBigLeftRectF.right - mThickness / 2, mBigLeftRectF.bottom - mThickness / 2);
        mRightRectF = new RectF(mBigRightRectF.left + mThickness / 2, mBigRightRectF.top + mThickness / 2, mBigRightRectF.right - mThickness / 2, mBigRightRectF.bottom - mThickness / 2);
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDrawOld(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        if (mPictureRing == null) {
            mPictureRing = new Picture();
            Canvas pictureCanvas = mPictureRing.beginRecording(canvas.getWidth(), canvas.getHeight());
            pictureCanvas.drawRoundRect(mBigLeftRectF, mBigRadius, mBigRadius, paint);
            pictureCanvas.drawRoundRect(mBigRightRectF, mBigRadius, mBigRadius, paint);

            paint.setColor(Color.BLACK);
            pictureCanvas.drawRoundRect(mSmallLeftRectF, mSmallRadius, mSmallRadius, paint);
            pictureCanvas.drawRoundRect(mSmallRightRectF, mSmallRadius, mSmallRadius, paint);
            mPictureRing.endRecording();
        }
        canvas.drawPicture(mPictureRing);
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        Paint.Style style = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mThickness);
        canvas.drawRoundRect(mLeftRectF, mBigRadius, mBigRadius, paint);
        canvas.drawRoundRect(mRightRectF, mBigRadius, mBigRadius, paint);
        paint.setStyle(style);
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDrawSmall(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.drawRoundRect(mSmallLeftRectF, mSmallRadius, mSmallRadius, paint);
        canvas.drawRoundRect(mSmallRightRectF, mSmallRadius, mSmallRadius, paint);
    }
}
