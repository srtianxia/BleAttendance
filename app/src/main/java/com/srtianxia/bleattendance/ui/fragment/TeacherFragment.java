package com.srtianxia.bleattendance.ui.fragment;

import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.base.view.BaseListFragment;
import com.srtianxia.bleattendance.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.entity.DeviceEntity;
import com.srtianxia.bleattendance.module.TeacherModule;
import com.srtianxia.bleattendance.presenter.TeacherPresenter;
import com.srtianxia.bleattendance.ui.adapter.TeacherAdapter;
import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherFragment extends BaseListFragment<DeviceEntity>
    implements TeacherPresenter.ITeacherView {

    @Inject
    TeacherPresenter mPresenter;


    @Override protected void initView() {
        super.initView();
        DaggerTeacherComponent.builder()
            .teacherModule(new TeacherModule(this))
            .build()
            .inject(this);
    }


    public static TeacherFragment newInstance() {
        Bundle args = new Bundle();
        TeacherFragment fragment = new TeacherFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void startScanDevice() {
        mPresenter.startScan();
    }


    @Override protected BaseAdapter getAdapter() {
        return new TeacherAdapter(getActivity());
    }


    @Override public void addDeviceInfo(ScanResult scanResult) {

    }


    @Override public void showScanFailure(int errorCode) {

    }


    @Override public void onDestroy() {
        super.onDestroy();
        mPresenter.stopScan();
        mPresenter.detachView();
    }
}
