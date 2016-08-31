package com.srtianxia.bleattendance.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.srtianxia.bleattendance.App;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class SharePreferenceManager {

    private String mPackName = "";
    private Context mContext;
    private static SharedPreferences mSettings;

    public static SharePreferenceManager getInstance() {
        return PreferenceInstance.instance;
    }

    private SharePreferenceManager() {
        this.mContext = App.getContext();
        mPackName = mContext.getPackageName();
        mSettings = mContext.getSharedPreferences(mPackName, 0);
    }

    private static class PreferenceInstance {
        private static final SharePreferenceManager instance = new SharePreferenceManager();
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return mSettings.getString(key, defaultValue);
    }


}