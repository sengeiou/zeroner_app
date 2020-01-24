package com.iwown.data_link.weight.archive;

public class DataNotBelongToBean {
    public static final int UNIT_1_BANG = 1;
    public static final int UNIT_1_JIN = 3;
    public static final int UNIT_2_KG = 2;
    private String date;
    private int type;
    private float weightData;
    private int weightId;

    public DataNotBelongToBean() {
    }

    public DataNotBelongToBean(int type2, float weightData2, String date2) {
        this.type = type2;
        this.weightData = weightData2;
        this.date = date2;
    }

    public DataNotBelongToBean(int type2, float weightData2, String date2, int weightId2) {
        this.type = type2;
        this.weightData = weightData2;
        this.date = date2;
        this.weightId = weightId2;
    }

    public int getWeightId() {
        return this.weightId;
    }

    public void setWeightId(int weightId2) {
        this.weightId = weightId2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public float getWeightData() {
        return this.weightData;
    }

    public void setWeightData(float weightData2) {
        this.weightData = weightData2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }
}
