package com.srtianxia.bleattendance.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import butterknife.BindView;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.fragment.AttendanceFragment;
import com.srtianxia.bleattendance.ui.fragment.TeacherFragment;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class TeacherActivity extends BaseActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.bottom_view) BottomNavigationView mBottomView;

    private TeacherFragment mTeacherFragment;
    private AttendanceFragment mAttendanceFragment;


    @Override protected void initView() {
        mTeacherFragment = TeacherFragment.newInstance();
        mAttendanceFragment = AttendanceFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment_container, mTeacherFragment)
            .add(R.id.fragment_container, mAttendanceFragment)
            .show(mTeacherFragment)
            .hide(mAttendanceFragment)
            .commit();

        mBottomView.setOnNavigationItemSelectedListener(this);
    }


    @Override protected int getLayoutRes() {
        return R.layout.activity_teacher;
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_scan:
                getSupportFragmentManager().beginTransaction()
                    .hide(mAttendanceFragment).show(mTeacherFragment).commit();
                break;
            case R.id.bottom_nav_attendance:
                getSupportFragmentManager().beginTransaction()
                    .hide(mTeacherFragment).show(mAttendanceFragment).commit();
                break;
        }
        return true;
    }
}
