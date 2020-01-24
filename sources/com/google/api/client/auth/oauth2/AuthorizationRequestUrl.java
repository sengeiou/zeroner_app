package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class AuthorizationRequestUrl extends GenericUrl {
    @Key("client_id")
    private String clientId;
    @Key("redirect_uri")
    private String redirectUri;
    @Key("response_type")
    private String responseTypes;
    @Key("scope")
    private String scopes;
    @Key
    private String state;

    public AuthorizationRequestUrl(String authorizationServerEncodedUrl, String clientId2, Collection<String> responseTypes2) {
        super(authorizationServerEncodedUrl);
        Preconditions.checkArgument(getFragment() == null);
        setClientId(clientId2);
        setResponseTypes(responseTypes2);
    }

    public final String getResponseTypes() {
        return this.responseTypes;
    }

    public AuthorizationRequestUrl setResponseTypes(Collection<String> responseTypes2) {
        this.responseTypes = Joiner.on(' ').join(responseTypes2);
        return this;
    }

    public final String getRedirectUri() {
        return this.redirectUri;
    }

    public AuthorizationRequestUrl setRedirectUri(String redirectUri2) {
        this.redirectUri = redirectUri2;
        return this;
    }

    public final String getScopes() {
        return this.scopes;
    }

    public AuthorizationRequestUrl setScopes(Collection<String> scopes2) {
        this.scopes = (scopes2 == null || !scopes2.iterator().hasNext()) ? null : Joiner.on(' ').join(scopes2);
        return this;
    }

    public final String getClientId() {
        return this.clientId;
    }

    public AuthorizationRequestUrl setClientId(String clientId2) {
        this.clientId = (String) Preconditions.checkNotNull(clientId2);
        return this;
    }

    public final String getState() {
        return this.state;
    }

    public AuthorizationRequestUrl setState(String state2) {
        this.state = state2;
        return this;
    }

    public AuthorizationRequestUrl set(String fieldName, Object value) {
        return (AuthorizationRequestUrl) super.set(fieldName, value);
    }

    public AuthorizationRequestUrl clone() {
        return (AuthorizationRequestUrl) super.clone();
    }
}
