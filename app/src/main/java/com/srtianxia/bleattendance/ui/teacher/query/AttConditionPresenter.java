package com.srtianxia.bleattendance.ui.teacher.query;

import com.orhanobut.logger.Logger;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.entity.Course;
import com.srtianxia.bleattendance.entity.StuInfoEntity;
import com.srtianxia.bleattendance.entity.StuListEntity;
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
    private static final int UN_ATT = 0;
    private static final int ATT = 1;


    public AttConditionPresenter(IAttConditionView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }

    public void postAttendanceInfo(Course course, int week) {
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getStuList(token, course.jxbID).flatMap(stuListEntity -> {
            String status = mergeAttendanceStatus(stuListEntity, getView().getBleAttendanceInfo());
            return mApi.postAttendanceInfo(token, course.jxbID, course.hash_day, course.hash_lesson, status, week);
        }).compose(RxSchedulersHelper.io2main())
                .subscribe(postAttResultEntity -> {
                            Logger.d(postAttResultEntity);
                            loadAttendanceInfo(course.jxbID, week);
                        },
                        throwable -> Logger.d(throwable));
    }


    /**
     * 请求应出勤列表
     *
     * @param course
     */
    public void getAllStuList(Course course) {
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getStuList(token, course.jxbID).compose(RxSchedulersHelper.io2main())
                .subscribe(entity -> {
                    List<String> numberList = new ArrayList<>();
                    for (StuInfoEntity data : entity.getData()) {
                        numberList.add(data.getStuNum());
                    }
                    getView().loadAllAttendanceInfoSuccess(numberList);
                }, throwable -> Logger.d(throwable));
    }

    public void loadAttendanceInfo(String jxbID, int week) {
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getAttendanceInfo(token, jxbID, week).compose(RxSchedulersHelper.io2main()).subscribe(this::loadAttInfoSuccess, this::loadAttInfoFailure);
    }

    private void loadAttInfoSuccess(AttInfoEntity attInfoEntity) {
        List<String> list = new ArrayList<>();
        for (AttInfoEntity.AttInfo info : attInfoEntity.data) {
            list.add(info.stuNum + " status " + info.status);
        }
        getView().loadAttendanceInfo(list);
        Logger.d(attInfoEntity);
    }

    private void loadAttInfoFailure(Throwable throwable) {
        Logger.d(throwable);
    }


    /**
     * @param entity             从网络请求到的信息
     * @param attentionStuNumber 从蓝牙考勤到的信息
     * @return
     */
    private String mergeAttendanceStatus(StuListEntity entity, List<String> attentionStuNumber) {
        StringBuilder builder = new StringBuilder();
        for (StuInfoEntity e : entity.getData()) {
            if (attentionStuNumber.contains(e.getStuNum())) {
                builder.append(ATT + ",");
            } else {
                builder.append(UN_ATT + ",");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }



    @Override
    public AttConditionFragment getViewType() {
        return (AttConditionFragment) getView();
    }

    public interface IAttConditionView extends BaseView {
        void loadAllAttendanceInfoSuccess(List<String> data);

        void loadAttendanceInfo(List<String> data);

        void loadAllAttendanceInfoFailure();

        List<String> getBleAttendanceInfo();
    }
}
