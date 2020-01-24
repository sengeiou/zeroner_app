package com.iwown.device_module.device_setting.wifi_scale.bean;

import java.util.List;

public class QuestionBean {
    private List<String> contents;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public List<String> getContents() {
        return this.contents;
    }

    public void setContents(List<String> contents2) {
        this.contents = contents2;
    }

    public String toString() {
        return "QuestionBean{title='" + this.title + '\'' + ", contents=" + this.contents + '}';
    }
}
