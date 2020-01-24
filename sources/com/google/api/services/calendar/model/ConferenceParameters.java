package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ConferenceParameters extends GenericJson {
    @Key
    private ConferenceParametersAddOnParameters addOnParameters;

    public ConferenceParametersAddOnParameters getAddOnParameters() {
        return this.addOnParameters;
    }

    public ConferenceParameters setAddOnParameters(ConferenceParametersAddOnParameters conferenceParametersAddOnParameters) {
        this.addOnParameters = conferenceParametersAddOnParameters;
        return this;
    }

    public ConferenceParameters set(String str, Object obj) {
        return (ConferenceParameters) super.set(str, obj);
    }

    public ConferenceParameters clone() {
        return (ConferenceParameters) super.clone();
    }
}
