package com.srtianxia.bleattendance.di.module;

import com.srtianxia.bleattendance.ui.student.table.StudentPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Module
public class StudentModule {
    private StudentPresenter.IStudentView mView;

    public StudentModule(StudentPresenter.IStudentView mView) {
        this.mView = mView;
    }

    @Provides
    StudentPresenter providePresenter() {
        return new StudentPresenter(mView);
    }

    @Provides
    StudentPresenter.IStudentView provideView() {
        return mView;
    }
}
