package com.iwown.sport_module.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.R;

public class SportDataGuideLine extends View {
    private static final int DIRECTION_LEFT_DOWN = 2;
    private static final int DIRECTION_LEFT_HORIZONTAL = 0;
    private static final int DIRECTION_LEFT_UP = 1;
    private static final int DIRECTION_RIGHT_DOWN = 5;
    private static final int DIRECTION_RIGHT_HORIZONTAL = 3;
    private static final int DIRECTION_RIGHT_UP = 4;
    private int dot_color;
    private int dot_radius = 8;
    private int extend_direction = 0;
    private int horizontal_extend_distance = 0;
    private int line_color;
    private Context mContext;
    private Paint mDotPaint;
    private float mEndX;
    private float mEndY;
    private int mHeight;
    private Paint mLindPaint;
    private float mStartX;
    private float mStartY;
    private float mTurnX;
    private float mTurnY;
    private int mWidth;

    public SportDataGuideLine(Context context) {
        super(context);
    }

    public SportDataGuideLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sport_module_SportGuideLine);
        this.dot_color = a.getColor(R.styleable.sport_module_SportGuideLine_sport_module_dot_color, ViewCompat.MEASURED_STATE_MASK);
        this.line_color = a.getColor(R.styleable.sport_module_SportGuideLine_sport_module_line_color, -7829368);
        this.extend_direction = a.getInt(R.styleable.sport_module_SportGuideLine_sport_module_extend_direction, 3);
        this.horizontal_extend_distance = a.getDimensionPixelSize(R.styleable.sport_module_SportGuideLine_sport_module_extend_distance, 200);
        a.recycle();
        this.mDotPaint = new Paint();
        this.mDotPaint.setAntiAlias(true);
        this.mDotPaint.setDither(true);
        this.mDotPaint.setColor(this.dot_color);
        this.mDotPaint.setStyle(Style.FILL);
        this.mLindPaint = new Paint();
        this.mLindPaint.setAntiAlias(true);
        this.mLindPaint.setDither(true);
        this.mLindPaint.setColor(this.line_color);
        this.mLindPaint.setStrokeWidth((float) DensityUtil.dip2px(this.mContext, 1.0f));
        this.mLindPaint.setStyle(Style.STROKE);
    }

    public SportDataGuideLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        Log.e("licl--origin--width", this.mWidth + "");
        this.mWidth += this.horizontal_extend_distance;
        Log.e("licl--final--width", this.mWidth + "");
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("licl--onDraw--measure", getMeasuredWidth() + "");
        drawLine(canvas);
        drawDot(canvas);
    }

    private void drawLine(Canvas canvas) {
        Path path = new Path();
        this.mStartX = 0.0f;
        this.mStartY = 0.0f;
        this.mTurnX = 0.0f;
        this.mTurnY = 0.0f;
        this.mEndX = 0.0f;
        this.mEndY = 0.0f;
        Log.e("licl--extend_direction", this.extend_direction + "");
        switch (this.extend_direction) {
            case 0:
                this.mStartX = (float) (this.mWidth - this.dot_radius);
                this.mStartY = (float) this.dot_radius;
                this.mEndX = (float) this.dot_radius;
                this.mEndY = (float) this.dot_radius;
                this.mTurnX = (float) this.horizontal_extend_distance;
                this.mTurnY = (float) this.dot_radius;
                break;
            case 2:
                this.mStartX = (float) (this.mWidth - this.dot_radius);
                this.mStartY = (float) this.dot_radius;
                this.mEndX = (float) this.dot_radius;
                this.mEndY = (float) (this.mHeight - this.dot_radius);
                this.mTurnX = (float) this.horizontal_extend_distance;
                this.mTurnY = (float) this.dot_radius;
                break;
            case 3:
                this.mStartX = (float) this.dot_radius;
                this.mStartY = (float) this.dot_radius;
                this.mEndX = (float) (this.mWidth - this.dot_radius);
                this.mEndY = (float) this.dot_radius;
                this.mTurnX = (float) (this.mWidth - this.horizontal_extend_distance);
                this.mTurnY = (float) this.dot_radius;
                break;
            case 5:
                this.mStartX = (float) this.dot_radius;
                this.mStartY = (float) this.dot_radius;
                this.mEndX = (float) (this.mWidth - this.dot_radius);
                this.mEndY = (float) (this.mHeight - this.dot_radius);
                this.mTurnX = (float) (this.mWidth - this.horizontal_extend_distance);
                this.mTurnY = (float) this.dot_radius;
                break;
        }
        path.moveTo(this.mStartX, this.mStartY);
        path.lineTo(this.mTurnX, this.mTurnY);
        path.lineTo(this.mEndX, this.mEndY);
        canvas.drawPath(path, this.mLindPaint);
    }

    private void drawDot(Canvas canvas) {
        canvas.drawCircle(this.mStartX, this.mStartY, (float) this.dot_radius, this.mDotPaint);
        canvas.drawCircle(this.mEndX, this.mEndY, (float) this.dot_radius, this.mDotPaint);
        Log.e("licl--start", this.mStartX + "/" + this.mStartY);
        Log.e("licl--turn", this.mTurnX + "/" + this.mTurnY);
        Log.e("licl--end", this.mEndX + "/" + this.mEndY);
    }
}
