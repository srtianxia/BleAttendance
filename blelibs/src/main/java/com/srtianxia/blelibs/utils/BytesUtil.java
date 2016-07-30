package com.srtianxia.blelibs.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by srtianxia on 2016/7/15.
 */
public class BytesUtil {
    public static byte[] string2Byte(String string) {
        byte[] bytes = new byte[0];
        try {
            bytes = string.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
