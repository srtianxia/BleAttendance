package com.srtianxia.bleattendance.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.config.Constant;
import com.srtianxia.bleattendance.ui.enter.LoginFragment;
import com.srtianxia.bleattendance.utils.ToastUtil;

public class MainActivity extends BaseActivity {
    private LoginFragment mLoginFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void initView() {
        openBlueTooth();
        mFragmentManager = getSupportFragmentManager();
        initFragment();

    }


    private void openBlueTooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, Constant.REQUEST_CODE_BLUE_OPEN);
        }
    }


    private void initFragment() {
        mLoginFragment = new LoginFragment();
        mFragmentManager.beginTransaction().add(R.id.fragment_container, mLoginFragment).commit();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_BLUE_OPEN) {
            if (resultCode == RESULT_OK) {
                ToastUtil.show(this, "蓝牙已开启", true);
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtil.show(this, "必须使用蓝牙", true);
                finish();
            }
        }
    }
}
