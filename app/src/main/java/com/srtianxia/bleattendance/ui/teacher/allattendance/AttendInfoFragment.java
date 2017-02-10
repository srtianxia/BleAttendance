package com.srtianxia.bleattendance.ui.teacher.allattendance;

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
    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void loadListData() {
        AttInfoEntity firstEntity = ((BeforeAttendanceFragment)getParentFragment()).getAttInfoEntity();
        AttInfoEntity secondEntity = mPresenter.absenceFiler(firstEntity);

        loadDataList(mPresenter.sortForAbsence(secondEntity.data));
        loadFinish();
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
