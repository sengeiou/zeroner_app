package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import android.support.annotation.FloatRange;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.utils.MiscUtils;
import java.util.ArrayList;
import java.util.List;

public class ShapeData {
    private boolean closed;
    private final List<CubicCurveData> curves = new ArrayList();
    private PointF initialPoint;

    public ShapeData(PointF initialPoint2, boolean closed2, List<CubicCurveData> curves2) {
        this.initialPoint = initialPoint2;
        this.closed = closed2;
        this.curves.addAll(curves2);
    }

    public ShapeData() {
    }

    private void setInitialPoint(float x, float y) {
        if (this.initialPoint == null) {
            this.initialPoint = new PointF();
        }
        this.initialPoint.set(x, y);
    }

    public PointF getInitialPoint() {
        return this.initialPoint;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public List<CubicCurveData> getCurves() {
        return this.curves;
    }

    public void interpolateBetween(ShapeData shapeData1, ShapeData shapeData2, @FloatRange(from = 0.0d, to = 1.0d) float percentage) {
        if (this.initialPoint == null) {
            this.initialPoint = new PointF();
        }
        this.closed = shapeData1.isClosed() || shapeData2.isClosed();
        if (this.curves.isEmpty() || this.curves.size() == shapeData1.getCurves().size() || this.curves.size() == shapeData2.getCurves().size()) {
            if (this.curves.isEmpty()) {
                for (int i = shapeData1.getCurves().size() - 1; i >= 0; i--) {
                    this.curves.add(new CubicCurveData());
                }
            }
            PointF initialPoint1 = shapeData1.getInitialPoint();
            PointF initialPoint2 = shapeData2.getInitialPoint();
            setInitialPoint(MiscUtils.lerp(initialPoint1.x, initialPoint2.x, percentage), MiscUtils.lerp(initialPoint1.y, initialPoint2.y, percentage));
            for (int i2 = this.curves.size() - 1; i2 >= 0; i2--) {
                CubicCurveData curve1 = (CubicCurveData) shapeData1.getCurves().get(i2);
                CubicCurveData curve2 = (CubicCurveData) shapeData2.getCurves().get(i2);
                PointF cp11 = curve1.getControlPoint1();
                PointF cp21 = curve1.getControlPoint2();
                PointF vertex1 = curve1.getVertex();
                PointF cp12 = curve2.getControlPoint1();
                PointF cp22 = curve2.getControlPoint2();
                PointF vertex2 = curve2.getVertex();
                ((CubicCurveData) this.curves.get(i2)).setControlPoint1(MiscUtils.lerp(cp11.x, cp12.x, percentage), MiscUtils.lerp(cp11.y, cp12.y, percentage));
                ((CubicCurveData) this.curves.get(i2)).setControlPoint2(MiscUtils.lerp(cp21.x, cp22.x, percentage), MiscUtils.lerp(cp21.y, cp22.y, percentage));
                ((CubicCurveData) this.curves.get(i2)).setVertex(MiscUtils.lerp(vertex1.x, vertex2.x, percentage), MiscUtils.lerp(vertex1.y, vertex2.y, percentage));
            }
            return;
        }
        throw new IllegalStateException("Curves must have the same number of control points. This: " + getCurves().size() + "\tShape 1: " + shapeData1.getCurves().size() + "\tShape 2: " + shapeData2.getCurves().size());
    }

    public String toString() {
        return "ShapeData{numCurves=" + this.curves.size() + "closed=" + this.closed + '}';
    }
}
