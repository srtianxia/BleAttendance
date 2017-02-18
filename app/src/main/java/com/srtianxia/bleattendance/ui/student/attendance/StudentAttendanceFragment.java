package com.srtianxia.bleattendance.ui.student.attendance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.service.LockService;
import com.srtianxia.bleattendance.ui.student.home.StudentHomeActivity;
import com.srtianxia.bleattendance.utils.DialogUtils;
import com.srtianxia.bleattendance.utils.ProcessUtil;
import com.srtianxia.bleattendance.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StudentAttendanceFragment extends BaseFragment implements StuAttendancePresenter.IStuAttendanceView {
    private static final int STATE_ADV = 1;
    private static final int STATE_DIS_ADV = 2;

    private static final String UN_ATT = "未考勤";
    private static final String ATT = "已考勤";


    //    @BindView(R.id.btn_advertise)
//    Button btnBroadcast;
    @BindView(R.id.img_adv_state)
    ImageView imgAdvState;
    @BindView(R.id.tv_attention_state)
    TextView tvAttentionState;
    @BindView(R.id.tv_start)Button start;

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
        dialogOpenAccess();
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
//    void advertise() {
//        mFrameAnim.start();
//        mPresenter.startAdv();
//    }
//
//    @OnClick(R.id.btn_dis_adv)
//    void disAdvertise() {
//        mFrameAnim.stop();
//        mPresenter.stopAdv();
//    }
//
//    @OnClick(R.id.btn_notify)
//    void notify_center() {
//        mPresenter.notifyCenter();
//    }

    @OnClick(R.id.tv_start)
    void start(){
        Intent intent = new Intent(getActivity(), LockService.class);
        getActivity().startService(intent);
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
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getActivity(),LockService.class);
        getActivity().stopService(intent);
    }

    @Override
    public void setAttState() {
        tvAttentionState.setText(ATT);
        Intent intent = new Intent(getActivity(), LockService.class);
        getActivity().startService(intent);
    }

    @Override
    public String getUuid() {
        return mActivity.getUuid();
    }


    private void dialogOpenAccess() {
        if (!ProcessUtil.isPermission(getActivity())) {
            DialogUtils.getInstance().showDialog(getActivity(), "申请必要权限", "请在有权查看使用情况的应用中，为这个App打上勾!", new DialogUtils.OnButtonChooseListener() {
                @Override
                public void onPositive() {
                    openUsageAccess();
                }

                @Override
                public void onNegative() {
                    ToastUtil.show(getActivity(), "必须取得权限", true);
                    getActivity().finish();
                }
            });
        }
    }

    // todo 申请权限的result
    private void openUsageAccess() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
