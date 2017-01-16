package com.srtianxia.bleattendance.rx;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.support.annotation.Nullable;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class RxBleAdapterWrapper {
    private final BluetoothAdapter bluetoothAdapter;


    public RxBleAdapterWrapper(@Nullable BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }


    public BluetoothDevice getRemoteDevice(String macAddress) {
        return bluetoothAdapter.getRemoteDevice(macAddress);
    }


    public boolean hasBluetoothAdapter() {
        return bluetoothAdapter != null;
    }


    public boolean isBluetoothEnabled() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }


    public void startAdvertise(AdvertiseSettings settings, AdvertiseData advData, AdvertiseData advScanResponse, AdvertiseCallback callback) {
        bluetoothAdapter.getBluetoothLeAdvertiser().startAdvertising(settings, advData, advScanResponse, callback);
    }


    public void stopAdvertise(AdvertiseCallback callback) {
        bluetoothAdapter.getBluetoothLeAdvertiser().stopAdvertising(callback);
    }

}

