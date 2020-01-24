package com.sweetzpot.stravazpot.common.api;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    private final String token;

    public AuthorizationInterceptor(String token2) {
        this.token = token2;
    }

    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        return chain.proceed(original.newBuilder().header(HttpHeaders.ACCEPT, "application/json").header(HttpHeaders.AUTHORIZATION, this.token).method(original.method(), original.body()).build());
    }
}
