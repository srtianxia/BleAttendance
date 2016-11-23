package com.srtianxia.bleattendance.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.entity.CourseEntity;
import com.srtianxia.bleattendance.utils.ScreenUtils;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by srtianxia on 2016/4/4.
 */
public class CoursesTableView extends FrameLayout {
    private CourseColorSelector colorSelector = new CourseColorSelector();
    private Context context;
    //二维数组，每一个格子中又包括很多课程，数组的行数为hash_lesson，列数为hash_day
    public CourseList[][] course = (CourseList[][]) Array.newInstance(CourseList.class, new int[] {7, 7});

    //每一个课程格子的高度(单个格子，两节课的占两个，三节课的占三个)
    private int height = (int) ScreenUtils.dp2Px(getContext(), 100.0F);

    //每一个课程格子的宽度
    private final int width = (int) (
        (ScreenUtils.getScreenWidth(getContext()) - ScreenUtils.dp2Px(getContext(), 56.0F)) / 7.0F);

    private View dialogView;


    public CoursesTableView(Context paramContext) {
        super(paramContext);
    }


    public CoursesTableView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.context = paramContext;
        //构造方法中进行判断，适配屏幕
        int i = ScreenUtils.getScreenHeight(paramContext);
        if (ScreenUtils.px2Dp(paramContext, i) > 700.0F) {
            this.height = (i / 6);
        }
        initCourse();

