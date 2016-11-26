package com.srtianxia.bleattendance.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.ParcelUuid;
import com.srtianxia.bleattendance.BleApplication;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.config.BleUUID;
import com.srtianxia.blelibs.utils.ToastUtil;
import java.util.HashSet;
import java.util.UUID;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StuAttendanceModel {
    private BluetoothGattService mBluetoothGattService;
    private BluetoothGattCharacteristic mWriteCharacteristic;
    private BluetoothGattServer mGattServer;
    private AdvertiseData mAdvData;
    private AdvertiseData mAdvScanResponse;
    private AdvertiseSettings mAdvSettings;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeAdvertiser mAdvertiser;
    private BluetoothManager mBluetoothManager;
    private HashSet<BluetoothDevice> mBluetoothDevices;
    private static final UUID CLIENT_CHARACTERISTIC_CONFIGURATION_UUID = UUID
        .fromString("00002902-0000-1000-8000-00805f9b34fb");
    private static final int EXPENDED_ENERGY_FORMAT = BluetoothGattCharacteristic.FORMAT_UINT16;
    private static final int INITIAL_EXPENDED_ENERGY = 0;

    private final AdvertiseCallback mCallback = new AdvertiseCallback() {
        @Override public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            ToastUtil.show(BleApplication.getContext(),
                "adv success", true);
        }


        @Override public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            int statusText;
            switch (errorCode) {
                case ADVERTISE_FAILED_ALREADY_STARTED:
                    statusText = R.string.status_advertising;
                    break;
                case ADVERTISE_FAILED_DATA_TOO_LARGE:
                    statusText = R.string.status_advDataTooLarge;
                    break;
                case ADVERTISE_FAILED_FEATURE_UNSUPPORTED:
                    statusText = R.string.status_advFeatureUnsupported;
                    break;
                case ADVERTISE_FAILED_INTERNAL_ERROR:
                    statusText = R.string.status_advInternalError;
                    break;
                case ADVERTISE_FAILED_TOO_MANY_ADVERTISERS:
                    statusText = R.string.status_advTooManyAdvertisers;
                    break;
                default:
                    statusText = R.string.status_notAdvertising;
            }
            ToastUtil.show(BleApplication.getContext(),
                BleApplication.getContext().getString(statusText), true);
        }
    };

    private final BluetoothGattServerCallback mGattServerCallback
        = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, final int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    mBluetoothDevices.add(device);
                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    mBluetoothDevices.remove(device);
                }
            } else {
                mBluetoothDevices.remove(device);
            }
        }


        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset,
                                                BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            if (offset != 0) {
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET,
                    offset, null);
                return;
            }
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,
                offset, characteristic.getValue());
        }


        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            super.onNotificationSent(device, status);
        }


        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId,
                                                 BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded,
                                                 int offset, byte[] value) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite,
                responseNeeded, offset, value);
            int status = writeCharacteristic(characteristic, offset, value);
            if (responseNeeded) {
                mGattServer.sendResponse(device, requestId, status,
                    0, null);
            }
        }


        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId,
                                             BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded,
                                             int offset,
                                             byte[] value) {
            super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite,
                responseNeeded,
                offset, value);
            if (responseNeeded) {
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,
                    0, null);
            }
        }
    };


    public StuAttendanceModel() {
        mBluetoothManager = (BluetoothManager) BleApplication.getContext()
            .getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
        mBluetoothGattService = new BluetoothGattService(
            UUID.fromString(BleUUID.ATTENDANCE_SERVICE_UUID),
            BluetoothGattService.SERVICE_TYPE_PRIMARY);
        mWriteCharacteristic = new BluetoothGattCharacteristic(
            UUID.fromString(BleUUID.ATTENDANCE_NOTIFY_WRITE),
            BluetoothGattCharacteristic.PROPERTY_WRITE | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_WRITE);
        mWriteCharacteristic.addDescriptor(getClientCharacteristicConfigurationDescriptor());
        mBluetoothGattService.addCharacteristic(mWriteCharacteristic);
        mAdvSettings = new AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
            .setConnectable(true)
            .build();
        mAdvData = new AdvertiseData.Builder()
            .setIncludeTxPowerLevel(true)
            .addServiceUuid(new ParcelUuid(UUID.fromString(BleUUID.ATTENDANCE_SERVICE_UUID)))
            .build();
        mAdvScanResponse = new AdvertiseData.Builder()
            .setIncludeDeviceName(true)
            .build();

        mGattServer = mBluetoothManager.openGattServer(BleApplication.getContext(),
            mGattServerCallback);
        mGattServer.addService(mBluetoothGattService);
        mBluetoothDevices = new HashSet<>();

    }


    public void startAdvertise() {
        mAdvertiser.startAdvertising(mAdvSettings, mAdvData, mAdvScanResponse, mCallback);
    }


    public static BluetoothGattDescriptor getClientCharacteristicConfigurationDescriptor() {
        return new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_UUID,
            (BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
    }


    private int writeCharacteristic(BluetoothGattCharacteristic characteristic, int offset, byte[] value) {
        if (offset != 0) {
            return BluetoothGatt.GATT_INVALID_OFFSET;
        }
        if (value.length != 1) {
            return BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH;
        }
        if ((value[0] & 1) == 1) {
            Observable.just("").observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
                mWriteCharacteristic.setValue(INITIAL_EXPENDED_ENERGY,
                    EXPENDED_ENERGY_FORMAT, /* offset */ 2);
            });
        }
        return BluetoothGatt.GATT_SUCCESS;
    }

}
