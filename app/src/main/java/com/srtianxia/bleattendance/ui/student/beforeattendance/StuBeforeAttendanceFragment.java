package com.srtianxia.bleattendance.ui.student.beforeattendance;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.StuAttInfoEntity;
import com.srtianxia.bleattendance.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/2/11.
 */
public class StuBeforeAttendanceFragment extends BaseFragment implements StuBeforeAttendancePresenter.IStuBeforeAttView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh_stu_before)SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler_view_stu_before)RecyclerView mRecyclerView;

    private StuBeforeAttendancePresenter mPresenter = new StuBeforeAttendancePresenter(this);

    private StuBeforeAttendanceAdapter mAdapter = new StuBeforeAttendanceAdapter();

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.requestStuAttForNet();

        mSwipeRefresh.setOnRefreshListener(this);
    }

    public void loadData(List<StuAttInfoEntity.ShowData> dataList){
        mAdapter.loadData(dataList);
    }

    public void loadFailure(String throwable){
        ToastUtil.show(getActivity(),getResources().getString(R.string.request_error_for_net),true);
        Log.i("TAG",throwable);
    }

    public void loading(){
        mSwipeRefresh.setRefreshing(true);
    }

    public void loadFinish(){
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.requestStuAttForNet();
        loadFinish();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_stu_before_attendance;
    }

    public static StuBeforeAttendanceFragment newInstance(){
        Bundle bundle = new Bundle();
        StuBeforeAttendanceFragment fragment = new StuBeforeAttendanceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
