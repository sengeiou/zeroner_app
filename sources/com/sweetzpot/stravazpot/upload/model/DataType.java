package com.sweetzpot.stravazpot.upload.model;

public enum DataType {
    FIT("fit"),
    FIT_GZ("fit.gz"),
    TCX("tcx"),
    TCX_GZ("tcx.gz"),
    GPX("gpx"),
    GPX_GZ("gpx.gz");
    
    private String rawValue;

    private DataType(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
