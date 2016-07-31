package com.srtianxia.blelibs;

import android.annotation.TargetApi;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;
import android.util.Log;
import com.srtianxia.blelibs.config.BLEProfile;
import com.srtianxia.blelibs.utils.BytesUtil;
import com.srtianxia.blelibs.utils.ToastUtil;

/**
 * Created by srtianxia on 2016/7/14.
 * BLEPeripheral
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BLEPeripheral extends BaseBlueTooth {
    private static final String TAG = "BLEPeripheral";
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private Context mContext;

    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {

        /**
         * 成功的回调
         * @param settingsInEffect 可以获取到广播时的各种参数 {@link AdvertiseSettings}
         */
        @Override public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            ToastUtil.show(mContext, "ADVERTISE_SUCCESS", true);
        }

        /**
         * 失败的callback 错误码共5种
         * @param errorCode
         */
        @Override public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            if(errorCode == ADVERTISE_FAILED_DATA_TOO_LARGE){
                ToastUtil.show(mContext, "ADVERTISE_FAILED_DATA_TOO_LARGE", true);
                //Log.d(TAG,"Failed to start advertising as the advertise data to be broadcasted is larger than 31 bytes.");
            }else if(errorCode == ADVERTISE_FAILED_TOO_MANY_ADVERTISERS){
                ToastUtil.show(mContext, "ADVERTISE_FAILED_TOO_MANY_ADVERTISERS", true);
                //Log.d(TAG,"Failed to start advertising because no advertising instance is available.");
            }else if(errorCode == ADVERTISE_FAILED_ALREADY_STARTED){
                ToastUtil.show(mContext, "ADVERTISE_FAILED_ALREADY_STARTED", true);
                //Log.d(TAG,"Failed to start advertising as the advertising is already started");
            }else if(errorCode == ADVERTISE_FAILED_INTERNAL_ERROR){
                ToastUtil.show(mContext, "ADVERTISE_FAILED_INTERNAL_ERROR", true);
                //Log.d(TAG,"Operation failed due to an internal error");
            }else if(errorCode == ADVERTISE_FAILED_FEATURE_UNSUPPORTED){
                ToastUtil.show(mContext, "ADVERTISE_FAILED_FEATURE_UNSUPPORTED", true);
                //Log.d(TAG,"This feature is not supported on this platform");
            }
        }
    };


    public BLEPeripheral(Context context) {
        super(context);
        this.mContext = context;
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
    }


    /**
     * 开始广播
     * @param advData   跟随广播发送的数据，注意BLE协议栈规定的最大字节数
     *
     * @param advertiseMode 广播的模式,分三种
     * {@link AdvertiseSettings#ADVERTISE_MODE_BALANCED}
     * {@link AdvertiseSettings#ADVERTISE_MODE_LOW_LATENCY}
     * {@link AdvertiseSettings#ADVERTISE_MODE_LOW_POWER}
     *
     * @param txPowerLevel  耗电模式,分四种
     * {@link AdvertiseSettings#ADVERTISE_TX_POWER_HIGH}
     * {@link AdvertiseSettings#ADVERTISE_TX_POWER_MEDIUM}
     * {@link AdvertiseSettings#ADVERTISE_TX_POWER_LOW}
     * {@link AdvertiseSettings#ADVERTISE_TX_POWER_ULTRA_LOW}
     *
     * 是否支持连接
     * @param connectable
     */
    public void startAdvertise(String advData, int advertiseMode, int txPowerLevel, boolean connectable) {
        mBluetoothLeAdvertiser.startAdvertising(createAdvSettings(advertiseMode, txPowerLevel, connectable), createAdvData(advData), mAdvertiseCallback);
    }

    public void startAdvertise(String davData) {
        startAdvertise(davData, AdvertiseSettings.ADVERTISE_MODE_BALANCED, AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM, true);
    }

    /**
     * 创建跟随BLE广播发送的数据
     * @param advData
     * @return 返回创建好的 {@link AdvertiseData}
     */
    private AdvertiseData createAdvData(String advData) {
        AdvertiseData data = new AdvertiseData.Builder().addServiceData(ParcelUuid.fromString(
            BLEProfile.UUID_SERVICE),
            BytesUtil.string2Byte(advData)).build();
        return data;
    }


    public static AdvertiseSettings createAdvSettings(int advertiseMode, int txPowerLevel, boolean connectable) {
        AdvertiseSettings advSettings = new AdvertiseSettings.Builder()
            .setAdvertiseMode(advertiseMode)
            .setTxPowerLevel(txPowerLevel)
            .setConnectable(connectable)
            .build();
        return advSettings;
    }
}
