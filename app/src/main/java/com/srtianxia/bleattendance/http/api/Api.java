package com.srtianxia.bleattendance.http.api;

import com.srtianxia.bleattendance.entity.TeacherEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by srtianxia on 2016/7/31.
 */
public interface Api {

    @FormUrlEncoded
    @POST("teacher/login")
    Observable<TeacherEntity> loginTeacher(@Field("trid") String trid, @Field("password") String password);

}
