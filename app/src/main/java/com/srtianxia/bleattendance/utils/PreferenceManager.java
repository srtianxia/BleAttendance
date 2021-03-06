package com.srtianxia.bleattendance.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.srtianxia.bleattendance.BleApplication;

import java.util.HashSet;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class PreferenceManager {

    private String mPackName = "";
    private Context mContext;
    private static SharedPreferences mSettings;

    public static final String SP_STUDENT_NUMBER = "S";
    public static final String SP_TEACHER_NUMBER = "T";
    public static final int DEFAULT_NUMBER = 0;

    public static final String SP_TOKEN_TEACHER = "TOKEN_T";
    public static final String SP_TOKEN_STUDENT = "TOKEN_S";

    public static final String SP_LOGIN_FLAG = "FLAG";
    public static final String SP_LOGIN_FLAG_STU = "FLAG_S";
    public static final String SP_LOGIN_FLAG_TEA = "FLAG_T";

    public static final String SP_IS_TEA_COURSE = "IS_TEA_C";
    public static final String SP_IS_STU_COURSE = "IS_STU_C";

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
        return mSettings.getInt(key, DEFAULT_NUMBER);
    }

    public void setHashSet(String key, HashSet<String> set){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putStringSet(key,set);
        editor.apply();
    }

    public HashSet<String> getHashSet(String key){
        return (HashSet<String>) mSettings.getStringSet(key,new HashSet<String>());
    }

}