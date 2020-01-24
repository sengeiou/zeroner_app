package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.Map;

public final class Colors extends GenericJson {
    @Key
    private Map<String, ColorDefinition> calendar;
    @Key
    private Map<String, ColorDefinition> event;
    @Key
    private String kind;
    @Key
    private DateTime updated;

    static {
        Data.nullOf(ColorDefinition.class);
        Data.nullOf(ColorDefinition.class);
    }

    public Map<String, ColorDefinition> getCalendar() {
        return this.calendar;
    }

    public Colors setCalendar(Map<String, ColorDefinition> map) {
        this.calendar = map;
        return this;
    }

    public Map<String, ColorDefinition> getEvent() {
        return this.event;
    }

    public Colors setEvent(Map<String, ColorDefinition> map) {
        this.event = map;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Colors setKind(String str) {
        this.kind = str;
        return this;
    }

    public DateTime getUpdated() {
        return this.updated;
    }

    public Colors setUpdated(DateTime dateTime) {
        this.updated = dateTime;
        return this;
    }

    public Colors set(String str, Object obj) {
        return (Colors) super.set(str, obj);
    }

    public Colors clone() {
        return (Colors) super.clone();
    }
}
