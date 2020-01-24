package com.iwown.ble_module.utils;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONArray;
import com.alibaba.json.JSONObject;
import com.alibaba.json.parser.Feature;
import com.alibaba.json.serializer.SerializeFilter;
import com.alibaba.json.serializer.SerializerFeature;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonTool {
    private static boolean DEBUG = false;

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
        return JSON.toJSONString(bean);
    }

    public static String toJson(Object bean, SerializeFilter profilter) {
        return JSON.toJSONString(bean, profilter, new SerializerFeature[0]);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return JSON.parseObject(json, classOfT);
    }

    public static <T> ArrayList<T> getListJson(String json, Class<T> t) {
        return (ArrayList) JSON.parseObject(json, new ListParameterizedType(t), new Feature[0]);
    }

    public static JSONObject putKeyValue(String json, String key, String value) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        jsonObject.put(key, (Object) value);
        return jsonObject;
    }

    public static String getString(String result, String key) {
        return JSONObject.parseObject(result).getString(key);
    }

    public static Long getLong(String result, String key) {
        return JSONObject.parseObject(result).getLong(key);
    }

    public static JSONObject getJsonObject(String result, String key) {
        return JSONObject.parseObject(result).getJSONObject(key);
    }

    public static JSONArray getJsonArray(String result, String key) {
        return JSONObject.parseObject(result).getJSONArray(key);
    }

    public static int getIntValue(String result, String key) {
        return JSONObject.parseObject(result).getIntValue(key);
    }
}
