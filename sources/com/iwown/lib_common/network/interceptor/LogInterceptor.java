package com.iwown.lib_common.network.interceptor;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.LongCompanionObject;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static Map<String, Long> requestMaps = new HashMap();

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Charset charset = UTF8;
        long t1 = System.nanoTime();
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer2 = new Buffer();
            requestBody.writeTo(buffer2);
            MediaType contentType2 = requestBody.contentType();
            if (contentType2 != null) {
                charset = contentType2.charset(UTF8);
            }
            KLog.debug(String.format("%s request %s  body %s ", new Object[]{request.method(), request.url(), buffer2.readString(charset)}), "net");
        } else {
            KLog.debug(String.format("%s request %s ", new Object[]{request.method(), request.url()}), "net");
        }
        int limit = 5000;
        if (TextUtils.equals(request.method(), "GET") && request.url().toString().contains("homedata?")) {
            limit = 8000;
        }
        Long lastTime = (Long) requestMaps.get(request.url().toString());
        boolean isCacheOk = false;
        Response originalResponse = null;
        if (lastTime != null && lastTime.longValue() > 0) {
            long size = System.currentTimeMillis() - lastTime.longValue();
            if (size < ((long) limit) && TextUtils.equals(request.method(), "GET")) {
                KLog.debug(limit + "s  距离上次 " + size + "ms  缓存读取", "net");
                try {
                    originalResponse = chain.proceed(request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build());
                    if (originalResponse.code() != 504) {
                        isCacheOk = true;
                    }
                } catch (IOException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }
        if (isCacheOk) {
            return originalResponse;
        }
        if (TextUtils.equals(request.method(), "GET")) {
            requestMaps.put(request.url().toString(), Long.valueOf(System.currentTimeMillis()));
        }
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(LongCompanionObject.MAX_VALUE);
        Buffer buffer = source.buffer();
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        KLog.debug("response " + request.url() + " time " + (((double) (System.nanoTime() - t1)) / 1000000.0d) + " \n " + buffer.clone().readString(charset), "net");
        return response;
    }
}
