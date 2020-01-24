package com.iwown.data_link;

import android.app.Application;
import android.graphics.Typeface;
import android.widget.TextView;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FontChangeUtils {
    private static Map<String, Typeface> typefaceList = new HashMap();

    public static void initFonts(Application application) {
        typefaceList = new LinkedHashMap();
        typefaceList.put("DINPRO-MEDIUM", Typeface.createFromAsset(application.getAssets(), "DINPRO-MEDIUM.ttf"));
    }

    public static Typeface getNumberTypeFace() {
        return (Typeface) typefaceList.get("DINPRO-MEDIUM");
    }

    public static void setTypeFace(Typeface numberTypeFace, TextView... ids) {
        for (int i = 0; i < ids.length; i++) {
            if (!(numberTypeFace == null || ids[i] == null)) {
                ids[i].setTypeface(numberTypeFace);
            }
        }
    }
}
