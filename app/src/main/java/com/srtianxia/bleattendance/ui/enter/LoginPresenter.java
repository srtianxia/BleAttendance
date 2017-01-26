package com.srtianxia.bleattendance.ui.enter;

import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.entity.StudentEntity;
import com.srtianxia.bleattendance.entity.TeacherEntity;
import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;
import com.srtianxia.bleattendance.utils.PreferenceManager;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class LoginPresenter extends BasePresenter<LoginPresenter.ILoginView> {
    private Api mApi;


    public LoginPresenter(ILoginView baseView) {
        super(baseView);
        mApi = ApiUtil.createApi(Api.class, ApiUtil.getBaseUrl());
    }


    @Override
    public LoginFragment getViewType() {
        return ((LoginFragment) getView());
    }


    public void teacherLogin() {
        mApi.loginTeacher(getView().getStuNum(), getView().getPassword())
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::loginTeacherSuccess, this::teacherLoginFailure);
    }

    private void loginTeacherSuccess(TeacherEntity entity) {
        PreferenceManager.getInstance().setString(PreferenceManager.SP_TOKEN_TEACHER, entity.data);
        getView().teacherLoginSuccess();
    }

    private void teacherLoginFailure(Throwable throwable) {
        getView().teacherLoginFailure();
    }

    public void studentLogin() {
        mApi.loginStudent(getView().getStuNum(), getView().getPassword())
                .compose(RxSchedulersHelper.io2main())
                .subscribe(this::loginStudentSuccess, this::loginStudentFailure);
    }


    private void loginStudentSuccess(StudentEntity entity) {
        PreferenceManager.getInstance().setString(PreferenceManager.SP_TOKEN_STUDENT, entity.data);
        getView().studentLoginSuccess();
    }

    private void loginStudentFailure(Throwable throwable) {
        getView().studentLoginFailure();
    }

    public interface ILoginView extends BaseView {
        String getStuNum();

        String getPassword();

        void studentLoginSuccess();

        void studentLoginFailure();

        void loginFailure(String cause);

        void teacherLoginSuccess();

        void teacherLoginFailure();
    }
}
