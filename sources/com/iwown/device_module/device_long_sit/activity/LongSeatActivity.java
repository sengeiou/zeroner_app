package com.iwown.device_module.device_long_sit.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.SwitchItme;
import com.iwown.device_module.common.view.SwitchItme.OnSwitchChangedListener;
import com.iwown.device_module.common.view.TimeIntervalView;
import com.iwown.device_module.device_alarm_schedule.activity.week.WeekDaySelectActivity;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import com.iwown.device_module.device_long_sit.bean.LongSitStatue;
import com.socks.library.KLog;

public class LongSeatActivity extends DeviceModuleBaseActivity implements View {
    /* access modifiers changed from: private */
    public static int WEEK_REPEAT_REQUEST = 58;
    private byte bb = -1;
    SwitchItme mLunchBreak;
    SwitchItme mRemindSwitch;
    ItemView mRepeat;
    private String[] mTimeArr;
    private String[] mTimeArr_1;
    TimeIntervalView mTimeIntervalPicker;
    ItemView mTimePeriod;
    /* access modifiers changed from: private */
    public LongSeatPresenter presenter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_sendentary_acitvity);
        this.mTimeArr = getResources().getStringArray(R.array.device_module_time);
        this.mTimeArr_1 = getResources().getStringArray(R.array.device_module_time_1_24);
        initView();
        initEvent();
    }

    private void initEvent() {
        this.mRemindSwitch.setOnSwitchChangedListener(new OnSwitchChangedListener() {
            public void onSwitchChanged(boolean isOn) {
                LongSitStatue statue = LongSeatActivity.this.presenter.longSeatStatue();
                statue.setLongSeat(isOn);
                LongSeatActivity.this.presenter.saveLongSeatStatue(statue);
                if (!isOn) {
                    LongSeatActivity.this.mRepeat.setClickable(false);
                    LongSeatActivity.this.mRepeat.setTitleColor(LongSeatActivity.this.getResources().getColor(R.color.device_module_long_seat_text_2));
                    LongSeatActivity.this.mTimePeriod.setTitleColor(LongSeatActivity.this.getResources().getColor(R.color.device_module_long_seat_text_2));
                    LongSeatActivity.this.mTimePeriod.setMsgColor(LongSeatActivity.this.getResources().getColor(R.color.device_module_long_seat_text_2));
                    LongSeatActivity.this.mLunchBreak.setVisibility(8);
                    LongSeatActivity.this.mTimeIntervalPicker.setVisibility(8);
                } else {
                    LongSeatActivity.this.mRepeat.setClickable(true);
                    LongSeatActivity.this.mRepeat.setTitleColor(LongSeatActivity.this.getResources().getColor(R.color.device_module_white));
                    LongSeatActivity.this.mTimePeriod.setTitleColor(LongSeatActivity.this.getResources().getColor(R.color.device_module_white));
                    LongSeatActivity.this.mTimePeriod.setMsgColor(LongSeatActivity.this.getResources().getColor(R.color.device_module_long_seat_text_1));
                    LongSeatActivity.this.mLunchBreak.setVisibility(0);
                }
                LongSeatActivity.this.setRepeat();
            }
        });
        this.mLunchBreak.setOnSwitchChangedListener(new OnSwitchChangedListener() {
            public void onSwitchChanged(boolean isOn) {
                LongSitStatue statue = LongSeatActivity.this.presenter.longSeatStatue();
                statue.setDoNotDisturb(isOn);
                LongSeatActivity.this.presenter.saveLongSeatStatue(statue);
            }
        });
        this.mTimePeriod.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LongSitStatue statue = LongSeatActivity.this.presenter.longSeatStatue();
                if (statue.isLongSeat()) {
                    int startIndex = LongSeatActivity.this.mTimeIntervalPicker.getStartTimeCurrPosition();
                    int endIndex = LongSeatActivity.this.mTimeIntervalPicker.getEndTimeCurrentPosition();
                    if (LongSeatActivity.this.mTimeIntervalPicker.getVisibility() == 0) {
                        LongSeatActivity.this.mTimeIntervalPicker.setVisibility(8);
                        KLog.i(startIndex + "===============" + endIndex);
                        if (startIndex <= endIndex || endIndex == 0) {
                            statue.setStartHour(startIndex);
                            statue.setEndHour(endIndex);
                            LongSeatActivity.this.presenter.saveLongSeatStatue(statue);
                        } else {
                            Toast.makeText(LongSeatActivity.this, LongSeatActivity.this.getString(R.string.device_module_invalid_time), 0).show();
                            return;
                        }
                    } else {
                        LongSeatActivity.this.mTimeIntervalPicker.setVisibility(0);
                        LongSeatActivity.this.mTimeIntervalPicker.setStartCurrPosition(startIndex);
                        LongSeatActivity.this.mTimeIntervalPicker.setEndCurrPosition(endIndex);
                    }
                    LongSeatActivity.this.setTimePeroid();
                }
            }
        });
        this.mRepeat.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LongSitStatue statue = LongSeatActivity.this.presenter.longSeatStatue();
                if (statue.isLongSeat()) {
                    Intent intent = new Intent(LongSeatActivity.this, WeekDaySelectActivity.class);
                    KLog.i("===getRepeat====" + statue.getRepeat());
                    intent.putExtra("day_of_week", statue.getRepeat());
                    intent.putExtra("what_activity", 2);
                    LongSeatActivity.this.startActivityForResult(intent, LongSeatActivity.WEEK_REPEAT_REQUEST);
                }
            }
        });
    }

    private void initView() {
        this.presenter = new LongSeatPresenter(this);
        this.mTimePeriod = (ItemView) findViewById(R.id.time_period);
        this.mRemindSwitch = (SwitchItme) findViewById(R.id.remind_switch);
        this.mTimeIntervalPicker = (TimeIntervalView) findViewById(R.id.time_interval_picker);
        this.mRepeat = (ItemView) findViewById(R.id.repeat);
        this.mLunchBreak = (SwitchItme) findViewById(R.id.lunch_break);
        setLeftBackTo();
        setTitleText(R.string.device_module_sedentary_reminder);
        this.mTimePeriod.setRightImagVisible(true);
        this.mTimePeriod.setTitleMsgRightMargin(WindowsUtil.dip2px(this, 16.0f));
        if (BluetoothOperation.isZg()) {
            this.mRepeat.setVisibility(8);
        }
        LongSitStatue statue = this.presenter.longSeatStatue();
        if (this.bb == Byte.MIN_VALUE) {
            this.bb = -1;
            statue.setRepeat(this.bb);
            statue.setLongSeat(false);
            this.presenter.saveLongSeatStatue(statue);
        }
        if (!statue.isLongSeat()) {
            this.mRepeat.setClickable(false);
            this.mRepeat.setTitleColor(getResources().getColor(R.color.device_module_long_seat_text_2));
            this.mTimePeriod.setTitleColor(getResources().getColor(R.color.device_module_long_seat_text_2));
            this.mLunchBreak.setVisibility(8);
            this.mTimePeriod.setMsgColor(getResources().getColor(R.color.device_module_long_seat_text_2));
        } else {
            this.mRepeat.setClickable(true);
            this.mRepeat.setTitleColor(getResources().getColor(R.color.device_module_white));
            this.mTimePeriod.setTitleColor(getResources().getColor(R.color.device_module_white));
            this.mLunchBreak.setVisibility(0);
            this.mTimePeriod.setMsgColor(getResources().getColor(R.color.device_module_long_seat_text_1));
        }
        setItemRightMsg();
    }

    private void setItemRightMsg() {
        setRemindSwitch();
        setTimePeroid();
        setRepeat();
        setLunchBreak();
        setTimeIntervalPicker();
    }

    public void setRemindSwitch() {
        this.mRemindSwitch.setOn(this.presenter.longSeatStatue().isLongSeat());
    }

    public void setTimePeroid() {
        LongSitStatue statue = this.presenter.longSeatStatue();
        this.mTimePeriod.setMessageText(this.mTimeArr[statue.getStartHour()] + " - " + this.mTimeArr_1[statue.getEndHour()]);
        KLog.e(TAG, statue.getStartHour() + "/" + statue.getEndHour());
        if (statue.isLongSeat()) {
            this.mTimePeriod.setMsgColor(Color.parseColor("#0476E6"));
        } else {
            this.mTimePeriod.setMsgColor(Color.parseColor("#B4B4B4"));
        }
    }

    public void setRepeat() {
        if (this.mRepeat.getVisibility() == 0) {
            this.mRepeat.setMessageText(this.presenter.getWeekRepeatStr(this.presenter.longSeatStatue().getRepeat()));
        }
    }

    public void setLunchBreak() {
        this.mLunchBreak.setOn(this.presenter.longSeatStatue().isDoNotDisturb());
    }

    public void setTimeIntervalPicker() {
        LongSitStatue statue = this.presenter.longSeatStatue();
        this.mTimeIntervalPicker.setStartCurrPosition(statue.getStartHour());
        this.mTimeIntervalPicker.setEndCurrPosition(statue.getEndHour());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        writeToDevice();
    }

    private void writeToDevice() {
        LongSitStatue statue = this.presenter.longSeatStatue();
        int startHour = statue.getStartHour();
        int endHour = statue.getEndHour();
        this.presenter.writeSedentaryIfLunchBreak(startHour, endHour, this.presenter.judgeWhatState(startHour, endHour), statue.isDoNotDisturb(), statue.getRepeat(), statue.isLongSeat());
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WEEK_REPEAT_REQUEST && resultCode == -1 && data != null) {
            this.bb = data.getByteExtra("week_repeat", 0);
            LongSitStatue statue = this.presenter.longSeatStatue();
            statue.setRepeat(this.bb);
            this.presenter.saveLongSeatStatue(statue);
            setRepeat();
            KLog.e(TAG, "收到久坐重复设置-->" + ByteUtil.byteToBitArray(this.bb));
            int startHour = statue.getStartHour();
            int endHour = statue.getEndHour();
            this.presenter.writeSedentaryIfLunchBreak(startHour, endHour, this.presenter.judgeWhatState(startHour, endHour), statue.isDoNotDisturb(), this.bb, statue.isLongSeat());
        }
    }

    public void setPresenter(Presenter presenter2) {
    }
}
