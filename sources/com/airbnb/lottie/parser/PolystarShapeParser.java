package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.PolystarShape.Type;
import java.io.IOException;

class PolystarShapeParser {
    private PolystarShapeParser() {
    }

    static PolystarShape parse(JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        Type type = null;
        AnimatableFloatValue points = null;
        AnimatableValue<PointF, PointF> position = null;
        AnimatableFloatValue rotation = null;
        AnimatableFloatValue outerRadius = null;
        AnimatableFloatValue outerRoundedness = null;
        AnimatableFloatValue innerRadius = null;
        AnimatableFloatValue innerRoundedness = null;
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 112:
                    if (nextName.equals("p")) {
                        c = 3;
                        break;
                    }
                    break;
                case 114:
                    if (nextName.equals("r")) {
                        c = 4;
                        break;
                    }
                    break;
                case 3369:
                    if (nextName.equals("ir")) {
                        c = 7;
                        break;
                    }
                    break;
                case 3370:
                    if (nextName.equals("is")) {
                        c = 8;
                        break;
                    }
                    break;
                case 3519:
                    if (nextName.equals("nm")) {
                        c = 0;
                        break;
                    }
                    break;
                case 3555:
                    if (nextName.equals("or")) {
                        c = 5;
                        break;
                    }
                    break;
                case 3556:
                    if (nextName.equals("os")) {
                        c = 6;
                        break;
                    }
                    break;
                case 3588:
                    if (nextName.equals("pt")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3686:
                    if (nextName.equals("sy")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    name = reader.nextString();
                    break;
                case 1:
                    type = Type.forValue(reader.nextInt());
                    break;
                case 2:
                    points = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 3:
                    position = AnimatablePathValueParser.parseSplitPath(reader, composition);
                    break;
                case 4:
                    rotation = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 5:
                    outerRadius = AnimatableValueParser.parseFloat(reader, composition);
                    break;
                case 6:
                    outerRoundedness = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 7:
                    innerRadius = AnimatableValueParser.parseFloat(reader, composition);
                    break;
                case 8:
                    innerRoundedness = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new PolystarShape(name, type, points, position, rotation, innerRadius, outerRadius, innerRoundedness, outerRoundedness);
    }
}
