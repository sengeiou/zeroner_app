package com.iwown.device_module.common.utils;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static final String MESSAGE = "message";
    public static final String RETCODE = "retCode";
    private static Gson gson = new Gson();

    private static class ListParameterizedType implements ParameterizedType {
        private Type type;

        private ListParameterizedType(Type type2) {
            this.type = type2;
        }

        public Type[] getActualTypeArguments() {
            return new Type[]{this.type};
        }

        public Type getRawType() {
            return ArrayList.class;
        }

        public Type getOwnerType() {
            return null;
        }
    }

    public static String toJson(Object bean) {
        return gson.toJson(bean);
    }

    public static String toJson(Object bean, Type type) {
        return gson.toJson(bean, type);
    }

    public static Object fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> ArrayList<T> getListJson(String json, Class<T> t) {
        return (ArrayList) gson.fromJson(json, new ListParameterizedType(t));
    }

    public static String getString(String response, String key) {
        String retCode = null;
        try {
            return new JSONObject(response).getString(key);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
            return retCode;
        }
    }

    public static int getInt(String response, String key) {
        int retCode = -1;
        try {
            return new JSONObject(response).getInt(key);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
            return retCode;
        }
    }

    public static boolean getBoolean(String response, String key) {
        boolean retCode = false;
        try {
            return new JSONObject(response).getBoolean(key);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
            return retCode;
        }
    }

    public static long getLong(String response, String key) {
        long retCode = -1;
        try {
            return new JSONObject(response).getLong("key");
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
            return retCode;
        }
    }

    public static int getRetCode(String response) {
        int retCode = -1;
        try {
            return new JSONObject(response).getInt("retCode");
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
            return retCode;
        }
    }

    public static boolean hasKey(String response, String key) {
        try {
            if (new JSONObject(response).has(key)) {
                return true;
            }
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        return false;
    }

    public static boolean retCodeisOk(int retCode) {
        return retCode == 0;
    }
}
