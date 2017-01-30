package com.srtianxia.bleattendance.ui.teacher.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.ui.course.CourseContainerFragment;
import com.srtianxia.bleattendance.ui.teacher.attendance.TeacherScanFragment;
import com.srtianxia.bleattendance.ui.teacher.record.AttendanceFragment;
import com.srtianxia.bleattendance.widget.CourseTableView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class TeacherHomeActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.bottom_view)
    BottomNavigationView mBottomView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.tv_current_course)
//    TextView tvCurrentCourse;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;
    //    @BindView(R.id.btn_post)
//    Button btnPost;
    @BindView(R.id.ed_test)
    EditText editText;


    private TeacherScanFragment mTeacherScanFragment = TeacherScanFragment.newInstance();

//    private AttConditionFragment mAttConditionFragment = AttConditionFragment.newInstance();

    private AttendanceFragment mAttendanceFragment = AttendanceFragment.newInstance();

    private List<Integer> mNumberList = new ArrayList<>();

    private CourseContainerFragment mCourseContainerFragment;

    public List<Integer> mStuNumber = new ArrayList<>();

    // 考勤需要post
    private NewCourseEntity.Course mCurrentCourse;
    private String mCurrentWeek;

    @Override
    protected void initView() {

        mCourseContainerFragment = CourseContainerFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mTeacherScanFragment)
                .add(R.id.fragment_container, mAttendanceFragment)
                .add(R.id.fragment_container, mCourseContainerFragment)
                .show(mTeacherScanFragment)
                .hide(mAttendanceFragment)
//                .hide(mAttConditionFragment)
                .hide(mCourseContainerFragment)
                .commit();

//        tvCurrentCourse.setText(getPrefixText() + getText(R.string.current_course_no_choose));

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
                toolbar.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .hide(mAttendanceFragment).hide(mCourseContainerFragment)
                        .show(mTeacherScanFragment).commit();
                break;
            case R.id.bottom_nav_attendance:
//                toolbar.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .hide(mTeacherScanFragment).hide(mCourseContainerFragment)
                        .show(mAttendanceFragment).commit();

                break;
            case R.id.bottom_nav_table:
                toolbar.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .hide(mTeacherScanFragment).hide(mAttendanceFragment)
                        .show(mCourseContainerFragment).commit();

        }
        return true;
    }

    public void addNumber(Integer number) {
        mNumberList.add(number);
    }

    public NewCourseEntity.Course getCourseInfo() {
        return mCurrentCourse;
    }


    public int getCurrentWeek() {
        return Integer.parseInt(mCurrentWeek);
    }

    public List<String> getNumberList() {
        List<String> list = new ArrayList<>();
        for (Integer i : mNumberList) {
            list.add("" + i);
        }
        return list;
    }

    public void setAttCourse(CourseTableView.CourseList course, String week) {
        mCurrentCourse = course.list.get(0);
        mCurrentWeek = week;
//        tvCurrentCourse.setText(getPrefixText() + mCurrentCourse.course);
    }

    // 获取textView 展示当前考勤课程的前缀文字
    private String getPrefixText() {
        return getText(R.string.current_course_title) + " ";
    }


    @OnClick(R.id.fab_add_att_info)
    void onFabClick() {
        mNumberList.add(Integer.valueOf(editText.getText().toString()));
    }

    //    @OnClick(R.id.btn_post)
    void onBtnPostClick() {
        // todo 携带考勤数据 week
//        mAttConditionFragment.postAttendanceInfo(mCurrentCourse, 0);

    }
}
