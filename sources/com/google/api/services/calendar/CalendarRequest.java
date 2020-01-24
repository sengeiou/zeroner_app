package com.google.api.services.calendar;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.Key;

public abstract class CalendarRequest<T> extends AbstractGoogleJsonClientRequest<T> {
    @Key
    private String alt;
    @Key
    private String fields;
    @Key
    private String key;
    @Key("oauth_token")
    private String oauthToken;
    @Key
    private Boolean prettyPrint;
    @Key
    private String quotaUser;
    @Key
    private String userIp;

    public CalendarRequest(Calendar calendar, String str, String str2, Object obj, Class<T> cls) {
        super(calendar, str, str2, obj, cls);
    }

    public String getAlt() {
        return this.alt;
    }

    public CalendarRequest<T> setAlt(String str) {
        this.alt = str;
        return this;
    }

    public String getFields() {
        return this.fields;
    }

    public CalendarRequest<T> setFields(String str) {
        this.fields = str;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public CalendarRequest<T> setKey(String str) {
        this.key = str;
        return this;
    }

    public String getOauthToken() {
        return this.oauthToken;
    }

    public CalendarRequest<T> setOauthToken(String str) {
        this.oauthToken = str;
        return this;
    }

    public Boolean getPrettyPrint() {
        return this.prettyPrint;
    }

    public CalendarRequest<T> setPrettyPrint(Boolean bool) {
        this.prettyPrint = bool;
        return this;
    }

    public String getQuotaUser() {
        return this.quotaUser;
    }

    public CalendarRequest<T> setQuotaUser(String str) {
        this.quotaUser = str;
        return this;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public CalendarRequest<T> setUserIp(String str) {
        this.userIp = str;
        return this;
    }

    public final Calendar getAbstractGoogleClient() {
        return (Calendar) super.getAbstractGoogleClient();
    }

    public CalendarRequest<T> setDisableGZipContent(boolean z) {
        return (CalendarRequest) super.setDisableGZipContent(z);
    }

    public CalendarRequest<T> setRequestHeaders(HttpHeaders httpHeaders) {
        return (CalendarRequest) super.setRequestHeaders(httpHeaders);
    }

    public CalendarRequest<T> set(String str, Object obj) {
        return (CalendarRequest) super.set(str, obj);
    }
}
