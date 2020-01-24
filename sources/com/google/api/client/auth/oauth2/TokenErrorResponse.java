package com.google.api.client.auth.oauth2;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;

public class TokenErrorResponse extends GenericJson {
    @Key
    private String error;
    @Key("error_description")
    private String errorDescription;
    @Key("error_uri")
    private String errorUri;

    public final String getError() {
        return this.error;
    }

    public TokenErrorResponse setError(String error2) {
        this.error = (String) Preconditions.checkNotNull(error2);
        return this;
    }

    public final String getErrorDescription() {
        return this.errorDescription;
    }

    public TokenErrorResponse setErrorDescription(String errorDescription2) {
        this.errorDescription = errorDescription2;
        return this;
    }

    public final String getErrorUri() {
        return this.errorUri;
    }

    public TokenErrorResponse setErrorUri(String errorUri2) {
        this.errorUri = errorUri2;
        return this;
    }

    public TokenErrorResponse set(String fieldName, Object value) {
        return (TokenErrorResponse) super.set(fieldName, value);
    }

    public TokenErrorResponse clone() {
        return (TokenErrorResponse) super.clone();
    }
}
