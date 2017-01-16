package com.srtianxia.bleattendance.ui.teacher.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import butterknife.BindView;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.teacher.record.QueryAttendanceFragment;
import com.srtianxia.bleattendance.ui.teacher.attendance.TeacherScanFragment;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class TeacherHomeActivity extends BaseActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.bottom_view) BottomNavigationView mBottomView;

    private TeacherScanFragment mTeacherScanFragment;
    private QueryAttendanceFragment mQueryAttendanceFragment;


    @Override protected void initView() {
        mTeacherScanFragment = TeacherScanFragment.newInstance();
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
        return R.layout.activity_teacher_home;
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
