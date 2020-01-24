package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import java.io.IOException;

public class AnimatableTextPropertiesParser {
    private AnimatableTextPropertiesParser() {
    }

    public static AnimatableTextProperties parse(JsonReader reader, LottieComposition composition) throws IOException {
        AnimatableTextProperties anim = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 97:
                    if (nextName.equals("a")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    anim = parseAnimatableTextProperties(reader, composition);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        if (anim == null) {
            return new AnimatableTextProperties(null, null, null, null);
        }
        return anim;
    }

    private static AnimatableTextProperties parseAnimatableTextProperties(JsonReader reader, LottieComposition composition) throws IOException {
        AnimatableColorValue color = null;
        AnimatableColorValue stroke = null;
        AnimatableFloatValue strokeWidth = null;
        AnimatableFloatValue tracking = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 116:
                    if (nextName.equals("t")) {
                        c = 3;
                        break;
                    }
                    break;
                case 3261:
                    if (nextName.equals("fc")) {
                        c = 0;
                        break;
                    }
                    break;
                case 3664:
                    if (nextName.equals("sc")) {
                        c = 1;
                        break;
                    }
                    break;
                case 3684:
                    if (nextName.equals("sw")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    color = AnimatableValueParser.parseColor(reader, composition);
                    break;
                case 1:
                    stroke = AnimatableValueParser.parseColor(reader, composition);
                    break;
                case 2:
                    strokeWidth = AnimatableValueParser.parseFloat(reader, composition);
                    break;
                case 3:
                    tracking = AnimatableValueParser.parseFloat(reader, composition);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new AnimatableTextProperties(color, stroke, strokeWidth, tracking);
    }
}
