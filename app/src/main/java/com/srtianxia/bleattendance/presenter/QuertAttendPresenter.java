package com.srtianxia.bleattendance.presenter;

import com.orhanobut.logger.Logger;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.AttendEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class QuertAttendPresenter extends BasePresenter<QuertAttendPresenter.IQueryView> {
    private Api mApi;

    public QuertAttendPresenter(IQueryView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }

    public void queryAttend() {
        mApi.queryAttend("040200")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<AttendEntity>() {
                @Override
                public void call(AttendEntity attendEntity) {
                    if (attendEntity.status == 200) {
                        getView().querySuccess(attendEntity.data.get(0));
                    } else {
                        getView().queryFailure(attendEntity.msg);
                    }
                }

            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    Logger.d(throwable.getMessage());
                }
            });
    }

    public interface IQueryView extends BaseView {

        void querySuccess(AttendEntity.Data datas);
        void queryFailure(String cause);
    }
}
