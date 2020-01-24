package com.sweetzpot.stravazpot.common.model;

public class Percentage {
    private float value;

    public static Percentage of(float value2) {
        return new Percentage(value2);
    }

    public Percentage(float value2) {
        this.value = value2;
    }

    public float getValue() {
        return this.value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (Float.compare(((Percentage) o).value, this.value) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.value != 0.0f) {
            return Float.floatToIntBits(this.value);
        }
        return 0;
    }
}
