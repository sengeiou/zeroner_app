package com.safframework.prefs;

import android.content.Context;

public class AppPrefs extends BasePrefs {
    private static final String PREFS_NAME = "AppPrefs";

    private AppPrefs(Context context) {
        super(context, PREFS_NAME);
    }

    public static AppPrefs get(Context context) {
        return new AppPrefs(context);
    }
}
