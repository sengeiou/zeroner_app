package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.Map;

public final class FreeBusyResponse extends GenericJson {
    @Key
    private Map<String, FreeBusyCalendar> calendars;
    @Key
    private Map<String, FreeBusyGroup> groups;
    @Key
    private String kind;
    @Key
    private DateTime timeMax;
    @Key
    private DateTime timeMin;

    static {
        Data.nullOf(FreeBusyCalendar.class);
        Data.nullOf(FreeBusyGroup.class);
    }

    public Map<String, FreeBusyCalendar> getCalendars() {
        return this.calendars;
    }

    public FreeBusyResponse setCalendars(Map<String, FreeBusyCalendar> map) {
        this.calendars = map;
        return this;
    }

    public Map<String, FreeBusyGroup> getGroups() {
        return this.groups;
    }

    public FreeBusyResponse setGroups(Map<String, FreeBusyGroup> map) {
        this.groups = map;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public FreeBusyResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public DateTime getTimeMax() {
        return this.timeMax;
    }

    public FreeBusyResponse setTimeMax(DateTime dateTime) {
        this.timeMax = dateTime;
        return this;
    }

    public DateTime getTimeMin() {
        return this.timeMin;
    }

    public FreeBusyResponse setTimeMin(DateTime dateTime) {
        this.timeMin = dateTime;
        return this;
    }

    public FreeBusyResponse set(String str, Object obj) {
        return (FreeBusyResponse) super.set(str, obj);
    }

    public FreeBusyResponse clone() {
        return (FreeBusyResponse) super.clone();
    }
}
