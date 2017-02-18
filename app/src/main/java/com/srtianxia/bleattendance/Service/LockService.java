package com.srtianxia.bleattendance.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.receiver.LockReceiver;
import com.srtianxia.bleattendance.ui.lock.LockActivity;
import com.srtianxia.bleattendance.utils.ProcessUtil;
import com.srtianxia.bleattendance.utils.TimeUtil;

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
    private final String TAG = "LockService";

    private static final float INTERVAL = 5f;//in seconds

    private final int NOTIFICATION_ID = 1;

    private int mNowTime = TimeUtil.CONTINUE_TIME;

    private boolean isNotification = false;

    private NotificationManager notificationManager;
    private AlarmManager alarmManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        countDown(TimeUtil.CONTINUE_TIME);
    }

    /**
     * 注册一个一个延迟执行的广播，当指定app被唤醒时，才发送广播，通过广播再次开启服务，运行此方法
     * 然后在此方法内做app开启时的逻辑处理
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TAG", "onStartCommand");

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int time = (int) INTERVAL * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + time;   //实际触发时间等于系统当前时间 + 间隔时间
        Intent i = new Intent(this, LockReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        //将倒计时所剩余的具体时间传递到LockActivity
        Intent intent_lock = new Intent(this, LockActivity.class);
        intent_lock.putExtra(NOW_TIME, mNowTime);
        intent_lock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //FLAG_UPDATE_CURRENT：如果该PendingIntent已经存在，则用新传入的Intent更新当前的数据。
        // 这个参数不填这个，则点击notification跳转到lockActivity 显示的时间是第一次传入的旧时间
        PendingIntent pi_lock = PendingIntent.getActivity(this,0,intent_lock,PendingIntent.FLAG_UPDATE_CURRENT);

        if (!isNotification){
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_home);
            builder.setContentTitle("正在认真上课");
            builder.setContentText(TimeUtil.getNotificationTime());
            builder.setContentIntent(pi_lock);
            Notification notification = builder.build();
            notificationManager.notify(NOTIFICATION_ID,notification);
            isNotification = true;
        }

//        startForeground(1,notification);

        if (ProcessUtil.isNeededInBackground(this)) {
            Log.i("TAG", "startActivity");
            startActivity(intent_lock);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("TAG","onDestroy");
        super.onDestroy();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, LockReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);

        notificationManager.cancel(NOTIFICATION_ID);

    }

    public void countDown(int seconds) {

        Observable.interval(1, TimeUnit.SECONDS)
                .onBackpressureDrop()
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
                        Log.i("TAG", nowTime + "");
                        if (nowTime.equals(0)){
                            Intent intent = new Intent(getApplication(),LockService.class);
                            getApplication().stopService(intent);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("TAG",throwable.toString());

                    }
                });
    }

}
