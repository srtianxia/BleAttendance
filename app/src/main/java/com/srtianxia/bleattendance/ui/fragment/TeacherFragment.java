package com.srtianxia.bleattendance.ui.fragment;

import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.di.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.di.module.TeacherModule;
import com.srtianxia.bleattendance.presenter.TeacherPresenter;
import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherFragment extends BaseFragment
    implements TeacherPresenter.ITeacherView {

    @Inject
    TeacherPresenter mPresenter;


    @Override protected void initView() {
        DaggerTeacherComponent.builder()
            .teacherModule(new TeacherModule(this))
            .build()
            .inject(this);
    }


    @Override protected int getLayoutRes() {
        return 0;
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
