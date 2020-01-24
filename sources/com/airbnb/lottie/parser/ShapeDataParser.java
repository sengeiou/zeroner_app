package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.util.JsonReader;
import android.util.JsonToken;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.utils.MiscUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapeDataParser implements ValueParser<ShapeData> {
    public static final ShapeDataParser INSTANCE = new ShapeDataParser();

    private ShapeDataParser() {
    }

    public ShapeData parse(JsonReader reader, float scale) throws IOException {
        if (reader.peek() == JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
        }
        boolean closed = false;
        List<PointF> pointsArray = null;
        List<PointF> inTangents = null;
        List<PointF> outTangents = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 99:
                    if (nextName.equals("c")) {
                        c = 0;
                        break;
                    }
                    break;
                case 105:
                    if (nextName.equals("i")) {
                        c = 2;
                        break;
                    }
                    break;
                case 111:
                    if (nextName.equals("o")) {
                        c = 3;
                        break;
                    }
                    break;
                case 118:
                    if (nextName.equals("v")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    closed = reader.nextBoolean();
                    break;
                case 1:
                    pointsArray = JsonUtils.jsonToPoints(reader, scale);
                    break;
                case 2:
                    inTangents = JsonUtils.jsonToPoints(reader, scale);
                    break;
                case 3:
                    outTangents = JsonUtils.jsonToPoints(reader, scale);
                    break;
            }
        }
        reader.endObject();
        if (reader.peek() == JsonToken.END_ARRAY) {
            reader.endArray();
        }
        if (pointsArray == null || inTangents == null || outTangents == null) {
            throw new IllegalArgumentException("Shape data was missing information.");
        } else if (pointsArray.isEmpty()) {
            return new ShapeData(new PointF(), false, Collections.emptyList());
        } else {
            int length = pointsArray.size();
            PointF initialPoint = (PointF) pointsArray.get(0);
            List<CubicCurveData> curves = new ArrayList<>(length);
            for (int i = 1; i < length; i++) {
                PointF vertex = (PointF) pointsArray.get(i);
                curves.add(new CubicCurveData(MiscUtils.addPoints((PointF) pointsArray.get(i - 1), (PointF) outTangents.get(i - 1)), MiscUtils.addPoints(vertex, (PointF) inTangents.get(i)), vertex));
            }
            if (closed) {
                PointF vertex2 = (PointF) pointsArray.get(0);
                curves.add(new CubicCurveData(MiscUtils.addPoints((PointF) pointsArray.get(length - 1), (PointF) outTangents.get(length - 1)), MiscUtils.addPoints(vertex2, (PointF) inTangents.get(0)), vertex2));
            }
            return new ShapeData(initialPoint, closed, curves);
        }
    }
}
