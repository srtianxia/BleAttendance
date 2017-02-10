package com.srtianxia.bleattendance.ui.teacher.allattendance;

import android.util.Log;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.entity.TeaCourse;
import com.srtianxia.bleattendance.entity.TeaCourseEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class JxbListPresenter extends BasePresenter<JxbListPresenter.JxbListView>{

    private Api mApi;

    public JxbListPresenter(JxbListView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class,ApiUtil.getBaseUrl());
    }

    @Override
    public JxbListView getViewType() {
        return getView();
    }

    public List<String> getData(List<TeaCourse> teaCourseList){
        if (teaCourseList != null){
            List<String> stringList = new ArrayList<>();
            for (int i=0; i<teaCourseList.size(); i++){
                stringList.add(teaCourseList.get(i).scNum);
            }
            return stringList;
        }
        return null;
    }

    public List<TeaCourse> JxbFilter(String choiceClass,TeaCourseEntity entity){

        if (entity != null && !choiceClass.equals("")){
            List<TeaCourse> teaCourseList = new ArrayList<>();
            List<String> stringList = new ArrayList<>();

            for (int i=0; i<entity.data.size(); i++){
                if (entity.data.get(i).course.equals(choiceClass)
                        && !stringList.contains(entity.data.get(i).jxbID)){
                    stringList.add(entity.data.get(i).jxbID);
                    teaCourseList.add(entity.data.get(i));
                }
            }
            return teaCourseList;
        }

        return null;
    }

    public void requestAttInfoForNet(String jxbID){
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER,"");
        mApi.getAttendanceInfo(token,jxbID,0,0,0)
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::requestAttInfoSuccess,this::requestAttInfoFaliure);
    }

    private void requestAttInfoSuccess(AttInfoEntity entity){
        getView().saveAttInfo(entity);
    }

    private void requestAttInfoFaliure(Throwable throwable){
        Log.i("TAG",throwable.toString());
    }

    public interface JxbListView extends BaseView{
        void saveAttInfo(AttInfoEntity entity);
    }
}
