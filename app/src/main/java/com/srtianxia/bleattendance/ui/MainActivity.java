package com.srtianxia.bleattendance.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.service.LockService;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.config.Constant;
import com.srtianxia.bleattendance.ui.fragment.LoginFragment;
import com.srtianxia.bleattendance.utils.ProcessUtil;
import com.srtianxia.blelibs.utils.ToastUtil;

public class MainActivity extends BaseActivity {

    private LoginFragment mLoginFragment;
    private FragmentManager mFragmentManager;


    @Override protected void initView() {
        openBlueTooth();
        mFragmentManager = getSupportFragmentManager();
        initFragment();
        if (!ProcessUtil.isPermission(this)) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast.makeText(this, "权限不够\n请打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾",
                Toast.LENGTH_LONG).show();
        }
        startService(new Intent(this, LockService.class));
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


    @Override protected int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
