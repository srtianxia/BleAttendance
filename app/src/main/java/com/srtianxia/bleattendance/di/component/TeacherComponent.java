package com.srtianxia.bleattendance.di.component;

import com.srtianxia.bleattendance.di.module.TeacherModule;
import com.srtianxia.bleattendance.ui.attendance.teacher.TeacherScanFragment;
import dagger.Component;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Component(modules = {TeacherModule.class})
public interface TeacherComponent {
    void inject(TeacherScanFragment fragment);
}
