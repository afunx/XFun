package com.ubtechinc.aimbothumming.biz;

import androidx.annotation.Nullable;

import com.ubtechinc.aimbothumming.biz.mock.Location;

public interface HummingFrameRouter {

    // 125ms/帧，8*10*60=4800为10分钟的容量
    int MAX_CACHE_SIZE = 8 * 10 * 60;

    // 最小写到硬盘的帧数大小为MAX_CACHE_SIZE的一半
    // !!!注意：MIN_STORAGE_SIZE > UPLOAD_HUMMING_FRAME_COUNT 是必须的！！！
    int MIN_STORAGE_SIZE = MAX_CACHE_SIZE / 2;

    // count = 1 表示 125ms，1 min / 125 ms = 60 * 1000 / 125 = 480
    // count = 1 表示 125ms，10 s / 125 ms = 10 * 1000 / 125 = 80
    int UPLOAD_HUMMING_FRAME_COUNT = 80;

    /**
     * HummingFrameRouter开始运行
     */
    void start();

    /**
     * HummingFrameRouter结束运行
     */
    void stop();

    /**
     * 检查HHummingFrameRouter是否正在运行
     *
     * @return  HummingFrameRouter是否正在运行
     */
    boolean isStopped();

    /**
     * 添加 "一帧" 音频数据及相关信息
     *
     * @param oneFrameBytes 音频数据
     * @param location      位置
     * @param detectType    识别类型
     * @param mapName       地图名称
     */
    void addOneFrame(byte[] oneFrameBytes, Location location, int detectType, String mapName);

    /**
     * 等待网络可用
     */
    void waitNetworkAvailable();

    /**
     * 获取帧文件路径
     *
     * @return  HummingFrameFilePath
     */
    @Nullable
    HummingFrameFilesPath getFrameFilePath();

    /**
     * 获取发送给服务器的HummingFrame[]
     *
     * @return              HummingFrame[]
     */
    @Nullable HummingFrame[] getUploadHummingFrames();

    /**
     * 发送数据给服务器成功
     */
    void doUploadSuc();

    /**
     * 发送数据给服务器失败
     */
    void doUploadFail();


}
