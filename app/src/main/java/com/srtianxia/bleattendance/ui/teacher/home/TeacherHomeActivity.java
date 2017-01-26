package com.srtianxia.bleattendance.ui.teacher.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.course.CourseFragment;
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
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;


    private TeacherScanFragment mTeacherScanFragment;

    private AttConditionFragment attConditionFragment = new AttConditionFragment();
    private List<Integer> mNumberList = new ArrayList<>();

    private CourseFragment mCourseFragment;

    public List<Integer> mStuNumber = new ArrayList<>();


    @Override
    protected void initView() {
        mTeacherScanFragment = TeacherScanFragment.newInstance();

        mCourseFragment = CourseFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mTeacherScanFragment)
                .add(R.id.fragment_container, attConditionFragment)
                .add(R.id.fragment_container, mCourseFragment)
                .show(mTeacherScanFragment)
                .hide(attConditionFragment)
                .hide(mCourseFragment)
                .commit();

        tvCurrentCourse.setText(getText(R.string.current_course_title) + "" + getText(R.string.current_course_no_choose));

        mBottomView.setOnNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(getString(R.string.teacher_page_toolbar));

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
                        .hide(attConditionFragment).hide(mCourseFragment)
                    .show(mTeacherScanFragment).commit();
                break;
            case R.id.bottom_nav_attendance:
                getSupportFragmentManager().beginTransaction()
                        .hide(mTeacherScanFragment).hide(mCourseFragment)
                        .show(attConditionFragment).commit();
                break;
            case R.id.bottom_nav_table:
                getSupportFragmentManager().beginTransaction()
                        .hide(mTeacherScanFragment).hide(attConditionFragment)
                        .show(mCourseFragment).commit();

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
