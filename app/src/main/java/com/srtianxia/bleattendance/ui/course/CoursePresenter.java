package com.srtianxia.bleattendance.ui.course;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;

/**
 * Created by 梅梅 on 2017/1/20.
 */
public class CoursePresenter extends BasePresenter<CoursePresenter.ICourseView>{

    public CoursePresenter(ICourseView baseView) {
        super(baseView);
    }

    @Override
    public ICourseView getViewType() {
        return getView();
    }

    public interface ICourseView extends BaseView{

    }
}
