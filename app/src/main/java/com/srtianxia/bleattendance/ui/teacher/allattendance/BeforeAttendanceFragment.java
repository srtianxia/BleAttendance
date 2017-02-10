package com.srtianxia.bleattendance.ui.teacher.allattendance;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.AttInfoEntity;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class BeforeAttendanceFragment extends BaseFragment implements BeforeAttendancePresenter.IBeforeAttendanceView{

    @BindView(R.id.framelayout_before)FrameLayout mFrameLayout;
    @BindView(R.id.recycler_view_before)RecyclerView mRecyclerView;

    private BeforeAttendancePresenter mPresenter = new BeforeAttendancePresenter(this);

    private AttendInfoFragment mAttInfoFragment = AttendInfoFragment.newInstance();

    private BeforeAttendanceAdapter mAdapter = new BeforeAttendanceAdapter();

    private AttInfoEntity mAttInfoEntity;

    @Override
    protected void initView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadData(mPresenter.getData());

        getChildFragmentManager().beginTransaction()
                .add(R.id.framelayout_before,mAttInfoFragment)
                .hide(mAttInfoFragment)
                .commit();

        mAdapter.setOnBeforeAttItemClickListener(new BeforeAttendanceAdapter.OnBeforeAttItemClickListener() {
            @Override
            public void onClick(int position, String jxbID) {
                mRecyclerView.setVisibility(View.INVISIBLE);
                mFrameLayout.setVisibility(View.VISIBLE);
                mPresenter.requestAttInfoForNet(jxbID);
                getChildFragmentManager().beginTransaction()
                        .show(mAttInfoFragment)
                        .commit();
            }
        });

    }

    public void showAttInfoFragment(){
        getChildFragmentManager().beginTransaction()
                .show(mAttInfoFragment)
                .commit();
    }

    public void setAttInfoEntity(AttInfoEntity entity){
        mAttInfoEntity = entity;
    }

    public AttInfoEntity getAttInfoEntity(){
        if (mAttInfoEntity != null){
            return mAttInfoEntity;
        }
        return null;
    }

    @Override
    public void loadFinish() {

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
