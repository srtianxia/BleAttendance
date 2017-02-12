package com.srtianxia.bleattendance.ui.teacher.beforeattendance;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.AttInfoEntity;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/2/10.
 */
public class AttendInfoFragment extends BaseFragment implements AttendInfoPresenter.AttendInfoView{

    @BindView(R.id.recycler_view_tea_att_info)RecyclerView mRecyclerView;

    private AttendInfoAdapter mAdapter = new AttendInfoAdapter();

    private AttendInfoPresenter mPresenter = new AttendInfoPresenter(this);

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        loadData();
    }

    public void loadData() {
        AttInfoEntity firstEntity = ((TeaBeforeAttendanceFragment)getParentFragment()).getAttInfoEntity();
        AttInfoEntity secondEntity = mPresenter.absenceFiler(firstEntity);

        mAdapter.loadData(mPresenter.sortForAbsence(secondEntity.data));
    }

    public void onRefresh(){
        loadData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tea_att_info;
    }

    public static AttendInfoFragment newInstance(){
        Bundle bundle = new Bundle();
        AttendInfoFragment fragment = new AttendInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
