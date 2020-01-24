package com.tencent.wxop.stat;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

class j extends DefaultConnectionKeepAliveStrategy {
    final /* synthetic */ i a;

    j(i iVar) {
        this.a = iVar;
    }

    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        long keepAliveDuration = j.super.getKeepAliveDuration(httpResponse, httpContext);
        if (keepAliveDuration == -1) {
            return 30000;
        }
        return keepAliveDuration;
    }
}
