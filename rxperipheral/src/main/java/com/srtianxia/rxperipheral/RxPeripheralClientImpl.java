package com.srtianxia.rxperipheral;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import rx.Observable;
import rx.functions.Action0;

/**
 * Created by srtianxia on 2016/11/26.
 */

class RxPeripheralClientImpl extends RxPeripheralClient {
    private RxBleAdapterWrapper mAdapterWrapper;
    private Context mContext;


    RxPeripheralClientImpl(RxBleAdapterWrapper adapterWrapper, Context context) {
        this.mAdapterWrapper = adapterWrapper;
        this.mContext = context;
    }


    public static RxPeripheralClientImpl getInstance(Context context) {
        final RxBleAdapterWrapper adapterWrapper = new RxBleAdapterWrapper(
                BluetoothAdapter.getDefaultAdapter());
        return new RxPeripheralClientImpl(adapterWrapper, context);
    }


    @Override
    public Observable<RxPeripheralAdvResult> advertise(String uuid) {
        return initAdvertiser(uuid);
    }

    @Override
    public Observable<RxPeripheralConnection> observeConnectionStateChanges() {
        return null;
    }


    private Observable<RxPeripheralAdvResult> initAdvertiser(String uuid) {
        RxAdvertiserOperation rxAdvertiserOperation = new RxAdvertiserOperation(mAdapterWrapper, mContext);
        return rxAdvertiserOperation.asObservable().doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                rxAdvertiserOperation.stopAdvertise();
            }
        }).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                rxAdvertiserOperation.protectedRun();
            }
        });
    }
}
