package com.youzan.sdk.web.plugin;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanToken;
import com.youzan.sdk.event.AbsChooserEvent;
import com.youzan.sdk.tool.Preference;
import com.youzan.sdk.tool.b.C0013b;
import com.youzan.sdk.tool.d;
import com.youzan.sdk.tool.f;
import com.youzan.sdk.tool.i;
import com.youzan.sdk.web.bridge.Event;

public class YouzanBrowser extends WebView implements YouzanClient {

    /* renamed from: ˊ reason: contains not printable characters */
    private static final int f3 = 2000;
    /* access modifiers changed from: private */

    /* renamed from: ˋ reason: contains not printable characters */
    public volatile boolean f4 = false;

    /* renamed from: ˎ reason: contains not printable characters */
    private volatile boolean f5 = false;

    /* renamed from: ˏ reason: contains not printable characters */
    private ChromeClientWrapper f6;

    /* renamed from: ᐝ reason: contains not printable characters */
    private WebClientWrapper f7;

    @Deprecated
    public interface a {
        @Deprecated
        /* renamed from: ˊ reason: contains not printable characters */
        void m3(Intent intent, int i) throws ActivityNotFoundException;
    }

    public YouzanBrowser(Context context) {
        super(context);
        m0(context);
    }

    public YouzanBrowser(Context context, AttributeSet attrs) {
        super(context, attrs);
        m0(context);
    }

    public YouzanBrowser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        m0(context);
    }

    @TargetApi(21)
    public YouzanBrowser(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        m0(context);
    }

    public YouzanBrowser(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        m0(context);
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private void m0(Context context) {
        if (!isInEditMode()) {
            if (!YouzanSDK.READY) {
                throw new IllegalStateException("You should init YouzanSDK at first!!!");
            }
            Preference.renew(context);
            this.f6 = new ChromeClientWrapper(this);
            this.f7 = new WebClientWrapper(this);
            try {
                this.f5 = false;
                getClass().getSuperclass().getDeclaredMethod("setWebChromeClient", new Class[]{WebChromeClient.class}).invoke(this, new Object[]{this.f6});
                getClass().getSuperclass().getDeclaredMethod("setWebViewClient", new Class[]{WebViewClient.class}).invoke(this, new Object[]{this.f7});
                this.f5 = true;
            } catch (Throwable th) {
            }
            m2(context);
            postDelayed(new Runnable() {
                public void run() {
                    YouzanBrowser.this.f4 = true;
                }
            }, 2000);
        }
    }

    /* renamed from: ˋ reason: contains not printable characters */
    private void m2(Context context) {
        C0013b.m85(getContext(), true);
        C0013b.m82(context);
        i.m129((WebView) this);
        i.m130(this, f.f367, "");
        i.m133((WebView) this);
    }

    public int getPageType() {
        return 1;
    }

    public final boolean pageGoBack() {
        if (this.f4) {
            if (VERSION.SDK_INT <= 19) {
                return this.f7.pageGoBack(this);
            }
            if (!pageCanGoBack()) {
                return false;
            }
            if (i.m132(i.m135((WebView) this))) {
                goBackOrForward(-2);
            } else {
                goBack();
            }
        }
        return true;
    }

    public final boolean pageCanGoBack() {
        if (VERSION.SDK_INT <= 19) {
            if (!TextUtils.isEmpty(this.f7.getUrl())) {
                return true;
            }
            return false;
        } else if (!i.m137((WebView) this) || !canGoBack()) {
            return false;
        } else {
            return true;
        }
    }

    public final void sharePage() {
        d.m109(this);
    }

    public final void setWebChromeClient(WebChromeClient client) {
        if (this.f5) {
            this.f6.setDelegate(client);
        } else {
            super.setWebChromeClient(client);
        }
    }

    public final void setWebViewClient(WebViewClient client) {
        if (this.f5) {
            this.f7.setDelegate(client);
        } else {
            super.setWebViewClient(client);
        }
    }

    public final void setWebChromeClient(YouzanChromeClient client) {
        this.f6.setDelegate(client);
    }

    public final void setWebViewClient(YouzanWebViewClient client) {
        this.f7.setDelegate(client);
    }

    @Deprecated
    public final void hideTopbar(boolean hide) {
        C0013b.m85(getContext(), hide);
    }

    public final YouzanBrowser subscribe(Event event) {
        this.f6.subscribe(event);
        return this;
    }

    public void sync(YouzanToken token) {
        f.m118(getContext(), token);
        reload();
    }

    public final boolean isReceiveFileForWebView(int requestCode, Intent data) {
        return receiveFile(requestCode, data);
    }

    public boolean receiveFile(int requestCode, Intent data) {
        if (requestCode != this.f6.f391.intValue()) {
            return false;
        }
        this.f6.receiveImage(data);
        return true;
    }

    @Deprecated
    public void setOnChooseFileCallback(final a listener) {
        this.f6.subscribe(new AbsChooserEvent() {
            public void call(View view, Intent intent, int requestId) throws ActivityNotFoundException {
                if (listener != null) {
                    listener.m3(intent, requestId);
                }
            }
        });
    }
}
