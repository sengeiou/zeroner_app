package com.youzan.sdk.web.event;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import com.youzan.sdk.web.bridge.Event;
import com.youzan.sdk.web.bridge.IBridgeEnv;

/* compiled from: CompactEvent */
abstract class a implements Event {
    public abstract void call(IBridgeEnv iBridgeEnv, String str);

    a() {
    }

    public final void call(final View view, String data) {
        call((IBridgeEnv) new IBridgeEnv() {
            public Activity getActivity() {
                Context context = view.getContext();
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                return null;
            }

            public WebView getWebView() {
                if (view instanceof WebView) {
                    return (WebView) view;
                }
                return null;
            }
        }, data);
    }
}
