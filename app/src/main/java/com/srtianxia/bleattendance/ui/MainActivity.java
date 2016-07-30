package com.srtianxia.bleattendance.ui;

import android.support.v4.app.FragmentManager;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.fragment.LoginFragment;

public class MainActivity extends BaseActivity {

    private LoginFragment mLoginFragment;
    private FragmentManager mFragmentManager;


    @Override protected void initView() {
        mFragmentManager = getSupportFragmentManager();

        initFragment();
    }


    private void initFragment() {
        mLoginFragment = new LoginFragment();
        mFragmentManager.beginTransaction().add(R.id.fragment_container, mLoginFragment).commit();
    }


    @Override protected int getLayoutRes() {
        return R.layout.activity_main;
    }
}
