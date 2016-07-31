package com.srtianxia.bleattendance.ui.activity;

import android.support.design.widget.FloatingActionButton;
import butterknife.BindView;
import butterknife.OnClick;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseActivity;
import com.srtianxia.bleattendance.component.DaggerTeacherComponent;
import com.srtianxia.bleattendance.module.TeacherModule;
import com.srtianxia.bleattendance.presenter.TeacherPresenter;
import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class TeacherActivity extends BaseActivity implements TeacherPresenter.ITeacherView {
    @BindView(R.id.fab) FloatingActionButton fabShift;

    @Inject
    TeacherPresenter mPresenter;

    @Override protected void initView() {
        DaggerTeacherComponent.builder().teacherModule(new TeacherModule(this)).build().inject(this);
    }


    @Override protected int getLayoutRes() {
        return R.layout.activity_teacher;
    }

    @OnClick(R.id.fab)
    void clickToScan() {
        mPresenter.startScan();
    }

}
