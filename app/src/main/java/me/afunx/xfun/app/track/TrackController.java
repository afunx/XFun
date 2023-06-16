package me.afunx.xfun.app.track;

import android.animation.FloatEvaluator;
import android.animation.TimeInterpolator;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 人脸跟随表情控制器。
 * 用于解耦算法结果和UI动效表现。
 */
public class TrackController {

    private static final String TAG = "TrackController";

    private static final boolean DEBUG = true;

    private static final Rect RECT_MISSED = new Rect();

    // 人脸矩形阻塞队列
    private final BlockingQueue<TrackFaceResult> mFaceResultBlockingQueue = new LinkedBlockingQueue<>();

    // 是否正在执行人脸复位动画
    private boolean mFaceResetting = false;
    // 是否正在执行人脸移动动画
    private boolean mFaceMoving = false;

    // 人脸动画开始时间戳
    private long mFaceStartTimestamp;

    // 人脸动画结束时间戳
    private long mFaceEndTimestamp;

    private final TrackLeftEye mLeftEye;
    private final TrackRightEye mRightEye;

    // left in
    private final TrackAnimation mLeftInAnimationStart = new TrackAnimation();
    private final TrackAnimation mLeftInAnimationEnd = new TrackAnimation();
    // left out
    private final TrackAnimation mLeftOutAnimationStart = new TrackAnimation();
    private final TrackAnimation mLeftOutAnimationEnd = new TrackAnimation();
    // left bottom
    private final TrackAnimation mLeftBottomAnimationStart = new TrackAnimation();
    private final TrackAnimation mLeftBottomAnimationEnd = new TrackAnimation();

    // right in
    private final TrackAnimation mRightInAnimationStart = new TrackAnimation();
    private final TrackAnimation mRightInAnimationEnd = new TrackAnimation();
    // right out
    private final TrackAnimation mRightOutAnimationStart = new TrackAnimation();
    private final TrackAnimation mRightOutAnimationEnd = new TrackAnimation();
    // right bottom
    private final TrackAnimation mRightBottomAnimationStart = new TrackAnimation();
    private final TrackAnimation mRightBottomAnimationEnd = new TrackAnimation();

    private final TimeInterpolator mTimeInterpolator = new LinearInterpolator();

    private final FloatEvaluator mFloatEvaluator = new FloatEvaluator();

    private final TrackFaceResultPool mFaceResultPool = new TrackFaceResultPool();

    private TrackFaceResult mLastFaceResult = null;

    TrackController(@NonNull TrackLeftEye leftEye, @NonNull TrackRightEye rightEye) {
        mLeftEye = leftEye;
        mRightEye = rightEye;
    }

    /**
     * 绘制回调
     *
     * @param elapsedRealTime 当前时间戳
     */
    void onDraw(long elapsedRealTime) {
        TrackFaceResult faceResult = mFaceResultBlockingQueue.poll();
        while (faceResult!= null && !mFaceResultBlockingQueue.isEmpty()) {
            LogUtils.i(TAG, "onDraw() elapsedRealTime: " + elapsedRealTime + ", skip faceResult: " + faceResult);
            mFaceResultPool.release(faceResult);
            faceResult = mFaceResultBlockingQueue.poll();
        }
        if (faceResult != null) {
            if (faceResult.rect == RECT_MISSED) {
                controlFaceReset(faceResult, elapsedRealTime);
                mFaceResetting = true;
            } else {
                controlFaceMove(faceResult, elapsedRealTime);
                mFaceMoving = true;
            }
            copyLastFaceResult(faceResult);
            mFaceResultPool.release(faceResult);
        }
        if (mFaceResetting || mFaceMoving) {
            boolean finished = updateAnimation(elapsedRealTime + 1);
            if (finished) {
                LogUtils.i(TAG, "onDraw() finished, mFaceResetting: " + mFaceResetting + ", mFaceMoving: " + mFaceMoving);
                mFaceMoving = false;
                mFaceResetting = false;
            }
        }
    }

