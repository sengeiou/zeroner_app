package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatableScaleValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.value.ScaleXY;
import java.io.IOException;

public class AnimatableTransformParser {
    private AnimatableTransformParser() {
    }

    public static AnimatableTransform parse(JsonReader reader, LottieComposition composition) throws IOException {
        boolean isObject;
        AnimatablePathValue anchorPoint = null;
        AnimatableValue<PointF, PointF> position = null;
        AnimatableScaleValue scale = null;
        AnimatableFloatValue rotation = null;
        AnimatableIntegerValue opacity = null;
        AnimatableFloatValue startOpacity = null;
        AnimatableFloatValue endOpacity = null;
        if (reader.peek() == JsonToken.BEGIN_OBJECT) {
            isObject = true;
        } else {
            isObject = false;
        }
        if (isObject) {
            reader.beginObject();
        }
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
                case 111:
                    if (nextName.equals("o")) {
                        c = 5;
                        break;
                    }
                    break;
                case 112:
                    if (nextName.equals("p")) {
                        c = 1;
                        break;
                    }
                    break;
                case 114:
                    if (nextName.equals("r")) {
                        c = 4;
                        break;
                    }
                    break;
                case 115:
                    if (nextName.equals("s")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3242:
                    if (nextName.equals("eo")) {
                        c = 7;
                        break;
                    }
                    break;
                case 3656:
                    if (nextName.equals("rz")) {
                        c = 3;
                        break;
                    }
                    break;
                case 3676:
                    if (nextName.equals("so")) {
                        c = 6;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    reader.beginObject();
                    while (reader.hasNext()) {
                        if (reader.nextName().equals("k")) {
                            anchorPoint = AnimatablePathValueParser.parse(reader, composition);
                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                    continue;
                case 1:
                    position = AnimatablePathValueParser.parseSplitPath(reader, composition);
                    continue;
                case 2:
                    scale = AnimatableValueParser.parseScale(reader, composition);
                    continue;
                case 3:
                    composition.addWarning("Lottie doesn't support 3D layers.");
                    break;
                case 4:
                    break;
                case 5:
                    opacity = AnimatableValueParser.parseInteger(reader, composition);
                    continue;
                case 6:
                    startOpacity = AnimatableValueParser.parseFloat(reader, composition, false);
                    continue;
                case 7:
                    endOpacity = AnimatableValueParser.parseFloat(reader, composition, false);
                    continue;
                default:
                    reader.skipValue();
                    continue;
            }
            rotation = AnimatableValueParser.parseFloat(reader, composition, false);
        }
        if (isObject) {
            reader.endObject();
        }
        if (anchorPoint == null) {
            Log.w(L.TAG, "Layer has no transform property. You may be using an unsupported layer type such as a camera.");
            anchorPoint = new AnimatablePathValue();
        }
        if (scale == null) {
            scale = new AnimatableScaleValue(new ScaleXY(1.0f, 1.0f));
        }
        if (opacity == null) {
            opacity = new AnimatableIntegerValue();
        }
        return new AnimatableTransform(anchorPoint, position, scale, rotation, opacity, startOpacity, endOpacity);
    }
}
