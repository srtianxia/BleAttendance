package com.srtianxia.bleattendance.ui.student.attendance;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.ImageView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StudentAttendanceFragment extends BaseFragment implements StuAttendancePresenter.IStuAttendanceView {
    private static final int STATE_ADV = 0;
    private static final int STATE_DIS_ADV = 1;

    @BindView(R.id.btn_advertise)
    Button btnBroadcast;
    @BindView(R.id.img_adv_state)
    ImageView imgAdvState;
    private AnimationDrawable mFrameAnim;

    private int mAdvState = STATE_DIS_ADV;

    private StuAttendancePresenter mPresenter;

    @Override
    protected void initView() {
        mPresenter = new StuAttendancePresenter(this);
        mFrameAnim = (AnimationDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.adv_state_anim);
        imgAdvState.setBackground(mFrameAnim);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_stu_attendance;
    }


    public static StudentAttendanceFragment newInstance() {
        Bundle args = new Bundle();
        StudentAttendanceFragment fragment = new StudentAttendanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.btn_advertise)
    void advertise() {
        mFrameAnim.start();
        mPresenter.startAdv();
    }

    @OnClick(R.id.btn_dis_adv)
    void disAdvertise() {
        mFrameAnim.stop();
        mPresenter.stopAdv();
    }

    @OnClick(R.id.btn_notify)
    void notify_center() {
        mPresenter.notifyCenter();
    }

    @OnClick(R.id.img_adv_state)
    void clickImgAdvState() {
        if (mAdvState == STATE_DIS_ADV) {
            mAdvState = STATE_ADV;
            startAdvStateAnim();
            mPresenter.startAdv();
        } else if (mAdvState == STATE_ADV) {
            mAdvState = STATE_DIS_ADV;
            stopAdvStateAnim();
            mPresenter.stopAdv();
        }
    }

    private void stopAdvStateAnim() {
        mFrameAnim.stop();
    }

    private void startAdvStateAnim() {
        mFrameAnim.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stopAdv();
    }
}