        //        setWillNotDraw(false)   当我们要复写一个ViewGroup的onDraw方法的时候要设置这个属性,这个View没有复写
        //        onDraw()方法，所以就可以不用标记。
        //        setWillNotDraw(false);
    }


    public CoursesTableView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    //
    //private void addAnchorView() {
    //    View localView = new View(getViewType());
    //    LayoutParams localLayoutParams = new LayoutParams(1, 1);
    //    localLayoutParams.topMargin = 0;
    //    localLayoutParams.leftMargin = (-1 + 7 * this.width);
    //    localView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    //    localView.setLayoutParams(localLayoutParams);
    //    addView(localView);
    //}


    /**
     * 数据传到每一个格子，这里分两种情况，一种是一个格子只有一节课，另一种是每个格子有很多
     *
     * @param paramCourseList 每个格子的课程列表
     */
    private void createLessonText(final CourseList paramCourseList) {
        CourseEntity.Course localCourse = (CourseEntity.Course) paramCourseList.list.get(0);
        TextView localTextView = new TextView(this.context);
        //下面四个参数是每一个课程格子(两节课就占两个格子)的位置信息
        //i 为上边界位置 j为左边界位置 k为宽度 l为高度
        //获取位置信息后用LayoutParams设置位置信息
        int i = this.height * localCourse.hash_lesson;
        int j = this.width * localCourse.hash_day;
        int k = this.width;
        final int l = (int) (this.height * localCourse.period / 2.0F);
        LayoutParams localLayoutParams1 = new LayoutParams(
            (int) (k - ScreenUtils.dp2Px(getContext(), 1.0F)),
            (int) (l - ScreenUtils.dp2Px(getContext(), 1.0F)));
        localLayoutParams1.topMargin = (int) (i + ScreenUtils.dp2Px(getContext(), 1.0F));
        localLayoutParams1.leftMargin = (int) (j + ScreenUtils.dp2Px(getContext(), 1.0F));
        localTextView.setLayoutParams(localLayoutParams1);
        localTextView.setTextColor(getResources().getColor(R.color.white));
        localTextView.setTextSize(2, 12.0F);
        localTextView.setGravity(Gravity.CENTER);
        localTextView.setText(localCourse.course + "@" + localCourse.classroom);
        GradientDrawable localGradientDrawable = new GradientDrawable();
        localGradientDrawable.setCornerRadius(ScreenUtils.dp2Px(getContext(), 1.0F));
        //根据课程的这两个参数找到一开始设置的颜色信息
        localGradientDrawable.setColor(
            this.colorSelector.getCourseColor(localCourse.begin_lesson, localCourse.hash_day));
        localTextView.setBackgroundDrawable(localGradientDrawable);
        localTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //CourseDialog.newInstance(paramCourseList).show(((AppCompatActivity)context).getSupportFragmentManager(),"");
            }
        });
        //        在这里添加每一个格子的点击事件，将paramCourseList传进去，再处理。
        addView(localTextView);
        //如果一个格子只有一节课，就返回，否则就在右下角添加一个标记
        if (paramCourseList.list.size() <= 1) {
            return;
        }
        //添加一个格子有多个课程的标记
        View localView = new View(getContext());
        localView.setBackgroundDrawable(
            getResources().getDrawable(R.mipmap.ic_corner_right_bottom));
        LayoutParams localLayoutParams2 = new LayoutParams(k / 5, k / 5);
        localLayoutParams2.topMargin = (i + l - (k / 5));
        localLayoutParams2.leftMargin = (j + k * 4 / 5);
        localView.setLayoutParams(localLayoutParams2);
        addView(localView);
    }


    private void initCourse() {
        for (int i = 0; i < 7; i++) {
            this.course[i] = new CourseList[7];
        }
    }


    private void loadingContent() {
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (this.course[i][j] == null) {
                    continue;
                }
                createLessonText(this.course[i][j]);
            }
        }
        //        addAnchorView();
    }


    public void addContentView(List<CourseEntity.Course> paramList) {
        removeAllViews();
        initCourse();
        if (paramList != null) {
            Iterator localIterator = paramList.iterator();
            while (localIterator.hasNext()) {
                CourseEntity.Course localCourse = (CourseEntity.Course) localIterator.next();
                this.colorSelector.addCourse(localCourse.begin_lesson, localCourse.hash_day);
                int i = localCourse.hash_lesson;
                int j = localCourse.hash_day;
                //数组的行数和列数，开始时没有格子就new一个，有就直接添加数据
                if (this.course[i][j] == null) {
                    this.course[i][j] = new CourseList();
                }
                this.course[i][j].list.add(localCourse);
            }
        }
        loadingContent();
    }


    //颜色选色器
    public static class CourseColorSelector {
        private int[] colors;
        private HashMap<String, Integer> mCourseColorMap;


        public CourseColorSelector() {
            int[] arrayOfInt = new int[4];
            arrayOfInt[0] = Color.argb(255, 254, 145, 103);
            arrayOfInt[1] = Color.argb(255, 120, 201, 252);
            arrayOfInt[2] = Color.argb(255, 111, 219, 188);
            arrayOfInt[3] = Color.argb(255, 191, 161, 233);
            this.colors = arrayOfInt;
            this.mCourseColorMap = new HashMap();
        }


        /**
         * 颜色选择的策略
         *
         * @param beginLesson 每一节课开始的节数
         * @param hashDay 周几的表示法，从0开始，0为周一，代表这个课程在周几
         */
        public void addCourse(int beginLesson, int hashDay) {
            // hashDay >=5 指的是周末
            if (hashDay >= 5) {
                this.mCourseColorMap.put(beginLesson + "," + hashDay,
                    Integer.valueOf(this.colors[3]));
                return;
            }
            if (beginLesson < 4) {
                this.mCourseColorMap.put(beginLesson + "," + hashDay,
                    Integer.valueOf(this.colors[0]));
                return;
            }
            if (beginLesson < 7) {
                this.mCourseColorMap.put(beginLesson + "," + hashDay,
                    Integer.valueOf(this.colors[1]));
                return;
            }
            this.mCourseColorMap.put(beginLesson + "," + hashDay, Integer.valueOf(this.colors[2]));
        }

        //        public void addCourse(String paramString) {
        //            Iterator localIterator = this.mCourseColorMap.entrySet().iterator();
        //            while (localIterator.hasNext())
        //                if (((String)((Map.Entry)localIterator.next()).getKey()).equals(paramString))
        //                    return;
        //            this.mCourseColorMap.put(paramString, Integer.valueOf(this.colors[(this.mCourseColorMap.size() % this.colors.length)]));
        //        }


        public int getCourseColor(int beginLesson, int hashDay) {
            return ((Integer) this.mCourseColorMap.get(beginLesson + "," + hashDay)).intValue();
        }

        //        public int getCourseColor(String paramString) {
        //            return ((Integer)this.mCourseColorMap.get(paramString)).intValue();
        //        }
    }


    public static class CourseList implements Serializable {
        public ArrayList<CourseEntity.Course> list = new ArrayList();
    }
}
