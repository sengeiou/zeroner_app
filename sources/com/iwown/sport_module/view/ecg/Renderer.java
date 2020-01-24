package com.iwown.sport_module.view.ecg;

import android.graphics.Typeface;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import java.io.Serializable;

public class Renderer implements Serializable {
    private static final Typeface REGULAR_TEXT_FONT = Typeface.create(Typeface.SERIF, 0);
    private int ECGAxesColor = SupportMenu.CATEGORY_MASK;
    private int ECGBackgroundColor = ViewCompat.MEASURED_SIZE_MASK;
    private String ECGChartLabel = "";
    private float ECGChartTextSize = 24.0f;
    private int ECGLineStep = 1;
    private boolean ECGScrollable = true;
    private boolean ECGShowAxes = true;
    private boolean ECGShowLabel = false;
    private Typeface ECGTextTypeface;
    private String ECGTextTypefaceName = REGULAR_TEXT_FONT.toString();
    private int ECGTextTypefaceStyle = 0;
    public int NO_COLOR = 0;
    private int TextColor = -1;

    public String getECGChartLabel() {
        return this.ECGChartLabel;
    }

    public void setTextColor(int textColor) {
        this.TextColor = textColor;
    }

    public int getTextColor() {
        return this.TextColor;
    }

    public void setECGChartLabel(String ECGChartLabel2) {
        this.ECGChartLabel = ECGChartLabel2;
    }

    public float getECGChartTextSize() {
        return this.ECGChartTextSize;
    }

    public void setECGChartTextSize(float ECGChartTextSize2) {
        this.ECGChartTextSize = ECGChartTextSize2;
    }

    public static Typeface getRegularTextFont() {
        return REGULAR_TEXT_FONT;
    }

    public String getECGTextTypefaceName() {
        return this.ECGTextTypefaceName;
    }

    public void setECGTextTypefaceName(String ECGTextTypefaceName2) {
        this.ECGTextTypefaceName = ECGTextTypefaceName2;
    }

    public int getECGTextTypefaceStyle() {
        return this.ECGTextTypefaceStyle;
    }

    public void setECGTextTypefaceStyle(int ECGTextTypefaceStyle2) {
        this.ECGTextTypefaceStyle = ECGTextTypefaceStyle2;
    }

    public int getECGAxesColor() {
        return this.ECGAxesColor;
    }

    public void setECGAxesColor(int ECGAxesColor2) {
        this.ECGAxesColor = ECGAxesColor2;
    }

    public int getECGBackgroundColor() {
        return this.ECGBackgroundColor;
    }

    public void setECGBackgroundColor(int ECGBackgroundColor2) {
        this.ECGBackgroundColor = ECGBackgroundColor2;
    }

    public Typeface getECGTextTypeface() {
        return this.ECGTextTypeface;
    }

    public void setECGTextTypeface(Typeface ECGTextTypeface2) {
        this.ECGTextTypeface = ECGTextTypeface2;
    }

    public boolean isECGShowAxes() {
        return this.ECGShowAxes;
    }

    public void setECGShowAxes(boolean ECGShowAxes2) {
        this.ECGShowAxes = ECGShowAxes2;
    }

    public int getECGLineStep() {
        return this.ECGLineStep;
    }

    public void setECGLineStep(int ECGLineStep2) {
        if (ECGLineStep2 < 1) {
            ECGLineStep2 = 1;
        }
        if (ECGLineStep2 > 10) {
            ECGLineStep2 = 10;
        }
        this.ECGLineStep = ECGLineStep2;
    }

    public boolean isECGScrollable() {
        return this.ECGScrollable;
    }

    public void setECGScrollable(boolean ECGScrollable2) {
        this.ECGScrollable = ECGScrollable2;
    }

    public boolean isECGShowLabel() {
        return this.ECGShowLabel;
    }

    public void setECGShowLabel(boolean ECGShowLabel2) {
        this.ECGShowLabel = ECGShowLabel2;
    }
}
