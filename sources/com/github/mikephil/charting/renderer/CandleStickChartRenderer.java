package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class CandleStickChartRenderer extends LineScatterCandleRadarRenderer {
    private float[] mBodyBuffers = new float[4];
    protected CandleDataProvider mChart;
    private float[] mCloseBuffers = new float[4];
    private float[] mOpenBuffers = new float[4];
    private float[] mRangeBuffers = new float[4];
    private float[] mShadowBuffers = new float[8];

    public CandleStickChartRenderer(CandleDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.mChart = chart;
    }

    public void initBuffers() {
    }

    public void drawData(Canvas c) {
        for (ICandleDataSet set : this.mChart.getCandleData().getDataSets()) {
            if (set.isVisible()) {
                drawDataSet(c, set);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas c, ICandleDataSet dataSet) {
        int barColor;
        int shadowColor;
        int neutralColor;
        int increasingColor;
        int decreasingColor;
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        float phaseY = this.mAnimator.getPhaseY();
        float barSpace = dataSet.getBarSpace();
        boolean showCandleBar = dataSet.getShowCandleBar();
        this.mXBounds.set(this.mChart, dataSet);
        this.mRenderPaint.setStrokeWidth(dataSet.getShadowWidth());
        for (int j = this.mXBounds.min; j <= this.mXBounds.range + this.mXBounds.min; j++) {
            CandleEntry e = (CandleEntry) dataSet.getEntryForIndex(j);
            if (e != null) {
                float xPos = e.getX();
                float open = e.getOpen();
                float close = e.getClose();
                float high = e.getHigh();
                float low = e.getLow();
                if (showCandleBar) {
                    this.mShadowBuffers[0] = xPos;
                    this.mShadowBuffers[2] = xPos;
                    this.mShadowBuffers[4] = xPos;
                    this.mShadowBuffers[6] = xPos;
                    if (open > close) {
                        this.mShadowBuffers[1] = high * phaseY;
                        this.mShadowBuffers[3] = open * phaseY;
                        this.mShadowBuffers[5] = low * phaseY;
                        this.mShadowBuffers[7] = close * phaseY;
                    } else if (open < close) {
                        this.mShadowBuffers[1] = high * phaseY;
                        this.mShadowBuffers[3] = close * phaseY;
                        this.mShadowBuffers[5] = low * phaseY;
                        this.mShadowBuffers[7] = open * phaseY;
                    } else {
                        this.mShadowBuffers[1] = high * phaseY;
                        this.mShadowBuffers[3] = open * phaseY;
                        this.mShadowBuffers[5] = low * phaseY;
                        this.mShadowBuffers[7] = this.mShadowBuffers[3];
                    }
                    trans.pointValuesToPixel(this.mShadowBuffers);
                    if (!dataSet.getShadowColorSameAsCandle()) {
                        Paint paint = this.mRenderPaint;
                        if (dataSet.getShadowColor() == 1122867) {
                            shadowColor = dataSet.getColor(j);
                        } else {
                            shadowColor = dataSet.getShadowColor();
                        }
                        paint.setColor(shadowColor);
                    } else if (open > close) {
                        Paint paint2 = this.mRenderPaint;
                        if (dataSet.getDecreasingColor() == 1122867) {
                            decreasingColor = dataSet.getColor(j);
                        } else {
                            decreasingColor = dataSet.getDecreasingColor();
                        }
                        paint2.setColor(decreasingColor);
                    } else if (open < close) {
                        Paint paint3 = this.mRenderPaint;
                        if (dataSet.getIncreasingColor() == 1122867) {
                            increasingColor = dataSet.getColor(j);
                        } else {
                            increasingColor = dataSet.getIncreasingColor();
                        }
                        paint3.setColor(increasingColor);
                    } else {
                        Paint paint4 = this.mRenderPaint;
                        if (dataSet.getNeutralColor() == 1122867) {
                            neutralColor = dataSet.getColor(j);
                        } else {
                            neutralColor = dataSet.getNeutralColor();
                        }
                        paint4.setColor(neutralColor);
                    }
                    this.mRenderPaint.setStyle(Style.STROKE);
                    c.drawLines(this.mShadowBuffers, this.mRenderPaint);
                    this.mBodyBuffers[0] = (xPos - 0.5f) + barSpace;
                    this.mBodyBuffers[1] = close * phaseY;
                    this.mBodyBuffers[2] = (0.5f + xPos) - barSpace;
                    this.mBodyBuffers[3] = open * phaseY;
                    trans.pointValuesToPixel(this.mBodyBuffers);
                    if (open > close) {
                        if (dataSet.getDecreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(dataSet.getColor(j));
                        } else {
                            this.mRenderPaint.setColor(dataSet.getDecreasingColor());
                        }
                        this.mRenderPaint.setStyle(dataSet.getDecreasingPaintStyle());
                        c.drawRect(this.mBodyBuffers[0], this.mBodyBuffers[3], this.mBodyBuffers[2], this.mBodyBuffers[1], this.mRenderPaint);
                    } else if (open < close) {
                        if (dataSet.getIncreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(dataSet.getColor(j));
                        } else {
                            this.mRenderPaint.setColor(dataSet.getIncreasingColor());
                        }
                        this.mRenderPaint.setStyle(dataSet.getIncreasingPaintStyle());
                        c.drawRect(this.mBodyBuffers[0], this.mBodyBuffers[1], this.mBodyBuffers[2], this.mBodyBuffers[3], this.mRenderPaint);
                    } else {
                        if (dataSet.getNeutralColor() == 1122867) {
                            this.mRenderPaint.setColor(dataSet.getColor(j));
                        } else {
                            this.mRenderPaint.setColor(dataSet.getNeutralColor());
                        }
                        c.drawLine(this.mBodyBuffers[0], this.mBodyBuffers[1], this.mBodyBuffers[2], this.mBodyBuffers[3], this.mRenderPaint);
                    }
                } else {
                    this.mRangeBuffers[0] = xPos;
                    this.mRangeBuffers[1] = high * phaseY;
                    this.mRangeBuffers[2] = xPos;
                    this.mRangeBuffers[3] = low * phaseY;
                    this.mOpenBuffers[0] = (xPos - 0.5f) + barSpace;
                    this.mOpenBuffers[1] = open * phaseY;
                    this.mOpenBuffers[2] = xPos;
                    this.mOpenBuffers[3] = open * phaseY;
                    this.mCloseBuffers[0] = (0.5f + xPos) - barSpace;
                    this.mCloseBuffers[1] = close * phaseY;
                    this.mCloseBuffers[2] = xPos;
                    this.mCloseBuffers[3] = close * phaseY;
                    trans.pointValuesToPixel(this.mRangeBuffers);
                    trans.pointValuesToPixel(this.mOpenBuffers);
                    trans.pointValuesToPixel(this.mCloseBuffers);
                    if (open > close) {
                        if (dataSet.getDecreasingColor() == 1122867) {
                            barColor = dataSet.getColor(j);
                        } else {
                            barColor = dataSet.getDecreasingColor();
                        }
                    } else if (open < close) {
                        if (dataSet.getIncreasingColor() == 1122867) {
                            barColor = dataSet.getColor(j);
                        } else {
                            barColor = dataSet.getIncreasingColor();
                        }
                    } else if (dataSet.getNeutralColor() == 1122867) {
                        barColor = dataSet.getColor(j);
                    } else {
                        barColor = dataSet.getNeutralColor();
                    }
                    this.mRenderPaint.setColor(barColor);
                    c.drawLine(this.mRangeBuffers[0], this.mRangeBuffers[1], this.mRangeBuffers[2], this.mRangeBuffers[3], this.mRenderPaint);
                    c.drawLine(this.mOpenBuffers[0], this.mOpenBuffers[1], this.mOpenBuffers[2], this.mOpenBuffers[3], this.mRenderPaint);
                    c.drawLine(this.mCloseBuffers[0], this.mCloseBuffers[1], this.mCloseBuffers[2], this.mCloseBuffers[3], this.mRenderPaint);
                }
            }
        }
    }

    public void drawValues(Canvas c) {
        if (isDrawingValuesAllowed(this.mChart)) {
            List<ICandleDataSet> dataSets = this.mChart.getCandleData().getDataSets();
            for (int i = 0; i < dataSets.size(); i++) {
                ICandleDataSet dataSet = (ICandleDataSet) dataSets.get(i);
                if (shouldDrawValues(dataSet) && dataSet.getEntryCount() >= 1) {
                    applyValueTextStyle(dataSet);
                    Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
                    this.mXBounds.set(this.mChart, dataSet);
                    float[] positions = trans.generateTransformedValuesCandle(dataSet, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
                    float yOffset = Utils.convertDpToPixel(5.0f);
                    ValueFormatter formatter = dataSet.getValueFormatter();
                    MPPointF iconsOffset = MPPointF.getInstance(dataSet.getIconsOffset());
                    iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x);
                    iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y);
                    for (int j = 0; j < positions.length; j += 2) {
                        float x = positions[j];
                        float y = positions[j + 1];
                        if (!this.mViewPortHandler.isInBoundsRight(x)) {
                            break;
                        }
                        if (this.mViewPortHandler.isInBoundsLeft(x) && this.mViewPortHandler.isInBoundsY(y)) {
                            CandleEntry entry = (CandleEntry) dataSet.getEntryForIndex((j / 2) + this.mXBounds.min);
                            if (dataSet.isDrawValuesEnabled()) {
                                drawValue(c, formatter.getCandleLabel(entry), x, y - yOffset, dataSet.getValueTextColor(j / 2));
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

    public void drawValue(Canvas c, String valueText, float x, float y, int color) {
        this.mValuePaint.setColor(color);
        c.drawText(valueText, x, y, this.mValuePaint);
    }

    public void drawExtras(Canvas c) {
    }

    public void drawHighlighted(Canvas c, Highlight[] indices) {
        CandleData candleData = this.mChart.getCandleData();
        for (Highlight high : indices) {
            ICandleDataSet set = (ICandleDataSet) candleData.getDataSetByIndex(high.getDataSetIndex());
            if (set != null && set.isHighlightEnabled()) {
                CandleEntry e = (CandleEntry) set.getEntryForXValue(high.getX(), high.getY());
                if (isInBoundsX(e, set)) {
                    MPPointD pix = this.mChart.getTransformer(set.getAxisDependency()).getPixelForValues(e.getX(), ((e.getLow() * this.mAnimator.getPhaseY()) + (e.getHigh() * this.mAnimator.getPhaseY())) / 2.0f);
                    high.setDraw((float) pix.x, (float) pix.y);
                    drawHighlightLines(c, (float) pix.x, (float) pix.y, set);
                }
            }
        }
    }
}
