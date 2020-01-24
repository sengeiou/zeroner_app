package com.iwown.device_module.device_setting.heart;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.SwitchItme;
import com.iwown.device_module.common.view.SwitchItme.OnSwitchChangedListener;
import com.iwown.device_module.common.view.TimeIntervalView;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.heart.bean.AutoHeartStatue;
import com.socks.library.KLog;

public class AutoHeartActivity extends DeviceModuleBaseActivity implements View {
    /* access modifiers changed from: private */
    public SwitchItme autoHeartHr;
    private boolean autoHeartTime;
    /* access modifiers changed from: private */
    public LinearLayout layoutAutoHeart;
    private String[] mTimeArr;
    private String[] mTimeArr2;
    /* access modifiers changed from: private */
    public HeartPresenter presenter;
    /* access modifiers changed from: private */
    public TimeIntervalView timeIntervalPickerForHeart;
    private ItemView timePeriod;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_auto_heart);
        initView();
        initData();
    }

    private void initData() {
        this.autoHeartHr.setOn(this.presenter.autoHeartStatue().isHeart_switch());
        if (this.presenter.autoHeartStatue().isHeart_switch()) {
            this.autoHeartHr.setDivideLineVisible(true);
        } else {
            this.autoHeartHr.setDivideLineVisible(false);
        }
    }

    private void initView() {
        this.presenter = new HeartPresenter(this);
        this.autoHeartTime = DeviceSettingsBiz.getInstance().supportSomeSetting(26);
        this.mTimeArr = getResources().getStringArray(R.array.device_module_time);
        this.mTimeArr2 = getResources().getStringArray(R.array.device_module_time_1_24);
        setTitleText(R.string.device_module_setting_auto_heart);
        setLeftBackTo();
        this.autoHeartHr = (SwitchItme) findViewById(R.id.auto_heart_hr);
        this.timePeriod = (ItemView) findViewById(R.id.time_period);
        this.timePeriod.setMsgColor(getResources().getColor(R.color.device_module_white));
        this.timeIntervalPickerForHeart = (TimeIntervalView) findViewById(R.id.time_interval_picker_for_auto_heart);
        this.layoutAutoHeart = (LinearLayout) findViewById(R.id.layout_auto_heart);
        if (this.autoHeartTime) {
            this.timePeriod.setVisibility(0);
        } else {
            this.timePeriod.setVisibility(8);
        }
        this.autoHeartHr.setOnSwitchChangedListener(new OnSwitchChangedListener() {
            public void onSwitchChanged(boolean isOn) {
                AutoHeartStatue statue = AutoHeartActivity.this.presenter.autoHeartStatue();
                statue.setHeart_switch(isOn);
                AutoHeartActivity.this.presenter.saveAutoHeartStatue(statue);
                if (isOn) {
                    AutoHeartActivity.this.layoutAutoHeart.setVisibility(0);
                    AutoHeartActivity.this.autoHeartHr.setDivideLineVisible(true);
                } else {
                    AutoHeartActivity.this.layoutAutoHeart.setVisibility(8);
                    AutoHeartActivity.this.autoHeartHr.setDivideLineVisible(false);
                }
                DeviceUtils.writeCommandToDevice(11);
            }
        });
        this.timePeriod.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AutoHeartStatue statue = AutoHeartActivity.this.presenter.autoHeartStatue();
                if (statue.isHeart_switch()) {
                    int startIndex = AutoHeartActivity.this.timeIntervalPickerForHeart.getStartTimeCurrPosition();
                    int endIndex = AutoHeartActivity.this.timeIntervalPickerForHeart.getEndTimeCurrentPosition();
                    KLog.i("startIndex" + startIndex + "===" + endIndex);
                    if (AutoHeartActivity.this.timeIntervalPickerForHeart.getVisibility() == 0) {
                        AutoHeartActivity.this.timeIntervalPickerForHeart.setVisibility(8);
                        if (startIndex <= endIndex || endIndex == 0) {
                            statue.setHeart_startTime(startIndex);
                            statue.setHeart_endTime(endIndex);
                            AutoHeartActivity.this.presenter.saveAutoHeartStatue(statue);
                            AutoHeartActivity.this.autoHeartHr.setDivideLineVisible(true);
                        } else {
                            Toast.makeText(AutoHeartActivity.this, AutoHeartActivity.this.getString(R.string.device_module_invalid_time), 0).show();
                            return;
                        }
                    } else {
                        AutoHeartActivity.this.timeIntervalPickerForHeart.setVisibility(0);
                        AutoHeartActivity.this.timeIntervalPickerForHeart.setStartCurrPosition(startIndex);
                        AutoHeartActivity.this.timeIntervalPickerForHeart.setEndCurrPosition(endIndex);
                        AutoHeartActivity.this.autoHeartHr.setDivideLineVisible(false);
                    }
                    AutoHeartActivity.this.setTimePeroid();
                    DeviceUtils.writeCommandToDevice(11);
                }
            }
        });
        setTimePeroid();
        setHeartGuidePicker();
        DeviceUtils.writeCommandToDevice(11);
    }

    /* access modifiers changed from: private */
    public void setTimePeroid() {
        AutoHeartStatue statue = this.presenter.autoHeartStatue();
        this.timePeriod.setMessageText(this.mTimeArr[statue.getHeart_startTime()] + " - " + this.mTimeArr2[statue.getHeart_endTime()]);
    }

    public void setHeartGuidePicker() {
        this.timeIntervalPickerForHeart.setStartCurrPosition(this.presenter.autoHeartStatue().getHeart_startTime());
        this.timeIntervalPickerForHeart.setEndCurrPosition(this.presenter.autoHeartStatue().getHeart_endTime());
    }
}
