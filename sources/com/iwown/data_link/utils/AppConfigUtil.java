package com.iwown.data_link.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.Constants;
import com.iwown.device_module.device_setting.configure.WristbandModel;
import com.iwown.healthy.BuildConfig;
import com.socks.library.KLog;

public class AppConfigUtil {
    public static String APP_NAME_FOR_WEATHER = "";
    public static int APP_TYPE = 6;
    public static String BLE_LOG_UP_APP = "";
    public static String Bugly_Key = "";
    private static String Drviva_Package_name = "com.doctorviva.app";
    public static String GoogleMapKey = "";
    public static String InstaBug_Key = "";
    private static String Iwownfit_Package_name = "com.healthy.iwownfit_pro";
    private static String Nanfei_Track_Gpx_Package_name = "com.healthy.nanfei_trax_gps";
    private static String Newfit_Package_name = "com.app.brands.newfit";
    public static String Privacy_Link = "";
    private static String Upfit_Package_name = "com.global.brands.upfit";
    public static String VERSION = "";
    public static String WEATHER_KEY = "";
    private static String ZeronerHealth_Package_name = BuildConfig.APPLICATION_ID;
    public static String app_name = "";
    public static String brand = "";
    private static String healthy_Package_name = "com.kunekt.healthy";
    private static AppConfigUtil instance = null;
    private static Context mContext = null;
    public static String package_name = "";

    public AppConfigUtil(Context context) {
        mContext = context;
        package_name = mContext.getApplicationContext().getPackageName();
        KLog.e("licl", "package_name: " + package_name);
        if (isIwownFitPro()) {
            if (isRussia(mContext)) {
                Privacy_Link = "http://search.iwown.com/pp.html";
            } else {
                Privacy_Link = "http://api4.iwown.com/pp.html";
            }
            Bugly_Key = "82d371e7ee";
            InstaBug_Key = Constants.INSTABUG_KEY;
            BLE_LOG_UP_APP = WristbandModel.BLE_LOG_UP_APP;
            APP_TYPE = 7;
            APP_NAME_FOR_WEATHER = "iwownfit_pro";
            WEATHER_KEY = "iwowna47d69d605ce4a039d320d680798ef33";
            brand = "iWOWN";
            GoogleMapKey = "AIzaSyBeUsy0D15Hw06Ts1JOSiymy2ITn2jm4bY";
        } else if (isZeronerHealthPro()) {
            if (isRussia(mContext)) {
                Privacy_Link = "http://search.iwown.com/ppm.html";
            } else {
                Privacy_Link = "http://54.67.86.82/ppm.html";
            }
            Bugly_Key = "eedef14042";
            InstaBug_Key = "49ff9d7293b6f88f2714e11d093258a9";
            BLE_LOG_UP_APP = "06";
            APP_TYPE = 6;
            APP_NAME_FOR_WEATHER = "zeroner_health_pro";
            WEATHER_KEY = "iwowna47d69d605ce4a039d320d680798ef33";
            brand = "Zeroner";
            GoogleMapKey = "AIzaSyCjR41YoYGcYeGcjq9hBwkh8yV3zrskKfM";
        } else if (isNanfei_TRAX_GPS()) {
            Privacy_Link = "http://54.67.86.82/tgpsppm.html";
            BLE_LOG_UP_APP = com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR;
            Bugly_Key = "7748ea6d0b";
            APP_TYPE = 12;
            APP_NAME_FOR_WEATHER = context.getApplicationContext().getPackageName();
            WEATHER_KEY = "iwowna47d69d605ce4a039d320d680798ef33";
            brand = "Trax";
            GoogleMapKey = "AIzaSyBpfmPGbsBhSIALWEeT_0glrmES5nEJY50";
        } else if (isHealthy()) {
            Privacy_Link = "https://api2.iwown.com/html/mianze.html";
            BLE_LOG_UP_APP = "3";
            Bugly_Key = "900011283";
            APP_TYPE = 3;
            APP_NAME_FOR_WEATHER = "android_iwown";
            WEATHER_KEY = "iwown1d279b4cc5374639b87af4452c124802";
            brand = "埃微";
        } else if (isUpfit()) {
            Privacy_Link = "http://api4.iwown.com/ppm/dist/#/upFit";
            BLE_LOG_UP_APP = com.tencent.connect.common.Constants.VIA_REPORT_TYPE_JOININ_GROUP;
            Bugly_Key = "b211abfae2";
            APP_TYPE = 13;
            APP_NAME_FOR_WEATHER = context.getApplicationContext().getPackageName();
            WEATHER_KEY = "iwowna47d69d605ce4a039d320d680798ef33";
            brand = "UP! Fit";
            GoogleMapKey = "AIzaSyCfXq7cwx1LVw-bjfKhraMONnlkfxvZpjA";
        } else if (isNewfit()) {
            Privacy_Link = "https://api4.iwown.com/ppm/dist/#/newFit";
            BLE_LOG_UP_APP = com.tencent.connect.common.Constants.VIA_REPORT_TYPE_MAKE_FRIEND;
            Bugly_Key = "c970043cb5";
            APP_TYPE = 14;
            APP_NAME_FOR_WEATHER = context.getApplicationContext().getPackageName();
            WEATHER_KEY = "iwowna47d69d605ce4a039d320d680798ef33";
            GoogleMapKey = "AIzaSyCfXq7cwx1LVw-bjfKhraMONnlkfxvZpjA";
            brand = "NewFit";
        } else if (isDrviva()) {
            if (isRussia(mContext)) {
                Privacy_Link = "http://search.iwown.com/pp.html";
            } else {
                Privacy_Link = "https://api4.iwown.com/ppmviva.html";
            }
            Bugly_Key = "1cc89b8245";
            InstaBug_Key = Constants.INSTABUG_KEY;
            BLE_LOG_UP_APP = com.tencent.connect.common.Constants.VIA_REPORT_TYPE_DATALINE;
            APP_TYPE = 22;
            APP_NAME_FOR_WEATHER = context.getApplicationContext().getPackageName();
            WEATHER_KEY = "iwowna47d69d605ce4a039d320d680798ef33";
            brand = "iWOWN";
            GoogleMapKey = "AIzaSyBeUsy0D15Hw06Ts1JOSiymy2ITn2jm4bY";
        }
        VERSION = getClientVersionName(context);
    }

