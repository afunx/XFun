package me.afunx.xfun.app;

import static me.afunx.xfun.app.DisplayRoundRect.BIG_BETWEEN_MARGIN_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_HEIGHT_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_LEFT_MARGIN_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_RADIUS_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_TOP_MARGIN_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_WIDTH_DP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;
import com.afunx.xfun.common.utils.MetricsFUtils;

public class DisplayWave {

    private static final String TAG = "DisplayWave";
    private static final boolean DEBUG = true;

    private final Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private final RectF mBigLeftRectF;
    private final RectF mBigRightRectF;
    private final float mBigRadius;
    private final RectF mLeftRectF;
    private final RectF mRightRectF;
    private final int mWaveCount;
    private final float mWaveHeight;
    private final float mWaveWidth;
    private final float mWaveSpeedPerSecond;
    private long mElapsedRealTime = 0;
    private float mWaveTranslateX = 0;
    private Path mWavePath = null;
    private Paint mPaint = null;
    private Bitmap mMaskBitmap = null;

    private static final float WAVE_HEIGHT_DP = 18.75f;
    private static final float WAVE_WIDTH_DP = 146.25f;
    private static final float WAVE_SPEED_DP_PER_SECOND = 135f;

    public DisplayWave() {
        final Context context = MainApplication.getAppContext();

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

        // 波浪高度
        mWaveHeight = MetricsFUtils.dp2px(context, WAVE_HEIGHT_DP);
        // 波浪宽度
        mWaveWidth = MetricsFUtils.dp2px(context, WAVE_WIDTH_DP);
        // 波浪速度
        mWaveSpeedPerSecond = MetricsFUtils.dp2px(context, WAVE_SPEED_DP_PER_SECOND);
        if (DEBUG) {
            LogUtils.i(TAG, "DisplayWave() mWaveHeight: " + mWaveHeight + ", mWaveWidth: " + mWaveWidth +
                    ", mWaveSpeedPerSecond: " + mWaveSpeedPerSecond);
        }

        final float width = MetricsFUtils.dp2px(context, BIG_WIDTH_DP);
        final float height = MetricsFUtils.dp2px(context, BIG_HEIGHT_DP);
        left = MetricsFUtils.dp2px(context, BIG_LEFT_MARGIN_DP);
        top = MetricsFUtils.dp2px(context, BIG_TOP_MARGIN_DP);
        right = left + width;
        bottom = top + height;
        mLeftRectF = new RectF(left, top + mWaveHeight, right, bottom - mWaveHeight);
        left += MetricsFUtils.dp2px(context, BIG_BETWEEN_MARGIN_DP) + width;
        right += MetricsFUtils.dp2px(context, BIG_BETWEEN_MARGIN_DP) + width;
        mRightRectF = new RectF(left, top + mWaveHeight, right, bottom - mWaveHeight);

        mWaveCount = (int) Math.ceil(width / mWaveWidth);
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        if (mElapsedRealTime == 0) {
            mElapsedRealTime = elapsedRealTime;
        }
        // 度过的时间
        long spentRealTime = elapsedRealTime - mElapsedRealTime;
        // 波浪走过的距离
        mWaveTranslateX += spentRealTime * mWaveSpeedPerSecond / 1000.0f;
        if (DEBUG) {
            LogUtils.i(TAG, "onDraw() spentRealTime: " + spentRealTime + " ms, waveTranslateX: " + mWaveTranslateX + " px");
        }
        if (mWaveTranslateX > mWaveWidth) {
            mWaveTranslateX -= mWaveWidth;
        }
        mElapsedRealTime = elapsedRealTime;
        // 绘制柱体
        //canvas.drawRect(mLeftRectF, paint);
        //canvas.drawRect(mRightRectF, paint);
        // 绘制波浪
        if (mWavePath == null) {
            mWavePath = new Path();
            mWavePath.addPath(getLeftTopPath());
            mWavePath.addPath(getLeftBottomPath());
            mWavePath.addPath(getRightTopPath());
            mWavePath.addPath(getRightBottomPath());
        }

        // dest
        if (mMaskBitmap == null) {
            mPaint = new Paint();
            mPaint.setColor(paint.getColor());
            mMaskBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bmpCanvas = new Canvas(mMaskBitmap);
            bmpCanvas.drawPath(mWavePath, mPaint);
            Shader shader = new BitmapShader(mMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
        }

        //paint.setColor(Color.parseColor("#80ff0000"));
        canvas.save();
        canvas.translate(mWaveTranslateX, 0);
        canvas.drawPath(mWavePath, paint);
        //canvas.drawRoundRect(mBigLeftRectF, mBigRadius, mBigRadius, mPaint);
        //canvas.drawRoundRect(mBigRightRectF, mBigRadius, mBigRadius, mPaint);
        canvas.restore();
        //paint.setColor(Color.parseColor("#8000ff00"));
        //canvas.drawRoundRect(mBigLeftRectF, mBigRadius, mBigRadius, mPaint);
    }

    private Path getLeftTopPath() {
        Path path = new Path();
        final float waveHeight = mWaveHeight;
        final float waveWidth = mWaveWidth;
        path.moveTo(mLeftRectF.left - waveWidth, mLeftRectF.top);
        for (int i = -1; i < mWaveCount; i++) {
            path.rQuadTo(waveWidth / 4, waveHeight * 2, waveWidth / 2, 0);
            path.rQuadTo(waveWidth / 4, -waveHeight * 2, waveWidth / 2, 0);
        }
        path.close();
        return path;
    }

    private Path getLeftBottomPath() {
        Path path = new Path();
        final float waveHeight = mWaveHeight;
        final float waveWidth = mWaveWidth;
        path.moveTo(mLeftRectF.left - waveWidth, mLeftRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rQuadTo(waveWidth / 4, waveHeight * 2, waveWidth / 2, 0);
            path.rQuadTo(waveWidth / 4, -waveHeight * 2, waveWidth / 2, 0);
        }
        path.close();
        return path;
    }

    private Path getRightTopPath() {
        Path path = new Path();
        final float waveHeight = mWaveHeight;
        final float waveWidth = mWaveWidth;
        path.moveTo(mRightRectF.left - waveWidth, mRightRectF.top);
        for (int i = -1; i < mWaveCount; i++) {
            path.rQuadTo(waveWidth / 4, waveHeight * 2, waveWidth / 2, 0);
            path.rQuadTo(waveWidth / 4, -waveHeight * 2, waveWidth / 2, 0);
        }
        path.close();
        return path;
    }

    private Path getRightBottomPath() {
        Path path = new Path();
        final float waveHeight = mWaveHeight;
        final float waveWidth = mWaveWidth;
        path.moveTo(mRightRectF.left - waveWidth, mRightRectF.bottom);
        for (int i = -1; i < mWaveCount; i++) {
            path.rQuadTo(waveWidth / 4, waveHeight * 2, waveWidth / 2, 0);
            path.rQuadTo(waveWidth / 4, -waveHeight * 2, waveWidth / 2, 0);
        }
        path.close();
        return path;
    }
}
