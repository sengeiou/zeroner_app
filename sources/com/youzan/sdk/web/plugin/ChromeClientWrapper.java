package com.youzan.sdk.web.plugin;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.Keep;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.youzan.sdk.event.EventAPI;
import com.youzan.sdk.tool.a;
import com.youzan.sdk.web.bridge.Event;
import com.youzan.sdk.web.bridge.d;
import com.youzan.sdk.web.bridge.e;

final class ChromeClientWrapper extends WebChromeClient {

    /* renamed from: ʻ reason: contains not printable characters */
    private ValueCallback<Uri[]> f390;

    /* renamed from: ˊ reason: contains not printable characters */
    public final Integer f391 = Integer.valueOf(a.m66());

    /* renamed from: ˋ reason: contains not printable characters */
    private final com.youzan.sdk.web.bridge.a f392 = new com.youzan.sdk.web.bridge.a(this.f393);

    /* renamed from: ˎ reason: contains not printable characters */
    private final WebView f393;

    /* renamed from: ˏ reason: contains not printable characters */
    private WebChromeClient f394;

    /* renamed from: ᐝ reason: contains not printable characters */
    private ValueCallback<Uri> f395;

    public ChromeClientWrapper(WebView webView) {
        this.f393 = webView;
    }

    public void subscribe(Event subscribe) {
        this.f392.add(subscribe);
    }

    public void setDelegate(WebChromeClient delegate) {
        if (!(delegate instanceof ChromeClientWrapper)) {
            this.f394 = delegate;
        }
    }

    public Bitmap getDefaultVideoPoster() {
        return this.f394 != null ? this.f394.getDefaultVideoPoster() : super.getDefaultVideoPoster();
    }

    public View getVideoLoadingProgressView() {
        return this.f394 != null ? this.f394.getVideoLoadingProgressView() : super.getVideoLoadingProgressView();
    }

    public void getVisitedHistory(ValueCallback<String[]> callback) {
        if (this.f394 != null) {
            this.f394.getVisitedHistory(callback);
        } else {
            super.getVisitedHistory(callback);
        }
    }

