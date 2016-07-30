package com.srtianxia.blelibs.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by srtianxia on 2016/7/14.
 */
public class ToastUtil {
    private static String oldMessage;
    private static Toast mToast;
    private static int mDuration;
    private static long mFirstTime = 0;
    private static long mNextTime = 0;


    public static void show(Context context, String message, boolean isShort) {
        mDuration = isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
        if (mToast == null) {
            mToast = Toast.makeText(context, message, mDuration);
        }
        if (message.equals(oldMessage)) {
            mNextTime = System.currentTimeMillis();
            if ((mNextTime - mFirstTime) > mDuration) {
                mToast.show();
            }
        } else {
            oldMessage = message;
            mFirstTime = System.currentTimeMillis();
            mToast.setText(message);
            mToast.setDuration(mDuration);
            mToast.show();
        }
    }
}
