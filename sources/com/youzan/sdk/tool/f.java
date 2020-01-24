package com.youzan.sdk.tool;

import android.content.Context;
import com.youzan.sdk.YouzanToken;
import com.youzan.sdk.tool.Preference.Token;
import com.youzan.sdk.tool.b.C0013b;
import com.youzan.sdk.tool.b.a;

/* compiled from: SessionManager */
public final class f {

    /* renamed from: ˊ reason: contains not printable characters */
    public static String f367 = null;

    /* renamed from: ˋ reason: contains not printable characters */
    public static String f368 = null;

    /* renamed from: ˎ reason: contains not printable characters */
    private static final String f369 = "kdtUnion_";

    private f() {
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static void m118(Context context, YouzanToken token) {
        C0013b.m84(context, token.getCookieKey(), token.getCookieValue());
        Token.setAccessToken(token.getAccessToken());
        Token.setCookieKey(token.getCookieKey());
        Token.setCookieValue(token.getCookieValue());
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static void m117(Context context) {
        a.m81(context);
        a.m75();
        Token.clear(context);
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static void m119(Context context, String clientId, boolean autoPrefix) {
        if (clientId != null) {
            if (autoPrefix && !clientId.toLowerCase().startsWith(f369.toLowerCase())) {
                clientId = f369 + clientId;
            }
            f367 = clientId;
            f368 = h.m127(context, f367);
        }
    }
}
