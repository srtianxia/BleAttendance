package com.srtianxia.bleattendance.ui.course;

import android.content.Context;

/**
 * Created by srtianxia on 2017/1/16.
 * 隔离实现的中间层
 */

public class CourseMidModel {

    public static ICourseModel getModel(Context context) {
        return new CourseModel();
    }
}
