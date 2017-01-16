package com.srtianxia.bleattendance.ui.attendance.student;

import android.content.Context;

import com.srtianxia.bleattendance.BleApplication;
import com.srtianxia.bleattendance.rx.RxPeripheralAdvResult;
import com.srtianxia.bleattendance.rx.RxPeripheralClient;
import com.srtianxia.bleattendance.rx.RxPeripheralConnection;
import com.srtianxia.blelibs.utils.ToastUtil;

import rx.functions.Action1;

/**
 * Created by srtianxia on 2017/1/16.
 */

public class StuRxAttModel implements IStuAttModel {
    private RxPeripheralClient mPeripheralClient;
    private RxPeripheralAdvResult mAdvResult;


    public StuRxAttModel(Context context) {
        this.mPeripheralClient = RxPeripheralClient.create(context);

    }

    @Override
    public IStuAttModel getInstance(Context context) {
        return new StuRxAttModel(context);
    }

    @Override
    public void startAdvertise() {
        mPeripheralClient.advertise("").subscribe(new Action1<RxPeripheralAdvResult>() {
            @Override
            public void call(RxPeripheralAdvResult rxPeripheralAdvResult) {
                ToastUtil.show(BleApplication.getContext(), "3232", true);
                mAdvResult = rxPeripheralAdvResult;
                mAdvResult.initConnection();
                mAdvResult.observeConnectionStateChanges().subscribe(new Action1<RxPeripheralConnection.ConnectionState>() {
                    @Override
                    public void call(RxPeripheralConnection.ConnectionState connectionState) {
                        ToastUtil.show(BleApplication.getContext(), connectionState.toString(), true);
                    }
                });
            }
        });


    }


    @Override
    public void stopAdvertise() {

    }
}
