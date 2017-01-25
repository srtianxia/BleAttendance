package com.srtianxia.bleattendance.ui.course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.entity.StuEntity;
import com.srtianxia.bleattendance.utils.DensityUtil;
import com.srtianxia.bleattendance.widget.CourseTableView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/1/20.
 */
public class CourseFragment extends BaseFragment implements CoursePresenter.ICourseView{

    @BindView(R.id.text_month) TextView mMonth;
    @BindView(R.id.linearlayout_weekday) LinearLayout mWeekday;
    @BindView(R.id.linearlayout_weeks) LinearLayout mWeeks;
    @BindView(R.id.linearlayout_course_time) LinearLayout mCourse_time;
    @BindView(R.id.swipe_refresh_layout_course) SwipeRefreshLayout mCourseSwipeRefreshLayout;
    @BindView(R.id.course_tab_view_course) CourseTableView mCourseTableView;

    private static final String TAG = "CourseFragment";

    private int mweek;      //记录用户选择的周数
    private StuEntity mStu;

    private CoursePresenter coursePresenter;

    private List<NewCourseEntity.Course> courseList = new ArrayList<>();

    @Override
    protected void initView() {
        String[] data = getResources().getStringArray(R.array.course_weeks);
        coursePresenter = new CoursePresenter(this);

        int mScreenHeight = DensityUtil.getScreenHeight(getContext());

        //适配屏幕高度大于700dp的设备
        if (mScreenHeight > DensityUtil.dp2px(getContext(),700)){
            mCourse_time.setLayoutParams(new LinearLayout.LayoutParams(DensityUtil.dp2px(getContext(),40),mScreenHeight));
            mCourseTableView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,mScreenHeight));
        }

        mMonth.setText("9"+"\n"+"月");

        for (int i=0; i<7; i++){
            //添加周数
            TextView tv = new TextView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            tv.setLayoutParams(params);
            tv.setText(data[i]);
            tv.setGravity(Gravity.CENTER);
            mWeeks.addView(tv);
        }

        for (int i=0; i<12; i++){
            TextView tv_course_time = new TextView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            tv_course_time.setLayoutParams(params);
            tv_course_time.setText(i+1+"");
            tv_course_time.setGravity(Gravity.CENTER);
            mCourse_time.addView(tv_course_time);
        }

        mCourseSwipeRefreshLayout.setOnRefreshListener(() -> {
            //如果用户登陆了，则刷新课表数据
            if (mStu != null){
                loadCourse(mweek);
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //如果mweek = 本周，则……

        loadCourse(mweek);

    }

    private void loadCourse(int week){
        for (int i = 0;i<6;i++){
            NewCourseEntity.Course testCourse = new NewCourseEntity.Course();
            testCourse.hash_day = 0;
            testCourse.hash_lesson = i;
            testCourse.begin_lesson = i*2+1;
            testCourse.day = "星期二";
            testCourse.lesson = "1、2节";
            testCourse.course = "数据库";
            testCourse.teacher = "陈乔松";
            testCourse.classroom = "4201";
            testCourse.rawWeek = "1-18周";
            testCourse.weekBegin = 1;
            testCourse.weekEnd = 18;
            testCourse.type = "限选课";
            testCourse.period = 2;
            testCourse.id = "123123456";

            testCourse.week = new ArrayList<>();
            for (int j=0;j<18;j++){
                testCourse.week.add(j);
            }
            courseList.add(testCourse);
        }

        List<NewCourseEntity.Course> tempCourseList = new ArrayList<>();
        tempCourseList.addAll(courseList);

        if (mCourseTableView != null){
            mCourseTableView.clearList();
            mCourseTableView.addContentView(tempCourseList);
            Log.i(TAG,"tempCourseList.size() = " + tempCourseList.size());
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_course;
    }

    public static CourseFragment newInstance(){
        Bundle bundle = new Bundle();
        CourseFragment fragment = new CourseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
