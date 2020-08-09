package com.afunx.xfun.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afunx.xfun.common.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        LogUtils.i(getClass().getSimpleName(), "onAttach() context: " + context + ", this: " + this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(getClass().getSimpleName(), "onCreate() savedInstanceState: " + savedInstanceState + ", this: " + this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.i(getClass().getSimpleName(), "onCreateView() savedInstanceState: " + savedInstanceState + ", this: " + this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtils.i(getClass().getSimpleName(), "onActivityCreated() savedInstanceState: " + savedInstanceState + ", this: " + this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(getClass().getSimpleName(), "onStart() this: " + this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(getClass().getSimpleName(), "onResume() this: " + this);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(getClass().getSimpleName(), "onPause() this: " + this);
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i(getClass().getSimpleName(), "onStop() this: " + this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(getClass().getSimpleName(), "onDestroyView() this: " + this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(getClass().getSimpleName(), "onDestroy() this: " + this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.i(getClass().getSimpleName(), "onDetach() this: " + this);
    }

}
