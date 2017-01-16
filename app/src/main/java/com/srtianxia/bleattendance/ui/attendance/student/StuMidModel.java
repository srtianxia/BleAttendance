package com.srtianxia.bleattendance.ui.attendance.student;

import android.content.Context;

import com.srtianxia.bleattendance.ui.attendance.student.IStuAttModel;
import com.srtianxia.bleattendance.ui.attendance.student.StuRxAttModel;

/**
 * Created by srtianxia on 2017/1/16.
 * 隔离实现中间层
 */

public class StuMidModel {

    public static IStuAttModel getModel(Context context) {
        return new StuRxAttModel(context);
    }
}
