package com.iwown.sport_module.ui.ecg.bean;

public class EcgTestBean {
    private int ecgTestTime;
    private String ecgTestTimeString;
    private int heartAVG;
    private String note;

    public int getEcgTestTime() {
        return this.ecgTestTime;
    }

    public void setEcgTestTime(int ecgTestTime2) {
        this.ecgTestTime = ecgTestTime2;
    }

    public String getEcgTestTimeString() {
        return this.ecgTestTimeString;
    }

    public void setEcgTestTimeString(String ecgTestTimeString2) {
        this.ecgTestTimeString = ecgTestTimeString2;
    }

    public int getHeartAVG() {
        return this.heartAVG;
    }

    public void setHeartAVG(int heartAVG2) {
        this.heartAVG = heartAVG2;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note2) {
        this.note = note2;
    }
}
