package com.srtianxia.bleattendance.ui.teacher.query;

import android.app.Activity;
import android.os.Bundle;

import com.srtianxia.bleattendance.base.view.BaseListFragment;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;
import com.srtianxia.bleattendance.utils.DialogUtils;
import com.srtianxia.bleattendance.utils.ToastUtil;

import java.util.List;

/**
 * Created by srtianxia on 2017/1/30.
 */

public class AttListFragment extends BaseListFragment<String, AttListAdapter> implements AttConditionPresenter.IAttConditionView {
    private static final String ARGS_KEY = "KEY";
    private AttListAdapter mAdapter = new AttListAdapter();
    private int mPosition = 0;

    private AttConditionPresenter mPresenter = new AttConditionPresenter(this);

    private TeacherHomeActivity mHomeActivity;

    @Override
    protected void initView() {
        super.initView();
        mPosition = getArguments().getInt(ARGS_KEY);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHomeActivity = ((TeacherHomeActivity) activity);
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
            if (mHomeActivity.getCourseInfo() != null) {
                mPresenter.getAllStuList(mHomeActivity.getCourseInfo());
            } else {
                ToastUtil.show(getActivity(), "尚未选择考勤课程", true);
                loadFinished();
            }
        } else if (mPosition == 1) {
            loadDataList(mHomeActivity.getNumberList());
            loadFinished();
        } else if (mPosition == 2) {
            if (mHomeActivity.getCourseInfo() != null) {
                mPresenter.loadAttendanceInfo(mHomeActivity.getCourseInfo().jxbID, mHomeActivity.getCurrentWeek(), mHomeActivity.getCourseInfo().hash_day, mHomeActivity.getCourseInfo().hash_lesson);
            } else {
                ToastUtil.show(getActivity(), "尚未选择考勤课程", true);
                loadFinished();
            }
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

    @Override
    public List<String> getBleAttendanceInfo() {
        return mHomeActivity.getNumberList();
    }

    @Override
    public void postSuccess() {
        ToastUtil.show(getActivity(), "上传成功", true);
        DialogUtils.getInstance().dismissProgressDialog();
    }

    @Override
    public void postFailure() {
        DialogUtils.getInstance().dismissProgressDialog();
    }


    // 这里写的 太差了  = =
    public void postAttInfo() {
        if (mHomeActivity.getCourseInfo() != null) {
            DialogUtils.getInstance().showProgressDialog(getActivity(), "考勤信息上传中,请稍后");
            mPresenter.postAttendanceInfo(mHomeActivity.getCourseInfo(), mHomeActivity.getCurrentWeek());
        } else {
            ToastUtil.show(getActivity(), "尚未选择考勤课程", true);
            loadFinished();
        }
    }
}
