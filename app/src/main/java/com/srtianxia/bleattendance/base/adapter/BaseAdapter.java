package com.srtianxia.bleattendance.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by srtianxia on 2016/7/31.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public DataController<T> mDataController = new DataController<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override
    public int getItemCount() {
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
        if (!mDataController.isContains(data)) {
            mDataController.addData(data);
//            if (mDataController.getDataSize() == 0) {
//                notifyItemInserted(0);
//            } else {
//                notifyItemInserted(mDataController.getDataSize() - 1);
//            }
            // TODO: 2017/1/18   要改成上面的写法才会触发动画
            notifyDataSetChanged();
        }
    }

    public List<T> getDataList() {
        return mDataController.getDataList();
    }

    public void clearData() {
        mDataController.clearData();
        notifyDataSetChanged();
    }


    public DataController<T> getDataController() {
        return mDataController;
    }
}
