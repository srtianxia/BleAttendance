package com.srtianxia.blelibs.callback;

import android.bluetooth.BluetoothDevice;

/**
 * Created by srtianxia on 2016/9/5.
 */

interface OnConnectionStateListener {
    void onConnect(BluetoothDevice device);

    void onDisConnect(BluetoothDevice device);
}
