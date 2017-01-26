package com.srtianxia.bleattendance.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.srtianxia.bleattendance.R;
import com.srtianxia.bleattendance.utils.DensityUtil;

/**
 * Created by 梅梅 on 2017/1/21.
 */
public class CourseDetailView extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private int mode;           //viewpager指示器的模式
    private int gravity;        //viewpager内容的位置
    private int delay = 0;      //viewpager内容自动滚动的时间间隔
    private View mHintView;

    public CourseDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    /**
     * 1、获取属性参数
     * 2、初始化viewpager，add到CourseDetailview中
     * 3、根据属性mode，设置初始化指示器
     * @param attrs    属性
     */
    private void initView(AttributeSet attrs) {
        TypedArray type = getContext().obtainStyledAttributes(attrs, R.styleable.course_detail_view);
        mode = type.getInteger(R.styleable.course_detail_view_hint_mode,0);
        gravity = type.getInteger(R.styleable.course_detail_view_hint_gravity,1);
        delay = type.getInt(R.styleable.course_detail_view_auto_play,0);

        mViewPager = new ViewPager(getContext());
        mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mViewPager);
        type.recycle();

        initHint();
    }

    /**
     *根据mode设置指示器的形式
     */
    private void initHint() {
        View hintView = null;
        switch (mode){
            case 0:
                hintView = new PointHintView(getContext());
                break;
            default:

                break;
        }
        if (hintView != null){
            addView(hintView);
            mHintView = hintView;
        }
    }

    public void setAdapter(PagerAdapter adapter){
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
        mAdapter = adapter;
        initHintView(adapter);
    }

    /**
     *设置HintView的位置，设置HintView的默认值
     * @param adapter
     */
    private void initHintView(PagerAdapter adapter){
        //设置指示器的位置（设置好HintView的位置，各个点(ImageView)就放在里边）
        LayoutParams layoutparams = new LayoutParams(LayoutParams.MATCH_PARENT,DensityUtil.dp2px(getContext(),24));
        layoutparams.addRule(ALIGN_PARENT_BOTTOM);
        mHintView.setLayoutParams(layoutparams);

        //默认选择第一项
        ((HintView)mHintView).initView(adapter.getCount(),gravity);
        ((HintView) mHintView).setCurrent(0);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 监听viewpager,当viewpager滑动时，指示器也跟着变化
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        ((HintView)mHintView).setCurrent(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface HintView{
        void initView(int length, int gravity);

        void setCurrent(int current);
    }

    /**
     * 点指示器（用一个或者多个点作为滑动viewpager视图时的指示器）
     */
    public class PointHintView extends LinearLayout implements HintView{

        private ImageView[] mPoints;                //ViewPager指示器集合
        private int length = 0;                         //ViewPager内容个数（点的个数、指示器的个数）
        private int lastPositon = 0;                    //上一个划过的ViewPager子View的位置

        private GradientDrawable point_normal;      //未选中时点的背景
        private GradientDrawable point_focus;       //选中时点的背景

        public PointHintView(Context context) {
            super(context);
        }

        public PointHintView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }


        /**
         *根据length和gravity设置参数，对指示器进行初始化工作
         * @param length    viewpager内容的个数 == 指示器的个数 == 点的个数
         * @param gravity   控制imageview(指示器==点)的位置
         */
        @Override
        public void initView(int length, int gravity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                setLayoutDirection(LAYOUT_DIRECTION_LTR);
            }
            switch (gravity){
                case 0:
                    setGravity(Gravity.LEFT | Gravity.CENTER);
                    break;
                case 1:
                    setGravity(Gravity.CENTER);
                    break;
                case 2:
                    setGravity(Gravity.LEFT | Gravity.CENTER);
            }

            mPoints = new ImageView[length];
            this.length = length;

            point_normal = new GradientDrawable();
            point_normal.setColor(Color.GRAY);
            point_normal.setAlpha(125);
            point_normal.setCornerRadius(DensityUtil.dp2px(getContext(),4));
            point_normal.setSize(DensityUtil.dp2px(getContext(),8),DensityUtil.dp2px(getContext(),8));

            point_focus = new GradientDrawable();
            point_focus.setColor(getResources().getColor(R.color.colorPrimaryDark));
            point_focus.setCornerRadius(DensityUtil.dp2px(getContext(),4));
            point_focus.setSize(DensityUtil.dp2px(getContext(),8),DensityUtil.dp2px(getContext(),8));

            for (int i = 0; i < length; i++){
                mPoints[i] = new ImageView(getContext());
                LayoutParams layoutparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutparams.setMargins(10,0,10,0);
                mPoints[i].setLayoutParams(layoutparams);
                mPoints[i].setBackgroundDrawable(point_normal);
                addView(mPoints[i]);
            }
        }

        @Override
        public void setCurrent(int current) {
            if (current <0 || current > length-1){
                return;
            }
            mPoints[lastPositon].setBackgroundDrawable(point_normal);
            mPoints[current].setBackgroundDrawable(point_focus);
            lastPositon = current;
        }
    }


}
