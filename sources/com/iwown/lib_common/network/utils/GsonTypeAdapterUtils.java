package com.iwown.lib_common.network.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GsonTypeAdapterUtils {
    public static Gson gsonWithDate2StringGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<String>() {
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return this.df.format(Long.valueOf(json.getAsLong()));
            }
        });
        builder.registerTypeAdapter(Timestamp.class, new JsonDeserializer<String>() {
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return this.df.format(Long.valueOf(json.getAsLong()));
            }
        });
        return builder.create();
    }
}
