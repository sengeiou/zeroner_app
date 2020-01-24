package com.youzan.sdk.web.bridge;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import com.youzan.sdk.event.EventAPI;

/* compiled from: BridgeDispatcher */
public class a extends AbsDispatcher {

    /* renamed from: ˊ reason: contains not printable characters */
    private final WebView f376;

    public a(WebView webView) {
        this.f376 = webView;
    }

    public boolean dispatch(String method, String json) {
        Event action = get(method);
        if (action != null) {
            if (EventAPI.EVENT_AUTHENTICATION.equals(method)) {
                json = EventAPI.SIGN_NEED_LOGIN;
            }
            Context context = this.f376.getContext();
            if ((context instanceof Activity) && !((Activity) context).isFinishing()) {
                runOnUi(new HandlerRunnable(action, this.f376, json));
                return true;
            }
        } else if (EventAPI.EVENT_PAGE_READY.equals(method) || EventAPI.EVENT_AUTHENTICATION.equals(method) || EventAPI.EVENT_SHARE.equals(method)) {
            return true;
        }
        return false;
    }
}
