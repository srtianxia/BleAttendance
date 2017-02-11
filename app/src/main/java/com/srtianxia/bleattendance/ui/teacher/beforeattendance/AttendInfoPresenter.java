package com.srtianxia.bleattendance.ui.teacher.beforeattendance;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;

import java.util.Collections;
import java.util.List;

/**
 * Created by 梅梅 on 2017/2/10.
 */
public class AttendInfoPresenter extends BasePresenter{

    private Api mApi;

    public AttendInfoPresenter(BaseView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class,ApiUtil.getBaseUrl());
    }

    @Override
    public BaseView getViewType() {
        return getView();
    }

    public AttInfoEntity absenceFiler(AttInfoEntity entity){
        if (entity != null){
            for (int i=0; i<entity.data.size(); i++){
                int counter = 0;
                for (int j=0; j<entity.data.get(i).status.size(); j++){
                    if (entity.data.get(i).status.get(j).equals("1"))
                        counter++;
                }
                entity.data.get(i).absence = counter;
            }
            return entity;
        }
        return null;
    }

    public List<AttInfoEntity.AttInfo> sortForAbsence(List<AttInfoEntity.AttInfo> attInfos){
        if (attInfos != null){
            List<AttInfoEntity.AttInfo> attInfoList = attInfos;
            for (int i=0; i<attInfoList.size(); i++){
                Collections.sort(attInfoList);
            }
            return attInfoList;
        }
        return null;
    }

    public interface AttendInfoView extends BaseView{

    }
}
