package com.srtianxia.bleattendance.ui.student.beforeattendance;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.StuAttInfoEntity;
import com.srtianxia.bleattendance.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 梅梅 on 2017/2/11.
 */
public class StuBeforeAttendanceFragment extends BaseFragment implements StuBeforeAttendancePresenter.IStuBeforeAttView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh_stu_before)SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler_view_stu_before)RecyclerView mRecyclerView;
    @BindView(R.id.linearlayout_request_error_info)LinearLayout mRequestError;
    @BindView(R.id.tv_request_again)TextView mRequestAgain;

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
    }

    public void loading(){
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void showFailureForNetWork() {
        mRecyclerView.setVisibility(View.GONE);
        mRequestError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFailureForNetWork() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mRequestError.setVisibility(View.GONE);
    }

    public void loadFinish(){
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.requestStuAttForNet();
        loadFinish();
    }

    @OnClick(R.id.tv_request_again)
    void doRequestAgain(){
        onRefresh();
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
