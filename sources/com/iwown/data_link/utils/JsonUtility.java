package com.iwown.data_link.utils;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;

public class JsonUtility {
    public static <T> List<T> jsonToList(String json, Class<T[]> clazz) {
        return Arrays.asList((Object[]) new Gson().fromJson(json, clazz));
    }

    public static <T> T[] jsonToArray(String json, Class<T[]> clazz) {
        return (Object[]) new Gson().fromJson(json, clazz);
    }
}
