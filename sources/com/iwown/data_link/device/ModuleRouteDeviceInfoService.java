package com.iwown.data_link.device;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;

public class ModuleRouteDeviceInfoService {
    @Autowired
    IDeviceService mDeviceService;

    public interface DeviceStatusListener {
        void bluetoothInit();

        void deviceConnectStatus(boolean z);
    }

    static class ModuleRouteDeviceInfoServiceHolder {
        static ModuleRouteDeviceInfoService moduleRouteDeviceInfoService = new ModuleRouteDeviceInfoService();

        ModuleRouteDeviceInfoServiceHolder() {
        }
    }

    private ModuleRouteDeviceInfoService() {
        ARouter.getInstance().inject(this);
    }

    public static ModuleRouteDeviceInfoService getInstance() {
        return ModuleRouteDeviceInfoServiceHolder.moduleRouteDeviceInfoService;
    }

    public DeviceInfo getDeviceInfo() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.getDeviceInfo();
        }
        return new DeviceInfo();
    }

    public boolean isWristConnected() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isWristConnected();
        }
        KLog.e("mDeviceService==null");
        return false;
    }

    public boolean isNotificationServiceRun() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isNotificationServiceRun();
        }
        return false;
    }

    public boolean isBind() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isBind();
        }
        return false;
    }

    public void setDeviceStatusListener(DeviceStatusListener listener) {
        if (this.mDeviceService != null) {
            this.mDeviceService.setListener(listener);
        }
    }

    public boolean isSupportRealTimeHeart() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isSupportRealTimeHeart();
        }
        return false;
    }

    public void controlRealTimeHR(boolean isOn) {
        if (this.mDeviceService != null) {
            L.file("gps 运动耳机 ; 开关-> " + isOn, 3);
            this.mDeviceService.controlRealTimeHr(isOn);
            return;
        }
        L.file("gps 运动耳机 ; 开关-> mDeviceService is null " + isOn, 3);
    }

    public boolean isSyncDataInfo() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isSyncDataInfo();
        }
        return false;
    }

    public void syncDataInfo(boolean toSync) {
        if (this.mDeviceService != null) {
            this.mDeviceService.syncDataInfo(toSync);
        }
    }

    public void getPower() {
        if (this.mDeviceService != null) {
            this.mDeviceService.getPower();
        }
    }

    public boolean isZg() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isZG();
        }
        return false;
    }

    public boolean isMtk() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isMtk();
        }
        return false;
    }

    public boolean isIw() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isIw();
        }
        return false;
    }

    public boolean isMTKHeadset() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isMTKHeadset();
        }
        return false;
    }

    public boolean isProtoBuf() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isProtoBuf();
        }
        return false;
    }

    public boolean isSupport08() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isSupport08();
        }
        return false;
    }

    public boolean isSupportHeart() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.isSupportHeart();
        }
        return false;
    }

    public boolean canEpoThisTime() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.canEpoThisTime();
        }
        return false;
    }

    public void startEpo() {
        if (this.mDeviceService != null) {
            this.mDeviceService.startEpo();
        }
    }

    public String getDevicemodel() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.getDeviceModel();
        }
        return "";
    }

    public String getDeviceVersion() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.getDeviceVersion();
        }
        return "";
    }

    public void writeWeather(float temperature, int weather_type, String country, String pm_25, String futureWeather24Json) {
        if (this.mDeviceService != null) {
            this.mDeviceService.writeWeather(temperature, weather_type, country, pm_25, futureWeather24Json);
        }
    }

    public void updateTargetStep(int step, float calorie) {
        KLog.e("===============updateTargetStep================" + step + "====calorie" + calorie);
        if (this.mDeviceService != null) {
            this.mDeviceService.updateTargetStep(step, calorie);
        }
    }

    public void updateMeasureUnit(EnumMeasureUnit unit) {
        if (this.mDeviceService != null) {
            this.mDeviceService.updateMeasureUnit(unit);
        }
    }

    public void updateBaseUserInfo(int gender, float height, float weight, int age) {
        if (this.mDeviceService != null) {
            this.mDeviceService.updateBaseUserInfo(gender, height, weight, age);
        }
    }

    public void upTodayLogFile() {
        if (this.mDeviceService != null) {
            this.mDeviceService.upTodayLogFile();
        }
    }

    public void userBindWifiScale(long uid) {
        if (this.mDeviceService != null) {
            this.mDeviceService.userBindWifiScale(uid);
        }
    }

    public DeviceSetting getDeviceSetting(String model) {
        if (this.mDeviceService != null) {
            return this.mDeviceService.getDeviceSetting(model);
        }
        return new DeviceSetting();
    }

    public String getDeviceAddress() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.getDeviceAddress();
        }
        return "";
    }

    public void setGpsSportTime(long time) {
        if (this.mDeviceService != null) {
            this.mDeviceService.setGpsSportTime(time);
        }
    }

    public String getDeviceClient() {
        if (this.mDeviceService != null) {
            return this.mDeviceService.getDeviceClient();
        }
        return "";
    }

    public boolean supportSomeSetting(int setIndex) {
        if (this.mDeviceService != null) {
            return this.mDeviceService.supportSomeSetting(setIndex);
        }
        return false;
    }
}
