package com.airbnb.lottie.parser;

import android.util.JsonReader;
import com.airbnb.lottie.model.DocumentData;
import com.github.mikephil.charting.utils.Utils;
import java.io.IOException;

public class DocumentDataParser implements ValueParser<DocumentData> {
    public static final DocumentDataParser INSTANCE = new DocumentDataParser();

    private DocumentDataParser() {
    }

    public DocumentData parse(JsonReader reader, float scale) throws IOException {
        String text = null;
        String fontName = null;
        double size = Utils.DOUBLE_EPSILON;
        int justification = 0;
        int tracking = 0;
        double lineHeight = Utils.DOUBLE_EPSILON;
        double baselineShift = Utils.DOUBLE_EPSILON;
        int fillColor = 0;
        int strokeColor = 0;
        int strokeWidth = 0;
        boolean strokeOverFill = true;
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 102:
                    if (nextName.equals("f")) {
                        c = 1;
                        break;
                    }
                    break;
                case 106:
                    if (nextName.equals("j")) {
                        c = 3;
                        break;
                    }
                    break;
                case 115:
                    if (nextName.equals("s")) {
                        c = 2;
                        break;
                    }
                    break;
                case 116:
                    if (nextName.equals("t")) {
                        c = 0;
                        break;
                    }
                    break;
                case 3261:
                    if (nextName.equals("fc")) {
                        c = 7;
                        break;
                    }
                    break;
                case 3452:
                    if (nextName.equals("lh")) {
                        c = 5;
                        break;
                    }
                    break;
                case 3463:
                    if (nextName.equals("ls")) {
                        c = 6;
                        break;
                    }
                    break;
                case 3543:
                    if (nextName.equals("of")) {
                        c = 10;
                        break;
                    }
                    break;
                case 3664:
                    if (nextName.equals("sc")) {
                        c = 8;
                        break;
                    }
                    break;
                case 3684:
                    if (nextName.equals("sw")) {
                        c = 9;
                        break;
                    }
                    break;
                case 3710:
                    if (nextName.equals("tr")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    text = reader.nextString();
                    break;
                case 1:
                    fontName = reader.nextString();
                    break;
                case 2:
                    size = reader.nextDouble();
                    break;
                case 3:
                    justification = reader.nextInt();
                    break;
                case 4:
                    tracking = reader.nextInt();
                    break;
                case 5:
                    lineHeight = reader.nextDouble();
                    break;
                case 6:
                    baselineShift = reader.nextDouble();
                    break;
                case 7:
                    fillColor = JsonUtils.jsonToColor(reader);
                    break;
                case 8:
                    strokeColor = JsonUtils.jsonToColor(reader);
                    break;
                case 9:
                    strokeWidth = reader.nextInt();
                    break;
                case 10:
                    strokeOverFill = reader.nextBoolean();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new DocumentData(text, fontName, size, justification, tracking, lineHeight, baselineShift, fillColor, strokeColor, strokeWidth, strokeOverFill);
    }
}
