package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.google.api.client.http.UrlEncodedParser;
import com.google.common.net.HttpHeaders;
import com.tencent.stat.DeviceInfo;
import java.util.HashMap;
import java.util.Map;

/* compiled from: GeoFenceNetManager */
public final class b {
    bm a = null;

    public b(Context context) {
        try {
            r.a().a(context);
        } catch (Throwable th) {
        }
        this.a = bm.a();
    }

    private String a(Context context, String str, Map<String, String> map) {
        byte[] b;
        try {
            HashMap hashMap = new HashMap(16);
            dc dcVar = new dc();
            hashMap.clear();
            hashMap.put("Content-Type", UrlEncodedParser.CONTENT_TYPE);
            hashMap.put(HttpHeaders.CONNECTION, "Keep-Alive");
            hashMap.put(HttpHeaders.USER_AGENT, "AMAP_Location_SDK_Android 4.7.0");
            String a2 = n.a();
            String a3 = n.a(context, a2, w.b(map));
            map.put(DeviceInfo.TAG_TIMESTAMPS, a2);
            map.put("scode", a3);
            dcVar.b(map);
            dcVar.a((Map<String, String>) hashMap);
            dcVar.a(str);
            dcVar.a(t.a(context));
            dcVar.a(di.f);
            dcVar.b(di.f);
            if (dr.k(context)) {
                dcVar.a(str.replace("http:", "https:"));
                bm bmVar = this.a;
                b = bm.a(dcVar);
            } else {
                bm bmVar2 = this.a;
                b = bm.b(dcVar);
            }
            return new String(b, "utf-8");
        } catch (Throwable th) {
            return null;
        }
    }

    private static Map<String, String> b(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        HashMap hashMap = new HashMap(16);
        hashMap.put("key", l.f(context));
        if (!TextUtils.isEmpty(str)) {
            hashMap.put("keywords", str);
        }
        if (!TextUtils.isEmpty(str2)) {
            hashMap.put("types", str2);
        }
        if (!TextUtils.isEmpty(str5) && !TextUtils.isEmpty(str6)) {
            hashMap.put("location", str6 + "," + str5);
        }
        if (!TextUtils.isEmpty(str3)) {
            hashMap.put("city", str3);
        }
        if (!TextUtils.isEmpty(str4)) {
            hashMap.put("offset", str4);
        }
        if (!TextUtils.isEmpty(str7)) {
            hashMap.put("radius", str7);
        }
        return hashMap;
    }

    public final String a(Context context, String str, String str2) {
        Map b = b(context, str2, null, null, null, null, null, null);
        b.put("extensions", "all");
        return a(context, str, b);
    }

    public final String a(Context context, String str, String str2, String str3, String str4, String str5) {
        Map b = b(context, str2, str3, str4, str5, null, null, null);
        b.put("children", "1");
        b.put("page", "1");
        b.put("extensions", "base");
        return a(context, str, b);
    }

    public final String a(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        Map b = b(context, str2, str3, null, str4, str5, str6, str7);
        b.put("children", "1");
        b.put("page", "1");
        b.put("extensions", "base");
        return a(context, str, b);
    }
}
