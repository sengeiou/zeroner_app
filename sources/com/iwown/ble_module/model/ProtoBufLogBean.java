package com.iwown.ble_module.model;

public class ProtoBufLogBean {
    private String id;
    private String name;
    private String sentence;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getSentence() {
        return this.sentence;
    }

    public void setSentence(String sentence2) {
        this.sentence = sentence2;
    }
}
