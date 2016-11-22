package com.srtianxia.bleattendance.ui.activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.di.component.DaggerStudentComponent;
import com.srtianxia.bleattendance.di.module.StudentModule;
import com.srtianxia.bleattendance.entity.CourseEntity;
import com.srtianxia.bleattendance.presenter.StudentPresenter;
import com.srtianxia.bleattendance.utils.LockUtil;
import com.srtianxia.bleattendance.utils.SchoolCalendar;
import com.srtianxia.bleattendance.utils.ScreenUtils;
import com.srtianxia.bleattendance.widget.CoursesTableView;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class StudentActivity extends BaseActivity implements StudentPresenter.IStudentView {
    @BindView(R.id.tv_month) TextView tvMonth;
    @BindView(R.id.container_course_number) LinearLayout containerCourseNumber;
    @BindView(R.id.container_course_data) LinearLayout containerCourseData;
    @BindView(R.id.container_course_time) LinearLayout containerCourseTime;
    @BindView(R.id.view_course_table) CoursesTableView viewCourseTable;
    @BindView(R.id.pb_course) ProgressBar pbCourse;
    @BindView(R.id.fab) FloatingActionButton fabShift;

    @Inject
    StudentPresenter mPresenter;

    //本周的周数
    private int week = 12;


    @Override protected void initView() {
        DaggerStudentComponent.builder()
            .studentModule(new StudentModule(this))
            .build()
            .inject(this);

        //pbCourse.setVisibility(View.VISIBLE);
        //
        //mPresenter.loadCourse("2014211819");
        openUsageAccess();
    }


    private void initCourseTextView() {
        tvMonth.setText("5月");
        String[] arrayOfString = getResources().getStringArray(R.array.week);
        int i = ScreenUtils.getScreenHeight(this);
        if (ScreenUtils.px2Dp(this, i) > 700.0F) {
            containerCourseTime.setLayoutParams(
                new LinearLayout.LayoutParams((int) ScreenUtils.dp2Px(this, 40.0F), i));
            //            viewCourseTable.setLayoutParams(new LinearLayout.LayoutParams(-1, i));
        }
        //添加星期行和那一行上面的日期行
        for (int j = 0; j < 7; j++) {
            SchoolCalendar schoolCalendar = new SchoolCalendar(week, j + 1);
            TextView weekDay = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1, 1.0F);
            weekDay.setLayoutParams(layoutParams);
            weekDay.setText(arrayOfString[j]);
            weekDay.setGravity(Gravity.CENTER);
            containerCourseTime.addView(weekDay);

            TextView monthData = new TextView(this);
            monthData.setLayoutParams(layoutParams);
            monthData.setText(schoolCalendar.getCurrentDay().get(Calendar.DAY_OF_MONTH) + "");
            monthData.setTextSize(14.0F);
            monthData.setGravity(Gravity.CENTER);
            containerCourseData.addView(monthData);
        }
        //添加节数行
        for (int k = 0; k < 12; k++) {
            TextView courseNumber = new TextView(this);
            courseNumber.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0F));
            courseNumber.setText((k + 1) + "");
            courseNumber.setGravity(Gravity.CENTER);
            this.containerCourseNumber.addView(courseNumber);
        }
    }


    @Override protected int getLayoutRes() {
        return R.layout.activity_student;
    }


    @Override public void setCourseTable(CourseEntity courses) {
        pbCourse.setVisibility(View.GONE);
        initCourseTextView();
        viewCourseTable.addContentView(courses.data);
    }


    @OnClick(R.id.fab)
    void clickToAttendance() {
        mPresenter.startAdvertise("2014211819");
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.stopAdvertise();
        mPresenter.detachView();
    }


    private void openUsageAccess() {
        if (LockUtil.isPermissionForTest(StudentActivity.this) == false) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast.makeText(StudentActivity.this, "权限不够\n请打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾",
                Toast.LENGTH_LONG).show();
        }
    }
}
