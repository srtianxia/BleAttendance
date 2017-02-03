package com.srtianxia.bleattendance.ui.course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.base.adapter.BaseAdapter;
import com.srtianxia.bleattendance.entity.CourseTimeEntity;
import com.srtianxia.bleattendance.utils.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 梅梅 on 2017/2/3.
 */
public class CourseTimeAdapter extends BaseAdapter<CourseTimeEntity>{

    public Context context;

    public CourseTimeAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {

        CourseTimeViewHolder courseTimeViewHolder = new CourseTimeViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_course_time,parent,false),context);

        return courseTimeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CourseTimeViewHolder){
            CourseTimeViewHolder viewHolder = (CourseTimeViewHolder) holder;
            viewHolder.setData(getDataController().getData(position));
        }
    }

    @Override
    public void loadData(List<CourseTimeEntity> mData) {

        super.loadData(mData);
    }

    @Override
    public void addData(CourseTimeEntity data) {
        super.addData(data);
    }

    public static class CourseTimeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_course_time)TextView mCourseTime;
        @BindView(R.id.tv_course_time_num)TextView mCourseTimeNum;
        @BindView(R.id.linearlayout_course_time)LinearLayout mLinearlayout;

        private Context context;

        public CourseTimeViewHolder(View itemView,Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this,itemView);
        }

        public void setData(CourseTimeEntity courseTimeEntity){
            int mScreenHeight = DensityUtil.getScreenHeight(context);
            if (mScreenHeight > DensityUtil.dp2px(context, 700)){
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLinearlayout.getLayoutParams();
                layoutParams.height = mScreenHeight/12;
                mLinearlayout.setLayoutParams(layoutParams);
            }

            this.mCourseTime.setText(courseTimeEntity.course_time);
            this.mCourseTimeNum.setText(courseTimeEntity.course_time_num);
        }
    }
}
