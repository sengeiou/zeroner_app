package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.GradientStroke;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.ShapeStroke.LineCapType;
import com.airbnb.lottie.model.content.ShapeStroke.LineJoinType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GradientStrokeParser {
    private GradientStrokeParser() {
    }

    static GradientStroke parse(JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        AnimatableGradientColorValue color = null;
        AnimatableIntegerValue opacity = null;
        GradientType gradientType = null;
        AnimatablePointValue startPoint = null;
        AnimatablePointValue endPoint = null;
        AnimatableFloatValue width = null;
        LineCapType capType = null;
        LineJoinType joinType = null;
        AnimatableFloatValue offset = null;
        List<AnimatableFloatValue> lineDashPattern = new ArrayList<>();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 100:
                    if (nextName.equals("d")) {
                        c = 9;
                        break;
                    }
                    break;
                case 101:
                    if (nextName.equals("e")) {
                        c = 5;
                        break;
                    }
                    break;
                case 103:
                    if (nextName.equals("g")) {
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
                case 115:
                    if (nextName.equals("s")) {
                        c = 4;
                        break;
                    }
                    break;
                case 116:
                    if (nextName.equals("t")) {
                        c = 3;
                        break;
                    }
                    break;
                case 119:
                    if (nextName.equals("w")) {
                        c = 6;
                        break;
                    }
                    break;
                case 3447:
                    if (nextName.equals("lc")) {
                        c = 7;
                        break;
                    }
                    break;
                case 3454:
                    if (nextName.equals("lj")) {
                        c = 8;
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
                    int points = -1;
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String nextName2 = reader.nextName();
                        char c2 = 65535;
                        switch (nextName2.hashCode()) {
                            case 107:
                                if (nextName2.equals("k")) {
                                    c2 = 1;
                                    break;
                                }
                                break;
                            case 112:
                                if (nextName2.equals("p")) {
                                    c2 = 0;
                                    break;
                                }
                                break;
                        }
                        switch (c2) {
                            case 0:
                                points = reader.nextInt();
                                break;
                            case 1:
                                color = AnimatableValueParser.parseGradientColor(reader, composition, points);
                                break;
                            default:
                                reader.skipValue();
                                break;
                        }
                    }
                    reader.endObject();
                    break;
                case 2:
                    opacity = AnimatableValueParser.parseInteger(reader, composition);
                    break;
                case 3:
                    if (reader.nextInt() != 1) {
                        gradientType = GradientType.Radial;
                        break;
                    } else {
                        gradientType = GradientType.Linear;
                        break;
                    }
                case 4:
                    startPoint = AnimatableValueParser.parsePoint(reader, composition);
                    break;
                case 5:
                    endPoint = AnimatableValueParser.parsePoint(reader, composition);
                    break;
                case 6:
                    width = AnimatableValueParser.parseFloat(reader, composition);
                    break;
                case 7:
                    capType = LineCapType.values()[reader.nextInt() - 1];
                    break;
                case 8:
                    joinType = LineJoinType.values()[reader.nextInt() - 1];
                    break;
                case 9:
                    reader.beginArray();
                    while (reader.hasNext()) {
                        String n = null;
                        AnimatableFloatValue val = null;
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String nextName3 = reader.nextName();
                            char c3 = 65535;
                            switch (nextName3.hashCode()) {
                                case 110:
                                    if (nextName3.equals("n")) {
                                        c3 = 0;
                                        break;
                                    }
                                    break;
                                case 118:
                                    if (nextName3.equals("v")) {
                                        c3 = 1;
                                        break;
                                    }
                                    break;
                            }
                            switch (c3) {
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
                        if (n.equals("o")) {
                            offset = val;
                        } else if (n.equals("d") || n.equals("g")) {
                            lineDashPattern.add(val);
                        }
                    }
                    reader.endArray();
                    if (lineDashPattern.size() != 1) {
                        break;
                    } else {
                        lineDashPattern.add(lineDashPattern.get(0));
                        break;
                    }
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        return new GradientStroke(name, gradientType, color, opacity, startPoint, endPoint, width, capType, joinType, lineDashPattern, offset);
    }
}
