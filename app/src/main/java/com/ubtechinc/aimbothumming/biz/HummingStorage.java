package com.ubtechinc.aimbothumming.biz;

public interface HummingStorage {
    /**
     * 深拷贝 "一帧" 音频数据及相关信息到缓存尾部
     *
     * @param hummingFrame  "一帧"音频数据及
     */
    void deepCopyOneFrameAtTail(HummingFrame hummingFrame);
}
