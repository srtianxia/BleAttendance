package com.srtianxia.bleattendance.presenter;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.model.StuAttendanceModel;
import com.srtianxia.bleattendance.ui.fragment.StudentAttendanceFragment;

/**
 * Created by srtianxia on 2016/11/26.
 */

public class StuAttendancePresenter extends BasePresenter<StuAttendancePresenter.IStuAttendanceView> {
    private StuAttendanceModel mStuAttendanceModel;

    public StuAttendancePresenter(IStuAttendanceView baseView) {
        super(baseView);
        mStuAttendanceModel = new StuAttendanceModel();
    }


    @Override public StudentAttendanceFragment getViewType() {
        return (StudentAttendanceFragment) getView();
    }

    public void startAdv() {
        mStuAttendanceModel.startAdvertise();
    }

    public interface IStuAttendanceView extends BaseView {

    }
}
