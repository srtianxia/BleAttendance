package com.srtianxia.bleattendance.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.srtianxia.bleattendance.base.view.BaseFragment;

import java.util.List;

/**
 * Created by srtianxia on 2017/1/31.
 */

public class BetterVpAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mFragmentList;
    private FragmentManager mFragmentManager;
    private List<String> mTitlesList;


    public BetterVpAdapter(FragmentManager fragmentManager, List<BaseFragment> fragments, List<String> titleList) {
        super(fragmentManager);
        this.mFragmentManager = fragmentManager;
        this.mFragmentList = fragments;
        this.mTitlesList = titleList;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mFragmentManager.beginTransaction().hide(mFragmentList.get(position)).commit();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitlesList != null && mTitlesList.size() > position)
            return mTitlesList.get(position);
        return "";
    }
}
