package com.iwown.lib_common.network.interceptor;

import com.google.common.net.HttpHeaders;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.network.NetworkUtils;
import com.socks.library.KLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {
    private static Map<String, Long> requestMaps = new HashMap();

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isNetworkAvailable() && canCache(request.method())) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            KLog.e("当前无网络 请求缓存数据");
        }
        try {
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isNetworkAvailable()) {
                return originalResponse.newBuilder().removeHeader(HttpHeaders.PRAGMA).header(HttpHeaders.CACHE_CONTROL, "public ,max-age=" + 60).build();
            }
            return originalResponse.newBuilder().build();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            if (!NetworkUtils.isNetworkAvailable()) {
                KLog.e("请检查本地网络");
                throw new IOException("网络不可用,请检查本地网络");
            }
            throw e;
        }
    }

    public static boolean canCache(String method) {
        return method.equals("GET");
    }
}
