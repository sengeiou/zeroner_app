package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ColorDefinition extends GenericJson {
    @Key
    private String background;
    @Key
    private String foreground;

    public String getBackground() {
        return this.background;
    }

    public ColorDefinition setBackground(String str) {
        this.background = str;
        return this;
    }

    public String getForeground() {
        return this.foreground;
    }

    public ColorDefinition setForeground(String str) {
        this.foreground = str;
        return this;
    }

    public ColorDefinition set(String str, Object obj) {
        return (ColorDefinition) super.set(str, obj);
    }

    public ColorDefinition clone() {
        return (ColorDefinition) super.clone();
    }
}
