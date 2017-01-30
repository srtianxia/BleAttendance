package com.srtianxia.bleattendance.ui.course;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.entity.StuEntity;
import com.srtianxia.bleattendance.ui.student.home.StudentHomeActivity;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;
import com.srtianxia.bleattendance.utils.DensityUtil;
import com.srtianxia.bleattendance.utils.DialogUtils;
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
    @BindView(R.id.linearlayout_course_time)
    LinearLayout mCourse_time;
    @BindView(R.id.swipe_refresh_layout_course)
    SwipeRefreshLayout mCourseSwipeRefreshLayout;
    @BindView(R.id.course_tab_view_course)
    CourseTableView mCourseTableView;

    private static final String TAG = "CourseFragment";

    public static final String BUNDLE_KEY = "WEEK_NUM";

    private int mWeek;      //记录用户选择的周数
    private StuEntity mStu;

    private CoursePresenter coursePresenter;

    private List<NewCourseEntity.Course> courseList = new ArrayList<>();

    private TeacherHomeActivity mTeacherHomeActivity;
    private StudentHomeActivity mStudentHomeActivity;

    @Override
    protected void initView() {
        mWeek = getArguments().getInt(BUNDLE_KEY);
        coursePresenter = new CoursePresenter(this);
        int mScreenHeight = DensityUtil.getScreenHeight(getContext());

        //适配屏幕高度大于700dp的设备
        if (mScreenHeight > DensityUtil.dp2px(getContext(), 700)) {
            mCourse_time.setLayoutParams(new LinearLayout.LayoutParams(DensityUtil.dp2px(getContext(), 40), mScreenHeight));
            mCourseTableView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mScreenHeight));
        }
        mMonth.setText("9" + "\n" + "月");

        initDraw();

        mCourseTableView.setOnLongClickListener(courses -> {
            DialogUtils.getInstance().showDialog(getActivity(), "课程选择", "是否选择：第 " + getWeek() + "  周 " + courses.list.get(0).course + " ？",
                    new DialogUtils.OnButtonChooseListener() {
                        @Override
                        public void onPositive() {
                            if (mTeacherHomeActivity != null) {
                                mTeacherHomeActivity.setAttCourse(courses, getWeek());
                            }
                        }

                        @Override
                        public void onNegative() {

                        }
                    });
        });

        // todo:如果mWeek = 本周，则……
        coursePresenter.loadData();

        mCourseSwipeRefreshLayout.setOnRefreshListener(() -> {
            coursePresenter.loadData();
        });
    }

    private void initDraw() {

        String[] data = getResources().getStringArray(R.array.course_weeks);

        for (int i = 0; i < 7; i++) {
            //添加周数
            TextView tv = new TextView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tv.setLayoutParams(params);
            tv.setText(data[i]);
            tv.setGravity(Gravity.CENTER);
            mWeeks.addView(tv);
        }
        for (int i = 0; i < 12; i++) {
            TextView tv_course_time = new TextView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            tv_course_time.setLayoutParams(params);
            tv_course_time.setText(i + 1 + "");
            tv_course_time.setGravity(Gravity.CENTER);
            mCourse_time.addView(tv_course_time);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TeacherHomeActivity) {
            mTeacherHomeActivity = (TeacherHomeActivity) activity;
        }
    }

    @Override
    public void showCourse(List<NewCourseEntity.Course> courses) {
        List<NewCourseEntity.Course> tempCourseList = new ArrayList<>();
        tempCourseList.addAll(courses);
        if (mCourseTableView != null) {
            mCourseTableView.clearList();
            mCourseTableView.addContentView(tempCourseList);
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
