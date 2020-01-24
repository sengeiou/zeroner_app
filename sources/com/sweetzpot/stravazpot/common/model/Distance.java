package com.sweetzpot.stravazpot.common.model;

public class Distance {
    private float meters;

    public static Distance meters(float meters2) {
        return new Distance(meters2);
    }

    public Distance(float meters2) {
        this.meters = meters2;
    }

    public float getMeters() {
        return this.meters;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (Float.compare(((Distance) o).meters, this.meters) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.meters != 0.0f) {
            return Float.floatToIntBits(this.meters);
        }
        return 0;
    }

    public String toString() {
        return String.valueOf(this.meters);
    }
}
