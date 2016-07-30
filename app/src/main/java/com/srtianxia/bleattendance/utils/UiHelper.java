package com.srtianxia.bleattendance.utils;

import android.content.Context;
import android.content.Intent;
import java.util.Map;

/**
 * Created by srtianxia on 2016/7/30.
 */
public class UiHelper {
    public static void startActivity(Context context, Class targetActivity) {
        startActivity(context, targetActivity, null);
    }

    public static void startActivity(Context context, Class targetActivity, Map<String, String> intentData) {
        Intent intent = new Intent(context, targetActivity);
        if (intentData != null) {
            for (Map.Entry<String, String> entry : intentData.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        context.startActivity(intent);
    }
}
