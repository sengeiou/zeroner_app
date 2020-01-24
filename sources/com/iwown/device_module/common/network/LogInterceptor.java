package com.iwown.device_module.common.network;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.LongCompanionObject;
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
        long nanoTime = System.nanoTime();
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer2 = new Buffer();
            requestBody.writeTo(buffer2);
            MediaType contentType2 = requestBody.contentType();
            if (contentType2 != null) {
                charset = contentType2.charset(UTF8);
            }
            KLog.i("request:" + request.url() + "\nbody:" + buffer2.readString(charset));
        } else {
            KLog.i("request:" + request.url());
        }
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (response != null) {
            try {
                ResponseBody responseBody = response.body();
                BufferedSource source = responseBody.source();
                source.request(LongCompanionObject.MAX_VALUE);
                Buffer buffer = source.buffer();
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                long nanoTime2 = System.nanoTime();
                KLog.i("response" + buffer.clone().readString(charset));
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
        return response;
    }
}
