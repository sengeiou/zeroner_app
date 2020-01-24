package com.youzan.sdk.tool;

import android.net.Uri;
import android.net.Uri.Builder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: UrlParse */
public final class g {
    /* renamed from: ˊ reason: contains not printable characters */
    public static String m120(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        Builder builder = Uri.parse(url).buildUpon();
        builder.clearQuery();
        return builder.build().toString();
    }

    @NonNull
    /* renamed from: ˊ reason: contains not printable characters */
    public static Map<String, String> m122(@Nullable Map<String, String> original, @Nullable Map<String, String> extra) {
        return m123(original, extra, true);
    }

    @NonNull
    /* renamed from: ˊ reason: contains not printable characters */
    public static Map<String, String> m123(@Nullable Map<String, String> original, @Nullable Map<String, String> extra, boolean cover) {
        if (original == null) {
            original = new LinkedHashMap<>();
        }
        if (cover && extra != null && extra.size() > 0) {
            for (Entry<String, String> entry : extra.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (!TextUtils.isEmpty(key)) {
                    if (TextUtils.isEmpty(value)) {
                        value = "";
                    }
                    original.put(key, value);
                }
            }
        }
        return original;
    }

    /* renamed from: ˋ reason: contains not printable characters */
    public static Map<String, String> m124(@NonNull String url) {
        Map<String, String> params = new LinkedHashMap<>();
        if (!TextUtils.isEmpty(url) && url.split("\\?").length > 1) {
            Uri uri = Uri.parse(url);
            Set<String> keys = uri.getQueryParameterNames();
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    String value = uri.getQueryParameter(key);
                    if (!TextUtils.isEmpty(value)) {
                        params.put(key, value);
                    }
                }
            }
        }
        if (params.isEmpty()) {
            return null;
        }
        return params;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static String m121(String url, Map<String, String> param) {
        if (param == null || param.size() == 0) {
            return url;
        }
        String uri = url;
        if (TextUtils.isEmpty(uri)) {
            return uri;
        }
        Builder builder = Uri.parse(url).buildUpon();
        for (Entry<String, String> entry : param.entrySet()) {
            builder.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        return builder.build().toString();
    }
}
