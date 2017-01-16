package com.srtianxia.bleattendance.ui.student.table;

import android.os.Bundle;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class CourseTableFragment extends BaseFragment {
    @Override protected void initView() {

    }


    @Override protected int getLayoutRes() {
        return R.layout.fragment_stu_course_table;
    }


    public static CourseTableFragment newInstance() {
        Bundle args = new Bundle();
        CourseTableFragment fragment = new CourseTableFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
