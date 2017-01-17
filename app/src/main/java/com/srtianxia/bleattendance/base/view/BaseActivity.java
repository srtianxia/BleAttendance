package com.srtianxia.bleattendance.base.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.srtianxia.bleattendance.R;

import butterknife.ButterKnife;

/**
 * Created by srtianxia on 2016/7/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        initStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initView();
    }


    private void initStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                Build.VERSION.SDK_INT < Build.VERSION_CODES.M
//                ? View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                : View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            );
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                getWindow().setStatusBarColor(Color.TRANSPARENT);
//            } else {
//                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
//            }
            getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        }
    }

    protected abstract void initView();

    protected abstract int getLayoutRes();

}
