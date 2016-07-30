package com.srtianxia.blelibs;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import com.srtianxia.blelibs.callback.OnScanCallback;
import java.util.List;

/**
 * Created by srtianxia on 2016/7/14.
 * BLECentral
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BLECentral extends BaseBlueTooth {
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothGatt mBluetoothGatt;

    private OnScanCallback mOnScanCallback;


    public void setOnScanCallback(OnScanCallback mOnScanCallback) {
        this.mOnScanCallback = mOnScanCallback;
    }


    private ScanCallback mScanCallback = new ScanCallback() {
        /**
         *
         * @param callbackType
         * Determines how this callback was triggered. Could be one of
         *            {@link ScanSettings#CALLBACK_TYPE_ALL_MATCHES},
         *            {@link ScanSettings#CALLBACK_TYPE_FIRST_MATCH} or
         *            {@link ScanSettings#CALLBACK_TYPE_MATCH_LOST}
         *
         * @param result
         * {@link ScanResult} 包括这些参数
         * {@link BluetoothDevice Remote bluetooth device that is found.}
         * {@link ScanRecord Scan record including both advertising data and scan response data.}
         * long timestampNanos Device timestamp when the scan result was observed.
         */
        @Override public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            mOnScanCallback.onScanResult(callbackType, result);
        }


        @Override public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            mOnScanCallback.onBatchScanResults(results);
        }


        @Override public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            mOnScanCallback.onScanFailed(errorCode);
        }
    };


    public BLECentral(Context context) {
        super(context);
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
    }


    public void startScan() {
        mBluetoothLeScanner.startScan(mScanCallback);
    }


    public void startScan(List<ScanFilter> scanFilters, ScanSettings scanSettings) {
        mBluetoothLeScanner.startScan(scanFilters, scanSettings, mScanCallback);
    }


    //public static ScanFilter createScanFilter() {
    //
    //}
    //
    //public static ScanSettings createScanSettings() {
    //
    //}

    //private BLECentral() {
    //
    //}
    //
    //public static BLECentral getCentral() {
    //    return SingleHolder.mBLECentral;
    //}
    //
    //private static class SingleHolder {
    //    private static final BLECentral mBLECentral = new BLECentral();
    //}
}
