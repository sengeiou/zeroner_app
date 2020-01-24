package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class EventReminder extends GenericJson {
    @Key
    private String method;
    @Key
    private Integer minutes;

    public String getMethod() {
        return this.method;
    }

    public EventReminder setMethod(String str) {
        this.method = str;
        return this;
    }

    public Integer getMinutes() {
        return this.minutes;
    }

    public EventReminder setMinutes(Integer num) {
        this.minutes = num;
        return this;
    }

    public EventReminder set(String str, Object obj) {
        return (EventReminder) super.set(str, obj);
    }

    public EventReminder clone() {
        return (EventReminder) super.clone();
    }
}
