package com.srtianxia.bleattendance.ui.activity.Lockactivity;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by 梅梅 on 2016/8/31.
 */
public class BaseLockActivity extends Activity{

    @Override
    public void onBackPressed() {
        Intent MyIntent = new Intent(Intent.ACTION_MAIN);
        MyIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(MyIntent);

        finish();
    }
}
