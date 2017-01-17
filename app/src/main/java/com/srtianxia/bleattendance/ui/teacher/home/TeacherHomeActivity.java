package com.srtianxia.bleattendance.ui.teacher.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
    @BindView(R.id.bottom_view)
    BottomNavigationView mBottomView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private TeacherScanFragment mTeacherScanFragment;
    private QueryAttendanceFragment mQueryAttendanceFragment;


    @Override
    protected void initView() {
        mTeacherScanFragment = TeacherScanFragment.newInstance();
        mQueryAttendanceFragment = QueryAttendanceFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mTeacherScanFragment)
                .add(R.id.fragment_container, mQueryAttendanceFragment)
                .show(mTeacherScanFragment)
                .hide(mQueryAttendanceFragment)
                .commit();


        mBottomView.setOnNavigationItemSelectedListener(this);
        toolbar.setTitle(getString(R.string.teacher_page_toolbar));
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                mTeacherScanFragment.startScan();
                break;
            case R.id.action_write:
                mTeacherScanFragment.write();
                break;
            case R.id.action_disconnect:
                mTeacherScanFragment.disconnect();
                break;
            case R.id.action_connect_all:
                mTeacherScanFragment.connectAll();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_teacher_home;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
