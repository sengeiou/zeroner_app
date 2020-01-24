package com.airbnb.lottie.utils;

import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.FloatRange;
import com.airbnb.lottie.animation.content.KeyPathElementContent;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.ShapeData;
import java.util.List;

public class MiscUtils {
    public static PointF addPoints(PointF p1, PointF p2) {
        return new PointF(p1.x + p2.x, p1.y + p2.y);
    }

    public static void getPathFromData(ShapeData shapeData, Path outPath) {
        outPath.reset();
        PointF initialPoint = shapeData.getInitialPoint();
        outPath.moveTo(initialPoint.x, initialPoint.y);
        PointF currentPoint = new PointF(initialPoint.x, initialPoint.y);
        for (int i = 0; i < shapeData.getCurves().size(); i++) {
            CubicCurveData curveData = (CubicCurveData) shapeData.getCurves().get(i);
            PointF cp1 = curveData.getControlPoint1();
            PointF cp2 = curveData.getControlPoint2();
            PointF vertex = curveData.getVertex();
            if (!cp1.equals(currentPoint) || !cp2.equals(vertex)) {
                outPath.cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, vertex.x, vertex.y);
            } else {
                outPath.lineTo(vertex.x, vertex.y);
            }
            currentPoint.set(vertex.x, vertex.y);
        }
        if (shapeData.isClosed()) {
            outPath.close();
        }
    }

    public static float lerp(float a, float b, @FloatRange(from = 0.0d, to = 1.0d) float percentage) {
        return ((b - a) * percentage) + a;
    }

    public static double lerp(double a, double b, @FloatRange(from = 0.0d, to = 1.0d) double percentage) {
        return ((b - a) * percentage) + a;
    }

    public static int lerp(int a, int b, @FloatRange(from = 0.0d, to = 1.0d) float percentage) {
        return (int) (((float) a) + (((float) (b - a)) * percentage));
    }

    static int floorMod(float x, float y) {
        return floorMod((int) x, (int) y);
    }

    private static int floorMod(int x, int y) {
        return x - (floorDiv(x, y) * y);
    }

    private static int floorDiv(int x, int y) {
        int r = x / y;
        if ((x ^ y) >= 0 || r * y == x) {
            return r;
        }
        return r - 1;
    }

    public static int clamp(int number, int min, int max) {
        return Math.max(min, Math.min(max, number));
    }

    public static float clamp(float number, float min, float max) {
        return Math.max(min, Math.min(max, number));
    }

    public static boolean contains(float number, float rangeMin, float rangeMax) {
        return number >= rangeMin && number <= rangeMax;
    }

    public static void resolveKeyPath(KeyPath keyPath, int depth, List<KeyPath> accumulator, KeyPath currentPartialKeyPath, KeyPathElementContent content) {
        if (keyPath.fullyResolvesTo(content.getName(), depth)) {
            accumulator.add(currentPartialKeyPath.addKey(content.getName()).resolve(content));
        }
    }
}
