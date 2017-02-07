package com.srtianxia.bleattendance.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.srtianxia.bleattendance.receiver.LockReceiver;
import com.srtianxia.bleattendance.ui.lock.LockActivity;
import com.srtianxia.bleattendance.utils.ProcessUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by 梅梅 on 2016/9/5.
 */
public class LockService extends Service {

    public static String NOW_TIME = "now_time";

    private static final float INTERVAL = 0.5f;//in seconds

    private int mNowTime = 40*60;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        countDown(40*60);
    }

    /**
     * 注册一个一个延迟执行的广播，当指定app被唤醒时，才发送广播，通过广播再次开启服务，运行此方法
     * 然后在此方法内做app开启时的逻辑处理
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TAG","onStartCommand");
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = (int) (INTERVAL * 1000);
        long triggerAtTime = SystemClock.elapsedRealtime() + time;   //实际触发时间等于系统当前时间 + 间隔时间
        Intent i = new Intent(this, LockReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        if (ProcessUtil.isNeededInBackground(this)) {
//            ToastUtil.show(this, "23333~", true);
            Log.i("TAG","startActivity");
            Intent activity_intent = new Intent(this, LockActivity.class);
            activity_intent.putExtra(NOW_TIME,mNowTime);
            activity_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(activity_intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void countDown(int seconds){

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        return seconds - aLong.intValue();
                    }
                })
                .take(seconds + 1)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer nowTime) {
                        mNowTime = nowTime;
                        Log.i("TAG",nowTime + "");
                    }
                });

    }

}
