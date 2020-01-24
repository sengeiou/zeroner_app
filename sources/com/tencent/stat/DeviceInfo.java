package com.tencent.stat;

import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.k;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceInfo {
    public static final int NEW_USER = 0;
    public static final int OLD_USER = 1;
    public static final String TAG_ANDROID_ID = "aid";
    public static final String TAG_FLAG = "__MTA_DEVICE_INFO__";
    public static final String TAG_IMEI = "ui";
    public static final String TAG_MAC = "mc";
    public static final String TAG_MID = "mid";
    public static final String TAG_TIMESTAMPS = "ts";
    public static final String TAG_VERSION = "ver";
    public static final int UPGRADE_USER = 2;
    private static StatLogger h = k.b();
    private String a = null;
    private String b = null;
    private String c = null;
    private String d = "0";
    private int e;
    private int f = 0;
    private long g = 0;

    DeviceInfo() {
    }

    DeviceInfo(String str, String str2, int i) {
        this.a = str;
        this.b = str2;
        this.e = i;
    }

    static DeviceInfo a(String str) {
        DeviceInfo deviceInfo = new DeviceInfo();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull(TAG_IMEI)) {
                deviceInfo.d(jSONObject.getString(TAG_IMEI));
            }
            if (!jSONObject.isNull(TAG_MAC)) {
                deviceInfo.e(jSONObject.getString(TAG_MAC));
            }
            if (!jSONObject.isNull(TAG_MID)) {
                deviceInfo.c(jSONObject.getString(TAG_MID));
            }
            if (!jSONObject.isNull(TAG_ANDROID_ID)) {
                deviceInfo.b(jSONObject.getString(TAG_ANDROID_ID));
            }
            if (!jSONObject.isNull(TAG_TIMESTAMPS)) {
                deviceInfo.a(jSONObject.getLong(TAG_TIMESTAMPS));
            }
            if (!jSONObject.isNull(TAG_VERSION)) {
                deviceInfo.a(jSONObject.getInt(TAG_VERSION));
            }
        } catch (JSONException e2) {
            h.e((Exception) e2);
        }
        return deviceInfo;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public int a(DeviceInfo deviceInfo) {
        if (deviceInfo == null) {
            return 1;
        }
        String mid = getMid();
        String mid2 = deviceInfo.getMid();
        if (mid != null && mid2 != null && mid.equals(mid2)) {
            return 0;
        }
        int a2 = a();
        int a3 = deviceInfo.a();
        if (a2 > a3) {
            return 1;
        }
        if (a2 != a3) {
            return -1;
        }
        long b2 = b();
        long b3 = deviceInfo.b();
        if (b2 <= b3) {
            return b2 == b3 ? 0 : -1;
        }
        return 1;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
        this.f = i;
    }

    /* access modifiers changed from: 0000 */
    public void a(long j) {
        this.g = j;
    }

    /* access modifiers changed from: 0000 */
    public long b() {
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public void b(int i) {
        this.e = i;
    }

    /* access modifiers changed from: 0000 */
    public void b(String str) {
        this.c = str;
    }

    /* access modifiers changed from: 0000 */
    public JSONObject c() {
        JSONObject jSONObject = new JSONObject();
        try {
            k.a(jSONObject, TAG_IMEI, this.a);
            k.a(jSONObject, TAG_MAC, this.b);
            k.a(jSONObject, TAG_MID, this.d);
            k.a(jSONObject, TAG_ANDROID_ID, this.c);
            jSONObject.put(TAG_TIMESTAMPS, this.g);
            jSONObject.put(TAG_VERSION, this.f);
        } catch (JSONException e2) {
            h.e((Exception) e2);
        }
        return jSONObject;
    }

    /* access modifiers changed from: 0000 */
    public void c(String str) {
        this.d = str;
    }

    /* access modifiers changed from: 0000 */
    public void d(String str) {
        this.a = str;
    }

    /* access modifiers changed from: 0000 */
    public void e(String str) {
        this.b = str;
    }

    public String getImei() {
        return this.a;
    }

    public String getMac() {
        return this.b;
    }

    public String getMid() {
        return this.d;
    }

    public int getUserType() {
        return this.e;
    }

    public String toString() {
        return c().toString();
    }
}
