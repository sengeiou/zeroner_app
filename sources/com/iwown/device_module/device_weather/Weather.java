package com.iwown.device_module.device_weather;

import android.support.annotation.NonNull;

public class Weather implements Comparable<Weather> {
    private boolean isCentigrade;
    private int pm_25;
    private int temperature;
    private long time_stamp;
    private int weather_type;

    public boolean isCentigrade() {
        return this.isCentigrade;
    }

    public void setCentigrade(boolean centigrade) {
        this.isCentigrade = centigrade;
    }

    public int getPm_25() {
        return this.pm_25;
    }

    public void setPm_25(int pm_252) {
        this.pm_25 = pm_252;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature2) {
        this.temperature = temperature2;
    }

    public long getTime_stamp() {
        return this.time_stamp;
    }

    public void setTime_stamp(long time_stamp2) {
        this.time_stamp = time_stamp2;
    }

    public int getWeather_type() {
        return this.weather_type;
    }

    public void setWeather_type(int weather_type2) {
        this.weather_type = weather_type2;
    }

    public int compareTo(@NonNull Weather o) {
        return (int) (getTime_stamp() - o.getTime_stamp());
    }
}
