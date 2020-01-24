package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.model.content.ShapeStroke.LineCapType;
import com.airbnb.lottie.model.content.ShapeStroke.LineJoinType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ShapeStrokeParser {
    private ShapeStrokeParser() {
    }

    static ShapeStroke parse(JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        AnimatableColorValue color = null;
        AnimatableFloatValue width = null;
        AnimatableIntegerValue opacity = null;
        LineCapType capType = null;
        LineJoinType joinType = null;
        AnimatableFloatValue offset = null;
        List<AnimatableFloatValue> lineDashPattern = new ArrayList<>();
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
                case 100:
                    if (nextName.equals("d")) {
                        c = 6;
                        break;
                    }
                    break;
                case 111:
                    if (nextName.equals("o")) {
                        c = 3;
                        break;
                    }
                    break;
                case 119:
                    if (nextName.equals("w")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3447:
                    if (nextName.equals("lc")) {
                        c = 4;
                        break;
                    }
                    break;
                case 3454:
                    if (nextName.equals("lj")) {
                        c = 5;
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
                    width = AnimatableValueParser.parseFloat(reader, composition);
                    break;
                case 3:
                    opacity = AnimatableValueParser.parseInteger(reader, composition);
                    break;
                case 4:
                    capType = LineCapType.values()[reader.nextInt() - 1];
                    break;
                case 5:
                    joinType = LineJoinType.values()[reader.nextInt() - 1];
                    break;
                case 6:
                    reader.beginArray();
                    while (reader.hasNext()) {
                        String n = null;
                        AnimatableFloatValue val = null;
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String nextName2 = reader.nextName();
                            char c2 = 65535;
                            switch (nextName2.hashCode()) {
                                case 110:
                                    if (nextName2.equals("n")) {
                                        c2 = 0;
                                        break;
                                    }
                                    break;
                                case 118:
                                    if (nextName2.equals("v")) {
                                        c2 = 1;
                                        break;
                                    }
                                    break;
                            }
                            switch (c2) {
                                case 0:
                                    n = reader.nextString();
                                    break;
                                case 1:
                                    val = AnimatableValueParser.parseFloat(reader, composition);
                                    break;
                                default:
                                    reader.skipValue();
                                    break;
                            }
                        }
                        reader.endObject();
                        char c3 = 65535;
                        switch (n.hashCode()) {
                            case 100:
                                if (n.equals("d")) {
                                    c3 = 1;
                                    break;
                                }
                                break;
                            case 103:
                                if (n.equals("g")) {
                                    c3 = 2;
                                    break;
                                }
                                break;
                            case 111:
                                if (n.equals("o")) {
                                    c3 = 0;
                                    break;
                                }
                                break;
                        }
                        switch (c3) {
                            case 0:
                                offset = val;
                                break;
                            case 1:
                            case 2:
                                lineDashPattern.add(val);
                                break;
                        }
                    }
                    reader.endArray();
                    if (lineDashPattern.size() != 1) {
                        break;
                    } else {
                        lineDashPattern.add(lineDashPattern.get(0));
                        break;
                    }
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new ShapeStroke(name, offset, lineDashPattern, color, opacity, width, capType, joinType);
    }
}
