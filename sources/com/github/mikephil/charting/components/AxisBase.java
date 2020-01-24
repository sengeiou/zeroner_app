package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.util.Log;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public abstract class AxisBase extends ComponentBase {
    private int mAxisLineColor;
    private DashPathEffect mAxisLineDashPathEffect;
    private float mAxisLineWidth;
    public float mAxisMaximum;
    public float mAxisMinimum;
    public float mAxisRange;
    protected ValueFormatter mAxisValueFormatter;
    protected boolean mCenterAxisLabels;
    public float[] mCenteredEntries;
    protected boolean mCustomAxisMax;
    protected boolean mCustomAxisMin;
    public int mDecimals;
    protected boolean mDrawAxisLine;
    protected boolean mDrawGridLines;
    protected boolean mDrawGridLinesBehindData;
    protected boolean mDrawLabels;
    protected boolean mDrawLimitLineBehindData;
    public float[] mEntries;
    public int mEntryCount;
    protected boolean mForceLabels;
    protected float mGranularity;
    protected boolean mGranularityEnabled;
    private int mGridColor;
    private DashPathEffect mGridDashPathEffect;
    private float mGridLineWidth;
    private int mLabelCount;
    protected List<LimitLine> mLimitLines;
    protected float mSpaceMax;
    protected float mSpaceMin;

    public AxisBase() {
        this.mGridColor = -7829368;
        this.mGridLineWidth = 1.0f;
        this.mAxisLineColor = -7829368;
        this.mAxisLineWidth = 1.0f;
        this.mEntries = new float[0];
        this.mCenteredEntries = new float[0];
        this.mLabelCount = 6;
        this.mGranularity = 1.0f;
        this.mGranularityEnabled = false;
        this.mForceLabels = false;
        this.mDrawGridLines = true;
        this.mDrawAxisLine = true;
        this.mDrawLabels = true;
        this.mCenterAxisLabels = false;
        this.mAxisLineDashPathEffect = null;
        this.mGridDashPathEffect = null;
        this.mDrawLimitLineBehindData = false;
        this.mDrawGridLinesBehindData = true;
        this.mSpaceMin = 0.0f;
        this.mSpaceMax = 0.0f;
        this.mCustomAxisMin = false;
        this.mCustomAxisMax = false;
        this.mAxisMaximum = 0.0f;
        this.mAxisMinimum = 0.0f;
        this.mAxisRange = 0.0f;
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(5.0f);
        this.mLimitLines = new ArrayList();
    }

    public void setDrawGridLines(boolean enabled) {
        this.mDrawGridLines = enabled;
    }

    public boolean isDrawGridLinesEnabled() {
        return this.mDrawGridLines;
    }

    public void setDrawAxisLine(boolean enabled) {
        this.mDrawAxisLine = enabled;
    }

    public boolean isDrawAxisLineEnabled() {
        return this.mDrawAxisLine;
    }

    public void setCenterAxisLabels(boolean enabled) {
        this.mCenterAxisLabels = enabled;
    }

    public boolean isCenterAxisLabelsEnabled() {
        return this.mCenterAxisLabels && this.mEntryCount > 0;
    }

    public void setGridColor(int color) {
        this.mGridColor = color;
    }

    public int getGridColor() {
        return this.mGridColor;
    }

    public void setAxisLineWidth(float width) {
        this.mAxisLineWidth = Utils.convertDpToPixel(width);
    }

    public float getAxisLineWidth() {
        return this.mAxisLineWidth;
    }

    public void setGridLineWidth(float width) {
        this.mGridLineWidth = Utils.convertDpToPixel(width);
    }

    public float getGridLineWidth() {
        return this.mGridLineWidth;
    }

    public void setAxisLineColor(int color) {
        this.mAxisLineColor = color;
    }

    public int getAxisLineColor() {
        return this.mAxisLineColor;
    }

    public void setDrawLabels(boolean enabled) {
        this.mDrawLabels = enabled;
    }

    public boolean isDrawLabelsEnabled() {
        return this.mDrawLabels;
    }

    public void setLabelCount(int count) {
        if (count > 25) {
            count = 25;
        }
        if (count < 2) {
            count = 2;
        }
        this.mLabelCount = count;
        this.mForceLabels = false;
    }

    public void setLabelCount(int count, boolean force) {
        setLabelCount(count);
        this.mForceLabels = force;
    }

    public boolean isForceLabelsEnabled() {
        return this.mForceLabels;
    }

    public int getLabelCount() {
        return this.mLabelCount;
    }

    public boolean isGranularityEnabled() {
        return this.mGranularityEnabled;
    }

    public void setGranularityEnabled(boolean enabled) {
        this.mGranularityEnabled = enabled;
    }

    public float getGranularity() {
        return this.mGranularity;
    }

    public void setGranularity(float granularity) {
        this.mGranularity = granularity;
        this.mGranularityEnabled = true;
    }

    public void addLimitLine(LimitLine l) {
        this.mLimitLines.add(l);
        if (this.mLimitLines.size() > 6) {
            Log.e("MPAndroiChart", "Warning! You have more than 6 LimitLines on your axis, do you really want that?");
        }
    }

    public void removeLimitLine(LimitLine l) {
        this.mLimitLines.remove(l);
    }

    public void removeAllLimitLines() {
        this.mLimitLines.clear();
    }

    public List<LimitLine> getLimitLines() {
        return this.mLimitLines;
    }

    public void setDrawLimitLinesBehindData(boolean enabled) {
        this.mDrawLimitLineBehindData = enabled;
    }

    public boolean isDrawLimitLinesBehindDataEnabled() {
        return this.mDrawLimitLineBehindData;
    }

    public void setDrawGridLinesBehindData(boolean enabled) {
        this.mDrawGridLinesBehindData = enabled;
    }

    public boolean isDrawGridLinesBehindDataEnabled() {
        return this.mDrawGridLinesBehindData;
    }

    public String getLongestLabel() {
        String longest = "";
        for (int i = 0; i < this.mEntries.length; i++) {
            String text = getFormattedLabel(i);
            if (text != null && longest.length() < text.length()) {
                longest = text;
            }
        }
        return longest;
    }

    public String getFormattedLabel(int index) {
        if (index < 0 || index >= this.mEntries.length) {
            return "";
        }
        return getValueFormatter().getAxisLabel(this.mEntries[index], this);
    }

    public void setValueFormatter(ValueFormatter f) {
        if (f == null) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        } else {
            this.mAxisValueFormatter = f;
        }
    }

    public ValueFormatter getValueFormatter() {
        if (this.mAxisValueFormatter == null || ((this.mAxisValueFormatter instanceof DefaultAxisValueFormatter) && ((DefaultAxisValueFormatter) this.mAxisValueFormatter).getDecimalDigits() != this.mDecimals)) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        }
        return this.mAxisValueFormatter;
    }

    public void enableGridDashedLine(float lineLength, float spaceLength, float phase) {
        this.mGridDashPathEffect = new DashPathEffect(new float[]{lineLength, spaceLength}, phase);
    }

    public void setGridDashedLine(DashPathEffect effect) {
        this.mGridDashPathEffect = effect;
    }

    public void disableGridDashedLine() {
        this.mGridDashPathEffect = null;
    }

    public boolean isGridDashedLineEnabled() {
        return this.mGridDashPathEffect != null;
    }

    public DashPathEffect getGridDashPathEffect() {
        return this.mGridDashPathEffect;
    }

    public void enableAxisLineDashedLine(float lineLength, float spaceLength, float phase) {
        this.mAxisLineDashPathEffect = new DashPathEffect(new float[]{lineLength, spaceLength}, phase);
    }

    public void setAxisLineDashedLine(DashPathEffect effect) {
        this.mAxisLineDashPathEffect = effect;
    }

    public void disableAxisLineDashedLine() {
        this.mAxisLineDashPathEffect = null;
    }

    public boolean isAxisLineDashedLineEnabled() {
        return this.mAxisLineDashPathEffect != null;
    }

    public DashPathEffect getAxisLineDashPathEffect() {
        return this.mAxisLineDashPathEffect;
    }

    public float getAxisMaximum() {
        return this.mAxisMaximum;
    }

    public float getAxisMinimum() {
        return this.mAxisMinimum;
    }

    public void resetAxisMaximum() {
        this.mCustomAxisMax = false;
    }

    public boolean isAxisMaxCustom() {
        return this.mCustomAxisMax;
    }

    public void resetAxisMinimum() {
        this.mCustomAxisMin = false;
    }

    public boolean isAxisMinCustom() {
        return this.mCustomAxisMin;
    }

    public void setAxisMinimum(float min) {
        this.mCustomAxisMin = true;
        this.mAxisMinimum = min;
        this.mAxisRange = Math.abs(this.mAxisMaximum - min);
    }

    @Deprecated
    public void setAxisMinValue(float min) {
        setAxisMinimum(min);
    }

    public void setAxisMaximum(float max) {
        this.mCustomAxisMax = true;
        this.mAxisMaximum = max;
        this.mAxisRange = Math.abs(max - this.mAxisMinimum);
    }

    @Deprecated
    public void setAxisMaxValue(float max) {
        setAxisMaximum(max);
    }

    public void calculate(float dataMin, float dataMax) {
        float min = this.mCustomAxisMin ? this.mAxisMinimum : dataMin - this.mSpaceMin;
        float max = this.mCustomAxisMax ? this.mAxisMaximum : dataMax + this.mSpaceMax;
        if (Math.abs(max - min) == 0.0f) {
            max += 1.0f;
            min -= 1.0f;
        }
        this.mAxisMinimum = min;
        this.mAxisMaximum = max;
        this.mAxisRange = Math.abs(max - min);
    }

    public float getSpaceMin() {
        return this.mSpaceMin;
    }

    public void setSpaceMin(float mSpaceMin2) {
        this.mSpaceMin = mSpaceMin2;
    }

    public float getSpaceMax() {
        return this.mSpaceMax;
    }

    public void setSpaceMax(float mSpaceMax2) {
        this.mSpaceMax = mSpaceMax2;
    }
}
