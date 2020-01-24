package com.iwown.sport_module.ui.blood;

public class BloodBeanData {
    private int BP_dbp;
    private String BP_grade;
    private String BP_num;
    private int BP_sbp;
    private String BP_time;

    public int getBP_sbp() {
        return this.BP_sbp;
    }

    public void setBP_sbp(int new_sbp) {
        this.BP_sbp = new_sbp;
    }

    public int getBP_dbp() {
        return this.BP_dbp;
    }

    public void setBP_dbp(int new_dbp) {
        this.BP_dbp = new_dbp;
    }

    public String getBP_grade() {
        return this.BP_grade;
    }

    public void setBP_grade(String BP_grade2) {
        this.BP_grade = BP_grade2;
    }

    public String getBP_time() {
        return this.BP_time;
    }

    public void setBP_time(String BP_time2) {
        this.BP_time = BP_time2;
    }

    public String getBP_num() {
        return this.BP_num;
    }

    public void setBP_num(String BP_num2) {
        this.BP_num = BP_num2;
    }
}
