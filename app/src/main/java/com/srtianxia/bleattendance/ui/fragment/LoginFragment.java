package com.srtianxia.bleattendance.ui.fragment;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
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
import com.srtianxia.bleattendance.presenter.LoginPresenter;
import com.srtianxia.bleattendance.ui.activity.StudentActivity;
import com.srtianxia.bleattendance.ui.activity.StudentHomeActivity;
import com.srtianxia.bleattendance.ui.activity.TeacherHomeActivity;
import com.srtianxia.bleattendance.utils.UiHelper;
import com.srtianxia.bleattendance.widget.LoginButton;
import com.srtianxia.blelibs.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by srtianxia on 2016/7/23.
 */
public class LoginFragment extends BaseFragment implements LoginPresenter.ILoginView {
    @BindView(R.id.container) RelativeLayout container;
    @BindView(R.id.input_username) EditText inputUsername;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.btn_login) LoginButton btnLogin;
    @BindView(R.id.tv_link_teacher_enter) TextView tvLinkTeacherEnter;
    @BindView(R.id.llt_container) LinearLayout lltContainer;

    @Inject
    LoginPresenter mPresenter;


    @Override protected void initView() {
        DaggerLoginComponent.builder().loginModule(new LoginModule(this)).build().inject(this);
    }


    @OnClick(R.id.tv_link_teacher_enter) void clickToTeacher() {
        UiHelper.startActivity(getActivity(), TeacherHomeActivity.class);
    }


    @OnClick(R.id.btn_login) void clickToStudent() {
        btnLogin.executeLogin();
        btnLogin.postDelayed(this::handleSuccess, 2000);
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
        handleSuccess();
    }


    @Override public void loginFailure(String cause) {
        ToastUtil.show(getActivity(), cause, true);
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
            @Override public void onAnimationStart(Animator animator) {

            }


            @Override public void onAnimationEnd(Animator animator) {
                startActivity(new Intent(getActivity(), StudentHomeActivity.class),
                    ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                getActivity().finishAfterTransition();
            }


            @Override public void onAnimationCancel(Animator animator) {

            }


            @Override public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
        hideAllView();
    }


    private void hideAllView() {
        lltContainer.setVisibility(View.GONE);
    }

}
