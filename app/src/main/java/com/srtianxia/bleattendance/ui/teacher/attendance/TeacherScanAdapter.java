package com.srtianxia.bleattendance.ui.teacher.attendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleScanResult;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.OnItemClickListener;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherScanAdapter extends BaseAdapter<RxBleScanResult> implements View.OnClickListener {
    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TeacherViewHolder) {
            TeacherViewHolder viewHolder = (TeacherViewHolder) holder;
            viewHolder.setData(getDataController().getData(position));
            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(v -> mOnItemClickListener.onClick(position));
            }
        }
    }


    @Override protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new TeacherViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false));
    }


    @Override public void loadData(List<RxBleScanResult> mData) {
        super.loadData(mData);
    }

    @Override
    public void addData(RxBleScanResult data) {
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


        public void setData(RxBleScanResult result) {
            tvDeviceName.setText(result.getBleDevice().getName());
            tvDeviceAddress.setText("mac地址 : " + result.getBleDevice().getMacAddress() + " Rssi: " + result.getRssi());
        }
    }
}
