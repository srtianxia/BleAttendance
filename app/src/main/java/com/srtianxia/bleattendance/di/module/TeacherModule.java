package com.srtianxia.bleattendance.di.module;

import com.srtianxia.bleattendance.presenter.TeacherScanPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Module
public class TeacherModule {
    private TeacherScanPresenter.ITeacherScanView mView;

    public TeacherModule(TeacherScanPresenter.ITeacherScanView mView) {
        this.mView = mView;
    }

    @Provides
    TeacherScanPresenter providePresenter(TeacherScanPresenter.ITeacherScanView view) {
        return new TeacherScanPresenter(view);
    }

    @Provides
    TeacherScanPresenter.ITeacherScanView provideView() {
        return mView;
    }

}
