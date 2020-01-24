package com.youzan.sdk.web.bridge;

import android.app.Activity;
import android.webkit.WebView;

public interface IBridgeEnv {
    Activity getActivity();

    WebView getWebView();
}
