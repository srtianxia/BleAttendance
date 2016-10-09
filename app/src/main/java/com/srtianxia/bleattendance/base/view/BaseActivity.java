package com.srtianxia.bleattendance.base.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;

/**
 * Created by srtianxia on 2016/7/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        /*getWindow().setExitTransition(new Explode().setDuration(500));
        getWindow().setEnterTransition(new Explode().setDuration(500));*/
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
