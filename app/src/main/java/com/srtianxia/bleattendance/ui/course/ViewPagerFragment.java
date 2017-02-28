package com.srtianxia.bleattendance.ui.course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srtianxia.bleattendance.base.view.BaseFragment;

/**
 * Created by 梅梅 on 2017/2/27.
 */
public abstract class ViewPagerFragment extends BaseFragment{

    protected boolean isVisible = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 该方法在每次fragment切换时回调，由isVisibleToUser判断fragment是否显示
     * @param isVisibleToUser   当fragment显示时为ture,隐藏时为false
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isVisible = true;
            lazyload();
        }else {
            isVisible = false;
        }
    }

    //// TODO: 2017/2/27 从一个fragment 滑到 另一个fragment的过程，serUserVisibleHint()方法执行吗？仿类似微信的处理（加载中……）
    protected abstract void lazyload();

    protected abstract void onVisible();

    protected abstract void onInVisible();

}
