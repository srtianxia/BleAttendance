package com.srtianxia.bleattendance.model;

import android.content.Context;
import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.RxBleScanResult;
import com.srtianxia.bleattendance.BleApplication;
import com.srtianxia.bleattendance.config.Constant;
import rx.Observable;

/**
 * Created by srtianxia on 2016/8/31.
 */
public class TeacherScanModel {
    private RxBleClient mRxBleClient;


    public TeacherScanModel(Context context) {
        this.mRxBleClient = BleApplication.getRxBleClient(context);
    }


    public Observable<RxBleScanResult> startScan() {
        return mRxBleClient.scanBleDevices();
    }


    public Observable<RxBleConnection> tryToConnect(String macAddress) {
        return mRxBleClient.getBleDevice(macAddress)
            .establishConnection(BleApplication.getContext(), false);
    }

    public RxBleDevice getBleDevice(String macAddress) {
        return mRxBleClient.getBleDevice(macAddress);
    }



}
