package com.srtianxia.bleattendance.ui.fragment;

import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.component.DaggerLoginComponent;
import com.srtianxia.bleattendance.module.LoginModule;
import com.srtianxia.bleattendance.presenter.LoginPresenter;
import com.srtianxia.bleattendance.ui.activity.TeacherActivity;
import com.srtianxia.bleattendance.utils.UiHelper;
import com.srtianxia.blelibs.utils.ToastUtil;
import javax.inject.Inject;

/**
 * Created by srtianxia on 2016/7/23.
 */
public class LoginFragment extends BaseFragment implements LoginPresenter.ILoginView {

    @BindView(R.id.input_username) EditText inputUsername;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.btn_login) AppCompatButton btnLogin;
    @BindView(R.id.tv_link_teacher_enter) TextView tvLinkTeacherEnter;

    @Inject
    LoginPresenter mPresenter;


    @Override protected void initView() {
        DaggerLoginComponent.builder().loginModule(new LoginModule(this)).build().inject(this);
    }


    @OnClick(R.id.tv_link_teacher_enter) void clickToTeacher() {
        UiHelper.startActivity(getActivity(), TeacherActivity.class);
    }


    @OnClick(R.id.btn_login) void clickToStudent() {
        //UiHelper.startActivity(getActivity(), StudentActivity.class);
        mPresenter.login();
    }


    @Override protected int getLayoutRes() {
        return R.layout.fragment_login;
    }


    @Override public String getStuNum() {
        return inputUsername.getText().toString();
    }


    @Override public String getPassword() {
        return inputPassword.getText().toString();
    }


    @Override public void loginSuccess() {
        ToastUtil.show(getActivity(), "success", true);
    }


    @Override public void loginFailure(String cause) {
        ToastUtil.show(getActivity(), cause, true);
    }
}
