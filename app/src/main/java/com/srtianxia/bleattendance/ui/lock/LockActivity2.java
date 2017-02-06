package com.srtianxia.bleattendance.ui.lock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;

import butterknife.BindView;

/**
 *  随便一个用于跳转的界面
 */
public class LockActivity2 extends BaseLockActivity {

    @BindView(R.id.tv_lock_back)TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock2);

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

}
