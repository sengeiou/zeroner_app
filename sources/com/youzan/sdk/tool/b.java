package com.youzan.sdk.tool;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebStorage;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: HtmlStorage */
public final class b {

    /* renamed from: ʻ reason: contains not printable characters */
    private static final String f323 = "KDTSESSIONID";

    /* renamed from: ʼ reason: contains not printable characters */
    private static final String f324 = "nobody_sign";

    /* renamed from: ʽ reason: contains not printable characters */
    private static final String f325 = "Set-Cookie";

    /* renamed from: ˊ reason: contains not printable characters */
    private static final String f326 = "koudaitong.com";

    /* renamed from: ˋ reason: contains not printable characters */
    private static final String f327 = "youzan.com";

    /* renamed from: ˎ reason: contains not printable characters */
    private static final String f328 = "youzan_user_id";

    /* renamed from: ˏ reason: contains not printable characters */
    private static final String f329 = "hide_app_topbar";

    /* renamed from: ͺ reason: contains not printable characters */
    private static final String f330 = ";";

    /* renamed from: ι reason: contains not printable characters */
    private static final String f331 = "Sat, 31 Dec 2016 23:59:59 GMT";

    /* renamed from: ᐝ reason: contains not printable characters */
    private static final String f332 = "alipay_installed";

    /* compiled from: HtmlStorage */
    public static class a {
        /* renamed from: ˊ reason: contains not printable characters */
        public static void m78(Context context, List<c> cookies) {
            if (context != null && cookies != null && cookies.size() > 0) {
                try {
                    CookieSyncManager.createInstance(context);
                    CookieManager manager = CookieManager.getInstance();
                    manager.setAcceptCookie(true);
                    for (c item : cookies) {
                        manager.setCookie("https://." + item.m89(), item.toString());
                    }
                    if (VERSION.SDK_INT >= 21) {
                        CookieManager.getInstance().flush();
                    } else {
                        CookieSyncManager.getInstance().sync();
                    }
                } catch (Throwable e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static void m79(Context context, c... cookies) {
            m78(context, Arrays.asList(cookies));
        }

        /* access modifiers changed from: private */
        /* renamed from: ˋ reason: contains not printable characters */
        public static List<c> m80(String key, String value) {
            List<c> cookies = new ArrayList<>(2);
            cookies.add(new com.youzan.sdk.tool.c.a().m105(b.f326).m102(key).m104(value).m106());
            cookies.add(new com.youzan.sdk.tool.c.a().m105(b.f327).m102(key).m104(value).m106());
            return cookies;
        }

        /* renamed from: ˊ reason: contains not printable characters */
        static void m77(Context context, String host) {
            try {
                CookieSyncManager.createInstance(context);
                CookieManager manager = CookieManager.getInstance();
                String httpsHost = "https://." + host;
                Set<String> filed = m74(manager.getCookie(httpsHost));
                if (filed != null) {
                    for (String item : filed) {
                        manager.setCookie(httpsHost, item + "=; Expires=" + b.f331);
                    }
                }
                if (VERSION.SDK_INT >= 21) {
                    CookieManager.getInstance().flush();
                } else {
                    CookieSyncManager.getInstance().sync();
                }
            } catch (Throwable th) {
            }
        }

        @Nullable
        /* renamed from: ˊ reason: contains not printable characters */
        private static Set<String> m74(String cookieString) {
            Set<String> filed = null;
            if (!TextUtils.isEmpty(cookieString)) {
                String[] cookies = cookieString.split(b.f330);
                filed = new HashSet<>(cookies.length);
                for (String item : cookies) {
                    if (item.contains("=")) {
                        filed.add(item.split("=", 2)[0].trim());
                    }
                }
            }
            return filed;
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static void m76(Context context) {
            m81(context);
            m75();
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static void m75() {
            try {
                WebStorage.getInstance().deleteAllData();
            } catch (Throwable e) {
                ThrowableExtension.printStackTrace(e);
            }
        }

        /* renamed from: ˋ reason: contains not printable characters */
        public static void m81(Context context) {
            m77(context, b.f326);
            m77(context, b.f327);
        }
    }

    /* renamed from: com.youzan.sdk.tool.b$b reason: collision with other inner class name */
    /* compiled from: HtmlStorage */
    public static class C0013b {
        /* renamed from: ˊ reason: contains not printable characters */
        public static void m84(Context context, String key, String value) {
            if ((!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) || "null".equalsIgnoreCase(key)) {
                a.m78(context, a.m80(key, value));
            }
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static void m85(Context context, boolean hide) {
            a.m78(context, a.m80(b.f329, hide ? "1" : "0"));
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static void m83(Context context, String value) {
            a.m78(context, a.m80(b.f323, value));
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static void m82(Context context) {
            a.m78(context, a.m80(b.f332, "1"));
        }

        /* renamed from: ˋ reason: contains not printable characters */
        public static void m86(Context context, String value) {
            a.m78(context, a.m80(b.f328, value));
        }
    }
}
