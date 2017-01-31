package com.srtianxia.bleattendance.ui.student.attendance;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;

/**
 * Created by srtianxia on 2017/1/16.
 */

public interface IStuAttModel {
    IStuAttModel getInstance(Context context);

    void initBle(String uuid);

    void startAdvertise();

    void stopAdvertise();

    void notifyCenter(BluetoothGattCharacteristic characteristic);

    void onDestroy();
}
