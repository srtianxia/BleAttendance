package com.srtianxia.bleattendance.http.api;

import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.entity.StudentEntity;
import com.srtianxia.bleattendance.entity.TeaCourseEntity;
import com.srtianxia.bleattendance.entity.TeacherEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by srtianxia on 2016/7/31.
 */
public interface Api {

    // teacher 部分
    @FormUrlEncoded
    @POST("teacher/login")
    Observable<TeacherEntity> loginTeacher(@Field("trid") String trid, @Field("password") String password);


    // student 部分
    @FormUrlEncoded
    @POST("student/login")
    Observable<StudentEntity> loginStudent(@Field("stu_code") String stuNum, @Field("password") String password);

    @FormUrlEncoded
    @POST("teacher/course")
    Observable<TeaCourseEntity> getTeaCourse(@Field("token") String token, @Field("week") String week);

    @FormUrlEncoded
    @POST("student/course")
    Observable<NewCourseEntity> getStuCourse(@Field("token") String token, @Field("week") String week);

    @GET("student/course")
    Observable<NewCourseEntity> getStuCourseWithoutWeek(@Query("token") String token);
}
