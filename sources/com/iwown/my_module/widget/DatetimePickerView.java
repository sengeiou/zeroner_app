package com.iwown.my_module.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.iwown.my_module.R;
import com.iwown.my_module.utility.CalendarUtility;
import com.iwown.my_module.widget.WangWheelView.OnWheelViewListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatetimePickerView extends LinearLayout {
    private static final String DATE_FMT = "yyyy-MM-dd";
    private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final String TAG = "DatetimePickerView";
    private String mBirthday;
    /* access modifiers changed from: private */
    public int mDay;
    /* access modifiers changed from: private */
    public WangWheelView mDayView;
    List<String> mDays = new ArrayList();
    /* access modifiers changed from: private */
    public int mMaxYear;
    /* access modifiers changed from: private */
    public int mMonth;
    private WangWheelView mMonthView;
    List<String> mMonths = new ArrayList();
    /* access modifiers changed from: private */
    public int mYear;
    private WangWheelView mYearView;
    List<String> mYears = new ArrayList();

    public String getBirthday() {
        this.mBirthday = String.format("%04d-%02d-%02d", new Object[]{Integer.valueOf(this.mYear), Integer.valueOf(this.mMonth), Integer.valueOf(this.mDay)});
        return this.mBirthday;
    }

    public void setBirthday(String birthday) {
        this.mBirthday = birthday;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar birth = Calendar.getInstance();
        try {
            birth.setTime(df.parse(this.mBirthday));
            this.mYear = birth.get(1);
            this.mMonth = birth.get(2) + 1;
            this.mDay = birth.get(5);
        } catch (Exception e) {
        }
        setDatePickerByDate(this.mMaxYear - 100, this.mYear, this.mMonth, this.mDay);
    }

    public DatetimePickerView(Context context) {
        super(context);
        init();
    }

    public DatetimePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DatetimePickerView(Context context, String birthday) {
        super(context);
        this.mBirthday = birthday;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.my_module_view_datetime_picker, this);
        this.mDayView = (WangWheelView) findViewById(R.id.picker_day);
        this.mMonthView = (WangWheelView) findViewById(R.id.picker_month);
        this.mYearView = (WangWheelView) findViewById(R.id.picker_year);
        Calendar birth = Calendar.getInstance();
        this.mMaxYear = birth.get(1);
        if (!TextUtils.isEmpty(this.mBirthday)) {
            try {
                birth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(this.mBirthday));
            } catch (Exception e) {
            }
        } else {
            birth.add(1, -25);
        }
        this.mYear = birth.get(1);
        this.mMonth = birth.get(2) + 1;
        this.mDay = birth.get(5);
        initView(this.mYear, this.mMonth, this.mDay);
        setDatePickerByDate(this.mMaxYear - 100, this.mYear, this.mMonth, this.mDay);
    }

    private void initView(int year, int month, int day) {
        this.mMonthView.setOffset(1);
        List<String> monthList = new ArrayList<>();
        monthList.add(getContext().getString(R.string.sport_module_month_jan));
        monthList.add(getContext().getString(R.string.sport_module_month_feb));
        monthList.add(getContext().getString(R.string.sport_module_month_mar));
        monthList.add(getContext().getString(R.string.sport_module_month_apr));
        monthList.add(getContext().getString(R.string.sport_module_month_may));
        monthList.add(getContext().getString(R.string.sport_module_month_june));
        monthList.add(getContext().getString(R.string.sport_module_month_july));
        monthList.add(getContext().getString(R.string.sport_module_month_aug));
        monthList.add(getContext().getString(R.string.sport_module_month_sept));
        monthList.add(getContext().getString(R.string.sport_module_month_oct));
        monthList.add(getContext().getString(R.string.sport_module_month_nov));
        monthList.add(getContext().getString(R.string.sport_module_month_dec));
        this.mMonthView.setItems(monthList);
        this.mMonthView.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                DatetimePickerView.this.mMonth = selectedIndex;
                List<String> dayList = new ArrayList<>();
                int days = CalendarUtility.getDaysInMonth(DatetimePickerView.this.mYear, DatetimePickerView.this.mMonth);
                for (int i = 1; i <= days; i++) {
                    dayList.add(String.valueOf(i));
                }
                DatetimePickerView.this.mDayView.setItems(dayList);
                DatetimePickerView.this.mDayView.invalidate();
                if (DatetimePickerView.this.mDay > days) {
                    DatetimePickerView.this.mDayView.setSeletion(days - 1);
                    DatetimePickerView.this.mDay = days;
                    return;
                }
                DatetimePickerView.this.mDayView.setSeletion(DatetimePickerView.this.mDay - 1);
            }
        });
        this.mYearView.setOffset(1);
        List<String> yearList = new ArrayList<>();
        for (int i = this.mMaxYear - 100; i <= this.mMaxYear; i++) {
            yearList.add(String.valueOf(i));
        }
        this.mYearView.setItems(yearList);
        this.mYearView.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                DatetimePickerView.this.mYear = (DatetimePickerView.this.mMaxYear - 100) + (selectedIndex - 1);
                List<String> dayList = new ArrayList<>();
                int days = CalendarUtility.getDaysInMonth(DatetimePickerView.this.mYear, DatetimePickerView.this.mMonth);
                for (int i = 1; i <= days; i++) {
                    dayList.add(String.valueOf(i));
                }
                DatetimePickerView.this.mDayView.setItems(dayList);
                DatetimePickerView.this.mDayView.invalidate();
                if (DatetimePickerView.this.mDay > days) {
                    DatetimePickerView.this.mDayView.setSeletion(days - 1);
                    DatetimePickerView.this.mDay = days;
                    return;
                }
                DatetimePickerView.this.mDayView.setSeletion(DatetimePickerView.this.mDay - 1);
            }
        });
        this.mDayView.setOffset(1);
        List<String> dayList = new ArrayList<>();
        int days = CalendarUtility.getDaysInMonth(year, month);
        for (int i2 = 1; i2 <= days; i2++) {
            dayList.add(String.valueOf(i2));
        }
        this.mDayView.setItems(dayList);
        this.mDayView.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                DatetimePickerView.this.mDay = selectedIndex;
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void setDatePickerByDate(int startYear, int year, int month, int day) {
        this.mYearView.setSeletion(year - startYear);
        this.mMonthView.setSeletion(month - 1);
        this.mDayView.setSeletion(day - 1);
    }
}
