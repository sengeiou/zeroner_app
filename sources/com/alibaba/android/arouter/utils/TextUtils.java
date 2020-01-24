package com.alibaba.android.arouter.utils;

import android.net.Uri;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TextUtils {
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Map<String, String> splitQueryParameters(Uri rawUri) {
        int end;
        String query = rawUri.getEncodedQuery();
        if (query == null) {
            return Collections.emptyMap();
        }
        Map<String, String> paramMap = new LinkedHashMap<>();
        int start = 0;
        do {
            int next = query.indexOf(38, start);
            if (next == -1) {
                end = query.length();
            } else {
                end = next;
            }
            int separator = query.indexOf(61, start);
            if (separator > end || separator == -1) {
                separator = end;
            }
            String name = query.substring(start, separator);
            if (!android.text.TextUtils.isEmpty(name)) {
                paramMap.put(Uri.decode(name), Uri.decode(separator == end ? "" : query.substring(separator + 1, end)));
            }
            start = end + 1;
        } while (start < query.length());
        return Collections.unmodifiableMap(paramMap);
    }

    public static String getLeft(String key) {
        if (!key.contains("|") || key.endsWith("|")) {
            return key;
        }
        return key.substring(0, key.indexOf("|"));
    }

    public static String getRight(String key) {
        if (!key.contains("|") || key.startsWith("|")) {
            return key;
        }
        return key.substring(key.indexOf("|") + 1);
    }
}
