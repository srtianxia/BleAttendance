package com.srtianxia.bleattendance.ui.course;

import android.util.Log;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.Course;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.entity.TeaCourseEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;
import com.srtianxia.bleattendance.utils.database.DataBaseManager;

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

    /**
     * 加载课表
     * 第一次加载时，数据库为空，则一次请求所有周的数据，并存入数据库。
     * 第二次加载后，则从数据库取数据。
     */
    public void loadData() {
        Log.i(TAG, "loadData");
//        mCourseModel.getData(token,week);
        String flag = PreferenceManager.getInstance().getString(PreferenceManager.SP_LOGIN_FLAG, "");

        getView().showRefreshing();

        //如果从数据库获取不到数据，才进行网络加载。获取到数据了，则直接返回显示。

        if (flag.equals(PreferenceManager.SP_LOGIN_FLAG_STU)) {
            if (DataBaseManager.getInstance().isStuCourse(Integer.parseInt(getView().getWeek())))
                requestStuDataForDB(Integer.parseInt(getView().getWeek()));
            else
                requestStuDataForNet(getView().getWeek());

        } else {
            if (DataBaseManager.getInstance().isTeaCourse(Integer.parseInt(getView().getWeek())))
                requestTeaDataForDB(Integer.parseInt(getView().getWeek()));
            else
                requestTeaDataForNet(getView().getWeek());

        }
        }


    /**
     * 更新课表数据
     * 刷新一次则更新所有周的课表，并保存。
     */
    public void updateData(){

        getView().showRefreshing();

        if (PreferenceManager.getInstance().getString(PreferenceManager.SP_LOGIN_FLAG, "").equals(PreferenceManager.SP_LOGIN_FLAG_STU)) {
            requestStuDataForNet(getView().getWeek());
        } else
            requestTeaDataForNet(getView().getWeek());
    }

    private void requestStuDataForDB(int week){
        NewCourseEntity stuEntity = DataBaseManager.getInstance().queryStuCourse(week);
        loadStuSuccess(stuEntity);
    }

    private void requestTeaDataForDB(int week){
        TeaCourseEntity teaEntity = DataBaseManager.getInstance().queryTeaCourse(week);
        loadTeaSuccess(teaEntity);
    }

    private void requestStuDataForNet(String week){

        String stuToken = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_STUDENT, "");
        mApi.getStuCourse(stuToken, week)
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::loadStuSuccess, this::loadFailure);
    }

    private void requestTeaDataForNet(String week){

        String teaToken = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getTeaCourse(teaToken, week)
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::loadTeaSuccess, this::loadFailure);
    }

    private void loadStuSuccess(NewCourseEntity stuCourseEntity) {
        Log.i(TAG,"loadStuSuccess");
        getView().showCourse(stuCourseEntity.data,getView().getWeek());
        DataBaseManager.getInstance().addStuCourse(stuCourseEntity,Integer.parseInt(getView().getWeek()));
        getView().unshowRefreshing();
    }

    private void loadTeaSuccess(TeaCourseEntity teaCourseEntity) {
        Log.i(TAG,"loadTeaSuccess");
        getView().showCourse(NewCourseEntity.Tea2NewCourse(teaCourseEntity).data,getView().getWeek());
        DataBaseManager.getInstance().addTeaCourse(teaCourseEntity,Integer.parseInt(getView().getWeek()));
        getView().unshowRefreshing();
    }

    private void loadFailure(Throwable throwable) {
        Log.i(TAG,"loadFailure:"+throwable.toString());
        getView().showCourseFailure(throwable);
        getView().unshowRefreshing();
    }

    @Override
    public CourseFragment getViewType() {
        return (CourseFragment) getView();
    }


    public interface ICourseView extends BaseView {

        void showCourse(List<Course> courses,String week);

        void showCourseFailure(Throwable throwable);

        void showRefreshing();

        void unshowRefreshing();

        String getWeek();

    }
}
