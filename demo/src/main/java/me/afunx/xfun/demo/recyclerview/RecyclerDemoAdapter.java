package me.afunx.xfun.demo.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.afunx.xfun.demo.R;

public class RecyclerDemoAdapter extends RecyclerView.Adapter<RecyclerDemoAdapter.DemoViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<String> itemNameList = new ArrayList<>();


    public RecyclerDemoAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void addNewItem(String itemName) {
        itemNameList.add(itemName);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DemoViewHolder(layoutInflater.inflate(R.layout.demo_recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DemoViewHolder holder, int position) {
        holder.tvItemName.setText(itemNameList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemNameList.size();
    }

    static class DemoViewHolder extends RecyclerView.ViewHolder{

        private TextView tvItemName;

        public DemoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tv_item_name);
        }
    }
}
