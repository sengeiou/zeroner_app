package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;

public final class Events extends GenericJson {
    @Key
    private String accessRole;
    @Key
    private List<EventReminder> defaultReminders;
    @Key
    private String description;
    @Key
    private String etag;
    @Key
    private List<Event> items;
    @Key
    private String kind;
    @Key
    private String nextPageToken;
    @Key
    private String nextSyncToken;
    @Key
    private String summary;
    @Key
    private String timeZone;
    @Key
    private DateTime updated;

    static {
        Data.nullOf(EventReminder.class);
        Data.nullOf(Event.class);
    }

    public String getAccessRole() {
        return this.accessRole;
    }

    public Events setAccessRole(String str) {
        this.accessRole = str;
        return this;
    }

    public List<EventReminder> getDefaultReminders() {
        return this.defaultReminders;
    }

    public Events setDefaultReminders(List<EventReminder> list) {
        this.defaultReminders = list;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Events setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public Events setEtag(String str) {
        this.etag = str;
        return this;
    }

    public List<Event> getItems() {
        return this.items;
    }

    public Events setItems(List<Event> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Events setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public Events setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public String getNextSyncToken() {
        return this.nextSyncToken;
    }

    public Events setNextSyncToken(String str) {
        this.nextSyncToken = str;
        return this;
    }

    public String getSummary() {
        return this.summary;
    }

    public Events setSummary(String str) {
        this.summary = str;
        return this;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public Events setTimeZone(String str) {
        this.timeZone = str;
        return this;
    }

    public DateTime getUpdated() {
        return this.updated;
    }

    public Events setUpdated(DateTime dateTime) {
        this.updated = dateTime;
        return this;
    }

    public Events set(String str, Object obj) {
        return (Events) super.set(str, obj);
    }

    public Events clone() {
        return (Events) super.clone();
    }
}
