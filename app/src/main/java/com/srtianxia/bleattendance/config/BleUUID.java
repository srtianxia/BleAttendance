package com.srtianxia.bleattendance.config;

import java.util.UUID;

/**
 * Created by srtianxia on 2016/11/26.
 * 存放UUID的类，使用心跳包的数值
 */

public class BleUUID {
    /**
     * Service
     */
    public static final UUID HEART_RATE_SERVICE_UUID = UUID
        .fromString("0000180D-0000-1000-8000-00805f9b34fb");

    /**
     * Notify
     */
    public static final UUID HEART_RATE_MEASUREMENT_UUID = UUID
        .fromString("00002A37-0000-1000-8000-00805f9b34fb");
    /**
     * Read
     */
    public static final UUID BODY_SENSOR_LOCATION_UUID = UUID
        .fromString("00002A38-0000-1000-8000-00805f9b34fb");

    /**
     * Write
     */
    public static final UUID HEART_RATE_CONTROL_POINT_UUID = UUID
        .fromString("00002A39-0000-1000-8000-00805f9b34fb");

    public static final UUID ATTENDANCE_SERVICE_UUID = UUID
        .fromString("0000080F-0000-1000-8000-00805f9b34fb");

    public static final UUID ATTENDANCE_NOTIFY_WRITE = UUID.fromString(
        "00001A37-0000-1000-8000-00805f9b34fb");
}
