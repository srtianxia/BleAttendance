package com.srtianxia.bleattendance.di.component;

import com.srtianxia.bleattendance.di.module.LoginModule;
import com.srtianxia.bleattendance.ui.fragment.LoginFragment;
import dagger.Component;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Component(modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginFragment loginFragment);
}

