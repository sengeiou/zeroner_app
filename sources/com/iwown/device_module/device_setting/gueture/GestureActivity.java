package com.iwown.device_module.device_setting.gueture;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.SwitchItme;
import com.iwown.device_module.common.view.SwitchItme.OnSwitchChangedListener;
import com.iwown.device_module.common.view.TimeIntervalView;
import com.iwown.device_module.device_setting.configure.DeviceSettingLocal;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.fragment.SettingContract.Presenter;
import com.iwown.device_module.device_setting.fragment.SettingContract.View;
import com.iwown.device_module.device_setting.fragment.SettingPresenter;
import com.socks.library.KLog;

public class GestureActivity extends DeviceModuleBaseActivity implements View {
    public static final int Disturb = 1;
    public static final String TYPE = "TYPE";
    public static final String TimeSelect = "timeSelect";
    /* access modifiers changed from: private */
    public SwitchItme autoGesture;
    /* access modifiers changed from: private */
    public LinearLayout layoutAutoHeart;
    private String[] mTimeArr;
    private String[] mTimeArr2;
    /* access modifiers changed from: private */
    public SettingPresenter presenter;
    /* access modifiers changed from: private */
    public int switchType;
    /* access modifiers changed from: private */
    public TimeIntervalView timeIntervalPickerForGesture;
    private ItemView timePeriod;
    /* access modifiers changed from: private */
    public boolean timeSwitch = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_gesture);
        initView();
        initData();
    }

    private void initData() {
        KLog.i("------------" + this.presenter.localDeviceSetting().isPalmingGesture());
        if (this.switchType == 1) {
            this.autoGesture.setOn(this.presenter.localDeviceSetting().isNoDisturb());
            if (this.presenter.localDeviceSetting().isNoDisturb()) {
                this.autoGesture.setDivideLineVisible(true);
            } else {
                this.autoGesture.setDivideLineVisible(false);
            }
        } else {
            this.autoGesture.setOn(this.presenter.localDeviceSetting().isPalmingGesture());
            if (this.presenter.localDeviceSetting().isPalmingGesture()) {
                this.autoGesture.setDivideLineVisible(true);
            } else {
                this.autoGesture.setDivideLineVisible(false);
            }
        }
    }

    private void initView() {
        this.presenter = new SettingPresenter(this);
        this.timeSwitch = getIntent().getBooleanExtra(TimeSelect, false);
        this.switchType = getIntent().getIntExtra("TYPE", 0);
        this.mTimeArr = getResources().getStringArray(R.array.device_module_time);
        this.mTimeArr2 = getResources().getStringArray(R.array.device_module_time_1_24);
        if (this.switchType == 1) {
            setTitleText(R.string.device_module_setting_no_disturb);
        } else {
            setTitleText(R.string.device_module_setting_palming_gesture);
        }
        setLeftBackTo();
        this.autoGesture = (SwitchItme) findViewById(R.id.auto_gesture_switch);
        this.timePeriod = (ItemView) findViewById(R.id.time_period);
        this.timePeriod.setMsgColor(getResources().getColor(R.color.device_module_white));
        this.timeIntervalPickerForGesture = (TimeIntervalView) findViewById(R.id.time_interval_picker_for_auto_gesture);
        this.layoutAutoHeart = (LinearLayout) findViewById(R.id.layout_auto_heart);
        if (this.switchType == 1) {
            this.autoGesture.setTitle(R.string.device_module_setting_no_disturb);
            this.autoGesture.setContent(R.string.device_module_setting_no_disturb);
        }
        this.autoGesture.setOnSwitchChangedListener(new OnSwitchChangedListener() {
            public void onSwitchChanged(boolean isOn) {
                if (GestureActivity.this.switchType == 1) {
                    DeviceSettingLocal statue = GestureActivity.this.presenter.localDeviceSetting();
                    statue.setNoDisturb(isOn);
                    GestureActivity.this.presenter.saveLocalDeviceSetting(statue);
                    if (isOn) {
                        if (GestureActivity.this.timeSwitch) {
                            GestureActivity.this.layoutAutoHeart.setVisibility(0);
                        }
                        GestureActivity.this.autoGesture.setDivideLineVisible(true);
                    } else {
                        GestureActivity.this.layoutAutoHeart.setVisibility(8);
                        GestureActivity.this.autoGesture.setDivideLineVisible(false);
                    }
                    DeviceUtils.writeCommandToDevice(39);
                    return;
                }
                DeviceSettingLocal statue2 = GestureActivity.this.presenter.localDeviceSetting();
                statue2.setPalmingGesture(isOn);
                GestureActivity.this.presenter.saveLocalDeviceSetting(statue2);
                if (isOn) {
                    if (GestureActivity.this.timeSwitch) {
                        GestureActivity.this.layoutAutoHeart.setVisibility(0);
                    }
                    GestureActivity.this.autoGesture.setDivideLineVisible(true);
                } else {
                    GestureActivity.this.layoutAutoHeart.setVisibility(8);
                    GestureActivity.this.autoGesture.setDivideLineVisible(false);
                }
                DeviceUtils.writeCommandToDevice(1);
            }
        });
        this.timePeriod.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View v) {
                DeviceSettingLocal statue = GestureActivity.this.presenter.localDeviceSetting();
                int startIndex = GestureActivity.this.timeIntervalPickerForGesture.getStartTimeCurrPosition();
                int endIndex = GestureActivity.this.timeIntervalPickerForGesture.getEndTimeCurrentPosition();
                KLog.i("startIndex" + startIndex + "===" + endIndex);
                if (GestureActivity.this.timeIntervalPickerForGesture.getVisibility() == 0) {
                    GestureActivity.this.timeIntervalPickerForGesture.setVisibility(8);
                    if (!BluetoothOperation.isZg() || startIndex <= endIndex || endIndex == 0) {
                        if (GestureActivity.this.switchType == 1) {
                            statue.setStartNoDisturbTime(startIndex);
                            statue.setEndNoDisturbTime(endIndex);
                        } else {
                            statue.setPalmingGestureStart(startIndex);
                            statue.setPalmingGestureEnd(endIndex);
                        }
                        GestureActivity.this.presenter.saveLocalDeviceSetting(statue);
                        GestureActivity.this.autoGesture.setDivideLineVisible(true);
                    } else {
                        Toast.makeText(GestureActivity.this, GestureActivity.this.getString(R.string.device_module_invalid_time), 0).show();
                        return;
                    }
                } else {
                    GestureActivity.this.timeIntervalPickerForGesture.setVisibility(0);
                    GestureActivity.this.timeIntervalPickerForGesture.setStartCurrPosition(startIndex);
                    GestureActivity.this.timeIntervalPickerForGesture.setEndCurrPosition(endIndex);
                    GestureActivity.this.autoGesture.setDivideLineVisible(false);
                }
                GestureActivity.this.setTimePeroid();
                if (GestureActivity.this.switchType == 1) {
                    DeviceUtils.writeCommandToDevice(39);
                } else {
                    DeviceUtils.writeCommandToDevice(1);
                }
            }
        });
        setTimePeroid();
        setGesturePicker();
    }

    /* access modifiers changed from: private */
    public void setTimePeroid() {
        DeviceSettingLocal statue = this.presenter.localDeviceSetting();
        if (this.switchType == 1) {
            this.timePeriod.setMessageText(this.mTimeArr[statue.getStartNoDisturbTime()] + " - " + this.mTimeArr2[statue.getEndNoDisturbTime()]);
        } else {
            this.timePeriod.setMessageText(this.mTimeArr[statue.getPalmingGestureStart()] + " - " + this.mTimeArr2[statue.getPalmingGestureEnd()]);
        }
    }

    public void setGesturePicker() {
        if (this.switchType == 1) {
            this.timeIntervalPickerForGesture.setStartCurrPosition(this.presenter.localDeviceSetting().getStartNoDisturbTime());
            this.timeIntervalPickerForGesture.setEndCurrPosition(this.presenter.localDeviceSetting().getEndNoDisturbTime());
            return;
        }
        this.timeIntervalPickerForGesture.setStartCurrPosition(this.presenter.localDeviceSetting().getPalmingGestureStart());
        this.timeIntervalPickerForGesture.setEndCurrPosition(this.presenter.localDeviceSetting().getPalmingGestureEnd());
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
