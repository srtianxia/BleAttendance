package com.srtianxia.bleattendance.ui.student.attendance;

import android.content.Context;

/**
 * Created by srtianxia on 2017/1/16.
 * 隔离实现中间层
 */

public class StuMidModel {

    public static IStuAttModel getModel(Context context) {
        return new StuRxAttModel(context);
    }
}
