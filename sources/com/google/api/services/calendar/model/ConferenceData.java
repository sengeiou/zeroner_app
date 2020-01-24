package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class ConferenceData extends GenericJson {
    @Key
    private String conferenceId;
    @Key
    private ConferenceSolution conferenceSolution;
    @Key
    private CreateConferenceRequest createRequest;
    @Key
    private List<EntryPoint> entryPoints;
    @Key
    private String notes;
    @Key
    private ConferenceParameters parameters;
    @Key
    private String signature;

    public String getConferenceId() {
        return this.conferenceId;
    }

    public ConferenceData setConferenceId(String str) {
        this.conferenceId = str;
        return this;
    }

    public ConferenceSolution getConferenceSolution() {
        return this.conferenceSolution;
    }

    public ConferenceData setConferenceSolution(ConferenceSolution conferenceSolution2) {
        this.conferenceSolution = conferenceSolution2;
        return this;
    }

    public CreateConferenceRequest getCreateRequest() {
        return this.createRequest;
    }

    public ConferenceData setCreateRequest(CreateConferenceRequest createConferenceRequest) {
        this.createRequest = createConferenceRequest;
        return this;
    }

    public List<EntryPoint> getEntryPoints() {
        return this.entryPoints;
    }

    public ConferenceData setEntryPoints(List<EntryPoint> list) {
        this.entryPoints = list;
        return this;
    }

    public String getNotes() {
        return this.notes;
    }

    public ConferenceData setNotes(String str) {
        this.notes = str;
        return this;
    }

    public ConferenceParameters getParameters() {
        return this.parameters;
    }

    public ConferenceData setParameters(ConferenceParameters conferenceParameters) {
        this.parameters = conferenceParameters;
        return this;
    }

    public String getSignature() {
        return this.signature;
    }

    public ConferenceData setSignature(String str) {
        this.signature = str;
        return this;
    }

    public ConferenceData set(String str, Object obj) {
        return (ConferenceData) super.set(str, obj);
    }

    public ConferenceData clone() {
        return (ConferenceData) super.clone();
    }
}
