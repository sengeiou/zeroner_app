package com.iwown.my_module.useractivity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.iwown.data_link.consts.UserConst;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.utility.CalendarUtility;
import com.iwown.my_module.widget.WangWheelView;
import com.iwown.my_module.widget.WangWheelView.OnWheelViewListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewProfileBirthdayActivity extends BaseActivity implements OnClickListener {
    public static final int BIRTHDAY_RESULT_CODE = 5;
    public static final String BIRTHDAY_VALUE = "birthday_value";
    private static final String TAG = "BirthdayActivity";
    WangWheelView dayPicker;
    String mBirthday;
    Button mBtnNext;
    int mDay;
    int mGender;
    float mHeight;
    int mMaxYear;
    int mMonth;
    float mWeight;
    int mYear;
    WangWheelView monthPicker;
    int status;
    WangWheelView yearPicker;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_newprofile_birthday);
        setTitleText(R.string.create_profile_title);
        setLeftBackTo();
        this.mBtnNext = (Button) findViewById(R.id.btn_next);
        this.mBtnNext.setOnClickListener(this);
        this.dayPicker = (WangWheelView) findViewById(R.id.day_picker);
        this.monthPicker = (WangWheelView) findViewById(R.id.month_picker);
        this.yearPicker = (WangWheelView) findViewById(R.id.year_picker);
        Intent intent = getIntent();
        if (intent != null) {
            this.status = intent.getIntExtra(UserConst.PROFILE_VIEW_STATUS, 1);
        } else {
            this.status = 1;
        }
        if (this.status == 1) {
            this.mGender = intent.getIntExtra(NewProfileGenderActivity.GENDER_VALUE, 0);
            this.mHeight = intent.getFloatExtra(NewProfileHeightActivity.HEIGHT_VALUE, 0.0f);
            this.mWeight = intent.getFloatExtra(NewProfileWeightActivity.WEIGHT_VALUE, 0.0f);
        } else {
            this.mBirthday = intent.getStringExtra(BIRTHDAY_VALUE);
            Log.i(TAG, String.format("birthday passed:%s", new Object[]{this.mBirthday}));
        }
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
        Log.i(TAG, String.format("year:%d,month:%d,day:%d", new Object[]{Integer.valueOf(this.mYear), Integer.valueOf(this.mMonth), Integer.valueOf(this.mDay)}));
        initView(this.mYear, this.mMonth, this.mDay);
    }

    public void onClick(View view) {
        if (view.getId() != R.id.btn_next) {
            return;
        }
        if (this.status == 1) {
            Intent intent = new Intent(this, ProfileCategoryActivity.class);
            intent.putExtra(NewProfileWeightActivity.WEIGHT_VALUE, this.mWeight);
            intent.putExtra(NewProfileHeightActivity.HEIGHT_VALUE, this.mHeight);
            intent.putExtra(NewProfileGenderActivity.GENDER_VALUE, this.mGender);
            intent.putExtra(BIRTHDAY_VALUE, String.format("%04d-%02d-%02d", new Object[]{Integer.valueOf(this.mYear), Integer.valueOf(this.mMonth), Integer.valueOf(this.mDay)}));
            Log.i(TAG, String.format("in birthday view, height:%f,weight:%f", new Object[]{Float.valueOf(this.mHeight), Float.valueOf(this.mWeight)}));
            intent.putExtra(UserConst.PROFILE_VIEW_STATUS, 1);
            intent.setFlags(67108864);
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent();
        intent2.putExtra(BIRTHDAY_VALUE, String.format("%04d-%02d-%02d", new Object[]{Integer.valueOf(this.mYear), Integer.valueOf(this.mMonth), Integer.valueOf(this.mDay)}));
        setResult(5, intent2);
        finish();
    }

    /* access modifiers changed from: 0000 */
    public void initView(int year, int month, int day) {
        this.monthPicker.setOffset(1);
        List<String> monthList = new ArrayList<>();
        monthList.add(getString(R.string.sport_module_month_jan));
        monthList.add(getString(R.string.sport_module_month_feb));
        monthList.add(getString(R.string.sport_module_month_mar));
        monthList.add(getString(R.string.sport_module_month_apr));
        monthList.add(getString(R.string.sport_module_month_may));
        monthList.add(getString(R.string.sport_module_month_june));
        monthList.add(getString(R.string.sport_module_month_july));
        monthList.add(getString(R.string.sport_module_month_aug));
        monthList.add(getString(R.string.sport_module_month_sept));
        monthList.add(getString(R.string.sport_module_month_oct));
        monthList.add(getString(R.string.sport_module_month_nov));
        monthList.add(getString(R.string.sport_module_month_dec));
        this.monthPicker.setItems(monthList);
        this.monthPicker.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                NewProfileBirthdayActivity.this.mMonth = selectedIndex;
                List<String> dayList = new ArrayList<>();
                int days = CalendarUtility.getDaysInMonth(NewProfileBirthdayActivity.this.mYear, NewProfileBirthdayActivity.this.mMonth);
                for (int i = 1; i <= days; i++) {
                    dayList.add(String.valueOf(i));
                }
                NewProfileBirthdayActivity.this.dayPicker.setItems(dayList);
                NewProfileBirthdayActivity.this.dayPicker.invalidate();
                if (NewProfileBirthdayActivity.this.mDay > days) {
                    NewProfileBirthdayActivity.this.dayPicker.setSeletion(days - 1);
                    NewProfileBirthdayActivity.this.mDay = days;
                    return;
                }
                NewProfileBirthdayActivity.this.dayPicker.setSeletion(NewProfileBirthdayActivity.this.mDay - 1);
            }
        });
        this.yearPicker.setOffset(1);
        List<String> yearList = new ArrayList<>();
        for (int i = this.mMaxYear - 100; i <= this.mMaxYear; i++) {
            yearList.add(String.valueOf(i));
        }
        this.yearPicker.setItems(yearList);
        this.yearPicker.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                NewProfileBirthdayActivity.this.mYear = (NewProfileBirthdayActivity.this.mMaxYear - 100) + (selectedIndex - 1);
                List<String> dayList = new ArrayList<>();
                int days = CalendarUtility.getDaysInMonth(NewProfileBirthdayActivity.this.mYear, NewProfileBirthdayActivity.this.mMonth);
                for (int i = 1; i <= days; i++) {
                    dayList.add(String.valueOf(i));
                }
                NewProfileBirthdayActivity.this.dayPicker.setItems(dayList);
                NewProfileBirthdayActivity.this.dayPicker.invalidate();
                if (NewProfileBirthdayActivity.this.mDay > days) {
                    NewProfileBirthdayActivity.this.dayPicker.setSeletion(days - 1);
                    NewProfileBirthdayActivity.this.mDay = days;
                    return;
                }
                NewProfileBirthdayActivity.this.dayPicker.setSeletion(NewProfileBirthdayActivity.this.mDay - 1);
            }
        });
        this.dayPicker.setOffset(1);
        List<String> dayList = new ArrayList<>();
        int days = CalendarUtility.getDaysInMonth(year, month);
        for (int i2 = 1; i2 <= days; i2++) {
            dayList.add(String.valueOf(i2));
        }
        this.dayPicker.setItems(dayList);
        this.dayPicker.setOnWheelViewListener(new OnWheelViewListener() {
            public void onSelected(int selectedIndex, String item) {
                NewProfileBirthdayActivity.this.mDay = selectedIndex;
            }
        });
        setDatePickerByDate(this.mMaxYear - 100, year, month, day);
    }

    /* access modifiers changed from: 0000 */
    public void setDatePickerByDate(int startYear, int year, int month, int day) {
        Log.i(TAG, String.format("set month:%d", new Object[]{Integer.valueOf(month)}));
        this.yearPicker.setSeletion(year - startYear);
        this.monthPicker.setSeletion(month - 1);
        this.dayPicker.setSeletion(day - 1);
    }
}
