package com.srtianxia.bleattendance.base.viewholder;

import android.support.v7.widget.RecyclerView;
import com.srtianxia.bleattendance.base.view.BaseItemView;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class BaseViewHolder<T extends BaseItemView> extends RecyclerView.ViewHolder {
    protected T mItemView;
    public BaseViewHolder(BaseItemView baseItemView) {
        super(baseItemView.getContentView());
        mItemView = (T) baseItemView;
        mItemView.setItemView(itemView);
    }

    public T getItemView() {
        return mItemView;
    }

}
