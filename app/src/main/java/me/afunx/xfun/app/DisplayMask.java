package me.afunx.xfun.app;

import static me.afunx.xfun.app.DisplayRoundRect.BIG_BETWEEN_MARGIN_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_HEIGHT_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_LEFT_MARGIN_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_TOP_MARGIN_DP;
import static me.afunx.xfun.app.DisplayRoundRect.BIG_WIDTH_DP;
import static me.afunx.xfun.app.DisplayRoundRect.SMALL_TRIANGLE_SIDE_DP;
import static me.afunx.xfun.app.DisplayRoundRect.THICKNESS_DP;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.MetricsFUtils;

public class DisplayMask {

    private final RectF mSmallLeftRectF;
    private final RectF mSmallRightRectF;
    private final float mSmallTriangleSide;
    private Path mMaskPath = null;

    public DisplayMask() {
        final Context context = MainApplication.getAppContext();
        // 大
        final float bigWidth = MetricsFUtils.dp2px(context, BIG_WIDTH_DP);
        final float bigHeight = MetricsFUtils.dp2px(context, BIG_HEIGHT_DP);
        float left = MetricsFUtils.dp2px(context, BIG_LEFT_MARGIN_DP);
        float top = MetricsFUtils.dp2px(context, BIG_TOP_MARGIN_DP);
        float right = left + bigWidth;
        float bottom = top + bigHeight;
        RectF bigLeftRectF = new RectF(left, top, right, bottom);
        left += MetricsFUtils.dp2px(context, BIG_BETWEEN_MARGIN_DP) + bigWidth;
        right += MetricsFUtils.dp2px(context, BIG_BETWEEN_MARGIN_DP) + bigWidth;
        RectF bigRightRectF = new RectF(left, top, right, bottom);

        // 小
        final float smallDiff = MetricsFUtils.dp2px(context, THICKNESS_DP);
        left = bigLeftRectF.left + smallDiff;
        top = bigLeftRectF.top + smallDiff;
        right = bigLeftRectF.right - smallDiff;
        bottom = bigLeftRectF.bottom - smallDiff;
        mSmallLeftRectF = new RectF(left, top, right, bottom);
        left = bigRightRectF.left + smallDiff;
        top = bigRightRectF.top + smallDiff;
        right = bigRightRectF.right - smallDiff;
        bottom = bigRightRectF.bottom - smallDiff;
        mSmallRightRectF = new RectF(left, top, right, bottom);

        mSmallTriangleSide = MetricsFUtils.dp2px(context, SMALL_TRIANGLE_SIDE_DP);
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 绘制该帧时，SystemClock.elapsedRealtime
     * @param canvas
     * @param paint
     */
    public void onDraw(long elapsedRealTime, @NonNull Canvas canvas, @NonNull Paint paint) {
        if (mMaskPath == null) {
            mMaskPath = getMaskPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mMaskPath, paint);
    }

    private Path getMaskPath(int canvasWidth, int canvasHeight) {
        Path path = new Path();
        final float side = mSmallTriangleSide;
        // 左
        final float leftSmallLeft = mSmallLeftRectF.left;
        final float leftSmallTop = mSmallLeftRectF.top;
        final float leftSmallRight = mSmallLeftRectF.right;
        final float leftSmallBottom = mSmallLeftRectF.bottom;
        // 0
        Path path0 = new Path();
        path0.moveTo(0, 0);
        path0.rLineTo(0, canvasHeight);
        path0.rLineTo(leftSmallLeft, 0);
        path0.rLineTo(0, -canvasHeight);
        path0.close();
        path.addPath(path0);
        // 1
        Path path1 = new Path();
        path1.moveTo(leftSmallLeft, 0);
        path1.rLineTo(0, leftSmallTop + side);
        path1.rLineTo(side, -side);
        path1.rLineTo(0, -leftSmallTop);
        path1.close();
        path.addPath(path1);
        // 2
        Path path2 = new Path();
        path2.moveTo(leftSmallLeft, leftSmallBottom - side);
        path2.rLineTo(0, canvasHeight - leftSmallBottom + side);
        path2.rLineTo(side, 0);
        path2.rLineTo(0, -(canvasHeight - leftSmallBottom));
        path2.close();
        path.addPath(path2);
        // 3
        Path path3 = new Path();
        path3.moveTo(leftSmallLeft + side, 0);
        path3.rLineTo(0, leftSmallTop);
        path3.rLineTo(leftSmallRight - leftSmallLeft - 2 * side, 0);
        path3.rLineTo(0, -leftSmallTop);
        path3.close();
        path.addPath(path3);
        // 4
        Path path4 = new Path();
        path4.moveTo(leftSmallLeft + side, leftSmallBottom);
        path4.rLineTo(0, canvasHeight - leftSmallBottom);
        path4.rLineTo(leftSmallRight - leftSmallLeft - 2 * side, 0);
        path4.rLineTo(0, -(canvasHeight - leftSmallBottom));
        path4.close();
        path.addPath(path4);
        // 5
        Path path5 = new Path();
        path5.moveTo(leftSmallRight - side, 0);
        path5.rLineTo(0, leftSmallTop);
        path5.rLineTo(side, side);
        path5.rLineTo(0, -(leftSmallTop + side));
        path5.close();
        path.addPath(path5);
        // 6
        Path path6 = new Path();
        path6.moveTo(leftSmallRight - side, leftSmallBottom);
        path6.rLineTo(0, canvasHeight - leftSmallBottom);
        path6.rLineTo(side, 0);
        path6.rLineTo(0, -(canvasHeight - leftSmallBottom + side));
        path6.close();
        path.addPath(path6);

        // 中
        final float rightSmallLeft = mSmallRightRectF.left;
        final float rightSmallTop = mSmallRightRectF.top;
        final float rightSmallRight = mSmallRightRectF.right;
        final float rightSmallBottom = mSmallRightRectF.bottom;
        // 7
        Path path7 = new Path();
        path7.moveTo(leftSmallRight,0);
        path7.rLineTo(0, canvasHeight);
        path7.rLineTo(rightSmallLeft - leftSmallRight, 0);
        path7.rLineTo(0, -canvasHeight);
        path7.close();
        path.addPath(path7);

        // 右
        // 8
        path.addPath(path1, rightSmallLeft - leftSmallLeft, 0);
        // 9
        path.addPath(path2, rightSmallLeft - leftSmallLeft, 0);
        // 10
        path.addPath(path3, rightSmallLeft - leftSmallLeft, 0);
        // 11
        path.addPath(path4, rightSmallLeft - leftSmallLeft, 0);
        // 12
        path.addPath(path5, rightSmallLeft - leftSmallLeft, 0);
        // 13
        path.addPath(path6, rightSmallLeft - leftSmallLeft, 0);
        // 14
        Path path14 = new Path();
        path14.moveTo(rightSmallRight,0);
        path14.rLineTo(0, canvasHeight);
        path14.rLineTo(canvasWidth - rightSmallRight, 0);
        path14.rLineTo(0, -canvasHeight);
        path14.close();
        path.addPath(path14);
        return path;
    }
}
