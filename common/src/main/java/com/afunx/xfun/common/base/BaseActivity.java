package com.afunx.xfun.common.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afunx.xfun.common.utils.LogUtils;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(getClass().getSimpleName(), "onCreate() taskId: " + getTaskId() + ", this: " + this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        LogUtils.i(getClass().getSimpleName(), "onCreate() persistentState: " + persistentState + ", taskId: " + getTaskId() + ", this: " + this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.i(getClass().getSimpleName(), "onStart() taskId: " + getTaskId() + ", this: " + this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i(getClass().getSimpleName(), "onResume() taskId: " + getTaskId() + ", this: " + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.i(getClass().getSimpleName(), "onPause() taskId: " + getTaskId() + ", this: " + this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i(getClass().getSimpleName(), "onStop() taskId: " + getTaskId() + ", this: " + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(getClass().getSimpleName(), "onDestroy() taskId: " + getTaskId() + ", this: " + this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.i(getClass().getSimpleName(), "onRestart() taskId: " + getTaskId() + ", this: " + this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        LogUtils.i(getClass().getSimpleName(), "onSaveInstanceState() taskId: " + getTaskId() + ", this: " + this
                + ", outState: " + outState + ", outPersistentState: " + outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.i(getClass().getSimpleName(), "onSaveInstanceState() taskId: " + getTaskId() + ", this: " + this
                + ", outState: " + outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtils.i(getClass().getSimpleName(), "onRestoreInstanceState() taskId: " + getTaskId() + ", this: " + this
                + ", savedInstanceState: " + savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogUtils.i(getClass().getSimpleName(), "onWindowFocusChanged() hasFocus: " + hasFocus);
    }
}
