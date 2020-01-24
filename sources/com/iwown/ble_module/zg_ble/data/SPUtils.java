package com.iwown.ble_module.zg_ble.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtils {
    private static final String PREFERENCE_FILE_NAME = "ZG_SP_File";
    private static final String SDK_Firmware = "ZG_Firmware";

    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_FILE_NAME, 0);
    }

    public static void saveFirmwareInformation(Context context, String json) {
        Editor edit = getDefaultSharedPreferences(context).edit();
        edit.putString(SDK_Firmware, json);
        edit.apply();
    }

    public static String readFirmwareInformation(Context context) {
        return getDefaultSharedPreferences(context).getString(SDK_Firmware, "");
    }
}
