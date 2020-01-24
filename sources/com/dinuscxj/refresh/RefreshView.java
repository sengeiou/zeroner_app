package com.dinuscxj.refresh;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class RefreshView extends View implements IRefreshStatus {
    private static final int ANIMATION_DURATION = 888;
    private static final int DEFAULT_START_DEGREES = 285;
    private static final int DEFAULT_STROKE_WIDTH = 2;
    private static final int MAX_ARC_DEGREE = 330;
    private final RectF mArcBounds;
    private boolean mHasTriggeredRefresh;
    private final Paint mPaint;
    private ValueAnimator mRotateAnimator;
    private float mStartDegrees;
    private float mStrokeWidth;
    private float mSwipeDegrees;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mArcBounds = new RectF();
        this.mPaint = new Paint();
        initData();
        initPaint();
    }

    private void initData() {
        this.mStrokeWidth = 2.0f * getResources().getDisplayMetrics().density;
        this.mStartDegrees = 285.0f;
        this.mSwipeDegrees = 0.0f;
    }

    private void initPaint() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(this.mStrokeWidth);
        this.mPaint.setColor(Color.parseColor("#FFD72263"));
    }

    private void startAnimator() {
        this.mRotateAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mRotateAnimator.setInterpolator(new LinearInterpolator());
        this.mRotateAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                RefreshView.this.setStartDegrees(285.0f + (360.0f * ((Float) animation.getAnimatedValue()).floatValue()));
            }
        });
        this.mRotateAnimator.setRepeatMode(1);
        this.mRotateAnimator.setRepeatCount(-1);
        this.mRotateAnimator.setDuration(888);
        this.mRotateAnimator.start();
    }

    private void resetAnimator() {
        if (this.mRotateAnimator != null) {
            this.mRotateAnimator.cancel();
            this.mRotateAnimator.removeAllUpdateListeners();
            this.mRotateAnimator = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        resetAnimator();
        super.onDetachedFromWindow();
    }

    private void drawArc(Canvas canvas) {
        canvas.drawArc(this.mArcBounds, this.mStartDegrees, this.mSwipeDegrees, false, this.mPaint);
    }

    /* access modifiers changed from: private */
    public void setStartDegrees(float startDegrees) {
        this.mStartDegrees = startDegrees;
        postInvalidate();
    }

    public void setSwipeDegrees(float swipeDegrees) {
        this.mSwipeDegrees = swipeDegrees;
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float radius = ((float) Math.min(w, h)) / 2.0f;
        float centerX = ((float) w) / 2.0f;
        float centerY = ((float) h) / 2.0f;
        this.mArcBounds.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        this.mArcBounds.inset(this.mStrokeWidth / 2.0f, this.mStrokeWidth / 2.0f);
    }

    public void reset() {
        resetAnimator();
        this.mHasTriggeredRefresh = false;
        this.mStartDegrees = 285.0f;
        this.mSwipeDegrees = 0.0f;
    }

    public void refreshing() {
        this.mHasTriggeredRefresh = true;
        this.mSwipeDegrees = 330.0f;
        startAnimator();
    }

    public void pullToRefresh() {
    }

    public void releaseToRefresh() {
    }

    public void pullProgress(float pullDistance, float pullProgress) {
        if (!this.mHasTriggeredRefresh) {
            setSwipeDegrees(330.0f * Math.min(1.0f, pullProgress));
        }
    }
}
