package com.iwown.data_link.locale;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.iwown.data_link.consts.UserConst;

public class PhoneLocale {
    private static final String TAG = "PhoneLocale";

    public static String getCountryZipCode(Context context) {
        return ((TelephonyManager) context.getSystemService(UserConst.PHONE)).getSimCountryIso().toUpperCase();
    }
}
