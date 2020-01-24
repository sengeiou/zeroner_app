package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.Build.VERSION;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.common.net.HttpHeaders;
import com.iwown.my_module.utility.Constants.SERVICE_UTIL;
import com.squareup.picasso.Downloader.Response;
import com.squareup.picasso.Downloader.ResponseException;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnectionDownloader implements Downloader {
    private static final ThreadLocal<StringBuilder> CACHE_HEADER_BUILDER = new ThreadLocal<StringBuilder>() {
        /* access modifiers changed from: protected */
        public StringBuilder initialValue() {
            return new StringBuilder();
        }
    };
    private static final String FORCE_CACHE = "only-if-cached,max-age=2147483647";
    static final String RESPONSE_SOURCE = "X-Android-Response-Source";
    static volatile Object cache;
    private static final Object lock = new Object();
    private final Context context;

    private static class ResponseCacheIcs {
        private ResponseCacheIcs() {
        }

        static Object install(Context context) throws IOException {
            File cacheDir = Utils.createDefaultCacheDir(context);
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            if (cache == null) {
                return HttpResponseCache.install(cacheDir, Utils.calculateDiskCacheSize(cacheDir));
            }
            return cache;
        }

        static void close(Object cache) {
            try {
                ((HttpResponseCache) cache).close();
            } catch (IOException e) {
            }
        }
    }

    public UrlConnectionDownloader(Context context2) {
        this.context = context2.getApplicationContext();
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection openConnection(Uri path) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(path.toString()).openConnection();
        connection.setConnectTimeout(SERVICE_UTIL.ELAPSED_TIME);
        connection.setReadTimeout(20000);
        return connection;
    }

    public Response load(Uri uri, int networkPolicy) throws IOException {
        String headerValue;
        if (VERSION.SDK_INT >= 14) {
            installCacheIfNeeded(this.context);
        }
        HttpURLConnection connection = openConnection(uri);
        connection.setUseCaches(true);
        if (networkPolicy != 0) {
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                headerValue = FORCE_CACHE;
            } else {
                StringBuilder builder = (StringBuilder) CACHE_HEADER_BUILDER.get();
                builder.setLength(0);
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.append("no-cache");
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    if (builder.length() > 0) {
                        builder.append(',');
                    }
                    builder.append("no-store");
                }
                headerValue = builder.toString();
            }
            connection.setRequestProperty(HttpHeaders.CACHE_CONTROL, headerValue);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode >= 300) {
            connection.disconnect();
            throw new ResponseException(responseCode + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + connection.getResponseMessage(), networkPolicy, responseCode);
        }
        long contentLength = (long) connection.getHeaderFieldInt("Content-Length", -1);
        return new Response(connection.getInputStream(), Utils.parseResponseSourceHeader(connection.getHeaderField(RESPONSE_SOURCE)), contentLength);
    }

    public void shutdown() {
        if (VERSION.SDK_INT >= 14 && cache != null) {
            ResponseCacheIcs.close(cache);
        }
    }

    private static void installCacheIfNeeded(Context context2) {
        if (cache == null) {
            try {
                synchronized (lock) {
                    if (cache == null) {
                        cache = ResponseCacheIcs.install(context2);
                    }
                }
            } catch (IOException e) {
            }
        }
    }
}
