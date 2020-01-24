package com.iwown.device_module.device_alarm_schedule.view.Calendar.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.device_module.R;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.Month;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.MonthDay;

public class WeekView extends View {
    private static final int DAYS_IN_WEEK = 7;
    private int dayHeight;
    private int dayIndexOffset;
    private int dayWidth;
    private boolean isShowLunar;
    private float mCircleRadius;
    private float mLunarOffset;
    private float mLunarTextSize;
    private Month mMonth;
    private final Region[] mMonthWithWeeks;
    private Paint mPaint;
    private int mSelectDayBgColor;
    private int mSolarTextColor;
    private int mSolarTextSelectedColor;
    private float mSolarTextSize;
    private int mSolarTextTodayColor;
    private int mUnCheckableColor;
    private int selectIndex;
    private int solarTextOffset;
    private int viewWidth;

    public WeekView(Context context) {
        this(context, null);
    }

    public WeekView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mMonthWithWeeks = new Region[7];
        init();
        setWeekView(null, 0);
    }

    public void setWeekView(Month month, int selectIndex2) {
        this.mMonth = month;
        this.selectIndex = selectIndex2;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.dayWidth = (int) (((float) w) / 7.0f);
        this.dayHeight = h;
        if (this.isShowLunar) {
            this.mCircleRadius = ((float) this.dayWidth) / 2.2f;
            this.mSolarTextSize = ((float) h) / 3.0f;
        } else {
            this.mCircleRadius = ((float) this.dayWidth) / 3.2f;
            this.mSolarTextSize = ((float) h) / 2.0f;
            this.solarTextOffset = this.dayHeight / 5;
        }
        this.mPaint.setTextSize(this.mSolarTextSize);
        if (this.isShowLunar) {
            float solarHeight = this.mPaint.getFontMetrics().bottom - this.mPaint.getFontMetrics().top;
            this.mLunarTextSize = this.mSolarTextSize / 2.0f;
            this.mPaint.setTextSize(this.mLunarTextSize);
            this.mLunarOffset = ((Math.abs(this.mPaint.ascent() + this.mPaint.descent()) + solarHeight) + (this.mPaint.getFontMetrics().bottom - this.mPaint.getFontMetrics().top)) / 3.0f;
        }
        initMonthRegion();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(measureWidth, measureWidth / 7);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mMonth != null) {
            this.dayIndexOffset = this.mMonth.getMonthDay(this.selectIndex).getmDayOfWeek();
            canvas.save();
            for (int i = 0; i < 7; i++) {
                draw(canvas, this.mMonthWithWeeks[i].getBounds(), i);
            }
            this.mPaint.setColor(this.mSelectDayBgColor);
            canvas.drawLine(0.0f, (float) (this.dayHeight - 1), (float) (this.viewWidth - 1), (float) (this.dayHeight - 1), this.mPaint);
            canvas.restore();
        }
    }

    private void draw(Canvas canvas, Rect rect, int index) {
        MonthDay monthDay = this.mMonth.getMonthDay((this.selectIndex - this.dayIndexOffset) + index);
        drawSelectDayBackground(canvas, rect, index);
        drawSolarText(canvas, rect, monthDay, index);
        if (this.isShowLunar) {
            drawLunarText(canvas, rect, monthDay, index);
        }
    }

    private void drawSelectDayBackground(Canvas canvas, Rect rect, int index) {
        if (index == this.dayIndexOffset) {
            this.mPaint.setColor(this.mSelectDayBgColor);
            canvas.drawCircle((float) rect.centerX(), (float) rect.centerY(), this.mCircleRadius, this.mPaint);
        }
    }

    private void drawSolarText(Canvas canvas, Rect rect, MonthDay monthDay, int index) {
        if (monthDay != null) {
            if (index == this.dayIndexOffset) {
                this.mPaint.setColor(this.mSolarTextSelectedColor);
            } else if (!monthDay.isCheckable()) {
                this.mPaint.setColor(this.mUnCheckableColor);
            } else if (monthDay.isToday()) {
                this.mPaint.setColor(this.mSolarTextTodayColor);
            } else {
                this.mPaint.setColor(this.mSolarTextColor);
            }
            this.mPaint.setTextSize(this.mSolarTextSize);
            if (this.isShowLunar) {
                canvas.drawText(monthDay.getSolarDay(), (float) rect.centerX(), (float) rect.centerY(), this.mPaint);
            } else {
                canvas.drawText(monthDay.getSolarDay(), (float) rect.centerX(), (float) (rect.centerY() + this.solarTextOffset), this.mPaint);
            }
        }
    }

    private void drawLunarText(Canvas canvas, Rect rect, MonthDay monthDay, int index) {
        if (monthDay != null) {
            if (index == this.dayIndexOffset) {
                this.mPaint.setColor(this.mSolarTextSelectedColor);
            } else if (monthDay.isToday()) {
                this.mPaint.setColor(this.mSolarTextTodayColor);
            } else {
                this.mPaint.setColor(this.mSolarTextColor);
            }
            this.mPaint.setTextSize(this.mLunarTextSize);
            canvas.drawText(monthDay.getLunarDay(), (float) rect.centerX(), ((float) rect.centerY()) + this.mLunarOffset, this.mPaint);
        }
    }

    private void init() {
        this.mPaint = new Paint(69);
        this.mPaint.setTextAlign(Align.CENTER);
        setBackgroundColor(getResources().getColor(R.color.device_module_white));
        this.mUnCheckableColor = getResources().getColor(R.color.device_module_colorPeCalendarUnCheckableColor);
        this.mSolarTextTodayColor = getResources().getColor(R.color.device_module_colorPeCalendarTodayColor);
        this.mSolarTextColor = getResources().getColor(R.color.device_module_colorPeCalendarSolarTextColor);
        this.mSelectDayBgColor = getResources().getColor(R.color.device_module_colorPeCalendarSelectBgColor);
        this.mSolarTextSelectedColor = getResources().getColor(R.color.device_module_colorPeCalendarSelectDayColor);
    }

    private void initMonthRegion() {
        for (int i = 0; i < 7; i++) {
            Region region = new Region();
            region.set(this.dayWidth * i, 0, this.dayWidth + (this.dayWidth * i), this.dayHeight);
            this.mMonthWithWeeks[i] = region;
        }
    }
}
