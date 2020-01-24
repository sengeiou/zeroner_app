package com.google.api.services.calendar;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer;
import java.io.IOException;

public class CalendarRequestInitializer extends CommonGoogleJsonClientRequestInitializer {
    public CalendarRequestInitializer() {
    }

    public CalendarRequestInitializer(String str) {
        super(str);
    }

    public CalendarRequestInitializer(String str, String str2) {
        super(str, str2);
    }

    public final void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> abstractGoogleJsonClientRequest) throws IOException {
        super.initializeJsonRequest(abstractGoogleJsonClientRequest);
        initializeCalendarRequest((CalendarRequest) abstractGoogleJsonClientRequest);
    }

    /* access modifiers changed from: protected */
    public void initializeCalendarRequest(CalendarRequest<?> calendarRequest) throws IOException {
    }
}
