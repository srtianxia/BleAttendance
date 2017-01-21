package com.srtianxia.bleattendance.ui.teacher.record;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;

/**
 * Created by srtianxia on 2017/1/20.
 */

public class AttConditionPresenter extends BasePresenter<AttConditionPresenter.IAttConditionView> {


    public AttConditionPresenter(IAttConditionView baseView) {
        super(baseView);
    }



    @Override
    public AttConditionFragment getViewType() {
        return (AttConditionFragment)getView();
    }

    public interface IAttConditionView extends BaseView {

    }
}
