package com.srtianxia.bleattendance.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.srtianxia.bleattendance.Receiver.LockReceiver;
import com.srtianxia.bleattendance.utils.LockUtil;

/**
 * Created by 梅梅 on 2016/9/5.
 */
public class LockService extends Service{
    private final String TAG = "MyService";
    private static final float INTERVAL = 0.5f;//in seconds

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"Myservice的onCreate方法调用");
    }

    /**
     * 注册一个一个延迟执行的广播，当指定app被唤醒时，才发送广播，通过广播再次开启服务，运行此方法
     * 然后在此方法内做app开启时的逻辑处理
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"Myservice的onStartCommand方法调用");
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = (int) (INTERVAL * 1000);
        long triggerAtTime = SystemClock.elapsedRealtime() + time;   //实际触发时间等于系统当前时间 + 间隔时间
        Intent i = new Intent(this,LockReceiver.class);
        //Pendingintent，一般用在 Notification上，可以理解为延迟执行的intent，
        //PendingIntent是对Intent一个包装。因为这里定义的是一个实时监听app开始运行的广播,app开始时才发送广播
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        LockUtil.queryUsageStats(this);

        return super.onStartCommand(intent, flags, startId);
    }


}
