package com.srtianxia.bleattendance.ui.student.attendance;

import android.content.Context;

import com.srtianxia.bleattendance.BleApplication;
import com.srtianxia.rxperipheral.RxPeripheralAdvResult;
import com.srtianxia.rxperipheral.RxPeripheralClient;
import com.srtianxia.rxperipheral.RxPeripheralConnection;
import com.srtianxia.bleattendance.utils.ToastUtil;

import rx.functions.Action1;
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
    public void startAdvertise() {
        mPeripheralClient.advertise("")
                .takeUntil(mDisconnectTriggerSubject)
                .subscribe(new Action1<RxPeripheralAdvResult>() {
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
        mDisconnectTriggerSubject.onNext(null);
    }
}
