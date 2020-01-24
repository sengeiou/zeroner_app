package com.iwown.device_module.device_firmware_upgrade;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.text.TextUtils;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.WristbandModel;
import com.socks.library.KLog;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.List;
import java.util.regex.Pattern;

public class Util {
    public static final int Scales = 2;
    public static final int WATCH = 1;
    public static final int WRISTBAND = 1;

    public static int device_type(String deviceName) {
        if (!TextUtils.isEmpty(deviceName) && !deviceName.contains("i5") && !deviceName.contains("l01") && !deviceName.contains(WristbandModel.MODEL_V6) && !deviceName.contains("l02") && !deviceName.contains("i7") && !deviceName.contains("l03") && !deviceName.contains("l04") && !deviceName.contains("l05") && !deviceName.contains("l06") && !deviceName.contains("l13") && deviceName.contains("l15")) {
        }
        return 1;
    }

    public static String getNewMac(String mac, int type) {
        if (isDialog() || BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
            return mac;
        }
        String str = "";
        String oneMac = mac.substring(0, mac.lastIndexOf(":") + 1);
        int newTwoMac = Integer.parseInt(mac.substring(mac.lastIndexOf(":") + 1, mac.length()), 16);
        if (type == 1) {
            if (newTwoMac == 255) {
                newTwoMac = 0;
            } else {
                newTwoMac++;
            }
        } else if (type == 2) {
            if (newTwoMac == 0) {
                newTwoMac = 255;
            } else {
                newTwoMac--;
            }
        }
        String last = Integer.toHexString(newTwoMac);
        StringBuilder append = new StringBuilder().append(oneMac);
        if (last.length() == 1) {
            last = 0 + last;
        }
        String newMac = append.append(last).toString();
        KLog.e("旧mac====>" + mac + "新mac" + newMac);
        return newMac.toUpperCase();
    }

    public static boolean isDialog() {
        KLog.i("getModel()" + DeviceUtils.getDeviceInfo().getModel());
        return 3 == DeviceSettingsBiz.getInstance().getDevPlatform(DeviceUtils.getDeviceInfo().getModel());
    }

    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        List<RunningServiceInfo> serviceInfos = ((ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME)).getRunningServices(50);
        if (serviceInfos == null || serviceInfos.size() < 1) {
            return false;
        }
        int i = 0;
        while (true) {
            if (i >= serviceInfos.size()) {
                break;
            } else if (((RunningServiceInfo) serviceInfos.get(i)).service.getClassName().contains(className)) {
                isRunning = true;
                break;
            } else {
                i++;
            }
        }
        return isRunning;
    }

    public static boolean isNumber(String str) {
        return Pattern.compile("^[0-9]+(.[0-9]*)?$").matcher(str).matches();
    }
}
