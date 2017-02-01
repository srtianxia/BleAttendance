package com.srtianxia.bleattendance.ui.course;

import android.util.Log;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.entity.TeaCourseEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;

import java.util.List;

/**
 * Created by 梅梅 on 2017/1/20.
 */
public class CoursePresenter extends BasePresenter<CoursePresenter.ICourseView> {

    private final String TAG = "CoursePresenter";
    private Api mApi;
    private ICourseModel mCourseModel;

    public CoursePresenter(ICourseView baseView) {
        super(baseView);
//        mCourseModel = CourseMidModel.getModel(getViewType().getActivity());
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }

    public void loadData() {
        Log.i(TAG, "loadData");
//        mCourseModel.getData(token,week);
        String flag = PreferenceManager.getInstance().getString(PreferenceManager.SP_LOGIN_FLAG, "");
        String token = "";

        if (flag == "") {

        } else {
            getView().showRefreshing();
            if (flag.equals(PreferenceManager.SP_LOGIN_FLAG_STU)) {
                token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_STUDENT, "");
                mApi.getStuCourse(token, getView().getWeek())
                        .compose(RxSchedulersHelper.io2main())
                        .subscribe(this::loadStuSuccess, this::loadFailure);
            } else {
                token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
                mApi.getTeaCourse(token, getView().getWeek())
                        .compose(RxSchedulersHelper.io2main())
                        .subscribe(this::loadTeaSuccess, this::loadFailure);
            }
        }

    }

    private void loadStuSuccess(NewCourseEntity newCourseEntity) {
        getView().showCourse(newCourseEntity.data);
        getView().unshowRefreshing();
    }

    private void loadTeaSuccess(TeaCourseEntity teaCourseEntity) {
        getView().showCourse(NewCourseEntity.Tea2NewCourse(teaCourseEntity).data);
        getView().unshowRefreshing();
    }

    private void loadFailure(Throwable throwable) {
        getView().showCourseFailure(throwable);
        getView().unshowRefreshing();
    }

    @Override
    public CourseFragment getViewType() {
        return (CourseFragment) getView();
    }


    public interface ICourseView extends BaseView {

        void showCourse(List<NewCourseEntity.Course> courses);

        void showCourseFailure(Throwable throwable);

        void showRefreshing();

        void unshowRefreshing();

        String getWeek();
    }
}
