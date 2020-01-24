package com.youzan.sdk.web.plugin;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebBackForwardList;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.youzan.sdk.YouzanLog;
import com.youzan.sdk.tool.e;
import com.youzan.sdk.tool.f;
import com.youzan.sdk.tool.i;
import com.youzan.sdk.web.bridge.d;
import java.lang.ref.WeakReference;
import java.util.Stack;

final class WebClientWrapper extends WebViewClient {

    /* renamed from: ˊ reason: contains not printable characters */
    private static int f396 = -9;

    /* renamed from: ˎ reason: contains not printable characters */
    private static final long f397 = 3000;

    /* renamed from: ʻ reason: contains not printable characters */
    private WeakReference<Activity> f398;

    /* renamed from: ʼ reason: contains not printable characters */
    private boolean f399 = false;

    /* renamed from: ʽ reason: contains not printable characters */
    private String f400;

    /* renamed from: ˋ reason: contains not printable characters */
    private long f401 = 0;

    /* renamed from: ˏ reason: contains not printable characters */
    private final Stack<String> f402 = new Stack<>();

    /* renamed from: ᐝ reason: contains not printable characters */
    private WebViewClient f403;

    public WebClientWrapper(WebView webView) {
        Context context = webView.getContext();
        if (context instanceof Activity) {
            this.f398 = new WeakReference<>((Activity) context);
        }
    }

