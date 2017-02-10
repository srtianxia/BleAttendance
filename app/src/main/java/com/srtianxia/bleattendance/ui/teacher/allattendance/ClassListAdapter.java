package com.srtianxia.bleattendance.ui.teacher.allattendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class ClassListAdapter extends BaseAdapter<String>{

    private OnClassItemClickListener onItemClickListener;

    public void setOnClassItemClickListener(OnClassItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new BeforeViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_search_class,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BeforeViewHolder viewHolder = (BeforeViewHolder) holder;
        viewHolder.setData(getDataController().getData(position));

        if (onItemClickListener != null){
            viewHolder.itemView.setOnClickListener(view -> onItemClickListener.onClick(position,getDataController().getData(position)));
        }
    }

    public static class BeforeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_search_class)TextView mSearchClass;

        public BeforeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(String s){
            mSearchClass.setText(s);
        }
    }

    public interface OnClassItemClickListener{
        void onClick(int position,String choiceClass);
    }
}
