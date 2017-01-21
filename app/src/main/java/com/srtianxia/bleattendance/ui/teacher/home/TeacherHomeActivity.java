package com.srtianxia.bleattendance.ui.teacher.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.teacher.attendance.TeacherScanFragment;
import com.srtianxia.bleattendance.ui.teacher.record.AttConditionFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class TeacherHomeActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.bottom_view)
    BottomNavigationView mBottomView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_current_course)
    TextView tvCurrentCourse;


    private TeacherScanFragment mTeacherScanFragment;
    private AttConditionFragment attConditionFragment = new AttConditionFragment();
    private List<Integer> mNumberList = new ArrayList<>();

    @Override
    protected void initView() {
        mTeacherScanFragment = TeacherScanFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mTeacherScanFragment)
                .add(R.id.fragment_container, attConditionFragment)
                .show(mTeacherScanFragment)
                .hide(attConditionFragment)
                .commit();

        tvCurrentCourse.setText(getText(R.string.current_course_title) + "" + getText(R.string.current_course_no_choose));

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
                        .hide(attConditionFragment).show(mTeacherScanFragment).commit();
                break;
            case R.id.bottom_nav_attendance:
                getSupportFragmentManager().beginTransaction()
                        .hide(mTeacherScanFragment).show(attConditionFragment).commit();
                break;
        }
        return true;
    }

    public void addNumber(Integer number) {
        mNumberList.add(number);
    }

    public List<Integer> getNumberList() {
        return mNumberList;
    }
}
