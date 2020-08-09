package me.afunx.xfun.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afunx.xfun.common.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.afunx.xfun.demo.R;

public class DemoFragmentA extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_a, container, false);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("DemoFragmentA");
        return view;
    }
}
