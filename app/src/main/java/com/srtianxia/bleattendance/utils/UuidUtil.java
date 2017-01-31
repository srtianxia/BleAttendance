package com.srtianxia.bleattendance.utils;

/**
 * Created by srtianxia on 2017/1/31.
 */

public class UuidUtil {
    public static String generateUuid(String classroom) {
        return "00000" + classroom.substring(classroom.length() - 3, classroom.length() - 1) + "-0000-1000-8000-00805f9b34fb";
    }
}
