package com.srtianxia.bleattendance.ui.attendance.student;

import android.content.Context;

/**
 * Created by srtianxia on 2017/1/16.
 */

public interface IStuAttModel {
    IStuAttModel getInstance(Context context);

    void startAdvertise();

    void stopAdvertise();
}
