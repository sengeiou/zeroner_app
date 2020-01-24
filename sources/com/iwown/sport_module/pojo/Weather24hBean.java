package com.iwown.sport_module.pojo;

public class Weather24hBean {
    public boolean isCentigrade;
    public float pm_25 = -1.0f;
    public double temperature;
    public long time_stamp;
    public int weather_type;

    public long getTime_stamp() {
        return this.time_stamp;
    }

    public void setTime_stamp(long time_stamp2) {
        this.time_stamp = time_stamp2;
    }

    public boolean isCentigrade() {
        return this.isCentigrade;
    }

    public void setCentigrade(boolean centigrade) {
        this.isCentigrade = centigrade;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double temperature2) {
        this.temperature = temperature2;
    }

    public int getWeather_type() {
        return this.weather_type;
    }

    public void setWeather_type(int weather_type2) {
        this.weather_type = weather_type2;
    }

    public float getPm_25() {
        return this.pm_25;
    }

    public void setPm_25(float pm_252) {
        this.pm_25 = pm_252;
    }
}
