package com.srtianxia.bleattendance.ui.teacher.record;

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
public class AttendFragment extends BaseFragment{

    private final String TAG = "AttendFragment";

    private static final String[] PARENT_TITLES = {"应出勤学生学号", "本地已考勤学号", "已上传考勤学号", "缺勤学生学号"};

    @BindView(R.id.tab_attend)TabLayout mTab;
    @BindView(R.id.vp_attend)ViewPager mViewPager;

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private List<String> mTitles = Arrays.asList(PARENT_TITLES);

    @Override
    protected void initView() {
        TabPagerAdapter adapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(),mFragmentList,mTitles);
        mViewPager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewPager);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_attend;
    }

    public static AttendFragment newInstance(){
        Bundle bundle = new Bundle();
        AttendFragment attendFragment = new AttendFragment();
        attendFragment.setArguments(bundle);
        return attendFragment;
    }
}
