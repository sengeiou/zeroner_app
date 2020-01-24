package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Error extends GenericJson {
    @Key
    private String domain;
    @Key
    private String reason;

    public String getDomain() {
        return this.domain;
    }

    public Error setDomain(String str) {
        this.domain = str;
        return this;
    }

    public String getReason() {
        return this.reason;
    }

    public Error setReason(String str) {
        this.reason = str;
        return this;
    }

    public Error set(String str, Object obj) {
        return (Error) super.set(str, obj);
    }

    public Error clone() {
        return (Error) super.clone();
    }
}