    public void setDelegate(WebViewClient delegate) {
        if (!(delegate instanceof WebClientWrapper)) {
            this.f403 = delegate;
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private void m166(String url) {
        if (!TextUtils.isEmpty(url) && !url.equals(getUrl())) {
            if (i.m136(url)) {
                this.f402.push(url);
            } else if (!TextUtils.isEmpty(this.f400)) {
                this.f402.push(this.f400);
                this.f400 = null;
            }
        }
    }

    @Nullable
    public String getUrl() {
        if (this.f402.size() > 0) {
            return (String) this.f402.peek();
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: ˊ reason: contains not printable characters */
    public String m167() {
        if (this.f402.size() > 0) {
            return (String) this.f402.pop();
        }
        return null;
    }

    public boolean pageCanGoBack() {
        return this.f402.size() >= 2;
    }

    public final boolean pageGoBack(@NonNull WebView webView) {
        if (pageCanGoBack()) {
            String url = popBackUrl();
            if (!TextUtils.isEmpty(url)) {
                webView.loadUrl(url);
                return true;
            }
        }
        return false;
    }

    @Nullable
    public final String popBackUrl() {
        if (this.f402.size() < 2) {
            return null;
        }
        this.f402.pop();
        return (String) this.f402.pop();
    }

    /* access modifiers changed from: protected */
    @Nullable
    /* renamed from: ˋ reason: contains not printable characters */
    public final Activity m168() {
        if (this.f398 != null) {
            return (Activity) this.f398.get();
        }
        return null;
    }

    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (this.f403 != null) {
            this.f403.doUpdateVisitedHistory(view, url, isReload);
        } else {
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (this.f403 != null) {
            this.f403.onFormResubmission(view, dontResend, resend);
        } else {
            super.onFormResubmission(view, dontResend, resend);
        }
    }

    public void onLoadResource(WebView view, String url) {
        if (this.f403 != null) {
            this.f403.onLoadResource(view, url);
        } else {
            super.onLoadResource(view, url);
        }
    }

    @TargetApi(23)
    public void onPageCommitVisible(WebView view, String url) {
        if (this.f403 != null) {
            this.f403.onPageCommitVisible(view, url);
        } else {
            super.onPageCommitVisible(view, url);
        }
    }

    public void onPageFinished(WebView view, String url) {
        if (this.f399) {
            this.f399 = false;
            YouzanLog.i("Inject on page finished");
            d.m153(view, url, false);
        }
        if (this.f403 != null) {
            this.f403.onPageFinished(view, url);
        }
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.f399 && this.f402.size() > 0) {
            this.f400 = (String) this.f402.pop();
        }
        m166(url);
        this.f399 = true;
        if (this.f403 != null) {
            this.f403.onPageStarted(view, url, favicon);
        }
    }

    @TargetApi(21)
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (this.f403 != null) {
            this.f403.onReceivedClientCertRequest(view, request);
        } else {
            super.onReceivedClientCertRequest(view, request);
        }
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (VERSION.SDK_INT < 23 && errorCode == f396) {
            m165(view);
        } else if (this.f403 != null) {
            this.f403.onReceivedError(view, errorCode, description, failingUrl);
        } else {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    @TargetApi(23)
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (VERSION.SDK_INT >= 23 && error != null && error.getErrorCode() == f396) {
            m165(view);
        } else if (this.f403 != null) {
            this.f403.onReceivedError(view, request, error);
        } else {
            super.onReceivedError(view, request, error);
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private void m165(WebView view) {
        if (System.currentTimeMillis() - this.f401 > f397) {
            this.f401 = System.currentTimeMillis();
            f.m117(view.getContext());
            view.reload();
        }
    }

    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (this.f403 != null) {
            this.f403.onReceivedHttpAuthRequest(view, handler, host, realm);
        } else {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @TargetApi(23)
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (this.f403 != null) {
            this.f403.onReceivedHttpError(view, request, errorResponse);
        } else {
            super.onReceivedHttpError(view, request, errorResponse);
        }
    }

    @TargetApi(12)
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        if (this.f403 != null) {
            this.f403.onReceivedLoginRequest(view, realm, account, args);
        } else {
            super.onReceivedLoginRequest(view, realm, account, args);
        }
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (this.f403 != null) {
            this.f403.onReceivedSslError(view, handler, error);
        } else {
            super.onReceivedSslError(view, handler, error);
        }
    }

    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (this.f403 != null) {
            this.f403.onScaleChanged(view, oldScale, newScale);
        } else {
            super.onScaleChanged(view, oldScale, newScale);
        }
    }

    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (this.f403 != null) {
            this.f403.onTooManyRedirects(view, cancelMsg, continueMsg);
        } else {
            super.onTooManyRedirects(view, cancelMsg, continueMsg);
        }
    }

    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (this.f403 != null) {
            this.f403.onUnhandledKeyEvent(view, event);
        } else {
            super.onUnhandledKeyEvent(view, event);
        }
    }

    @TargetApi(21)
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (this.f403 != null) {
            return this.f403.shouldInterceptRequest(view, request);
        }
        return super.shouldInterceptRequest(view, request);
    }

    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (this.f403 != null) {
            return this.f403.shouldInterceptRequest(view, url);
        }
        return super.shouldInterceptRequest(view, url);
    }

    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (this.f403 != null) {
            return this.f403.shouldOverrideKeyEvent(view, event);
        }
        return super.shouldOverrideKeyEvent(view, event);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        boolean result;
        Context context = view.getContext();
        d.m152();
        if (TextUtils.isEmpty(url)) {
            return super.shouldOverrideUrlLoading(view, url);
        }
        Uri uri = Uri.parse(url);
        WebBackForwardList stack = view.copyBackForwardList();
        if (stack != null && stack.getSize() > 0 && stack.getCurrentItem() != null && (url.equals(stack.getCurrentItem().getOriginalUrl()) || url.equals(i.m128(uri)))) {
            return false;
        }
        if (e.m116(uri.getScheme())) {
            return e.m115(context, uri);
        }
        if (i.m134(uri.getHost())) {
            return false;
        }
        if (this.f403 == null || !this.f403.shouldOverrideUrlLoading(view, url)) {
            result = false;
        } else {
            result = true;
        }
        if (result || e.m112(context, uri)) {
            return true;
        }
        return false;
    }
}
