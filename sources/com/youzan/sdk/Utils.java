package com.youzan.sdk;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;
import com.youzan.sdk.tool.b.a;
import com.youzan.sdk.tool.e;
import com.youzan.sdk.tool.f;
import com.youzan.sdk.tool.i;

public final class Utils {
    public static boolean dealWAPWxPay(Activity activity, String url) {
        return e.m115(activity, TextUtils.isEmpty(url) ? null : Uri.parse(url));
    }

    public static void initWebViewParameter(WebView webView) {
        i.m129(webView);
    }

    public static void clearCookie(Context context) {
        a.m81(context);
    }

    public static void clearLocalStorage() {
        a.m75();
    }

    public static boolean isYouzanPage(String url) {
        return i.m136(url);
    }

    public static boolean isYouzanHost(@NonNull String host) {
        return i.m138(host);
    }

    public static void sync(Context context, YouzanToken token) {
        f.m118(context, token);
    }

    public static boolean isTokenInactive(@Nullable String msg) {
        return msg != null && (msg.contains("40009") || msg.contains("40010") || msg.contains("42000"));
    }

    public static int generateRequestId() {
        return com.youzan.sdk.tool.a.m66();
    }

    public static void copyText(Context context, String content) {
        com.youzan.sdk.tool.a.m68(context, content);
    }

    public static boolean isNetworkConnect(Context context) {
        return com.youzan.sdk.tool.a.m72(context);
    }
}
