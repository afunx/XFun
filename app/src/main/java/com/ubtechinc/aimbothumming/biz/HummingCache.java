package com.ubtechinc.aimbothumming.biz;

import androidx.annotation.NonNull;

public interface HummingCache {

    /**
     * 设置HummingCache最大可容纳Frame的大小
     *
     * @param size  最大可容纳Frame的大小
     */
    void setFrameSize(int size);

    /**
     * 打断waitFramesFromHead的等待
     */
    void interrupt();

    /**
     * 清除interrupt标志位
     */
    void clearInterrupted();

    /**
     * 添加 "一帧" HummingFrame到尾部
     *
     * @param hummingFrame HummingFrame
     */
    void putOneFrameAtTail(@NonNull HummingFrame hummingFrame);

    /**
     * 等待从头部取出HummingFrame放入hummingFrames
     *
     * @return               是否已经interrupt
     */
    boolean waitFramesFromHead(@NonNull HummingFrame[] hummingFrames);

    /**
     * 返回HummingFrames
     *
     * @param hummingFrames HummingFrame[]
     */
    void returnFrames(@NonNull HummingFrame[] hummingFrames);

    /**
     * 释放HummingFrames
     *
     * @param hummingFrames HummingFrame[]
     */
    void releaseFrames(@NonNull HummingFrame[] hummingFrames);

}