package com.iwown.my_module.utility;

import android.content.Context;
import android.graphics.Typeface;

public class FontProvider {
    private static Typeface _DINPRO_MEDIUM;

    public static Typeface getFont_DINPRO_MEDIUM(Context context) {
        if (_DINPRO_MEDIUM == null) {
            _DINPRO_MEDIUM = Typeface.createFromAsset(context.getAssets(), "DINPRO-MEDIUM.ttf");
        }
        return _DINPRO_MEDIUM;
    }
}
