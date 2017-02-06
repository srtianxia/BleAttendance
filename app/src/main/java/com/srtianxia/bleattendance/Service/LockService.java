package com.srtianxia.bleattendance.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.srtianxia.bleattendance.receiver.LockReceiver;
import com.srtianxia.bleattendance.ui.lock.LockActivity;
import com.srtianxia.bleattendance.ui.student.attendance.StuAttendancePresenter;
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

    private static final float INTERVAL = 0.5f;//in seconds

    private final IBinder mBinder = new LockBinder();

    private String mNowTime = "00:00:00";

    private StuAttendancePresenter.IStuAttendanceView mBindView;

    public class LockBinder extends Binder {
        public LockService getService(){
            return LockService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = (int) (INTERVAL * 1000);
        long triggerAtTime = SystemClock.elapsedRealtime() + time;   //实际触发时间等于系统当前时间 + 间隔时间
        Intent i = new Intent(this, LockReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        if (ProcessUtil.isNeededInBackground(this)) {
//            ToastUtil.show(this, "23333~", true);

            Intent activity_intent = new Intent(this, LockActivity.class);
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
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer time) {
                        int hours = time/3600 ;
                        int minutes = (time - hours*3600)/60;
                        int seconds = time - hours*3600 - minutes*60;
                        String str = "";

                        if (hours < 10 ){
                            str = "0" + hours + ":";
                        }else
                            str = "" + hours + ":";

                        if (minutes < 10){
                            str = str + "0" + minutes + ":";
                        }else
                            str = str + minutes + ":";

                        if (seconds < 10){
                            str = str + "0" + seconds ;
                        }else
                            str = str + seconds ;

                        return str;
                    }
                })
                .take(seconds + 1)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mNowTime = s;
                    }
                });

    }

    public String getNowTime(){
        return mNowTime;
    }

    public void setActivity(StuAttendancePresenter.IStuAttendanceView view){
        mBindView = view;
    }

}
