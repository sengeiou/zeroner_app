package com.iwown.data_link.eventbus;

public class HaveGetModelEvent {
    private String model = "";

    public String getModel() {
        return this.model;
    }

    public void setModel(String model2) {
        this.model = model2;
    }

    public HaveGetModelEvent(String model2) {
        this.model = model2;
    }
}
