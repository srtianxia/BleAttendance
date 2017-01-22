package com.srtianxia.bleattendance.ui.course;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/1/20.
 */
public class CourseFragment extends BaseFragment implements CoursePresenter.ICourseView{

    @BindView(R.id.text_month) TextView mmonth;
    @BindView(R.id.linearlayout_weekday) LinearLayout mweekday;
    @BindView(R.id.linearlayout_weeks) LinearLayout mweeks;
    @BindView(R.id.linearlayout_course_time) LinearLayout mcourse_time;
    @BindView(R.id.swipe_refresh_layout_course) SwipeRefreshLayout mcourse;

    private CoursePresenter coursePresenter;


    @Override
    protected void initView() {
        String[] data = getResources().getStringArray(R.array.course_weeks);
        coursePresenter = new CoursePresenter(this);

        mmonth.setText("9"+"\n"+"月");

        for (int i=0; i<7; i++){
            //添加周数
            TextView tv = new TextView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            tv.setLayoutParams(params);
            tv.setText(data[i]);
            tv.setGravity(Gravity.CENTER);
            mweeks.addView(tv);
        }

        for (int i=0; i<12; i++){
            TextView tv_course_time = new TextView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            tv_course_time.setLayoutParams(params);
            tv_course_time.setText(i+1+"");
            tv_course_time.setGravity(Gravity.CENTER);
            mcourse_time.addView(tv_course_time);
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