    public void onCloseWindow(WebView window) {
        if (this.f394 != null) {
            this.f394.onCloseWindow(window);
        } else {
            super.onCloseWindow(window);
        }
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return this.f394 != null ? this.f394.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
    }

    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        if (this.f394 != null) {
            this.f394.onConsoleMessage(message, lineNumber, sourceID);
        } else {
            super.onConsoleMessage(message, lineNumber, sourceID);
        }
    }

    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        if (this.f394 != null) {
            return this.f394.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(5242880);
        if (this.f394 != null) {
            this.f394.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
        }
    }

    public void onGeolocationPermissionsHidePrompt() {
        if (this.f394 != null) {
            this.f394.onGeolocationPermissionsHidePrompt();
        } else {
            super.onGeolocationPermissionsHidePrompt();
        }
    }

    public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
        callback.invoke(origin, true, false);
        if (this.f394 != null) {
            this.f394.onGeolocationPermissionsShowPrompt(origin, callback);
        } else {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    public void onHideCustomView() {
        if (this.f394 != null) {
            this.f394.onHideCustomView();
        } else {
            super.onHideCustomView();
        }
    }

    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return this.f394 != null ? this.f394.onJsAlert(view, url, message, result) : super.onJsAlert(view, url, message, result);
    }

    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return this.f394 != null ? this.f394.onJsBeforeUnload(view, url, message, result) : super.onJsBeforeUnload(view, url, message, result);
    }

    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return this.f394 != null ? this.f394.onJsConfirm(view, url, message, result) : super.onJsConfirm(view, url, message, result);
    }

    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (m163(message)) {
            result.confirm("{\"code\": 200, \"result\":\"\"}");
            return true;
        } else if (this.f394 != null) {
            return this.f394.onJsPrompt(view, url, message, defaultValue, result);
        } else {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private boolean m163(String message) {
        e parser = new e(message);
        if (parser.m158()) {
            return this.f392.dispatch(parser.m160(), parser.m159());
        }
        return false;
    }

    public boolean onJsTimeout() {
        return this.f394 != null ? this.f394.onJsTimeout() : super.onJsTimeout();
    }

    @TargetApi(21)
    public void onPermissionRequest(PermissionRequest request) {
        if (this.f394 != null) {
            this.f394.onPermissionRequest(request);
        } else {
            super.onPermissionRequest(request);
        }
    }

    @TargetApi(21)
    public void onPermissionRequestCanceled(PermissionRequest request) {
        if (this.f394 != null) {
            this.f394.onPermissionRequestCanceled(request);
        } else {
            super.onPermissionRequestCanceled(request);
        }
    }

    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress > 25) {
            d.m153(this.f393, this.f393.getUrl(), true);
        } else {
            d.m152();
        }
        if (this.f394 != null) {
            this.f394.onProgressChanged(view, newProgress);
        } else {
            super.onProgressChanged(view, newProgress);
        }
    }

    public void onReachedMaxAppCacheSize(long requiredStorage, long quota, QuotaUpdater quotaUpdater) {
        if (this.f394 != null) {
            this.f394.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        } else {
            super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        }
    }

    public void onReceivedIcon(WebView view, Bitmap icon) {
        if (this.f394 != null) {
            this.f394.onReceivedIcon(view, icon);
        } else {
            super.onReceivedIcon(view, icon);
        }
    }

    public void onReceivedTitle(WebView view, String title) {
        if (this.f394 != null) {
            this.f394.onReceivedTitle(view, title);
        } else {
            super.onReceivedTitle(view, title);
        }
    }

    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        if (this.f394 != null) {
            this.f394.onReceivedTouchIconUrl(view, url, precomposed);
        } else {
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }
    }

    public void onRequestFocus(WebView view) {
        if (this.f394 != null) {
            this.f394.onRequestFocus(view);
        } else {
            super.onRequestFocus(view);
        }
    }

    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (this.f394 != null) {
            this.f394.onShowCustomView(view, callback);
        } else {
            super.onShowCustomView(view, callback);
        }
    }

    @TargetApi(14)
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        if (this.f394 != null) {
            this.f394.onShowCustomView(view, requestedOrientation, callback);
        } else {
            super.onShowCustomView(view, requestedOrientation, callback);
        }
    }

    @Keep
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        if (!m162(uploadMsg) && this.f394 != null) {
            try {
                this.f394.getClass().getDeclaredMethod("openFileChooser", new Class[]{ValueCallback.class, String.class, String.class}).invoke(this.f394, new Object[]{uploadMsg, acceptType, capture});
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    @Keep
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (!m162(uploadMsg) && this.f394 != null) {
            try {
                this.f394.getClass().getDeclaredMethod("openFileChooser", new Class[]{ValueCallback.class, String.class}).invoke(this.f394, new Object[]{uploadMsg, acceptType});
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    @Keep
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        if (!m162(uploadMsg) && this.f394 != null) {
            try {
                this.f394.getClass().getDeclaredMethod("openFileChooser", new Class[]{ValueCallback.class}).invoke(this.f394, new Object[]{uploadMsg});
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    @Keep
    @TargetApi(21)
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (m164(filePathCallback) || this.f394 == null) {
            return true;
        }
        return this.f394.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private final boolean m162(ValueCallback<Uri> msg) {
        this.f395 = msg;
        return m161();
    }

    /* renamed from: ˋ reason: contains not printable characters */
    private final boolean m164(ValueCallback<Uri[]> msg) {
        this.f390 = msg;
        return m161();
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private boolean m161() {
        return this.f392.dispatch(EventAPI.EVENT_FILE_CHOOSER, String.valueOf(this.f391));
    }

    public final void receiveImage(Intent data) {
        try {
            if (this.f395 != null) {
                this.f395.onReceiveValue(data == null ? null : data.getData());
                this.f390 = null;
                this.f395 = null;
            }
            if (this.f390 != null) {
                this.f390.onReceiveValue(data == null ? null : new Uri[]{Uri.parse(data.getDataString())});
            }
            this.f390 = null;
            this.f395 = null;
        } catch (Throwable e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
