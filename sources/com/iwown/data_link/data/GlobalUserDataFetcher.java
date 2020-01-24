package com.iwown.data_link.data;

import android.content.Context;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.enumtype.EnumTemperature;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.my_module.utility.Constants;

public class GlobalUserDataFetcher {
    public static Long getCurrentUid(Context context) {
        return Long.valueOf(new PreferenceUtility(context).fetchLongValueWithKey(UserConst.UID));
    }

    public static EnumMeasureUnit getPreferredMeasureUnit(Context context) {
        return EnumMeasureUnit.values()[new PreferenceUtility(context).fetchNumberValueWithKey(UserConst.MEASUREUNIT)];
    }

    public static EnumTemperature getPreferredTemperature(Context context) {
        return EnumTemperature.values()[new PreferenceUtility(context).fetchNumberValueWithKey(UserConst.TEMPERATUREUNIT)];
    }

    public static int getUnitType(Context context) {
        return new PreferenceUtility(context).fetchNumberValueWithKey(UserConst.MEASUREUNIT);
    }

    public static int getTemperatureUnit(Context context) {
        return new PreferenceUtility(context).fetchNumberValueWithKey(UserConst.TEMPERATUREUNIT);
    }

    public static int getLoginStatus(Context context) {
        return new PreferenceUtility(context).fetchNumberValueWithKey("login");
    }

    public static int getBleFlag(Context context) {
        return new PreferenceUtility(context).fetchNumberValueWithKey(UserConst.BLE_FLAG);
    }

    public static String getEmail(Context context) {
        return new PreferenceUtility(context).fetchStrValueWithKey("email");
    }

    public static String getStravaToken(Context context) {
        return new PreferenceUtility(context).fetchStrValueWithKey(UserConst.STRAVA_TOKEN);
    }

    public static String getStravaCode(Context context) {
        return new PreferenceUtility(context).fetchStrValueWithKey(UserConst.STRAVA_CODE);
    }

    public static int getGoogleFitConnectStatus(Context context) {
        return new PreferenceUtility(context).fetchNumberValueWithKey(UserConst.GOOGLEFIT_STATUS);
    }

    public static int getUnitSetStatus(Context context) {
        return new PreferenceUtility(context).fetchNumberValueWithKey(UserConst.UNIT_SET_STATUS);
    }

    public static int getMineGuideStatus(Context context) {
        return new PreferenceUtility(context).fetchNumberValueWithKey(UserConst.MINE_GUIDE_STATUS);
    }

    public static String getBindDevice(Context context) {
        return new PreferenceUtility(context).fetchStrValueWithKey(UserConst.BIND_DEVICE);
    }

    public static String getPrevBindDevice(Context context) {
        return new PreferenceUtility(context).fetchStrValueWithKey(UserConst.PREV_BIND_DEVICE);
    }

    public static String getRegisterDate(Context context) {
        return new PreferenceUtility(context).fetchStrValueWithKey(UserConst.REGISTER_DATE);
    }

    public static long getPrevUid(Context context) {
        return new PreferenceUtility(context).fetchLongValueWithKey("prev_uid");
    }

    public static String getEmailOldApp(Context context) {
        return new PreferenceUtility(context).fetchStrValueWithKey(Constants.LOGIN_USERNAME);
    }

    public static boolean getFeedbackStatus(Context context) {
        return new PreferenceUtility(context).fetchBooleanValueWithKey(UserConst.FEEDBACK_STATUS);
    }

    public static long getRegisterTime(Context context) {
        return new PreferenceUtility(context).fetchLongValueWithKey("register");
    }
}
