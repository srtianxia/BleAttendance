package com.srtianxia.bleattendance.http.api;

import com.srtianxia.bleattendance.entity.AttendEntity;
import com.srtianxia.bleattendance.entity.CourseEntity;
import com.srtianxia.bleattendance.entity.StuEntity;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by srtianxia on 2016/7/31.
 */
public interface Api {

    @FormUrlEncoded
    @POST("login") Observable<StuEntity> login(@Field("stuNum") String username,
                                               @Field("stuPass") String password,
                                               @Field("type") String type);

    @FormUrlEncoded
    @POST("StudentKeBiao")
    Observable<CourseEntity> loadCourseTable(@Field("stuNum") String stuNum);

    @FormUrlEncoded
    @POST("StudentKaoQin")
    Observable<AttendEntity> queryAttend(@Field("cid") String classId);
}
