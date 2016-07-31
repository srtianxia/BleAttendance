package com.srtianxia.bleattendance.component;

import com.srtianxia.bleattendance.module.TeacherModule;
import com.srtianxia.bleattendance.ui.fragment.TeacherFragment;
import dagger.Component;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Component(modules = {TeacherModule.class})
public interface TeacherComponent {
    void inject(TeacherFragment fragment);
}
