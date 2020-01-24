package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class FreeBusyCalendar extends GenericJson {
    @Key
    private List<TimePeriod> busy;
    @Key
    private List<Error> errors;

    static {
        Data.nullOf(Error.class);
    }

    public List<TimePeriod> getBusy() {
        return this.busy;
    }

    public FreeBusyCalendar setBusy(List<TimePeriod> list) {
        this.busy = list;
        return this;
    }

    public List<Error> getErrors() {
        return this.errors;
    }

    public FreeBusyCalendar setErrors(List<Error> list) {
        this.errors = list;
        return this;
    }

    public FreeBusyCalendar set(String str, Object obj) {
        return (FreeBusyCalendar) super.set(str, obj);
    }

    public FreeBusyCalendar clone() {
        return (FreeBusyCalendar) super.clone();
    }
}
