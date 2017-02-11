package com.srtianxia.bleattendance.base.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by srtianxia on 2017/1/16.
 */

public abstract class BaseListFragment<E, T extends BaseAdapter<E>> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.base_recycler_view)
    RecyclerView baseRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected int getLayoutRes() {
        return R.layout.base_list_fragment;
    }

    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        baseRecyclerView.setAdapter(getAdapter());
        baseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onRefresh() {
        getAdapter().clearData();
        loadListData();
    }


    // 添加一个item数据
    public void addData(E data) {
        getAdapter().addData(data);
    }

    public void loadDataList(List<E> data) {
        getAdapter().addDataList(data);
    }


    public abstract T getAdapter();

    // 从数据源读取数据加载进来  一个列表
    protected abstract void loadListData();

    protected boolean needRefresh() {
        return true;
    }

    protected boolean needLoadMore() {
        return false;
    }

    public void loadFinished() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
