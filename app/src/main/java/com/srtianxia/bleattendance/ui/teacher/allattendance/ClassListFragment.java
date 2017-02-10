package com.srtianxia.bleattendance.ui.teacher.allattendance;

import android.os.Bundle;
import android.util.Log;

import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.base.view.BaseListFragment;
import com.srtianxia.bleattendance.entity.TeaCourseEntity;

import java.util.List;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class ClassListFragment extends BaseListFragment implements ClassListPresenter.IClassListView{

    private final String TAG = "ClassListFragment";

    private ClassListPresenter mPresenter = new ClassListPresenter(this);

    private ClassListAdapter mAdapter = new ClassListAdapter();

    @Override
    protected void initView() {
        super.initView();
        mAdapter.setOnClassItemClickListener(new ClassListAdapter.OnClassItemClickListener() {
            @Override
            public void onClick(int position, String choiceClass) {
                BeforeAttendanceFragment fragment = (BeforeAttendanceFragment)(ClassListFragment.this.getParentFragment());
                TeaCourseEntity teaCourseEntity = mPresenter.getTeaCourseEntity();
                fragment.setTeaCourseEntity(teaCourseEntity);
                fragment.setChoiceClass(choiceClass);
                fragment.showJxbFragment();
                Log.i(TAG,"showJxbFragment");
            }
        });
    }

    @Override
    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void loadListData() {
        List<String> stringList = mPresenter.getData();
        if (stringList != null){

            loadDataList(stringList);
            loadFinish();

        }else {

        }
    }

    public static ClassListFragment newInstance(){
        Bundle bundle = new Bundle();
        ClassListFragment fragment = new ClassListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void loadFinish(){
        loadFinished();
    }

}
