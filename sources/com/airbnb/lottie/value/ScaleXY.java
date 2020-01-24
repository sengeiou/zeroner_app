package com.airbnb.lottie.value;

public class ScaleXY {
    private final float scaleX;
    private final float scaleY;

    public ScaleXY(float sx, float sy) {
        this.scaleX = sx;
        this.scaleY = sy;
    }

    public ScaleXY() {
        this(1.0f, 1.0f);
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public String toString() {
        return getScaleX() + "x" + getScaleY();
    }
}
