package com.srtianxia.bleattendance.ui.teacher.allattendance;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
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
public class ClassListPresenter extends BasePresenter<ClassListPresenter.IClassListView>{

    private Api mApi;

    private TeaCourseEntity teaCourseEntity;

    public ClassListPresenter(IClassListView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }

    @Override
    public IClassListView getViewType() {
        return getView();
    }

    public List<String> getData(){

        if (DataBaseManager.getInstance().queryTeaCourse(0) == null){
            requestTeaDataForNet("0");
        }else {
            teaCourseEntity = DataBaseManager.getInstance().queryTeaCourse(0);
        }

        List<TeaCourse> teaCourseList = classFilter(teaCourseEntity);

        if (teaCourseList != null){
            List<String> stringList = new ArrayList<>();
            for (int i=0; i<teaCourseList.size(); i++){
                stringList.add(teaCourseList.get(i).course);
            }
            return stringList;
        }
        return null;
    }

    public List<TeaCourse> classFilter(TeaCourseEntity teaCourseEntity){
        if (teaCourseEntity != null){
            List<TeaCourse> teaCourseList = new ArrayList<>();
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < teaCourseEntity.data.size(); i++){
                if (!stringList.contains(teaCourseEntity.data.get(i).course)){
                    stringList.add(teaCourseEntity.data.get(i).course);
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

    private void requestTeaDataForNet(String week){

        String teaToken = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getTeaCourse(teaToken, week)
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::loadTeaSuccess, this::loadFailure);
    }

    private void loadTeaSuccess(TeaCourseEntity teaCourseEntity) {
        this.teaCourseEntity = teaCourseEntity;
    }

    private void loadFailure(Throwable throwable) {

    }

    public interface IClassListView extends BaseView{
        void loadFinish();
    }

}
