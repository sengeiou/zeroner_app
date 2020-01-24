package com.airbnb.lottie.utils;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.L;
import com.airbnb.lottie.animation.content.TrimPathContent;
import java.io.Closeable;

public final class Utils {
    public static final int SECOND_IN_NANOS = 1000000000;
    private static final float SQRT_2 = ((float) Math.sqrt(2.0d));
    private static float dpScale = -1.0f;
    private static final PathMeasure pathMeasure = new PathMeasure();
    private static final float[] points = new float[4];
    private static final Path tempPath = new Path();
    private static final Path tempPath2 = new Path();

    private Utils() {
    }

    public static Path createPath(PointF startPoint, PointF endPoint, PointF cp1, PointF cp2) {
        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        if (cp1 == null || cp2 == null || (cp1.length() == 0.0f && cp2.length() == 0.0f)) {
            path.lineTo(endPoint.x, endPoint.y);
        } else {
            path.cubicTo(startPoint.x + cp1.x, startPoint.y + cp1.y, endPoint.x + cp2.x, endPoint.y + cp2.y, endPoint.x, endPoint.y);
        }
        return path;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }

    public static float getScale(Matrix matrix) {
        points[0] = 0.0f;
        points[1] = 0.0f;
        points[2] = SQRT_2;
        points[3] = SQRT_2;
        matrix.mapPoints(points);
        return ((float) Math.hypot((double) (points[2] - points[0]), (double) (points[3] - points[1]))) / 2.0f;
    }

    public static void applyTrimPathIfNeeded(Path path, @Nullable TrimPathContent trimPath) {
        if (trimPath != null) {
            applyTrimPathIfNeeded(path, ((Float) trimPath.getStart().getValue()).floatValue() / 100.0f, ((Float) trimPath.getEnd().getValue()).floatValue() / 100.0f, ((Float) trimPath.getOffset().getValue()).floatValue() / 360.0f);
        }
    }

    public static void applyTrimPathIfNeeded(Path path, float startValue, float endValue, float offsetValue) {
        L.beginSection("applyTrimPathIfNeeded");
        pathMeasure.setPath(path, false);
        float length = pathMeasure.getLength();
        if (startValue == 1.0f && endValue == 0.0f) {
            L.endSection("applyTrimPathIfNeeded");
        } else if (length < 1.0f || ((double) Math.abs((endValue - startValue) - 1.0f)) < 0.01d) {
            L.endSection("applyTrimPathIfNeeded");
        } else {
            float start = length * startValue;
            float end = length * endValue;
            float offset = offsetValue * length;
            float newStart = Math.min(start, end) + offset;
            float newEnd = Math.max(start, end) + offset;
            if (newStart >= length && newEnd >= length) {
                newStart = (float) MiscUtils.floorMod(newStart, length);
                newEnd = (float) MiscUtils.floorMod(newEnd, length);
            }
            if (newStart < 0.0f) {
                newStart = (float) MiscUtils.floorMod(newStart, length);
            }
            if (newEnd < 0.0f) {
                newEnd = (float) MiscUtils.floorMod(newEnd, length);
            }
            if (newStart == newEnd) {
                path.reset();
                L.endSection("applyTrimPathIfNeeded");
                return;
            }
            if (newStart >= newEnd) {
                newStart -= length;
            }
            tempPath.reset();
            pathMeasure.getSegment(newStart, newEnd, tempPath, true);
            if (newEnd > length) {
                tempPath2.reset();
                pathMeasure.getSegment(0.0f, newEnd % length, tempPath2, true);
                tempPath.addPath(tempPath2);
            } else if (newStart < 0.0f) {
                tempPath2.reset();
                pathMeasure.getSegment(length + newStart, length, tempPath2, true);
                tempPath.addPath(tempPath2);
            }
            path.set(tempPath);
            L.endSection("applyTrimPathIfNeeded");
        }
    }

    public static boolean isAtLeastVersion(int major, int minor, int patch, int minMajor, int minMinor, int minPatch) {
        if (major < minMajor) {
            return false;
        }
        if (major > minMajor) {
            return true;
        }
        if (minor < minMinor) {
            return false;
        }
        if (minor > minMinor || patch >= minPatch) {
            return true;
        }
        return false;
    }

    public static int hashFor(float a, float b, float c, float d) {
        int result = 17;
        if (a != 0.0f) {
            result = (int) (((float) 527) * a);
        }
        if (b != 0.0f) {
            result = (int) (((float) (result * 31)) * b);
        }
        if (c != 0.0f) {
            result = (int) (((float) (result * 31)) * c);
        }
        if (d != 0.0f) {
            return (int) (((float) (result * 31)) * d);
        }
        return result;
    }

    public static float dpScale() {
        if (dpScale == -1.0f) {
            dpScale = Resources.getSystem().getDisplayMetrics().density;
        }
        return dpScale;
    }
}
