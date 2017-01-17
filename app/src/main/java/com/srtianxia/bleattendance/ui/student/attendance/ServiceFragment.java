package com.srtianxia.bleattendance.ui.student.attendance;

import android.app.Fragment;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.ParcelUuid;

public abstract class ServiceFragment extends Fragment {
    public abstract BluetoothGattService getBluetoothGattService();

    public abstract ParcelUuid getServiceUUID();


    public int writeCharacteristic(BluetoothGattCharacteristic characteristic, int offset, byte[] value) {
        throw new UnsupportedOperationException("Method writeCharacteristic not overriden");
    }


    public interface ServiceFragmentDelegate {
        void sendNotificationToDevices(BluetoothGattCharacteristic characteristic);
    }
}
