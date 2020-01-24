package com.youzan.sdk.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public final class Preference {

    /* renamed from: ʻ reason: contains not printable characters */
    private static final String f304 = "shop.url";

    /* renamed from: ʼ reason: contains not printable characters */
    private static final String f305 = "shop.cert_type";

    /* renamed from: ʽ reason: contains not printable characters */
    private static final String f306 = "token.access_token";

    /* renamed from: ʾ reason: contains not printable characters */
    private static final String f307 = "config.image_loader";

    /* renamed from: ʿ reason: contains not printable characters */
    private static Preference f308 = null;

    /* renamed from: ˊ reason: contains not printable characters */
    private static final String f309 = "com.youzan.open.sdk.preferences";

    /* renamed from: ˋ reason: contains not printable characters */
    private static final String f310 = "!@INVALID!@";

    /* renamed from: ˎ reason: contains not printable characters */
    private static final String f311 = "shop.sid";

    /* renamed from: ˏ reason: contains not printable characters */
    private static final String f312 = "shop.name";

    /* renamed from: ͺ reason: contains not printable characters */
    private static final String f313 = "token.cookie_key";

    /* renamed from: ι reason: contains not printable characters */
    private static final String f314 = "token.cookie_value";

    /* renamed from: ᐝ reason: contains not printable characters */
    private static final String f315 = "shop.logo";

    /* renamed from: ˈ reason: contains not printable characters */
    private final Bundle f316 = new Bundle();
    @Nullable

    /* renamed from: ˉ reason: contains not printable characters */
    private SharedPreferences f317;
    @Nullable

    /* renamed from: ˌ reason: contains not printable characters */
    private Editor f318;

    public static class Config {
        public static String getImageLoadAdapter() {
            return Preference.instance().m58(Preference.f307, (String) null);
        }

        public static void setImageLoadAdapter(String value) {
            Preference.instance().m65(Preference.f307, value);
        }

        public static void clear(Context context) {
            Preference.renew(context);
            setImageLoadAdapter(null);
        }
    }

    public static class Shop {
        public static String getSid() {
            return Preference.instance().m58(Preference.f311, (String) null);
        }

        public static void setSid(String value) {
            Preference.instance().m65(Preference.f311, value);
        }

        public static String getShopName() {
            return Preference.instance().m58(Preference.f312, (String) null);
        }

        public static void setShopName(String value) {
            Preference.instance().m65(Preference.f312, value);
        }

        public static String getShopLogo() {
            return Preference.instance().m58(Preference.f315, (String) null);
        }

        public static void setShopLogo(String value) {
            Preference.instance().m65(Preference.f315, value);
        }

        public static String getShopUrl() {
            return Preference.instance().m58(Preference.f304, (String) null);
        }

        public static void setShopUrl(String value) {
            Preference.instance().m65(Preference.f304, value);
        }

        public static int getShopCertType() {
            return Preference.instance().m55(Preference.f305, 0);
        }

        public static void setShopCertType(int value) {
            Preference.instance().m63(Preference.f305, value);
        }

        public static boolean isValid(String sid) {
            return TextUtils.equals(sid, getSid()) && !TextUtils.isEmpty(getShopUrl());
        }

        public static void clear(Context context) {
            Preference.renew(context);
            setSid(null);
            setShopName(null);
            setShopLogo(null);
            setShopUrl(null);
            setShopCertType(Integer.MIN_VALUE);
        }
    }

    public static class Token {
        public static String getAccessToken() {
            return Preference.instance().m58(Preference.f306, (String) null);
        }

        public static void setAccessToken(String value) {
            Preference.instance().m65(Preference.f306, value);
        }

        public static String geCookieKey() {
            return Preference.instance().m58(Preference.f313, (String) null);
        }

        public static void setCookieKey(String value) {
            Preference.instance().m65(Preference.f313, value);
        }

        public static String geCookieValue() {
            return Preference.instance().m58(Preference.f314, (String) null);
        }

        public static void setCookieValue(String value) {
            Preference.instance().m65(Preference.f314, value);
        }

        public static void clear(Context context) {
            Preference.renew(context);
            setAccessToken(null);
            setCookieKey(null);
            setCookieValue(null);
        }
    }

    private Preference() {
    }

    public static Preference instance() {
        if (f308 == null) {
            synchronized (Preference.class) {
                if (f308 == null) {
                    f308 = new Preference();
                }
            }
        }
        return f308;
    }

    public static void renew(Context context) {
        if (!instance().m60() && context != null) {
            instance().init(context);
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private boolean m60() {
        return (this.f317 == null || this.f318 == null) ? false : true;
    }

    public void init(Context context) {
        Context appCtx = context.getApplicationContext();
        if (appCtx != null) {
            context = appCtx;
        }
        this.f317 = context.getSharedPreferences(f309, 0);
        this.f318 = this.f317.edit();
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private void m59(String key) {
        this.f316.remove(key);
        if (m60()) {
            this.f318.remove(key).commit();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: ˊ reason: contains not printable characters */
    public String m58(String key, String defaultValue) {
        String value;
        String value2 = this.f316.getString(key, f310);
        if (!f310.equals(value2)) {
            return value2;
        }
        if (m60()) {
            value = this.f317.getString(key, defaultValue);
            this.f316.putString(key, value);
        } else {
            value = null;
        }
        return value;
    }

    /* access modifiers changed from: private */
    /* renamed from: ˋ reason: contains not printable characters */
    public void m65(String key, String value) {
        if (value == null) {
            m59(key);
            return;
        }
        if (m60()) {
            this.f318.putString(key, value);
            this.f318.commit();
        }
        this.f316.putString(key, value);
    }

    /* access modifiers changed from: private */
    /* renamed from: ˊ reason: contains not printable characters */
    public int m55(String key, int defaultValue) {
        int value;
        int value2 = this.f316.getInt(key, Integer.MIN_VALUE);
        if (value2 != Integer.MIN_VALUE) {
            return value2;
        }
        if (m60()) {
            value = this.f317.getInt(key, defaultValue);
            this.f316.putInt(key, value);
        } else {
            value = 0;
        }
        return value;
    }

    /* access modifiers changed from: private */
    /* renamed from: ˋ reason: contains not printable characters */
    public void m63(String key, int value) {
        if (value == Integer.MIN_VALUE) {
            m59(key);
            return;
        }
        if (m60()) {
            this.f318.putInt(key, value);
            this.f318.commit();
        }
        this.f316.putInt(key, value);
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private long m56(String key, long defaultValue) {
        long value;
        long value2 = this.f316.getLong(key, Long.MIN_VALUE);
        if (value2 != Long.MIN_VALUE) {
            return value2;
        }
        if (m60()) {
            value = this.f317.getLong(key, defaultValue);
            this.f316.putLong(key, value);
        } else {
            value = 0;
        }
        return value;
    }

    /* renamed from: ˋ reason: contains not printable characters */
    private void m64(String key, long value) {
        if (value == Long.MIN_VALUE) {
            m59(key);
            return;
        }
        if (m60()) {
            this.f318.putLong(key, value);
            this.f318.commit();
        }
        this.f316.putLong(key, value);
    }
}
