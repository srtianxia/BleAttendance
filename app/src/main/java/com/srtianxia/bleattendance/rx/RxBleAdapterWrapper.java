package com.srtianxia.bleattendance.rx;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import com.srtianxia.bleattendance.config.BleUUID;
import java.util.UUID;

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


    public void startAdvertise(AdvertiseCallback callback) {
        bluetoothAdapter.getBluetoothLeAdvertiser().startAdvertising(new AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
            .setConnectable(true)
            .build(), new AdvertiseData.Builder()
            .setIncludeTxPowerLevel(true)
            .addServiceUuid(new ParcelUuid(UUID.fromString(BleUUID.ATTENDANCE_SERVICE_UUID)))
            .build(), callback);
    }


    public void stopAdvertise(AdvertiseCallback callback) {
        bluetoothAdapter.getBluetoothLeAdvertiser().stopAdvertising(callback);
    }

}

