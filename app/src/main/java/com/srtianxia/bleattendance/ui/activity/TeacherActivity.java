package com.srtianxia.bleattendance.ui.activity;

import android.support.annotation.IdRes;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.fragment.AttendFragment;
import com.srtianxia.bleattendance.ui.fragment.TeacherFragment;

import butterknife.BindView;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class TeacherActivity extends BaseActivity implements OnTabSelectListener {
//    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.btb_teacher) BottomBar btb;

    private TeacherFragment mTeacherFragment;
    private AttendFragment mAttendFragment;

    @Override protected void initView() {
        mTeacherFragment = TeacherFragment.newInstance();
        mAttendFragment = AttendFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mTeacherFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mAttendFragment).commit();
        btb.setOnTabSelectListener(this);
    }


    @Override protected int getLayoutRes() {
        return R.layout.activity_teacher;
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_scan){
            getSupportFragmentManager().beginTransaction()
                    .hide(mAttendFragment).show(mTeacherFragment).commit();
        }else if (tabId == R.id.tab_attend){
            getSupportFragmentManager().beginTransaction()
                    .hide(mTeacherFragment).show(mAttendFragment).commit();
        }
    }



    /*@OnClick(R.id.fab)
    void clickToScan() {
        mTeacherFragment.startScanDevice();
    }*/
}
