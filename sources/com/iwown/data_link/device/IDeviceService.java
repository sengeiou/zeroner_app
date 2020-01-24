package com.iwown.data_link.device;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService.DeviceStatusListener;
import com.iwown.data_link.enumtype.EnumMeasureUnit;

public interface IDeviceService extends IProvider {
    boolean canEpoThisTime();

    void controlRealTimeHr(boolean z);

    String getDeviceAddress();

    String getDeviceClient();

    DeviceInfo getDeviceInfo();

    String getDeviceModel();

    DeviceSetting getDeviceSetting(String str);

    String getDeviceVersion();

    void getPower();

    boolean isBind();

    boolean isIw();

    boolean isMTKHeadset();

    boolean isMtk();

    boolean isNotificationServiceRun();

    boolean isProtoBuf();

    boolean isSupport08();

    boolean isSupportHeart();

    boolean isSupportRealTimeHeart();

    boolean isSyncDataInfo();

    boolean isWristConnected();

    boolean isZG();

    void setGpsSportTime(long j);

    void setListener(DeviceStatusListener deviceStatusListener);

    void startEpo();

    boolean supportSomeSetting(int i);

    void syncDataInfo(boolean z);

    void upTodayLogFile();

    void updateBaseUserInfo(int i, float f, float f2, int i2);

    void updateMeasureUnit(EnumMeasureUnit enumMeasureUnit);

    void updateTargetStep(int i, float f);

    void userBindWifiScale(long j);

    void writeWeather(float f, int i, String str, String str2, String str3);
}
