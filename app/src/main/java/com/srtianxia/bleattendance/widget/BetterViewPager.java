package com.srtianxia.bleattendance.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by srtianxia on 2017/1/31.
 */

public class BetterViewPager extends ViewPager {
    public BetterViewPager(Context context) {
        super(context);
    }

    public BetterViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void detachViewFromParent(View child) {
        super.detachViewFromParent(child);
    }

    @Override
    public void attachViewToParent(View child, int index, ViewGroup.LayoutParams params) {
        super.attachViewToParent(child, index, params);
    }
}
