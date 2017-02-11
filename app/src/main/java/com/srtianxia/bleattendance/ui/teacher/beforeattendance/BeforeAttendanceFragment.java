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
public class BeforeAttendanceFragment extends BaseFragment implements BeforeAttendancePresenter.IBeforeAttendanceView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.framelayout_before)FrameLayout mFrameLayout;
    @BindView(R.id.recycler_view_before)RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_before)SwipeRefreshLayout mSwipeRefresh;

    private BeforeAttendancePresenter mPresenter = new BeforeAttendancePresenter(this);

    private BeforeAttendanceAdapter mAdapter = new BeforeAttendanceAdapter();

    private AttendInfoFragment mAttInfoFragment;


    private AttInfoEntity mAttInfoEntity;

    @Override
    protected void initView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.loadData(mPresenter.getData());
        mPresenter.requestTeaDataForNet("0");

        mSwipeRefresh.setOnRefreshListener(this);

        mAdapter.setOnBeforeAttItemClickListener(new BeforeAttendanceAdapter.OnBeforeAttItemClickListener() {
            @Override
            public void onClick(int position, String jxbID) {
                mRecyclerView.setVisibility(View.INVISIBLE);
                mFrameLayout.setVisibility(View.VISIBLE);
                mPresenter.requestAttInfoForNet(jxbID);

            }
        });

    }

    /**
     * todo：因为网络请求后才有数据：mAttInfoEntity，所以此时才能newInstance()
     */
    public void showAttInfoFragment(){
        mAttInfoFragment = AttendInfoFragment.newInstance();

        ((TeacherHomeActivity)getActivity()).showHome();

        getChildFragmentManager().beginTransaction()
                .add(R.id.framelayout_before,mAttInfoFragment)
                .commit();
    }

    public void saveAttInfoEntity(AttInfoEntity entity){
        mAttInfoEntity = entity;
    }

    public AttInfoEntity getAttInfoEntity(){
        if (mAttInfoEntity != null){
            return mAttInfoEntity;
        }
        return null;
    }

    @Override
    public void showFailure() {
        ToastUtil.show(getActivity(),getResources().getString(R.string.request_error_for_net),true);
    }

    @Override
    public void loadFinish() {
        mSwipeRefresh.setRefreshing(false);
    }

    public void showBeforeAttFragment(){
        getChildFragmentManager().beginTransaction()
                .remove(mAttInfoFragment).commit();
        mFrameLayout.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void loadData(List<TeaCourse> teaCourseList){
        mAdapter.loadData(teaCourseList);
    }

    public boolean isShowAttInfoFragment(){
        if (mFrameLayout.getVisibility() == View.VISIBLE)
            return true;
        else
            return false;
    }

    @Override
    public void onRefresh() {
        mPresenter.requestTeaDataForNet("0");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_before_attendance;
    }

    public static BeforeAttendanceFragment newInstance(){
        Bundle bundle = new Bundle();
        BeforeAttendanceFragment allAttendanceFragment = new BeforeAttendanceFragment();
        allAttendanceFragment.setArguments(bundle);
        return allAttendanceFragment;
    }
}
