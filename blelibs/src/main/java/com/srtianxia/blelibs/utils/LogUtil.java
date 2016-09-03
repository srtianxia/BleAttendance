package com.srtianxia.blelibs.utils;

import android.bluetooth.BluetoothGatt;
import com.orhanobut.logger.Logger;

/**
 * Created by srtianxia on 2016/9/3.
 */
public class LogUtil {
    public static void logGattDetail(BluetoothGatt gatt) {
        Logger.d("gatt device --- >" + gatt.getDevice());
    }
}
