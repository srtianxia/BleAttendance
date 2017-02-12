package com.srtianxia.bleattendance.ui.teacher.beforeattendance;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.entity.TeaCourse;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;
import com.srtianxia.bleattendance.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class TeaBeforeAttendanceFragment extends BaseFragment implements TeaBeforeAttendancePresenter.IBeforeAttendanceView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.framelayout_before)
    FrameLayout mFrameLayout;
    @BindView(R.id.recycler_view_before)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_tea_before)
    SwipeRefreshLayout mSwipeRefresh;

    private TeaBeforeAttendancePresenter mPresenter = new TeaBeforeAttendancePresenter(this);

    private TeaBeforeAttendanceAdapter mAdapter = new TeaBeforeAttendanceAdapter();

    private AttendInfoFragment mAttInfoFragment;

    private AttInfoEntity mAttInfoEntity;

    private String mJxbID;
    private Boolean isShowAttInfoFragment = false;
    private Boolean havaAttInfoFragment = false;


    @Override
    protected void initView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.loadData(mPresenter.getData());
        mPresenter.requestTeaDataForNet("0");

        mSwipeRefresh.setOnRefreshListener(this);

        mAdapter.setOnBeforeAttItemClickListener(new TeaBeforeAttendanceAdapter.OnBeforeAttItemClickListener() {
            @Override
            public void onClick(int position, String jxbID) {
                mJxbID = jxbID;
                mPresenter.requestAttInfoForNet(jxbID);

            }
        });

    }

    /**
     * todo：因为网络请求后才有数据：mAttInfoEntity，所以此时才能newInstance()
     */
    public void showAttInfoFragment() {

        ((TeacherHomeActivity) getActivity()).showHome();

        mRecyclerView.setVisibility(View.INVISIBLE);

        if (!havaAttInfoFragment) {
            mAttInfoFragment = AttendInfoFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.framelayout_before, mAttInfoFragment)
                    .commit();
            havaAttInfoFragment = true;
            isShowAttInfoFragment = true;
        } else {
            getChildFragmentManager().beginTransaction()
                    .show(mAttInfoFragment)
                    .commit();
            mAttInfoFragment.loadData();
            isShowAttInfoFragment = true;
        }

    }

    public void saveAttInfoEntity(AttInfoEntity entity) {
        mAttInfoEntity = entity;
    }

    public AttInfoEntity getAttInfoEntity() {
        if (mAttInfoEntity != null) {
            return mAttInfoEntity;
        }
        return null;
    }

    @Override
    public void showFailure() {
        ToastUtil.show(getActivity(), getResources().getString(R.string.request_error_for_net), true);
    }

    public void loading(){
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void loadFinish() {
        mSwipeRefresh.setRefreshing(false);
    }

    public void showBeforeAttFragment() {

        mRecyclerView.setVisibility(View.VISIBLE);

        getChildFragmentManager().beginTransaction()
                .hide(mAttInfoFragment)
                .commit();

        isShowAttInfoFragment = false;
    }

    public void loadData(List<TeaCourse> teaCourseList) {
        mAdapter.loadData(teaCourseList);
    }

    public boolean isShowAttInfoFragment() {

        return isShowAttInfoFragment;
    }

    @Override
    public void onRefresh() {

        if (!isShowAttInfoFragment()) {
            mPresenter.requestTeaDataForNet("0");

        } else {
            mAttInfoFragment.onRefresh();
        }
        loadFinish();

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tea_before_attendance;
    }

    public static TeaBeforeAttendanceFragment newInstance() {
        Bundle bundle = new Bundle();
        TeaBeforeAttendanceFragment allAttendanceFragment = new TeaBeforeAttendanceFragment();
        allAttendanceFragment.setArguments(bundle);
        return allAttendanceFragment;
    }
}
