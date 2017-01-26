package com.srtianxia.bleattendance.ui.teacher.record;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ExpandableListView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by srtianxia on 2017/1/20.
 */

public class AttConditionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AttConditionPresenter.IAttConditionView {
    private static final String[] PARENT_TITLES = {"本地已考勤学号", "已上传考勤学号", "缺勤学生学号"};

    @BindView(R.id.expandable_condition_list)
    ExpandableListView expandableConditionList;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    // todo 本地以考勤学号从activity中获取
    private TeacherHomeActivity mHomeActivity;

    private AttConditionAdapter mConditionAdapter;

    private AttConditionPresenter mPresenter = new AttConditionPresenter(this);

    List<Integer> list_1 = new ArrayList<>();
    List<Integer> list_2 = new ArrayList<>();
    List<Integer> list_3 = new ArrayList<>();
    List<List<Integer>> list = new ArrayList<>();


    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
//        for (int i = 0; i < 3; i++) {
//            list_1.add("list_1 " + i);
//            list_2.add("list_2 " + i);
//            list_3.add("list_3 " + i);
//        }

        list.add(list_1);
        list.add(list_2);
        list.add(list_3);
        expandableConditionList.setAdapter(mConditionAdapter = new AttConditionAdapter(/*getActivity(), */PARENT_TITLES, list));
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
//        list_3.add("xxx - add");
        mConditionAdapter.notifyDataSetChanged();
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
}
