package com.iwown.data_link.sport_data.gps;

public class GpsUpData {
    private int R;
    private long T;
    private int V;
    private double X;
    private double Y;

    public GpsUpData(long time, double lat, double lon, int pause, int step) {
        this.T = time;
        this.Y = lat;
        this.X = lon;
        this.R = pause;
        this.V = step;
    }

    public double getX() {
        return this.X;
    }

    public void setX(double x) {
        this.X = x;
    }

    public double getY() {
        return this.Y;
    }

    public void setY(double y) {
        this.Y = y;
    }

    public long getT() {
        return this.T;
    }

    public void setT(long t) {
        this.T = t;
    }

    public int getV() {
        return this.V;
    }

    public void setV(int v) {
        this.V = v;
    }

    public int getR() {
        return this.R;
    }

    public void setR(int r) {
        this.R = r;
    }
}