    /**
     * 更新动画
     *
     * @param elapsedRealTime 时间戳
     * @return 动画是否已完成
     */
    private boolean updateAnimation(long elapsedRealTime) {
        boolean finished = elapsedRealTime >= mFaceEndTimestamp;
        elapsedRealTime = Math.min(elapsedRealTime, mFaceEndTimestamp);
        float input = 1.0f * (elapsedRealTime - mFaceStartTimestamp) / (mFaceEndTimestamp - mFaceStartTimestamp);
        float fraction = mTimeInterpolator.getInterpolation(input);
        // 目前，仅支持位移
        float translateX = mFloatEvaluator.evaluate(fraction, mLeftInAnimationStart.mTranslateX, mLeftInAnimationEnd.mTranslateX);
        float translateY = mFloatEvaluator.evaluate(fraction, mLeftInAnimationStart.mTranslateY, mLeftInAnimationEnd.mTranslateY);
        mLeftEye.getInPosture().translate(translateX, translateY);

        translateX = mFloatEvaluator.evaluate(fraction, mLeftOutAnimationStart.mTranslateX, mLeftOutAnimationEnd.mTranslateX);
        translateY = mFloatEvaluator.evaluate(fraction, mLeftOutAnimationStart.mTranslateY, mLeftOutAnimationEnd.mTranslateY);
        mLeftEye.getOutPosture().translate(translateX, translateY);

        translateX = mFloatEvaluator.evaluate(fraction, mLeftBottomAnimationStart.mTranslateX, mLeftBottomAnimationEnd.mTranslateX);
        translateY = mFloatEvaluator.evaluate(fraction, mLeftBottomAnimationStart.mTranslateY, mLeftBottomAnimationEnd.mTranslateY);
        mLeftEye.getBottomPosture().translate(translateX, translateY);

        translateX = mFloatEvaluator.evaluate(fraction, mRightInAnimationStart.mTranslateX, mRightInAnimationEnd.mTranslateX);
        translateY = mFloatEvaluator.evaluate(fraction, mRightInAnimationStart.mTranslateY, mRightInAnimationEnd.mTranslateY);
        mRightEye.getInPosture().translate(translateX, translateY);

        translateX = mFloatEvaluator.evaluate(fraction, mRightOutAnimationStart.mTranslateX, mRightOutAnimationEnd.mTranslateX);
        translateY = mFloatEvaluator.evaluate(fraction, mRightOutAnimationStart.mTranslateY, mRightOutAnimationEnd.mTranslateY);
        mRightEye.getOutPosture().translate(translateX, translateY);

        translateX = mFloatEvaluator.evaluate(fraction, mRightBottomAnimationStart.mTranslateX, mRightBottomAnimationEnd.mTranslateX);
        translateY = mFloatEvaluator.evaluate(fraction, mRightBottomAnimationStart.mTranslateY, mRightBottomAnimationEnd.mTranslateY);
        mRightEye.getBottomPosture().translate(translateX, translateY);

        return finished;
    }

    private void controlFaceReset(@NonNull TrackFaceResult faceResult, long elapsedRealTime) {
        final long duration;
        if (mLastFaceResult != null) {
            duration = faceResult.timestamp - mLastFaceResult.timestamp;
        } else {
            duration = 0;
        }
        long startTimestamp = Math.min(faceResult.timestamp, elapsedRealTime);
        if (DEBUG) {
            LogUtils.i(TAG, "controlFaceReset() duration: " + duration + ", startTimestamp: " + startTimestamp);
        }
        setAnimationStart(startTimestamp, duration);
        setAnimationEndReset();
    }

    private void controlFaceMove(@NonNull TrackFaceResult faceResult, long elapsedRealTime) {
        final long duration;
        if (mLastFaceResult != null) {
            duration = faceResult.timestamp - mLastFaceResult.timestamp;
        } else {
            duration = 0;
        }
        long startTimestamp = Math.min(faceResult.timestamp, elapsedRealTime);
        if (DEBUG) {
            LogUtils.i(TAG, "controlFaceMove() duration: " + duration + ", startTimestamp: " + startTimestamp);
        }
        setAnimationStart(startTimestamp, duration);
        setAnimationEndMove(faceResult);
    }

    private void copyLastFaceResult(@NonNull TrackFaceResult faceResult) {
        if (mLastFaceResult == null) {
            mLastFaceResult = new TrackFaceResult();
        }
        mLastFaceResult.copy(faceResult);
    }

    // 调用setAnimation之前，需要先设置好mLeftAnimationEnd和mRightAnimationEnd
    private void setAnimationStart(long startTimestamp, long duration) {
        // duration最少为1，否则，会有分母为0的问题
        duration = Math.max(1, duration);
        mFaceStartTimestamp = startTimestamp;
        mFaceEndTimestamp = startTimestamp + duration;
        mLeftInAnimationStart.copy(mLeftEye.getInPosture().getTrackAnimation());
        mLeftOutAnimationStart.copy(mLeftEye.getOutPosture().getTrackAnimation());
        mLeftBottomAnimationStart.copy(mLeftEye.getBottomPosture().getTrackAnimation());
        mRightInAnimationStart.copy(mRightEye.getInPosture().getTrackAnimation());
        mRightOutAnimationStart.copy(mRightEye.getOutPosture().getTrackAnimation());
        mRightBottomAnimationStart.copy(mRightEye.getBottomPosture().getTrackAnimation());
    }

