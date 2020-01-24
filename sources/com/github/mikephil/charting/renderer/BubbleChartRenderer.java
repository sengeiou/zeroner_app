package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class BubbleChartRenderer extends BarLineScatterCandleBubbleRenderer {
    private float[] _hsvBuffer = new float[3];
    protected BubbleDataProvider mChart;
    private float[] pointBuffer = new float[2];
    private float[] sizeBuffer = new float[4];

    public BubbleChartRenderer(BubbleDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.mChart = chart;
        this.mRenderPaint.setStyle(Style.FILL);
        this.mHighlightPaint.setStyle(Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(1.5f));
    }

    public void initBuffers() {
    }

    public void drawData(Canvas c) {
        for (IBubbleDataSet set : this.mChart.getBubbleData().getDataSets()) {
            if (set.isVisible()) {
                drawDataSet(c, set);
            }
        }
    }

    /* access modifiers changed from: protected */
    public float getShapeSize(float entrySize, float maxSize, float reference, boolean normalizeSize) {
        float factor = normalizeSize ? maxSize == 0.0f ? 1.0f : (float) Math.sqrt((double) (entrySize / maxSize)) : entrySize;
        return reference * factor;
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas c, IBubbleDataSet dataSet) {
        if (dataSet.getEntryCount() >= 1) {
            Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
            float phaseY = this.mAnimator.getPhaseY();
            this.mXBounds.set(this.mChart, dataSet);
            this.sizeBuffer[0] = 0.0f;
            this.sizeBuffer[2] = 1.0f;
            trans.pointValuesToPixel(this.sizeBuffer);
            boolean normalizeSize = dataSet.isNormalizeSizeEnabled();
            float referenceSize = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), Math.abs(this.sizeBuffer[2] - this.sizeBuffer[0]));
            for (int j = this.mXBounds.min; j <= this.mXBounds.range + this.mXBounds.min; j++) {
                BubbleEntry entry = (BubbleEntry) dataSet.getEntryForIndex(j);
                this.pointBuffer[0] = entry.getX();
                this.pointBuffer[1] = entry.getY() * phaseY;
                trans.pointValuesToPixel(this.pointBuffer);
                float shapeHalf = getShapeSize(entry.getSize(), dataSet.getMaxSize(), referenceSize, normalizeSize) / 2.0f;
                if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + shapeHalf) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - shapeHalf) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + shapeHalf)) {
                    if (this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - shapeHalf)) {
                        this.mRenderPaint.setColor(dataSet.getColor((int) entry.getX()));
                        c.drawCircle(this.pointBuffer[0], this.pointBuffer[1], shapeHalf, this.mRenderPaint);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void drawValues(Canvas c) {
        float alpha;
        BubbleData bubbleData = this.mChart.getBubbleData();
        if (bubbleData != null) {
            if (isDrawingValuesAllowed(this.mChart)) {
                List<IBubbleDataSet> dataSets = bubbleData.getDataSets();
                float lineHeight = (float) Utils.calcTextHeight(this.mValuePaint, "1");
                for (int i = 0; i < dataSets.size(); i++) {
                    IBubbleDataSet dataSet = (IBubbleDataSet) dataSets.get(i);
                    if (shouldDrawValues(dataSet) && dataSet.getEntryCount() >= 1) {
                        applyValueTextStyle(dataSet);
                        float phaseX = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
                        float phaseY = this.mAnimator.getPhaseY();
                        this.mXBounds.set(this.mChart, dataSet);
                        float[] positions = this.mChart.getTransformer(dataSet.getAxisDependency()).generateTransformedValuesBubble(dataSet, phaseY, this.mXBounds.min, this.mXBounds.max);
                        if (phaseX == 1.0f) {
                            alpha = phaseY;
                        } else {
                            alpha = phaseX;
                        }
                        ValueFormatter formatter = dataSet.getValueFormatter();
                        MPPointF iconsOffset = MPPointF.getInstance(dataSet.getIconsOffset());
                        iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x);
                        iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y);
                        for (int j = 0; j < positions.length; j += 2) {
                            int valueTextColor = dataSet.getValueTextColor((j / 2) + this.mXBounds.min);
                            int valueTextColor2 = Color.argb(Math.round(255.0f * alpha), Color.red(valueTextColor), Color.green(valueTextColor), Color.blue(valueTextColor));
                            float x = positions[j];
                            float y = positions[j + 1];
                            if (!this.mViewPortHandler.isInBoundsRight(x)) {
                                break;
                            }
                            if (this.mViewPortHandler.isInBoundsLeft(x) && this.mViewPortHandler.isInBoundsY(y)) {
                                BubbleEntry entry = (BubbleEntry) dataSet.getEntryForIndex((j / 2) + this.mXBounds.min);
                                if (dataSet.isDrawValuesEnabled()) {
                                    Canvas canvas = c;
                                    drawValue(canvas, formatter.getBubbleLabel(entry), x, y + (0.5f * lineHeight), valueTextColor2);
                                }
                                if (entry.getIcon() != null && dataSet.isDrawIconsEnabled()) {
                                    Drawable icon = entry.getIcon();
                                    Utils.drawImage(c, icon, (int) (iconsOffset.x + x), (int) (iconsOffset.y + y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                                }
                            }
                        }
                        MPPointF.recycleInstance(iconsOffset);
                    }
                }
            }
        }
    }

    public void drawValue(Canvas c, String valueText, float x, float y, int color) {
        this.mValuePaint.setColor(color);
        c.drawText(valueText, x, y, this.mValuePaint);
    }

    public void drawExtras(Canvas c) {
    }

    public void drawHighlighted(Canvas c, Highlight[] indices) {
        BubbleData bubbleData = this.mChart.getBubbleData();
        float phaseY = this.mAnimator.getPhaseY();
        int length = indices.length;
        for (int i = 0; i < length; i++) {
            Highlight high = indices[i];
            IBubbleDataSet set = (IBubbleDataSet) bubbleData.getDataSetByIndex(high.getDataSetIndex());
            if (set != null && set.isHighlightEnabled()) {
                BubbleEntry entry = (BubbleEntry) set.getEntryForXValue(high.getX(), high.getY());
                if (entry.getY() == high.getY() && isInBoundsX(entry, set)) {
                    Transformer trans = this.mChart.getTransformer(set.getAxisDependency());
                    this.sizeBuffer[0] = 0.0f;
                    this.sizeBuffer[2] = 1.0f;
                    trans.pointValuesToPixel(this.sizeBuffer);
                    boolean normalizeSize = set.isNormalizeSizeEnabled();
                    float referenceSize = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), Math.abs(this.sizeBuffer[2] - this.sizeBuffer[0]));
                    this.pointBuffer[0] = entry.getX();
                    this.pointBuffer[1] = entry.getY() * phaseY;
                    trans.pointValuesToPixel(this.pointBuffer);
                    high.setDraw(this.pointBuffer[0], this.pointBuffer[1]);
                    float shapeHalf = getShapeSize(entry.getSize(), set.getMaxSize(), referenceSize, normalizeSize) / 2.0f;
                    if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + shapeHalf) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - shapeHalf) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + shapeHalf)) {
                        if (this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - shapeHalf)) {
                            int originalColor = set.getColor((int) entry.getX());
                            Color.RGBToHSV(Color.red(originalColor), Color.green(originalColor), Color.blue(originalColor), this._hsvBuffer);
                            float[] fArr = this._hsvBuffer;
                            fArr[2] = fArr[2] * 0.5f;
                            this.mHighlightPaint.setColor(Color.HSVToColor(Color.alpha(originalColor), this._hsvBuffer));
                            this.mHighlightPaint.setStrokeWidth(set.getHighlightCircleWidth());
                            c.drawCircle(this.pointBuffer[0], this.pointBuffer[1], shapeHalf, this.mHighlightPaint);
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }
}
