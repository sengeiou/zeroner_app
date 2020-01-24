package com.airbnb.lottie.parser;

import android.util.JsonReader;
import android.util.JsonToken;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.animation.keyframe.PathKeyframe;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;

class PathKeyframeParser {
    private PathKeyframeParser() {
    }

    static PathKeyframe parse(JsonReader reader, LottieComposition composition) throws IOException {
        return new PathKeyframe(composition, KeyframeParser.parse(reader, composition, Utils.dpScale(), PathParser.INSTANCE, reader.peek() == JsonToken.BEGIN_OBJECT));
    }
}
