package com.srtianxia.bleattendance.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.srtianxia.bleattendance.receiver.LockReceiver;
import com.srtianxia.bleattendance.utils.ProcessUtil;
import com.srtianxia.bleattendance.utils.ToastUtil;

/**
 * Created by 梅梅 on 2016/9/5.
 */
public class LockService extends Service {
    private static final float INTERVAL = 0.5f;//in seconds


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }


    /**
     * 注册一个一个延迟执行的广播，当指定app被唤醒时，才发送广播，通过广播再次开启服务，运行此方法
     * 然后在此方法内做app开启时的逻辑处理
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = (int) (INTERVAL * 1000);
        long triggerAtTime = SystemClock.elapsedRealtime() + time;   //实际触发时间等于系统当前时间 + 间隔时间
        Intent i = new Intent(this, LockReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        if (ProcessUtil.isNeededInBackground(this)) {
            ToastUtil.show(this, "23333~", true);


            //跳转至随机一个activity
            /*int ran_num = new Random().nextInt(4);
            Intent activity_intent = null;
            if (ran_num == 0) activity_intent = new Intent(this, LockActivity.class);
            if (ran_num == 1) activity_intent = new Intent(this, LockActivity1.class);
            if (ran_num == 2) activity_intent = new Intent(this, LockActivity2.class);
            if (ran_num == 3) activity_intent = new Intent(this, LockActivity3.class);
            activity_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(activity_intent);*/
        }
        return super.onStartCommand(intent, flags, startId);
    }

}
