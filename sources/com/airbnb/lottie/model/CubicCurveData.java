package com.airbnb.lottie.model;

import android.graphics.PointF;

public class CubicCurveData {
    private final PointF controlPoint1;
    private final PointF controlPoint2;
    private final PointF vertex;

    public CubicCurveData() {
        this.controlPoint1 = new PointF();
        this.controlPoint2 = new PointF();
        this.vertex = new PointF();
    }

    public CubicCurveData(PointF controlPoint12, PointF controlPoint22, PointF vertex2) {
        this.controlPoint1 = controlPoint12;
        this.controlPoint2 = controlPoint22;
        this.vertex = vertex2;
    }

    public void setControlPoint1(float x, float y) {
        this.controlPoint1.set(x, y);
    }

    public PointF getControlPoint1() {
        return this.controlPoint1;
    }

    public void setControlPoint2(float x, float y) {
        this.controlPoint2.set(x, y);
    }

    public PointF getControlPoint2() {
        return this.controlPoint2;
    }

    public void setVertex(float x, float y) {
        this.vertex.set(x, y);
    }

    public PointF getVertex() {
        return this.vertex;
    }
}
