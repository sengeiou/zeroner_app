package com.youzan.sdk.loader.http;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.youzan.sdk.loader.http.interfaces.HttpEngine;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import no.nordicsemi.android.dfu.DfuBaseService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* compiled from: OkEngine */
final class d extends c {

    /* renamed from: ˋ reason: contains not printable characters */
    private static final OkHttpClient f34 = new Builder().connectTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).readTimeout(30, TimeUnit.SECONDS).build();

    /* renamed from: ˎ reason: contains not printable characters */
    private final Request.Builder f35 = new Request.Builder();

    /* renamed from: ˏ reason: contains not printable characters */
    private Call f36;

    /* compiled from: OkEngine */
    private static class a<MODEL> implements Callback {

        /* renamed from: ˊ reason: contains not printable characters */
        private final boolean f37;

        /* renamed from: ˋ reason: contains not printable characters */
        private final HttpEngine f38;

        /* renamed from: ˎ reason: contains not printable characters */
        private final Class<MODEL> f39;

        /* renamed from: ˏ reason: contains not printable characters */
        private final WeakReference<Context> f40;

        /* renamed from: ᐝ reason: contains not printable characters */
        private final Query<MODEL> f41;

        public a(Context context, Class<MODEL> cls, boolean onUI, Query<MODEL> query, HttpEngine engine) {
            this.f37 = onUI;
            this.f39 = cls;
            this.f38 = engine;
            this.f41 = query;
            this.f40 = new WeakReference<>(context);
        }

        /* renamed from: ˊ reason: contains not printable characters */
        private boolean m44() {
            Context context = (Context) this.f40.get();
            return !this.f37 || ((context instanceof Activity) && !((Activity) context).isFinishing());
        }

        public void onFailure(Call call, IOException e) {
            Context context = (Context) this.f40.get();
            Query<MODEL> query = this.f41;
            if (!"Canceled".equalsIgnoreCase(e.getMessage()) && m44() && query != null) {
                this.f38.response(null, null, e, query, context, null);
            }
        }

        public void onResponse(Call call, Response response) throws IOException {
            IllegalStateException illegalStateException;
            Context context = (Context) this.f40.get();
            Query<MODEL> query = this.f41;
            if (query != null && m44()) {
                HttpEngine httpEngine = this.f38;
                String string = response.body().string();
                Map multimap = response.headers().toMultimap();
                if (response.isSuccessful()) {
                    illegalStateException = null;
                } else {
                    illegalStateException = new IllegalStateException(String.format(Locale.CHINA, "%s(%d)", new Object[]{response.message(), Integer.valueOf(response.code())}));
                }
                httpEngine.response(string, multimap, illegalStateException, query, context, this.f39);
            }
        }
    }

    d() {
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public void m43(Map<String, File> files, Map<String, String> parameter) {
        if (files == null || files.size() <= 0) {
            FormBody.Builder builder = new FormBody.Builder();
            for (Entry<String, String> entry : parameter.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (!TextUtils.isEmpty(key)) {
                    if (TextUtils.isEmpty(value)) {
                        value = "";
                    }
                    builder.add(key, value);
                }
            }
            this.f35.post(builder.build());
            return;
        }
        MultipartBody.Builder builder2 = new MultipartBody.Builder();
        builder2.setType(MultipartBody.FORM);
        for (Entry<String, String> entry2 : parameter.entrySet()) {
            String key2 = (String) entry2.getKey();
            String value2 = (String) entry2.getValue();
            if (!TextUtils.isEmpty(key2)) {
                if (TextUtils.isEmpty(value2)) {
                    value2 = "";
                }
                builder2.addFormDataPart(key2, value2);
            }
        }
        for (Entry<String, File> entry3 : files.entrySet()) {
            String key3 = (String) entry3.getKey();
            File file = (File) entry3.getValue();
            if (!TextUtils.isEmpty(key3) && file != null) {
                builder2.addFormDataPart(key3, file.getName(), RequestBody.create(MediaType.parse(DfuBaseService.MIME_TYPE_OCTET_STREAM), file));
            }
        }
        this.f35.post(builder2.build());
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public void m41(String url) {
        this.f35.url(url);
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public void m42(Map<String, String> header) {
        if (header != null && header.size() > 0) {
            for (Entry<String, String> entry : header.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (!TextUtils.isEmpty(key)) {
                    Request.Builder builder = this.f35;
                    if (TextUtils.isEmpty(value)) {
                        value = "";
                    }
                    builder.addHeader(key, value);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public <MODEL> void m40(@Nullable Class<MODEL> cls, @Nullable Query<MODEL> query, @Nullable Context context, boolean onUI) {
        this.f36 = f34.newCall(this.f35.build());
        this.f36.enqueue(new a(context, cls, onUI, query, this));
    }

    public void cancel() {
        if (this.f36 != null && !this.f36.isCanceled()) {
            this.f36.cancel();
        }
    }
}
