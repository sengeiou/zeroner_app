package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.PointF;
import android.support.annotation.ColorInt;
import android.util.JsonReader;
import android.util.JsonToken;
import com.tencent.bugly.beta.tinker.TinkerReport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class JsonUtils {

    /* renamed from: com.airbnb.lottie.parser.JsonUtils$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$util$JsonToken = new int[JsonToken.values().length];

        static {
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.BEGIN_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private JsonUtils() {
    }

    @ColorInt
    static int jsonToColor(JsonReader reader) throws IOException {
        reader.beginArray();
        int r = (int) (reader.nextDouble() * 255.0d);
        int g = (int) (reader.nextDouble() * 255.0d);
        int b = (int) (reader.nextDouble() * 255.0d);
        while (reader.hasNext()) {
            reader.skipValue();
        }
        reader.endArray();
        return Color.argb(255, r, g, b);
    }

    static List<PointF> jsonToPoints(JsonReader reader, float scale) throws IOException {
        List<PointF> points = new ArrayList<>();
        reader.beginArray();
        while (reader.peek() == JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
            points.add(jsonToPoint(reader, scale));
            reader.endArray();
        }
        reader.endArray();
        return points;
    }

    static PointF jsonToPoint(JsonReader reader, float scale) throws IOException {
        switch (AnonymousClass1.$SwitchMap$android$util$JsonToken[reader.peek().ordinal()]) {
            case 1:
                return jsonNumbersToPoint(reader, scale);
            case 2:
                return jsonArrayToPoint(reader, scale);
            case 3:
                return jsonObjectToPoint(reader, scale);
            default:
                throw new IllegalArgumentException("Unknown point starts with " + reader.peek());
        }
    }

    private static PointF jsonNumbersToPoint(JsonReader reader, float scale) throws IOException {
        float x = (float) reader.nextDouble();
        float y = (float) reader.nextDouble();
        while (reader.hasNext()) {
            reader.skipValue();
        }
        return new PointF(x * scale, y * scale);
    }

    private static PointF jsonArrayToPoint(JsonReader reader, float scale) throws IOException {
        reader.beginArray();
        float x = (float) reader.nextDouble();
        float y = (float) reader.nextDouble();
        while (reader.peek() != JsonToken.END_ARRAY) {
            reader.skipValue();
        }
        reader.endArray();
        return new PointF(x * scale, y * scale);
    }

    private static PointF jsonObjectToPoint(JsonReader reader, float scale) throws IOException {
        float x = 0.0f;
        float y = 0.0f;
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 120:
                    if (nextName.equals("x")) {
                        c = 0;
                        break;
                    }
                    break;
                case TinkerReport.KEY_APPLIED_DEXOPT_OTHER /*121*/:
                    if (nextName.equals("y")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    x = valueFromObject(reader);
                    break;
                case 1:
                    y = valueFromObject(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new PointF(x * scale, y * scale);
    }

    static float valueFromObject(JsonReader reader) throws IOException {
        JsonToken token = reader.peek();
        switch (AnonymousClass1.$SwitchMap$android$util$JsonToken[token.ordinal()]) {
            case 1:
                return (float) reader.nextDouble();
            case 2:
                reader.beginArray();
                float nextDouble = (float) reader.nextDouble();
                while (reader.hasNext()) {
                    reader.skipValue();
                }
                reader.endArray();
                return nextDouble;
            default:
                throw new IllegalArgumentException("Unknown value for token of type " + token);
        }
    }
}
