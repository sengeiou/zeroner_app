package com.iwown.sport_module.ui.ecg.bean;

import java.util.List;

public class EcgResultEventBus {
    private List<String> data;
    private String strData;

    public String getStrData() {
        return this.strData;
    }

    public void setStrData(String strData2) {
        this.strData = strData2;
    }

    public List<String> getData() {
        return this.data;
    }

    public void setData(List<String> data2) {
        this.data = data2;
    }
}
