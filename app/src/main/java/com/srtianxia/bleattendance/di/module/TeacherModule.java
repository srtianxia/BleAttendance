package com.srtianxia.bleattendance.di.module;

import com.srtianxia.bleattendance.presenter.TeacherPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Module
public class TeacherModule {
    private TeacherPresenter.ITeacherView mView;

    public TeacherModule(TeacherPresenter.ITeacherView mView) {
        this.mView = mView;
    }

    @Provides
    TeacherPresenter providePresenter(TeacherPresenter.ITeacherView view) {
        return new TeacherPresenter(view);
    }

    @Provides
    TeacherPresenter.ITeacherView provideView() {
        return mView;
    }

}
