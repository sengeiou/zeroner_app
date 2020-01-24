package com.iwown.device_module.device_alarm_schedule.view.Calendar.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.Month;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.MonthDay;
import java.util.Calendar;
import java.util.List;
import org.litepal.crud.DataSupport;

public class WeekView2 extends View {
    private static final int DAYS_IN_WEEK = 7;
    private static int sHeight = 0;
    private String[] CHINESE_WEEK;
    private SparseBooleanArray booleanArray;
    private int dayHeight;
    private int dayIndexOffset;
    private int dayWidth;
    private boolean[] isHasSchedule;
    private boolean isShowLunar;
    private float mCircleRadius;
    private int mHasScheduleCircleRadiusSelected;
    private float mLunarOffset;
    private float mLunarTextSize;
    private Month mMonth;
    private final Region[] mMonthWithWeeks;
    private OnDatePickerListener mOnDatePickerListener;
    private Paint mPaint;
    private List<TB_schedulestatue> mSchedulestatueList;
    private int mSelectDayBgColor;
    private int mSolarTextColor;
    private int mSolarTextSelectedColor;
    private float mSolarTextSize;
    private int mSolarTextTodayColor;
    private int mSolarTodayBgColor;
    private String mStrUID;
    private int mUnCheckableColor;
    private Paint mWeekLabelPaint;
    private int mWeekLabelUnderLineColor;
    private Paint mWeekLabelUnderLinePaint;
    private Region[] mWeekRegion;
    private int selectIndex;
    private int solarTextOffset;
    private Paint todayPaint;
    private int viewWidth;
    private int weekLableHeight;

    public interface OnDatePickerListener {
        void onDatePicker(Month month, int i);
    }

    public WeekView2(Context context) {
        this(context, null);
    }

