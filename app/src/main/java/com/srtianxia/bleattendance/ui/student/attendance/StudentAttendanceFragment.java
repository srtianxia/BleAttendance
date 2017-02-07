package com.srtianxia.bleattendance.ui.student.attendance;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.service.LockService;
import com.srtianxia.bleattendance.ui.student.home.StudentHomeActivity;
import com.srtianxia.bleattendance.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StudentAttendanceFragment extends BaseFragment implements StuAttendancePresenter.IStuAttendanceView {
    private static final int STATE_ADV = 0;
    private static final int STATE_DIS_ADV = 1;

    private static final String UN_ATT = "未考勤";
    private static final String ATT = "已考勤";


    //    @BindView(R.id.btn_advertise)
//    Button btnBroadcast;
    @BindView(R.id.img_adv_state)
    ImageView imgAdvState;
    @BindView(R.id.tv_attention_state)
    TextView tvAttentionState;

    private AnimationDrawable mFrameAnim;

    private LockService mLockService;

    private int mAdvState = STATE_DIS_ADV;
    private String mAttState = UN_ATT;


    private StuAttendancePresenter mPresenter;

    private StudentHomeActivity mActivity;

    @Override
    protected void initView() {
        mPresenter = new StuAttendancePresenter(this);
        mFrameAnim = (AnimationDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.adv_state_anim);
        imgAdvState.setBackground(mFrameAnim);
        tvAttentionState.setText(mAttState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (StudentHomeActivity) activity;
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


    //    @OnClick(R.id.btn_advertise)
    void advertise() {
        mFrameAnim.start();
        mPresenter.startAdv();
    }

    //    @OnClick(R.id.btn_dis_adv)
    void disAdvertise() {
        mFrameAnim.stop();
        mPresenter.stopAdv();
    }

    //    @OnClick(R.id.btn_notify)
    void notify_center() {
        mPresenter.notifyCenter();
    }

    @OnClick(R.id.img_adv_state)
    void clickImgAdvState() {
        if (getUuid() != null) {
            if (mAdvState == STATE_DIS_ADV) {
                mAdvState = STATE_ADV;
                startAdvStateAnim();
                mPresenter.startAdv();
            } else if (mAdvState == STATE_ADV) {
                mAdvState = STATE_DIS_ADV;
                stopAdvStateAnim();
                mPresenter.stopAdv();
            }
        } else {
            ToastUtil.show(getActivity(), "未选择考勤课程", true);
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

    @Override
    public void setAttState() {
        tvAttentionState.setText(ATT);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), LockService.class);
                getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                getActivity().startService(intent);
            }
        });

    }

    @Override
    public String getUuid() {
        return mActivity.getUuid();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LockService.LockBinder lockBinder = (LockService.LockBinder) iBinder;
            mLockService = lockBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
