package com.youzan.sdk.tool;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.youzan.sdk.YouzanLog;

/* compiled from: WebParameter */
public final class i {

    /* renamed from: ʻ reason: contains not printable characters */
    private static final String[] f370 = {"tenpay.com", "alipay.com", "qq.com"};

    /* renamed from: ˊ reason: contains not printable characters */
    private static final String f371 = "redirect_uri";

    /* renamed from: ˋ reason: contains not printable characters */
    private static final String f372 = "koudaitong.com";

    /* renamed from: ˎ reason: contains not printable characters */
    private static final String f373 = "youzan.com";

    /* renamed from: ˏ reason: contains not printable characters */
    private static final String f374 = "kdt.im";

    /* renamed from: ᐝ reason: contains not printable characters */
    private static final String f375 = "database";

    @SuppressLint({"SetJavaScriptEnabled"})
    /* renamed from: ˊ reason: contains not printable characters */
    public static void m129(@Nullable WebView webView) {
        if (webView != null) {
            webView.setOverScrollMode(2);
            try {
                Context context = webView.getContext();
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setSavePassword(false);
                settings.setSaveFormData(false);
                settings.setDomStorageEnabled(true);
                settings.setDatabaseEnabled(true);
                if (VERSION.SDK_INT < 19) {
                    settings.setDatabasePath(context.getApplicationContext().getDir(f375, 0).getPath());
                }
                if (VERSION.SDK_INT >= 21) {
                    settings.setMixedContentMode(0);
                }
                settings.setGeolocationEnabled(true);
                settings.setGeolocationDatabasePath(context.getFilesDir().getPath());
            } catch (Throwable e) {
                YouzanLog.w("WARNING: init WebView Failed");
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    /* renamed from: ˋ reason: contains not printable characters */
    public static void m133(@Nullable WebView web) {
        if (web != null) {
            web.removeJavascriptInterface("searchBoxJavaBridge_");
            web.removeJavascriptInterface("accessibility");
            web.removeJavascriptInterface("accessibilityTraversal");
        }
    }

    @TargetApi(19)
    /* renamed from: ˊ reason: contains not printable characters */
    public static void m131(boolean debug2) {
        if (VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(debug2);
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static void m130(@Nullable WebView webView, String UA, String appVersion) {
        if (TextUtils.isEmpty(UA) || webView == null) {
            YouzanLog.w("UserAgent Is Null");
            return;
        }
        if (appVersion == null) {
            appVersion = "";
        }
        WebSettings settings = webView.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + UA + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + appVersion);
    }

    @Nullable
    /* renamed from: ˎ reason: contains not printable characters */
    public static String m135(WebView webView) {
        WebBackForwardList list = webView.copyBackForwardList();
        int curIndex = list.getCurrentIndex();
        int preIndex = curIndex > 0 ? curIndex - 1 : -1;
        if (preIndex < 0) {
            return null;
        }
        WebHistoryItem item = list.getItemAtIndex(preIndex);
        if (item != null) {
            return item.getUrl();
        }
        return null;
    }

    /* renamed from: ˏ reason: contains not printable characters */
    public static boolean m137(WebView webView) {
        WebBackForwardList list = webView.copyBackForwardList();
        int curIndex = list.getCurrentIndex();
        int preIndex = curIndex > 0 ? curIndex - 1 : -1;
        if (preIndex < 0) {
            return false;
        }
        WebHistoryItem item = list.getItemAtIndex(preIndex);
        if (item == null) {
            return false;
        }
        if (!m132(item.getUrl()) || preIndex != 0) {
            return true;
        }
        return false;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static boolean m132(@Nullable String url) {
        if (TextUtils.isEmpty(url)) {
            return true;
        }
        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        if (!TextUtils.isEmpty(m128(uri)) || TextUtils.isEmpty(host) || m134(host)) {
            return true;
        }
        return false;
    }

    @Nullable
    /* renamed from: ˊ reason: contains not printable characters */
    public static String m128(@NonNull Uri uri) {
        if (uri.isOpaque()) {
            return null;
        }
        return uri.getQueryParameter(f371);
    }

    /* renamed from: ˋ reason: contains not printable characters */
    public static boolean m134(@Nullable String host) {
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        for (String item : f370) {
            if (host.contains(item)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: ˎ reason: contains not printable characters */
    public static boolean m136(@Nullable String url) {
        if (!TextUtils.isEmpty(url)) {
            return m138(Uri.parse(url).getHost());
        }
        return false;
    }

    /* renamed from: ˏ reason: contains not printable characters */
    public static boolean m138(String host) {
        return !TextUtils.isEmpty(host) && (host.contains(f373) || host.contains(f372) || host.contains(f374));
    }
}
