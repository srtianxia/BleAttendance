package com.srtianxia.bleattendance.ui.fragment;

import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleScanResult;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.di.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.di.module.TeacherModule;
import com.srtianxia.bleattendance.entity.DeviceEntity;
import com.srtianxia.bleattendance.presenter.TeacherPresenter;
import com.srtianxia.bleattendance.ui.adapter.OnItemClickListener;
import com.srtianxia.bleattendance.ui.adapter.TeacherAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherFragment extends BaseFragment implements TeacherPresenter.ITeacherView {

    @Inject
    TeacherPresenter mPresenter;
    @BindView(R.id.rv_teacher) RecyclerView rvTeacher;
    @BindView(R.id.btn_connect) Button btnConnect;
    @BindView(R.id.fab) FloatingActionButton fab;
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

                RxBleClient.create(getActivity())
                    .getBleDevice(mAdapter.getDataController().getData(position).address)
                    .establishConnection(getActivity(), true)
                    .subscribe(
                        new Action1<RxBleConnection>() {
                            @Override public void call(RxBleConnection rxBleConnection) {
                                Logger.d(rxBleConnection.readRssi());
                            }
                        }, new Action1<Throwable>() {
                            @Override public void call(Throwable throwable) {
                                Logger.d(throwable.getMessage());
                            }
                        });
                //mPresenter.startConnect(mAdapter.getDataController().getData(position).address);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanDevice();
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


    @Override public void addScanResult(RxBleScanResult rxBleScanResult) {
        mAdapter.addData(new DeviceEntity("name", rxBleScanResult.getBleDevice().getMacAddress()));
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
