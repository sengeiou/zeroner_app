package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

public class Result implements Parcelable, Serializable {
    public static final Creator<Result> CREATOR = new Creator<Result>() {
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
    private static final String TAG = Result.class.getSimpleName();
    private static final long serialVersionUID = 1;

    public Result() {
    }

    public String toJson() {
        return new Gson().toJson((Object) this);
    }

    public Map<String, Object> getFieldMap() {
        Field[] fields;
        Map<String, Object> result = new HashMap<>();
        try {
            for (Field field : getClass().getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                    field.setAccessible(true);
                    result.put(field.getName(), field.get(this));
                }
            }
        } catch (IllegalAccessException e) {
            ThrowableExtension.printStackTrace(e);
        }
        return result;
    }

    public ContentValues getContentMap() {
        Map<String, Object> result = getFieldMap();
        ContentValues values = new ContentValues();
        for (String key : result.keySet()) {
            if (result.get(key) instanceof Integer) {
                values.put(key, (Integer) result.get(key));
            } else if (result.get(key) instanceof Long) {
                values.put(key, (Long) result.get(key));
            } else if (result.get(key) instanceof Float) {
                values.put(key, (Float) result.get(key));
            } else if (result.get(key) instanceof Double) {
                values.put(key, (Double) result.get(key));
            } else if (result.get(key) instanceof String) {
                values.put(key, (String) result.get(key));
            } else {
                values.put(key, new Gson().toJson(result.get(key)));
            }
        }
        return values;
    }

    public static Result parse(String json) {
        return (Result) new Gson().fromJson(json, Result.class);
    }

    public static <T> T parse(String json, Class<T> classOfT) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return new Gson().fromJson(json, classOfT);
    }

    public static <T> T parse(Cursor cur, Class<T> classOfT) {
        Field[] fields;
        try {
            T ret = classOfT.newInstance();
            for (Field field : classOfT.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                    field.setAccessible(true);
                    if (field.getType() == Integer.TYPE) {
                        field.set(ret, Integer.valueOf(cur.getInt(cur.getColumnIndex(field.getName()))));
                    } else if (field.getType() == Long.TYPE) {
                        field.set(ret, Long.valueOf(cur.getLong(cur.getColumnIndex(field.getName()))));
                    } else if (field.getType() == Float.TYPE) {
                        field.set(ret, Float.valueOf(cur.getFloat(cur.getColumnIndex(field.getName()))));
                    } else if (field.getType() == Double.TYPE) {
                        field.set(ret, Double.valueOf(cur.getDouble(cur.getColumnIndex(field.getName()))));
                    } else if (field.getType() == String.class) {
                        field.set(ret, cur.getString(cur.getColumnIndex(field.getName())));
                    } else if (field.getType() != Boolean.TYPE) {
                        field.set(ret, new Gson().toJson((Object) cur.getString(cur.getColumnIndex(field.getName()))));
                    } else if (cur.getString(cur.getColumnIndex(field.getName())).equals("true")) {
                        field.set(ret, Boolean.valueOf(true));
                    } else {
                        field.set(ret, Boolean.valueOf(false));
                    }
                }
            }
            return ret;
        } catch (InstantiationException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        } catch (IllegalAccessException e2) {
            ThrowableExtension.printStackTrace(e2);
            return null;
        }
    }

    public static <T> T parse() {
        return null;
    }

    public static <T> List<T> parseArray(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        List<T> arrT = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                arrT.add(gson.fromJson(jsonArray.getString(i), classOfT));
            }
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        return arrT;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    private Result(Parcel in) {
    }
}
