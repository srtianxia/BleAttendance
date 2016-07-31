package com.srtianxia.bleattendance.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.component.DaggerStudentComponent;
import com.srtianxia.bleattendance.entity.CourseEntity;
import com.srtianxia.bleattendance.module.StudentModule;
import com.srtianxia.bleattendance.presenter.StudentPresenter;
import com.srtianxia.bleattendance.utils.SchoolCalendar;
import com.srtianxia.bleattendance.utils.ScreenUtils;
import com.srtianxia.bleattendance.widget.CoursesTableView;
import java.util.Calendar;
import javax.inject.Inject;

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

        pbCourse.setVisibility(View.VISIBLE);

        mPresenter.loadCourse("2014211819");
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
}
