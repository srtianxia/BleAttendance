package com.srtianxia.bleattendance.ui.course;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.ui.teacher.query.AttListFragment;

import java.util.List;

/**
 * Created by 梅梅 on 2017/1/26.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private List<AttListFragment> mFragmentList;
    private List<String> mTitlesList;

    public TabPagerAdapter(FragmentManager fm, List<AttListFragment> fragmentList, List<String> titlesList) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitlesList = titlesList;

    }

    @Override
    public Fragment getItem(int position) {
        if (mFragmentList != null && mFragmentList.size() != 0)
            return mFragmentList.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if (mFragmentList != null )
            return mFragmentList.size();
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitlesList != null && mTitlesList.size() > position)
            return mTitlesList.get(position);
        return "";
    }
}
