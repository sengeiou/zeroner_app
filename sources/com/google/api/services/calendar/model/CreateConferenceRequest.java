package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class CreateConferenceRequest extends GenericJson {
    @Key
    private ConferenceSolutionKey conferenceSolutionKey;
    @Key
    private String requestId;
    @Key
    private ConferenceRequestStatus status;

    public ConferenceSolutionKey getConferenceSolutionKey() {
        return this.conferenceSolutionKey;
    }

    public CreateConferenceRequest setConferenceSolutionKey(ConferenceSolutionKey conferenceSolutionKey2) {
        this.conferenceSolutionKey = conferenceSolutionKey2;
        return this;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public CreateConferenceRequest setRequestId(String str) {
        this.requestId = str;
        return this;
    }

    public ConferenceRequestStatus getStatus() {
        return this.status;
    }

    public CreateConferenceRequest setStatus(ConferenceRequestStatus conferenceRequestStatus) {
        this.status = conferenceRequestStatus;
        return this;
    }

    public CreateConferenceRequest set(String str, Object obj) {
        return (CreateConferenceRequest) super.set(str, obj);
    }

    public CreateConferenceRequest clone() {
        return (CreateConferenceRequest) super.clone();
    }
}
