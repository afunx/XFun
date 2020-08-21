package me.afunx.xfun.demo.mvvm;

import androidx.databinding.DataBindingUtil;
import me.afunx.xfun.demo.R;
import me.afunx.xfun.demo.databinding.ActivityDemoMvvmBinding;

import android.os.Bundle;

import com.afunx.xfun.common.base.BaseActivity;

public class DemoMVVMActivity extends BaseActivity {

    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDemoMvvmBinding mvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo_mvvm);
        userModel = new UserModel();
        userModel.setName("afunx");
        mvvmBinding.setUser(userModel);
    }
}
