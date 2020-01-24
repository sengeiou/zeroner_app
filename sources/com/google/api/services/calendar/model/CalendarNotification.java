package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class CalendarNotification extends GenericJson {
    @Key
    private String method;
    @Key
    private String type;

    public String getMethod() {
        return this.method;
    }

    public CalendarNotification setMethod(String str) {
        this.method = str;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public CalendarNotification setType(String str) {
        this.type = str;
        return this;
    }

    public CalendarNotification set(String str, Object obj) {
        return (CalendarNotification) super.set(str, obj);
    }

    public CalendarNotification clone() {
        return (CalendarNotification) super.clone();
    }
}
