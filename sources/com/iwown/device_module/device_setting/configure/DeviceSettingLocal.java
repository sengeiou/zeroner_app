package com.iwown.device_module.device_setting.configure;

import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;

public class DeviceSettingLocal {
    private boolean autoRecognitionMotion = true;
    private int backLightEndTime = 6;
    private int backLightStartTime = 18;
    private int[] blood;
    private boolean callEnable = true;
    private int callEnd = 0;
    private int callStart = 0;
    private boolean dateFormat;
    private boolean double_touch_switch;
    private int endNoDisturbTime;
    private boolean exception;
    private boolean is24AfSwitch;
    private boolean isNoDisturb;
    private int language;
    private boolean led;
    private boolean msgEnable = true;
    private int msgEnd = 0;
    private int msgStart = 0;
    private boolean palmingGesture = true;
    private int palmingGestureEnd;
    private int palmingGestureStart;
    private boolean screenColor = true;
    private int startNoDisturbTime;
    private boolean supportHeart;
    private boolean timeFormat;
    private boolean unit;
    private boolean wear_recognize_switch;
    private boolean wearingManager;
    private boolean weatherFormat;
    private String welcome_text = "there";

    public boolean isSupportHeart() {
        return this.supportHeart;
    }

    public void setSupportHeart(boolean supportHeart2) {
        this.supportHeart = supportHeart2;
    }

    public String getWelcome_text() {
        return this.welcome_text;
    }

    public void setWelcome_text(String welcome_text2) {
        this.welcome_text = welcome_text2;
    }

    public boolean isCallEnable() {
        return this.callEnable;
    }

    public void setCallEnable(boolean callEnable2) {
        this.callEnable = callEnable2;
    }

    public int getCallStart() {
        return this.callStart;
    }

    public void setCallStart(int callStart2) {
        this.callStart = callStart2;
    }

    public int getCallEnd() {
        return this.callEnd;
    }

    public void setCallEnd(int callEnd2) {
        this.callEnd = callEnd2;
    }

    public boolean isMsgEnable() {
        return this.msgEnable;
    }

    public void setMsgEnable(boolean msgEnable2) {
        this.msgEnable = msgEnable2;
    }

    public int getMsgStart() {
        return this.msgStart;
    }

    public void setMsgStart(int msgStart2) {
        this.msgStart = msgStart2;
    }

    public int getMsgEnd() {
        return this.msgEnd;
    }

    public void setMsgEnd(int msgEnd2) {
        this.msgEnd = msgEnd2;
    }

    public boolean isUnit() {
        return this.unit;
    }

    public void setUnit(boolean unit2) {
        this.unit = unit2;
    }

    public boolean isLed() {
        return this.led;
    }

    public void setLed(boolean led2) {
        this.led = led2;
    }

    public int getLanguage() {
        if (!AppConfigUtil.isHealthy() || PrefUtil.getBoolean(ContextUtil.app, UserAction.HEALTHY_LANGUAGE) || BluetoothOperation.isMtk()) {
            return this.language;
        }
        return 1;
    }

    public void setLanguage(int language2) {
        this.language = language2;
    }

    public boolean isTimeFormat() {
        return this.timeFormat;
    }

    public boolean isDateFormat() {
        return this.dateFormat;
    }

    public boolean isWeatherFormat() {
        return this.weatherFormat;
    }

    public boolean isWearingManager() {
        return this.wearingManager;
    }

    public void setTimeFormat(boolean timeFormat2) {
        this.timeFormat = timeFormat2;
    }

    public void setDateFormat(boolean dateFormat2) {
        this.dateFormat = dateFormat2;
    }

    public void setWeatherFormat(boolean weatherFormat2) {
        this.weatherFormat = weatherFormat2;
    }

    public void setWearingManager(boolean wearingManager2) {
        this.wearingManager = wearingManager2;
    }

    public boolean isAutoRecognitionMotion() {
        return this.autoRecognitionMotion;
    }

    public void setAutoRecognitionMotion(boolean autoRecognitionMotion2) {
        this.autoRecognitionMotion = autoRecognitionMotion2;
    }

    public boolean isScreenColor() {
        return this.screenColor;
    }

    public void setScreenColor(boolean screenColor2) {
        this.screenColor = screenColor2;
    }

    public boolean isPalmingGesture() {
        return this.palmingGesture;
    }

    public void setPalmingGesture(boolean palmingGesture2) {
        this.palmingGesture = palmingGesture2;
    }

    public int getPalmingGestureStart() {
        return this.palmingGestureStart;
    }

    public void setPalmingGestureStart(int palmingGestureStart2) {
        this.palmingGestureStart = palmingGestureStart2;
    }

    public int getPalmingGestureEnd() {
        return this.palmingGestureEnd;
    }

    public void setPalmingGestureEnd(int palmingGestureEnd2) {
        this.palmingGestureEnd = palmingGestureEnd2;
    }

    public int getBackLightStartTime() {
        return this.backLightStartTime;
    }

    public void setBackLightStartTime(int backLightStartTime2) {
        this.backLightStartTime = backLightStartTime2;
    }

    public int getBackLightEndTime() {
        return this.backLightEndTime;
    }

    public void setBackLightEndTime(int backLightEndTime2) {
        this.backLightEndTime = backLightEndTime2;
    }

    public boolean isDouble_touch_switch() {
        return this.double_touch_switch;
    }

    public void setDouble_touch_switch(boolean double_touch_switch2) {
        this.double_touch_switch = double_touch_switch2;
    }

    public boolean isWear_recognize_switch() {
        return this.wear_recognize_switch;
    }

    public void setWear_recognize_switch(boolean wear_recognize_switch2) {
        this.wear_recognize_switch = wear_recognize_switch2;
    }

    public boolean isException() {
        return this.exception;
    }

    public void setException(boolean exception2) {
        this.exception = exception2;
    }

    public int[] getBlood() {
        return this.blood;
    }

    public void setBlood(int[] blood2) {
        this.blood = blood2;
    }

    public boolean isNoDisturb() {
        return this.isNoDisturb;
    }

    public void setNoDisturb(boolean noDisturb) {
        this.isNoDisturb = noDisturb;
    }

    public int getStartNoDisturbTime() {
        return this.startNoDisturbTime;
    }

    public void setStartNoDisturbTime(int startNoDisturbTime2) {
        this.startNoDisturbTime = startNoDisturbTime2;
    }

    public int getEndNoDisturbTime() {
        return this.endNoDisturbTime;
    }

    public void setEndNoDisturbTime(int endNoDisturbTime2) {
        this.endNoDisturbTime = endNoDisturbTime2;
    }

    public boolean isIs24AfSwitch() {
        return this.is24AfSwitch;
    }

    public void setIs24AfSwitch(boolean is24AfSwitch2) {
        this.is24AfSwitch = is24AfSwitch2;
    }
}
