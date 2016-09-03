package com.srtianxia.bleattendance.ui.fragment;

import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.di.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.di.module.TeacherModule;
import com.srtianxia.bleattendance.entity.DeviceEntity;
import com.srtianxia.bleattendance.presenter.TeacherPresenter;
import com.srtianxia.bleattendance.ui.adapter.TeacherAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherFragment extends BaseFragment
    implements TeacherPresenter.ITeacherView {

    @Inject
    TeacherPresenter mPresenter;
    @BindView(R.id.rv_teacher) RecyclerView rvTeacher;
    private TeacherAdapter mAdapter;


    @Override protected void initView() {
        DaggerTeacherComponent.builder()
            .teacherModule(new TeacherModule(this))
            .build()
            .inject(this);

        rvTeacher.setAdapter(mAdapter = new TeacherAdapter());
        rvTeacher.setLayoutManager(
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        List<DeviceEntity> entities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entities.add(new DeviceEntity("name" + i , "address" + i));
        }
        mAdapter.loadData(entities);
    }


    @Override protected int getLayoutRes() {
        return R.layout.fragment_teacher;
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
        mAdapter.addData(new DeviceEntity(scanResult.getDevice().getName(),
            scanResult.getDevice().getAddress()));
    }


    @Override public void showScanFailure(String errorResult) {

    }


    @Override public void onDestroy() {
        super.onDestroy();
        mPresenter.stopScan();
        mPresenter.detachView();
    }

}
