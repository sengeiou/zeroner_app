package com.iwown.data_link.sport_data.gps;

import io.reactivex.annotations.SchedulerSupport;

public class FixType {
    public static FixType DGPS = new FixType("dgps");
    public static FixType NONE = new FixType(SchedulerSupport.NONE);
    public static FixType PPS = new FixType("pps");
    public static FixType THREE_D = new FixType("3d");
    public static FixType TWO_D = new FixType("2d");
    private String value;

    private FixType(String paramString) {
        this.value = paramString;
    }

    public String getValue() {
        return this.value;
    }

    public static FixType returnType(String paramString) {
        if (NONE.getValue().equals(paramString)) {
            return NONE;
        }
        if (TWO_D.getValue().equals(paramString)) {
            return TWO_D;
        }
        if (THREE_D.getValue().equals(paramString)) {
            return THREE_D;
        }
        if (DGPS.getValue().equals(paramString)) {
            return DGPS;
        }
        if (PPS.getValue().equals(paramString)) {
            return PPS;
        }
        return null;
    }

    public String toString() {
        return this.value;
    }
}
