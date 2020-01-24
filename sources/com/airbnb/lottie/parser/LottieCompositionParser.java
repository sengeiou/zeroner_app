package com.airbnb.lottie.parser;

import android.graphics.Rect;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SparseArrayCompat;
import android.util.JsonReader;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.model.layer.Layer.LayerType;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottieCompositionParser {
    private LottieCompositionParser() {
    }

    public static LottieComposition parse(JsonReader reader) throws IOException {
        float scale = Utils.dpScale();
        float startFrame = 0.0f;
        float endFrame = 0.0f;
        float frameRate = 0.0f;
        LongSparseArray<Layer> layerMap = new LongSparseArray<>();
        List<Layer> layers = new ArrayList<>();
        int width = 0;
        int height = 0;
        Map<String, List<Layer>> precomps = new HashMap<>();
        Map<String, LottieImageAsset> images = new HashMap<>();
        Map<String, Font> fonts = new HashMap<>();
        SparseArrayCompat<FontCharacter> characters = new SparseArrayCompat<>();
        LottieComposition composition = new LottieComposition();
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case -1408207997:
                    if (nextName.equals("assets")) {
                        c = 7;
                        break;
                    }
                    break;
                case -1109732030:
                    if (nextName.equals("layers")) {
                        c = 6;
                        break;
                    }
                    break;
                case 104:
                    if (nextName.equals("h")) {
                        c = 1;
                        break;
                    }
                    break;
                case 118:
                    if (nextName.equals("v")) {
                        c = 5;
                        break;
                    }
                    break;
                case 119:
                    if (nextName.equals("w")) {
                        c = 0;
                        break;
                    }
                    break;
                case 3276:
                    if (nextName.equals("fr")) {
                        c = 4;
                        break;
                    }
                    break;
                case 3367:
                    if (nextName.equals("ip")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3553:
                    if (nextName.equals("op")) {
                        c = 3;
                        break;
                    }
                    break;
                case 94623709:
                    if (nextName.equals("chars")) {
                        c = 9;
                        break;
                    }
                    break;
                case 97615364:
                    if (nextName.equals("fonts")) {
                        c = 8;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    width = reader.nextInt();
                    break;
                case 1:
                    height = reader.nextInt();
                    break;
                case 2:
                    startFrame = (float) reader.nextDouble();
                    break;
                case 3:
                    endFrame = (float) reader.nextDouble();
                    break;
                case 4:
                    frameRate = (float) reader.nextDouble();
                    break;
                case 5:
                    String[] versions = reader.nextString().split("\\.");
                    if (Utils.isAtLeastVersion(Integer.parseInt(versions[0]), Integer.parseInt(versions[1]), Integer.parseInt(versions[2]), 4, 4, 0)) {
                        break;
                    } else {
                        composition.addWarning("Lottie only supports bodymovin >= 4.4.0");
                        break;
                    }
                case 6:
                    parseLayers(reader, composition, layers, layerMap);
                    break;
                case 7:
                    parseAssets(reader, composition, precomps, images);
                    break;
                case 8:
                    parseFonts(reader, fonts);
                    break;
                case 9:
                    parseChars(reader, composition, characters);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        LottieComposition lottieComposition = composition;
        lottieComposition.init(new Rect(0, 0, (int) (((float) width) * scale), (int) (((float) height) * scale)), startFrame, endFrame, frameRate, layers, layerMap, precomps, images, characters, fonts);
        return composition;
    }

    private static void parseLayers(JsonReader reader, LottieComposition composition, List<Layer> layers, LongSparseArray<Layer> layerMap) throws IOException {
        int imageCount = 0;
        reader.beginArray();
        while (reader.hasNext()) {
            Layer layer = LayerParser.parse(reader, composition);
            if (layer.getLayerType() == LayerType.Image) {
                imageCount++;
            }
            layers.add(layer);
            layerMap.put(layer.getId(), layer);
            if (imageCount > 4) {
                L.warn("You have " + imageCount + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
            }
        }
        reader.endArray();
    }

    private static void parseAssets(JsonReader reader, LottieComposition composition, Map<String, List<Layer>> precomps, Map<String, LottieImageAsset> images) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            String id = null;
            List<Layer> layers = new ArrayList<>();
            LongSparseArray<Layer> layerMap = new LongSparseArray<>();
            int width = 0;
            int height = 0;
            String imageFileName = null;
            String relativeFolder = null;
            reader.beginObject();
            while (reader.hasNext()) {
                String nextName = reader.nextName();
                char c = 65535;
                switch (nextName.hashCode()) {
                    case -1109732030:
                        if (nextName.equals("layers")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 104:
                        if (nextName.equals("h")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 112:
                        if (nextName.equals("p")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 117:
                        if (nextName.equals("u")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 119:
                        if (nextName.equals("w")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 3355:
                        if (nextName.equals("id")) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        id = reader.nextString();
                        break;
                    case 1:
                        reader.beginArray();
                        while (reader.hasNext()) {
                            Layer layer = LayerParser.parse(reader, composition);
                            layerMap.put(layer.getId(), layer);
                            layers.add(layer);
                        }
                        reader.endArray();
                        break;
                    case 2:
                        width = reader.nextInt();
                        break;
                    case 3:
                        height = reader.nextInt();
                        break;
                    case 4:
                        imageFileName = reader.nextString();
                        break;
                    case 5:
                        relativeFolder = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            if (imageFileName != null) {
                LottieImageAsset image = new LottieImageAsset(width, height, id, imageFileName, relativeFolder);
                images.put(image.getId(), image);
            } else {
                precomps.put(id, layers);
            }
        }
        reader.endArray();
    }

    private static void parseFonts(JsonReader reader, Map<String, Font> fonts) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            char c = 65535;
            switch (nextName.hashCode()) {
                case 3322014:
                    if (nextName.equals("list")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    reader.beginArray();
                    while (reader.hasNext()) {
                        Font font = FontParser.parse(reader);
                        fonts.put(font.getName(), font);
                    }
                    reader.endArray();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    private static void parseChars(JsonReader reader, LottieComposition composition, SparseArrayCompat<FontCharacter> characters) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            FontCharacter character = FontCharacterParser.parse(reader, composition);
            characters.put(character.hashCode(), character);
        }
        reader.endArray();
    }
}
