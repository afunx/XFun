package com.ubtechinc.aimbothumming.biz;

import com.ubtechinc.aimbothumming.biz.mock.Location;

public interface HummingCache {

    /**
     * HummingCache开始运行
     */
    void start();

    /**
     * HummingCache结束运行
     */
    void stop();

    /**
     * 检查HummingCache是否正在运行
     *
     * @return  HummingCache是否正在运行
     */
    boolean isStopped();

    /**
     * 添加 "一帧" 音频数据及定位到缓存尾部
     *
     * @param oneFrameBytes 音频数据
     * @param location      位置
     * @param detectType    识别类型
     * @param mapName       地图名称
     */
    void putOneFrameAtTail(byte[] oneFrameBytes, Location location, int detectType, String mapName);

    /**
     * 从缓存头部取出count帧TempCacheFrames
     *
     * @param count         待取出的帧数
     * @return              TempCacheFrames
     */
    HummingFrame[] getTempCacheFramesFromHead(int count);

    /**
     * 返回TempCacheFrames（目前是在发送给服务器失败后调用）
     */
    void returnTempCacheFrames();

    /**
     * 释放TempCacheFrames（目前是在发送给服务器成功后调用）
     */
    void releaseTempCacheFrames();
}
