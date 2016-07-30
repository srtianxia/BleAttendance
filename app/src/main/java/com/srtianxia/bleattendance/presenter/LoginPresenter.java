package com.srtianxia.bleattendance.presenter;

import com.orhanobut.logger.Logger;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.config.Constant;
import com.srtianxia.bleattendance.entity.StuEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class LoginPresenter extends BasePresenter<LoginPresenter.ILoginView> {
    private Api mApi;

    public LoginPresenter(ILoginView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }

    public void login() {
        mApi.login(getView().getStuNum(), getView().getPassword(), Constant.TYPE_STU)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<StuEntity>() {
                @Override public void call(StuEntity stuEntity) {
                    Logger.d(stuEntity.sessionId);
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    Logger.d(throwable.getMessage());
                }
            });
    }

    public interface ILoginView extends BaseView {
        String getStuNum();
        String getPassword();
    }
}
