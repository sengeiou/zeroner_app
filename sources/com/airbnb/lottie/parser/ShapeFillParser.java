package com.airbnb.lottie.parser;

import android.graphics.Path.FillType;
import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.ShapeFill;
import java.io.IOException;

class ShapeFillParser {
    private ShapeFillParser() {
    }

    static ShapeFill parse(JsonReader reader, LottieComposition composition) throws IOException {
        AnimatableColorValue color = null;
        boolean fillEnabled = false;
        AnimatableIntegerValue opacity = null;
        String name = null;
        int fillTypeInt = 1;
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case -396065730:
                    if (nextName.equals("fillEnabled")) {
                        c = 3;
                        break;
                    }
                    break;
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
                case 114:
                    if (nextName.equals("r")) {
                        c = 4;
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
                    color = AnimatableValueParser.parseColor(reader, composition);
                    break;
                case 2:
                    opacity = AnimatableValueParser.parseInteger(reader, composition);
                    break;
                case 3:
                    fillEnabled = reader.nextBoolean();
                    break;
                case 4:
                    fillTypeInt = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new ShapeFill(name, fillEnabled, fillTypeInt == 1 ? FillType.WINDING : FillType.EVEN_ODD, color, opacity);
    }
}
