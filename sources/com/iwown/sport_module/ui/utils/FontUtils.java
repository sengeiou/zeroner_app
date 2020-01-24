package com.iwown.sport_module.ui.utils;

import android.app.Application;
import android.graphics.Typeface;
import java.util.HashMap;
import java.util.Map;

public class FontUtils {
    private static Map<String, Typeface> typefaceList = new HashMap();

    public static void initFonts(Application application) {
        typefaceList.put("DINCOND-BOLD", Typeface.createFromAsset(application.getAssets(), "DINCOND-BOLD.ttf"));
    }

    public static void destory() {
        typefaceList.clear();
    }

    public static Typeface getSleepFeedBackCenterTextTypeface() {
        return (Typeface) typefaceList.get("DINCOND-BOLD");
    }
}
