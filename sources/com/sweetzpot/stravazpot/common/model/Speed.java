package com.sweetzpot.stravazpot.common.model;

public class Speed {
    private float metersPerSecond;

    public static Speed metersPerSecond(float metersPerSecond2) {
        return new Speed(metersPerSecond2);
    }

    public Speed(float metersPerSecond2) {
        this.metersPerSecond = metersPerSecond2;
    }

    public float getMetersPerSecond() {
        return this.metersPerSecond;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (Float.compare(((Speed) o).metersPerSecond, this.metersPerSecond) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.metersPerSecond != 0.0f) {
            return Float.floatToIntBits(this.metersPerSecond);
        }
        return 0;
    }
}
