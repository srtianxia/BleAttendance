package com.srtianxia.bleattendance.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.entity.DeviceEntity;
import java.util.List;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherAdapter extends BaseAdapter<DeviceEntity> implements View.OnClickListener{
    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TeacherViewHolder) {
            TeacherViewHolder viewHolder = (TeacherViewHolder) holder;
            viewHolder.setData(getDataController().getData(position));
            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        mOnItemClickListener.onClick(position);
                    }
                });
            }
        }
    }


    @Override protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new TeacherViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false
            ));
    }


    @Override public void loadData(List<DeviceEntity> mData) {
        super.loadData(mData);
    }


    @Override public void addData(DeviceEntity data) {
        super.addData(data);
    }


    @Override public void onClick(View view) {

    }


    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_device_name) TextView tvDeviceName;
        @BindView(R.id.tv_device_address) TextView tvDeviceAddress;


        public TeacherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(DeviceEntity entity) {
            tvDeviceName.setText(entity.name);
            tvDeviceAddress.setText(entity.address);
        }
    }
}
