package com.srtianxia.bleattendance.ui.fragment;

import android.os.Bundle;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.presenter.StuAttendancePresenter;
import com.srtianxia.bleattendance.rx.RxPeripheralClient;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StudentAttendanceFragment extends BaseFragment implements StuAttendancePresenter.IStuAttendanceView{
    @BindView(R.id.btn_advertiser) Button btnBroadcast;
    private RxPeripheralClient client;
    StuAttendancePresenter mPresenter;

    @Override protected void initView() {
        client = RxPeripheralClient.create(getActivity());
        mPresenter = new StuAttendancePresenter(this);
    }

    @Override protected int getLayoutRes() {
        return R.layout.fragment_stu_attendance;
    }



    public static StudentAttendanceFragment newInstance() {
        Bundle args = new Bundle();
        StudentAttendanceFragment fragment = new StudentAttendanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.btn_advertiser)
    void advertiser() {
        mPresenter.startAdv();
    }
}
