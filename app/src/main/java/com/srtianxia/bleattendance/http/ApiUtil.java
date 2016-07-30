package com.srtianxia.bleattendance.http;

import android.content.res.Resources;
import com.srtianxia.bleattendance.App;
import com.srtianxia.bleattendance.R;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class ApiUtil {

    public static String getBaseUrl() {
        Resources resources = App.getContext().getResources();
        return resources.getString(R.string.base_url);
    }

    public static <T> T createApi(Class clazz, String baseUrl) {
        return ApiFactory.createApi(clazz, baseUrl);
    }
}
