package com.google.api.client.auth.oauth2;

import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Data;
import com.google.api.client.util.Preconditions;
import com.tencent.connect.common.Constants;
import java.io.IOException;
import java.util.Map;

public class ClientParametersAuthentication implements HttpRequestInitializer, HttpExecuteInterceptor {
    private final String clientId;
    private final String clientSecret;

    public ClientParametersAuthentication(String clientId2, String clientSecret2) {
        this.clientId = (String) Preconditions.checkNotNull(clientId2);
        this.clientSecret = clientSecret2;
    }

    public void initialize(HttpRequest request) throws IOException {
        request.setInterceptor(this);
    }

    public void intercept(HttpRequest request) throws IOException {
        Map<String, Object> data = Data.mapOf(UrlEncodedContent.getContent(request).getData());
        data.put(Constants.PARAM_CLIENT_ID, this.clientId);
        if (this.clientSecret != null) {
            data.put("client_secret", this.clientSecret);
        }
    }

    public final String getClientId() {
        return this.clientId;
    }

    public final String getClientSecret() {
        return this.clientSecret;
    }
}
