package com.srtianxia.bleattendance.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by srtianxia on 2016/7/31.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected DataController<T> mDataController = new DataController<>();

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override public int getItemCount() {
        return mDataController.getDataSize();
    }

    protected abstract RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType);

    //initData
    public void loadData(List<T> mData) {
        mDataController.setData(mData);
        notifyDataSetChanged();
    }

    //addData
    public void addData(T data) {
        if (!mDataController.isContains(data)){
            mDataController.addData(data);
            notifyItemInserted(mDataController.getDataSize() - 1);
        }
    }

    public DataController<T> getDataController() {
        return mDataController;
    }
}
