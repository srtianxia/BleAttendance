package com.srtianxia.blelibs;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.srtianxia.blelibs.callback.OnScanCallback;
import com.srtianxia.blelibs.config.BLEProfile;
import com.srtianxia.blelibs.utils.LogUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by srtianxia on 2016/7/14.
 * BLECentral
 */
public class BLECentral extends BaseBlueTooth {
    private BluetoothLeScanner mBluetoothLeScanner;

    private OnScanCallback mOnScanCallback;
    private Map<String, BluetoothGatt> mGattMap = new HashMap<>();

    private BluetoothGattCharacteristic mWriteChannel;

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

            }
        }


        @Override public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                BluetoothGattService service = gatt.getService(
                    UUID.fromString(BLEProfile.UUID_SERVICE));
                if (service != null) {
                    BluetoothGattCharacteristic characteristic = service.getCharacteristic(
                        UUID.fromString(BLEProfile.UUID_CHARACTERISTIC_NOTIFY));
                    if (characteristic != null) {
                        gatt.setCharacteristicNotification(characteristic, true);
                        for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                            gatt.writeDescriptor(descriptor);
                        }
                    }
                    mWriteChannel = service.getCharacteristic(
                        UUID.fromString(BLEProfile.UUID_CHARACTERISTIC_WRITE));
                }
            }
        }


        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Logger.d(new String(characteristic.getValue()));
        }


        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
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
            if (mOnScanCallback != null) {
                mOnScanCallback.onScanResult(callbackType, result);
                Logger.i("ScanResult" + result.getDevice().getAddress());
            }
        }


        @Override public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            if (mOnScanCallback != null) {
                mOnScanCallback.onScanFailed(errorCode);
            }
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
            //if (!mGattMap.containsKey(macAddress)) {
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);
                BluetoothGatt gatt = device.connectGatt(mContext, true, mGattCallback);
                mGattMap.put(macAddress, gatt);
            //}
        }
    }
}
