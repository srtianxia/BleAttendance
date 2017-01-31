package com.srtianxia.bleattendance.ui.student.attendance;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;

import com.srtianxia.bleattendance.BleApplication;
import com.srtianxia.bleattendance.utils.ToastUtil;
import com.srtianxia.rxperipheral.RxPeripheralAdvResult;
import com.srtianxia.rxperipheral.RxPeripheralClient;
import com.srtianxia.rxperipheral.RxPeripheralConnection;

import rx.subjects.PublishSubject;

/**
 * Created by srtianxia on 2017/1/16.
 */

public class StuRxAttModel implements IStuAttModel {
    private RxPeripheralClient mPeripheralClient;
    private RxPeripheralAdvResult mAdvResult;
    private PublishSubject<Void> mDisconnectTriggerSubject = PublishSubject.create();


    public StuRxAttModel(Context context) {
        this.mPeripheralClient = RxPeripheralClient.create(context);

    }

    @Override
    public IStuAttModel getInstance(Context context) {
        return new StuRxAttModel(context);
    }

    @Override
    public void initBle(String uuid) {

    }

    @Override
    public void startAdvertise() {
        mPeripheralClient.advertise("")
                .takeUntil(mDisconnectTriggerSubject)
                .subscribe(this::advertiseResult);
    }


    @Override
    public void stopAdvertise() {
        mDisconnectTriggerSubject.onNext(null);
    }

    @Override
    public void notifyCenter(BluetoothGattCharacteristic characteristic) {

    }

    @Override
    public void onDestroy() {

    }

    private void advertiseResult(RxPeripheralAdvResult rxPeripheralAdvResult) {
        mAdvResult = rxPeripheralAdvResult;
        mAdvResult.initConnection();
        mAdvResult.observeConnectionStateChanges().subscribe(this::observeConnectionStateChanges);
    }

    private void observeConnectionStateChanges(RxPeripheralConnection.ConnectionState connectionState) {
        ToastUtil.show(BleApplication.getContext(), connectionState.toString(), true);
    }
}
