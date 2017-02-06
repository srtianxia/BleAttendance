package com.srtianxia.bleattendance.ui.student.attendance;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StuAttendancePresenter extends BasePresenter<StuAttendancePresenter.IStuAttendanceView> {
    private IStuAttModel mStuAttendanceModel;

    public StuAttendancePresenter(IStuAttendanceView baseView) {
        super(baseView);
        mStuAttendanceModel = StuMidModel.getModel(getViewType().getActivity());
        EventBus.getDefault().register(this);
    }


    @Override
    public StudentAttendanceFragment getViewType() {
        return (StudentAttendanceFragment) getView();
    }

    public void startAdv() {
        mStuAttendanceModel.initBle(getView().getUuid());
        mStuAttendanceModel.startAdvertise();
    }


    public void stopAdv() {
        mStuAttendanceModel.stopAdvertise();
    }

    public void notifyCenter() {
        mStuAttendanceModel.notifyCenter(null);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotifySend(OnNotifySend onNotifySend) {
        getView().setAttState();
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
        mStuAttendanceModel.onDestroy();
    }

    public interface IStuAttendanceView extends BaseView {
        void setAttState();

        String getUuid();

    }
}
