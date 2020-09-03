package com.ubtechinc.aimbothumming.biz;

import androidx.annotation.NonNull;

import com.ubtechinc.aimbothumming.utils.LogUtils;

public class HummingFrame {

    private static final String TAG = "HummingFrame";

    private static int BUF_SIZE;

    public static void setBufSize(int bufSize) {
        LogUtils.ii(TAG, "setBufSize() bufSize: " + bufSize);
        BUF_SIZE = bufSize;
    }

    private final byte[] bytes;

    private long timestamp;

    // 0: 未定位 1: 定位
    private int locationFlag;

    private float locationX;

    private float locationY;

    private int detectType;

    private String mapName;

    public HummingFrame() {
        bytes = new byte[BUF_SIZE];
    }

    public byte[] getBytes() {
        return bytes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float locationX) {
        this.locationX = locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float locationY) {
        this.locationY = locationY;
    }

    public int getLocationFlag() {
        return locationFlag;
    }

    public void setLocationFlag(int locationFlag) {
        this.locationFlag = locationFlag;
    }

    public int getDetectType() {
        return detectType;
    }

    public void setDetectType(int detectType) {
        this.detectType = detectType;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    @NonNull
    @Override
    public String toString() {
        return "HummingFrame{" +
                "bytes.length=" + bytes.length +
                ", timestamp=" + timestamp +
                ", locationFlag=" + locationFlag +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", detectType=" + detectType +
                ", mapName='" + mapName + '\'' +
                '}';
    }
}