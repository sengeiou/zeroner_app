package com.iwown.my_module.strava;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;

public class StravaLoginActivity extends BaseActivity {
    public static final String EXTRA_LOGIN_URL = "StravaLoginActivity.EXTRA_LOGIN_URL";
    public static final String EXTRA_REDIRECT_URL = "StravaLoginActivity.EXTRA_REDIRECT_URL";
    public static final String RESULT_CODE = "StravaLoginActivity.RESULT_CODE";
    private static final String TAG = "StravaLoginActivity";
    private WebView loginWebview;
    /* access modifiers changed from: private */
    public ProgressBar pr1;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_strava_login);
        setLeftBackTo();
        setTitleText(getString(R.string.strava_authorize_view));
        this.loginWebview = (WebView) findViewById(R.id.login_webview);
        this.pr1 = (ProgressBar) findViewById(R.id.webview_progress);
        this.pr1.setProgress(2);
        this.pr1.setVisibility(0);
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
                if (!uri.toString().startsWith(StravaLoginActivity.this.getIntent().getStringExtra("StravaLoginActivity.EXTRA_REDIRECT_URL"))) {
                    return false;
                }
                String code = uri.getQueryParameter("code");
                Log.i(StravaLoginActivity.TAG, code);
                return makeResult(code);
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
        this.loginWebview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    StravaLoginActivity.this.pr1.setVisibility(8);
                    return;
                }
                StravaLoginActivity.this.pr1.setVisibility(0);
                StravaLoginActivity.this.pr1.setProgress(newProgress);
            }
        });
    }

    private void loadLoginURL() {
        this.loginWebview.loadUrl(getIntent().getStringExtra("StravaLoginActivity.EXTRA_LOGIN_URL"));
    }
}
