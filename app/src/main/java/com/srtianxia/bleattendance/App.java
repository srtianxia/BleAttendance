package com.srtianxia.bleattendance;

import android.app.Application;
import android.content.Context;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class App extends Application {
    private static Context mContext;
    @Override public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }


    public static Context getContext() {
        return mContext;
    }
}
