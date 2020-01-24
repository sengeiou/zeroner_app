package com.iwown.sport_module.view.calendar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.view.calendar.HistoryCalendar.ShowLeveTag;
import com.socks.library.KLog;
import java.util.Date;
import java.util.Map;

public class CalendarShowHanlder {
    private static CalendarShowHanlder calendarShowHanlder;
    /* access modifiers changed from: private */
    public CalendarDayClickData calendarDayClickData = new CalendarDayClickData();
    CallBack callBack;
    private final Context context;
    private boolean leveTag = false;
    private View mCalendarLeft;
    /* access modifiers changed from: private */
    public ImageView mCalendarRight;
    private TextView mCalendarTitle;
    /* access modifiers changed from: private */
    public HistoryCalendar mCalendarView;
    private ProgressBar mCalendar_loading;
    private final String[] months;
    private PopupWindow popupWindow_calendar;
    /* access modifiers changed from: private */
    public int selectedDay;
    /* access modifiers changed from: private */
    public int selectedMonth;
    /* access modifiers changed from: private */
    public int selectedYear;

    public interface CallBack {
        void onResult(int i, int i2, int i3);
    }

    public void setLeveTag(boolean leveTag2) {
        this.leveTag = leveTag2;
    }

    public void setCallBack(CallBack callBack2) {
        this.callBack = callBack2;
    }

    public void setRoundColor(int color) {
        this.mCalendarView.setRoundColor(color);
    }

