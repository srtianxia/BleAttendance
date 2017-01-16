package com.srtianxia.blelibs;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.ParcelUuid;
import com.srtianxia.blelibs.callback.OnConnectListener;
import com.srtianxia.blelibs.config.BLEProfile;
import com.srtianxia.blelibs.utils.BytesUtil;
import com.srtianxia.blelibs.utils.ToastUtil;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by srtianxia on 2016/7/14.
 * BLEPeripheral
 */
public class BLEPeripheral extends BaseBlueTooth {
    private static final String TAG = "BLEPeripheral";
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private BluetoothGattServer mGattServer;
    private BluetoothGattService mGattService;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothDevice mRemoteDevice;

    private Context mContext;

    private boolean isStartAdvertise = false;

//    private OnConnectionStateListener mConnectionStateListener;

    private OnConnectListener mOnConnectListener;

//    public void setConnectionStateListener(OnConnectionStateListener mConnectionStateListener) {
//        this.mConnectionStateListener = mConnectionStateListener;
//    }


    //连接的回调
    private BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback() {

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite,
                responseNeeded, offset, value);
            if (characteristic.getUuid().toString().equals(BLEProfile.UUID_CHARACTERISTIC_WRITE)) {
                //onCharacteristicWriteListener.write(value);
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
            } else {
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_FAILURE, offset, value);
            }
        }


        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
            super.onDescriptorReadRequest(device, requestId, offset, descriptor);
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, descriptor.getValue());
        }


        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic.getValue());
        }


        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite,
                responseNeeded,
                offset, value);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mRemoteDevice = device;
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        }


        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
