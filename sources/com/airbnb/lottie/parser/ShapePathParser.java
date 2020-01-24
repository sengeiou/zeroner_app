package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.content.ShapePath;
import java.io.IOException;

class ShapePathParser {
    private ShapePathParser() {
    }

    static ShapePath parse(JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        int ind = 0;
        AnimatableShapeValue shape = null;
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 3432:
                    if (nextName.equals("ks")) {
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
                case 104415:
                    if (nextName.equals("ind")) {
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
                    ind = reader.nextInt();
                    break;
                case 2:
                    shape = AnimatableValueParser.parseShapeData(reader, composition);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new ShapePath(name, ind, shape);
    }
}
