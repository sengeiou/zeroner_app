package com.iwown.device_module.device_alarm_schedule.view.Calendar.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.Month;
import com.iwown.device_module.device_alarm_schedule.view.Calendar.Model.MonthDay;
import com.socks.library.KLog;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.litepal.crud.DataSupport;

public class MonthView extends View {
    private static final int DAYS_IN_WEEK = 7;
    public static final float heightRatioLunar = 0.5714286f;
    public static final float heightRatioSolar = 0.6f;
    private int dayHeight;
    private int dayWidth;
    private int hasScheduleEndIndex;
    private boolean[] isHasSchedule;
    private boolean isHasSelectDay = false;
    private boolean isShowLunar;
    private float mCircleRadius;
    private ShouldDismissListener mDismissListener;
    private int mHasScheduleCircleRadius;
    private int mHasScheduleCircleRadiusNoSelected;
    private int mHasScheduleCircleRadiusSelected;
    private float mLunarOffset;
    private float mLunarTextSize;
    private Month mMonth;
    private int mMonthBgColorDeep;
    private final Region[][] mMonthWithWeeks = ((Region[][]) Array.newInstance(Region.class, new int[]{6, 7}));
    private Paint mPaint;
    private CalendarView mPeCalendarView;
    private int mSelectDayBgColor;
    private int mSelectedIndex = -1;
    private int mSolarTextColor;
    private int mSolarTextSelectedColor;
    private float mSolarTextSize;
    private int mSolarTextTodayColor;
    private int mSolarTodayBgColor;
    private VelocityTracker mTracker = VelocityTracker.obtain();
    private int mUnCheckableColor;
    private int solarTextOffset;
    private String strUID;
    private Paint todayPaint;
    float veloX = 0.0f;
    float veloY = 0.0f;
    private int viewHeight;
    private int viewWidth;
    private int weekSize;

    public interface ShouldDismissListener {
        void shouldDismiss();
    }

    public void setMonth(Month month) {
        this.mMonth = month;
    }

    public void setDismissListener(ShouldDismissListener dismissListener) {
        this.mDismissListener = dismissListener;
    }

