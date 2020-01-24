package com.iwown.my_module.feedback.util;

import android.os.Build.VERSION;
import android.os.LocaleList;
import java.util.Locale;

public class LocaleUtil {
    public static String getLanguage() {
        Locale locale;
        if (VERSION.SDK_INT >= 24) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        String language = locale.getLanguage();
        if (!"zh".equals(language)) {
            return language;
        }
        if ("CN".equals(locale.getCountry())) {
            return "zh-Hans";
        }
        return "zh-Hant";
    }
}
