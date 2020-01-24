package com.youzan.sdk.tool;

import android.webkit.WebView;
import com.youzan.sdk.YouzanLog;

/* compiled from: Javascript */
public final class d {

    /* renamed from: ˊ reason: contains not printable characters */
    private static final String f354 = "javascript:window.YouzanJSBridge.trigger('share')";

    /* renamed from: ˊ reason: contains not printable characters */
    public static void m109(WebView webView) {
        if (webView != null) {
            webView.loadUrl(f354);
        } else {
            YouzanLog.e("WebView Is Null On sharePage");
        }
    }
}
