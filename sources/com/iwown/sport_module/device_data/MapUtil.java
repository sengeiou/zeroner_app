package com.iwown.sport_module.device_data;

public class MapUtil {
    public static String number2mins(float maxY_pace) {
        int minute = (int) maxY_pace;
        return minute + "'" + Math.round((maxY_pace - ((float) minute)) * 60.0f) + "''";
    }
}
