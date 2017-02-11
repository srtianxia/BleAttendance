package com.srtianxia.bleattendance.ui.teacher.allattendance;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.entity.TeaCourse;
import com.srtianxia.bleattendance.entity.TeaCourseEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;
import com.srtianxia.bleattendance.utils.database.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class BeforeAttendancePresenter extends BasePresenter<BeforeAttendancePresenter.IBeforeAttendanceView>{

    private Api mApi;

    private TeaCourseEntity teaCourseEntity;

    public BeforeAttendancePresenter(IBeforeAttendanceView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }

    @Override
    public IBeforeAttendanceView getViewType() {
        return getView();
    }

    public List<TeaCourse> getData(){

        if (DataBaseManager.getInstance().queryTeaCourse(0) == null){
            requestTeaDataForNet("0");
        }else {
            teaCourseEntity = DataBaseManager.getInstance().queryTeaCourse(0);
        }

        List<TeaCourse> teaCourseList = classFilter(teaCourseEntity);

        if (teaCourseList != null){
            for (int i=0; i<teaCourseList.size(); i++){
                teaCourseList.get(i).course_class = (teaCourseList.get(i).course + " ( " + teaCourseList.get(i).scNum + ")");
            }
            return teaCourseList;
        }
        return null;
    }

    public List<TeaCourse> classFilter(TeaCourseEntity teaCourseEntity){
        if (teaCourseEntity != null){
            List<TeaCourse> teaCourseList = new ArrayList<>();
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < teaCourseEntity.data.size(); i++){
                String str = teaCourseEntity.data.get(i).course + " ( " + teaCourseEntity.data.get(i).scNum + ")";
                if (!stringList.contains(str)){
                    stringList.add(str);
                    teaCourseList.add(teaCourseEntity.data.get(i));
                }
            }
            return teaCourseList;
        }
        return null;
    }

    public TeaCourseEntity getTeaCourseEntity(){
        if (teaCourseEntity != null)
            return teaCourseEntity;
        return null;
    }

    public void requestTeaDataForNet(String week){

        String teaToken = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getTeaCourse(teaToken, week)
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::loadTeaSuccess, this::loadFailure);

    }

    private void loadTeaSuccess(TeaCourseEntity teaCourseEntity) {
        this.teaCourseEntity = teaCourseEntity;

        List<TeaCourse> teaCourseList = classFilter(teaCourseEntity);

        if (teaCourseList != null){
            for (int i=0; i<teaCourseList.size(); i++){
                teaCourseList.get(i).course_class = (teaCourseList.get(i).course + " ( " + teaCourseList.get(i).scNum + ")");
            }

        }
        getView().loadData(teaCourseList);
        getView().loadFinish();
    }

    private void loadFailure(Throwable throwable) {
        getView().showFailure();
    }

    public void requestAttInfoForNet(String jxbID){
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER,"");
        mApi.getAttendanceInfo(token,jxbID,0,0,0)
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::requestAttInfoSuccess,this::loadFailure);
    }

    private void requestAttInfoSuccess(AttInfoEntity entity){
        getView().saveAttInfoEntity(entity);
        getView().showAttInfoFragment();
    }

    public interface IBeforeAttendanceView extends BaseView{

        void loadFinish();
        void saveAttInfoEntity(AttInfoEntity entity);
        void showAttInfoFragment();
        void showFailure();
        void loadData(List<TeaCourse> teaCourseList);
    }

}