//            if (mConnectionStateListener != null) {
//                if (newState == BluetoothProfile.STATE_CONNECTED) {
//                    mConnectionStateListener.onConnect(device);
//                    mRemoteDevice = device;
//                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                    mConnectionStateListener.onDisConnect(device);
//                }
//            }

        }
    };


    public void setOnConnectListener(OnConnectListener mOnConnectListener) {
        this.mOnConnectListener = mOnConnectListener;
    }


    //广播的回调
    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {

        /**
         * 成功的回调
         * @param settingsInEffect 可以获取到广播时的各种参数 {@link AdvertiseSettings}
         */
        @Override public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            isStartAdvertise = true;
            ToastUtil.show(mContext, "ADVERTISE_SUCCESS", true);
        }


        /**
         * 失败的callback 错误码共5种
         * @param errorCode
         */
        @Override public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            isStartAdvertise = false;
            if (errorCode == ADVERTISE_FAILED_DATA_TOO_LARGE) {
                ToastUtil.show(mContext, "ADVERTISE_FAILED_DATA_TOO_LARGE", true);
                //Log.d(TAG,"Failed to start advertising as the advertise data to be broadcasted is larger than 31 bytes.");
            } else if (errorCode == ADVERTISE_FAILED_TOO_MANY_ADVERTISERS) {
                ToastUtil.show(mContext, "ADVERTISE_FAILED_TOO_MANY_ADVERTISERS", true);
                //Log.d(TAG,"Failed to start advertising because no advertising instance is available.");
            } else if (errorCode == ADVERTISE_FAILED_ALREADY_STARTED) {
                ToastUtil.show(mContext, "ADVERTISE_FAILED_ALREADY_STARTED", true);
                //Log.d(TAG,"Failed to start advertising as the advertising is already started");
            } else if (errorCode == ADVERTISE_FAILED_INTERNAL_ERROR) {
                ToastUtil.show(mContext, "ADVERTISE_FAILED_INTERNAL_ERROR", true);
                //Log.d(TAG,"Operation failed due to an internal error");
            } else if (errorCode == ADVERTISE_FAILED_FEATURE_UNSUPPORTED) {
                ToastUtil.show(mContext, "ADVERTISE_FAILED_FEATURE_UNSUPPORTED", true);
                //Log.d(TAG,"This feature is not supported on this platform");
            }
        }
    };


    public BLEPeripheral(Context context) {
        super(context);
        this.mContext = context;
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
        BluetoothGattDescriptor gattDescriptor = new BluetoothGattDescriptor(UUID.randomUUID(), BluetoothGattDescriptor.PERMISSION_WRITE);
        mNotifyCharacteristic = new BluetoothGattCharacteristic(UUID.fromString(BLEProfile.UUID_CHARACTERISTIC_NOTIFY),
            BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        mNotifyCharacteristic.addDescriptor(gattDescriptor);
        BluetoothGattCharacteristic writeCharacteristic = new BluetoothGattCharacteristic(UUID.fromString(BLEProfile.UUID_CHARACTERISTIC_WRITE),
            BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PERMISSION_WRITE);
        mGattService = new BluetoothGattService(UUID.fromString(BLEProfile.UUID_SERVICE), BluetoothGattService.SERVICE_TYPE_PRIMARY);
        mGattService.addCharacteristic(mNotifyCharacteristic);
        mGattService.addCharacteristic(writeCharacteristic);
        mGattServer = mBluetoothManager.openGattServer(mContext, mGattServerCallback);
        mGattServer.addService(mGattService);
    }


    /**
     * 开始广播
     *
     * @param advData 跟随广播发送的数据，注意BLE协议栈规定的最大字节数
     * @param advertiseMode 广播的模式,分三种
     * {@link AdvertiseSettings#ADVERTISE_MODE_BALANCED}
     * {@link AdvertiseSettings#ADVERTISE_MODE_LOW_LATENCY}
     * {@link AdvertiseSettings#ADVERTISE_MODE_LOW_POWER}
     * @param txPowerLevel 耗电模式,分四种
     * {@link AdvertiseSettings#ADVERTISE_TX_POWER_HIGH}
     * {@link AdvertiseSettings#ADVERTISE_TX_POWER_MEDIUM}
     * {@link AdvertiseSettings#ADVERTISE_TX_POWER_LOW}
     * {@link AdvertiseSettings#ADVERTISE_TX_POWER_ULTRA_LOW}
     *
     * 是否支持连接
     */
    public void startAdvertise(String advData, int advertiseMode, int txPowerLevel, boolean connectable) {
        if (!isStartAdvertise) {
            mBluetoothLeAdvertiser.startAdvertising(
                createAdvSettings(advertiseMode, txPowerLevel, connectable), createAdvData(advData),
                mAdvertiseCallback);
        } else {
            ToastUtil.show(mContext, "已经开始广播", true);
        }
    }


    public void startAdvertise(String davData) {
        startAdvertise(davData, AdvertiseSettings.ADVERTISE_MODE_BALANCED,
            AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM, true);
    }


    /**
     * 创建跟随BLE广播发送的数据
     *
     * @return 返回创建好的 {@link AdvertiseData}
     */
    private AdvertiseData createAdvData(String advData) {
        AdvertiseData data = new AdvertiseData.Builder().addServiceData(ParcelUuid.fromString(
            BLEProfile.UUID_SERVICE),
            BytesUtil.string2Byte(advData)).build();
        return data;
    }


    public void stopAdvertise() {
        mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
    }


    public static AdvertiseSettings createAdvSettings(int advertiseMode, int txPowerLevel, boolean connectable) {
        AdvertiseSettings advSettings = new AdvertiseSettings.Builder()
            .setAdvertiseMode(advertiseMode)
            .setTxPowerLevel(txPowerLevel)
            .setConnectable(connectable)
            .build();
        return advSettings;
    }


    public void sendMessage(String message) {
        byte[] bytes = BytesUtil.string2Byte(message);
        mNotifyCharacteristic.setValue(Arrays.copyOf(bytes, bytes.length));
        mGattServer.notifyCharacteristicChanged(mRemoteDevice, mNotifyCharacteristic, false);
    }
}
