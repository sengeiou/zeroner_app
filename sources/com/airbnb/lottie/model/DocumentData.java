package com.airbnb.lottie.model;

import android.support.annotation.ColorInt;

public class DocumentData {
    public final double baselineShift;
    @ColorInt
    public final int color;
    public final String fontName;
    final int justification;
    final double lineHeight;
    public final double size;
    @ColorInt
    public final int strokeColor;
    public final boolean strokeOverFill;
    public final int strokeWidth;
    public final String text;
    public final int tracking;

    public DocumentData(String text2, String fontName2, double size2, int justification2, int tracking2, double lineHeight2, double baselineShift2, @ColorInt int color2, @ColorInt int strokeColor2, int strokeWidth2, boolean strokeOverFill2) {
        this.text = text2;
        this.fontName = fontName2;
        this.size = size2;
        this.justification = justification2;
        this.tracking = tracking2;
        this.lineHeight = lineHeight2;
        this.baselineShift = baselineShift2;
        this.color = color2;
        this.strokeColor = strokeColor2;
        this.strokeWidth = strokeWidth2;
        this.strokeOverFill = strokeOverFill2;
    }

    public int hashCode() {
        int result = (((((int) (((double) (((this.text.hashCode() * 31) + this.fontName.hashCode()) * 31)) + this.size)) * 31) + this.justification) * 31) + this.tracking;
        long temp = Double.doubleToLongBits(this.lineHeight);
        return (((result * 31) + ((int) ((temp >>> 32) ^ temp))) * 31) + this.color;
    }
}
