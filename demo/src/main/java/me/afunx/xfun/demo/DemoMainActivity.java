package me.afunx.xfun.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.afunx.xfun.common.base.BaseActivity;

import me.afunx.xfun.demo.fragment.DemoFragmentActivity;
import me.afunx.xfun.demo.listview.DemoListViewActivity;
import me.afunx.xfun.demo.mvvm.DemoMVVMActivity;
import me.afunx.xfun.demo.recyclerview.DemoRecyclerViewActivity;

public class DemoMainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
        findViewById(R.id.btn_goto_demo_list_view).setOnClickListener(this);
        findViewById(R.id.btn_goto_demo_fragment).setOnClickListener(this);
        findViewById(R.id.btn_goto_demo_mvvm).setOnClickListener(this);
        findViewById(R.id.btn_goto_demo_recyclerview).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_goto_demo_list_view) {
            Intent intent = new Intent(this, DemoListViewActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_goto_demo_fragment) {
            Intent intent = new Intent(this, DemoFragmentActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_goto_demo_mvvm) {
            Intent intent = new Intent(this, DemoMVVMActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_goto_demo_recyclerview) {
            Intent intent = new Intent(this, DemoRecyclerViewActivity.class);
            startActivity(intent);
        }
    }
}
