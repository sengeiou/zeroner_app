package com.sweetzpot.stravazpot.authenticaton.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.iwown.data_link.R;

public class StravaLoginActivity extends AppCompatActivity {
    public static final String EXTRA_LOGIN_URL = "StravaLoginActivity.EXTRA_LOGIN_URL";
    public static final String EXTRA_REDIRECT_URL = "StravaLoginActivity.EXTRA_REDIRECT_URL";
    public static final String RESULT_CODE = "StravaLoginActivity.RESULT_CODE";
    private WebView loginWebview;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_link_activity_strava_login);
        this.loginWebview = (WebView) findViewById(R.id.login_webview);
        configureWebViewClient();
        loadLoginURL();
    }

    private void configureWebViewClient() {
        this.loginWebview.getSettings().setJavaScriptEnabled(true);
        this.loginWebview.getSettings().setUserAgentString("Mozilla/5.0 Google");
        this.loginWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleUrl(Uri.parse(url)) || super.shouldOverrideUrlLoading(view, url);
            }

            @TargetApi(24)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return handleUrl(request.getUrl()) || super.shouldOverrideUrlLoading(view, request);
            }

            private boolean handleUrl(Uri uri) {
                if (uri.toString().startsWith(StravaLoginActivity.this.getIntent().getStringExtra("StravaLoginActivity.EXTRA_REDIRECT_URL"))) {
                    return makeResult(uri.getQueryParameter("code"));
                }
                return false;
            }

            private boolean makeResult(String code) {
                if (code == null || code.isEmpty()) {
                    StravaLoginActivity.this.finish();
                    return false;
                }
                Intent result = new Intent();
                result.putExtra("StravaLoginActivity.RESULT_CODE", code);
                StravaLoginActivity.this.setResult(-1, result);
                StravaLoginActivity.this.finish();
                return true;
            }
        });
    }

    private void loadLoginURL() {
        this.loginWebview.loadUrl(getIntent().getStringExtra("StravaLoginActivity.EXTRA_LOGIN_URL"));
    }
}
