package com.srtianxia.bleattendance.http.api;

import com.srtianxia.bleattendance.entity.AttInfoEntity;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.entity.PostAttResultEntity;
import com.srtianxia.bleattendance.entity.StuListEntity;
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


    // post考勤信息
    @FormUrlEncoded
    @POST("teacher/attendance")
    Observable<PostAttResultEntity> postAttendanceInfo(@Field("token") String token, @Field("jxbID") String jxbID,
                                                       @Field("hash_day") int hash_day, @Field("hash_lesson") int hash_lesson,
                                                       @Field("status") String status, @Field("week") int week);


    // 根据周数获取考勤信息 todo  这里和后端再确认下 是否可以唯一的确定一节课程
    @GET("teacher/attendance")
    Observable<AttInfoEntity> getAttendanceInfo(@Query("token") String token,
                                                @Query("jxbID") String jxbID,
                                                @Query("week") int week,
                                                @Query("hash_day") int hash_day,
                                                @Query("hash_lesson") int hash_lesson);

    // 获取一个班的人列表
    @GET("teacher/stulist")
    Observable<StuListEntity> getStuList(@Query("token") String token, @Query("jxbID") String jxbID);

    // student 部分
    @FormUrlEncoded
    @POST("student/login")
    Observable<StudentEntity> loginStudent(@Field("stu_code") String stuNum, @Field("password") String password);

    @GET("student/course")
    Observable<NewCourseEntity> getStuCourse(@Query("token") String token, @Query("week") String week);

    @GET("teacher/course")
    Observable<TeaCourseEntity> getTeaCourse(@Query("token") String token, @Query("week") String week);

}
