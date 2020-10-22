package me.afunx.xfun.demo.soundmeter;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.File;

public class MediaRecorderNoiseDB {

    private final String TAG = "MediaRecorderNoiseDB";

    private static final int BASE = 1;
    private static final int INTERVAL_MILLISECONDS = 100;

    private MediaRecorder mMediaRecorder;

    public void startRecord() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        try {
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "XFun";
            File dirFile = new File(dirPath);
            if (!dirFile.exists() && !dirFile.mkdirs()) {
                Log.e(TAG, "startRecord() fail to mkdirs dirPath: " + dirPath);
                return;
            }
            String filePath = dirPath + File.separator + System.currentTimeMillis() + ".amr";
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            updateMicStatus();
            Log.i(TAG, "startRecord()");
        } catch (Exception e) {
            Log.i(TAG, "startRecord() failed, e: " + e);
            e.printStackTrace();
        }
    }

    public void stopRecord() {
        if (mMediaRecorder == null) {
            return;
        }
        Log.i(TAG, "stopRecord()");
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    private final Handler mHandler = new Handler();

    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    private void updateMicStatus() {
        if (mMediaRecorder != null) {
            double ratio = (double) mMediaRecorder.getMaxAmplitude() / BASE;
            double db = 0;
            if (ratio > 1)
                db = 20 * Math.log10(ratio);
            Log.d(TAG, "Noise DB：" + db + ", ratio: " + ratio);
            mHandler.postDelayed(mUpdateMicStatusTimer, INTERVAL_MILLISECONDS);
        }
    }
}
