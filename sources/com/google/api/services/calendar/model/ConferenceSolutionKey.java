package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ConferenceSolutionKey extends GenericJson {
    @Key
    private String type;

    public String getType() {
        return this.type;
    }

    public ConferenceSolutionKey setType(String str) {
        this.type = str;
        return this;
    }

    public ConferenceSolutionKey set(String str, Object obj) {
        return (ConferenceSolutionKey) super.set(str, obj);
    }

    public ConferenceSolutionKey clone() {
        return (ConferenceSolutionKey) super.clone();
    }
}
