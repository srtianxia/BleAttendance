package com.srtianxia.bleattendance.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.entity.Course;
import com.srtianxia.bleattendance.utils.DensityUtil;

import java.util.ArrayList;

/**
 * Created by 梅梅 on 2017/1/21.
 */
public class CourseDialog {

    public static void show(Context context,CourseTableView.CourseList courseList){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_course_table,null);
        CourseDetailView courseDetailView = (CourseDetailView) layout.findViewById(R.id.courseview_course);

        /*if (courseList.list.size() == 2){
            ViewGroup.LayoutParams params = courseDetailView.getLayoutParams();
            params.height = DensityUtil.dp2px(context,220);
            courseDetailView.setLayoutParams(params);
        }*/

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("详细信息")
                .setCancelable(true)
                .setView(layout, DensityUtil.dp2px(context, 12), DensityUtil.dp2px(context, 24)
                        , DensityUtil.dp2px(context, 12), 0/*DensityUtil.dp2px(context, 24)*/)
                .create();

        CoursePagerAdapter adapter = new CoursePagerAdapter(context,inflater,courseList,dialog);
        courseDetailView.setAdapter(adapter);
        dialog.show();

    }



    public static class CoursePagerAdapter extends PagerAdapter{
        private Context context;
        private LayoutInflater mInflater;
        private ArrayList<Course> mCourseList;
        private Dialog mDialog;


        public CoursePagerAdapter(Context context, LayoutInflater mInflater,
                                  CourseTableView.CourseList courseList, Dialog dialog) {
            this.context = context;
            this.mInflater = mInflater;
            this.mCourseList = courseList.list;
            this.mDialog = dialog;

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View courseview = container.getChildAt(position);
            Course course = mCourseList.get(position);

            if (courseview == null){
                courseview = mInflater.inflate(R.layout.item_dialog_course,container,false);
                TextView name = (TextView) courseview.findViewById(R.id.text_class_name);
                TextView teacher = (TextView) courseview.findViewById(R.id.text_class_teacher);
                TextView classroom = (TextView) courseview.findViewById(R.id.text_class_classroom);
                TextView weeks = (TextView) courseview.findViewById(R.id.text_class_weeks);
                TextView time = (TextView) courseview.findViewById(R.id.text_class_time);
                TextView time_num = (TextView) courseview.findViewById(R.id.text_class_time_num);
                TextView type = (TextView) courseview.findViewById(R.id.text_class_type);

                name.setText(course.course);
                teacher.setText(course.teacher);
                classroom.setText(course.classroom);
                time.setText(course.day + " ~ " + course.lesson);
                type.setText(course.type);
                weeks.setText(course.week.size()+"");
                if (course.period == 2){
                    String timenum = context.getResources().getStringArray(R.array.course_time_2)[course.hash_lesson];
                    time_num.setText(timenum);
                }else {
                    String timenum = context.getResources().getStringArray(R.array.course_time_3)[course.hash_lesson];
                    time_num.setText(timenum);
                }

                container.addView(courseview);
            }


            return courseview;
        }

        @Override
        public int getCount() {
            return mCourseList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }
}
