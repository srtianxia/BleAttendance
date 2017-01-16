package com.srtianxia.bleattendance.di.component;

import com.srtianxia.bleattendance.di.module.StudentModule;
import com.srtianxia.bleattendance.ui.home.StudentActivity;
import dagger.Component;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Component(modules = {StudentModule.class})
public interface StudentComponent {
    void inject(StudentActivity studentActivity);
}
