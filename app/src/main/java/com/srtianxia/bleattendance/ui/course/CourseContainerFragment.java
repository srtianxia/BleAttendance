package com.srtianxia.bleattendance.ui.course;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/1/26.
 */
public class CourseContainerFragment extends BaseFragment{

    @BindView(R.id.tab_course_container)TabLayout mTabs;
    @BindView(R.id.vp_course_container)ViewPager mViewPager;

    private TabPagerAdapter mAdapter;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    private ViewPager.OnPageChangeListener mTabsListener;
    private ViewPager.OnPageChangeListener mViewPagerListener;

    @Override
    protected void initView() {

        initData();
        initListener();
        mAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(),mFragmentList,mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);

    }

    private void initData() {

        mTitles.addAll(Arrays.asList(getResources().getStringArray(R.array.course_title_weeks)));

        if (mFragmentList.isEmpty()){
            for (int i = 0; i < mTitles.size(); i++){
                CourseFragment courseFragment = new CourseFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(CourseFragment.BUNDLE_KEY,i);
                courseFragment.setArguments(bundle);
                mFragmentList.add(courseFragment);
            }
        }

    }

    private void initListener() {

        mViewPager.addOnPageChangeListener(mTabsListener = new TabLayout.TabLayoutOnPageChangeListener(mTabs));
        mViewPager.addOnPageChangeListener(mViewPagerListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //修改toolbarTitle;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_course_container;
    }

    public static CourseContainerFragment newInstance(){
        Bundle bundle = new Bundle();
        CourseContainerFragment fragment = new CourseContainerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
