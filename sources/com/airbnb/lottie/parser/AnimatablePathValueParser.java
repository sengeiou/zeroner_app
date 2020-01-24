package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.util.JsonReader;
import android.util.JsonToken;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatableSplitDimensionPathValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.tencent.bugly.beta.tinker.TinkerReport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimatablePathValueParser {
    private AnimatablePathValueParser() {
    }

    public static AnimatablePathValue parse(JsonReader reader, LottieComposition composition) throws IOException {
        List<Keyframe<PointF>> keyframes = new ArrayList<>();
        if (reader.peek() == JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
            while (reader.hasNext()) {
                keyframes.add(PathKeyframeParser.parse(reader, composition));
            }
            reader.endArray();
            KeyframesParser.setEndFrames(keyframes);
        } else {
            keyframes.add(new Keyframe(JsonUtils.jsonToPoint(reader, Utils.dpScale())));
        }
        return new AnimatablePathValue(keyframes);
    }

    static AnimatableValue<PointF, PointF> parseSplitPath(JsonReader reader, LottieComposition composition) throws IOException {
        AnimatablePathValue pathAnimation = null;
        AnimatableFloatValue xAnimation = null;
        AnimatableFloatValue yAnimation = null;
        boolean hasExpressions = false;
        reader.beginObject();
        while (reader.peek() != JsonToken.END_OBJECT) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 107:
                    if (nextName.equals("k")) {
                        c = 0;
                        break;
                    }
                    break;
                case 120:
                    if (nextName.equals("x")) {
                        c = 1;
                        break;
                    }
                    break;
                case TinkerReport.KEY_APPLIED_DEXOPT_OTHER /*121*/:
                    if (nextName.equals("y")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    pathAnimation = parse(reader, composition);
                    break;
                case 1:
                    if (reader.peek() != JsonToken.STRING) {
                        xAnimation = AnimatableValueParser.parseFloat(reader, composition);
                        break;
                    } else {
                        hasExpressions = true;
                        reader.skipValue();
                        break;
                    }
                case 2:
                    if (reader.peek() != JsonToken.STRING) {
                        yAnimation = AnimatableValueParser.parseFloat(reader, composition);
                        break;
                    } else {
                        hasExpressions = true;
                        reader.skipValue();
                        break;
                    }
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        if (hasExpressions) {
            composition.addWarning("Lottie doesn't support expressions.");
        }
        return pathAnimation != null ? pathAnimation : new AnimatableSplitDimensionPathValue(xAnimation, yAnimation);
    }
}
