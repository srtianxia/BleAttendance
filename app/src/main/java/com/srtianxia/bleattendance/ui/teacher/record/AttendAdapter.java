package com.srtianxia.bleattendance.ui.teacher.record;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.entity.AttendEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 梅梅 on 2016/9/13.
 */
public class AttendAdapter extends BaseAdapter {
    private final String TAG = "AttendAdapter";
    List<AttendEntity.Absences> absences;


    public AttendAdapter(List<AttendEntity.Absences> absences) {
        this.absences = absences;
        loadData(absences);
        Log.i(TAG, absences.size() + "");
    }


    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "createHolder");

        return new AttendViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_attend, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");
        AttendViewHolder viewHolder = (AttendViewHolder) holder;
        viewHolder.setData(absences.get(position));
    }


    public static class AttendViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_attend_name) TextView tvattendname;
        @BindView(R.id.tv_attend_num) TextView tvattendnum;


        public AttendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(AttendEntity.Absences absences) {
            tvattendname.setText(absences.stuName);
            tvattendnum.setText("" + absences.stuNum);
        }
    }
}
