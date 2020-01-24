package com.iwown.device_module.device_alarm_schedule.view.Calendar.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.device_module.R;

final class WeekLabelView extends View {
    private static final int DAYS_IN_WEEK = 7;
    public static final float HEIGHT_SCALE = 0.10714286f;
    private static int sHeight = 0;
    private String[] CHINESE_WEEK;
    private Paint mPaint;
    private int mWeekLabelUnderLineColor;
    private Paint mWeekLabelUnderLinePaint;
    private Region[] mWeekRegion = new Region[7];

    public WeekLabelView(Context context) {
        super(context);
        init();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int itemWidth = (int) (((float) w) / 7.0f);
        for (int i = 0; i < 7; i++) {
            this.mWeekRegion[i].set(i * itemWidth, 0, (i + 1) * itemWidth, h);
        }
        this.mPaint.setTextSize(((float) w) / 25.0f);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        FontMetrics fm = this.mPaint.getFontMetrics();
        int heightSize = ((int) Math.ceil((double) (fm.descent - fm.ascent))) + getPaddingTop() + getPaddingBottom();
        if (heightSize > sHeight) {
            sHeight = heightSize;
        }
        setMeasuredDimension(widthSize, (int) (((float) widthSize) * 0.10714286f));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        FontMetrics fm = this.mPaint.getFontMetrics();
        for (int i = 0; i < 7; i++) {
            Rect rect = this.mWeekRegion[i].getBounds();
            canvas.drawText(this.CHINESE_WEEK[i], (float) rect.centerX(), (((float) (rect.height() / 2)) - fm.descent) + ((fm.descent - fm.ascent) / 2.0f), this.mPaint);
            if (i == 6) {
                canvas.drawLine(0.0f, (float) rect.bottom, (float) rect.right, (float) rect.bottom, this.mWeekLabelUnderLinePaint);
            }
        }
    }

    private void init() {
        this.mWeekLabelUnderLineColor = getResources().getColor(R.color.device_module_colorPeCalendarUnCheckableColor);
        this.mPaint = new Paint(69);
        this.mPaint.setTextAlign(Align.CENTER);
        this.mPaint.setColor(-7829368);
        this.mWeekLabelUnderLinePaint = new Paint(69);
        this.mWeekLabelUnderLinePaint.setStrokeWidth(2.0f);
        this.mWeekLabelUnderLinePaint.setColor(this.mWeekLabelUnderLineColor);
        setPadding(0, 10, 0, 10);
        for (int i = 0; i < 7; i++) {
            this.mWeekRegion[i] = new Region();
        }
        this.CHINESE_WEEK = getResources().getStringArray(R.array.device_module_CalendarView_WeekLable);
    }
}
