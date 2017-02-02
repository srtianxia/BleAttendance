package com.srtianxia.bleattendance.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.entity.Course;
import com.srtianxia.bleattendance.utils.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 梅梅 on 2017/1/21.
 */
public class CourseTableView extends FrameLayout {

    private static final String TAG = "CourseTableView";
    /**
     * width,课表每个格子的宽度
     * height,课表每个格子的长度
     * colorSelector，颜色选择器
     * course数组，7*7个格子，每个格子都有一个courselist,同一格子可以有很多课
     */
    private final int width = (int) ((DensityUtil.getScreenWidth(getContext()) - DensityUtil.dp2px(getContext(), 56)) / 7);
    private int height = (int) DensityUtil.dp2px(getContext(), 100);
    private CourseList[][] course = new CourseList[7][7];
    private CourseColorSelector courseColorSelector = new CourseColorSelector();

    private OnLongClickListener mOnLongClickListener;

    private Context context;

    public CourseTableView(Context context) {
        super(context);
    }

    public CourseTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "CourseTableView");
        this.context = context;

        //如果手机屏幕过大，则重新设置格子height
        if (DensityUtil.getScreenHeight(context) > 700) {
            height = DensityUtil.getScreenHeight(context) / 6;
        }
//        initCourse();
    }

    public CourseTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化数组course
     */
    private void initCourse() {
        Log.i(TAG, "initCourse");
        for (int i = 0; i < 7; i++) {
            if (course != null) {
                if (course[i] == null) {
                    course[i] = new CourseList[7];
                }
            }
        }
    }

    /**
     * 1、移除layout上的所有view
     * 2、将传入的courselist里面的每个course放入数组course[7][7]的对应位置。
     *
     * @param data 某周的所有课程信息（courselist<course>）
     */
    public void addContentView(List<Course> data) {
        Log.i(TAG, "addContentView");
        removeAllViews();
        initCourse();
        for (Course aData : data) {
            //设置颜色
            courseColorSelector.addCourseColors(aData.begin_lesson, aData.hash_day);

            int x = aData.hash_day;
            int y = aData.hash_lesson;
            if (course[x][y] == null) {
                course[x][y] = new CourseList();
            }
            course[x][y].list.add(aData);
        }
        loadingContent();
    }

    /**
     * 将数组course[7][7]内不为空的courselist，显示在对应的课表格子上
     */
    private void loadingContent() {
        Log.i(TAG, "loadingContent");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (course[i][j] != null && course[i][j].list != null && course[i][j].list.size() != 0) {
                    createLessonText(course[i][j]);
                }
            }
        }
    }

    /**
     * 1、根据传入的courselist,将第一个显示在格子上，若有多门课左下角加个标记,若有事件左上角加个标记。
     * 2、设置点击事件，将courselist传入Dialog，点击则显示在Dialog上
     *
     * @param courses 某个格子的课程、事件的courselist
     */
    private void createLessonText(CourseList courses) {
        Course course = courses.list.get(0);
        height = DensityUtil.dp2px(getContext(), 100);

        TextView tv = new TextView(context);
        int mTop = height * course.hash_lesson;
        int mLeft = width * course.hash_day;
        int mWidth = width;
        int mHeight = (int) (height * (float) course.period / 2);

        LayoutParams layoutparams = new LayoutParams(mWidth - DensityUtil.dp2px(getContext(), 1), mHeight - DensityUtil.dp2px(getContext(), 1));
        layoutparams.topMargin = mTop + DensityUtil.dp2px(getContext(), 1);
        layoutparams.leftMargin = mLeft + DensityUtil.dp2px(getContext(), 1);
        tv.setLayoutParams(layoutparams);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv.setTextColor(Color.WHITE);
        tv.setText(course.course + "@" + course.classroom);


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(DensityUtil.dp2px(getContext(), 1));
        gd.setColor(courseColorSelector.getCourseColors(course.begin_lesson, course.hash_day));

        tv.setBackground(gd);

        tv.setOnClickListener(view -> {
            CourseDialog.show(getContext(), courses);
        });

        tv.setOnLongClickListener(v -> {
            if (mOnLongClickListener != null) {
                mOnLongClickListener.onClick(courses);
            }
            return false;
        });

        addView(tv);


        /*LayoutParams testLayoutparams = new LayoutParams(mWidth,mHihgt);
        layoutparams.topMargin = DensityUtil.dp2px(context,mTop-1);
        layoutparams.leftMargin = DensityUtil.dp2px(context,mLeft-1);
        tv.setLayoutParams(layoutparams);
        tv.setGravity(Gravity.CENTER);*/

        //如果一个格子的课超过一门，则在右下角显示一个提醒标记
        if (courses.list.size() > 1) {
            View flag = new View(context);
            LayoutParams flagLp = new LayoutParams(mWidth / 5, mWidth / 5);
            flagLp.topMargin = mTop + mHeight - mWidth / 5;
            flagLp.leftMargin = mLeft + mWidth * 4 / 5;
            flag.setLayoutParams(flagLp);
            flag.setBackgroundResource(R.mipmap.ic_corner_right_bottom);
            addView(flag);
        }
    }

    public void clearList() {
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < course[0].length; j++)
                if (course[i][j] != null && course[i][j].list != null) {
                    course[i][j].list.clear();
                }

    }

    public class CourseColorSelector {

        private int[] courseColors = new int[]{
                R.color.course_Blue,
                R.color.course_Yellow,
                R.color.course_Green,
                R.color.course_Gray
        };

        HashMap<String, Integer> mCourseColorMap = new HashMap<>();

        public void addCourseColors(int beginLesson, int hashDay) {
            if (hashDay >= 5)
                mCourseColorMap.put(beginLesson + "," + hashDay, courseColors[3]);
            else if (beginLesson < 5)
                mCourseColorMap.put(beginLesson + "," + hashDay, courseColors[0]);
            else if (beginLesson < 9)
                mCourseColorMap.put(beginLesson + "," + hashDay, courseColors[1]);
            else
                mCourseColorMap.put(beginLesson + "," + hashDay, courseColors[2]);

        }

        public int getCourseColors(int beginLesson, int hashDay) {
            return getResources().getColor(mCourseColorMap.get(beginLesson + "," + hashDay));
        }
    }

    public void setOnLongClickListener(OnLongClickListener noClickListener) {
        this.mOnLongClickListener = noClickListener;
    }

    public static class CourseList {
        public ArrayList<Course> list = new ArrayList<>();
    }

    public interface OnLongClickListener {
        void onClick(CourseList courses);
    }
}
