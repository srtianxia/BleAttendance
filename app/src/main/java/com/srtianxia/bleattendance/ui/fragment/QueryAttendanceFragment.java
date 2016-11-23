package com.srtianxia.bleattendance.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.entity.AttendEntity;
import com.srtianxia.bleattendance.presenter.QueryAttendancePresenter;
import com.srtianxia.bleattendance.ui.adapter.AttendAdapter;
import com.srtianxia.blelibs.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2016/9/13.
 */
public class QueryAttendanceFragment extends BaseFragment implements QueryAttendancePresenter.IQueryView{

    @BindView(R.id.rv_attend) RecyclerView rvAttend;
    @BindView(R.id.btn_attend_query) Button btnquery;
    @BindView(R.id.tv_course) TextView tvcourse;
    @BindView(R.id.tv_all) TextView tvall;
    @BindView(R.id.tv_attend) TextView tvattend;
    @BindView(R.id.tv_absences) TextView tvabsences;

    QueryAttendancePresenter mPresenter;
    AttendAdapter mAdapter;

    @Override
    protected void initView() {
        mPresenter = new QueryAttendancePresenter(this);
        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.queryAttend();
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_attend;
    }

    public static QueryAttendanceFragment newInstance(){
        Bundle args = new Bundle();
        QueryAttendanceFragment fragment = new QueryAttendanceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void querySuccess(AttendEntity.Data data) {
        tvcourse.setText(data.course);
        tvall.setText(""+data.all);
        tvattend.setText(""+(data.all-data.absenceNum));
        tvabsences.setText(""+data.absenceNum);
        rvAttend.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        rvAttend.setAdapter(mAdapter = new AttendAdapter(data.absences));

    }

    @Override
    public void queryFailure(String cause) {
        ToastUtil.show(getActivity(),cause,true);
    }
}
