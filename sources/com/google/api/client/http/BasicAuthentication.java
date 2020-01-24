package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import java.io.IOException;

public final class BasicAuthentication implements HttpRequestInitializer, HttpExecuteInterceptor {
    private final String password;
    private final String username;

    public BasicAuthentication(String username2, String password2) {
        this.username = (String) Preconditions.checkNotNull(username2);
        this.password = (String) Preconditions.checkNotNull(password2);
    }

    public void initialize(HttpRequest request) throws IOException {
        request.setInterceptor(this);
    }

    public void intercept(HttpRequest request) throws IOException {
        request.getHeaders().setBasicAuthentication(this.username, this.password);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
