package com.google.api.client.http.apache;

import com.google.api.client.util.Preconditions;
import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

final class HttpExtensionMethod extends HttpEntityEnclosingRequestBase {
    private final String methodName;

    public HttpExtensionMethod(String methodName2, String uri) {
        this.methodName = (String) Preconditions.checkNotNull(methodName2);
        setURI(URI.create(uri));
    }

    public String getMethod() {
        return this.methodName;
    }
}
