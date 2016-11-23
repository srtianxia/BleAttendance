package com.srtianxia.bleattendance.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import butterknife.BindView;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.fragment.QueryAttendanceFragment;
import com.srtianxia.bleattendance.ui.fragment.TeacherScanScanFragment;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class TeacherActivity extends BaseActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.bottom_view) BottomNavigationView mBottomView;

    private TeacherScanScanFragment mTeacherScanFragment;
    private QueryAttendanceFragment mQueryAttendanceFragment;


    @Override protected void initView() {
        mTeacherScanFragment = TeacherScanScanFragment.newInstance();
        mQueryAttendanceFragment = QueryAttendanceFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment_container, mTeacherScanFragment)
            .add(R.id.fragment_container, mQueryAttendanceFragment)
            .show(mTeacherScanFragment)
            .hide(mQueryAttendanceFragment)
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
                    .hide(mQueryAttendanceFragment).show(mTeacherScanFragment).commit();
                break;
            case R.id.bottom_nav_attendance:
                getSupportFragmentManager().beginTransaction()
                    .hide(mTeacherScanFragment).show(mQueryAttendanceFragment).commit();
                break;
        }
        return true;
    }
}
