package com.iwown.sport_module.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.util.WindowUtil;

public class ActiveTodayChart extends View {
    public static int TYPE_CALORIA = 1;
    public static int TYPE_STAND_UP = 2;
    public static int TYPE_STEP = 0;
    private int TOTAL_WITH = 100;
    private boolean hasData = true;
    private Paint mBottomLinePaint;
    private int mBottom_line_y;
    private Paint mColumnPaint;
    private int mColumn_value_width;
    private Context mContext;
    private int mData_type;
    private int mHeight;
    private Paint mTextPaint;
    private int mText_size;
    private String[] mTimeStrArr;
    private int[] mTimes;
    private float[] mValues;
    private int mWidth;
    private float[] stepValue;

    public ActiveTodayChart(Context context) {
        super(context);
    }

    public ActiveTodayChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.TOTAL_WITH = DensityUtil.getScreenWidth(context);
        this.mContext = context;
        this.mColumn_value_width = DensityUtil.dip2px(this.mContext, 7.0f);
        this.mValues = new float[24];
        this.mTimes = new int[]{0, 6, 12, 18};
        this.mTimeStrArr = this.mContext.getResources().getStringArray(R.array.sport_module_24h_string);
        this.mText_size = DensityUtil.dip2px(this.mContext, 15.0f);
        this.mData_type = TYPE_STEP;
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setDither(true);
        this.mTextPaint.setTextAlign(Align.CENTER);
        this.mTextPaint.setColor(Color.parseColor("#76A0E3"));
        this.mTextPaint.setTextSize((float) this.mText_size);
        this.mBottomLinePaint = new Paint();
        this.mBottomLinePaint.setAntiAlias(true);
        this.mBottomLinePaint.setDither(true);
        this.mBottomLinePaint.setColor(Color.parseColor("#ffffffff"));
        this.mBottomLinePaint.setStrokeWidth((float) DensityUtil.dip2px(this.mContext, 1.0f));
        this.mBottomLinePaint.setStyle(Style.STROKE);
        this.mColumnPaint = new Paint();
        this.mColumnPaint.setAntiAlias(true);
        this.mColumnPaint.setDither(true);
        this.mColumnPaint.setColor(Color.parseColor("#FFFFFF"));
    }

    public ActiveTodayChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        if (modeW == Integer.MIN_VALUE) {
            this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (modeW == 1073741824) {
            this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (modeW == 0) {
            this.mWidth = this.TOTAL_WITH;
        }
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        if (modeH == Integer.MIN_VALUE) {
            this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (modeH == 1073741824) {
            this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (modeH == 0) {
            this.mHeight = DensityUtil.dip2px(this.mContext, 280.0f);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBottomLine(canvas);
        drawColumn(canvas);
    }

    private void drawColumn(Canvas canvas) {
        float interval = (float) ((((double) (this.mWidth - this.mColumn_value_width)) * 1.0d) / 23.0d);
        float max_value = -1.0f;
        for (int i = 0; i < this.mValues.length; i++) {
            max_value = Math.max(max_value, this.mValues[i]);
        }
        if (!this.hasData) {
            this.mTextPaint.setTextAlign(Align.CENTER);
            canvas.drawText(this.mContext.getString(R.string.sport_module_no_data), ((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f, this.mTextPaint);
            return;
        }
        int textH = 0;
        String max = "";
        if (this.mData_type == TYPE_STEP) {
            max = ((int) max_value) + "";
            textH = WindowUtil.getTextWidthAndHigh(this.mTextPaint, max)[1];
        } else if (this.mData_type == TYPE_CALORIA) {
            max = Util.doubleToFloat(1, (double) max_value) + "";
            textH = WindowUtil.getTextWidthAndHigh(this.mTextPaint, max)[1];
        } else if (this.mData_type == TYPE_STAND_UP) {
        }
        int end_y = textH + DensityUtil.dip2px(this.mContext, 10.0f);
        int start_y = this.mBottom_line_y - 2;
        boolean has_draw_max_value = false;
        for (int i2 = 0; i2 < this.mValues.length; i2++) {
            float value = this.mValues[i2];
            float left = interval * ((float) i2);
            float top = 0.0f;
            if (this.mData_type != TYPE_STAND_UP) {
                top = ((float) start_y) - ((value / max_value) * ((float) (start_y - end_y)));
            }
            float right = (((float) i2) * interval) + ((float) this.mColumn_value_width);
            float bottom = (float) start_y;
            if (this.mData_type == TYPE_STAND_UP) {
                if (this.mValues[i2] >= 7.0f || this.stepValue[i2] >= 250.0f) {
                    this.mColumnPaint.setColor(Color.parseColor("#FFFFFF"));
                } else {
                    this.mColumnPaint.setColor(this.mContext.getResources().getColor(R.color.sport_module_2669d4));
                }
            }
            canvas.drawRect(left, top, right, bottom, this.mColumnPaint);
            this.mColumnPaint.setColor(Color.parseColor("#FFFFFF"));
            if (value == max_value && this.mData_type != TYPE_STAND_UP && !has_draw_max_value && max_value > 0.0f) {
                has_draw_max_value = true;
                if (i2 != 0 && i2 != this.mValues.length - 1) {
                    this.mTextPaint.setTextAlign(Align.CENTER);
                    canvas.drawText(max, (((float) this.mColumn_value_width) / 2.0f) + left, (float) textH, this.mTextPaint);
                } else if (i2 == 0) {
                    this.mTextPaint.setTextAlign(Align.LEFT);
                    canvas.drawText(max, left, (float) textH, this.mTextPaint);
                } else if (i2 == this.mValues.length - 1) {
                    this.mTextPaint.setTextAlign(Align.RIGHT);
                    canvas.drawText(max, ((float) this.mColumn_value_width) + left, (float) textH, this.mTextPaint);
                }
            }
            for (int i3 : this.mTimes) {
                if (i2 == i3) {
                    this.mTextPaint.setTextAlign(Align.LEFT);
                    canvas.drawText(this.mTimeStrArr[i2], left, (float) (this.mHeight - 5), this.mTextPaint);
                    this.mBottomLinePaint.setStrokeWidth((float) DensityUtil.dip2px(this.mContext, 2.0f));
                    canvas.drawLine(left, (float) this.mBottom_line_y, left, (float) (this.mBottom_line_y + 8), this.mBottomLinePaint);
                }
            }
        }
    }

    private void drawBottomLine(Canvas canvas) {
        this.mBottomLinePaint.setStrokeWidth((float) DensityUtil.dip2px(this.mContext, 1.0f));
        this.mBottom_line_y = (this.mHeight - WindowUtil.getTextWidthAndHigh(this.mTextPaint, this.mTimeStrArr[this.mTimes.length - 1])[1]) - DensityUtil.dip2px(this.mContext, 10.0f);
        canvas.drawLine(0.0f, (float) this.mBottom_line_y, (float) this.mWidth, (float) this.mBottom_line_y, this.mBottomLinePaint);
    }

    public void refresh(@Size(max = 24, min = 24) float[] values, float[] stepValue2, int data_type) {
        this.mData_type = data_type;
        this.mValues = values;
        this.stepValue = stepValue2;
        this.hasData = true;
        invalidate();
    }

    public void noData() {
        this.hasData = false;
        invalidate();
    }
}
