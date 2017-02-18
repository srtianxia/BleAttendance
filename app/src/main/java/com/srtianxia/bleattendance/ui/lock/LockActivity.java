package com.srtianxia.bleattendance.ui.lock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.service.LockService;
import com.srtianxia.bleattendance.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 随便一个用于跳转的界面
 */
public class LockActivity extends BaseLockActivity implements LockPresenter.ILockView{

    @BindView(R.id.tv_lock_back)TextView mTextView;
    @BindView(R.id.tv_lock_time)TextView mLockTime;

    private final String TAG = "LockActivity";

    private final int DEFAULT_TIME = 40*60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        ButterKnife.bind(this);

        int nowTime = getIntent().getIntExtra(LockService.NOW_TIME,DEFAULT_TIME);

        countDown(nowTime);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MyIntent = new Intent(Intent.ACTION_MAIN);
                MyIntent.addCategory(Intent.CATEGORY_HOME);
                startActivity(MyIntent);
                finish();
            }
        });
    }

    private void countDown(int seconds){

        Observable.interval(1, TimeUnit.SECONDS)
                .onBackpressureDrop()
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mLockTime.setText(TimeUtil.timeTransform(seconds));
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        return seconds - aLong.intValue() - 1;
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer time) {
                        return TimeUtil.timeTransform(time);
                    }
                })
                .take(seconds + 1)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String nowTime) {
                        mLockTime.setText(nowTime);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i(TAG,throwable.toString());
                    }
                });
    }

}
