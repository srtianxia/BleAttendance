package com.srtianxia.bleattendance.http;

import android.content.res.Resources;
import com.srtianxia.bleattendance.BleApplication;
import com.srtianxia.bleattendance.R;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class ApiUtil {

    public static String getBaseUrl() {
        Resources resources = BleApplication.getContext().getResources();
        return resources.getString(R.string.new_base_url);
    }

    public static <T> T createApi(Class clazz, String baseUrl) {
        return ApiFactory.createApi(clazz, baseUrl);
    }
}
