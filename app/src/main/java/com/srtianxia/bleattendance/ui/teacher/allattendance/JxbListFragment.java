package com.srtianxia.bleattendance.ui.teacher.allattendance;

import android.os.Bundle;

import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.base.view.BaseListFragment;
import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.entity.TeaCourse;
import com.srtianxia.bleattendance.entity.TeaCourseEntity;

import java.util.List;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class JxbListFragment extends BaseListFragment implements JxbListPresenter.JxbListView{

    private JxbListAdapter mAdapter = new JxbListAdapter();

    private JxbListPresenter mPresenter = new JxbListPresenter(this);

    @Override
    protected void initView() {
        super.initView();
        mAdapter.setOnJxbItemClickListener(new JxbListAdapter.OnJxbItemClickListener() {
            @Override
            public void onClick(int position, String jxbID) {
                mPresenter.requestAttInfoForNet(jxbID);
                ((BeforeAttendanceFragment)getParentFragment()).showAttInfoFragment();
            }
        });
    }

    @Override
    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void loadListData() {
        String choiceClass = ((BeforeAttendanceFragment)getParentFragment()).getChoiceClass();
        TeaCourseEntity entity =  ((BeforeAttendanceFragment)getParentFragment()).getTeaCourseEntity();

        List<TeaCourse> teaCourseList = mPresenter.JxbFilter(choiceClass,entity);

        loadDataList(teaCourseList);
        loadFinish();
    }

    public void saveAttInfo(AttInfoEntity entity){
        ((BeforeAttendanceFragment)getParentFragment()).setAttInfoEntity(entity);
    }

    private void loadFinish(){
        loadFinished();
    }

    public static JxbListFragment newInstance(){
        Bundle bundle = new Bundle();
        JxbListFragment fragment = new JxbListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
