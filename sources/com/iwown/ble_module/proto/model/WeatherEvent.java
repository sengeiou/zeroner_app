package com.iwown.ble_module.proto.model;

public class WeatherEvent {
    private int degreeMax;
    private int degreeMin;
    private int pm2p5;
    private int timeMills;
    private int weatherDesc;
    private int weatherType;

    public int getTimeMills() {
        return this.timeMills;
    }

    public void setTimeMills(int timeMills2) {
        this.timeMills = timeMills2;
    }

    public int getWeatherDesc() {
        return this.weatherDesc;
    }

    public void setWeatherDesc(int weatherDesc2) {
        this.weatherDesc = weatherDesc2;
    }

    public int getWeatherType() {
        return this.weatherType;
    }

    public void setWeatherType(int weatherType2) {
        this.weatherType = weatherType2;
    }

    public int getDegreeMax() {
        return this.degreeMax;
    }

    public void setDegreeMax(int degreeMax2) {
        this.degreeMax = degreeMax2;
    }

    public int getDegreeMin() {
        return this.degreeMin;
    }

    public void setDegreeMin(int degreeMin2) {
        this.degreeMin = degreeMin2;
    }

    public int getPm2p5() {
        return this.pm2p5;
    }

    public void setPm2p5(int pm2p52) {
        this.pm2p5 = pm2p52;
    }
}
