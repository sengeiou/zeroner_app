package com.youzan.sdk.loader.http;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.youzan.sdk.loader.http.interfaces.HttpCall;
import com.youzan.sdk.loader.http.interfaces.HttpEngine;
import com.youzan.sdk.loader.http.interfaces.HttpForms;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Http implements HttpForms {
    private final Context mContext;
    private final HttpEngine mEngine;
    private final boolean mOnUI;
    private final Map<String, File> mRequestFileParameter = new LinkedHashMap(3);
    private final Map<String, String> mRequestHeader = new LinkedHashMap(5);
    private final Map<String, String> mRequestParameter = new LinkedHashMap(10);

    private Http(@Nullable Context context, boolean onUI) {
        this.mOnUI = onUI;
        this.mContext = context;
        this.mEngine = new d();
    }

    public static Http attach(@Nullable Context context, boolean onUI) {
        return new Http(context, onUI);
    }

    public static Http attach(@Nullable Context context) {
        return new Http(context, true);
    }

    private static <T> T checkEmpty(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        } else if (!(obj instanceof String) || !TextUtils.isEmpty(String.valueOf(obj))) {
            return obj;
        } else {
            throw new NullPointerException();
        }
    }

    public HttpForms put(String key, boolean value) {
        return put(key, value, true);
    }

    public HttpForms put(String key, boolean value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    public HttpForms put(String key, String value) {
        return put(key, value, true);
    }

    public HttpForms put(String key, String value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    public HttpForms put(String key, int value) {
        return put(key, value, true);
    }

    public HttpForms put(String key, int value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    public HttpForms put(String key, double value) {
        return put(key, value, true);
    }

    public HttpForms put(String key, double value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    public HttpForms put(String key, float value) {
        return put(key, value, true);
    }

    public HttpForms put(String key, float value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    public HttpForms put(String key, long value) {
        return put(key, value, true);
    }

    public HttpForms put(String key, long value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    public HttpForms put(String key, short value) {
        return put(key, value, true);
    }

    public HttpForms put(String key, short value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    public HttpForms put(String key, File value) {
        return put(key, value, true);
    }

    public HttpForms put(String key, File value, boolean apply) {
        if (apply) {
            this.mRequestFileParameter.put(key, value);
        }
        return this;
    }

    public HttpForms puts(Map<String, String> forms) {
        this.mRequestParameter.putAll(forms);
        return this;
    }

    public <MODEL> HttpCall with(@NonNull Query<MODEL> query) throws NullPointerException {
        this.mEngine.request(this.mContext, query.getHTTPMethod(), a.m21(((Query) checkEmpty(query)).getAuthType(), (String) checkEmpty(((Query) checkEmpty(query)).attachTo())), this.mRequestParameter, this.mRequestFileParameter, this.mRequestHeader, query.getModel(), query, this.mOnUI);
        return query;
    }
}
