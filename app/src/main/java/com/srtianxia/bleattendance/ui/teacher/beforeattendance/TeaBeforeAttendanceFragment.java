package com.srtianxia.bleattendance.ui.teacher.beforeattendance;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.BleApplication;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.entity.TeaCourse;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;
import com.srtianxia.bleattendance.utils.NetWorkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.linearlayout_request_error_info)
    LinearLayout mReqeustErrorInfo;
    @BindView(R.id.tv_request_again)
    TextView mRequestAgain;

    private TeaBeforeAttendancePresenter mPresenter = new TeaBeforeAttendancePresenter(this);

    private TeaBeforeAttendanceAdapter mAdapter = new TeaBeforeAttendanceAdapter();

    private AttendInfoFragment mAttInfoFragment = AttendInfoFragment.newInstance();

    private AttInfoEntity mAttInfoEntity = null;

    private String mJxbID;
    private Boolean isShowAttInfoFragment = false;  //判断attInfofragment是否正在显示
    private Boolean havaAttInfoFragment = false;    //判断是否已经add过fragment


    @Override
    protected void initView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getData();

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
        mRecyclerView.setVisibility(View.GONE);
        mReqeustErrorInfo.setVisibility(View.GONE);
        addAttInfoFragment();

    }

    @Override
    public void showAttInfoFailure() {
        mRecyclerView.setVisibility(View.GONE);
        mReqeustErrorInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTeaDataFailure() {
        mReqeustErrorInfo.setVisibility(View.VISIBLE);
    }

    private void addAttInfoFragment() {

        if (!havaAttInfoFragment) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.framelayout_before, mAttInfoFragment)
                    .show(mAttInfoFragment)
                    .commit();
            havaAttInfoFragment = true;
            isShowAttInfoFragment = true;
        } else {
            getChildFragmentManager().beginTransaction()
                    .show(mAttInfoFragment)
                    .commit();
            isShowAttInfoFragment = true;
            mAttInfoFragment.loadData();
        }

        /*if (mAttInfoEntity != null){//不为空说明请求数据成功
            mAttInfoFragment.loadData();
        }*/

    }

    public void saveAttInfoEntity(AttInfoEntity entity) {
        mAttInfoEntity = entity;
    }

    /**
     * 返回详细信息 AttInfoEntity 给 AttendInfoFragment
     *
     * @return
     */
    public AttInfoEntity getAttInfoEntity() {
        if (mAttInfoEntity != null) {
            return mAttInfoEntity;
        }
        return null;
    }

    public void updataAttInfo() {
        mPresenter.requestAttInfoForNet(mJxbID);
    }

    public void loading() {
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void loadFinish() {
        mSwipeRefresh.setRefreshing(false);
    }

    public void showBeforeAttFragment() {

        mRecyclerView.setVisibility(View.VISIBLE);
        mReqeustErrorInfo.setVisibility(View.GONE);

        getChildFragmentManager().beginTransaction()
                .hide(mAttInfoFragment)
                .commit();

        isShowAttInfoFragment = false;
    }

    public void loadData(List<TeaCourse> teaCourseList) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mReqeustErrorInfo.setVisibility(View.GONE);
        mAdapter.loadData(teaCourseList);
    }

    @Override
    public void showFailureForNetWork() {
        mReqeustErrorInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCourseName() {
        mRecyclerView.setVisibility(View.GONE);
    }


    public boolean isShowAttInfoFragment() {

        return isShowAttInfoFragment;
    }

    @Override
    public void onRefresh() {

        /*if (!isShowAttInfoFragment() && mJxbID.equals("")) {    //在第一个fragment，并且课程名还没出来的情况
            mPresenter.getData();
        }
        if (!isShowAttInfoFragment() && !mJxbID.equals("")){    //在第一个fragment，课程名出来了，点击但是没网加载的情况
            mPresenter.requestAttInfoForNet(mJxbID);
        }
        if (isShowAttInfoFragment()){   //在第二个fragment，实现他本身的刷新
            mAttInfoFragment.updataAttInfo();
        }*/
        if (!NetWorkUtils.isNetworkConnected(BleApplication.getContext())){
            mReqeustErrorInfo.setVisibility(View.GONE);
        }else {
            if (!isShowAttInfoFragment()){
                mPresenter.getData();
            }
            if (isShowAttInfoFragment()){
                mPresenter.requestAttInfoForNet(mJxbID);
            }

        }

        loadFinish();

    }

    @OnClick(R.id.tv_request_again)
    void doRequestAgain() {

        /*if (mJxbID.equals("")){
            mPresenter.getData();
        }else {
            mPresenter.requestAttInfoForNet(mJxbID);
        }
*/
        if (isShowAttInfoFragment()){
            mPresenter.requestAttInfoForNet(mJxbID);
        }
        if (!isShowAttInfoFragment()){
            mPresenter.getData();
        }

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