    public static String getApp_name() {
        return app_name;
    }

    public static void setApp_name(String app_name2) {
        app_name = app_name2;
        KLog.e("licl", "app_name: " + app_name2);
    }

    public static AppConfigUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (AppConfigUtil.class) {
                if (instance == null) {
                    instance = new AppConfigUtil(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public static boolean isCustomApp() {
        KLog.e(String.format("package_name:%s", new Object[]{package_name}));
        if (!package_name.equalsIgnoreCase(ZeronerHealth_Package_name) && !package_name.equalsIgnoreCase(Iwownfit_Package_name) && !package_name.equalsIgnoreCase(healthy_Package_name) && !package_name.equalsIgnoreCase(Drviva_Package_name)) {
            return true;
        }
        return false;
    }

    private static AppConfigUtil getInstance() {
        return instance;
    }

    public static boolean isNewfit() {
        return package_name.equalsIgnoreCase(Newfit_Package_name);
    }

    public static boolean isUpfit() {
        return package_name.equalsIgnoreCase(Upfit_Package_name);
    }

    public static boolean isIwownFitPro() {
        return package_name.equalsIgnoreCase(Iwownfit_Package_name);
    }

    public static boolean isDrviva() {
        return package_name.equalsIgnoreCase(Drviva_Package_name);
    }

    public static boolean isZeronerHealthPro() {
        return package_name.equalsIgnoreCase(ZeronerHealth_Package_name);
    }

    public static boolean isNanfei_TRAX_GPS() {
        return package_name.equalsIgnoreCase(Nanfei_Track_Gpx_Package_name);
    }

    public static boolean isHealthy() {
        if (TextUtils.isEmpty(package_name)) {
            try {
                package_name = mContext.getApplicationContext().getPackageName();
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                return false;
            }
        }
        if (package_name.equalsIgnoreCase(healthy_Package_name)) {
            return true;
        }
        return false;
    }

    public static boolean isHealthy(Context context) {
        if (TextUtils.isEmpty(package_name)) {
            try {
                if (mContext == null) {
                    getInstance(context.getApplicationContext());
                }
                package_name = mContext.getApplicationContext().getPackageName();
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                return false;
            }
        }
        if (package_name.equalsIgnoreCase(healthy_Package_name)) {
            return true;
        }
        return false;
    }

    public static String getPrivacy_Link() {
        if ("".equals(Privacy_Link)) {
            if (isIwownFitPro()) {
                if (isRussia(mContext)) {
                    return "http://search.iwown.com/pp.html";
                }
                return "http://api4.iwown.com/pp.html";
            } else if (isZeronerHealthPro()) {
                if (isRussia(mContext)) {
                    return "http://search.iwown.com/ppm.html";
                }
                return "http://54.67.86.82/ppm.html";
            } else if (isHealthy()) {
                return "https://api2.iwown.com/html/mianze.html";
            } else {
                if (isDrviva()) {
                    if (isRussia(mContext)) {
                        return "http://search.iwown.com/ppm.html";
                    }
                    return "https://api4.iwown.com/ppmviva.html";
                }
            }
        }
        return Privacy_Link;
    }

    public static String getClientVersionName(Context context) {
        String versionName = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
            return versionName;
        }
    }

    public static boolean isRussia(Context context) {
        return TextUtils.equals("RU", context.getResources().getConfiguration().locale.getCountry().toUpperCase());
    }
}
