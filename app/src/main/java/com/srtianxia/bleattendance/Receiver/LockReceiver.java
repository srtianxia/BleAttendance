package com.srtianxia.bleattendance.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.srtianxia.bleattendance.Service.LockService;

/**
 * Created by 梅梅 on 2016/9/5.
 */
public class LockReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LockService.class);
        context.startService(i);
    }
}