package com.srtianxia.bleattendance.ui.teacher.allattendance;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class BeforeAttendancePresenter extends BasePresenter<BeforeAttendancePresenter.IBeforeAttendanceView>{


    public BeforeAttendancePresenter(IBeforeAttendanceView baseView) {
        super(baseView);
    }

    @Override
    public IBeforeAttendanceView getViewType() {
        return getView();
    }

    public interface IBeforeAttendanceView extends BaseView{

    }
}
