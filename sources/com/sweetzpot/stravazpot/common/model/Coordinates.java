package com.sweetzpot.stravazpot.common.model;

public class Coordinates {
    private float latitude;
    private float longitude;

    public static Coordinates at(float latitude2, float longitude2) {
        return new Coordinates(latitude2, longitude2);
    }

    public Coordinates(float latitude2, float longitude2) {
        this.latitude = latitude2;
        this.longitude = longitude2;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinates that = (Coordinates) o;
        if (Float.compare(that.latitude, this.latitude) != 0) {
            return false;
        }
        if (Float.compare(that.longitude, this.longitude) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.latitude != 0.0f) {
            result = Float.floatToIntBits(this.latitude);
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.longitude != 0.0f) {
            i = Float.floatToIntBits(this.longitude);
        }
        return i2 + i;
    }
}
