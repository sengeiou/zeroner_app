package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.support.annotation.IntRange;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import com.airbnb.lottie.L;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.utils.MiscUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GradientColorParser implements ValueParser<GradientColor> {
    private int colorPoints;

    public GradientColorParser(int colorPoints2) {
        this.colorPoints = colorPoints2;
    }

    public GradientColor parse(JsonReader reader, float scale) throws IOException {
        List<Float> array = new ArrayList<>();
        boolean isArray = reader.peek() == JsonToken.BEGIN_ARRAY;
        if (isArray) {
            reader.beginArray();
        }
        while (reader.hasNext()) {
            array.add(Float.valueOf((float) reader.nextDouble()));
        }
        if (isArray) {
            reader.endArray();
        }
        if (this.colorPoints == -1) {
            this.colorPoints = array.size() / 4;
        }
        float[] positions = new float[this.colorPoints];
        int[] colors = new int[this.colorPoints];
        int r = 0;
        int g = 0;
        if (array.size() != this.colorPoints * 4) {
            Log.w(L.TAG, "Unexpected gradient length: " + array.size() + ". Expected " + (this.colorPoints * 4) + ". This may affect the appearance of the gradient. Make sure to save your After Effects file before exporting an animation with gradients.");
        }
        for (int i = 0; i < this.colorPoints * 4; i++) {
            int colorIndex = i / 4;
            double value = (double) ((Float) array.get(i)).floatValue();
            switch (i % 4) {
                case 0:
                    positions[colorIndex] = (float) value;
                    break;
                case 1:
                    r = (int) (255.0d * value);
                    break;
                case 2:
                    g = (int) (255.0d * value);
                    break;
                case 3:
                    colors[colorIndex] = Color.argb(255, r, g, (int) (255.0d * value));
                    break;
            }
        }
        GradientColor gradientColor = new GradientColor(positions, colors);
        addOpacityStopsToGradientIfNeeded(gradientColor, array);
        return gradientColor;
    }

    private void addOpacityStopsToGradientIfNeeded(GradientColor gradientColor, List<Float> array) {
        int startIndex = this.colorPoints * 4;
        if (array.size() > startIndex) {
            int opacityStops = (array.size() - startIndex) / 2;
            double[] positions = new double[opacityStops];
            double[] opacities = new double[opacityStops];
            int j = 0;
            for (int i = startIndex; i < array.size(); i++) {
                if (i % 2 == 0) {
                    positions[j] = (double) ((Float) array.get(i)).floatValue();
                } else {
                    opacities[j] = (double) ((Float) array.get(i)).floatValue();
                    j++;
                }
            }
            for (int i2 = 0; i2 < gradientColor.getSize(); i2++) {
                int color = gradientColor.getColors()[i2];
                gradientColor.getColors()[i2] = Color.argb(getOpacityAtPosition((double) gradientColor.getPositions()[i2], positions, opacities), Color.red(color), Color.green(color), Color.blue(color));
            }
        }
    }

    @IntRange(from = 0, to = 255)
    private int getOpacityAtPosition(double position, double[] positions, double[] opacities) {
        for (int i = 1; i < positions.length; i++) {
            double lastPosition = positions[i - 1];
            double thisPosition = positions[i];
            if (positions[i] >= position) {
                return (int) (MiscUtils.lerp(opacities[i - 1], opacities[i], (position - lastPosition) / (thisPosition - lastPosition)) * 255.0d);
            }
        }
        return (int) (255.0d * opacities[opacities.length - 1]);
    }
}
