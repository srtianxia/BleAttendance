package com.srtianxia.bleattendance.ui.student.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.course.CourseFragment;
import com.srtianxia.bleattendance.ui.student.attendance.StudentAttendanceFragment;
import com.srtianxia.bleattendance.ui.student.table.CourseTableFragment;

import butterknife.BindView;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StudentHomeActivity extends BaseActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fragment_container) FrameLayout fragmentContainer;
    @BindView(R.id.bottom_view_student) BottomNavigationView bottomViewStudent;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title) TextView toolbar_title;

    private StudentAttendanceFragment mAttendanceFragment;
    private CourseTableFragment mCourseTableFragment;
    private CourseFragment mCourseFragment;


    @Override protected void initView() {
        mAttendanceFragment = StudentAttendanceFragment.newInstance();
        mCourseTableFragment = CourseTableFragment.newInstance();
        mCourseFragment = CourseFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment_container, mAttendanceFragment)
//          .add(R.id.fragment_container,mCourseTableFragment)
            .add(R.id.fragment_container, mCourseFragment)
            .hide(mCourseFragment).show(mAttendanceFragment).commit();
        bottomViewStudent.setOnNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(getString(R.string.student_page_toolbar));
    }


    @Override protected int getLayoutRes() {
        return R.layout.activity_student_home;
    }


    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_attendance:
                getSupportFragmentManager().beginTransaction().hide(mCourseFragment).show(mAttendanceFragment).commit();
                break;
            case R.id.bottom_nav_table:
                getSupportFragmentManager().beginTransaction().hide(mAttendanceFragment).show(mCourseFragment).commit();
                break;
        }
        return true;
    }
}
