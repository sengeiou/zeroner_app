package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class FreeBusyRequestItem extends GenericJson {
    @Key
    private String id;

    public String getId() {
        return this.id;
    }

    public FreeBusyRequestItem setId(String str) {
        this.id = str;
        return this;
    }

    public FreeBusyRequestItem set(String str, Object obj) {
        return (FreeBusyRequestItem) super.set(str, obj);
    }

    public FreeBusyRequestItem clone() {
        return (FreeBusyRequestItem) super.clone();
    }
}