    private CalendarShowHanlder(Context context2) {
        this.context = context2;
        DateUtil dateUtil = new DateUtil();
        this.selectedYear = dateUtil.getYear();
        this.selectedMonth = dateUtil.getMonth();
        this.selectedDay = dateUtil.getDay();
        if (this.popupWindow_calendar == null) {
            this.popupWindow_calendar = new PopupWindow(context2);
            this.popupWindow_calendar.setWidth(-1);
            this.popupWindow_calendar.setHeight(DensityUtil.dip2px(context2, 320.0f));
            this.popupWindow_calendar.setContentView(LayoutInflater.from(context2).inflate(R.layout.sport_module_sleep_calendar_layout, null));
            this.popupWindow_calendar.setBackgroundDrawable(new ColorDrawable(0));
            this.popupWindow_calendar.setOutsideTouchable(false);
            this.popupWindow_calendar.setFocusable(true);
        }
        this.months = context2.getResources().getStringArray(R.array.sport_module_months_items);
        this.mCalendarRight = (ImageView) this.popupWindow_calendar.getContentView().findViewById(R.id.calendar_right);
        this.mCalendarLeft = this.popupWindow_calendar.getContentView().findViewById(R.id.calendar_left);
        this.mCalendarTitle = (TextView) this.popupWindow_calendar.getContentView().findViewById(R.id.calendar_title);
        this.mCalendarView = (HistoryCalendar) this.popupWindow_calendar.getContentView().findViewById(R.id.calendar_view);
        this.mCalendar_loading = (ProgressBar) this.popupWindow_calendar.getContentView().findViewById(R.id.calendar_loading);
        this.mCalendarView.update(this.selectedYear, this.selectedMonth, this.selectedDay);
        this.mCalendarView.setOnCalendarDayClick(new OnCalendarDayClick() {
            public void onDayClick(CalendarDayClickData item) {
                CalendarShowHanlder.this.calendarDayClickData.set(item);
                CalendarShowHanlder.this.selectedYear = item.getYear();
                CalendarShowHanlder.this.selectedMonth = item.getMonth();
                CalendarShowHanlder.this.selectedDay = item.getDay();
                CalendarShowHanlder.this.hiddenCalendar();
                CalendarShowHanlder.this.resultDate();
            }
        });
        this.mCalendarLeft.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DateUtil dateUtil = new DateUtil(CalendarShowHanlder.this.selectedYear, CalendarShowHanlder.this.selectedMonth, CalendarShowHanlder.this.selectedDay);
                dateUtil.addMonth(-1);
                CalendarShowHanlder.this.selectedYear = dateUtil.getYear();
                CalendarShowHanlder.this.selectedMonth = dateUtil.getMonth();
                CalendarShowHanlder.this.selectedDay = dateUtil.getDay();
                CalendarShowHanlder.this.mCalendarView.update(CalendarShowHanlder.this.selectedYear, CalendarShowHanlder.this.selectedMonth, CalendarShowHanlder.this.selectedDay);
                CalendarShowHanlder.this.mCalendarRight.setVisibility(0);
                CalendarShowHanlder.this.updateCanTitle();
                CalendarShowHanlder.this.resultDate();
            }
        });
        this.mCalendarRight.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DateUtil dateUtil = new DateUtil(CalendarShowHanlder.this.selectedYear, CalendarShowHanlder.this.selectedMonth, CalendarShowHanlder.this.selectedDay);
                dateUtil.addMonth(1);
                CalendarShowHanlder.this.selectedYear = dateUtil.getYear();
                CalendarShowHanlder.this.selectedMonth = dateUtil.getMonth();
                CalendarShowHanlder.this.selectedDay = dateUtil.getDay();
                if (DateUtil.isSameMonth(new Date(), new Date(dateUtil.getTimestamp()))) {
                    CalendarShowHanlder.this.mCalendarRight.setVisibility(4);
                }
                CalendarShowHanlder.this.mCalendarView.update(CalendarShowHanlder.this.selectedYear, CalendarShowHanlder.this.selectedMonth, CalendarShowHanlder.this.selectedDay);
                CalendarShowHanlder.this.updateCanTitle();
                CalendarShowHanlder.this.resultDate();
            }
        });
        this.mCalendarRight.setVisibility(4);
        updateCanTitle();
    }

    public void controlCalendarLoading(boolean showLoading) {
        if (showLoading) {
            this.mCalendar_loading.setVisibility(0);
        } else {
            this.mCalendar_loading.setVisibility(8);
        }
    }

    public void updateSleepStatus(Context context2, Map<String, ShowLeveTag> showLeveTagList) {
        if (this.mCalendarView != null) {
            this.mCalendarView.updateLevelTags(showLeveTagList);
        }
    }

    /* access modifiers changed from: private */
    public void resultDate() {
        KLog.e("licl", "日历选择日期后的查询日期->" + this.selectedYear + "/" + this.selectedMonth + "/" + this.selectedDay);
        if (this.callBack != null) {
            this.callBack.onResult(this.selectedYear, this.selectedMonth, this.selectedDay);
        }
    }

    public static void init(Context context2) {
        if (calendarShowHanlder == null) {
            calendarShowHanlder = new CalendarShowHanlder(context2);
        }
    }

    public void updateSelectDate(int year, int month, int day) {
        this.mCalendarView.update(year, month, day);
        DateUtil dateUtil = new DateUtil(year, month, day);
        this.selectedMonth = dateUtil.getMonth();
        this.selectedYear = dateUtil.getYear();
        updateCanTitle();
    }

    public static CalendarShowHanlder getCalendarShowHanlder() {
        if (calendarShowHanlder == null) {
            return null;
        }
        return calendarShowHanlder;
    }

    public void showCalendar(View view) {
        this.mCalendarView.setShowLeveTag(this.leveTag);
        this.popupWindow_calendar.showAsDropDown(view);
    }

    public void hiddenCalendar() {
        this.popupWindow_calendar.dismiss();
    }

    /* access modifiers changed from: private */
    public void updateCanTitle() {
        String month = this.months[this.selectedMonth - 1];
        this.mCalendarTitle.setText(month + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.selectedYear);
        KLog.i("---" + month + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.selectedYear);
    }

    public void destory() {
        KLog.e("  destory   xxx");
        this.popupWindow_calendar = null;
        calendarShowHanlder = null;
        this.leveTag = false;
    }
}
