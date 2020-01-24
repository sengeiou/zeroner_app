package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class EntryPoint extends GenericJson {
    @Key
    private String accessCode;
    @Key
    private String entryPointType;
    @Key
    private String label;
    @Key
    private String meetingCode;
    @Key
    private String passcode;
    @Key
    private String password;
    @Key
    private String pin;
    @Key
    private String uri;

    public String getAccessCode() {
        return this.accessCode;
    }

    public EntryPoint setAccessCode(String str) {
        this.accessCode = str;
        return this;
    }

    public String getEntryPointType() {
        return this.entryPointType;
    }

    public EntryPoint setEntryPointType(String str) {
        this.entryPointType = str;
        return this;
    }

    public String getLabel() {
        return this.label;
    }

    public EntryPoint setLabel(String str) {
        this.label = str;
        return this;
    }

    public String getMeetingCode() {
        return this.meetingCode;
    }

    public EntryPoint setMeetingCode(String str) {
        this.meetingCode = str;
        return this;
    }

    public String getPasscode() {
        return this.passcode;
    }

    public EntryPoint setPasscode(String str) {
        this.passcode = str;
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public EntryPoint setPassword(String str) {
        this.password = str;
        return this;
    }

    public String getPin() {
        return this.pin;
    }

    public EntryPoint setPin(String str) {
        this.pin = str;
        return this;
    }

    public String getUri() {
        return this.uri;
    }

    public EntryPoint setUri(String str) {
        this.uri = str;
        return this;
    }

    public EntryPoint set(String str, Object obj) {
        return (EntryPoint) super.set(str, obj);
    }

    public EntryPoint clone() {
        return (EntryPoint) super.clone();
    }
}
