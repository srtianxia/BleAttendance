package com.srtianxia.bleattendance.ui.teacher.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.entity.Course;
import com.srtianxia.bleattendance.ui.MainActivity;
import com.srtianxia.bleattendance.ui.course.CourseContainerFragment;
mport com.srtianxia.bleattendance.ui.teacher.allattendance.BeforeAttendanceFragment;
import com.srtianxia.bleattendance.ui.teacher.attendance.TeacherScanFragment;
import com.srtianxia.bleattendance.ui.teacher.query.AttendanceFragment;
import com.srtianxia.bleattendance.utils.DialogUtils;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.UiHelper;
import com.srtianxia.bleattendance.utils.UuidUtil;
import com.srtianxia.bleattendance.utils.database.DataBaseManager;
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
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.tv_tea_cover)
    TextView mCover;
    @BindView(R.id.fab_tea_menu)
    FloatingActionMenu mFabMenu;
    @BindView(R.id.fab_tea_connect)
    FloatingActionButton mFabConnect;
    @BindView(R.id.fab_tea_input)
    FloatingActionButton mFabInput;
    @BindView(R.id.fab_tea_scan)
    FloatingActionButton mFabScan;

    //    @BindView(R.id.tv_current_course)
//    TextView tvCurrentCourse;
    //    @BindView(R.id.btn_post)
//    Button btnPost;


    private TeacherScanFragment mTeacherScanFragment = TeacherScanFragment.newInstance();

//    private AttConditionFragment mAttConditionFragment = AttConditionFragment.newInstance();

    private AttendanceFragment mAttendanceFragment = AttendanceFragment.newInstance();

    private BeforeAttendanceFragment mBeforeAttendanceFragment = BeforeAttendanceFragment.newInstance();

    private List<Integer> mNumberList = new ArrayList<>();

    private CourseContainerFragment mCourseContainerFragment;

    public List<Integer> mStuNumber = new ArrayList<>();

    // 考勤需要post
    private Course mCurrentCourse;
    private String mCurrentWeek;

    private String mUuid;

    @Override
    protected void initView() {

        mCourseContainerFragment = CourseContainerFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mTeacherScanFragment)
                .add(R.id.fragment_container, mAttendanceFragment)
                .add(R.id.fragment_container, mCourseContainerFragment)
                .add(R.id.fragment_container,mBeforeAttendanceFragment)
                .show(mTeacherScanFragment)
                .hide(mAttendanceFragment)
                .hide(mCourseContainerFragment)
                .hide(mBeforeAttendanceFragment)
                .commit();

//        tvCurrentCourse.setText(getPrefixText() + getText(R.string.current_course_no_choose));

        mFabMenu.setClosedOnTouchOutside(true);
        mFabMenu.setOnMenuButtonClickListener(onMenuButton);

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
            case R.id.action_logout:
                PreferenceManager.getInstance().setString(PreferenceManager.SP_TOKEN_TEACHER, "");
                PreferenceManager.getInstance().setString(PreferenceManager.SP_LOGIN_FLAG, "");
                DataBaseManager.getInstance().deleteTeaCourse();
                UiHelper.startActivity(this, MainActivity.class);
                finish();
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
                        .hide(mBeforeAttendanceFragment).show(mTeacherScanFragment)
                        .commit();
                if (mFabMenu.isOpened())
                    closeFabMenu();

                break;
            case R.id.bottom_nav_attendance:
//                toolbar.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .hide(mTeacherScanFragment).hide(mCourseContainerFragment)
                        .hide(mBeforeAttendanceFragment).show(mAttendanceFragment)
                        .commit();
                if (mFabMenu.isOpened())
                    closeFabMenu();

                break;
            case R.id.bottom_nav_before:
                getSupportFragmentManager().beginTransaction()
                        .hide(mTeacherScanFragment).hide(mCourseContainerFragment)
                        .hide(mAttendanceFragment).show(mBeforeAttendanceFragment)
                        .commit();
                if (mFabMenu.isOpened())
                    closeFabMenu();

                break;

            case R.id.bottom_nav_table:
                toolbar.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .hide(mTeacherScanFragment).hide(mAttendanceFragment)
                        .hide(mBeforeAttendanceFragment).show(mCourseContainerFragment)
                        .commit();
                if (mFabMenu.isOpened())
                    closeFabMenu();

                break;
        }
        return true;
    }

    public void addNumber(Integer number) {
        if (!mNumberList.contains(number)) {
            mNumberList.add(number);
        }
    }

    public Course getCourseInfo() {
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
        this.mCurrentCourse = course.list.get(0);
        this.mCurrentWeek = week;
        this.mUuid = UuidUtil.generateUuid(course.list.get(0).classroom);
//        tvCurrentCourse.setText(getPrefixText() + mCurrentCourse.course);
    }

    // 获取textView 展示当前考勤课程的前缀文字
    private String getPrefixText() {
        return getText(R.string.current_course_title) + " ";
    }

    private View.OnClickListener onMenuButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mFabMenu.isOpened()){
                closeFabMenu();
            }else {
                openFabMenu();
            }
        }
    };

    @OnClick(R.id.tv_tea_cover)
    void onTvCover(){
        if (mCover.getVisibility() == View.VISIBLE){
            closeFabMenu();
        }
    }

    @OnClick(R.id.fab_tea_connect)
    void onFabConnect(){
        mTeacherScanFragment.connectAll();
        closeFabMenu();
    }

    @OnClick(R.id.fab_tea_scan)
    void onFabScan(){
        mTeacherScanFragment.startScan();
        closeFabMenu();
    }

    @OnClick(R.id.fab_tea_input)
    void onFabInput(){
        DialogUtils.getInstance().showInputDialog(this, "输入学号", "请输入特殊考勤方式学生的学号 ", new DialogUtils.OnButtonChooseListener() {
            @Override
            public void onPositive() {
                closeFabMenu();
            }

            @Override
            public void onNegative() {
                closeFabMenu();
            }

            @Override
            public void onEditTextContent(String string) {
                super.onEditTextContent(string);
                mNumberList.add(Integer.valueOf(string));
            }
        });
    }

    //    @OnClick(R.id.btn_post)
    void onBtnPostClick() {
        // todo 携带考勤数据 week
//        mAttConditionFragment.postAttendanceInfo(mCurrentCourse, 0);

    }


    public String getUuid() {
        return mUuid;
    }

    private void closeFabMenu(){
        mFabMenu.close(true);
        mCover.setVisibility(View.INVISIBLE);
    }

    private void openFabMenu(){
        mFabMenu.open(true);
        mCover.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (mFabMenu.isOpened()){
            closeFabMenu();
        }else {
            super.onBackPressed();
        }
    }
}
