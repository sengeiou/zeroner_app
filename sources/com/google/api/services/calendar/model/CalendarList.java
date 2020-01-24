package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class CalendarList extends GenericJson {
    @Key
    private String etag;
    @Key
    private List<CalendarListEntry> items;
    @Key
    private String kind;
    @Key
    private String nextPageToken;
    @Key
    private String nextSyncToken;

    public String getEtag() {
        return this.etag;
    }

    public CalendarList setEtag(String str) {
        this.etag = str;
        return this;
    }

    public List<CalendarListEntry> getItems() {
        return this.items;
    }

    public CalendarList setItems(List<CalendarListEntry> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public CalendarList setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public CalendarList setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public String getNextSyncToken() {
        return this.nextSyncToken;
    }

    public CalendarList setNextSyncToken(String str) {
        this.nextSyncToken = str;
        return this;
    }

    public CalendarList set(String str, Object obj) {
        return (CalendarList) super.set(str, obj);
    }

    public CalendarList clone() {
        return (CalendarList) super.clone();
    }
}
