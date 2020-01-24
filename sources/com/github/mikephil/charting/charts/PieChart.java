package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.PieHighlighter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.PieChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

public class PieChart extends PieRadarChartBase<PieData> {
    private float[] mAbsoluteAngles = new float[1];
    private CharSequence mCenterText = "";
    private MPPointF mCenterTextOffset = MPPointF.getInstance(0.0f, 0.0f);
    private float mCenterTextRadiusPercent = 100.0f;
    private RectF mCircleBox = new RectF();
    private float[] mDrawAngles = new float[1];
    private boolean mDrawCenterText = true;
    private boolean mDrawEntryLabels = true;
    private boolean mDrawHole = true;
    private boolean mDrawRoundedSlices = false;
    private boolean mDrawSlicesUnderHole = false;
    private float mHoleRadiusPercent = 50.0f;
    protected float mMaxAngle = 360.0f;
    private float mMinAngleForSlices = 0.0f;
    protected float mTransparentCircleRadiusPercent = 55.0f;
    private boolean mUsePercentValues = false;

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        this.mRenderer = new PieChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.mXAxis = null;
        this.mHighlighter = new PieHighlighter(this);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mData != null) {
            this.mRenderer.drawData(canvas);
            if (valuesToHighlight()) {
                this.mRenderer.drawHighlighted(canvas, this.mIndicesToHighlight);
            }
            this.mRenderer.drawExtras(canvas);
            this.mRenderer.drawValues(canvas);
            this.mLegendRenderer.renderLegend(canvas);
            drawDescription(canvas);
            drawMarkers(canvas);
        }
    }

    public void calculateOffsets() {
        super.calculateOffsets();
        if (this.mData != null) {
            float radius = getDiameter() / 2.0f;
            MPPointF c = getCenterOffsets();
            float shift = ((PieData) this.mData).getDataSet().getSelectionShift();
            this.mCircleBox.set((c.x - radius) + shift, (c.y - radius) + shift, (c.x + radius) - shift, (c.y + radius) - shift);
            MPPointF.recycleInstance(c);
        }
    }

    /* access modifiers changed from: protected */
    public void calcMinMax() {
        calcAngles();
    }

    /* access modifiers changed from: protected */
    public float[] getMarkerPosition(Highlight highlight) {
        MPPointF center = getCenterCircleBox();
        float r = getRadius();
        float off = (r / 10.0f) * 3.6f;
        if (isDrawHoleEnabled()) {
            off = (r - ((r / 100.0f) * getHoleRadius())) / 2.0f;
        }
        float r2 = r - off;
        float rotationAngle = getRotationAngle();
        int entryIndex = (int) highlight.getX();
        float offset = this.mDrawAngles[entryIndex] / 2.0f;
        float x = (float) ((((double) r2) * Math.cos(Math.toRadians((double) (((this.mAbsoluteAngles[entryIndex] + rotationAngle) - offset) * this.mAnimator.getPhaseY())))) + ((double) center.x));
        float y = (float) ((((double) r2) * Math.sin(Math.toRadians((double) (((this.mAbsoluteAngles[entryIndex] + rotationAngle) - offset) * this.mAnimator.getPhaseY())))) + ((double) center.y));
        MPPointF.recycleInstance(center);
        return new float[]{x, y};
    }

    private void calcAngles() {
        int entryCount = ((PieData) this.mData).getEntryCount();
        if (this.mDrawAngles.length != entryCount) {
            this.mDrawAngles = new float[entryCount];
        } else {
            for (int i = 0; i < entryCount; i++) {
                this.mDrawAngles[i] = 0.0f;
            }
        }
        if (this.mAbsoluteAngles.length != entryCount) {
            this.mAbsoluteAngles = new float[entryCount];
        } else {
            for (int i2 = 0; i2 < entryCount; i2++) {
                this.mAbsoluteAngles[i2] = 0.0f;
            }
        }
        float yValueSum = ((PieData) this.mData).getYValueSum();
        List<IPieDataSet> dataSets = ((PieData) this.mData).getDataSets();
        boolean hasMinAngle = this.mMinAngleForSlices != 0.0f && ((float) entryCount) * this.mMinAngleForSlices <= this.mMaxAngle;
        float[] minAngles = new float[entryCount];
        int cnt = 0;
        float offset = 0.0f;
        float diff = 0.0f;
        for (int i3 = 0; i3 < ((PieData) this.mData).getDataSetCount(); i3++) {
            IPieDataSet set = (IPieDataSet) dataSets.get(i3);
            for (int j = 0; j < set.getEntryCount(); j++) {
                float drawAngle = calcAngle(Math.abs(((PieEntry) set.getEntryForIndex(j)).getY()), yValueSum);
                if (hasMinAngle) {
                    float temp = drawAngle - this.mMinAngleForSlices;
                    if (temp <= 0.0f) {
                        minAngles[cnt] = this.mMinAngleForSlices;
                        offset += -temp;
                    } else {
                        minAngles[cnt] = drawAngle;
                        diff += temp;
                    }
                }
                this.mDrawAngles[cnt] = drawAngle;
                if (cnt == 0) {
                    this.mAbsoluteAngles[cnt] = this.mDrawAngles[cnt];
                } else {
                    this.mAbsoluteAngles[cnt] = this.mAbsoluteAngles[cnt - 1] + this.mDrawAngles[cnt];
                }
                cnt++;
            }
        }
        if (hasMinAngle) {
            for (int i4 = 0; i4 < entryCount; i4++) {
                minAngles[i4] = minAngles[i4] - (((minAngles[i4] - this.mMinAngleForSlices) / diff) * offset);
                if (i4 == 0) {
                    this.mAbsoluteAngles[0] = minAngles[0];
                } else {
                    this.mAbsoluteAngles[i4] = this.mAbsoluteAngles[i4 - 1] + minAngles[i4];
                }
            }
            this.mDrawAngles = minAngles;
        }
    }

    public boolean needsHighlight(int index) {
        if (!valuesToHighlight()) {
            return false;
        }
        for (Highlight x : this.mIndicesToHighlight) {
            if (((int) x.getX()) == index) {
                return true;
            }
        }
        return false;
    }

    private float calcAngle(float value) {
        return calcAngle(value, ((PieData) this.mData).getYValueSum());
    }

    private float calcAngle(float value, float yValueSum) {
        return (value / yValueSum) * this.mMaxAngle;
    }

    @Deprecated
    public XAxis getXAxis() {
        throw new RuntimeException("PieChart has no XAxis");
    }

    public int getIndexForAngle(float angle) {
        float a = Utils.getNormalizedAngle(angle - getRotationAngle());
        for (int i = 0; i < this.mAbsoluteAngles.length; i++) {
            if (this.mAbsoluteAngles[i] > a) {
                return i;
            }
        }
        return -1;
    }

    public int getDataSetIndexForIndex(int xIndex) {
        List<IPieDataSet> dataSets = ((PieData) this.mData).getDataSets();
        for (int i = 0; i < dataSets.size(); i++) {
            if (((IPieDataSet) dataSets.get(i)).getEntryForXValue((float) xIndex, Float.NaN) != null) {
                return i;
            }
        }
        return -1;
    }

    public float[] getDrawAngles() {
        return this.mDrawAngles;
    }

    public float[] getAbsoluteAngles() {
        return this.mAbsoluteAngles;
    }

    public void setHoleColor(int color) {
        ((PieChartRenderer) this.mRenderer).getPaintHole().setColor(color);
    }

    public void setDrawSlicesUnderHole(boolean enable) {
        this.mDrawSlicesUnderHole = enable;
    }

    public boolean isDrawSlicesUnderHoleEnabled() {
        return this.mDrawSlicesUnderHole;
    }

    public void setDrawHoleEnabled(boolean enabled) {
        this.mDrawHole = enabled;
    }

    public boolean isDrawHoleEnabled() {
        return this.mDrawHole;
    }

    public void setCenterText(CharSequence text) {
        if (text == null) {
            this.mCenterText = "";
        } else {
            this.mCenterText = text;
        }
    }

    public CharSequence getCenterText() {
        return this.mCenterText;
    }

    public void setDrawCenterText(boolean enabled) {
        this.mDrawCenterText = enabled;
    }

    public boolean isDrawCenterTextEnabled() {
        return this.mDrawCenterText;
    }

    /* access modifiers changed from: protected */
    public float getRequiredLegendOffset() {
        return this.mLegendRenderer.getLabelPaint().getTextSize() * 2.0f;
    }

    /* access modifiers changed from: protected */
    public float getRequiredBaseOffset() {
        return 0.0f;
    }

    public float getRadius() {
        if (this.mCircleBox == null) {
            return 0.0f;
        }
        return Math.min(this.mCircleBox.width() / 2.0f, this.mCircleBox.height() / 2.0f);
    }

    public RectF getCircleBox() {
        return this.mCircleBox;
    }

    public MPPointF getCenterCircleBox() {
        return MPPointF.getInstance(this.mCircleBox.centerX(), this.mCircleBox.centerY());
    }

    public void setCenterTextTypeface(Typeface t) {
        ((PieChartRenderer) this.mRenderer).getPaintCenterText().setTypeface(t);
    }

    public void setCenterTextSize(float sizeDp) {
        ((PieChartRenderer) this.mRenderer).getPaintCenterText().setTextSize(Utils.convertDpToPixel(sizeDp));
    }

    public void setCenterTextSizePixels(float sizePixels) {
        ((PieChartRenderer) this.mRenderer).getPaintCenterText().setTextSize(sizePixels);
    }

    public void setCenterTextOffset(float x, float y) {
        this.mCenterTextOffset.x = Utils.convertDpToPixel(x);
        this.mCenterTextOffset.y = Utils.convertDpToPixel(y);
    }

    public MPPointF getCenterTextOffset() {
        return MPPointF.getInstance(this.mCenterTextOffset.x, this.mCenterTextOffset.y);
    }

    public void setCenterTextColor(int color) {
        ((PieChartRenderer) this.mRenderer).getPaintCenterText().setColor(color);
    }

    public void setHoleRadius(float percent) {
        this.mHoleRadiusPercent = percent;
    }

    public float getHoleRadius() {
        return this.mHoleRadiusPercent;
    }

    public void setTransparentCircleColor(int color) {
        Paint p = ((PieChartRenderer) this.mRenderer).getPaintTransparentCircle();
        int alpha = p.getAlpha();
        p.setColor(color);
        p.setAlpha(alpha);
    }

    public void setTransparentCircleRadius(float percent) {
        this.mTransparentCircleRadiusPercent = percent;
    }

    public float getTransparentCircleRadius() {
        return this.mTransparentCircleRadiusPercent;
    }

    public void setTransparentCircleAlpha(int alpha) {
        ((PieChartRenderer) this.mRenderer).getPaintTransparentCircle().setAlpha(alpha);
    }

    @Deprecated
    public void setDrawSliceText(boolean enabled) {
        this.mDrawEntryLabels = enabled;
    }

    public void setDrawEntryLabels(boolean enabled) {
        this.mDrawEntryLabels = enabled;
    }

    public boolean isDrawEntryLabelsEnabled() {
        return this.mDrawEntryLabels;
    }

    public void setEntryLabelColor(int color) {
        ((PieChartRenderer) this.mRenderer).getPaintEntryLabels().setColor(color);
    }

    public void setEntryLabelTypeface(Typeface tf) {
        ((PieChartRenderer) this.mRenderer).getPaintEntryLabels().setTypeface(tf);
    }

    public void setEntryLabelTextSize(float size) {
        ((PieChartRenderer) this.mRenderer).getPaintEntryLabels().setTextSize(Utils.convertDpToPixel(size));
    }

    public void setDrawRoundedSlices(boolean enabled) {
        this.mDrawRoundedSlices = enabled;
    }

    public boolean isDrawRoundedSlicesEnabled() {
        return this.mDrawRoundedSlices;
    }

    public void setUsePercentValues(boolean enabled) {
        this.mUsePercentValues = enabled;
    }

    public boolean isUsePercentValuesEnabled() {
        return this.mUsePercentValues;
    }

    public void setCenterTextRadiusPercent(float percent) {
        this.mCenterTextRadiusPercent = percent;
    }

    public float getCenterTextRadiusPercent() {
        return this.mCenterTextRadiusPercent;
    }

    public float getMaxAngle() {
        return this.mMaxAngle;
    }

    public void setMaxAngle(float maxangle) {
        if (maxangle > 360.0f) {
            maxangle = 360.0f;
        }
        if (maxangle < 90.0f) {
            maxangle = 90.0f;
        }
        this.mMaxAngle = maxangle;
    }

    public float getMinAngleForSlices() {
        return this.mMinAngleForSlices;
    }

    public void setMinAngleForSlices(float minAngle) {
        if (minAngle > this.mMaxAngle / 2.0f) {
            minAngle = this.mMaxAngle / 2.0f;
        } else if (minAngle < 0.0f) {
            minAngle = 0.0f;
        }
        this.mMinAngleForSlices = minAngle;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mRenderer != null && (this.mRenderer instanceof PieChartRenderer)) {
            ((PieChartRenderer) this.mRenderer).releaseBitmap();
        }
        super.onDetachedFromWindow();
    }
}