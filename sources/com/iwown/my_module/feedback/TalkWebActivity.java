package com.iwown.my_module.feedback;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;

public class TalkWebActivity extends BaseActivity {
    /* access modifiers changed from: private */
    public boolean isLoadUrl = false;
    WebView webView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_talkweb_main);
        setTitleText(getString(R.string.my_module_feedback_title));
        setLeftBackTo();
        this.webView = (WebView) findViewById(R.id.talk_web_view);
        this.webView.loadUrl(getIntent().getStringExtra("url"));
        this.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!TalkWebActivity.this.isLoadUrl) {
                    TalkWebActivity.this.isLoadUrl = true;
                    view.loadUrl(url);
                }
                super.onPageStarted(view, url, favicon);
            }
        });
    }
}
