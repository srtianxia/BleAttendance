package com.srtianxia.bleattendance.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by srtianxia on 2016/4/3.
 */
public class ScreenUtils {
    public static int dip2px(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
    }


    public static float dp2Px(Context paramContext, float paramFloat) {
        if (paramContext == null) {
            return -1.0F;
        }
        return (paramFloat * paramContext.getResources().getDisplayMetrics().density);
    }


    public static float dp2PxInt(Context paramContext, float paramFloat) {
        return (int) (0.5F + dp2Px(paramContext, paramFloat));
    }


    public static int getScreenHeight(Context paramContext) {
        int screenHeight;
        Display localDisplay = ((WindowManager) paramContext.getSystemService(
            Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point localPoint = new Point();
        localDisplay.getSize(localPoint);
        screenHeight = localPoint.y;
        return screenHeight;
    }


    public static int getScreenWidth(Context paramContext) {
        int screenWidth;
        Display localDisplay = ((WindowManager) paramContext.getSystemService(
            Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point localPoint = new Point();
        localDisplay.getSize(localPoint);
        screenWidth = localPoint.x;
        return screenWidth;
    }


    public static float px2Dp(Context paramContext, float paramFloat) {
        if (paramContext == null) {
            return -1.0F;
        }
        return (paramFloat / paramContext.getResources().getDisplayMetrics().density);
    }


    public static int px2dip(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
    }


    public static int px2sp(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().scaledDensity);
    }
}
