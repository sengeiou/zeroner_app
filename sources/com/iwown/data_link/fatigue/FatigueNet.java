package com.iwown.data_link.fatigue;

import java.util.List;

public class FatigueNet {
    private String data_from;
    private List<FatigueData> fatigues;
    private int recordDate;

    public int getRecordDate() {
        return this.recordDate;
    }

    public void setRecordDate(int recordDate2) {
        this.recordDate = recordDate2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public List<FatigueData> getFatigues() {
        return this.fatigues;
    }

    public void setFatigues(List<FatigueData> fatigues2) {
        this.fatigues = fatigues2;
    }
}
