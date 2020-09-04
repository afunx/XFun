package com.ubtechinc.aimbothumming.biz.thread;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.ubtechinc.aimbothumming.biz.HummingFrame;
import com.ubtechinc.aimbothumming.biz.LocationSnapshot;
import com.ubtechinc.aimbothumming.biz.impl.HummingCacheOldImpl;
import com.ubtechinc.aimbothumming.biz.mock.Location;
import com.ubtechinc.aimbothumming.utils.LogUtils;
//import com.ubtrobot.navigation.Location;

public class HummingRecorderThread extends AbstractTaskThread {

    private static final String TAG = "HummingRecorderThread";

    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    // 16K采样率
    private static final int SAMPLE_RATE_IN_HZ = 16 * 1000;
    // 单声道
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    // PCM 16bit
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord mAudioRecord;

    private int mRecordBufSize;

    private byte[] mRecordBuffer;

    private final HummingCacheOldImpl mHummingCache = HummingCacheOldImpl.get();

    private volatile int mDetectType;

    private HummingRecorderThread() {
        super("HummingRecorderThread");
        Runnable taskRunnable = new Runnable() {
            @Override
            public void run() {
                mAudioRecord.read(mRecordBuffer, 0, mRecordBufSize);

                Location location = LocationSnapshot.get().getCurrentLocation();
                String mapName = LocationSnapshot.get().getCurrentMapName();
                mHummingCache.putOneFrameAtTail(mRecordBuffer, location, mDetectType, mapName);
            }
        };
        setRunnable(taskRunnable);
    }

    private static class Singleton {
        private static HummingRecorderThread instance = new HummingRecorderThread();
    }

    public static HummingRecorderThread get() {
        return HummingRecorderThread.Singleton.instance;
    }

    public void setDetectType(int detectType) {
        mDetectType = detectType;
        LogUtils.ii(TAG, "setDetectType() detectType: " + detectType);
    }

    private boolean initAudioRecord() {
        mRecordBufSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        LogUtils.ii(TAG, "initAudioRecord() recordBufSize: " + mRecordBufSize);

        HummingFrame.setBufSize(mRecordBufSize);

        if (mRecordBuffer == null) {
            mRecordBuffer = new byte[mRecordBufSize];
        }

        mAudioRecord = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT, mRecordBufSize);
        boolean initSuccess = mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED;
        LogUtils.ii(TAG, "initAudioRecord() initSuccess: " + initSuccess);
        return initSuccess;
    }

    @Override
    protected void onStart() {
        LogUtils.ii(TAG, "onStart()");

        // 初始化录音
        boolean initSuccess = initAudioRecord();
        if (!initSuccess) {
            LogUtils.ee(TAG, "onStart() init fail");
            mHummingCache.stop();
            return;
        }

        // 开始录音
        mAudioRecord.startRecording();
    }

    @Override
    protected void onStop() {
        LogUtils.ii(TAG, "onStop() release");
        if (mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
        }
    }

}
