package com.youzan.sdk.loader.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.tencent.connect.common.Constants;
import com.youzan.sdk.loader.http.interfaces.HttpEngine;
import com.youzan.sdk.tool.Preference.Token;
import com.youzan.sdk.tool.f;
import com.youzan.sdk.tool.g;
import java.io.File;
import java.util.List;
import java.util.Map;

/* compiled from: Engine */
abstract class c implements HttpEngine {

    /* renamed from: ˊ reason: contains not printable characters */
    static final String f30 = "application/octet-stream";

    /* compiled from: Engine */
    private static class a<MODEL> implements Runnable {

        /* renamed from: ˊ reason: contains not printable characters */
        private final Query<MODEL> f31;

        /* renamed from: ˋ reason: contains not printable characters */
        private final Exception f32;

        /* renamed from: ˎ reason: contains not printable characters */
        private final MODEL f33;

        a(Query<MODEL> listener, MODEL data, Exception error) {
            this.f31 = listener;
            this.f33 = data;
            this.f32 = error;
        }

        public void run() {
            if (this.f32 == null) {
                this.f31.onSuccess(this.f33);
            } else {
                this.f31.onFailure(this.f32);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public abstract <MODEL> void m36(@Nullable Class<MODEL> cls, @Nullable Query<MODEL> query, @Nullable Context context, boolean z);

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public abstract void m37(String str);

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public abstract void m38(Map<String, String> map);

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public abstract void m39(Map<String, File> map, Map<String, String> map2);

    c() {
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private static void m34(Runnable runnable) {
        if (runnable != null) {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    public <MODEL> void request(@Nullable Context context, int method, @NonNull String url, @NonNull Map<String, String> parameter, @Nullable Map<String, File> files, @NonNull Map<String, String> header, @Nullable Class<MODEL> cls, @NonNull Query<MODEL> query, boolean onUI) {
        query.f22 = this;
        m38(m35(header));
        Map<String, String> param = m33(query.getAuthType(), g.m122(g.m124(url), parameter));
        if (method == 2 || (files != null && files.size() > 0)) {
            m37(g.m120(url));
            m39(files, param);
        } else {
            m37(g.m121(g.m120(url), param));
        }
        m36(cls, query, context, onUI);
    }

    public <MODEL> void response(@Nullable String body, @Nullable Map<String, List<String>> header, @Nullable Exception error, @NonNull Query<MODEL> query, @Nullable Context context, @Nullable Class<MODEL> cls) {
        query.f21 = body;
        query.f20 = header;
        MODEL data = null;
        if (error == null) {
            try {
                data = query.m20(body);
            } catch (Exception e) {
                error = e;
            }
        }
        a<MODEL> runner = new a<>(query, data, error);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runner.run();
        } else if (context != null) {
            m34((Runnable) runner);
        }
    }

    /* renamed from: ˋ reason: contains not printable characters */
    private Map<String, String> m35(@NonNull Map<String, String> header) {
        header.put("User-agent", f.f368);
        return header;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private Map<String, String> m33(int authType, @NonNull Map<String, String> parameter) {
        switch (authType) {
            case 2:
            case 3:
                parameter.put(Constants.PARAM_ACCESS_TOKEN, Token.getAccessToken());
                break;
        }
        return parameter;
    }
}
