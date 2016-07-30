package com.srtianxia.bleattendance.base.presenter;

import com.srtianxia.bleattendance.base.view.BaseView;
import java.lang.ref.WeakReference;

/**
 * Created by srtianxia on 2016/7/30.
 */
public abstract class BasePresenter<T extends BaseView> {
    private WeakReference<T> mReference;

    public BasePresenter(T baseView) {
        this.mReference = new WeakReference<T>(baseView);
    }

    public T getView() {
        if (mReference != null) {
            return mReference.get();
        }
        return null;
    }

    public void detachView() {
        mReference.clear();
        mReference = null;
    }
}
