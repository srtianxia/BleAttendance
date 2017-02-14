package com.srtianxia.bleattendance.ui.course;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.Course;
import com.srtianxia.bleattendance.entity.CourseTimeEntity;
import com.srtianxia.bleattendance.entity.StuEntity;
import com.srtianxia.bleattendance.ui.student.home.StudentHomeActivity;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;
import com.srtianxia.bleattendance.utils.DensityUtil;
import com.srtianxia.bleattendance.utils.DialogUtils;
import com.srtianxia.bleattendance.utils.SchoolCalendar;
import com.srtianxia.bleattendance.widget.CourseTableView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/1/20.
 */
public class CourseFragment extends BaseFragment implements CoursePresenter.ICourseView {
    @BindView(R.id.text_month)
    TextView mMonth;
    @BindView(R.id.linearlayout_weekday)
    LinearLayout mWeekday;
    @BindView(R.id.linearlayout_weeks)
    LinearLayout mWeeks;
    @BindView(R.id.swipe_refresh_layout_course)
    SwipeRefreshLayout mCourseSwipeRefreshLayout;
    @BindView(R.id.course_tab_view_course)
    CourseTableView mCourseTableView;
    @BindView(R.id.linearlayout_course_time)
    LinearLayout mCourse_Time;

    private static final String TAG = "CourseFragment";

    public static final String BUNDLE_KEY = "WEEK_NUM";

    private int mWeek;      //记录用户选择的周数
    private StuEntity mStu;

    private CoursePresenter coursePresenter;

    private List<Course> courseList = new ArrayList<>();

    private TeacherHomeActivity mTeacherHomeActivity;
    private StudentHomeActivity mStudentHomeActivity;

    @Override
    protected void initView() {
        mWeek = getArguments().getInt(BUNDLE_KEY);

        coursePresenter = new CoursePresenter(this);
        int mScreenHeight = DensityUtil.getScreenHeight(getContext());

        //适配屏幕高度大于700dp的设备
        if (mScreenHeight > DensityUtil.dp2px(getContext(), 700)) {
            mCourse_Time.setLayoutParams(new LinearLayout.LayoutParams(DensityUtil.dp2px(getContext(), 40), mScreenHeight));
            mCourseTableView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mScreenHeight));
        }

        initDraw();

        // todo:如果mWeek = 本周，则……
        coursePresenter.loadData();

        mCourseTableView.setOnLongClickListener(courses -> {
            DialogUtils.getInstance().showDialog(getActivity(), "课程选择", "是否选择：第 " + getWeek() + "  周 " + courses.list.get(0).course + " ？",
                    new DialogUtils.OnButtonChooseListener() {
                        @Override
                        public void onPositive() {
                            if (mTeacherHomeActivity != null) {
                                mTeacherHomeActivity.setAttCourse(courses, getWeek());
                            } else if (mStudentHomeActivity != null) {
                                mStudentHomeActivity.setAttCourse(courses, getWeek());
                            }
                        }

                        @Override
                        public void onNegative() {

                        }
                    });
        });

        mCourseSwipeRefreshLayout.setOnRefreshListener(() -> {
            coursePresenter.updateData();
        });
    }

    private void initDraw() {

        String[] str_weeks = getResources().getStringArray(R.array.course_weeks);
        String[] str_times = getResources().getStringArray(R.array.course_time_1);
        String month = new SchoolCalendar(mWeek, 1).getCurrentMonth() + "";

        if (mWeek == 0) {
            mWeekday.setVisibility(View.GONE);
        } else {
            mMonth.setText(month + "\n" + "月");
            mWeekday.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < 7; i++) {
            //添加日期
            SchoolCalendar calendar = new SchoolCalendar(mWeek, i + 1);
            TextView tv_day = new TextView(getActivity());
            LinearLayout.LayoutParams params_day = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tv_day.setLayoutParams(params_day);
            tv_day.setText(calendar.getCurrentDay() + "");
            tv_day.setTextColor(getResources().getColor(R.color.colorGrey_content));
            tv_day.setGravity(Gravity.CENTER);
            mWeekday.addView(tv_day);
            //添加周数
            TextView tv = new TextView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tv.setLayoutParams(params);
            tv.setText(str_weeks[i]);
            tv.setGravity(Gravity.CENTER);
            mWeeks.addView(tv);
        }

        for (int i = 0; i < 12; i++) {
            TextView time = new TextView(getContext());
            TextView course = new TextView(getContext());
            time.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            time.setText(str_times[i]);
            time.setTextColor(getResources().getColor(R.color.colorGrey_content));
            time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            course.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            course.setText((i + 1) + "");
            LinearLayout.LayoutParams time_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            LinearLayout.LayoutParams course_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

            time.setLayoutParams(time_lp);
            course.setLayoutParams(course_lp);

            mCourse_Time.addView(time);
            mCourse_Time.addView(course);

        }

        List<CourseTimeEntity> courseTimeEntities = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            CourseTimeEntity temp = new CourseTimeEntity(str_times[i], (i + 1) + "");
            courseTimeEntities.add(temp);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TeacherHomeActivity) {
            mTeacherHomeActivity = (TeacherHomeActivity) activity;
        } else if (activity instanceof StudentHomeActivity) {
            mStudentHomeActivity = (StudentHomeActivity) activity;
        }
    }

    @Override
    public void showCourse(List<Course> courses, String week) {
        List<Course> tempCourseList = new ArrayList<>();
        tempCourseList.addAll(courses);
        if (mCourseTableView != null) {
            mCourseTableView.clearList();
            mCourseTableView.addContentView(tempCourseList, week);
        }
    }

    @Override
    public void showCourseFailure(Throwable throwable) {
        mCourseSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showRefreshing() {
        if (mCourseSwipeRefreshLayout != null) {
            mCourseSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void unshowRefreshing() {
        if (mCourseSwipeRefreshLayout != null) {
            mCourseSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public String getWeek() {
        return mWeek + "";
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_course;
    }

    public static CourseFragment newInstance() {
        Bundle bundle = new Bundle();
        CourseFragment fragment = new CourseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
