package com.sweetzpot.stravazpot.common.model;

public class Temperature {
    private float celsiusDegrees;

    public static Temperature celsiusDegrees(float celsiusDegrees2) {
        return new Temperature(celsiusDegrees2);
    }

    private Temperature(float celsiusDegrees2) {
        this.celsiusDegrees = celsiusDegrees2;
    }

    public float getCelsiusDegrees() {
        return this.celsiusDegrees;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (Float.compare(((Temperature) o).celsiusDegrees, this.celsiusDegrees) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.celsiusDegrees != 0.0f) {
            return Float.floatToIntBits(this.celsiusDegrees);
        }
        return 0;
    }
}
