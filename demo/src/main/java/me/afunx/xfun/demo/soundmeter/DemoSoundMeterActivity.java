package me.afunx.xfun.demo.soundmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import me.afunx.xfun.demo.R;

public class DemoSoundMeterActivity extends AppCompatActivity {

    // use AudioRecordNoiseDB or MediaRecorderNoiseDB
    private final boolean useAudioRecord = true;

    private AudioRecordNoiseDB mAudioRecordNoiseDB = new AudioRecordNoiseDB();
    private MediaRecorderNoiseDB mMediaRecorderNoiseDB = new MediaRecorderNoiseDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_sound_meter);

        if (useAudioRecord) {
            mAudioRecordNoiseDB.startRecord();
        } else {
            mMediaRecorderNoiseDB.startRecord();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (useAudioRecord) {
            mAudioRecordNoiseDB.stopRecord();
        } else {
            mMediaRecorderNoiseDB.stopRecord();
        }
    }
}