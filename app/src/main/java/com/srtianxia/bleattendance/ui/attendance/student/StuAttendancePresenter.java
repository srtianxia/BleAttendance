package com.srtianxia.bleattendance.ui.attendance.student;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StuAttendancePresenter extends BasePresenter<StuAttendancePresenter.IStuAttendanceView> {
    private IStuAttModel mStuAttendanceModel;

    public StuAttendancePresenter(IStuAttendanceView baseView) {
        super(baseView);
        mStuAttendanceModel = StuMidModel.getModel(getViewType().getActivity());
    }


    @Override
    public StudentAttendanceFragment getViewType() {
        return (StudentAttendanceFragment) getView();
    }

    public void startAdv() {
        mStuAttendanceModel.startAdvertise();
    }


    public void stopAdv() {
        mStuAttendanceModel.stopAdvertise();
    }

    public interface IStuAttendanceView extends BaseView {

    }
}
