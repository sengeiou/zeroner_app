package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.util.JsonReader;
import android.util.JsonToken;
import java.io.IOException;

public class PointFParser implements ValueParser<PointF> {
    public static final PointFParser INSTANCE = new PointFParser();

    private PointFParser() {
    }

    public PointF parse(JsonReader reader, float scale) throws IOException {
        JsonToken token = reader.peek();
        if (token == JsonToken.BEGIN_ARRAY) {
            return JsonUtils.jsonToPoint(reader, scale);
        }
        if (token == JsonToken.BEGIN_OBJECT) {
            return JsonUtils.jsonToPoint(reader, scale);
        }
        if (token == JsonToken.NUMBER) {
            PointF pointF = new PointF(((float) reader.nextDouble()) * scale, ((float) reader.nextDouble()) * scale);
            while (reader.hasNext()) {
                reader.skipValue();
            }
            return pointF;
        }
        throw new IllegalArgumentException("Cannot convert json to point. Next token is " + token);
    }
}
