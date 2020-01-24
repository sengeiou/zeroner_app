package com.airbnb.lottie.parser;

import android.util.JsonReader;
import android.util.JsonToken;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.value.Keyframe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class KeyframesParser {
    private KeyframesParser() {
    }

    static <T> List<Keyframe<T>> parse(JsonReader reader, LottieComposition composition, float scale, ValueParser<T> valueParser) throws IOException {
        List<Keyframe<T>> keyframes = new ArrayList<>();
        if (reader.peek() == JsonToken.STRING) {
            composition.addWarning("Lottie doesn't support expressions.");
        } else {
            reader.beginObject();
            while (reader.hasNext()) {
                String nextName = reader.nextName();
                char c = 65535;
                switch (nextName.hashCode()) {
                    case 107:
                        if (nextName.equals("k")) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        if (reader.peek() != JsonToken.BEGIN_ARRAY) {
                            keyframes.add(KeyframeParser.parse(reader, composition, scale, valueParser, false));
                            break;
                        } else {
                            reader.beginArray();
                            if (reader.peek() == JsonToken.NUMBER) {
                                keyframes.add(KeyframeParser.parse(reader, composition, scale, valueParser, false));
                            } else {
                                while (reader.hasNext()) {
                                    keyframes.add(KeyframeParser.parse(reader, composition, scale, valueParser, true));
                                }
                            }
                            reader.endArray();
                            break;
                        }
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            setEndFrames(keyframes);
        }
        return keyframes;
    }

    public static void setEndFrames(List<? extends Keyframe<?>> keyframes) {
        int size = keyframes.size();
        for (int i = 0; i < size - 1; i++) {
            ((Keyframe) keyframes.get(i)).endFrame = Float.valueOf(((Keyframe) keyframes.get(i + 1)).startFrame);
        }
        Keyframe<?> lastKeyframe = (Keyframe) keyframes.get(size - 1);
        if (lastKeyframe.startValue == null) {
            keyframes.remove(lastKeyframe);
        }
    }
}
