package com.srtianxia.bleattendance;

import android.app.Application;
import android.content.Context;
import com.polidea.rxandroidble.RxBleClient;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class BleApplication extends Application {
    private static Context mContext;
    private RxBleClient mClient;


    @Override public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mClient = RxBleClient.create(this);
    }


    public static Context getContext() {
        return mContext;
    }


    public static RxBleClient getRxBleClient(Context context) {
        BleApplication application = (BleApplication) context.getApplicationContext();
        return application.mClient;
    }
}
