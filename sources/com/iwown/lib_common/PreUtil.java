package com.iwown.lib_common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.ArrayMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PreUtil {
    private static Context mContext = null;

    public static void init(Context context) {
        mContext = context;
    }

    public static int getSharedInt(String file_name, String key, int defaultValue) {
        return getSharedPreferences(file_name).getInt(key, defaultValue);
    }

    public static boolean getSharedBoolean(String file_name, String key, boolean defaultValue) {
        return getSharedPreferences(file_name).getBoolean(key, defaultValue);
    }

    public static float getSharedFloat(String file_name, String key, float defaultValue) {
        return getSharedPreferences(file_name).getFloat(key, defaultValue);
    }

    public static long getSharedLong(String file_name, String key, long defaultValue) {
        return getSharedPreferences(file_name).getLong(key, defaultValue);
    }

    public static String getSharedString(String file_name, String key, String defaultValue) {
        return getSharedPreferences(file_name).getString(key, defaultValue);
    }

    public static Set<String> getSharedSet(String file_name, String key, Set<String> defaultValue) {
        return getSharedPreferences(file_name).getStringSet(key, defaultValue);
    }

    public static Map<String, ?> getAll(String file_name) {
        return getSharedPreferences(file_name).getAll();
    }

    public static boolean contain(String file_name, String key) {
        return getSharedPreferences(file_name).contains(key);
    }

    public static void putSharedInt(String file_name, String key, int value) {
        Editor editor = getSharedPreferences(file_name).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putSharedBoolean(String file_name, String key, boolean value) {
        Editor editor = getSharedPreferences(file_name).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putSharedFloat(String file_name, String key, float value) {
        Editor editor = getSharedPreferences(file_name).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void putSharedLong(String file_name, String key, long value) {
        Editor editor = getSharedPreferences(file_name).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void putSharedString(String file_name, String key, String value) {
        Editor editor = getSharedPreferences(file_name).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putSharedSet(String file_name, String key, Set<String> value) {
        Editor editor = getSharedPreferences(file_name).edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static void putAll(String file_name, ArrayMap<String, Object> arrayMap) {
        Editor editor = getSharedEditor(file_name, mContext);
        for (Entry<String, Object> entry : arrayMap.entrySet()) {
            String key = (String) entry.getKey();
            Object obj = entry.getValue();
            if (entry.getValue() instanceof Integer) {
                editor.putInt(key, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                editor.putLong(key, ((Long) obj).longValue());
            } else if (obj instanceof Boolean) {
                editor.putBoolean(key, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Float) {
                editor.putFloat(key, ((Float) obj).floatValue());
            } else if (obj instanceof Set) {
                editor.putStringSet(key, (Set) obj);
            } else if (obj instanceof String) {
                editor.putString(key, String.valueOf(obj));
            }
        }
        editor.apply();
    }

    public static void putAll(String file_name, HashMap<String, Object> arrayMap) {
        Editor editor = getSharedEditor(file_name, mContext);
        for (Entry<String, Object> entry : arrayMap.entrySet()) {
            String key = (String) entry.getKey();
            Object obj = entry.getValue();
            if (entry.getValue() instanceof Integer) {
                editor.putInt(key, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                editor.putLong(key, ((Long) obj).longValue());
            } else if (obj instanceof Boolean) {
                editor.putBoolean(key, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Float) {
                editor.putFloat(key, ((Float) obj).floatValue());
            } else if (obj instanceof Set) {
                editor.putStringSet(key, (Set) obj);
            } else if (obj instanceof String) {
                editor.putString(key, String.valueOf(obj));
            }
        }
        editor.apply();
    }

    public static void clear(String file_name) {
        getSharedPreferences(file_name).edit().clear().apply();
    }

    public static void remove(String file_name, String... keys) {
        Editor editor = getSharedPreferences(file_name).edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }

    private static SharedPreferences getSharedPreferences(String file_name) {
        if (mContext != null) {
            return mContext.getSharedPreferences(file_name, 0);
        }
        throw new IllegalStateException("Please init PreUtil in Application");
    }

    public static SharedPreferences getSharedPreferences(String name, Context context) {
        return context.getSharedPreferences(name, 0);
    }

    public static Editor getSharedEditor(String name, Context context) {
        return context.getSharedPreferences(name, 0).edit();
    }
}
