package com.sweetzpot.stravazpot.stream.model;

public enum StreamType {
    TIME("time"),
    LATLNG("latlng"),
    DISTANCE("distance"),
    ALTITUDE("altitude"),
    VELOCITY_SMOOTH("velocity_smooth"),
    HEART_RATE("heartrate"),
    CADENCE("cadence"),
    WATTS("watts"),
    TEMPERATURE("temp"),
    MOVING("moving"),
    GRADE_SMOOTH("grade_smooth");
    
    private String rawValue;

    private StreamType(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }

    public static String getQueryString(StreamType[] types) {
        if (types == null) {
            return null;
        }
        String result = "";
        for (int i = 0; i < types.length; i++) {
            if (i > 0) {
                result = result + ",";
            }
            result = result + types[i].toString();
        }
        return result;
    }
}
