package com.facebook.stetho.inspector.domstorage;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;

public class SharedPreferencesHelper {
    private static final String PREFS_SUFFIX = ".xml";

    private SharedPreferencesHelper() {
    }

    public static List<String> getSharedPreferenceTags(Context context) {
        ArrayList<String> tags = new ArrayList<>();
        File root = new File(context.getApplicationInfo().dataDir + "/shared_prefs");
        if (root.exists()) {
            for (File file : root.listFiles()) {
                String fileName = file.getName();
                if (fileName.endsWith(PREFS_SUFFIX)) {
                    tags.add(fileName.substring(0, fileName.length() - PREFS_SUFFIX.length()));
                }
            }
        }
        Collections.sort(tags);
        return tags;
    }

    public static String valueToString(Object value) {
        if (value == null) {
            return null;
        }
        if (!(value instanceof Set)) {
            return value.toString();
        }
        JSONArray array = new JSONArray();
        for (String entry : (Set) value) {
            array.put(entry);
        }
        return array.toString();
    }

    @Nullable
    public static Object valueFromString(String newValue, Object existingValue) throws IllegalArgumentException {
        if (existingValue instanceof Integer) {
            return Integer.valueOf(Integer.parseInt(newValue));
        }
        if (existingValue instanceof Long) {
            return Long.valueOf(Long.parseLong(newValue));
        }
        if (existingValue instanceof Float) {
            return Float.valueOf(Float.parseFloat(newValue));
        }
        if (existingValue instanceof Boolean) {
            return parseBoolean(newValue);
        }
        if (existingValue instanceof String) {
            return newValue;
        }
        if (existingValue instanceof Set) {
            try {
                JSONArray obj = new JSONArray(newValue);
                int objN = obj.length();
                HashSet<String> set = new HashSet<>(objN);
                for (int i = 0; i < objN; i++) {
                    set.add(obj.getString(i));
                }
                return set;
            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            throw new IllegalArgumentException("Unsupported type: " + existingValue.getClass().getName());
        }
    }

    private static Boolean parseBoolean(String s) throws IllegalArgumentException {
        if ("1".equals(s) || "true".equalsIgnoreCase(s)) {
            return Boolean.TRUE;
        }
        if ("0".equals(s) || "false".equalsIgnoreCase(s)) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException("Expected boolean, got " + s);
    }
}
