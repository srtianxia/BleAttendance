package com.srtianxia.bleattendance.ui.teacher.record;

import android.app.Activity;
import android.os.Bundle;

import com.srtianxia.bleattendance.base.view.BaseListFragment;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srtianxia on 2017/1/30.
 */

public class AttListFragment extends BaseListFragment<String, AttListAdapter> implements AttConditionPresenter.IAttConditionView {
    private static final String ARGS_KEY = "KEY";
    private AttListAdapter mAdapter = new AttListAdapter();
    private int mPosition = 0;

    private AttConditionPresenter mPresenter = new AttConditionPresenter(this);


    private NewCourseEntity.Course mCurrentCourse;


    @Override
    protected void initView() {
        super.initView();
        mPosition = getArguments().getInt(ARGS_KEY);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCurrentCourse = ((TeacherHomeActivity) activity).getCourseInfo();
    }

    @Override
    public AttListAdapter getAdapter() {
        return mAdapter;
    }


    public static AttListFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARGS_KEY, position);
        AttListFragment fragment = new AttListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void loadListData() {
        if (mPosition == 0) {
            mPresenter.getAllStuList(mCurrentCourse);
        } else if (mPosition == 1) {

            // todo 获取本地蓝牙考勤信息
        } else if (mPosition == 2) {
            mPresenter.postAttendanceInfo(mCurrentCourse, 3);
        }
    }

    @Override
    public void loadAllAttendanceInfoSuccess(List<String> data) {
        loadDataList(data);
        loadFinished();
    }

    @Override
    public void loadAttendanceInfo(List<String> data) {
        loadDataList(data);
        loadFinished();
    }

    @Override
    public void loadAllAttendanceInfoFailure() {

    }

    public void postAttInfo() {
        // todo  week  !!!
        mPresenter.postAttendanceInfo(mCurrentCourse, 0);
    }

    @Override
    public List<String> getBleAttendanceInfo() {
        List<String> list = new ArrayList<>();
        list.add("2010210373");
        list.add("2014210541");
        return list;
    }
}
