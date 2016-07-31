package com.srtianxia.bleattendance.ui.activity;

import android.support.design.widget.FloatingActionButton;
import butterknife.BindView;
import butterknife.OnClick;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.fragment.TeacherFragment;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class TeacherActivity extends BaseActivity {
    @BindView(R.id.fab) FloatingActionButton fab;

    private TeacherFragment mTeacherFragment;

    @Override protected void initView() {
        mTeacherFragment = TeacherFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment_container, mTeacherFragment).commit();
    }


    @Override protected int getLayoutRes() {
        return R.layout.activity_teacher;
    }

    @OnClick(R.id.fab)
    void clickToScan() {
        mTeacherFragment.startScanDevice();
    }
}
