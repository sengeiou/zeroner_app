package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.sport_module.R;

public class SlideVpIndicator extends View {
    private float bar_len_ratio;
    private float bar_offset = 0.0f;
    private int dot_r = 0;
    private Paint mBarPaint;
    private int mBar_color;
    private float mBar_width;
    private int mDot_color;
    private int mDot_counts;
    private int mHeight;
    private float mInterval_of_dot;
    private Paint mPaint;
    private int mWidth = 0;

    public SlideVpIndicator(Context context) {
        super(context);
    }

    public SlideVpIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.slide_vp_indicator);
        this.mDot_color = a.getColor(R.styleable.slide_vp_indicator_dot_color, -1);
        this.mBar_color = a.getColor(R.styleable.slide_vp_indicator_bar_color, -1);
        this.mDot_counts = a.getInt(R.styleable.slide_vp_indicator_dot_count, 3);
        this.bar_len_ratio = a.getFloat(R.styleable.slide_vp_indicator_bar_len_ratio, 2.5f);
        Log.e("licl", this.mDot_color + "/" + this.mBar_color);
        a.recycle();
        initPaint();
    }

    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setColor(this.mDot_color);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
        this.mBarPaint = new Paint();
        this.mBarPaint.setColor(this.mBar_color);
        this.mBarPaint.setStyle(Style.FILL);
        this.mBarPaint.setAntiAlias(true);
        this.mBarPaint.setDither(true);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = 0;
        this.mHeight = 0;
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        if (modeW == Integer.MIN_VALUE) {
            this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (modeW == 1073741824) {
            this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (modeW == 0) {
        }
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        if (modeH == Integer.MIN_VALUE) {
            this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (modeH == 1073741824) {
            this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (modeH == 0) {
        }
        this.dot_r = this.mHeight;
        this.mInterval_of_dot = (float) ((((double) this.mWidth) * 1.0d) / ((double) (this.mDot_counts + 1)));
        if (this.bar_len_ratio >= 0.0f) {
            this.mBar_width = (this.bar_len_ratio * ((float) this.dot_r)) + ((float) this.dot_r);
        } else {
            this.mBar_width = (float) this.dot_r;
        }
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    private void drawIndicator(Canvas canvas) {
        for (int i = 1; i <= this.mDot_counts; i++) {
            canvas.drawCircle(this.mInterval_of_dot * ((float) i), (float) ((((double) this.mHeight) * 1.0d) / 2.0d), (float) ((((double) this.dot_r) * 1.0d) / 2.0d), this.mPaint);
        }
        canvas.save();
        canvas.translate((this.bar_offset * (this.mInterval_of_dot * ((float) (this.mDot_counts - 1)))) / ((float) (this.mDot_counts - 1)), 0.0f);
        this.mBarPaint.setColor(this.mBar_color);
        RectF rectF = new RectF(this.mInterval_of_dot - (this.mBar_width / 2.0f), 0.0f, this.mInterval_of_dot + (this.mBar_width / 2.0f), (float) this.mHeight);
        float rx = this.dot_r % 2 == 0 ? (float) (this.dot_r / 2) : (float) (((double) (this.dot_r / 2)) + 0.5d);
        canvas.drawRoundRect(rectF, rx, rx, this.mBarPaint);
        canvas.restore();
    }

    public void setMoveOffset(float offset) {
        this.bar_offset = offset;
        invalidate();
    }
}
