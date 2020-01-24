package com.iwown.ble_module.model;

public class Weather24h {
    private int pm_2_5;
    private int temperature;
    private long time_stamp;
    private int weather_type;

    public long getTime_stamp() {
        return this.time_stamp;
    }

    public void setTime_stamp(long time_stamp2) {
        this.time_stamp = time_stamp2;
    }

    public int getPm_2_5() {
        return this.pm_2_5;
    }

    public void setPm_2_5(int pm_2_52) {
        this.pm_2_5 = pm_2_52;
    }

    public int getWeather_type() {
        return this.weather_type;
    }

    public void setWeather_type(int weather_type2) {
        this.weather_type = weather_type2;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature2) {
        this.temperature = temperature2;
    }
}
