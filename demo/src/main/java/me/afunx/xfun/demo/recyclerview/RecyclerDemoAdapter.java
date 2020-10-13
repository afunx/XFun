package me.afunx.xfun.demo.recyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.GoalRow;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.afunx.xfun.demo.R;

public class RecyclerDemoAdapter extends RecyclerView.Adapter<RecyclerDemoAdapter.DemoViewHolder> {

    private static final String TAG = "RecyclerDemoAdapter";

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
    public void onBindViewHolder(@NonNull final DemoViewHolder holder, final int position) {
        holder.tvItemName.setText(itemNameList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "item: " + itemNameList.get(position) + " is clicked");
                holder.expand();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemNameList.size();
    }

    private static ValueAnimator createDropAnim(final  View view, int start, int end, final int mHiddenViewMeasuredHeight) {
        final ValueAnimator va = ValueAnimator.ofInt(start, end);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();//根据时间因子的变化系数进行设置高度
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;

                float alpha = ((float)value) / mHiddenViewMeasuredHeight;
//                alpha = 1;
//                layoutParams.height = 120;

                Log.d(TAG, "alpha: " + alpha + ", height:" + layoutParams.height);
                view.setAlpha(alpha);

                view.setLayoutParams(layoutParams);//设置高度
                if (view.getVisibility() != View.VISIBLE) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
        return  va;
    }

    class DemoViewHolder extends RecyclerView.ViewHolder{

        private TextView tvItemName;
        private TextView tvSubTitle;
        private View itemView;


        private int getTargetHeight(View v) {
            try {
                Method m = v.getClass().getDeclaredMethod("onMeasure", int.class,
                        int.class);
                m.setAccessible(true);
                m.invoke(v, View.MeasureSpec.makeMeasureSpec(
                        ((View) v.getParent()).getMeasuredWidth(),
                        View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));
            } catch (Exception e) {

            }
            return v.getMeasuredHeight();
        }

        private void animOpen(final View view){
            Log.e(TAG, "animOpen()");
//            view.setVisibility(View.VISIBLE);
//            view.setAlpha(0);
//            if (mOpenValueAnimator == null) {
//                mOpenValueAnimator = createDropAnim(view,0, mHiddenViewMeasuredHeight);
//            }
//            mOpenValueAnimator.start();

//            view.setVisibility(View.VISIBLE);
//            view.measure(0, 0);
            view.setAlpha(0);
            view.setVisibility(View.VISIBLE);
            int height = getTargetHeight(view);
            ValueAnimator dropAnim = createDropAnim(view, 0, height, height);
            dropAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.e(TAG, "animOpen() onAnimationEnd");
                }
            });
            dropAnim.setDuration(2000);
            dropAnim.start();
        }

        public void expand() {
            if (tvSubTitle.getVisibility() == View.VISIBLE) {
                tvSubTitle.setVisibility(View.GONE);
            } else {
//                tvSubTitle.setVisibility(View.VISIBLE);
                animOpen(tvSubTitle);
            }
        }

        public DemoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            tvSubTitle = itemView.findViewById(R.id.tv_second_item);
        }
    }
}
