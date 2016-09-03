package com.srtianxia.bleattendance.di.module;

import com.srtianxia.bleattendance.presenter.LoginPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Module
public class LoginModule {
    private LoginPresenter.ILoginView mView;

    public LoginModule(LoginPresenter.ILoginView view) {
        this.mView = view;
    }

    @Provides
    LoginPresenter provideLoginPresenter(LoginPresenter.ILoginView view) {
        return new LoginPresenter(view);
    }

    @Provides
    LoginPresenter.ILoginView provideLoginView() {
        return mView;
    }
}
