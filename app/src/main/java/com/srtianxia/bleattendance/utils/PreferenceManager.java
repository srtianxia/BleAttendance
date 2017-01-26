package com.srtianxia.bleattendance.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.srtianxia.bleattendance.BleApplication;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class PreferenceManager {

    private String mPackName = "";
    private Context mContext;
    private static SharedPreferences mSettings;

    public static final String SP_STUDENT_NUMBER = "S";
    public static final String SP_TEACHER_NUMBER = "T";
    public static final int DEFALUT_NUMBER = 0;

    public static final String SP_TOKEN_TEACHER = "TOKEN_T";


    public static PreferenceManager getInstance() {
        return PreferenceInstance.instance;
    }

    private PreferenceManager() {
        this.mContext = BleApplication.getContext();
        mPackName = mContext.getPackageName();
        mSettings = mContext.getSharedPreferences(mPackName, 0);
    }

    private static class PreferenceInstance {
        private static final PreferenceManager instance = new PreferenceManager();
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return mSettings.getString(key, defaultValue);
    }

    public void setInteger(String key, int value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInteger(String key) {
        return mSettings.getInt(key, DEFALUT_NUMBER);
    }
}