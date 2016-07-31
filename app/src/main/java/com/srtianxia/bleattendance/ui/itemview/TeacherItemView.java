package com.srtianxia.bleattendance.ui.itemview;

import android.content.Context;
import android.view.ViewGroup;
import com.srtianxia.bleattendance.base.view.BaseItemView;
import com.srtianxia.bleattendance.entity.DeviceEntity;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class TeacherItemView extends BaseItemView<DeviceEntity> {
    public TeacherItemView(Context context, ViewGroup root) {
        super(context, root);
    }


    @Override public void setData(DeviceEntity deviceEntity) {

    }


    @Override protected int getLayoutRes() {
        return 0;
    }
}
