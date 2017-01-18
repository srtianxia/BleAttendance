package com.srtianxia.bleattendance.ui.student.attendance;

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

import com.orhanobut.logger.Logger;
import com.srtianxia.bleattendance.BleApplication;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.config.BleUUID;
import com.srtianxia.bleattendance.utils.ToastUtil;

import java.util.HashSet;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT32;
import static com.srtianxia.bleattendance.config.Constant.NOTIFY_OFFSET;

/**
 * Created by srtianxia on 2016/11/26.
 * todo 现在的情况是 广播后停止广播再广播 ， 就出现找不到write和notify的uuid的情况 详情看备忘录
 */

public class StuAttendanceModel implements IStuAttModel {
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


    //todo 这里randomUUID 是搜索的时候使用的UUID应该由教师端生产 再post到服务器上
    private static final UUID UUID_SERVICE = UUID.randomUUID();
    private static final UUID UUID_OLD = UUID.fromString(BleUUID.ATTENDANCE_SERVICE_UUID);

    private final AdvertiseCallback mCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            ToastUtil.show(BleApplication.getContext(),
                    "Advertise Success", true);
        }


        @Override
        public void onStartFailure(int errorCode) {
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

    private final BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, final int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                String s = "";
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    mBluetoothDevices.add(device);
//                    notifyCenter(null);
                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    mBluetoothDevices.remove(device);
                }
                Observable.just("")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                ToastUtil.show(BleApplication.getContext(), "newState" + newState, true);
                            }
                        });
            } else {
                mBluetoothDevices.remove(device);
                Observable.just("").observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                ToastUtil.show(BleApplication.getContext(), "GATT FAILURE", true);
                            }
                        });
            }
        }


        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset,
                                                BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            Logger.d("callback --->" + "onCharacteristicReadRequest");

            if (offset != 0) {
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET,
                        offset, null);
                return;
            }
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,
                    offset, characteristic.getValue());
            Observable.just("").observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> ToastUtil.show(BleApplication.getContext(), "onCharacteristicReadRequest", true));
        }


        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            super.onNotificationSent(device, status);
            Logger.d("callback --->" + "onNotificationSent");

            Observable.just("").observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> ToastUtil.show(BleApplication.getContext(), "onNotificationSent", true));
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

            Logger.d("callback --->" + "onCharacteristicWriteRequest");

            Observable.just("").observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> ToastUtil.show(BleApplication.getContext(), "onCharacteristicWriteRequest", true));
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

                notifyCenter(null);
            }

            Logger.d("callback --->" + "onDescriptorWriteRequest");
            Observable.just("").observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            ToastUtil.show(BleApplication.getContext(), "onDescriptorWriteRequest", true);
                        }
                    });
        }
    };


    public StuAttendanceModel() {
        mBluetoothManager = (BluetoothManager) BleApplication.getContext()
                .getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
        mBluetoothGattService = new BluetoothGattService(
                UUID_SERVICE,
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
                .addServiceUuid(new ParcelUuid(UUID_SERVICE))
                .build();
        mAdvScanResponse = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .build();

        mGattServer = mBluetoothManager.openGattServer(BleApplication.getContext(),
                mGattServerCallback);
        mGattServer.addService(mBluetoothGattService);
        mBluetoothDevices = new HashSet<>();

    }

    @Override
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

    @Override
    public void notifyCenter(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothDevices.isEmpty()) {

        } else {
            boolean indicate = (mWriteCharacteristic.getProperties()
                    & BluetoothGattCharacteristic.PROPERTY_INDICATE)
                    == BluetoothGattCharacteristic.PROPERTY_INDICATE;
            for (BluetoothDevice device : mBluetoothDevices) {
                int newEnergyExpended = Integer.parseInt("2014211819");
                mWriteCharacteristic.setValue(newEnergyExpended, FORMAT_UINT32, NOTIFY_OFFSET);
                mGattServer.notifyCharacteristicChanged(device, mWriteCharacteristic, indicate);
            }
        }
    }

    @Override
    public void stopAdvertise() {
        if (mGattServer != null) {
            mGattServer.close();
        }

        if (mBluetoothAdapter.isEnabled() && mAdvertiser != null) {
            mAdvertiser.stopAdvertising(mCallback);
        }
    }

    @Override
    public IStuAttModel getInstance(Context context) {
        return new StuAttendanceModel();
    }
}
