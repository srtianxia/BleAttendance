package com.srtianxia.bleattendance.rx;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class RxAdvertiserOperation extends RxPeripheralOperation<RxPeripheralAdvResult> {
    private RxBleAdapterWrapper mRxBleAdapterWrapper;


    private final AdvertiseCallback mCallback = new AdvertiseCallback() {
        @Override public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            onNext(new RxPeripheralAdvResult());
        }


        @Override public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            onError(new Exception("error code" + errorCode));
        }
    };

    public RxAdvertiserOperation(RxBleAdapterWrapper mRxBleAdapterWrapper) {
        this.mRxBleAdapterWrapper = mRxBleAdapterWrapper;
    }


    @Override protected void protectedRun() {
        mRxBleAdapterWrapper.startAdvertise(mCallback);
    }

    public void stopAdvertise() {
        mRxBleAdapterWrapper.stopAdvertise(mCallback);
    }
}
