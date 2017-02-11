package com.srtianxia.bleattendance.ui.teacher.beforeattendance;

import android.os.Bundle;

import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.base.view.BaseListFragment;
import com.srtianxia.bleattendance.entity.AttInfoEntity;

/**
 * Created by 梅梅 on 2017/2/10.
 */
public class AttendInfoFragment extends BaseListFragment implements AttendInfoPresenter.AttendInfoView{

    private AttendInfoAdapter mAdapter = new AttendInfoAdapter();

    private AttendInfoPresenter mPresenter = new AttendInfoPresenter(this);

    @Override
    protected void initView() {
        super.initView();
        loadListData();
    }

    @Override
    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void loadListData() {
        AttInfoEntity firstEntity = ((TeaBeforeAttendanceFragment)getParentFragment()).getAttInfoEntity();
        AttInfoEntity secondEntity = mPresenter.absenceFiler(firstEntity);

        loadDataList(mPresenter.sortForAbsence(secondEntity.data));
        loadFinish();
    }

    @Override
    public void onRefresh() {

    }

    public void onAttInfoRefresh(){
        getAdapter().clearData();
        loadListData();
    }

    private void loadFinish(){
        loadFinished();
    }

    public static AttendInfoFragment newInstance(){
        Bundle bundle = new Bundle();
        AttendInfoFragment fragment = new AttendInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
