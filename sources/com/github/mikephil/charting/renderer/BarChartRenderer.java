package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.tencent.connect.common.Constants;
import java.util.List;

public class BarChartRenderer extends BarLineScatterCandleBubbleRenderer {
    protected Paint mBarBorderPaint;
    protected BarBuffer[] mBarBuffers;
    protected RectF mBarRect = new RectF();
    private RectF mBarShadowRectBuffer = new RectF();
    protected BarDataProvider mChart;
    protected Paint mShadowPaint;

    public BarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.mChart = chart;
        this.mHighlightPaint = new Paint(1);
        this.mHighlightPaint.setStyle(Style.FILL);
        this.mHighlightPaint.setColor(Color.rgb(0, 0, 0));
        this.mHighlightPaint.setAlpha(120);
        this.mShadowPaint = new Paint(1);
        this.mShadowPaint.setStyle(Style.FILL);
        this.mBarBorderPaint = new Paint(1);
        this.mBarBorderPaint.setStyle(Style.STROKE);
    }

    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new BarBuffer[barData.getDataSetCount()];
        for (int i = 0; i < this.mBarBuffers.length; i++) {
            IBarDataSet set = (IBarDataSet) barData.getDataSetByIndex(i);
            this.mBarBuffers[i] = new BarBuffer((set.isStacked() ? set.getStackSize() : 1) * set.getEntryCount() * 4, barData.getDataSetCount(), set.isStacked());
        }
    }

    public void drawData(Canvas c) {
        BarData barData = this.mChart.getBarData();
        for (int i = 0; i < barData.getDataSetCount(); i++) {
            IBarDataSet set = (IBarDataSet) barData.getDataSetByIndex(i);
            if (set.isVisible()) {
                drawDataSet(c, set, i);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        this.mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));
        boolean drawBorder = dataSet.getBarBorderWidth() > 0.0f;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        if (this.mChart.isDrawBarShadowEnabled()) {
            this.mShadowPaint.setColor(dataSet.getBarShadowColor());
            float barWidthHalf = this.mChart.getBarData().getBarWidth() / 2.0f;
            int count = Math.min((int) Math.ceil((double) (((float) dataSet.getEntryCount()) * phaseX)), dataSet.getEntryCount());
            for (int i = 0; i < count; i++) {
                float x = ((BarEntry) dataSet.getEntryForIndex(i)).getX();
                this.mBarShadowRectBuffer.left = x - barWidthHalf;
                this.mBarShadowRectBuffer.right = x + barWidthHalf;
                trans.rectValueToPixel(this.mBarShadowRectBuffer);
                if (this.mViewPortHandler.isInBoundsLeft(this.mBarShadowRectBuffer.right)) {
                    if (!this.mViewPortHandler.isInBoundsRight(this.mBarShadowRectBuffer.left)) {
                        break;
                    }
                    this.mBarShadowRectBuffer.top = this.mViewPortHandler.contentTop();
                    this.mBarShadowRectBuffer.bottom = this.mViewPortHandler.contentBottom();
                    c.drawRect(this.mBarShadowRectBuffer, this.mShadowPaint);
                }
            }
        }
        BarBuffer buffer = this.mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(this.mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(this.mChart.getBarData().getBarWidth());
        buffer.feed(dataSet);
        trans.pointValuesToPixel(buffer.buffer);
        boolean isSingleColor = dataSet.getColors().size() == 1;
        if (isSingleColor) {
            this.mRenderPaint.setColor(dataSet.getColor());
        }
        for (int j = 0; j < buffer.size(); j += 4) {
            if (this.mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])) {
                if (this.mViewPortHandler.isInBoundsRight(buffer.buffer[j])) {
                    if (!isSingleColor) {
                        this.mRenderPaint.setColor(dataSet.getColor(j / 4));
                    }
                    if (dataSet.getGradientColor() != null) {
                        GradientColor gradientColor = dataSet.getGradientColor();
                        this.mRenderPaint.setShader(new LinearGradient(buffer.buffer[j], buffer.buffer[j + 3], buffer.buffer[j], buffer.buffer[j + 1], gradientColor.getStartColor(), gradientColor.getEndColor(), TileMode.MIRROR));
                    }
                    if (dataSet.getGradientColors() != null) {
                        this.mRenderPaint.setShader(new LinearGradient(buffer.buffer[j], buffer.buffer[j + 3], buffer.buffer[j], buffer.buffer[j + 1], dataSet.getGradientColor(j / 4).getStartColor(), dataSet.getGradientColor(j / 4).getEndColor(), TileMode.MIRROR));
                    }
                    c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3], this.mRenderPaint);
                    if (drawBorder) {
                        c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3], this.mBarBorderPaint);
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void prepareBarHighlight(float x, float y1, float y2, float barWidthHalf, Transformer trans) {
        float top = y1;
        this.mBarRect.set(x - barWidthHalf, top, x + barWidthHalf, y2);
        trans.rectToPixelPhase(this.mBarRect, this.mAnimator.getPhaseY());
    }

    public void drawValues(Canvas c) {
        float negOffset;
        float f;
        float y;
        float f2;
        float py;
        float f3;
        if (isDrawingValuesAllowed(this.mChart)) {
            List<IBarDataSet> dataSets = this.mChart.getBarData().getDataSets();
            float valueOffsetPlus = Utils.convertDpToPixel(4.5f);
            boolean drawValueAboveBar = this.mChart.isDrawValueAboveBarEnabled();
            for (int i = 0; i < this.mChart.getBarData().getDataSetCount(); i++) {
                IBarDataSet dataSet = (IBarDataSet) dataSets.get(i);
                if (shouldDrawValues(dataSet)) {
                    applyValueTextStyle(dataSet);
                    boolean isInverted = this.mChart.isInverted(dataSet.getAxisDependency());
                    float valueTextHeight = (float) Utils.calcTextHeight(this.mValuePaint, Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
                    float posOffset = drawValueAboveBar ? -valueOffsetPlus : valueTextHeight + valueOffsetPlus;
                    if (drawValueAboveBar) {
                        negOffset = valueTextHeight + valueOffsetPlus;
                    } else {
                        negOffset = -valueOffsetPlus;
                    }
                    if (isInverted) {
                        posOffset = (-posOffset) - valueTextHeight;
                        negOffset = (-negOffset) - valueTextHeight;
                    }
                    BarBuffer buffer = this.mBarBuffers[i];
                    float phaseY = this.mAnimator.getPhaseY();
                    ValueFormatter formatter = dataSet.getValueFormatter();
                    MPPointF iconsOffset = MPPointF.getInstance(dataSet.getIconsOffset());
                    iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x);
                    iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y);
                    if (!dataSet.isStacked()) {
                        for (int j = 0; ((float) j) < ((float) buffer.buffer.length) * this.mAnimator.getPhaseX(); j += 4) {
                            float x = (buffer.buffer[j] + buffer.buffer[j + 2]) / 2.0f;
                            if (!this.mViewPortHandler.isInBoundsRight(x)) {
                                break;
                            }
                            if (this.mViewPortHandler.isInBoundsY(buffer.buffer[j + 1]) && this.mViewPortHandler.isInBoundsLeft(x)) {
                                BarEntry entry = (BarEntry) dataSet.getEntryForIndex(j / 4);
                                float val = entry.getY();
                                if (dataSet.isDrawValuesEnabled()) {
                                    String barLabel = formatter.getBarLabel(entry);
                                    if (val >= 0.0f) {
                                        f3 = buffer.buffer[j + 1] + posOffset;
                                    } else {
                                        f3 = buffer.buffer[j + 3] + negOffset;
                                    }
                                    drawValue(c, barLabel, x, f3, dataSet.getValueTextColor(j / 4));
                                }
                                if (entry.getIcon() != null && dataSet.isDrawIconsEnabled()) {
                                    Drawable icon = entry.getIcon();
                                    float px = x;
                                    if (val >= 0.0f) {
                                        py = buffer.buffer[j + 1] + posOffset;
                                    } else {
                                        py = buffer.buffer[j + 3] + negOffset;
                                    }
                                    Utils.drawImage(c, icon, (int) (px + iconsOffset.x), (int) (py + iconsOffset.y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                                }
                            }
                        }
                    } else {
                        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
                        int bufferIndex = 0;
                        int index = 0;
                        while (((float) index) < ((float) dataSet.getEntryCount()) * this.mAnimator.getPhaseX()) {
                            BarEntry entry2 = (BarEntry) dataSet.getEntryForIndex(index);
                            float[] vals = entry2.getYVals();
                            float x2 = (buffer.buffer[bufferIndex] + buffer.buffer[bufferIndex + 2]) / 2.0f;
                            int color = dataSet.getValueTextColor(index);
                            if (vals == null) {
                                if (!this.mViewPortHandler.isInBoundsRight(x2)) {
                                    break;
                                } else if (this.mViewPortHandler.isInBoundsY(buffer.buffer[bufferIndex + 1]) && this.mViewPortHandler.isInBoundsLeft(x2)) {
                                    if (dataSet.isDrawValuesEnabled()) {
                                        drawValue(c, formatter.getBarLabel(entry2), x2, buffer.buffer[bufferIndex + 1] + (entry2.getY() >= 0.0f ? posOffset : negOffset), color);
                                    }
                                    if (entry2.getIcon() != null && dataSet.isDrawIconsEnabled()) {
                                        Drawable icon2 = entry2.getIcon();
                                        float px2 = x2;
                                        float f4 = buffer.buffer[bufferIndex + 1];
                                        if (entry2.getY() >= 0.0f) {
                                            f2 = posOffset;
                                        } else {
                                            f2 = negOffset;
                                        }
                                        Utils.drawImage(c, icon2, (int) (px2 + iconsOffset.x), (int) (f4 + f2 + iconsOffset.y), icon2.getIntrinsicWidth(), icon2.getIntrinsicHeight());
                                    }
                                }
                            } else {
                                float[] transformed = new float[(vals.length * 2)];
                                float posY = 0.0f;
                                float negY = -entry2.getNegativeSum();
                                int k = 0;
                                int idx = 0;
                                while (k < transformed.length) {
                                    float value = vals[idx];
                                    if (value == 0.0f && (posY == 0.0f || negY == 0.0f)) {
                                        y = value;
                                    } else if (value >= 0.0f) {
                                        posY += value;
                                        y = posY;
                                    } else {
                                        y = negY;
                                        negY -= value;
                                    }
                                    transformed[k + 1] = y * phaseY;
                                    k += 2;
                                    idx++;
                                }
                                trans.pointValuesToPixel(transformed);
                                for (int k2 = 0; k2 < transformed.length; k2 += 2) {
                                    float val2 = vals[k2 / 2];
                                    boolean drawBelow = (val2 == 0.0f && negY == 0.0f && posY > 0.0f) || val2 < 0.0f;
                                    float f5 = transformed[k2 + 1];
                                    if (drawBelow) {
                                        f = negOffset;
                                    } else {
                                        f = posOffset;
                                    }
                                    float y2 = f5 + f;
                                    if (!this.mViewPortHandler.isInBoundsRight(x2)) {
                                        break;
                                    }
                                    if (this.mViewPortHandler.isInBoundsY(y2) && this.mViewPortHandler.isInBoundsLeft(x2)) {
                                        if (dataSet.isDrawValuesEnabled()) {
                                            drawValue(c, formatter.getBarStackedLabel(val2, entry2), x2, y2, color);
                                        }
                                        if (entry2.getIcon() != null && dataSet.isDrawIconsEnabled()) {
                                            Drawable icon3 = entry2.getIcon();
                                            Utils.drawImage(c, icon3, (int) (iconsOffset.x + x2), (int) (iconsOffset.y + y2), icon3.getIntrinsicWidth(), icon3.getIntrinsicHeight());
                                        }
                                    }
                                }
                            }
                            bufferIndex = vals == null ? bufferIndex + 4 : bufferIndex + (vals.length * 4);
                            index++;
                        }
                    }
                    MPPointF.recycleInstance(iconsOffset);
                }
            }
        }
    }

    public void drawValue(Canvas c, String valueText, float x, float y, int color) {
        this.mValuePaint.setColor(color);
        c.drawText(valueText, x, y, this.mValuePaint);
    }

    public void drawHighlighted(Canvas c, Highlight[] indices) {
        float y1;
        float y2;
        BarData barData = this.mChart.getBarData();
        for (Highlight high : indices) {
            IBarDataSet set = (IBarDataSet) barData.getDataSetByIndex(high.getDataSetIndex());
            if (set != null && set.isHighlightEnabled()) {
                BarEntry e = (BarEntry) set.getEntryForXValue(high.getX(), high.getY());
                if (isInBoundsX(e, set)) {
                    Transformer trans = this.mChart.getTransformer(set.getAxisDependency());
                    this.mHighlightPaint.setColor(set.getHighLightColor());
                    this.mHighlightPaint.setAlpha(set.getHighLightAlpha());
                    if (!(high.getStackIndex() >= 0 && e.isStacked())) {
                        y1 = e.getY();
                        y2 = 0.0f;
                    } else if (this.mChart.isHighlightFullBarEnabled()) {
                        y1 = e.getPositiveSum();
                        y2 = -e.getNegativeSum();
                    } else {
                        Range range = e.getRanges()[high.getStackIndex()];
                        y1 = range.from;
                        y2 = range.to;
                    }
                    prepareBarHighlight(e.getX(), y1, y2, barData.getBarWidth() / 2.0f, trans);
                    setHighlightDrawPos(high, this.mBarRect);
                    c.drawRect(this.mBarRect, this.mHighlightPaint);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setHighlightDrawPos(Highlight high, RectF bar) {
        high.setDraw(bar.centerX(), bar.top);
    }

    public void drawExtras(Canvas c) {
    }
}
