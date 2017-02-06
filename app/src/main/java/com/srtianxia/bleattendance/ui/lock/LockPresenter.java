package com.srtianxia.bleattendance.ui.lock;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;

/**
 * Created by 梅梅 on 2017/2/4.
 */
public class LockPresenter extends BasePresenter<LockPresenter.ILockView>{


    public LockPresenter(ILockView baseView) {
        super(baseView);
    }

    @Override
    public ILockView getViewType() {
        return getView();
    }

    public interface ILockView extends BaseView{
        void showTime(String time);
    }
}
