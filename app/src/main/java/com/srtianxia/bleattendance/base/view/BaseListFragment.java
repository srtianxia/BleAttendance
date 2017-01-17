package com.srtianxia.bleattendance.base.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paginate.Paginate;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by srtianxia on 2017/1/16.
 */

public abstract class BaseListFragment<T extends BaseAdapter, E> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, Paginate.Callbacks {
    @BindView(R.id.base_recycler_view)
    RecyclerView baseRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Paginate mPaginate;

    @Override
    protected int getLayoutRes() {
        return R.layout.base_list_fragment;
    }

    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        baseRecyclerView.setAdapter(getAdapter());
        baseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        if (needLoadMore()) {
            mPaginate = Paginate.with(baseRecyclerView, this).build();
        }
    }

    @Override
    public void onRefresh() {
        getAdapter().clearData();
        loadListData();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean isLoading() {
        return false;
    }


    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }


    public void setDataList(List<E> dataList) {
        getAdapter().loadData(dataList);
    }

    public abstract T getAdapter();

    protected abstract void loadListData();

    protected boolean needRefresh() {
        return true;
    }

    protected boolean needLoadMore() {
        return false;
    }

    public void loadFinished() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
