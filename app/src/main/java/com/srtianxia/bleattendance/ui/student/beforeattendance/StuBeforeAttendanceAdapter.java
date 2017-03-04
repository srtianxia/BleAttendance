package com.srtianxia.bleattendance.ui.student.beforeattendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.entity.StuAttInfoEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 梅梅 on 2017/2/11.
 */
public class StuBeforeAttendanceAdapter extends RecyclerView.Adapter{

    private List<StuAttInfoEntity.ShowData> dataList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new stuBeforeAttViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_stu_before_att,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        stuBeforeAttViewHolder viewHolder = (stuBeforeAttViewHolder) holder;
        viewHolder.setData(position);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void loadData(List<StuAttInfoEntity.ShowData> dataList){
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public class stuBeforeAttViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_before_att_course)TextView mCourseName;
        @BindView(R.id.tv_before_att_num)TextView mAttendance;

        public stuBeforeAttViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(int position){
            mCourseName.setText(dataList.get(position).course);
            mAttendance.setText(dataList.get(position).att_num+"");
        }

    }
}
