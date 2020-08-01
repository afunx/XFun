package me.afunx.xfun.demo.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import me.afunx.xfun.demo.R;

public class DemoListViewActivity extends AppCompatActivity {

    private static final String TAG = "DemoListViewActivity";

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list_view);
        mListView = findViewById(R.id.lv_demo);
        initListView();
    }

    private void initListView() {
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            titleList.add("title " + i);
        }
        DemoAdapter demoAdapter = new DemoAdapter(getApplicationContext(), titleList);
        mListView.setAdapter(demoAdapter);
    }

    private static class DemoAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;

        private final List<String> titleList;

        private DemoAdapter(final Context context, List<String> titleList) {
            this.layoutInflater = LayoutInflater.from(context);
            this.titleList = titleList;
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public Object getItem(int position) {
            return titleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.demo_list_view_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvTitle.setText(titleList.get(position));
            viewHolder.tvSubtitle.setText(String.format("%s subtitle", titleList.get(position)));
            return convertView;
        }


        private static class ViewHolder {
            TextView tvTitle;
            TextView tvSubtitle;

            static AtomicInteger sCount = new AtomicInteger(0);

            ViewHolder(View root) {
                Log.i(TAG, "ViewHolder " + sCount.incrementAndGet());
                tvTitle = root.findViewById(R.id.tv_title);
                tvSubtitle = root.findViewById(R.id.tv_subtitle);
            }
        }
    }
}
