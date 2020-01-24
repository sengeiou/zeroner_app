package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.JsonReader;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.io.IOException;
import java.lang.ref.WeakReference;

class KeyframeParser {
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final float MAX_CP_VALUE = 100.0f;
    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache;

    KeyframeParser() {
    }

    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache() {
        if (pathInterpolatorCache == null) {
            pathInterpolatorCache = new SparseArrayCompat<>();
        }
        return pathInterpolatorCache;
    }

    @Nullable
    private static WeakReference<Interpolator> getInterpolator(int hash) {
        WeakReference<Interpolator> weakReference;
        synchronized (KeyframeParser.class) {
            weakReference = (WeakReference) pathInterpolatorCache().get(hash);
        }
        return weakReference;
    }

    private static void putInterpolator(int hash, WeakReference<Interpolator> interpolator) {
        synchronized (KeyframeParser.class) {
            pathInterpolatorCache.put(hash, interpolator);
        }
    }

    static <T> Keyframe<T> parse(JsonReader reader, LottieComposition composition, float scale, ValueParser<T> valueParser, boolean animated) throws IOException {
        if (animated) {
            return parseKeyframe(composition, reader, scale, valueParser);
        }
        return parseStaticValue(reader, scale, valueParser);
    }

    private static <T> Keyframe<T> parseKeyframe(LottieComposition composition, JsonReader reader, float scale, ValueParser<T> valueParser) throws IOException {
        Object obj;
        PointF cp1 = null;
        PointF cp2 = null;
        float startFrame = 0.0f;
        boolean hold = false;
        Interpolator interpolator = null;
        PointF pathCp1 = null;
        PointF pathCp2 = null;
        reader.beginObject();
        Object obj2 = null;
        Object obj3 = null;
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 101:
                    if (nextName.equals("e")) {
                        c = 2;
                        break;
                    }
                    break;
                case 104:
                    if (nextName.equals("h")) {
                        c = 5;
                        break;
                    }
                    break;
                case 105:
                    if (nextName.equals("i")) {
                        c = 4;
                        break;
                    }
                    break;
                case 111:
                    if (nextName.equals("o")) {
                        c = 3;
                        break;
                    }
                    break;
                case 115:
                    if (nextName.equals("s")) {
                        c = 1;
                        break;
                    }
                    break;
                case 116:
                    if (nextName.equals("t")) {
                        c = 0;
                        break;
                    }
                    break;
                case 3701:
                    if (nextName.equals("ti")) {
                        c = 7;
                        break;
                    }
                    break;
                case 3707:
                    if (nextName.equals("to")) {
                        c = 6;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    startFrame = (float) reader.nextDouble();
                    break;
                case 1:
                    obj3 = valueParser.parse(reader, scale);
                    break;
                case 2:
                    obj2 = valueParser.parse(reader, scale);
                    break;
                case 3:
                    cp1 = JsonUtils.jsonToPoint(reader, scale);
                    break;
                case 4:
                    cp2 = JsonUtils.jsonToPoint(reader, scale);
                    break;
                case 5:
                    if (reader.nextInt() != 1) {
                        hold = false;
                        break;
                    } else {
                        hold = true;
                        break;
                    }
                case 6:
                    pathCp1 = JsonUtils.jsonToPoint(reader, scale);
                    break;
                case 7:
                    pathCp2 = JsonUtils.jsonToPoint(reader, scale);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        if (hold) {
            interpolator = LINEAR_INTERPOLATOR;
            obj = obj3;
        } else if (cp1 == null || cp2 == null) {
            interpolator = LINEAR_INTERPOLATOR;
            obj = obj2;
        } else {
            cp1.x = MiscUtils.clamp(cp1.x, -scale, scale);
            cp1.y = MiscUtils.clamp(cp1.y, -100.0f, (float) MAX_CP_VALUE);
            cp2.x = MiscUtils.clamp(cp2.x, -scale, scale);
            cp2.y = MiscUtils.clamp(cp2.y, -100.0f, (float) MAX_CP_VALUE);
            int hash = Utils.hashFor(cp1.x, cp1.y, cp2.x, cp2.y);
            WeakReference<Interpolator> interpolatorRef = getInterpolator(hash);
            if (interpolatorRef != null) {
                interpolator = (Interpolator) interpolatorRef.get();
            }
            if (interpolatorRef == null || interpolator == null) {
                interpolator = PathInterpolatorCompat.create(cp1.x / scale, cp1.y / scale, cp2.x / scale, cp2.y / scale);
                try {
                    putInterpolator(hash, new WeakReference(interpolator));
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
            obj = obj2;
        }
        Keyframe<T> keyframe = new Keyframe<>(composition, obj3, obj, interpolator, startFrame, null);
        keyframe.pathCp1 = pathCp1;
        keyframe.pathCp2 = pathCp2;
        return keyframe;
    }

    private static <T> Keyframe<T> parseStaticValue(JsonReader reader, float scale, ValueParser<T> valueParser) throws IOException {
        return new Keyframe<>(valueParser.parse(reader, scale));
    }
}
