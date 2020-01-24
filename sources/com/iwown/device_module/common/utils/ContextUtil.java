package com.iwown.device_module.common.utils;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.device_module.DeviceInitUtils;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;

public class ContextUtil {
    public static Application app = DeviceInitUtils.getInstance().getMyApplication();
    public static boolean isBackground = true;
    public static boolean isFirmwareUp;
    public static final Object sObject = new Object();

    public static Object getObject() {
        return sObject;
    }

    public static String getUID() {
        PrefUtil.save((Context) app, UserAction.User_Uid, ModuleRouteUserInfoService.getInstance().getUserInfo(app).uid);
        return String.valueOf(PrefUtil.getLong(app, UserAction.User_Uid));
    }

    public static long getLUID() {
        return PrefUtil.getLong(app, UserAction.User_Uid);
    }

    public static String getDeviceNameNoClear() {
        return PrefUtil.getString(app, BleAction.Bluetooth_Device_Name);
    }

    public static String getDeviceAddressNoClear() {
        return PrefUtil.getString(app, BleAction.Bluetooth_Device_Address);
    }

    public static String getDeviceAddressCurr() {
        return PrefUtil.getString(app, BleAction.Bluetooth_Device_Address_Current_Device);
    }

    public static String getDeviceNameCurr() {
        return PrefUtil.getString(app, BleAction.Bluetooth_Device_Name_Current_Device);
    }

    public static void startSong() {
        try {
            MediaPlayer.create(app, R.raw.song).start();
        } catch (IllegalStateException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
