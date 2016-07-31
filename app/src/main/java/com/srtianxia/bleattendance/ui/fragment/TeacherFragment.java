package com.srtianxia.bleattendance.ui.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.Bundle;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.base.view.BaseListFragment;
import com.srtianxia.bleattendance.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.config.Constant;
import com.srtianxia.bleattendance.entity.DeviceEntity;
import com.srtianxia.bleattendance.module.TeacherModule;
import com.srtianxia.bleattendance.presenter.TeacherPresenter;
import com.srtianxia.bleattendance.ui.adapter.TeacherAdapter;
import com.srtianxia.blelibs.utils.ToastUtil;
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

        openBlueTooth();
    }


    private void openBlueTooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, Constant.REQUEST_CODE_BLUE_OPEN);
        }
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


    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_BLUE_OPEN) {
            if (resultCode == getActivity().RESULT_OK) {
                ToastUtil.show(getActivity(), "蓝牙已开启", true);
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                ToastUtil.show(getActivity(), "必须使用蓝牙", true);
                getActivity().finish();
            }
        }
    }


    @Override public void onDestroy() {
        super.onDestroy();
        mPresenter.stopScan();
        mPresenter.detachView();
    }
}
