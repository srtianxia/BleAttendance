package com.srtianxia.blelibs;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import com.srtianxia.blelibs.utils.ToastUtil;

/**
 * Created by srtianxia on 2016/7/14.
 */
public class BaseBlueTooth {
    protected BluetoothAdapter mBluetoothAdapter;
    protected BluetoothManager mBluetoothManager;
    protected Context mContext;

    public BaseBlueTooth(Context context) {
        this.mContext = context;
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) context.getSystemService(
                Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                ToastUtil.show(context, "Unable to initialize BluetoothManager.", true);
            }

            mBluetoothAdapter = mBluetoothManager.getAdapter();
            if (mBluetoothAdapter == null) {
                ToastUtil.show(context, "Unable to obtain a BluetoothAdapter.", true);
            }
        }
    }
}
