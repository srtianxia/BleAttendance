package com.srtianxia.bleattendance.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.base.view.BaseItemView;
import com.srtianxia.bleattendance.base.viewholder.BaseViewHolder;
import com.srtianxia.bleattendance.entity.DeviceEntity;
import com.srtianxia.bleattendance.ui.itemview.TeacherItemView;
import java.util.List;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherAdapter extends BaseAdapter<DeviceEntity> {

    public TeacherAdapter(Context mContext) {
        super(mContext);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    @Override protected BaseViewHolder createHolder(ViewGroup parent, int viewType) {
        return new TeacherViewHolder(new TeacherItemView(parent.getContext(), parent));
    }


    @Override public void loadData(List<DeviceEntity> mData) {
        super.loadData(mData);
    }


    @Override public void addData(DeviceEntity data) {
        super.addData(data);
    }


    public static class TeacherViewHolder extends BaseViewHolder<TeacherItemView> {
        public TeacherViewHolder(BaseItemView baseItemView) {
            super(baseItemView);
        }
    }
}
