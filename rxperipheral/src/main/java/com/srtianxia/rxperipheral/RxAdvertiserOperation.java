package com.srtianxia.rxperipheral;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.os.ParcelUuid;

import java.util.UUID;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class RxAdvertiserOperation extends RxPeripheralOperation<RxPeripheralAdvResult> {
    private RxBleAdapterWrapper mRxBleAdapterWrapper;
    private Context mContext;

    private final AdvertiseCallback mCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            onNext(new RxPeripheralAdvResult(settingsInEffect, mContext));
        }


        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            onError(new Exception("error code" + errorCode));
        }
    };

    public RxAdvertiserOperation(RxBleAdapterWrapper mRxBleAdapterWrapper, Context context) {
        this.mRxBleAdapterWrapper = mRxBleAdapterWrapper;
        this.mContext = context;
    }


    @Override
    protected void protectedRun() {
        //TODO 这里的setting和data的初始化可以放在上面的构造函数里
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                .setConnectable(true)
                .build();
        AdvertiseData advData = new AdvertiseData.Builder()
                .setIncludeTxPowerLevel(true)
                .addServiceUuid(new ParcelUuid(UUID.fromString(BleUUID.ATTENDANCE_SERVICE_UUID)))
                .build();
        AdvertiseData advScanResponse = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .build();
        mRxBleAdapterWrapper.startAdvertise(settings, advData, advScanResponse, mCallback);
    }

    public void stopAdvertise() {
        mRxBleAdapterWrapper.stopAdvertise(mCallback);
    }
}
