package com.srtianxia.bleattendance.ui.enter;

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
import com.srtianxia.bleattendance.ui.student.home.StudentHomeActivity;
import com.srtianxia.bleattendance.ui.teacher.home.TeacherHomeActivity;
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
    }


    @OnClick(R.id.tv_link_teacher_enter)
    void clickToTeacher() {
//        UiHelper.startActivity(getActivity(), TeacherHomeActivity.class);
        mPresenter.teacherLogin();
    }


    @OnClick(R.id.btn_login)
    void clickToStudent() {
        // todo 先放在这儿 这里保存的逻辑应该移动到model层
        if ("".equals(getStuNum())) {
            ToastUtil.show(getActivity(), "not allow null", true);
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
        btnLogin.postDelayed(this::handleSuccess, 1000);
    }

    @Override
    public void studentLoginFailure() {
        ToastUtil.show(getActivity(), "password error", true);
        // 这边登录失败还要变回按钮才行
        btnLogin.executeLoginFailure();
        btnLogin.setClickable(true);
    }

    @Override
    public void loginFailure(String cause) {

    }

    @Override
    public void teacherLoginSuccess() {
        UiHelper.startActivity(getActivity(), TeacherHomeActivity.class);
    }

    @Override
    public void teacherLoginFailure() {

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
