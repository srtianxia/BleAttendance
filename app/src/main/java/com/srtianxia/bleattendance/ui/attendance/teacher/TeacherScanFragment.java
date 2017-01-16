package com.srtianxia.bleattendance.ui.attendance.teacher;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.RxBleScanResult;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.di.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.di.module.TeacherModule;
import com.srtianxia.bleattendance.ui.adapter.TeacherAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherScanFragment extends BaseFragment implements TeacherScanPresenter.ITeacherScanView {
    @Inject TeacherScanPresenter mPresenter;
    @BindView(R.id.rv_teacher) RecyclerView rvTeacher;
    @BindView(R.id.fab) FloatingActionButton fab;
    private TeacherAdapter mAdapter;


    public static TeacherScanFragment newInstance() {
        Bundle args = new Bundle();
        TeacherScanFragment fragment = new TeacherScanFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override protected void initView() {
        DaggerTeacherComponent.builder()
            .teacherModule(new TeacherModule(this))
            .build()
            .inject(this);
        initRecyclerView();
    }


    private void initRecyclerView() {
        rvTeacher.setAdapter(mAdapter = new TeacherAdapter());
        rvTeacher.setLayoutManager(
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter.setOnItemClickListener(
            position -> mPresenter.tryToConnect(
                mAdapter.getDataController().getData(position).getMacAddress()));
    }


    @Override protected int getLayoutRes() {
        return R.layout.fragment_teacher;
    }


    @Override public void addScanResult(RxBleScanResult rxBleScanResult) {
        mAdapter.addData(rxBleScanResult.getBleDevice());
    }


    @Override public void handleScanError(Throwable throwable) {

    }


    @Override public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


    @OnClick(R.id.fab)
    void startScan() {
        mPresenter.startScan();
    }


    @OnClick(R.id.btn_write)
    void write() {
        mPresenter.write();
    }

    @OnClick(R.id.btn_disconnect)
    void disconnect() {
        mPresenter.disconnect();
    }

    @OnClick(R.id.btn_connect_all)
    void connectAll() {
        List<String> address_list = new ArrayList<>();
        for (RxBleDevice device : mAdapter.getDataController().getDataList()) {
            address_list.add(device.getMacAddress());
        }
        mPresenter.queueToConnect(address_list);
    }
}
