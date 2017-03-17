package com.srtianxia.bleattendance.ui.teacher.attendance;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import com.polidea.rxandroidble.RxBleScanResult;
import com.srtianxia.bleattendance.base.view.BaseListFragment;
import com.srtianxia.bleattendance.di.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.di.module.TeacherModule;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;
import com.srtianxia.bleattendance.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherScanFragment extends BaseListFragment<RxBleScanResult, TeacherScanAdapter> implements TeacherScanPresenter.ITeacherScanView {
    @Inject
    TeacherScanPresenter mPresenter;
    private TeacherScanAdapter mAdapter = new TeacherScanAdapter();

    private TeacherHomeActivity mHomeActivity;


    private RxPermissions mRxPermissions; // where this is an Activity instance


    public static TeacherScanFragment newInstance() {
        Bundle args = new Bundle();
        TeacherScanFragment fragment = new TeacherScanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHomeActivity = (TeacherHomeActivity) activity;
    }

    @Override
    protected void initView() {
        super.initView();
        mRxPermissions = new RxPermissions(getActivity());
        DaggerTeacherComponent.builder()
                .teacherModule(new TeacherModule(this))
                .build()
                .inject(this);
        initRecyclerViewAdapter();

        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(aBoolean -> {
                    if (aBoolean) {

                    } else {

                    }
                });
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
                        mAdapter.getDataController().getData(position).getBleDevice().getMacAddress()));
    }


    @Override
    public void addScanResult(RxBleScanResult rxBleScanResult) {
        addData(rxBleScanResult);
    }

    @Override
    public void handleScanError(Throwable throwable) {

    }

    @Override
    public void addAttendanceNumber(String number) {
        mHomeActivity.addNumber(Integer.valueOf(number));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


    public void startScan() {
        if (mHomeActivity.getUuid() != null) {
            mPresenter.startScan(mHomeActivity.getUuid());
        } else {
            ToastUtil.show(getActivity(), "尚未选择考勤课程", true);
        }
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
