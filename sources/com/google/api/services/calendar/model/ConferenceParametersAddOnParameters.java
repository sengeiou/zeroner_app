package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.Map;

public final class ConferenceParametersAddOnParameters extends GenericJson {
    @Key
    private Map<String, String> parameters;

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public ConferenceParametersAddOnParameters setParameters(Map<String, String> map) {
        this.parameters = map;
        return this;
    }

    public ConferenceParametersAddOnParameters set(String str, Object obj) {
        return (ConferenceParametersAddOnParameters) super.set(str, obj);
    }

    public ConferenceParametersAddOnParameters clone() {
        return (ConferenceParametersAddOnParameters) super.clone();
    }
}
