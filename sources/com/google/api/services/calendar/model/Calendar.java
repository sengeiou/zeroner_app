package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Calendar extends GenericJson {
    @Key
    private ConferenceProperties conferenceProperties;
    @Key
    private String description;
    @Key
    private String etag;
    @Key
    private String id;
    @Key
    private String kind;
    @Key
    private String location;
    @Key
    private String summary;
    @Key
    private String timeZone;

    public ConferenceProperties getConferenceProperties() {
        return this.conferenceProperties;
    }

    public Calendar setConferenceProperties(ConferenceProperties conferenceProperties2) {
        this.conferenceProperties = conferenceProperties2;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Calendar setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public Calendar setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public Calendar setId(String str) {
        this.id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Calendar setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getLocation() {
        return this.location;
    }

    public Calendar setLocation(String str) {
        this.location = str;
        return this;
    }

    public String getSummary() {
        return this.summary;
    }

    public Calendar setSummary(String str) {
        this.summary = str;
        return this;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public Calendar setTimeZone(String str) {
        this.timeZone = str;
        return this;
    }

    public Calendar set(String str, Object obj) {
        return (Calendar) super.set(str, obj);
    }

    public Calendar clone() {
        return (Calendar) super.clone();
    }
}
