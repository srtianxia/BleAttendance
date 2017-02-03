package com.srtianxia.bleattendance.ui.enter;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.view.BaseFragment;
import com.srtianxia.bleattendance.di.component.DaggerLoginComponent;
import com.srtianxia.bleattendance.di.module.LoginModule;
import com.srtianxia.bleattendance.ui.student.home.StudentHomeActivity;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;
import com.srtianxia.bleattendance.utils.DialogUtils;
import com.srtianxia.bleattendance.utils.NetWorkUtils;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.ToastUtil;
import com.srtianxia.bleattendance.utils.UiHelper;
import com.srtianxia.bleattendance.widget.LoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by srtianxia on 2016/7/23.
 */
public class LoginFragment extends BaseFragment implements LoginPresenter.ILoginView {
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_login)
    LoginButton btnLogin;
    @BindView(R.id.tv_link_teacher_enter)
    TextView tvLinkTeacherEnter;
    @BindView(R.id.llt_container)
    LinearLayout lltContainer;

    @Inject
    LoginPresenter mPresenter;


    @Override
    protected void initView() {
        DaggerLoginComponent.builder().loginModule(new LoginModule(this)).build().inject(this);
        String flag = PreferenceManager.getInstance().getString(PreferenceManager.SP_LOGIN_FLAG, "");
        if (TextUtils.equals(flag, PreferenceManager.SP_LOGIN_FLAG_STU)) {
            UiHelper.startActivity(getActivity(), StudentHomeActivity.class);
            getActivity().finish();
        } else if (TextUtils.equals(flag, PreferenceManager.SP_LOGIN_FLAG_TEA)) {
            UiHelper.startActivity(getActivity(), TeacherHomeActivity.class);
            getActivity().finish();
        }
    }


    @OnClick(R.id.tv_link_teacher_enter)
    void clickToTeacher() {
        if ("".equals(getStuNum())) {
            ToastUtil.show(getActivity(), getResources().getString(R.string.login_error_name_null), true);
            return;
        }else if ("".equals(getPassword())){
            ToastUtil.show(getActivity(), getResources().getString(R.string.login_error_password_null), true);
            return;
        }
        DialogUtils.getInstance().showProgressDialog(getActivity(), getResources().getString(R.string.login_dialog));
        mPresenter.teacherLogin();
    }


    @OnClick(R.id.btn_login)
    void clickToStudent() {
        // todo 先放在这儿 这里保存的逻辑应该移动到model层
        if ("".equals(getStuNum())) {
            ToastUtil.show(getActivity(), getResources().getString(R.string.login_error_name_null), true);
            return;
        }else if ("".equals(getPassword())){
            ToastUtil.show(getActivity(), getResources().getString(R.string.login_error_password_null), true);
            return;
        }
        mPresenter.studentLogin();
        btnLogin.executeLogin();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }


    //todo 要加上格式处理
    @Override
    public String getStuNum() {
        return inputUsername.getText().toString();
    }


    @Override
    public String getPassword() {
        return inputPassword.getText().toString();
    }


    @Override
    public void studentLoginSuccess() {
        PreferenceManager.getInstance().setString(PreferenceManager.SP_LOGIN_FLAG, PreferenceManager.SP_LOGIN_FLAG_STU);
        btnLogin.postDelayed(this::handleSuccess, 1000);
    }

    @Override
    public void studentLoginFailure() {
        if (NetWorkUtils.isNetworkConnected(getActivity())){
            ToastUtil.show(getActivity(),getResources().getString(R.string.login_error_passwork_error), true);
        }else {
            ToastUtil.show(getActivity(), getResources().getString(R.string.login_error_not_network),true);
        }
        // 这边登录失败还要变回按钮才行
        btnLogin.executeLoginFailure();
        btnLogin.setClickable(true);
    }

    @Override
    public void loginFailure(String cause) {

    }

    @Override
    public void teacherLoginSuccess() {
        DialogUtils.getInstance().dismissProgressDialog();
        PreferenceManager.getInstance().setString(PreferenceManager.SP_LOGIN_FLAG, PreferenceManager.SP_LOGIN_FLAG_TEA);
        UiHelper.startActivity(getActivity(), TeacherHomeActivity.class);
        getActivity().finish();
    }

    @Override
    public void teacherLoginFailure() {
        if (NetWorkUtils.isNetworkConnected(getActivity())){
            ToastUtil.show(getActivity(),getResources().getString(R.string.login_error_passwork_error), true);
        }else {
            ToastUtil.show(getActivity(), getResources().getString(R.string.login_error_not_network),true);
        }
        DialogUtils.getInstance().dismissProgressDialog();
    }


    private void handleSuccess() {
        float finalRadius = (float) Math.hypot(container.getWidth(), container.getHeight());
        int[] location = new int[2];
        btnLogin.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        Animator animator = ViewAnimationUtils.createCircularReveal(container,
                x + btnLogin.getMeasuredWidth() / 2, y - btnLogin.getMeasuredHeight(), 100,
                finalRadius);
        container.setBackgroundColor(0xff6abad3);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }


            @Override
            public void onAnimationEnd(Animator animator) {
                startActivity(new Intent(getActivity(), StudentHomeActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                getActivity().finishAfterTransition();
                getActivity().finish();
            }


            @Override
            public void onAnimationCancel(Animator animator) {

            }


            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
        hideAllView();
    }


    private void hideAllView() {
        lltContainer.setVisibility(View.GONE);
    }

}
