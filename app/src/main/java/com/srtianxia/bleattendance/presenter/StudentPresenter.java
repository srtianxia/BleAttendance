package com.srtianxia.bleattendance.presenter;

import android.content.Intent;
import com.orhanobut.logger.Logger;
import com.srtianxia.bleattendance.service.LockService;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.CourseEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import com.srtianxia.bleattendance.ui.activity.StudentActivity;
import com.srtianxia.blelibs.BLEPeripheral;
import com.srtianxia.blelibs.callback.OnConnectListener;
import com.srtianxia.blelibs.utils.ToastUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Deprecated
public class StudentPresenter extends BasePresenter<StudentPresenter.IStudentView> {
    private Api mApi;
    private BLEPeripheral mBlePeripheral;


    public StudentPresenter(final IStudentView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
        mBlePeripheral = new BLEPeripheral(((StudentActivity) baseView));
        mBlePeripheral.setOnConnectListener(new OnConnectListener() {
            @Override
            public void onConnect() {
                ToastUtil.show((StudentActivity) baseView, "连接成功", true);
                Intent intent = new Intent((StudentActivity) baseView, LockService.class);
                ((StudentActivity) baseView).startService(intent);
            }


            @Override
            public void onDisConnect() {

            }
        });
    }


    @Override public StudentActivity getViewType() {
        return ((StudentActivity) getView());
    }


    public void loadCourse(String stuNum) {
        mApi.loadCourseTable(stuNum).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<CourseEntity>() {
                @Override
                public void call(CourseEntity courseEntity) {
                    getView().setCourseTable(courseEntity);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Logger.d(throwable.getMessage());
                }
            });
    }


    public void startAdvertise(String advData) {
        mBlePeripheral.startAdvertise(advData);
    }


    public void stopAdvertise() {
        mBlePeripheral.stopAdvertise();
    }


    public interface IStudentView extends BaseView {
        void setCourseTable(CourseEntity courses);
    }
}
