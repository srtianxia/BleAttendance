package com.srtianxia.bleattendance.base.view;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import java.util.List;

/**
 * Created by srtianxia on 2016/7/31.
 */
public abstract class BaseListFragment<T> extends BaseFragment {
    @BindView(R.id.rv_base) RecyclerView rvBase;
    @BindView(R.id.pb_loading) ContentLoadingProgressBar pbLoading;

    private BaseAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;


    @Override protected void initView() {
        pbLoading.setVisibility(View.VISIBLE);
        rvBase.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(getActivity(),
            LinearLayoutManager.VERTICAL, false));
        mAdapter = getAdapter();
        rvBase.setAdapter(mAdapter);
    }


    protected abstract BaseAdapter getAdapter();

    //加载数据的时候调用
    protected void loadData(List<T> data) {
        pbLoading.setVisibility(View.GONE);
        mAdapter.addData(data);
    }

    @Override protected int getLayoutRes() {
        return R.layout.fragment_base_list;
    }
}
