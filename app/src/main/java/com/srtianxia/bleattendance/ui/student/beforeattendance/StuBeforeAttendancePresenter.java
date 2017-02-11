package com.srtianxia.bleattendance.ui.student.beforeattendance;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.StuAttInfoEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 梅梅 on 2017/2/11.
 */
public class StuBeforeAttendancePresenter extends BasePresenter<StuBeforeAttendancePresenter.IStuBeforeAttView>{

    private Api mApi;

    public StuBeforeAttendancePresenter(IStuBeforeAttView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class,ApiUtil.getBaseUrl());
    }

    @Override
    public IStuBeforeAttView getViewType() {
        return getView();
    }

    public void requestStuAttForNet(){
        getView().loading();
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_STUDENT,"");
        mApi.getStuAttendanceInfo(token,0,0,0)
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::requestStuAttSuccess,this::requestStuAttFailure);
    }

    private void requestStuAttSuccess(StuAttInfoEntity entity){
        List<StuAttInfoEntity.ShowData> dataList = stuAttInfoFiler(entity);
        getView().loadData(dataList);
        getView().loadFinish();
    }

    private void requestStuAttFailure(Throwable throwable){
        getView().loadFailure(throwable.toString());
        getView().loadFinish();
    }

    private List<StuAttInfoEntity.ShowData> stuAttInfoFiler(StuAttInfoEntity entity){

        List<StuAttInfoEntity.ShowData> dataList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        HashMap<String,Integer> map = new HashMap();

        for (int i=0; i<entity.data.size(); i++){
            if (!map.containsKey(entity.data.get(i).jxbID)){
                map.put(entity.data.get(i).jxbID,0);
                stringList.add(entity.data.get(i).jxbID);
            }
        }

        for (int j=0; j<entity.data.size(); j++){
            if (map.containsKey(entity.data.get(j).jxbID) && entity.data.get(j).status.equals("1")){
                int tempInt = map.get(entity.data.get(j).jxbID) + 1;
                map.put(entity.data.get(j).jxbID,tempInt);
            }
        }

        for (int k=0; k<map.size(); k++){
            StuAttInfoEntity.ShowData tempData = new StuAttInfoEntity.ShowData();
            tempData.jxbID = stringList.get(k);
            tempData.att_num = map.get(stringList.get(k));
            dataList.add(tempData);
        }
        return dataList;
    }

    public interface IStuBeforeAttView extends BaseView {
        void loadData(List<StuAttInfoEntity.ShowData> dataList);
        void loadFailure(String throwable);
        void loadFinish();
        void loading();
    }
}
