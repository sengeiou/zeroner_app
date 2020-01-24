package com.iwown.data_link.data;

import android.content.Context;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.utils.PreferenceUtility;

public class GlobalDataUpdater {
    public static void setLoginStatus(Context context, int status) {
        new PreferenceUtility(context).updateNumberValueWithKey("login", status);
    }

    public static void setMeasureUnit(Context context, EnumMeasureUnit unit) {
        new PreferenceUtility(context).updateNumberValueWithKey(UserConst.MEASUREUNIT, unit.ordinal());
    }

    public static void setBleFlag(Context context, int flag) {
        new PreferenceUtility(context).updateNumberValueWithKey(UserConst.BLE_FLAG, flag);
    }

    public static void setAppFlag(Context context, int flag) {
        new PreferenceUtility(context).updateNumberValueWithKey(UserConst.APP_FLAG, flag);
    }

    public static void setEmail(Context context, String email) {
        new PreferenceUtility(context).updateStrValueWithKey("email", email);
    }

    public static void setRegisterDate(Context context, String registerDate) {
        new PreferenceUtility(context).updateStrValueWithKey(UserConst.REGISTER_DATE, registerDate);
    }

    public static void setStravaCode(Context context, String code) {
        new PreferenceUtility(context).updateStrValueWithKey(UserConst.STRAVA_CODE, code);
    }

    public static void setStravaToken(Context context, String token) {
        new PreferenceUtility(context).updateStrValueWithKey(UserConst.STRAVA_TOKEN, token);
    }

    public static void setGoogleFitConnectStatus(Context context, int status) {
        new PreferenceUtility(context).updateNumberValueWithKey(UserConst.GOOGLEFIT_STATUS, status);
    }

    public static void setUnitSetStatus(Context context, int status) {
        new PreferenceUtility(context).updateNumberValueWithKey(UserConst.UNIT_SET_STATUS, status);
    }

    public static void setMineGuideStatus(Context context, int status) {
        new PreferenceUtility(context).updateNumberValueWithKey(UserConst.MINE_GUIDE_STATUS, status);
    }

    public static void setBindDevice(Context context, String device) {
        new PreferenceUtility(context).updateStrValueWithKey(UserConst.BIND_DEVICE, device);
    }

    public static void setPrevBindDevice(Context context, String device) {
        new PreferenceUtility(context).updateStrValueWithKey(UserConst.PREV_BIND_DEVICE, device);
    }

    public static void setPrevUid(Context context, long uid) {
        new PreferenceUtility(context).updateLongValueWithKey("prev_uid", uid);
    }

    public static void setFeedbackStatus(Context context, boolean hasNew) {
        new PreferenceUtility(context).updateBooleanValueWithKey(UserConst.FEEDBACK_STATUS, hasNew);
    }

    public static void setRegisterTime(Context context, long time) {
        new PreferenceUtility(context).updateLongValueWithKey("register", time);
    }
}
