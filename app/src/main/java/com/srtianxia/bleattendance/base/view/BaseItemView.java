package com.srtianxia.bleattendance.base.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * Created by srtianxia on 2016/7/31.
 */
public abstract class BaseItemView<T> {

    private View mView;

    private Context mContext;


    public BaseItemView(Context context, ViewGroup root) {
        mContext = context;
        mView = LayoutInflater.from(context).inflate(getLayoutRes(), root, false);
        if (root == null) {
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            mView.setLayoutParams(new RecyclerView.LayoutParams(dm.widthPixels,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        }
        ButterKnife.bind(this, mView);
        initView();
    }


    public abstract void setData(T t);


    protected void initView() {

    }


    protected abstract int getLayoutRes();


    public void setItemView(View itemView) {
        mView = itemView;
    }


    public View getContentView() {
        return mView;
    }


    public Context getContext() {
        return mContext;
    }
}
