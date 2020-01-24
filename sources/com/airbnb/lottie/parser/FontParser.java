package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.model.Font;
import java.io.IOException;

class FontParser {
    private FontParser() {
    }

    static Font parse(JsonReader reader) throws IOException {
        String family = null;
        String name = null;
        String style = null;
        float ascent = 0.0f;
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case -1866931350:
                    if (nextName.equals("fFamily")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1408684838:
                    if (nextName.equals("ascent")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1294566165:
                    if (nextName.equals("fStyle")) {
                        c = 2;
                        break;
                    }
                    break;
                case 96619537:
                    if (nextName.equals("fName")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    family = reader.nextString();
                    break;
                case 1:
                    name = reader.nextString();
                    break;
                case 2:
                    style = reader.nextString();
                    break;
                case 3:
                    ascent = (float) reader.nextDouble();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Font(family, name, style, ascent);
    }
}
