package com.iwown.device_module;

import android.app.Application;
import com.iwown.ble_module.iwown.bluetooth.IBle;
import com.iwown.ble_module.zg_ble.bluetooth.ZGIBle;
import com.iwown.device_module.common.network.config.BaseNetParams;

public class DeviceInitUtils {
    private IBle mBle;
    private Application myApplication;
    private com.iwown.ble_module.proto.ble.IBle proBle;
    private ZGIBle zgiBle;

    static class DeviceInitUtilsHolder {
        static DeviceInitUtils deviceInitUtils = new DeviceInitUtils();

        DeviceInitUtilsHolder() {
        }
    }

    public void changeURLRU(boolean b) {
        if (b) {
            BaseNetParams.changeURLRU();
        }
    }

    public static DeviceInitUtils getInstance() {
        return DeviceInitUtilsHolder.deviceInitUtils;
    }

    public Application getMyApplication() {
        return this.myApplication;
    }

    private DeviceInitUtils() {
    }

    public IBle getmBle() {
        return this.mBle;
    }

    public void init(Application myApplication2) {
        this.myApplication = myApplication2;
    }

    public void addBle(IBle mBle1) {
        this.mBle = mBle1;
    }

    public void addZGBle(ZGIBle mBle1) {
        this.zgiBle = mBle1;
    }

    public ZGIBle getZgBle() {
        return this.zgiBle;
    }

    public void addProBle(com.iwown.ble_module.proto.ble.IBle proBle2) {
        this.proBle = proBle2;
    }

    public com.iwown.ble_module.proto.ble.IBle getProBle() {
        return this.proBle;
    }
}
