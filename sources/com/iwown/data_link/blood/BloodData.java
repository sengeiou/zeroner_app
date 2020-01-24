package com.iwown.data_link.blood;

public class BloodData {
    private String Data_from;
    private int Dbp;
    private String Record_date;
    private int Sbp;

    public String getRecord_date() {
        return this.Record_date;
    }

    public void setRecord_date(String record_date) {
        this.Record_date = record_date;
    }

    public String getData_from() {
        return this.Data_from;
    }

    public void setData_from(String data_from) {
        this.Data_from = data_from;
    }

    public int getSbp() {
        return this.Sbp;
    }

    public void setSbp(int sbp) {
        this.Sbp = sbp;
    }

    public int getDbp() {
        return this.Dbp;
    }

    public void setDbp(int dbp) {
        this.Dbp = dbp;
    }
}
