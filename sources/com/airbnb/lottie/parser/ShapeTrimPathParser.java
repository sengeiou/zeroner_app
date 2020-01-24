package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import java.io.IOException;

class ShapeTrimPathParser {
    private ShapeTrimPathParser() {
    }

    static ShapeTrimPath parse(JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        Type type = null;
        AnimatableFloatValue start = null;
        AnimatableFloatValue end = null;
        AnimatableFloatValue offset = null;
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 101:
                    if (nextName.equals("e")) {
                        c = 1;
                        break;
                    }
                    break;
                case 109:
                    if (nextName.equals("m")) {
                        c = 4;
                        break;
                    }
                    break;
                case 111:
                    if (nextName.equals("o")) {
                        c = 2;
                        break;
                    }
                    break;
                case 115:
                    if (nextName.equals("s")) {
                        c = 0;
                        break;
                    }
                    break;
                case 3519:
                    if (nextName.equals("nm")) {
                        c = 3;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    start = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 1:
                    end = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 2:
                    offset = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 3:
                    name = reader.nextString();
                    break;
                case 4:
                    type = Type.forId(reader.nextInt());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new ShapeTrimPath(name, type, start, end, offset);
    }
}
