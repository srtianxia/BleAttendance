package com.srtianxia.bleattendance.ui.course;

import android.util.Log;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;

import java.util.List;

/**
 * Created by 梅梅 on 2017/1/20.
 */
public class CoursePresenter extends BasePresenter<CoursePresenter.ICourseView>{

    private final String TAG = "CoursePresenter";
    private Api mApi;
    private ICourseModel mCourseModel;

    public CoursePresenter(ICourseView baseView) {
        super(baseView);
//        mCourseModel = CourseMidModel.getModel(getViewType().getActivity());
        mApi = ApiUtil.createApi(Api.class,ApiUtil.getBaseUrl());
    }

    public void loadData(){
        Log.i(TAG,"loadData");
//        mCourseModel.getData(token,week);
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_STUDENT,"");

        mApi.getStuCourseWithoutWeek(token)
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::loadSuccess,this::loadFailure);
    }

    private void loadSuccess(NewCourseEntity stuCourseEntity){
        Log.i(TAG,"loadSuccess");
        getView().showCourse(stuCourseEntity.data);
    }

    private void loadFailure(Throwable throwable){
        Log.i(TAG,"loadFailure");
        getView().showCourseFailure(throwable);
    }

    @Override
    public CourseFragment getViewType() {
        return (CourseFragment) getView();
    }


    public interface ICourseView extends BaseView{

        void showCourse(List<NewCourseEntity.Course> courses);

        void showCourseFailure(Throwable throwable);

        String getWeek();
    }
}
