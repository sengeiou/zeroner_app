package com.youzan.sdk.web.plugin;

import android.content.Context;
import android.content.Intent;
import com.youzan.sdk.YouzanToken;
import com.youzan.sdk.web.bridge.Event;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface YouzanClient {
    public static final int PAGE_TYPE_HTML5 = 1;
    public static final int PAGE_TYPE_NATIVE_CART = 18;
    public static final int PAGE_TYPE_NATIVE_GOODS = 17;
    public static final int PAGE_TYPE_UNKNOWN = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface a {
    }

    Context getContext();

    int getPageType();

    String getTitle();

    String getUrl();

    void loadUrl(String str);

    boolean pageCanGoBack();

    boolean pageGoBack();

    boolean receiveFile(int i, Intent intent);

    void reload();

    void sharePage();

    YouzanClient subscribe(Event event);

    void sync(YouzanToken youzanToken);
}
