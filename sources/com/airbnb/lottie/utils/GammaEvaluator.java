package com.airbnb.lottie.utils;

public class GammaEvaluator {
    private static float OECF_sRGB(float linear) {
        if (linear <= 0.0031308f) {
            return 12.92f * linear;
        }
        return (float) ((Math.pow((double) linear, 0.4166666567325592d) * 1.0549999475479126d) - 0.054999999701976776d);
    }

    private static float EOCF_sRGB(float srgb) {
        return srgb <= 0.04045f ? srgb / 12.92f : (float) Math.pow((double) ((0.055f + srgb) / 1.055f), 2.4000000953674316d);
    }

    public static int evaluate(float fraction, int startInt, int endInt) {
        float startA = ((float) ((startInt >> 24) & 255)) / 255.0f;
        float startG = ((float) ((startInt >> 8) & 255)) / 255.0f;
        float startB = ((float) (startInt & 255)) / 255.0f;
        float endA = ((float) ((endInt >> 24) & 255)) / 255.0f;
        float endR = ((float) ((endInt >> 16) & 255)) / 255.0f;
        float endG = ((float) ((endInt >> 8) & 255)) / 255.0f;
        float endB = ((float) (endInt & 255)) / 255.0f;
        float startR = EOCF_sRGB(((float) ((startInt >> 16) & 255)) / 255.0f);
        float startG2 = EOCF_sRGB(startG);
        float startB2 = EOCF_sRGB(startB);
        float endR2 = EOCF_sRGB(endR);
        return (Math.round((startA + ((endA - startA) * fraction)) * 255.0f) << 24) | (Math.round(OECF_sRGB(startR + ((endR2 - startR) * fraction)) * 255.0f) << 16) | (Math.round(OECF_sRGB(startG2 + ((EOCF_sRGB(endG) - startG2) * fraction)) * 255.0f) << 8) | Math.round(OECF_sRGB(startB2 + ((EOCF_sRGB(endB) - startB2) * fraction)) * 255.0f);
    }
}