    public MonthView(Context context, Month month, int selectedDay, CalendarView peCalendarView) {
        super(context);
        this.mMonth = month;
        this.mPeCalendarView = peCalendarView;
        this.isShowLunar = peCalendarView.isShowLunar();
        this.mSelectedIndex = this.mMonth.getIndexOfDayInCurMonth(selectedDay);
        this.strUID = String.valueOf(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid));
        init();
        LayoutParams layoutParams = getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -2);
        }
        layoutParams.height = WindowsUtil.dip2px(getContext(), 300.0f);
        setLayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
        this.weekSize = this.mMonth.getWeeksInMonth();
        this.dayWidth = (int) (((float) w) / 7.0f);
        this.dayHeight = this.viewHeight / this.weekSize;
        if (this.isShowLunar) {
            this.mCircleRadius = ((float) this.dayWidth) / 3.2f;
            this.mSolarTextSize = ((float) h) / 15.0f;
        } else {
            this.mCircleRadius = ((float) this.dayWidth) / 4.0f;
            this.mSolarTextSize = ((float) h) / 15.0f;
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
        if (this.isShowLunar) {
            setMeasuredDimension(measureWidth, (int) (((float) measureWidth) * 0.5714286f));
        } else {
            setMeasuredDimension(measureWidth, (int) (((float) measureWidth) * 0.6f));
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mMonth != null) {
            int dayCount = this.mMonth.getmTotalDays();
            this.isHasSchedule = new boolean[dayCount];
            this.hasScheduleEndIndex = dayCount - 1;
            for (int i = 0; i < dayCount; i++) {
                this.isHasSchedule[i] = false;
            }
            new ArrayList();
            List<TB_schedulestatue> scheduleDataList = DataSupport.where("UID=? AND year=? AND month=? AND day>? AND day<?", this.strUID, String.valueOf(this.mMonth.getYear()), String.valueOf(this.mMonth.getMonth() + 1), String.valueOf(0), String.valueOf(dayCount + 1)).find(TB_schedulestatue.class);
            int num = scheduleDataList.size();
            for (int i2 = 0; i2 < num; i2++) {
                this.isHasSchedule[((TB_schedulestatue) scheduleDataList.get(i2)).getDay() - 1] = true;
            }
            canvas.save();
            drawBackground(canvas);
            for (int i3 = 0; i3 < this.weekSize; i3++) {
                for (int j = 0; j < 7; j++) {
                    draw(canvas, this.mMonthWithWeeks[i3][j].getBounds(), i3, j);
                }
            }
            canvas.restore();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != 1) {
            this.mTracker.addMovement(event);
            this.mTracker.computeCurrentVelocity(1000);
            KLog.e("Velo:X-Y", this.mTracker.getXVelocity() + "/" + this.mTracker.getYVelocity());
            this.veloY = this.mTracker.getYVelocity();
            this.veloX = this.mTracker.getXVelocity();
            if (this.veloY < -500.0f && Math.abs(this.veloY / this.veloX) > 1.0f && this.mDismissListener != null) {
                this.mDismissListener.shouldDismiss();
            }
        }
        switch (event.getAction()) {
            case 0:
                return true;
            case 1:
                handleClickEvent((int) event.getX(), (int) event.getY());
                return true;
            case 2:
                return false;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void handleClickEvent(int x, int y) {
        for (int i = 0; i < this.weekSize; i++) {
            int j = 0;
            while (true) {
                if (j >= 7) {
                    break;
                } else if (!this.mMonthWithWeeks[i][j].contains(x, y)) {
                    j++;
                } else {
                    MonthDay monthDay = this.mMonth.getMonthDay(i, j);
                    if (monthDay != null) {
                        if (monthDay.isCheckable()) {
                            this.mSelectedIndex = (i * 7) + j;
                            invalidate();
                            performDayClick();
                        } else {
                            int day = monthDay.getCalendar().get(5);
                            if (monthDay.getDayFlag() == 1) {
                                this.mPeCalendarView.showPrevMonth(day);
                            } else if (monthDay.getDayFlag() == 2) {
                                this.mPeCalendarView.showNextMonth(day);
                            }
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void performDayClick() {
        this.mPeCalendarView.dispatchDateClickListener(this.mMonth, this.mSelectedIndex);
    }

    private void drawBackground(Canvas canvas) {
        this.mPaint.setColor(this.mMonthBgColorDeep);
        for (int i = 0; i < this.weekSize; i++) {
            canvas.drawRect(0.0f, (float) (this.dayHeight * i), (float) this.viewWidth, (float) (this.dayHeight * (i + 1)), this.mPaint);
        }
    }

    private void draw(Canvas canvas, Rect rect, int xIndex, int yIndex) {
        MonthDay monthDay = this.mMonth.getMonthDay(xIndex, yIndex);
        drawSelectDayBackground(canvas, rect, monthDay, xIndex, yIndex);
        drawSolarText(canvas, rect, monthDay);
        if (this.isShowLunar) {
            drawLunarText(canvas, rect, monthDay);
        }
    }

    private void drawSelectDayBackground(Canvas canvas, Rect rect, MonthDay day, int xIndex, int yIndex) {
        if (this.mSelectedIndex == -1 && day.isFirstDay()) {
            this.mSelectedIndex = (xIndex * 7) + yIndex;
        }
        if (this.mSelectedIndex / 7 == xIndex && this.mSelectedIndex % 7 == yIndex) {
            this.mPaint.setColor(this.mSelectDayBgColor);
            canvas.drawCircle((float) rect.centerX(), (float) rect.centerY(), this.mCircleRadius, this.mPaint);
            this.isHasSelectDay = true;
            return;
        }
        Calendar todayCal = Calendar.getInstance();
        todayCal.setTimeInMillis(System.currentTimeMillis());
        if (todayCal.get(5) == day.getCalendar().get(5) && todayCal.get(2) == day.getCalendar().get(2)) {
            this.todayPaint.setColor(this.mSolarTodayBgColor);
            canvas.drawCircle((float) rect.centerX(), (float) rect.centerY(), this.mCircleRadius, this.todayPaint);
        }
    }

    private void drawSolarText(Canvas canvas, Rect rect, MonthDay monthDay) {
        if (monthDay != null) {
            this.mHasScheduleCircleRadius = this.mHasScheduleCircleRadiusNoSelected;
            if (this.isHasSelectDay) {
                this.mPaint.setColor(this.mSolarTextSelectedColor);
                this.mHasScheduleCircleRadius = this.mHasScheduleCircleRadiusSelected;
                if (!this.isShowLunar) {
                    this.isHasSelectDay = false;
                }
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
            int index = monthDay.getmDay() - 1;
            if (index <= this.hasScheduleEndIndex && index >= 0 && monthDay.getmMonth() == this.mMonth.getmMonth() && this.isHasSchedule[index]) {
                this.mPaint.setColor(this.mHasScheduleCircleRadius);
                canvas.drawCircle((float) rect.centerX(), ((float) rect.centerY()) + this.mCircleRadius + 15.0f, 8.0f, this.mPaint);
            }
        }
    }

    private void drawLunarText(Canvas canvas, Rect rect, MonthDay monthDay) {
        if (monthDay != null) {
            if (this.isHasSelectDay) {
                this.isHasSelectDay = false;
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
        this.todayPaint = new Paint(69);
        this.todayPaint.setStyle(Style.STROKE);
        this.todayPaint.setStrokeWidth(2.0f);
        if (this.mMonth.isMonthOfToday()) {
            this.mSelectedIndex = this.mMonth.getIndexOfToday();
        }
        setBackgroundColor(getResources().getColor(R.color.device_module_alarm_line_1));
        this.mMonthBgColorDeep = getResources().getColor(R.color.device_module_colorPeCalendarMonthBgDeep);
        this.mUnCheckableColor = getResources().getColor(R.color.device_module_colorPeCalendarUnCheckableColor);
        this.mSolarTextTodayColor = getResources().getColor(R.color.device_module_colorPeCalendarTodayColor);
        this.mSolarTextColor = getResources().getColor(R.color.device_module_colorPeCalendarSolarTextColor);
        this.mSelectDayBgColor = getResources().getColor(R.color.device_module_colorPeCalendarSelectBgColor);
        this.mSolarTodayBgColor = getResources().getColor(R.color.device_module_colorPeCalendarSelectBgColor);
        this.mSolarTextSelectedColor = getResources().getColor(R.color.device_module_colorPeCalendarSelectDayColor);
        this.mHasScheduleCircleRadiusNoSelected = getResources().getColor(R.color.device_module_mHasScheduleCircleRadiusSelectedColor);
        this.mHasScheduleCircleRadiusSelected = getResources().getColor(R.color.device_module_mHasScheduleCircleRadiusSelectedColor);
        this.mHasScheduleCircleRadius = this.mHasScheduleCircleRadiusNoSelected;
    }

    private void initMonthRegion() {
        for (int i = 0; i < this.weekSize; i++) {
            int iHeight1 = i * this.dayHeight;
            int iHeight2 = iHeight1 + this.dayHeight;
            for (int j = 0; j < 7; j++) {
                Region region = new Region();
                region.set(this.dayWidth * j, iHeight1, this.dayWidth + (this.dayWidth * j), iHeight2);
                this.mMonthWithWeeks[i][j] = region;
            }
        }
    }

    public int getmSelectedIndex() {
        return this.mSelectedIndex;
    }

    public void updateView() {
        invalidate();
    }

    public void updateView(Month month, int selectedDay) {
        this.mMonth = month;
        this.mSelectedIndex = this.mMonth.getIndexOfDayInCurMonth(selectedDay);
        invalidate();
        performDayClick();
    }

    public void setSelectedDay(int day) {
        int selectedDay;
        if (!this.mMonth.isMonthOfToday() || day != 0) {
            if (day == 0) {
                selectedDay = 1;
            } else {
                selectedDay = day;
            }
            this.mSelectedIndex = this.mMonth.getIndexOfDayInCurMonth(selectedDay);
        } else {
            this.mSelectedIndex = this.mMonth.getIndexOfToday();
        }
        invalidate();
        if (day == 0 || day != 0) {
            performDayClick();
        }
    }

    public void updateSelectedIndex(Month month, int selectedIndex) {
        this.mMonth = month;
        this.mSelectedIndex = selectedIndex;
        MonthDay monthDay = month.getMonthDay(selectedIndex);
        if (monthDay.isCheckable()) {
            invalidate();
            performDayClick();
            return;
        }
        int day = monthDay.getCalendar().get(5);
        if (monthDay.getDayFlag() == 1) {
            this.mPeCalendarView.showPrevMonth(day);
        } else if (monthDay.getDayFlag() == 2) {
            this.mPeCalendarView.showNextMonth(day);
        }
    }
}
