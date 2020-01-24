package com.iwown.data_link.utils;

import android.content.Context;

public class HealthyShareUtility {
    static final String PREFERENCENAME = "healthy_share";
    Context mContext;

    public HealthyShareUtility(Context context) {
        this.mContext = context;
    }

    public void updateStrValueWithKey(String key, String value) {
        this.mContext.getSharedPreferences(PREFERENCENAME, 0).edit().putString(key, value).commit();
    }

    public String fetchStrValueWithKey(String key) {
        return this.mContext.getSharedPreferences(PREFERENCENAME, 0).getString(key, null);
    }

    public void updateNumberValueWithKey(String key, int value) {
        this.mContext.getSharedPreferences(PREFERENCENAME, 0).edit().putInt(key, value).commit();
    }

    public int fetchNumberValueWithKey(String key) {
        return this.mContext.getSharedPreferences(PREFERENCENAME, 0).getInt(key, 0);
    }

    public long fetchLongValueWithKey(String key) {
        return this.mContext.getSharedPreferences(PREFERENCENAME, 0).getLong(key, 0);
    }

    public void updateLongValueWithKey(String key, long value) {
        this.mContext.getSharedPreferences(PREFERENCENAME, 0).edit().putLong(key, value).commit();
    }

    public boolean fetchBooleanValueWithKey(String key) {
        return this.mContext.getSharedPreferences(PREFERENCENAME, 0).getBoolean(key, false);
    }

    public void updateBooleanValueWithKey(String key, boolean value) {
        this.mContext.getSharedPreferences(PREFERENCENAME, 0).edit().putBoolean(key, value).commit();
    }
}