    private void setAnimationEndReset() {
        mLeftInAnimationEnd.reset();
        mLeftOutAnimationEnd.reset();
        mLeftBottomAnimationEnd.reset();
        mRightInAnimationEnd.reset();
        mRightOutAnimationEnd.reset();
        mRightBottomAnimationEnd.reset();
    }

    private void setAnimationEndMove(@NonNull TrackFaceResult faceResult) {
        mLeftOutAnimationEnd.mTranslateX = faceResult.trackFaceOutTranslateX();
        mLeftOutAnimationEnd.mTranslateY = faceResult.trackFaceOutTranslateY();
        // TODO eyeIn方法暂时没有，先用eyeOut数值
        mLeftInAnimationEnd.mTranslateX = mLeftOutAnimationEnd.mTranslateX;
        mLeftInAnimationEnd.mTranslateY = mLeftOutAnimationEnd.mTranslateY;
        mLeftBottomAnimationEnd.mTranslateX = mLeftOutAnimationEnd.mTranslateX;
        mLeftBottomAnimationEnd.mTranslateY = mLeftOutAnimationEnd.mTranslateY;

        mRightOutAnimationEnd.mTranslateX = faceResult.trackFaceOutTranslateX();
        mRightOutAnimationEnd.mTranslateY = faceResult.trackFaceOutTranslateY();
        // TODO eyeIn方法暂时没有，先用eyeOut数值
        mRightInAnimationEnd.mTranslateX = mLeftOutAnimationEnd.mTranslateX;
        mRightInAnimationEnd.mTranslateY = mLeftOutAnimationEnd.mTranslateY;
        mRightBottomAnimationEnd.mTranslateX = mLeftOutAnimationEnd.mTranslateX;
        mRightBottomAnimationEnd.mTranslateY = mLeftOutAnimationEnd.mTranslateY;
    }

    // 调试使用
    void translateDelta(float deltaTranslateX, float deltaTranslateY) {
        mLeftEye.getInPosture().translateDelta(deltaTranslateX, deltaTranslateY);
        mLeftEye.getOutPosture().translateDelta(deltaTranslateX, deltaTranslateY);
        mLeftEye.getBottomPosture().translateDelta(deltaTranslateX, deltaTranslateY);
        mRightEye.getInPosture().translateDelta(deltaTranslateX, deltaTranslateY);
        mRightEye.getOutPosture().translateDelta(deltaTranslateX, deltaTranslateY);
        mRightEye.getBottomPosture().translateDelta(deltaTranslateX, deltaTranslateY);
    }

    // 调试使用
    void translateInDelta(float deltaTranslateX, float deltaTranslateY) {
        mLeftEye.getInPosture().translateDelta(deltaTranslateX, deltaTranslateY);
        mRightEye.getInPosture().translateDelta(deltaTranslateX, deltaTranslateY);
    }

    /**
     * 发现人脸
     *
     * @param faceRect     人脸矩形
     * @param bitmapWidth  图片宽度
     * @param bitmapHeight 图片高度
     */
    public void findFace(@NonNull Rect faceRect, int bitmapWidth, int bitmapHeight) {
        final long timestamp = SystemClock.elapsedRealtime();
        LogUtils.i(TAG, "findFace() faceRect: " + faceRect);
        TrackFaceResult trackFaceResult = mFaceResultPool.acquire();
        trackFaceResult.rect = faceRect;
        trackFaceResult.timestamp = timestamp;
        trackFaceResult.bitmapWidth = bitmapWidth;
        trackFaceResult.bitmapHeight = bitmapHeight;
        //noinspection ResultOfMethodCallIgnored
        mFaceResultBlockingQueue.offer(trackFaceResult);
    }

    /**
     * 丢失人脸
     */
    public void missFace() {
        final long timestamp = SystemClock.elapsedRealtime();
        LogUtils.i(TAG, "missFace()");
        TrackFaceResult trackFaceResult = mFaceResultPool.acquire();
        trackFaceResult.rect = RECT_MISSED;
        trackFaceResult.timestamp = timestamp;
        //noinspection ResultOfMethodCallIgnored
        mFaceResultBlockingQueue.offer(trackFaceResult);
    }

}
