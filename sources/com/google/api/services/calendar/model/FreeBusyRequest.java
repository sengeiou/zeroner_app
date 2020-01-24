package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;

public final class FreeBusyRequest extends GenericJson {
    @Key
    private Integer calendarExpansionMax;
    @Key
    private Integer groupExpansionMax;
    @Key
    private List<FreeBusyRequestItem> items;
    @Key
    private DateTime timeMax;
    @Key
    private DateTime timeMin;
    @Key
    private String timeZone;

    public Integer getCalendarExpansionMax() {
        return this.calendarExpansionMax;
    }

    public FreeBusyRequest setCalendarExpansionMax(Integer num) {
        this.calendarExpansionMax = num;
        return this;
    }

    public Integer getGroupExpansionMax() {
        return this.groupExpansionMax;
    }

    public FreeBusyRequest setGroupExpansionMax(Integer num) {
        this.groupExpansionMax = num;
        return this;
    }

    public List<FreeBusyRequestItem> getItems() {
        return this.items;
    }

    public FreeBusyRequest setItems(List<FreeBusyRequestItem> list) {
        this.items = list;
        return this;
    }

    public DateTime getTimeMax() {
        return this.timeMax;
    }

    public FreeBusyRequest setTimeMax(DateTime dateTime) {
        this.timeMax = dateTime;
        return this;
    }

    public DateTime getTimeMin() {
        return this.timeMin;
    }

    public FreeBusyRequest setTimeMin(DateTime dateTime) {
        this.timeMin = dateTime;
        return this;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public FreeBusyRequest setTimeZone(String str) {
        this.timeZone = str;
        return this;
    }

    public FreeBusyRequest set(String str, Object obj) {
        return (FreeBusyRequest) super.set(str, obj);
    }

    public FreeBusyRequest clone() {
        return (FreeBusyRequest) super.clone();
    }
}
