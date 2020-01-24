package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.RectangleShape;
import java.io.IOException;

class RectangleShapeParser {
    private RectangleShapeParser() {
    }

    static RectangleShape parse(JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        AnimatableValue<PointF, PointF> position = null;
        AnimatablePointValue size = null;
        AnimatableFloatValue roundedness = null;
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 112:
                    if (nextName.equals("p")) {
                        c = 1;
                        break;
                    }
                    break;
                case 114:
                    if (nextName.equals("r")) {
                        c = 3;
                        break;
                    }
                    break;
                case 115:
                    if (nextName.equals("s")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3519:
                    if (nextName.equals("nm")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    name = reader.nextString();
                    break;
                case 1:
                    position = AnimatablePathValueParser.parseSplitPath(reader, composition);
                    break;
                case 2:
                    size = AnimatableValueParser.parsePoint(reader, composition);
                    break;
                case 3:
                    roundedness = AnimatableValueParser.parseFloat(reader, composition);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new RectangleShape(name, position, size, roundedness);
    }
}
