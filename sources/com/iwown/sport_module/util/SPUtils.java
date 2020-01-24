package com.iwown.sport_module.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SPUtils {
    public static String ALL_Data_Upload_LAST_MS = "all_data_upload_last_ms";
    public static String Fatigue_Data_Request_Time = "fatigue_request_time";
    public static String Heart_Download_Requests_Time = "heart_download_requests_time";
    public static String Last_uploadEcgDataTime = "Last_uploadEcgDataTime";
    public static String Last_uploadFatigueDataTime = "last_uploadfatiguedatatime";
    public static String Last_uploadHeartDataTime = "last_uploadheartdatatime";
    public static String Last_uploadSleepDataTime = "last_uploadsleepdatatime";
    private static final String PREF_FILE = "iwown_sport_sp";
    public static String SLEEP_Download_Requests_Time = "sleep_download_requests_time";
    public static String TODAY_UI_First_Update = "TODAY_UI_First_Update";
    public static String Weight_Data_Request_Time = "weight_request_time";

    public static boolean save(String prefFile, Context context, String key, long value) {
        Editor editor = context.getSharedPreferences(prefFile, 0).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean save(Context context, String key, long value) {
        return save(PREF_FILE, context, key, value);
    }

    public static boolean save(String prefFile, Context context, String key, String value) {
        Editor editor = context.getSharedPreferences(prefFile, 0).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean save(Context context, String key, String value) {
        return save(PREF_FILE, context, key, value);
    }

    public static boolean save(String prefFile, Context context, String key, int value) {
        Editor editor = context.getSharedPreferences(prefFile, 0).edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean save(Context context, String key, int value) {
        return save(PREF_FILE, context, key, value);
    }

    public static boolean save(String prefFile, Context context, String key, float value) {
        Editor editor = context.getSharedPreferences(prefFile, 0).edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static boolean save(Context context, String key, float value) {
        return save(PREF_FILE, context, key, value);
    }

    public static boolean save(String prefFile, Context context, String key, boolean value) {
        Editor editor = context.getSharedPreferences(prefFile, 0).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean save(Context context, String key, boolean value) {
        return save(PREF_FILE, context, key, value);
    }

    public static long getLong(String prefFile, Context context, String key) {
        return getLong(prefFile, context, key, 0);
    }

    public static long getLong(Context context, String key) {
        return getLong(PREF_FILE, context, key, 0);
    }

    public static long getLong(String prefFile, Context context, String key, long defValue) {
        return context.getSharedPreferences(prefFile, 0).getLong(key, defValue);
    }

    public static long getLong(Context context, String key, long defValue) {
        return getLong(PREF_FILE, context, key, defValue);
    }

    public static float getFloat(String prefFile, Context context, String key) {
        return getFloat(prefFile, context, key, 0.0f);
    }

    public static float getFloat(Context context, String key) {
        return getFloat(PREF_FILE, context, key, 0.0f);
    }

    public static float getFloat(String prefFile, Context context, String key, float defValue) {
        return context.getSharedPreferences(prefFile, 0).getFloat(key, defValue);
    }

    public static float getFloat(Context context, String key, float defValue) {
        return getFloat(PREF_FILE, context, key, defValue);
    }

    public static String getString(String prefFile, Context context, String key) {
        return getString(prefFile, context, key, "");
    }

    public static String getString(Context context, String key) {
        return getString(PREF_FILE, context, key, "");
    }

    public static String getString(String prefFile, Context context, String key, String defValue) {
        return context.getSharedPreferences(prefFile, 0).getString(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        return getString(PREF_FILE, context, key, defValue);
    }

    public static int getInt(String prefFile, Context context, String key) {
        return getInt(prefFile, context, key, 0);
    }

    public static int getInt(Context context, String key) {
        return getInt(PREF_FILE, context, key, 0);
    }

    public static int getInt(String prefFile, Context context, String key, int defValue) {
        return context.getSharedPreferences(prefFile, 0).getInt(key, defValue);
    }

    public static int getInt(Context context, String key, int defValue) {
        return getInt(PREF_FILE, context, key, defValue);
    }

    public static boolean getBoolean(String prefFile, Context context, String key) {
        return getBoolean(prefFile, context, key, false);
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(PREF_FILE, context, key, false);
    }

    public static boolean getBoolean(String prefFile, Context context, String key, boolean defValue) {
        return context.getSharedPreferences(prefFile, 0).getBoolean(key, defValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getBoolean(PREF_FILE, context, key, defValue);
    }

    public static boolean contains(String prefFile, Context context, String key) {
        return context.getSharedPreferences(prefFile, 0).contains(key);
    }

    public static boolean contains(Context context, String key) {
        return contains(PREF_FILE, context, key);
    }

    public static boolean remove(String prefFile, Context context, String key) {
        return context.getSharedPreferences(prefFile, 0).edit().remove(key).commit();
    }

    public static boolean clear(String prefFile, Context context) {
        return context.getSharedPreferences(prefFile, 0).edit().clear().commit();
    }

    public static boolean remove(Context context, String key) {
        return remove(PREF_FILE, context, key);
    }

    public static boolean clear(Context context) {
        return clear(PREF_FILE, context);
    }
}
