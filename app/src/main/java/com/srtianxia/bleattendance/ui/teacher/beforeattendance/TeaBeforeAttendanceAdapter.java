package com.srtianxia.bleattendance.ui.teacher.beforeattendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.entity.TeaCourse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 梅梅 on 2017/2/10.
 */
public class TeaBeforeAttendanceAdapter extends RecyclerView.Adapter{

    private OnBeforeAttItemClickListener onBeforeAttItemClickListener;

    List<TeaCourse> mData = new ArrayList<>();

    public void setOnBeforeAttItemClickListener(OnBeforeAttItemClickListener onBeforeAttItemClickListener){
        this.onBeforeAttItemClickListener = onBeforeAttItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BeforeAttendanceViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_search_class,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BeforeAttendanceViewHolder viewHolder = (BeforeAttendanceViewHolder) holder;
        viewHolder.setData(position);

        if (onBeforeAttItemClickListener != null){
            viewHolder.itemView.setOnClickListener(
                    view -> onBeforeAttItemClickListener.onClick(position,mData.get(position).jxbID));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void loadData(List<TeaCourse> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public class BeforeAttendanceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_search_class)TextView course_class;

        public BeforeAttendanceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(int position){
            course_class.setText(mData.get(position).course_class);
        }
    }

    public interface OnBeforeAttItemClickListener{
        void onClick(int position,String jxbID);
    }
}
