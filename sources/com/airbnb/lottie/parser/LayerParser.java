package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.JsonReader;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.model.layer.Layer.LayerType;
import com.airbnb.lottie.model.layer.Layer.MatteType;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LayerParser {
    private LayerParser() {
    }

    public static Layer parse(LottieComposition composition) {
        Rect bounds = composition.getBounds();
        return new Layer(Collections.emptyList(), composition, "__container", -1, LayerType.PreComp, -1, null, Collections.emptyList(), new AnimatableTransform(), 0, 0, 0, 0.0f, 0.0f, bounds.width(), bounds.height(), null, null, Collections.emptyList(), MatteType.None, null);
    }

    public static Layer parse(JsonReader reader, LottieComposition composition) throws IOException {
        String layerName = null;
        LayerType layerType = null;
        String refId = null;
        long layerId = 0;
        int solidWidth = 0;
        int solidHeight = 0;
        int solidColor = 0;
        int preCompWidth = 0;
        int preCompHeight = 0;
        long parentId = -1;
        float timeStretch = 1.0f;
        float startFrame = 0.0f;
        float inFrame = 0.0f;
        float outFrame = 0.0f;
        String cl = null;
        MatteType matteType = MatteType.None;
        AnimatableTransform transform = null;
        AnimatableTextFrame text = null;
        AnimatableTextProperties textProperties = null;
        AnimatableFloatValue timeRemapping = null;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case -995424086:
                    if (nextName.equals("parent")) {
                        c = 4;
                        break;
                    }
                    break;
                case -903568142:
                    if (nextName.equals("shapes")) {
                        c = 11;
                        break;
                    }
                    break;
                case 104:
                    if (nextName.equals("h")) {
                        c = 17;
                        break;
                    }
                    break;
                case 116:
                    if (nextName.equals("t")) {
                        c = 12;
                        break;
                    }
                    break;
                case 119:
                    if (nextName.equals("w")) {
                        c = 16;
                        break;
                    }
                    break;
                case 3177:
                    if (nextName.equals("cl")) {
                        c = 21;
                        break;
                    }
                    break;
                case 3233:
                    if (nextName.equals("ef")) {
                        c = 13;
                        break;
                    }
                    break;
                case 3367:
                    if (nextName.equals("ip")) {
                        c = 18;
                        break;
                    }
                    break;
                case 3432:
                    if (nextName.equals("ks")) {
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
                case 3553:
                    if (nextName.equals("op")) {
                        c = 19;
                        break;
                    }
                    break;
                case 3664:
                    if (nextName.equals("sc")) {
                        c = 7;
                        break;
                    }
                    break;
                case 3669:
                    if (nextName.equals("sh")) {
                        c = 6;
                        break;
                    }
                    break;
                case 3679:
                    if (nextName.equals("sr")) {
                        c = 14;
                        break;
                    }
                    break;
                case 3681:
                    if (nextName.equals("st")) {
                        c = 15;
                        break;
                    }
                    break;
                case 3684:
                    if (nextName.equals("sw")) {
                        c = 5;
                        break;
                    }
                    break;
                case 3705:
                    if (nextName.equals("tm")) {
                        c = 20;
                        break;
                    }
                    break;
                case 3712:
                    if (nextName.equals("tt")) {
                        c = 9;
                        break;
                    }
                    break;
                case 3717:
                    if (nextName.equals("ty")) {
                        c = 3;
                        break;
                    }
                    break;
                case 104415:
                    if (nextName.equals("ind")) {
                        c = 1;
                        break;
                    }
                    break;
                case 108390670:
                    if (nextName.equals("refId")) {
                        c = 2;
                        break;
                    }
                    break;
                case 1441620890:
                    if (nextName.equals("masksProperties")) {
                        c = 10;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    layerName = reader.nextString();
                    break;
                case 1:
                    layerId = (long) reader.nextInt();
                    break;
                case 2:
                    refId = reader.nextString();
                    break;
                case 3:
                    int layerTypeInt = reader.nextInt();
                    if (layerTypeInt >= LayerType.Unknown.ordinal()) {
                        layerType = LayerType.Unknown;
                        break;
                    } else {
                        layerType = LayerType.values()[layerTypeInt];
                        break;
                    }
                case 4:
                    parentId = (long) reader.nextInt();
                    break;
                case 5:
                    solidWidth = (int) (((float) reader.nextInt()) * Utils.dpScale());
                    break;
                case 6:
                    solidHeight = (int) (((float) reader.nextInt()) * Utils.dpScale());
                    break;
                case 7:
                    solidColor = Color.parseColor(reader.nextString());
                    break;
                case 8:
                    transform = AnimatableTransformParser.parse(reader, composition);
                    break;
                case 9:
                    matteType = MatteType.values()[reader.nextInt()];
                    break;
                case 10:
                    reader.beginArray();
                    while (reader.hasNext()) {
                        arrayList.add(MaskParser.parse(reader, composition));
                    }
                    reader.endArray();
                    break;
                case 11:
                    reader.beginArray();
                    while (reader.hasNext()) {
                        ContentModel shape = ContentModelParser.parse(reader, composition);
                        if (shape != null) {
                            arrayList2.add(shape);
                        }
                    }
                    reader.endArray();
                    break;
                case 12:
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String nextName2 = reader.nextName();
                        char c2 = 65535;
                        switch (nextName2.hashCode()) {
                            case 97:
                                if (nextName2.equals("a")) {
                                    c2 = 1;
                                    break;
                                }
                                break;
                            case 100:
                                if (nextName2.equals("d")) {
                                    c2 = 0;
                                    break;
                                }
                                break;
                        }
                        switch (c2) {
                            case 0:
                                text = AnimatableValueParser.parseDocumentData(reader, composition);
                                break;
                            case 1:
                                reader.beginArray();
                                if (reader.hasNext()) {
                                    textProperties = AnimatableTextPropertiesParser.parse(reader, composition);
                                }
                                while (reader.hasNext()) {
                                    reader.skipValue();
                                }
                                reader.endArray();
                                break;
                            default:
                                reader.skipValue();
                                break;
                        }
                    }
                    reader.endObject();
                    break;
                case 13:
                    reader.beginArray();
                    ArrayList arrayList3 = new ArrayList();
                    while (reader.hasNext()) {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String nextName3 = reader.nextName();
                            char c3 = 65535;
                            switch (nextName3.hashCode()) {
                                case 3519:
                                    if (nextName3.equals("nm")) {
                                        c3 = 0;
                                        break;
                                    }
                                    break;
                            }
                            switch (c3) {
                                case 0:
                                    arrayList3.add(reader.nextString());
                                    break;
                                default:
                                    reader.skipValue();
                                    break;
                            }
                        }
                        reader.endObject();
                    }
                    reader.endArray();
                    composition.addWarning("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList3);
                    break;
                case 14:
                    timeStretch = (float) reader.nextDouble();
                    break;
                case 15:
                    startFrame = (float) reader.nextDouble();
                    break;
                case 16:
                    preCompWidth = (int) (((float) reader.nextInt()) * Utils.dpScale());
                    break;
                case 17:
                    preCompHeight = (int) (((float) reader.nextInt()) * Utils.dpScale());
                    break;
                case 18:
                    inFrame = (float) reader.nextDouble();
                    break;
                case 19:
                    outFrame = (float) reader.nextDouble();
                    break;
                case 20:
                    timeRemapping = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 21:
                    cl = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        float inFrame2 = inFrame / timeStretch;
        float outFrame2 = outFrame / timeStretch;
        ArrayList arrayList4 = new ArrayList();
        if (inFrame2 > 0.0f) {
            ArrayList arrayList5 = arrayList4;
            arrayList5.add(new Keyframe<>(composition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(inFrame2)));
        }
        if (outFrame2 <= 0.0f) {
            outFrame2 = composition.getEndFrame();
        }
        float outFrame3 = outFrame2 + 1.0f;
        ArrayList arrayList6 = arrayList4;
        arrayList6.add(new Keyframe<>(composition, Float.valueOf(1.0f), Float.valueOf(1.0f), null, inFrame2, Float.valueOf(outFrame3)));
        ArrayList arrayList7 = arrayList4;
        arrayList7.add(new Keyframe<>(composition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, outFrame3, Float.valueOf(Float.MAX_VALUE)));
        if (layerName.endsWith(".ai") || "ai".equals(cl)) {
            composition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList2, composition, layerName, layerId, layerType, parentId, refId, arrayList, transform, solidWidth, solidHeight, solidColor, timeStretch, startFrame, preCompWidth, preCompHeight, text, textProperties, arrayList4, matteType, timeRemapping);
    }
}
