package com.srtianxia.bleattendance.ui.teacher.query;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.ui.course.TabPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/1/30.
 */
public class AttendanceFragment extends BaseFragment {

    private final String TAG = "AttendanceFragment";

    private static final String[] PARENT_TITLES = {"应出勤", "BLE接收考勤", "考勤情况"};

    @BindView(R.id.tab_attend)
    TabLayout mTab;
    @BindView(R.id.vp_attend)
    ViewPager mViewPager;

    private List<AttListFragment> mFragmentList = new ArrayList<>();
    private List<String> mTitles = Arrays.asList(PARENT_TITLES);


    @Override
    protected void initView() {
        TabPagerAdapter adapter = new TabPagerAdapter(getChildFragmentManager(), mFragmentList, mTitles);
        for (int i = 0; i < 3; i++) {
            mFragmentList.add(AttListFragment.newInstance(i));
        }
        mViewPager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_attendance;
    }

    public static AttendanceFragment newInstance() {
        Bundle bundle = new Bundle();
        AttendanceFragment attendanceFragment = new AttendanceFragment();
        attendanceFragment.setArguments(bundle);
        return attendanceFragment;
    }

    public void postAttInfo() {
        mFragmentList.get(2).postAttInfo();
    }
}
