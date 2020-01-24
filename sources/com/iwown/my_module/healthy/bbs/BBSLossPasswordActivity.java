package com.iwown.my_module.healthy.bbs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.iwown.my_module.R;
import com.socks.library.KLog;
import java.lang.reflect.Field;

public class BBSLossPasswordActivity extends Activity {
    private static final int FILECHOOSER_RESULTCODE = 1;
    ProgressBar mProgressBar;
    ValueCallback<Uri[]> mUploadCallbackAboveL;
    /* access modifiers changed from: private */
    public ValueCallback<Uri> mUploadMessage;
    private WebView webView;

    public class HWebChromeClient extends WebChromeClient {
        public HWebChromeClient() {
        }

        public void onProgressChanged(WebView view, int progress) {
            if (progress == 100) {
                BBSLossPasswordActivity.this.mProgressBar.setVisibility(8);
            } else {
                BBSLossPasswordActivity.this.mProgressBar.setVisibility(0);
            }
            if (BBSLossPasswordActivity.this.mProgressBar != null) {
                BBSLossPasswordActivity.this.mProgressBar.setProgress(progress);
            }
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            KLog.d("openFileChoose(ValueCallback<Uri> uploadMsg)");
            BBSLossPasswordActivity.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            BBSLossPasswordActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), 1);
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            openFileChooser(uploadMsg);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg);
        }

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            BBSLossPasswordActivity.this.mUploadCallbackAboveL = filePathCallback;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            BBSLossPasswordActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), 1);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs2);
        setConfigCallback((WindowManager) getApplicationContext().getSystemService("window"));
        initView();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initView() {
        this.webView = (WebView) findViewById(R.id.bbs_web_view);
        this.webView.getSettings().setJavaScriptEnabled(true);
        WebSettings websettings = this.webView.getSettings();
        websettings.setBuiltInZoomControls(false);
        websettings.setUseWideViewPort(true);
        websettings.setAppCacheEnabled(true);
        websettings.setDatabaseEnabled(true);
        websettings.setDomStorageEnabled(true);
        websettings.setCacheMode(-1);
        websettings.setLoadWithOverviewMode(true);
        websettings.setDefaultTextEncodingName("utf-8");
        this.mProgressBar = (ProgressBar) findViewById(R.id.progressbar_2);
        websettings.setCacheMode(2);
        this.webView.clearHistory();
        this.webView.clearFormData();
        this.webView.clearCache(true);
        CookieSyncManager createInstance = CookieSyncManager.createInstance(this.webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
        String url = "http://www.iwown.com/bbs/member.php?mod=logging&action=login";
        KLog.e("url " + url);
        this.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        this.webView.setWebChromeClient(new HWebChromeClient());
        this.webView.loadUrl(url);
    }

    public void setConfigCallback(WindowManager windowManager) {
        try {
            Field field = WebView.class.getDeclaredField("mWebViewCore").getType().getDeclaredField("mBrowserFrame").getType().getDeclaredField("sConfigCallback");
            field.setAccessible(true);
            Object configCallback = field.get(null);
            if (configCallback != null) {
                Field field2 = field.getType().getDeclaredField("mWindowManager");
                field2.setAccessible(true);
                field2.set(configCallback, windowManager);
            }
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1) {
            return;
        }
        if (this.mUploadMessage != null || this.mUploadCallbackAboveL != null) {
            Object data2 = (data == null || resultCode != -1) ? null : data.getData();
            if (this.mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (this.mUploadMessage != null) {
                this.mUploadMessage.onReceiveValue(data2);
                this.mUploadMessage = null;
            }
        }
    }

    @TargetApi(21)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && this.mUploadCallbackAboveL != null) {
            Uri[] results = null;
            if (resultCode == -1 && data != null) {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        results[i] = clipData.getItemAt(i).getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
            this.mUploadCallbackAboveL.onReceiveValue(results);
            this.mUploadCallbackAboveL = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.webView.clearCache(true);
        this.webView.removeAllViews();
        this.webView.destroy();
        this.webView.setVisibility(8);
        setConfigCallback(null);
        super.onDestroy();
    }
}
