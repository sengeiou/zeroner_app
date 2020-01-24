package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapeGroup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ShapeGroupParser {
    private ShapeGroupParser() {
    }

    static ShapeGroup parse(JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        List<ContentModel> items = new ArrayList<>();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 3371:
                    if (nextName.equals("it")) {
                        c = 1;
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
                    reader.beginArray();
                    while (reader.hasNext()) {
                        ContentModel newItem = ContentModelParser.parse(reader, composition);
                        if (newItem != null) {
                            items.add(newItem);
                        }
                    }
                    reader.endArray();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new ShapeGroup(name, items);
    }
}
