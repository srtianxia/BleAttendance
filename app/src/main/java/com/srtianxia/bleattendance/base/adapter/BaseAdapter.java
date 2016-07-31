package com.srtianxia.bleattendance.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.srtianxia.bleattendance.base.viewholder.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by srtianxia on 2016/7/31.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mData;
    protected Context mContext;


    public BaseAdapter(Context mContext) {
        this.mContext = mContext;
        this.mData = new ArrayList<>();
    }


    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override public int getItemCount() {
        return mData.size();
    }

    protected abstract BaseViewHolder createHolder(ViewGroup parent, int viewType);

    //initData
    public void loadData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    //addData
    public void addData(T data) {
        mData.add(data);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }
}
