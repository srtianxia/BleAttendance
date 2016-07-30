package com.srtianxia.bleattendance.component;

import com.srtianxia.bleattendance.module.StudentModule;
import com.srtianxia.bleattendance.ui.activity.StudentActivity;
import dagger.Component;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Component(modules = {StudentModule.class})
public interface StudentComponent {
    void inject(StudentActivity studentActivity);
}
