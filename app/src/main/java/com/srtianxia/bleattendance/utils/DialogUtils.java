package com.srtianxia.bleattendance.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;


/**
 * Created by srtianxia on 2017/1/29.
 */

public class DialogUtils {
    private ProgressDialog mProgressDialog;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {

        return DialogInstance.instance;
    }

    private static final class DialogInstance {
        private static final DialogUtils instance = new DialogUtils();
    }

    public void showDialog(Context context, String title, String content, OnButtonChooseListener listener) {
        new AlertDialog.Builder(context)
                .setMessage(content)
                .setTitle(title)
                .setPositiveButton("确定", (dialog, which) -> listener.onPositive())
                .setNegativeButton("取消", (dialog, which) -> listener.onNegative())
                .create()
                .show();
    }

    public void showProgressDialog(Context context, String text) {
        // 感觉这个不应该封装成工具类来用 ....
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(text);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    public interface OnButtonChooseListener {
        void onPositive();

        void onNegative();
    }
}
