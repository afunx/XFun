package me.afunx.xfun.demo.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import me.afunx.xfun.demo.R;

public class DemoRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_recycler_view);

        initRecyclerView();
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.rv);

        // 设置Adapter
        RecyclerDemoAdapter demoAdapter = new RecyclerDemoAdapter(this);
        recyclerView.setAdapter(demoAdapter);

        // 设置LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 数据初始化
        for (int i = 0; i < 20; i++) {
            demoAdapter.addNewItem("item " + i);
        }
    }
}