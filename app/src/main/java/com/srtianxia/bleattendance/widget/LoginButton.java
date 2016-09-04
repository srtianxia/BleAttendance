package com.srtianxia.bleattendance.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import com.srtianxia.bleattendance.utils.DensityUtil;

/**
 * Created by srtianxia on 2016/8/29.
 */
public class LoginButton extends View {
    private Paint mRectPaint;
    private Paint mCirclePaint;
    private Paint mProgressPaint;
    private Paint mTextPaint;

    private int mColor = 0xff6abad3;
    private int mProgressColor = 0xffffffff;
    private float mButtonWidth = 300;
    private int mButtonHeight = 120;

    private float angle = 0;

    private boolean isPressed = false;

    private boolean isOnceClick = false;

    private String mText = "LOGIN IN";


    public LoginButton(Context context) {
        this(context, null);
    }


    public LoginButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mButtonWidth = DensityUtil.dp2px(getContext(), 180);
    }


    public LoginButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRectPaint = new Paint();
        mRectPaint.setColor(mColor);
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setStrokeWidth(mButtonHeight);
        mRectPaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mColor);
        mCirclePaint.setAntiAlias(true);

        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(5);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mProgressColor);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(DensityUtil.sp2px(getContext(), 12));
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
        drawCircle(canvas);
        if (isPressed) {
            drawArcProgress(canvas);
        } else {
            drawText(canvas);
        }
    }


    private void drawRect(Canvas canvas) {
        canvas.drawLine(getWidth() / 2, getHeight() / 2,
            getWidth() / 2 - mButtonWidth / 2, getHeight() / 2,
            mRectPaint);
        canvas.drawLine(getWidth() / 2, getHeight() / 2,
            getWidth() / 2 + mButtonWidth / 2, getHeight() / 2,
            mRectPaint);
    }


    private void drawText(Canvas canvas) {
        canvas.drawText(mText, getWidth() / 2 - mTextPaint.measureText(mText) / 2,
            getHeight() / 2 - getTextDatumLine() / 2, mTextPaint);
    }


    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2 - mButtonWidth / 2,
            getHeight() / 2, mButtonHeight / 2, mCirclePaint);
        canvas.drawCircle(getWidth() / 2 + mButtonWidth / 2,
            getHeight() / 2, mButtonHeight / 2, mCirclePaint);
    }


    public void get() {

    }


    private void startTranslation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mButtonWidth, 0);
        valueAnimator.setDuration(800);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (float) valueAnimator.getAnimatedValue();
                mButtonWidth = progress;
                invalidate();
                if (progress == 0) {
                    isPressed = true;
                    startRotate();
                }
            }
        });
        valueAnimator.start();
    }


    private void drawArcProgress(Canvas canvas) {
        canvas.drawArc(new RectF((float) (getWidth() / 2 - (mButtonHeight / 2) * Math.sin(45))
                , (float) (getHeight() / 2 - (mButtonHeight / 2) * Math.sin(45)),
                (float) (getWidth() / 2 + (mButtonHeight / 2) * Math.sin(45)),
                (float) (getHeight() / 2 + (mButtonHeight / 2) * Math.sin(45))), angle, 270,
            false,
            mProgressPaint);
    }


    private void startRotate() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setDuration(800);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                angle = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }


    public void executeLogin() {
        isOnceClick = true;
        startTranslation();
    }


    private float getTextDatumLine() {
        return mTextPaint.getFontMetrics().descent - (mTextPaint.getFontMetrics().bottom - mTextPaint.getFontMetrics().top) / 2;
    }
}
