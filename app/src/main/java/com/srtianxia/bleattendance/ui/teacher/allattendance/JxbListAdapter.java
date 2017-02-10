package com.srtianxia.bleattendance.ui.teacher.allattendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.entity.TeaCourse;

import butterknife.BindView;

/**
 * Created by 梅梅 on 2017/2/9.
 */
public class JxbListAdapter extends BaseAdapter<TeaCourse>{

    @BindView(R.id.tv_search_jxb)TextView mJxb;

    private OnJxbItemClickListener onJxbItemClickListener;

    public void setOnJxbItemClickListener(OnJxbItemClickListener onJxbItemClickListener){
        this.onJxbItemClickListener = onJxbItemClickListener;
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new JxbViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_search_jxb,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JxbViewHolder jxbViewHolder = (JxbViewHolder) holder;
        jxbViewHolder.setData(getDataController().getData(position).jxbID);

        if (onJxbItemClickListener != null){
            jxbViewHolder.itemView.setOnClickListener(view -> onJxbItemClickListener.onClick(position,getDataController().getData(position).jxbID));
        }
    }

    public class JxbViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public JxbViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_search_jxb);
        }

        public void setData(String s){
            textView.setText(s);
        }

    }

    public interface OnJxbItemClickListener{
        void onClick(int position,String JxbID);
    }
}
