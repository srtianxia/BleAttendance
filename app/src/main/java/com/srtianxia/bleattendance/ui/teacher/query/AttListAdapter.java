package com.srtianxia.bleattendance.ui.teacher.query;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.OnItemClickListener;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by srtianxia on 2017/1/30.
 */

public class AttListAdapter extends BaseAdapter<String> {
    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AttViewHolder) {
            AttViewHolder viewHolder = (AttViewHolder) holder;
            viewHolder.setData(getDataController().getData(position));
            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(v -> mOnItemClickListener.onClick(position));
            }
        }
    }


    @Override protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new AttViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_child, parent, false));
    }


    @Override public void loadData(List<String> mData) {
        super.loadData(mData);
    }

    @Override
    public void addData(String data) {
        super.addData(data);
    }



    public static class AttViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_child_content)
        TextView tvStuNumber;


        public AttViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(String data) {
            tvStuNumber.setText(data);
        }
    }
}
