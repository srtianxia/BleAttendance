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
import java.util.Collections;
import java.util.List;

/**
 * Created by srtianxia on 2017/1/20.
 */

public class AttConditionPresenter extends BasePresenter<AttConditionPresenter.IAttConditionView> {
    private Api mApi;
    private static final int UN_ATT = 2;
    private static final int ATT = 1;


    public AttConditionPresenter(IAttConditionView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }

    public void postAttendanceInfo(Course course, int week) {
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getStuList(token, course.jxbID)
                .flatMap(stuListEntity -> {
                    String status = mergeAttendanceStatus(stuListEntity, getView().getBleAttendanceInfo());
                    return mApi.postAttendanceInfo(token, course.jxbID, course.hash_day, course.hash_lesson, status, week);
                })
                .compose(RxSchedulersHelper.io2main())
                .subscribe(postAttResultEntity -> {
                            Logger.d(postAttResultEntity);
                            getView().postSuccess();
                        },
                        throwable -> {
                            Logger.d(throwable);
                            getView().postFailure();
                        });
    }


    /**
     * 请求应出勤列表
     *
     * @param course
     */
    public void getAllStuList(Course course) {
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getStuList(token, course.jxbID)
                .map(entity -> stuListEntity2StringList(entity))
                .compose(RxSchedulersHelper.io2main())
                .subscribe(numberList -> {
                    getView().loadAllAttendanceInfoSuccess(numberList);
                }, throwable -> Logger.d(throwable));
    }

    public void loadAttendanceInfo(String jxbID, int week, int hash_day, int hash_lesson) {
        String token = PreferenceManager.getInstance().getString(PreferenceManager.SP_TOKEN_TEACHER, "");
        mApi.getAttendanceInfo(token, jxbID, week, hash_day, hash_lesson)
                .map(attInfoEntity -> attInfoEntity2SortStringList(attInfoEntity))
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::loadAttInfoSuccess, this::loadAttInfoFailure);
    }

    private void loadAttInfoSuccess(List<String> list) {
        getView().loadAttendanceInfo(list);
        Logger.d(list);
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

    private List<String> stuListEntity2StringList(StuListEntity entity) {
        List<String> numberList = new ArrayList<>();
        for (StuInfoEntity data : entity.getData()) {
            numberList.add("学号: " + data.getStuNum() + "\n" + "姓名: " + data.getName());
        }
        return numberList;
    }

    private List<String> attInfoEntity2SortStringList(AttInfoEntity attInfoEntity) {
        List<String> list = new ArrayList<>();
        Collections.sort(attInfoEntity.data, (o1, o2) -> {
            int i = Integer.valueOf(o1.status.get(0));
            int j = Integer.valueOf(o2.status.get(0));
            if (i == j) {
                return 0;
            } else {
                return i < j ? -1 : 1;
            }
        });
        for (AttInfoEntity.AttInfo info : attInfoEntity.data) {
            String s = Integer.valueOf(info.status.get(0)) == ATT ? "出勤" : "缺勤";
            list.add("学号: " + info.stuNum + "\n" + "\n" + "姓名: " + info.stuName + " 考勤状态: " + s);
        }
        return list;
    }


    @Override
    public AttListFragment getViewType() {
        return (AttListFragment) getView();
    }

    public interface IAttConditionView extends BaseView {
        void loadAllAttendanceInfoSuccess(List<String> data);

        void loadAttendanceInfo(List<String> data);

        void loadAllAttendanceInfoFailure();

        List<String> getBleAttendanceInfo();


        void postSuccess();

        void postFailure();
    }
}
