package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.CircleShape;
import java.io.IOException;

class CircleShapeParser {
    private CircleShapeParser() {
    }

    static CircleShape parse(JsonReader reader, LottieComposition composition, int d) throws IOException {
        boolean reversed;
        String name = null;
        AnimatableValue<PointF, PointF> position = null;
        AnimatablePointValue size = null;
        if (d == 3) {
            reversed = true;
        } else {
            reversed = false;
        }
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 100:
                    if (nextName.equals("d")) {
                        c = 3;
                        break;
                    }
                    break;
                case 112:
                    if (nextName.equals("p")) {
                        c = 1;
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
                    if (reader.nextInt() != 3) {
                        reversed = false;
                        break;
                    } else {
                        reversed = true;
                        break;
                    }
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new CircleShape(name, position, size, reversed);
    }
}
