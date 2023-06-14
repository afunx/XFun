package me.afunx.xfun.app.track;

import android.animation.FloatEvaluator;
import android.animation.TimeInterpolator;
import android.graphics.Rect;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 人脸跟随表情控制器。
 * 用于解耦算法结果和UI动效表现。
 */
public class TrackController {

    private static final String TAG = "TrackController";

    private static final boolean DEBUG = true;

    private static final Rect RECT_MISSED = new Rect();

    // 人脸矩形阻塞队列
    private final BlockingQueue<Rect> mFaceRectBlockingQueue = new LinkedBlockingQueue<>();

    // 人脸矩形阻塞队列中，人脸丢失次数
    private final AtomicInteger mFaceMissedCount = new AtomicInteger(0);
    // 是否正在执行人脸复位动画（该变量只在同一线程中使用，无需任何同步机制）
    private boolean mFaceResetting = false;
    // 是否正在执行人脸移动动画（该变量只在同一线程中使用，无需任何同步机制）
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
        if (mFaceResetting) {
            boolean finished = updateAnimation(elapsedRealTime);
            if (finished) {
                if (DEBUG) {
                    LogUtils.i(TAG, "onDraw() faceResetting false");
                }
                mFaceResetting = false;
            }
            return;
        }
        // 存在丢失人脸，必须复原，并且，直接获取最后一个丢失人脸
        if (mFaceMissedCount.get() > 0) {
            synchronized (mFaceRectBlockingQueue) {
                int faceMissedCount = mFaceMissedCount.get();
                while (faceMissedCount > 0) {
                    Rect faceRect = mFaceRectBlockingQueue.poll();
                    if (faceRect == RECT_MISSED) {
                        --faceMissedCount;
                    }
                }
                mFaceMissedCount.set(0);
            }
            controlFaceReset(elapsedRealTime);
        } else {
            // 每次移动必须做完，才进行下一次移动。

            //
        }
    }

    /**
     * 更新动画
     *
     * @param elapsedRealTime   时间戳
     * @return  动画是否已完成
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

    private void controlFaceReset(long elapsedRealTime) {
        if (DEBUG) {
            LogUtils.i(TAG, "controlFaceReset() faceResetting true");
        }
        mFaceResetting = true;
        animationReset();
        setAnimation(elapsedRealTime, 300);
    }

    // 调用setAnimation之前，需要先设置好mLeftAnimationEnd和mRightAnimationEnd
    private void setAnimation(long startTimestamp, long duration) {
        mFaceStartTimestamp = startTimestamp;
        mFaceEndTimestamp = startTimestamp + duration;
        mLeftInAnimationStart.copy(mLeftEye.getInPosture().getTrackAnimation());
        mLeftOutAnimationStart.copy(mLeftEye.getOutPosture().getTrackAnimation());
        mLeftBottomAnimationStart.copy(mLeftEye.getBottomPosture().getTrackAnimation());
        mRightInAnimationStart.copy(mRightEye.getInPosture().getTrackAnimation());
        mRightOutAnimationStart.copy(mRightEye.getOutPosture().getTrackAnimation());
        mRightBottomAnimationStart.copy(mRightEye.getBottomPosture().getTrackAnimation());
    }

    private void animationReset() {
        mLeftInAnimationEnd.reset();
        mLeftOutAnimationEnd.reset();
        mLeftBottomAnimationEnd.reset();
        mRightInAnimationEnd.reset();
        mRightOutAnimationEnd.reset();
        mRightBottomAnimationEnd.reset();
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

    /**
     * 发现人脸
     *
     * @param faceRect 人脸矩形
     */
    public void findFace(@NonNull Rect faceRect) {
        LogUtils.i(TAG, "findFace() faceRect: " + faceRect);
        synchronized (mFaceRectBlockingQueue) {
            //noinspection ResultOfMethodCallIgnored
            mFaceRectBlockingQueue.offer(faceRect);
        }
    }

    /**
     * 丢失人脸
     */
    public void missFace() {
        LogUtils.i(TAG, "missFace()");
        synchronized (mFaceRectBlockingQueue) {
            //noinspection ResultOfMethodCallIgnored
            mFaceRectBlockingQueue.offer(RECT_MISSED);
            mFaceMissedCount.incrementAndGet();
        }
    }

}
