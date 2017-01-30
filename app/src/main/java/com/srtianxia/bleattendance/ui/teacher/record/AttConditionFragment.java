package com.srtianxia.bleattendance.ui.teacher.record;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by srtianxia on 2017/1/20.
 */

public class AttConditionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AttConditionPresenter.IAttConditionView {
    private static final String[] PARENT_TITLES = {"应出勤学生学号", "本地已考勤学号", "已上传考勤学号", "缺勤学生学号"};

    @BindView(R.id.expandable_condition_list)
    ExpandableListView expandableConditionList;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ed_test)
    EditText edTest;
    @BindView(R.id.btn_test)
    Button btnTest;


    // todo 本地以考勤学号从activity中获取
    private TeacherHomeActivity mHomeActivity;

    private AttConditionAdapter mConditionAdapter;

    private AttConditionPresenter mPresenter = new AttConditionPresenter(this);


    private List<String> mTestList = new ArrayList<>();

    List<String> list_0 = new ArrayList<>();
    List<String> list_1 = new ArrayList<>();
    List<String> list_2 = new ArrayList<>();
    List<String> list_3 = new ArrayList<>();
    List<List<String>> list = new ArrayList<>();


    public static AttConditionFragment newInstance() {
        Bundle args = new Bundle();
        AttConditionFragment fragment = new AttConditionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        list.add(list_0);
        list.add(list_1);
        list.add(list_2);
        list.add(list_3);
        expandableConditionList.setAdapter(mConditionAdapter = new AttConditionAdapter(/*getActivity(), */PARENT_TITLES, list));
        mTestList.add("2014210542");
//        mTestList.add();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHomeActivity = (TeacherHomeActivity) activity;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragement_att_condition;
    }

    //    @OnClick(R.id.btn_add)
    public void add() {
        mConditionAdapter.notifyDataSetChanged();
    }

    public void postAttendanceInfo(NewCourseEntity.Course course, int week) {
        mPresenter.postAttendanceInfo(course, week);
//        mPresenter.loadAttendanceInfo(course.jxbID);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    // todo 下拉刷新的时候就要先网络请求再获取activity中的List<Integer>信息
    @Override
    public void onRefresh() {
        if (!list_1.containsAll(mHomeActivity.getNumberList())) {
            list_1.addAll(mHomeActivity.getNumberList());
        }
        mConditionAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.btn_test)
    void test() {
        mTestList.add(edTest.getText().toString());
    }


    @Override
    public void loadAllAttendanceInfoSuccess(List<String> data) {
        list_0.clear();
        list_0.addAll(data);
        mConditionAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadAttendanceInfo(List<String> data) {
        list_2.clear();
        list_2.addAll(data);
        mConditionAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadAllAttendanceInfoFailure() {

    }

    @Override
    public List<String> getBleAttendanceInfo() {
//        return mHomeActivity.getNumberList();
        return mTestList;
    }

}
