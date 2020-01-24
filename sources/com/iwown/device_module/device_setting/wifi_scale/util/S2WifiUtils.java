package com.iwown.device_module.device_setting.wifi_scale.util;

import android.content.Context;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.common.network.utils.ToastUtil;
import com.iwown.device_module.common.sql.weight.TB_S2WifiConfig;
import com.socks.library.KLog;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.litepal.crud.DataSupport;

public class S2WifiUtils {
    public static void updateMac(long uid, String mac) {
        TB_S2WifiConfig last = (TB_S2WifiConfig) DataSupport.where("uid=? ", uid + "").findLast(TB_S2WifiConfig.class);
        if (last == null) {
            last = new TB_S2WifiConfig();
        }
        last.setUid(uid);
        last.setMac(mac);
        last.save();
    }

    public static void updateS2WifiConfig(long uid, String config_wifi_name, String config_wifi_pwd) {
        TB_S2WifiConfig last = (TB_S2WifiConfig) DataSupport.where("uid=? ", uid + "").findLast(TB_S2WifiConfig.class);
        if (last == null) {
            ToastUtil.showToast("error s2wifi mac is null");
            return;
        }
        last.setConfig_wifi_name(config_wifi_name);
        last.setConfig_wifi_pwd(config_wifi_pwd);
        last.save();
    }

    public static boolean s2WifiConfigIsOK(long uid) {
        TB_S2WifiConfig last = (TB_S2WifiConfig) DataSupport.where("uid=? ", uid + "").findLast(TB_S2WifiConfig.class);
        if (last != null && !TextUtils.isEmpty(last.getConfig_wifi_name()) && !TextUtils.isEmpty(last.getConfig_wifi_pwd())) {
            return true;
        }
        return false;
    }

    public static boolean s2WifiConfigMacIsOK(long uid) {
        TB_S2WifiConfig last = (TB_S2WifiConfig) DataSupport.where("uid=? ", uid + "").findLast(TB_S2WifiConfig.class);
        if (last != null && !TextUtils.isEmpty(last.getMac())) {
            return true;
        }
        return false;
    }

    public static String wifiScaleMac(String uid) {
        KLog.e("   " + uid);
        TB_S2WifiConfig last = (TB_S2WifiConfig) DataSupport.where("uid=? ", uid + "").findLast(TB_S2WifiConfig.class);
        if (last != null) {
            return last.getMac();
        }
        return null;
    }

    public static String getFromAssets(Context context, String fileName) {
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(fileName)));
            String str = "";
            String Result = "";
            while (true) {
                String line = bufReader.readLine();
                if (line == null) {
                    return Result;
                }
                Result = Result + line;
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }
}
