package com.srtianxia.bleattendance.base.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srtianxia on 2016/9/3.
 */
public class DataController<T> {
    private List<T> mDataList = new ArrayList<>();


    public void addDataList(List<T> data) {
        mDataList.addAll(data);
    }


    public void addData(T data) {
        mDataList.add(data);
    }


    public void setData(List<T> mData) {
        clearData();
        mDataList.addAll(mData);
    }


    public void clearData() {
        mDataList.clear();
    }


    public T getData(int position) {
        return mDataList.get(position);
    }


    public int getDataSize() {
        return mDataList.size();
    }

    public boolean isContains(T data) {
        return mDataList.contains(data);
    }

    public List<T> getDataList() {
        return mDataList;
    }
}
