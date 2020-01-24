package com.iwown.my_module.healthy.bbs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.google.gson.Gson;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.network.request.BBSAccount;
import com.socks.library.KLog;

public class BBSActivity extends BaseActivity {
    private static final int FILECHOOSER_RESULTCODE = 1;
    public static final String RECEIVER_ACTION_FINISH_A = "receiver_bbs_finish";
    ProgressBar mProgressBar;
    private BBSFinishReceiver mRecevier;
    ValueCallback<Uri[]> mUploadCallbackAboveL;
    /* access modifiers changed from: private */
    public ValueCallback<Uri> mUploadMessage;
    private WebView webView;

    public class BBSFinishReceiver extends BroadcastReceiver {
        public BBSFinishReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            BBSActivity.this.finish();
            BBSActivity.this.startActivity(new Intent(BBSActivity.this.getApplicationContext(), BBSActivity.class));
        }
    }

    public class HWebChromeClient extends WebChromeClient {
        public HWebChromeClient() {
        }

        public void onProgressChanged(WebView view, int progress) {
            if (progress == 100) {
                BBSActivity.this.mProgressBar.setVisibility(8);
            } else {
                BBSActivity.this.mProgressBar.setVisibility(0);
            }
            if (BBSActivity.this.mProgressBar != null) {
                BBSActivity.this.mProgressBar.setProgress(progress);
            }
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            KLog.d("openFileChoose(ValueCallback<Uri> uploadMsg)");
            BBSActivity.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            BBSActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), 1);
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            openFileChooser(uploadMsg);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg);
        }

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            BBSActivity.this.mUploadCallbackAboveL = filePathCallback;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            BBSActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), 1);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs2);
        setTitleText("埃微论坛");
        this.mRecevier = new BBSFinishReceiver();
        registerFinishReciver();
        setRightText("切换账号", new OnClickListener() {
            public void onClick(View v) {
                BBSActivity.this.startActivity(new Intent(BBSActivity.this, BBSRegisterActivity.class));
            }
        });
        setLeftBtn(new OnClickListener() {
            public void onClick(View v) {
                BBSActivity.this.finish();
            }
        });
        initView();
    }

    public void back() {
        super.back();
    }

    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        }
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
        BBSAccount account = (BBSAccount) new Gson().fromJson(new HealthySharedUtil(this).getBBsAccount(), BBSAccount.class);
        String name = null;
        String password = null;
        if (account != null) {
            name = account.getUserName();
            password = account.getPassword();
        }
        String url = "http://iwown.com:9090/ucenterService1/discuz.jsp?userName=" + name + "&password=" + password + "";
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
        if (this.webView != null) {
            this.webView.getSettings().setJavaScriptEnabled(false);
            try {
                this.webView.destroy();
                this.webView = null;
            } catch (Throwable th) {
            }
        }
        if (this.mRecevier != null) {
            unregisterReceiver(this.mRecevier);
        }
        super.onDestroy();
    }

    private void registerFinishReciver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION_FINISH_A);
        registerReceiver(this.mRecevier, intentFilter);
    }
}
