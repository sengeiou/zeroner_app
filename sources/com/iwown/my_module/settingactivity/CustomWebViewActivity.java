package com.iwown.my_module.settingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.socks.library.KLog;

@Route(path = "/my/AppBackgroundActivity")
public class CustomWebViewActivity extends BaseActivity {
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (TextUtils.isEmpty(CustomWebViewActivity.this.mUrl)) {
                CustomWebViewActivity.this.mWebView.loadUrl("");
            } else {
                CustomWebViewActivity.this.mWebView.loadUrl(CustomWebViewActivity.this.mUrl);
            }
        }
    };
    private TextView mBtnBack;
    private TextView mCloseView;
    private String mTitle;
    private TextView mTitleView;
    /* access modifiers changed from: private */
    public String mUrl;
    /* access modifiers changed from: private */
    public WebView mWebView;
    /* access modifiers changed from: private */
    public ProgressBar pr1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_webview);
        this.mTitleBar.setVisibility(8);
        Intent intent = getIntent();
        this.mUrl = intent.getStringExtra("url");
        this.mTitle = intent.getStringExtra("title");
        this.mTitleView = (TextView) findViewById(R.id.web_title);
        this.mTitleView.setText(this.mTitle);
        KLog.i("webView_url:" + this.mUrl);
        this.mBtnBack = (TextView) findViewById(R.id.button_backward);
        this.mCloseView = (TextView) findViewById(R.id.button_close);
        this.mWebView = (WebView) findViewById(R.id.iwown_privae_view);
        this.pr1 = (ProgressBar) findViewById(R.id.webview_progress);
        this.pr1.setProgress(2);
        this.pr1.setVisibility(0);
        init();
        new Thread(new Runnable() {
            public void run() {
                CustomWebViewActivity.this.handler.sendEmptyMessage(1);
            }
        }).start();
    }

    private void init() {
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setSupportZoom(true);
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        this.mWebView.getSettings().setDisplayZoomControls(true);
        this.mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    CustomWebViewActivity.this.pr1.setVisibility(8);
                    return;
                }
                CustomWebViewActivity.this.pr1.setVisibility(0);
                CustomWebViewActivity.this.pr1.setProgress(newProgress);
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                CustomWebViewActivity.this.raiseErrorNotice(CustomWebViewActivity.this.getString(R.string.network_error));
            }
        });
        this.mBtnBack.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (CustomWebViewActivity.this.mWebView.canGoBack()) {
                    CustomWebViewActivity.this.mWebView.goBack();
                }
            }
        });
        this.mCloseView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CustomWebViewActivity.this.back();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.mWebView != null) {
            this.mWebView.destroy();
        }
    }

    public void back() {
        super.back();
    }
}
