package com.srtianxia.bleattendance.ui.teacher.beforeattendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.entity.AttInfoEntity;

/**
 * Created by 梅梅 on 2017/2/10.
 */
public class AttendInfoAdapter extends BaseAdapter<AttInfoEntity.AttInfo>{

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new AttendInfoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_att_info,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AttendInfoViewHolder viewHolder = (AttendInfoViewHolder) holder;
        viewHolder.setData(getDataController().getData(position));
    }

    public class AttendInfoViewHolder extends RecyclerView.ViewHolder{

        TextView mName;
        TextView mNum;
        TextView mWeek;

        public AttendInfoViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_search_att_info_name);
            mNum = (TextView) itemView.findViewById(R.id.tv_search_att_info_num);
            mWeek = (TextView) itemView.findViewById(R.id.tv_search_att_info_week);
        }

        public void setData(AttInfoEntity.AttInfo info){
            mName.setText(info.stuName);
            mNum.setText(info.stuNum);
            mWeek.setText(info.absence + "");
        }
    }

}
