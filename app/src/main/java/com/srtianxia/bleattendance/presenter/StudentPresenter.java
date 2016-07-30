package com.srtianxia.bleattendance.presenter;

import com.orhanobut.logger.Logger;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.CourseEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class StudentPresenter extends BasePresenter<StudentPresenter.IStudentView> {
    private Api mApi;

    public StudentPresenter(IStudentView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }

    public void loadCourse(String stuNum) {
        mApi.loadCourseTable(stuNum).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<CourseEntity>() {
                @Override public void call(CourseEntity courseEntity) {
                    Logger.d(courseEntity);
                    getView().setCourseTable(courseEntity);
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    Logger.d(throwable.getMessage());
                }
            });
    }


    public interface IStudentView extends BaseView {
        void setCourseTable(CourseEntity courses);
    }
}
