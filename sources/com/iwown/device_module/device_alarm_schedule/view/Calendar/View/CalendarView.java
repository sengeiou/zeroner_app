package com.iwown.device_module.device_alarm_schedule.view.Calendar.View;

import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.google.common.primitives.Ints;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.Month;
import java.util.Calendar;

public class CalendarView extends LinearLayout {
    private Month curMonth;
    private boolean isShowLunar;
    private Context mContext;
    private MonthView mMonthView;
    private NewMonthViewListener mMonthViewListener;
    private OnDatePickListener mOnDatePickListener;
    private int mWeekLabelBackgroundColor;
    private WeekLabelView mWeekLabelView;

    public interface NewMonthViewListener {
        void onNewMonthView();
    }

    public interface OnDatePickListener {
        void onDatePick(CalendarView calendarView, Month month, int i);
    }

    public MonthView getMonthView() {
        return this.mMonthView;
    }

    public void setMonthViewListener(NewMonthViewListener monthViewListener) {
        this.mMonthViewListener = monthViewListener;
    }

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mWeekLabelBackgroundColor = 528420;
        this.mContext = context;
        init(attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureHeight;
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(measureWidth, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(measureWidth, Ints.MAX_POWER_OF_TWO));
        }
        if (this.isShowLunar) {
            measureHeight = ((int) (((float) measureWidth) * 0.5714286f)) + this.mWeekLabelView.getMeasuredHeight();
        } else {
            measureHeight = ((int) (((float) measureWidth) * 0.6f)) + this.mWeekLabelView.getMeasuredHeight();
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    public boolean isShowLunar() {
        return this.isShowLunar;
    }

    private void init(AttributeSet attrs) {
        if (VERSION.SDK_INT >= 19) {
            setClipToPadding(false);
            setClipChildren(false);
        } else {
            setClipChildren(true);
            setClipToPadding(true);
        }
        setOrientation(1);
        this.mWeekLabelView = new WeekLabelView(getContext());
        this.mWeekLabelView.setBackgroundColor(Color.parseColor("#081024"));
        addView(this.mWeekLabelView);
        Calendar caleandar = Calendar.getInstance();
        this.curMonth = new Month(caleandar.get(1), caleandar.get(2), caleandar.get(5));
        this.mMonthView = new MonthView(getContext(), this.curMonth, 1, this);
        addView(this.mMonthView);
    }

    public Month getCurMonth() {
        return this.curMonth;
    }

    public int getCurSelectIndex() {
        return this.mMonthView.getmSelectedIndex();
    }

    public void showPrevMonth(int selectedDay) {
        if (this.curMonth.getYear() != 2000) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(this.curMonth.getYear(), this.curMonth.getMonth(), 1);
            calendar.add(2, -1);
            this.curMonth = new Month(calendar.get(1), calendar.get(2), calendar.get(5));
            if (this.mMonthView != null) {
                removeView(this.mMonthView);
                this.mMonthView = new MonthView(getContext(), this.curMonth, selectedDay, this);
                addView(this.mMonthView);
                if (this.mMonthViewListener != null) {
                    this.mMonthViewListener.onNewMonthView();
                }
                this.mMonthView.performDayClick();
            }
        }
    }

    public void performDayClick() {
        this.mMonthView.performDayClick();
    }

    public void showNextMonth(int selectedDay) {
        if (this.curMonth.getYear() != 2099) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(this.curMonth.getYear(), this.curMonth.getMonth(), 1);
            calendar.add(2, 1);
            this.curMonth = new Month(calendar.get(1), calendar.get(2), calendar.get(5));
            if (this.mMonthView != null) {
                removeView(this.mMonthView);
                this.mMonthView = new MonthView(getContext(), this.curMonth, selectedDay, this);
                addView(this.mMonthView);
                if (this.mMonthViewListener != null) {
                    this.mMonthViewListener.onNewMonthView();
                }
                this.mMonthView.performDayClick();
            }
        }
    }

    public void showMonth(int dmonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, dmonth);
        this.curMonth = new Month(calendar.get(1), calendar.get(2), calendar.get(5));
        if (this.mMonthView != null) {
            this.mMonthView.setMonth(this.curMonth);
            this.mMonthView.performDayClick();
        }
    }

    public void updateView() {
        this.mMonthView.updateView();
    }

    public void updateSelectedIndex(Month month, int selectedIndex) {
        this.mMonthView.updateSelectedIndex(month, selectedIndex);
    }

    public void setOnDatePickListener(OnDatePickListener l) {
        this.mOnDatePickListener = l;
    }

    /* access modifiers changed from: protected */
    public void dispatchDateClickListener(Month month, int selectIndex) {
        if (this.mOnDatePickListener != null) {
            this.mOnDatePickListener.onDatePick(this, month, selectIndex);
        }
    }

    public void showNextMonth(Month curMonth2, int selectDay) {
        this.curMonth = curMonth2;
        showNextMonth(selectDay);
    }

    public void showPrevMonth(Month curMonth2, int selectDay) {
        this.curMonth = curMonth2;
        showPrevMonth(selectDay);
    }

    public void showDay(int day) {
        this.mMonthView.setSelectedDay(day);
    }

    public boolean restView() {
        Calendar caleandar = Calendar.getInstance();
        if (isToday(this.curMonth.getYear(), this.curMonth.getMonth(), this.curMonth.getMonthDay(getCurSelectIndex()).getmDay(), caleandar)) {
            return false;
        }
        removeView(this.mMonthView);
        this.curMonth = new Month(caleandar.get(1), caleandar.get(2), caleandar.get(5));
        this.mMonthView = new MonthView(getContext(), this.curMonth, 1, this);
        addView(this.mMonthView);
        if (this.mMonthViewListener == null) {
            return true;
        }
        this.mMonthViewListener.onNewMonthView();
        return true;
    }

    private boolean isToday(int year, int month, int day, Calendar curCalendar) {
        if (day == curCalendar.get(5) && month == curCalendar.get(2) && year == curCalendar.get(1)) {
            return true;
        }
        return false;
    }
}
