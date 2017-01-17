package com.srtianxia.rxperipheral;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;

import java.util.HashSet;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by srtianxia on 2016/11/26.
 * todo disconnection
 */

public class RxPeripheralAdvResult implements RxPeripheralConnection {
    private static final UUID CLIENT_CHARACTERISTIC_CONFIGURATION_UUID = UUID
            .fromString("00002902-0000-1000-8000-00805f9b34fb");
    private static final int EXPENDED_ENERGY_FORMAT = BluetoothGattCharacteristic.FORMAT_UINT16;
    private static final int INITIAL_EXPENDED_ENERGY = 0;

    private AdvertiseSettings mAdvertiseSettings;
    private BluetoothGattService mGattService;
    private BluetoothGattCharacteristic mWriteCharacteristic;
    private BluetoothGattServer mGattServer;
    private HashSet<BluetoothDevice> mBluetoothDevices;

    private Context mContext;


    private BehaviorSubject<ConnectionState> behaviorSubject = BehaviorSubject.create(ConnectionState.DISCONNECTED);

    public RxPeripheralAdvResult(AdvertiseSettings advertiseSettings, Context context) {
        this.mAdvertiseSettings = advertiseSettings;
        this.mContext = context;
    }

    private final BluetoothGattServerCallback mGattServerCallback
            = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, final int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    mBluetoothDevices.add(device);
                    behaviorSubject.onNext(ConnectionState.CONNECTED);
                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    mBluetoothDevices.remove(device);
                    behaviorSubject.onNext(ConnectionState.DISCONNECTED);
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


    public AdvertiseSettings advertiseSettings() {
        return mAdvertiseSettings;
    }

    public void initConnection() {
        mGattService = new BluetoothGattService(
                UUID.fromString(BleUUID.ATTENDANCE_SERVICE_UUID),
                BluetoothGattService.SERVICE_TYPE_PRIMARY);
        mWriteCharacteristic = new BluetoothGattCharacteristic(
                UUID.fromString(BleUUID.ATTENDANCE_NOTIFY_WRITE),
                BluetoothGattCharacteristic.PROPERTY_WRITE | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_WRITE);
        mWriteCharacteristic.addDescriptor(getClientCharacteristicConfigurationDescriptor());
        mGattService.addCharacteristic(mWriteCharacteristic);

        mGattServer = getBluetoothManager().openGattServer(mContext,
                mGattServerCallback);
        mGattServer.addService(mGattService);
        mBluetoothDevices = new HashSet<>();
    }

    private BluetoothManager getBluetoothManager() {
        return (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
    }


    private BluetoothGattDescriptor getClientCharacteristicConfigurationDescriptor() {
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

    @Override
    public Observable<ConnectionState> observeConnectionStateChanges() {
        return behaviorSubject.distinctUntilChanged();
    }
}
