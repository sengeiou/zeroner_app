package com.iwown.device_module.common.utils;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.iwown.device_module.common.sql.DeviceName;
import com.iwown.device_module.common.sql.DeviceNameCode;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;

public class HealthyUtil {
    public static String alias = null;

    public static String getNewName(String name) {
        if (TextUtils.isEmpty(alias)) {
            alias = Utils.getFromAssets(ContextUtil.app, "alias.txt");
        }
        List<DeviceName> deviceNames = ((DeviceNameCode) new Gson().fromJson(alias, DeviceNameCode.class)).getData();
        String[] s1 = name.split(HelpFormatter.DEFAULT_OPT_PREFIX);
        if (s1 == null || s1.length <= 0 || deviceNames == null || deviceNames.size() <= 0) {
            return name;
        }
        for (DeviceName deviceName : deviceNames) {
            if (deviceName.getBroadcast_name().contains(s1[0])) {
                return createDeviceName(deviceName.getAlias(), name);
            }
        }
        return name;
    }

    private static String createDeviceName(String pref, String deviceName) {
        if (deviceName.lastIndexOf(HelpFormatter.DEFAULT_OPT_PREFIX) > 0) {
            return pref + deviceName.substring(deviceName.lastIndexOf(HelpFormatter.DEFAULT_OPT_PREFIX), deviceName.length());
        }
        return deviceName;
    }
}
