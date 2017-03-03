package com.srtianxia.bleattendance.ui.student.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.ui.MainActivity;
import com.srtianxia.bleattendance.ui.course.CourseContainerFragment;
import com.srtianxia.bleattendance.ui.student.attendance.StudentAttendanceFragment;
import com.srtianxia.bleattendance.ui.student.beforeattendance.StuBeforeAttendanceFragment;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.ProcessUtil;
import com.srtianxia.bleattendance.utils.UiHelper;
import com.srtianxia.bleattendance.utils.UuidUtil;
import com.srtianxia.bleattendance.utils.database.DataBaseManager;
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
    private CourseContainerFragment mCourseContainerFragment;
    private StuBeforeAttendanceFragment mBeforeAttendanceFragment;

    private CourseTableView.CourseList mCourse;
    private String mWeek;

    private String mUUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initFragment();
        }
    }

    @Override
    protected void initView() {
        bottomViewStudent.setOnNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(getString(R.string.student_page_toolbar));

//        openUsageAccess();
    }

    protected void initFragment() {
        mAttendanceFragment = StudentAttendanceFragment.newInstance();
        mCourseContainerFragment = CourseContainerFragment.newInstance();
        mBeforeAttendanceFragment = StuBeforeAttendanceFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mAttendanceFragment)
                .add(R.id.fragment_container, mCourseContainerFragment)
                .add(R.id.fragment_container, mBeforeAttendanceFragment)
                .hide(mCourseContainerFragment)
                .hide(mBeforeAttendanceFragment)
                .show(mAttendanceFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_student, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                PreferenceManager.getInstance().setString(PreferenceManager.SP_TOKEN_STUDENT, "");
                PreferenceManager.getInstance().setString(PreferenceManager.SP_LOGIN_FLAG, "");
                DataBaseManager.getInstance().deleteStuCourse();
                UiHelper.startActivity(this, MainActivity.class);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_attendance:
                getSupportFragmentManager().beginTransaction()
                        .hide(mCourseContainerFragment)
                        .hide(mBeforeAttendanceFragment)
                        .show(mAttendanceFragment).commit();
                break;
            case R.id.bottom_nav_table:
                getSupportFragmentManager().beginTransaction()
                        .hide(mAttendanceFragment)
                        .hide(mBeforeAttendanceFragment)
                        .show(mCourseContainerFragment).commit();
                break;
            case R.id.bottom_before_att:
                getSupportFragmentManager().beginTransaction()
                        .hide(mAttendanceFragment)
                        .hide(mCourseContainerFragment)
                        .show(mBeforeAttendanceFragment)
                        .commit();

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

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_student_home;
    }

    private void openUsageAccess() {
        if (!ProcessUtil.isPermission(this)) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast.makeText(this, "权限不够\n请打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
