package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ConferenceRequestStatus extends GenericJson {
    @Key
    private String statusCode;

    public String getStatusCode() {
        return this.statusCode;
    }

    public ConferenceRequestStatus setStatusCode(String str) {
        this.statusCode = str;
        return this;
    }

    public ConferenceRequestStatus set(String str, Object obj) {
        return (ConferenceRequestStatus) super.set(str, obj);
    }

    public ConferenceRequestStatus clone() {
        return (ConferenceRequestStatus) super.clone();
    }
}
