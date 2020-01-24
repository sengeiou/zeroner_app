package com.iwown.device_module.device_setting.backlight;

import android.os.Bundle;
import android.view.View.OnClickListener;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.TimeIntervalView;
import com.iwown.device_module.device_setting.configure.DeviceSettingLocal;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.fragment.SettingContract.Presenter;
import com.iwown.device_module.device_setting.fragment.SettingContract.View;
import com.iwown.device_module.device_setting.fragment.SettingPresenter;
import com.socks.library.KLog;

public class BackLightActivity extends DeviceModuleBaseActivity implements View {
    private String[] mTimeArr;
    private String[] mTimeArr_1;
    /* access modifiers changed from: private */
    public SettingPresenter presenter;
    /* access modifiers changed from: private */
    public TimeIntervalView timeIntervalPickerForBackLight;
    private ItemView timePeriod;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_back_light);
        initView();
    }

    private void initView() {
        this.presenter = new SettingPresenter(this);
        this.mTimeArr = getResources().getStringArray(R.array.device_module_time);
        this.mTimeArr_1 = getResources().getStringArray(R.array.device_module_time_1_24);
        setTitleText(R.string.device_module_setting_back_light_time);
        setLeftBackTo();
        this.timePeriod = (ItemView) findViewById(R.id.time_period);
        this.timePeriod.setMsgColor(getResources().getColor(R.color.device_module_white));
        this.timeIntervalPickerForBackLight = (TimeIntervalView) findViewById(R.id.time_interval_picker_for_back_light);
        this.timePeriod.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View v) {
                DeviceSettingLocal statue = BackLightActivity.this.presenter.localDeviceSetting();
                int startIndex = BackLightActivity.this.timeIntervalPickerForBackLight.getStartTimeCurrPosition();
                int endIndex = BackLightActivity.this.timeIntervalPickerForBackLight.getEndTimeCurrentPosition();
                KLog.i("startIndex" + startIndex + "===" + endIndex);
                if (BackLightActivity.this.timeIntervalPickerForBackLight.getVisibility() == 0) {
                    BackLightActivity.this.timeIntervalPickerForBackLight.setVisibility(8);
                    statue.setBackLightStartTime(startIndex);
                    statue.setBackLightEndTime(endIndex);
                    BackLightActivity.this.presenter.saveLocalDeviceSetting(statue);
                } else {
                    BackLightActivity.this.timeIntervalPickerForBackLight.setVisibility(0);
                    BackLightActivity.this.timeIntervalPickerForBackLight.setStartCurrPosition(startIndex);
                    BackLightActivity.this.timeIntervalPickerForBackLight.setEndCurrPosition(endIndex);
                }
                BackLightActivity.this.setTimePeroid();
                DeviceUtils.writeCommandToDevice(5);
            }
        });
        setTimePeroid();
        setBackLightPicker();
    }

    /* access modifiers changed from: private */
    public void setTimePeroid() {
        DeviceSettingLocal statue = this.presenter.localDeviceSetting();
        KLog.i("DeviceSettingLocal" + JsonUtils.toJson(statue));
        this.timePeriod.setMessageText(this.mTimeArr[statue.getBackLightStartTime()] + " - " + this.mTimeArr_1[statue.getBackLightEndTime()]);
    }

    public void setBackLightPicker() {
        this.timeIntervalPickerForBackLight.setStartCurrPosition(this.presenter.localDeviceSetting().getBackLightStartTime());
        this.timeIntervalPickerForBackLight.setEndCurrPosition(this.presenter.localDeviceSetting().getBackLightEndTime());
    }

    public void connectStatue(boolean statue) {
    }

    public void updateConfigUi(String action) {
    }

    public void upDateFirmwareUi(String content, int cancelSwitch, int errorCode) {
    }

    public void connectingView(int type) {
    }

    public void updateFirmwareUpgradeSuccess() {
    }

    public void keyDownDismissDialog() {
    }

    public void setPresenter(Presenter presenter2) {
    }
}
