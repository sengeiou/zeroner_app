package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.Repeater;
import java.io.IOException;

class RepeaterParser {
    private RepeaterParser() {
    }

    static Repeater parse(JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        AnimatableFloatValue copies = null;
        AnimatableFloatValue offset = null;
        AnimatableTransform transform = null;
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 99:
                    if (nextName.equals("c")) {
                        c = 1;
                        break;
                    }
                    break;
                case 111:
                    if (nextName.equals("o")) {
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
                case 3710:
                    if (nextName.equals("tr")) {
                        c = 3;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    name = reader.nextString();
                    break;
                case 1:
                    copies = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 2:
                    offset = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 3:
                    transform = AnimatableTransformParser.parse(reader, composition);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new Repeater(name, copies, offset, transform);
    }
}
