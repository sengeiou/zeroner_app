package com.youzan.sdk.web.bridge;

import android.text.TextUtils;
import android.webkit.WebView;
import com.youzan.sdk.tool.i;

/* compiled from: Injector */
public class d {

    /* renamed from: ˊ reason: contains not printable characters */
    private static final String f381 = "javascript:var isReadyForYouZanJSBridge=true;";

    /* renamed from: ˋ reason: contains not printable characters */
    private static String f382;

    /* renamed from: ˎ reason: contains not printable characters */
    private static String f383;

    /* renamed from: ˊ reason: contains not printable characters */
    public static void m152() {
        f382 = null;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static void m153(WebView web, String url, boolean keep) {
        if (!TextUtils.equals(url, f382) && i.m136(url)) {
            if (!keep) {
                url = null;
            }
            f382 = url;
            if (f383 == null) {
                f383 = new b().m141();
            }
            web.loadUrl(f383);
            web.loadUrl(f381);
        }
    }
}
