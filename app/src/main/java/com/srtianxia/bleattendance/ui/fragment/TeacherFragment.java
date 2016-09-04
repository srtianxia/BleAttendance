package com.srtianxia.bleattendance.ui.fragment;

import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.di.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.di.module.TeacherModule;
import com.srtianxia.bleattendance.entity.DeviceEntity;
import com.srtianxia.bleattendance.presenter.TeacherPresenter;
import com.srtianxia.bleattendance.ui.adapter.OnItemClickListener;
import com.srtianxia.bleattendance.ui.adapter.TeacherAdapter;
import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherFragment extends BaseFragment
    implements TeacherPresenter.ITeacherView {

    @Inject
    TeacherPresenter mPresenter;
    @BindView(R.id.rv_teacher) RecyclerView rvTeacher;
    @BindView(R.id.btn_connect) Button btnConnect;
    private TeacherAdapter mAdapter;

    private int i = 1;


    @Override protected void initView() {
        DaggerTeacherComponent.builder()
            .teacherModule(new TeacherModule(this))
            .build()
            .inject(this);

        rvTeacher.setAdapter(mAdapter = new TeacherAdapter());
        rvTeacher.setLayoutManager(
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override public void onClick(int position) {
                mPresenter.startConnect(mAdapter.getDataController().getData(position).address);
            }
        });
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
        mAdapter.addData(new DeviceEntity("name" + i,
            scanResult.getDevice().getAddress()));
        i++;
    }


    @Override public void showScanFailure(String errorResult) {

    }


    @Override public void onDestroy() {
        super.onDestroy();
        mPresenter.stopScan();
        mPresenter.detachView();
    }

    @OnClick(R.id.btn_connect)
    void connect() {

    }
}
