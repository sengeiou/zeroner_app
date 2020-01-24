package com.iwown.ble_module.model;

public class Weather7D {
    private int max_temp;
    private int min_temp;
    private int weather_type;

    public int getMax_temp() {
        return this.max_temp;
    }

    public void setMax_temp(int max_temp2) {
        this.max_temp = max_temp2;
    }

    public int getMin_temp() {
        return this.min_temp;
    }

    public void setMin_temp(int min_temp2) {
        this.min_temp = min_temp2;
    }

    public int getWeather_type() {
        return this.weather_type;
    }

    public void setWeather_type(int weather_type2) {
        this.weather_type = weather_type2;
    }
}