    public WeekView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mWeekRegion = new Region[7];
        this.mMonthWithWeeks = new Region[7];
        this.isHasSchedule = new boolean[7];
        this.booleanArray = new SparseBooleanArray();
        init();
        initWeekView();
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
        this.weekLableHeight = (h - 20) / 2;
        if (this.isShowLunar) {
            this.mCircleRadius = ((float) this.dayWidth) / 2.2f;
            this.mSolarTextSize = ((float) h) / 3.0f;
        } else {
            this.mCircleRadius = ((float) this.dayWidth) / 4.0f;
            this.mSolarTextSize = ((float) h) / 6.0f;
            this.solarTextOffset = (this.dayHeight - this.weekLableHeight) / 5;
        }
        this.mPaint.setTextSize(this.mSolarTextSize);
        if (this.isShowLunar) {
            float solarHeight = this.mPaint.getFontMetrics().bottom - this.mPaint.getFontMetrics().top;
            this.mLunarTextSize = this.mSolarTextSize / 2.0f;
            this.mPaint.setTextSize(this.mLunarTextSize);
            this.mLunarOffset = ((Math.abs(this.mPaint.ascent() + this.mPaint.descent()) + solarHeight) + (this.mPaint.getFontMetrics().bottom - this.mPaint.getFontMetrics().top)) / 3.0f;
        }
        int itemWidth = (int) (((float) w) / 7.0f);
        for (int i = 0; i < 7; i++) {
            this.mWeekRegion[i].set(i * itemWidth, 0, (i + 1) * itemWidth, this.weekLableHeight);
        }
        this.mWeekLabelPaint.setTextSize(((float) w) / 25.0f);
        initMonthRegion();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(measureWidth, ((measureWidth / 7) * 8) / 5);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mMonth != null) {
            this.dayIndexOffset = this.mMonth.getMonthDay(this.selectIndex).getmDayOfWeek();
            canvas.save();
            FontMetrics fm = this.mPaint.getFontMetrics();
            for (int i = 0; i < 7; i++) {
                Rect rect = this.mWeekRegion[i].getBounds();
                canvas.drawText(this.CHINESE_WEEK[i], (float) rect.centerX(), (((float) (rect.height() / 2)) - fm.descent) + ((fm.descent - fm.ascent) / 2.0f), this.mWeekLabelPaint);
                if (i == 6) {
                    canvas.drawLine(0.0f, (float) rect.bottom, (float) rect.right, (float) rect.bottom, this.mWeekLabelUnderLinePaint);
                }
            }
            for (int i2 = 0; i2 < 7; i2++) {
                draw(canvas, this.mMonthWithWeeks[i2].getBounds(), i2);
            }
            canvas.restore();
        }
    }

    private void draw(Canvas canvas, Rect rect, int index) {
        MonthDay monthDay = this.mMonth.getMonthDay((this.selectIndex - this.dayIndexOffset) + index);
        drawSelectDayBackground(canvas, rect, index, monthDay);
        drawSolarText(canvas, rect, monthDay, index);
        if (this.isShowLunar) {
            drawLunarText(canvas, rect, monthDay, index);
        }
    }

    private void drawSelectDayBackground(Canvas canvas, Rect rect, int index, MonthDay day) {
        if (day.isCheckable()) {
            this.mSchedulestatueList = DataSupport.where("UID=? AND year=? AND month=? AND day=?", this.mStrUID, String.valueOf(this.mMonth.getYear()), String.valueOf(this.mMonth.getMonth() + 1), String.valueOf(day.getSolarDay())).find(TB_schedulestatue.class);
        }
        if (this.mSchedulestatueList != null && this.mSchedulestatueList.size() > 0) {
            this.mPaint.setColor(this.mHasScheduleCircleRadiusSelected);
            canvas.drawCircle((float) rect.centerX(), ((float) rect.centerY()) + this.mCircleRadius + 15.0f, 8.0f, this.mPaint);
        }
        if (index != this.dayIndexOffset) {
            Calendar todayCal = Calendar.getInstance();
            todayCal.setTimeInMillis(System.currentTimeMillis());
            if (todayCal.get(5) == day.getCalendar().get(5) && todayCal.get(2) == day.getCalendar().get(2)) {
                this.todayPaint.setColor(this.mSolarTodayBgColor);
                canvas.drawCircle((float) rect.centerX(), (float) rect.centerY(), this.mCircleRadius, this.todayPaint);
                return;
            }
            return;
        }
        this.mPaint.setColor(this.mSelectDayBgColor);
        canvas.drawCircle((float) rect.centerX(), (float) rect.centerY(), this.mCircleRadius, this.mPaint);
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
                canvas.drawText(monthDay.getSolarDay(), (float) rect.centerX(), (float) (rect.centerY() + ((this.solarTextOffset * 2) / 3)), this.mPaint);
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
        this.mStrUID = String.valueOf(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid));
        this.mPaint = new Paint(69);
        this.mPaint.setTextAlign(Align.CENTER);
        this.todayPaint = new Paint(69);
        this.todayPaint.setStyle(Style.STROKE);
        this.todayPaint.setStrokeWidth(2.0f);
        setBackgroundColor(getResources().getColor(R.color.device_module_alarm_2));
        this.mUnCheckableColor = getResources().getColor(R.color.device_module_colorPeCalendarUnCheckableColor);
        this.mSolarTextTodayColor = getResources().getColor(R.color.device_module_colorPeCalendarTodayColor);
        this.mSolarTextColor = getResources().getColor(R.color.device_module_colorPeCalendarSolarTextColor);
        this.mSelectDayBgColor = getResources().getColor(R.color.device_module_colorPeCalendarSelectBgColor);
        this.mSolarTextSelectedColor = getResources().getColor(R.color.device_module_colorPeCalendarSelectDayColor);
        this.mSolarTodayBgColor = getResources().getColor(R.color.device_module_colorPeCalendarSelectBgColor);
        this.mWeekLabelUnderLineColor = getResources().getColor(R.color.device_module_colorPeCalendarUnCheckableColor);
        this.mHasScheduleCircleRadiusSelected = getResources().getColor(R.color.device_module_mHasScheduleCircleRadiusSelectedColor);
    }

    private void initMonthRegion() {
        for (int i = 0; i < 7; i++) {
            Region region = new Region();
            region.set(this.dayWidth * i, this.weekLableHeight, this.dayWidth + (this.dayWidth * i), this.dayHeight);
            this.mMonthWithWeeks[i] = region;
        }
    }

    private void initWeekView() {
        this.mWeekLabelPaint = new Paint(69);
        this.mWeekLabelPaint.setTextAlign(Align.CENTER);
        this.mWeekLabelPaint.setColor(-7829368);
        this.mWeekLabelUnderLinePaint = new Paint(69);
        this.mWeekLabelUnderLinePaint.setStrokeWidth(1.0f);
        this.mWeekLabelUnderLinePaint.setColor(this.mWeekLabelUnderLineColor);
        setPadding(0, 10, 0, 10);
        for (int i = 0; i < 7; i++) {
            this.mWeekRegion[i] = new Region();
        }
        this.CHINESE_WEEK = getResources().getStringArray(R.array.device_module_CalendarView_WeekLable);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                return true;
            case 1:
                handleClickEvent((int) event.getX(), (int) event.getY());
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void handleClickEvent(int x, int y) {
        for (int index = 0; index < 7; index++) {
            if (this.mMonthWithWeeks[index].contains(x, y)) {
                int clickIndex = (this.selectIndex - this.dayIndexOffset) + index;
                if (clickIndex != this.selectIndex) {
                    MonthDay monthDay = this.mMonth.getMonthDay(clickIndex);
                    if (monthDay == null) {
                        return;
                    }
                    if (monthDay.isCheckable()) {
                        this.selectIndex = clickIndex;
                        invalidate();
                        if (this.mOnDatePickerListener != null) {
                            this.mOnDatePickerListener.onDatePicker(this.mMonth, this.selectIndex);
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    public void setOnDatePickerListener(OnDatePickerListener onDatePickerListener) {
        this.mOnDatePickerListener = onDatePickerListener;
    }
}
