package com.srtianxia.bleattendance.ui.teacher.record;

import com.orhanobut.logger.Logger;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srtianxia on 2017/1/20.
 */

public class AttConditionPresenter extends BasePresenter<AttConditionPresenter.IAttConditionView> {
    private Api mApi;

    public AttConditionPresenter(IAttConditionView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }

    public void postAttendanceInfo(NewCourseEntity.Course course, int week) {
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.postAttendanceInfo(token, course.jxbID, course.hash_day, course.hash_lesson, "2332", week);
    }

    // week 暂时都为3
    public void loadAttendanceInfo(String jxbID) {
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getAttendanceInfo(token, jxbID, 3).compose(RxSchedulersHelper.io2main()).subscribe(this::loadAttInfoSuccess, this::loadAttInfoFailure);
    }


    private void loadAttInfoSuccess(AttInfoEntity attInfoEntity) {
        List<String> list = new ArrayList<>();
        for (AttInfoEntity.AttInfo info : attInfoEntity.data) {
            list.add(info.stuNum);
        }
        getView().loadAllAttendanceInfoSuccess(list);
        Logger.d(attInfoEntity);
    }

    private void loadAttInfoFailure(Throwable throwable) {
        Logger.d(throwable);
    }

    @Override
    public AttConditionFragment getViewType() {
        return (AttConditionFragment) getView();
    }

    public interface IAttConditionView extends BaseView {
        void loadAllAttendanceInfoSuccess(List<String> data);

        void loadAllAttendanceInfoFailure();
    }
}
