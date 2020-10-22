package me.afunx.xfun.demo.soundmeter;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.afunx.xfun.common.utils.ByteUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class AudioRecordNoiseDB extends Thread {

    private static final String TAG = "RecorderThread";

    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;

    private static final int SAMPLE_RATE_IN_HZ = 16 * 1000;

    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;

    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord mAudioRecord;

    private final AtomicBoolean mRunning = new AtomicBoolean(false);

    private int mRecordBufSize;

    private byte[] mRecordBuffer;

    private boolean initAudioRecord() {
        mRecordBufSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        Log.i(TAG, "initAudioRecord() recordBufSize: " + mRecordBufSize);

        if (mRecordBuffer == null) {
            mRecordBuffer = new byte[mRecordBufSize];
        }

        mAudioRecord = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT, mRecordBufSize);
        boolean initSuccess = mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED;
        Log.i(TAG, "initAudioRecord() initSuccess: " + initSuccess);
        return initSuccess;
    }

    private void testNoiseDb(byte[] noiseBytes) {
        short maxValue = 0;
        short temp;
        for (int i = 0; i < noiseBytes.length / 2; i += 2) {
            temp = ByteUtils.bytes2short(noiseBytes, i, false);
            if (temp < 0) {
                temp = (short) -temp;
            }
            if (temp > maxValue) {
                maxValue = temp;
            }
        }
        double db = maxValue > 0 ? 20 * Math.log10(maxValue) : 0;
        Log.e(TAG, "testNoiseDb() maxValue: " + maxValue + ", db: " + db);
    }

    @Override
    public void run() {
        while (mRunning.get()) {
            mAudioRecord.read(mRecordBuffer, 0, mRecordBufSize);
            testNoiseDb(mRecordBuffer);
        }
    }

    public void startRecord() {
        Log.i(TAG, "startRecord()");
        // 初始化录音
        boolean initSuccess = initAudioRecord();
        if (!initSuccess) {
            Log.e(TAG, "onStart() init fail");
            return;
        }

        mRunning.set(true);
        mAudioRecord.startRecording();
        start();
    }

    public void stopRecord() {
        Log.i(TAG, "stopRecord()");
        mRunning.set(false);
        if (mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }
    }
}
