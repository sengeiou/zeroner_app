package com.sweetzpot.stravazpot.segment.model;

public enum DateRange {
    THIS_YEAR("this_year"),
    THIS_MONTH("this_month"),
    THIS_WEEK("this_week"),
    TODAY("today");
    
    private String rawValue;

    private DateRange(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
