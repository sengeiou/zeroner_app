package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Credential implements HttpExecuteInterceptor, HttpRequestInitializer, HttpUnsuccessfulResponseHandler {
    static final Logger LOGGER = Logger.getLogger(Credential.class.getName());
    private String accessToken;
    private final HttpExecuteInterceptor clientAuthentication;
    private final Clock clock;
    private Long expirationTimeMilliseconds;
    private final JsonFactory jsonFactory;
    private final Lock lock;
    private final AccessMethod method;
    private final Collection<CredentialRefreshListener> refreshListeners;
    private String refreshToken;
    private final HttpRequestInitializer requestInitializer;
    private final String tokenServerEncodedUrl;
    private final HttpTransport transport;

    public interface AccessMethod {
        String getAccessTokenFromRequest(HttpRequest httpRequest);

        void intercept(HttpRequest httpRequest, String str) throws IOException;
    }

    public static class Builder {
        HttpExecuteInterceptor clientAuthentication;
        Clock clock = Clock.SYSTEM;
        JsonFactory jsonFactory;
        final AccessMethod method;
        Collection<CredentialRefreshListener> refreshListeners = Lists.newArrayList();
        HttpRequestInitializer requestInitializer;
        GenericUrl tokenServerUrl;
        HttpTransport transport;

        public Builder(AccessMethod method2) {
            this.method = (AccessMethod) Preconditions.checkNotNull(method2);
        }

        public Credential build() {
            return new Credential(this);
        }

        public final AccessMethod getMethod() {
            return this.method;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public Builder setTransport(HttpTransport transport2) {
            this.transport = transport2;
            return this;
        }

        public final Clock getClock() {
            return this.clock;
        }

        public Builder setClock(Clock clock2) {
            this.clock = (Clock) Preconditions.checkNotNull(clock2);
            return this;
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public Builder setJsonFactory(JsonFactory jsonFactory2) {
            this.jsonFactory = jsonFactory2;
            return this;
        }

        public final GenericUrl getTokenServerUrl() {
            return this.tokenServerUrl;
        }

        public Builder setTokenServerUrl(GenericUrl tokenServerUrl2) {
            this.tokenServerUrl = tokenServerUrl2;
            return this;
        }

        public Builder setTokenServerEncodedUrl(String tokenServerEncodedUrl) {
            this.tokenServerUrl = tokenServerEncodedUrl == null ? null : new GenericUrl(tokenServerEncodedUrl);
            return this;
        }

        public final HttpExecuteInterceptor getClientAuthentication() {
            return this.clientAuthentication;
        }

        public Builder setClientAuthentication(HttpExecuteInterceptor clientAuthentication2) {
            this.clientAuthentication = clientAuthentication2;
            return this;
        }

        public final HttpRequestInitializer getRequestInitializer() {
            return this.requestInitializer;
        }

        public Builder setRequestInitializer(HttpRequestInitializer requestInitializer2) {
            this.requestInitializer = requestInitializer2;
            return this;
        }

        public Builder addRefreshListener(CredentialRefreshListener refreshListener) {
            this.refreshListeners.add(Preconditions.checkNotNull(refreshListener));
            return this;
        }

        public final Collection<CredentialRefreshListener> getRefreshListeners() {
            return this.refreshListeners;
        }

        public Builder setRefreshListeners(Collection<CredentialRefreshListener> refreshListeners2) {
            this.refreshListeners = (Collection) Preconditions.checkNotNull(refreshListeners2);
            return this;
        }
    }

    public Credential(AccessMethod method2) {
        this(new Builder(method2));
    }

    protected Credential(Builder builder) {
        this.lock = new ReentrantLock();
        this.method = (AccessMethod) Preconditions.checkNotNull(builder.method);
        this.transport = builder.transport;
        this.jsonFactory = builder.jsonFactory;
        this.tokenServerEncodedUrl = builder.tokenServerUrl == null ? null : builder.tokenServerUrl.build();
        this.clientAuthentication = builder.clientAuthentication;
        this.requestInitializer = builder.requestInitializer;
        this.refreshListeners = Collections.unmodifiableCollection(builder.refreshListeners);
        this.clock = (Clock) Preconditions.checkNotNull(builder.clock);
    }

    public void intercept(HttpRequest request) throws IOException {
        this.lock.lock();
        try {
            Long expiresIn = getExpiresInSeconds();
            if (this.accessToken == null || (expiresIn != null && expiresIn.longValue() <= 60)) {
                refreshToken();
                if (this.accessToken == null) {
                    return;
                }
            }
            this.method.intercept(request, this.accessToken);
            this.lock.unlock();
        } finally {
            this.lock.unlock();
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handleResponse(com.google.api.client.http.HttpRequest r11, com.google.api.client.http.HttpResponse r12, boolean r13) {
        /*
            r10 = this;
            r6 = 1
            r7 = 0
            r5 = 0
            r2 = 0
            com.google.api.client.http.HttpHeaders r8 = r12.getHeaders()
            java.util.List r1 = r8.getAuthenticateAsList()
            if (r1 == 0) goto L_0x0032
            java.util.Iterator r4 = r1.iterator()
        L_0x0012:
            boolean r8 = r4.hasNext()
            if (r8 == 0) goto L_0x0032
            java.lang.Object r0 = r4.next()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r8 = "Bearer "
            boolean r8 = r0.startsWith(r8)
            if (r8 == 0) goto L_0x0012
            r2 = 1
            java.util.regex.Pattern r8 = com.google.api.client.auth.oauth2.BearerToken.INVALID_TOKEN_ERROR
            java.util.regex.Matcher r8 = r8.matcher(r0)
            boolean r5 = r8.find()
        L_0x0032:
            if (r2 != 0) goto L_0x003d
            int r8 = r12.getStatusCode()
            r9 = 401(0x191, float:5.62E-43)
            if (r8 != r9) goto L_0x005e
            r5 = r6
        L_0x003d:
            if (r5 == 0) goto L_0x0074
            java.util.concurrent.locks.Lock r8 = r10.lock     // Catch:{ IOException -> 0x0069 }
            r8.lock()     // Catch:{ IOException -> 0x0069 }
            java.lang.String r8 = r10.accessToken     // Catch:{ all -> 0x0062 }
            com.google.api.client.auth.oauth2.Credential$AccessMethod r9 = r10.method     // Catch:{ all -> 0x0062 }
            java.lang.String r9 = r9.getAccessTokenFromRequest(r11)     // Catch:{ all -> 0x0062 }
            boolean r8 = com.google.api.client.util.Objects.equal(r8, r9)     // Catch:{ all -> 0x0062 }
            if (r8 == 0) goto L_0x0058
            boolean r8 = r10.refreshToken()     // Catch:{ all -> 0x0062 }
            if (r8 == 0) goto L_0x0060
        L_0x0058:
            java.util.concurrent.locks.Lock r8 = r10.lock     // Catch:{ IOException -> 0x0069 }
            r8.unlock()     // Catch:{ IOException -> 0x0069 }
        L_0x005d:
            return r6
        L_0x005e:
            r5 = r7
            goto L_0x003d
        L_0x0060:
            r6 = r7
            goto L_0x0058
        L_0x0062:
            r6 = move-exception
            java.util.concurrent.locks.Lock r8 = r10.lock     // Catch:{ IOException -> 0x0069 }
            r8.unlock()     // Catch:{ IOException -> 0x0069 }
            throw r6     // Catch:{ IOException -> 0x0069 }
        L_0x0069:
            r3 = move-exception
            java.util.logging.Logger r6 = LOGGER
            java.util.logging.Level r8 = java.util.logging.Level.SEVERE
            java.lang.String r9 = "unable to refresh token"
            r6.log(r8, r9, r3)
        L_0x0074:
            r6 = r7
            goto L_0x005d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.auth.oauth2.Credential.handleResponse(com.google.api.client.http.HttpRequest, com.google.api.client.http.HttpResponse, boolean):boolean");
    }

    public void initialize(HttpRequest request) throws IOException {
        request.setInterceptor(this);
        request.setUnsuccessfulResponseHandler(this);
    }

    public final String getAccessToken() {
        this.lock.lock();
        try {
            return this.accessToken;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setAccessToken(String accessToken2) {
        this.lock.lock();
        try {
            this.accessToken = accessToken2;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public final AccessMethod getMethod() {
        return this.method;
    }

    public final Clock getClock() {
        return this.clock;
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final String getTokenServerEncodedUrl() {
        return this.tokenServerEncodedUrl;
    }

    public final String getRefreshToken() {
        this.lock.lock();
        try {
            return this.refreshToken;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setRefreshToken(String refreshToken2) {
        this.lock.lock();
        if (refreshToken2 != null) {
            try {
                Preconditions.checkArgument((this.jsonFactory == null || this.transport == null || this.clientAuthentication == null || this.tokenServerEncodedUrl == null) ? false : true, "Please use the Builder and call setJsonFactory, setTransport, setClientAuthentication and setTokenServerUrl/setTokenServerEncodedUrl");
            } catch (Throwable th) {
                this.lock.unlock();
                throw th;
            }
        }
        this.refreshToken = refreshToken2;
        this.lock.unlock();
        return this;
    }

    public final Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            return this.expirationTimeMilliseconds;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setExpirationTimeMilliseconds(Long expirationTimeMilliseconds2) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = expirationTimeMilliseconds2;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public final Long getExpiresInSeconds() {
        this.lock.lock();
        try {
            if (this.expirationTimeMilliseconds == null) {
                return null;
            }
            Long valueOf = Long.valueOf((this.expirationTimeMilliseconds.longValue() - this.clock.currentTimeMillis()) / 1000);
            this.lock.unlock();
            return valueOf;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setExpiresInSeconds(Long expiresIn) {
        return setExpirationTimeMilliseconds(expiresIn == null ? null : Long.valueOf(this.clock.currentTimeMillis() + (expiresIn.longValue() * 1000)));
    }

    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }

    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }

    public final boolean refreshToken() throws IOException {
        boolean statusCode4xx;
        this.lock.lock();
        try {
            TokenResponse tokenResponse = executeRefreshToken();
            if (tokenResponse != null) {
                setFromTokenResponse(tokenResponse);
                for (CredentialRefreshListener refreshListener : this.refreshListeners) {
                    refreshListener.onTokenResponse(this, tokenResponse);
                }
                this.lock.unlock();
                return true;
            }
        } catch (TokenResponseException e) {
            if (400 > e.getStatusCode() || e.getStatusCode() >= 500) {
                statusCode4xx = false;
            } else {
                statusCode4xx = true;
            }
            if (e.getDetails() != null && statusCode4xx) {
                setAccessToken(null);
                setExpiresInSeconds(null);
            }
            for (CredentialRefreshListener refreshListener2 : this.refreshListeners) {
                refreshListener2.onTokenErrorResponse(this, e.getDetails());
            }
            if (statusCode4xx) {
                throw e;
            }
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
        this.lock.unlock();
        return false;
    }

    public Credential setFromTokenResponse(TokenResponse tokenResponse) {
        setAccessToken(tokenResponse.getAccessToken());
        if (tokenResponse.getRefreshToken() != null) {
            setRefreshToken(tokenResponse.getRefreshToken());
        }
        setExpiresInSeconds(tokenResponse.getExpiresInSeconds());
        return this;
    }

    /* access modifiers changed from: protected */
    public TokenResponse executeRefreshToken() throws IOException {
        if (this.refreshToken == null) {
            return null;
        }
        return new RefreshTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), this.refreshToken).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).execute();
    }

    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }
}
