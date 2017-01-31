package com.srtianxia.bleattendance.ui.student.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.course.CourseContainerFragment;
import com.srtianxia.bleattendance.ui.student.attendance.StudentAttendanceFragment;
import com.srtianxia.bleattendance.ui.student.table.CourseTableFragment;
import com.srtianxia.bleattendance.utils.UuidUtil;
import com.srtianxia.bleattendance.widget.CourseTableView;

import butterknife.BindView;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StudentHomeActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.bottom_view_student)
    BottomNavigationView bottomViewStudent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;

    private StudentAttendanceFragment mAttendanceFragment;
    private CourseTableFragment mCourseTableFragment;
    private CourseContainerFragment mCourseContainerFragment;

    private CourseTableView.CourseList mCourse;
    private String mWeek;

    private String mUUid;

    @Override
    protected void initView() {
        mAttendanceFragment = StudentAttendanceFragment.newInstance();
        mCourseTableFragment = CourseTableFragment.newInstance();
        mCourseContainerFragment = CourseContainerFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mAttendanceFragment)
                .add(R.id.fragment_container, mCourseContainerFragment)
                .hide(mCourseContainerFragment).show(mAttendanceFragment).commit();
        bottomViewStudent.setOnNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(getString(R.string.student_page_toolbar));
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_student_home;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_attendance:
                getSupportFragmentManager().beginTransaction().hide(mCourseContainerFragment).show(mAttendanceFragment).commit();
                break;
            case R.id.bottom_nav_table:
                getSupportFragmentManager().beginTransaction().hide(mAttendanceFragment).show(mCourseContainerFragment).commit();
                break;
        }
        return true;
    }

    public void setAttCourse(CourseTableView.CourseList course, String week) {
        this.mCourse = course;
        this.mWeek = week;
        this.mUUid = UuidUtil.generateUuid(course.list.get(0).classroom);
    }

    public String getUuid() {
        return mUUid;
    }
}
