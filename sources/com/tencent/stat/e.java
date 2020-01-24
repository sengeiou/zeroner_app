package com.tencent.stat;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

class e extends DefaultConnectionKeepAliveStrategy {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        long keepAliveDuration = e.super.getKeepAliveDuration(httpResponse, httpContext);
        if (keepAliveDuration == -1) {
            return 20000;
        }
        return keepAliveDuration;
    }
}
