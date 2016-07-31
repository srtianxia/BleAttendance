package com.srtianxia.bleattendance.base.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by srtianxia on 2016/7/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initView();
    }


    @Override protected void onDestroy() {
        super.onDestroy();
    }


    protected abstract void initView();

    protected abstract int getLayoutRes();

}
