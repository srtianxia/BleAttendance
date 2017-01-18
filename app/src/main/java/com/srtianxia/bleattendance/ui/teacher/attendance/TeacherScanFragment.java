package com.srtianxia.bleattendance.ui.teacher.attendance;

import android.app.Activity;
import android.os.Bundle;

import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.RxBleScanResult;
import com.srtianxia.bleattendance.base.view.BaseListFragment;
import com.srtianxia.bleattendance.di.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.di.module.TeacherModule;

import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherScanFragment extends BaseListFragment<RxBleDevice, TeacherScanAdapter> implements TeacherScanPresenter.ITeacherScanView {
    @Inject
    TeacherScanPresenter mPresenter;
    private TeacherScanAdapter mAdapter = new TeacherScanAdapter();



    public static TeacherScanFragment newInstance() {
        Bundle args = new Bundle();
        TeacherScanFragment fragment = new TeacherScanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ()activity
    }

    @Override
    protected void initView() {
        super.initView();
        DaggerTeacherComponent.builder()
                .teacherModule(new TeacherModule(this))
                .build()
                .inject(this);
        initRecyclerViewAdapter();
    }

    @Override
    public TeacherScanAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void loadListData() {
        // 这里加载一些数据
        //getAdapter().loadData();

        loadFinished();
    }


    private void initRecyclerViewAdapter() {
        mAdapter.setOnItemClickListener(
                position -> mPresenter.tryToConnect(
                        mAdapter.getDataController().getData(position).getMacAddress()));
    }


    @Override
    public void addScanResult(RxBleScanResult rxBleScanResult) {
        addData(rxBleScanResult.getBleDevice());
    }


    @Override
    public void handleScanError(Throwable throwable) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


    //    @OnClick(R.id.fab)
    public void startScan() {
        mPresenter.startScan();
    }


    //    @OnClick(R.id.btn_write)
    public void write() {
        mPresenter.write();
    }

    //    @OnClick(R.id.btn_disconnect)
    public void disconnect() {
        mPresenter.disconnect();
    }

    //    @OnClick(R.id.btn_connect_all)
    public void connectAll() {
        mPresenter.queueToConnect(mAdapter.getDataList());
    }
}
