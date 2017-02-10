package com.srtianxia.bleattendance.ui.teacher.allattendance;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.entity.TeaCourseEntity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class BeforeAttendanceFragment extends BaseFragment{

    @BindView(R.id.framelayout_before)FrameLayout mFrameLayout;
    @BindView(R.id.btn_before_search)Button mBtnSearch;

    private BeforeAttendancePresenter mPresenter;

    private ClassListFragment mClassFragment = ClassListFragment.newInstance();

    private JxbListFragment mJxbFragment = JxbListFragment.newInstance();

    private AttendInfoFragment mAttInfoFragment = AttendInfoFragment.newInstance();

    private String mClass = "";

    private TeaCourseEntity mTeaCourseEntity;

    private AttInfoEntity mAttInfoEntity;

    @Override
    protected void initView() {
        getChildFragmentManager().beginTransaction()
                .add(R.id.framelayout_before,mClassFragment)
                .add(R.id.framelayout_before,mJxbFragment)
                .add(R.id.framelayout_before,mAttInfoFragment)
                .hide(mClassFragment)
                .hide(mJxbFragment)
                .hide(mAttInfoFragment)
                .commit();
    }

    public void showJxbFragment(){
        getChildFragmentManager().beginTransaction()
                .hide(mClassFragment)
                .hide(mAttInfoFragment)
                .show(mJxbFragment)
                .commit();
    }

    public void showAttInfoFragment(){
        getChildFragmentManager().beginTransaction()
                .hide(mClassFragment)
                .hide(mJxbFragment)
                .show(mAttInfoFragment)
                .commit();
    }

    public void setChoiceClass(String mClass){
        this.mClass = mClass;
    }

    public String getChoiceClass(){
        return mClass;
    }

    public void setTeaCourseEntity(TeaCourseEntity entity){
        this.mTeaCourseEntity = entity;
    }

    public TeaCourseEntity getTeaCourseEntity(){
        if (mTeaCourseEntity != null){
            return mTeaCourseEntity;
        }
        return null;
    }

    public void setAttInfoEntity(AttInfoEntity entity){
        mAttInfoEntity = entity;
    }

    public AttInfoEntity getAttInfoEntity(){
        if (mAttInfoEntity != null){
            return mAttInfoEntity;
        }
        return null;
    }

    @OnClick(R.id.btn_before_search)
    void onSearch(){
        getChildFragmentManager().beginTransaction()
                .hide(mJxbFragment)
                .hide(mAttInfoFragment)
                .show(mClassFragment).commit();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_before_attendance;
    }

    public static BeforeAttendanceFragment newInstance(){
        Bundle bundle = new Bundle();
        BeforeAttendanceFragment allAttendanceFragment = new BeforeAttendanceFragment();
        allAttendanceFragment.setArguments(bundle);
        return allAttendanceFragment;
    }
}
