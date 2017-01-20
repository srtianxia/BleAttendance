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
import butterknife.OnClick;

/**
 * Created by srtianxia on 2017/1/20.
 */

public class AttConditionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.expandable_condition_list)
    ExpandableListView expandableConditionList;

    private static final String[] PARENT_TITLES = {"本地已考勤学号", "已上传考勤学号", "缺勤名单"};
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    // todo 本地以考勤学号从activity中获取
    private TeacherHomeActivity mHomeActivity;

    private AttConditionAdapter mConditionAdapter;


    List<String> list_1 = new ArrayList<>();
    List<String> list_2 = new ArrayList<>();
    List<String> list_3 = new ArrayList<>();
    List<List<String>> list = new ArrayList<>();


    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        for (int i = 0; i < 3; i++) {
            list_1.add("list_1 " + i);
            list_2.add("list_2 " + i);
            list_3.add("list_3 " + i);
        }

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

    @OnClick(R.id.btn_add)
    void add() {
        list_3.add("xxx - add");
        mConditionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
