package com.srtianxia.blelibs;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.text.TextUtils;
import com.srtianxia.blelibs.callback.OnScanCallback;
import com.srtianxia.blelibs.utils.LogUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by srtianxia on 2016/7/14.
 * BLECentral
 */
public class BLECentral extends BaseBlueTooth {
    private BluetoothLeScanner mBluetoothLeScanner;
    //private BluetoothGatt mBluetoothGatt;

    private OnScanCallback mOnScanCallback;
    private Map<String, BluetoothGatt> mGattMap = new HashMap<>();

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            LogUtil.logGattDetail(gatt);
        }


        @Override public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }


        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }
    };


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


    public void stopScan() {
        mBluetoothLeScanner.stopScan(mScanCallback);
    }


    public void connectRemoteDevice(String macAddress) {
        if (!TextUtils.isEmpty(macAddress)) {
            if (!mGattMap.containsKey(macAddress)) {
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);
                BluetoothGatt gatt = device.connectGatt(mContext, true, mGattCallback);
                mGattMap.put(macAddress, gatt);
            }
        }
    }
}
