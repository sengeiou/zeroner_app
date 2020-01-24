package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;

public class TokenRequest extends GenericData {
    HttpExecuteInterceptor clientAuthentication;
    @Key("grant_type")
    private String grantType;
    private final JsonFactory jsonFactory;
    HttpRequestInitializer requestInitializer;
    @Key("scope")
    private String scopes;
    private GenericUrl tokenServerUrl;
    private final HttpTransport transport;

    public TokenRequest(HttpTransport transport2, JsonFactory jsonFactory2, GenericUrl tokenServerUrl2, String grantType2) {
        this.transport = (HttpTransport) Preconditions.checkNotNull(transport2);
        this.jsonFactory = (JsonFactory) Preconditions.checkNotNull(jsonFactory2);
        setTokenServerUrl(tokenServerUrl2);
        setGrantType(grantType2);
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }

    public TokenRequest setRequestInitializer(HttpRequestInitializer requestInitializer2) {
        this.requestInitializer = requestInitializer2;
        return this;
    }

    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }

    public TokenRequest setClientAuthentication(HttpExecuteInterceptor clientAuthentication2) {
        this.clientAuthentication = clientAuthentication2;
        return this;
    }

    public final GenericUrl getTokenServerUrl() {
        return this.tokenServerUrl;
    }

    public TokenRequest setTokenServerUrl(GenericUrl tokenServerUrl2) {
        this.tokenServerUrl = tokenServerUrl2;
        Preconditions.checkArgument(tokenServerUrl2.getFragment() == null);
        return this;
    }

    public final String getScopes() {
        return this.scopes;
    }

    public TokenRequest setScopes(Collection<String> scopes2) {
        this.scopes = scopes2 == null ? null : Joiner.on(' ').join(scopes2);
        return this;
    }

    public final String getGrantType() {
        return this.grantType;
    }

    public TokenRequest setGrantType(String grantType2) {
        this.grantType = (String) Preconditions.checkNotNull(grantType2);
        return this;
    }

    public final HttpResponse executeUnparsed() throws IOException {
        HttpRequest request = this.transport.createRequestFactory(new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
                if (TokenRequest.this.requestInitializer != null) {
                    TokenRequest.this.requestInitializer.initialize(request);
                }
                final HttpExecuteInterceptor interceptor = request.getInterceptor();
                request.setInterceptor(new HttpExecuteInterceptor() {
                    public void intercept(HttpRequest request) throws IOException {
                        if (interceptor != null) {
                            interceptor.intercept(request);
                        }
                        if (TokenRequest.this.clientAuthentication != null) {
                            TokenRequest.this.clientAuthentication.intercept(request);
                        }
                    }
                });
            }
        }).buildPostRequest(this.tokenServerUrl, new UrlEncodedContent(this));
        request.setParser(new JsonObjectParser(this.jsonFactory));
        request.setThrowExceptionOnExecuteError(false);
        HttpResponse response = request.execute();
        if (response.isSuccessStatusCode()) {
            return response;
        }
        throw TokenResponseException.from(this.jsonFactory, response);
    }

    public TokenResponse execute() throws IOException {
        return (TokenResponse) executeUnparsed().parseAs(TokenResponse.class);
    }

    public TokenRequest set(String fieldName, Object value) {
        return (TokenRequest) super.set(fieldName, value);
    }
}
