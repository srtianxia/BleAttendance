package com.srtianxia.bleattendance.ui.course;

import com.srtianxia.bleattendance.http.ApiUtil;
import com.srtianxia.bleattendance.http.api.Api;

/**
 * Created by 梅梅 on 2017/1/20.
 */
public class CourseModel implements ICourseModel{
    private Api mApi;

    @Override
    public void getData(int token, int week) {
        mApi = ApiUtil.createApi(Api.class,ApiUtil.getBaseUrl());
    }

}
