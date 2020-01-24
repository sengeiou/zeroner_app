package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.github.mikephil.charting.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FontCharacterParser {
    private FontCharacterParser() {
    }

    static FontCharacter parse(JsonReader reader, LottieComposition composition) throws IOException {
        char character = 0;
        int size = 0;
        double width = Utils.DOUBLE_EPSILON;
        String style = null;
        String fontFamily = null;
        List<ShapeGroup> shapes = new ArrayList<>();
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case -1866931350:
                    if (nextName.equals("fFamily")) {
                        c = 4;
                        break;
                    }
                    break;
                case 119:
                    if (nextName.equals("w")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3173:
                    if (nextName.equals("ch")) {
                        c = 0;
                        break;
                    }
                    break;
                case 3076010:
                    if (nextName.equals("data")) {
                        c = 5;
                        break;
                    }
                    break;
                case 3530753:
                    if (nextName.equals("size")) {
                        c = 1;
                        break;
                    }
                    break;
                case 109780401:
                    if (nextName.equals("style")) {
                        c = 3;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    character = reader.nextString().charAt(0);
                    break;
                case 1:
                    size = reader.nextInt();
                    break;
                case 2:
                    width = reader.nextDouble();
                    break;
                case 3:
                    style = reader.nextString();
                    break;
                case 4:
                    fontFamily = reader.nextString();
                    break;
                case 5:
                    reader.beginObject();
                    while (reader.hasNext()) {
                        if ("shapes".equals(reader.nextName())) {
                            reader.beginArray();
                            while (reader.hasNext()) {
                                shapes.add((ShapeGroup) ContentModelParser.parse(reader, composition));
                            }
                            reader.endArray();
                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new FontCharacter(shapes, character, size, width, style, fontFamily);
    }
}
