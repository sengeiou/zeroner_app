package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendDirection;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment;
import com.github.mikephil.charting.components.Legend.LegendOrientation;
import com.github.mikephil.charting.components.Legend.LegendVerticalAlignment;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegendRenderer extends Renderer {
    protected List<LegendEntry> computedEntries = new ArrayList(16);
    protected FontMetrics legendFontMetrics = new FontMetrics();
    protected Legend mLegend;
    protected Paint mLegendFormPaint;
    protected Paint mLegendLabelPaint;
    private Path mLineFormPath = new Path();

    public LegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler);
        this.mLegend = legend;
        this.mLegendLabelPaint = new Paint(1);
        this.mLegendLabelPaint.setTextSize(Utils.convertDpToPixel(9.0f));
        this.mLegendLabelPaint.setTextAlign(Align.LEFT);
        this.mLegendFormPaint = new Paint(1);
        this.mLegendFormPaint.setStyle(Style.FILL);
    }

    public Paint getLabelPaint() {
        return this.mLegendLabelPaint;
    }

    public Paint getFormPaint() {
        return this.mLegendFormPaint;
    }

    public void computeLegend(ChartData<?> data) {
        String label;
        if (!this.mLegend.isLegendCustom()) {
            this.computedEntries.clear();
            for (int i = 0; i < data.getDataSetCount(); i++) {
                IDataSet dataSet = data.getDataSetByIndex(i);
                List<Integer> clrs = dataSet.getColors();
                int entryCount = dataSet.getEntryCount();
                if ((dataSet instanceof IBarDataSet) && ((IBarDataSet) dataSet).isStacked()) {
                    IBarDataSet bds = (IBarDataSet) dataSet;
                    String[] sLabels = bds.getStackLabels();
                    int j = 0;
                    while (j < clrs.size() && j < bds.getStackSize()) {
                        this.computedEntries.add(new LegendEntry(sLabels[j % sLabels.length], dataSet.getForm(), dataSet.getFormSize(), dataSet.getFormLineWidth(), dataSet.getFormLineDashEffect(), ((Integer) clrs.get(j)).intValue()));
                        j++;
                    }
                    if (bds.getLabel() != null) {
                        this.computedEntries.add(new LegendEntry(dataSet.getLabel(), LegendForm.NONE, Float.NaN, Float.NaN, null, ColorTemplate.COLOR_NONE));
                    }
                } else if (dataSet instanceof IPieDataSet) {
                    IPieDataSet pds = (IPieDataSet) dataSet;
                    int j2 = 0;
                    while (j2 < clrs.size() && j2 < entryCount) {
                        this.computedEntries.add(new LegendEntry(((PieEntry) pds.getEntryForIndex(j2)).getLabel(), dataSet.getForm(), dataSet.getFormSize(), dataSet.getFormLineWidth(), dataSet.getFormLineDashEffect(), ((Integer) clrs.get(j2)).intValue()));
                        j2++;
                    }
                    if (pds.getLabel() != null) {
                        this.computedEntries.add(new LegendEntry(dataSet.getLabel(), LegendForm.NONE, Float.NaN, Float.NaN, null, ColorTemplate.COLOR_NONE));
                    }
                } else if (!(dataSet instanceof ICandleDataSet) || ((ICandleDataSet) dataSet).getDecreasingColor() == 1122867) {
                    int j3 = 0;
                    while (j3 < clrs.size() && j3 < entryCount) {
                        if (j3 >= clrs.size() - 1 || j3 >= entryCount - 1) {
                            label = data.getDataSetByIndex(i).getLabel();
                        } else {
                            label = null;
                        }
                        this.computedEntries.add(new LegendEntry(label, dataSet.getForm(), dataSet.getFormSize(), dataSet.getFormLineWidth(), dataSet.getFormLineDashEffect(), ((Integer) clrs.get(j3)).intValue()));
                        j3++;
                    }
                } else {
                    int decreasingColor = ((ICandleDataSet) dataSet).getDecreasingColor();
                    int increasingColor = ((ICandleDataSet) dataSet).getIncreasingColor();
                    this.computedEntries.add(new LegendEntry(null, dataSet.getForm(), dataSet.getFormSize(), dataSet.getFormLineWidth(), dataSet.getFormLineDashEffect(), decreasingColor));
                    this.computedEntries.add(new LegendEntry(dataSet.getLabel(), dataSet.getForm(), dataSet.getFormSize(), dataSet.getFormLineWidth(), dataSet.getFormLineDashEffect(), increasingColor));
                }
            }
            if (this.mLegend.getExtraEntries() != null) {
                Collections.addAll(this.computedEntries, this.mLegend.getExtraEntries());
            }
            this.mLegend.setEntries(this.computedEntries);
        }
        Typeface tf = this.mLegend.getTypeface();
        if (tf != null) {
            this.mLegendLabelPaint.setTypeface(tf);
        }
        this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
        this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
        this.mLegend.calculateDimensions(this.mLegendLabelPaint, this.mViewPortHandler);
    }

    public void renderLegend(Canvas c) {
        float originPosX;
        double d;
        float originPosX2;
        float posY;
        float posY2;
        float formSize;
        float f;
        float f2;
        float f3;
        if (this.mLegend.isEnabled()) {
            Typeface tf = this.mLegend.getTypeface();
            if (tf != null) {
                this.mLegendLabelPaint.setTypeface(tf);
            }
            this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
            this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
            float labelLineHeight = Utils.getLineHeight(this.mLegendLabelPaint, this.legendFontMetrics);
            float labelLineSpacing = Utils.getLineSpacing(this.mLegendLabelPaint, this.legendFontMetrics) + Utils.convertDpToPixel(this.mLegend.getYEntrySpace());
            float formYOffset = labelLineHeight - (((float) Utils.calcTextHeight(this.mLegendLabelPaint, "ABC")) / 2.0f);
            LegendEntry[] entries = this.mLegend.getEntries();
            float formToTextSpace = Utils.convertDpToPixel(this.mLegend.getFormToTextSpace());
            float xEntrySpace = Utils.convertDpToPixel(this.mLegend.getXEntrySpace());
            LegendOrientation orientation = this.mLegend.getOrientation();
            LegendHorizontalAlignment horizontalAlignment = this.mLegend.getHorizontalAlignment();
            LegendVerticalAlignment verticalAlignment = this.mLegend.getVerticalAlignment();
            LegendDirection direction = this.mLegend.getDirection();
            float defaultFormSize = Utils.convertDpToPixel(this.mLegend.getFormSize());
            float stackSpace = Utils.convertDpToPixel(this.mLegend.getStackSpace());
            float yoffset = this.mLegend.getYOffset();
            float xoffset = this.mLegend.getXOffset();
            float originPosX3 = 0.0f;
            switch (horizontalAlignment) {
                case LEFT:
                    if (orientation == LegendOrientation.VERTICAL) {
                        originPosX3 = xoffset;
                    } else {
                        originPosX3 = this.mViewPortHandler.contentLeft() + xoffset;
                    }
                    if (direction == LegendDirection.RIGHT_TO_LEFT) {
                        originPosX3 += this.mLegend.mNeededWidth;
                        break;
                    }
                    break;
                case RIGHT:
                    if (orientation == LegendOrientation.VERTICAL) {
                        originPosX2 = this.mViewPortHandler.getChartWidth() - xoffset;
                    } else {
                        originPosX2 = this.mViewPortHandler.contentRight() - xoffset;
                    }
                    if (direction == LegendDirection.LEFT_TO_RIGHT) {
                        originPosX3 -= this.mLegend.mNeededWidth;
                        break;
                    }
                    break;
                case CENTER:
                    if (orientation == LegendOrientation.VERTICAL) {
                        originPosX = this.mViewPortHandler.getChartWidth() / 2.0f;
                    } else {
                        originPosX = this.mViewPortHandler.contentLeft() + (this.mViewPortHandler.contentWidth() / 2.0f);
                    }
                    originPosX3 = originPosX + (direction == LegendDirection.LEFT_TO_RIGHT ? xoffset : -xoffset);
                    if (orientation == LegendOrientation.VERTICAL) {
                        double d2 = (double) originPosX3;
                        if (direction == LegendDirection.LEFT_TO_RIGHT) {
                            d = (((double) (-this.mLegend.mNeededWidth)) / 2.0d) + ((double) xoffset);
                        } else {
                            d = (((double) this.mLegend.mNeededWidth) / 2.0d) - ((double) xoffset);
                        }
                        originPosX3 = (float) (d + d2);
                        break;
                    }
                    break;
            }
            switch (orientation) {
                case HORIZONTAL:
                    List<FSize> calculatedLineSizes = this.mLegend.getCalculatedLineSizes();
                    List<FSize> calculatedLabelSizes = this.mLegend.getCalculatedLabelSizes();
                    List<Boolean> calculatedLabelBreakPoints = this.mLegend.getCalculatedLabelBreakPoints();
                    float posX = originPosX3;
                    float posY3 = 0.0f;
                    switch (verticalAlignment) {
                        case TOP:
                            posY3 = yoffset;
                            break;
                        case BOTTOM:
                            posY3 = (this.mViewPortHandler.getChartHeight() - yoffset) - this.mLegend.mNeededHeight;
                            break;
                        case CENTER:
                            posY3 = ((this.mViewPortHandler.getChartHeight() - this.mLegend.mNeededHeight) / 2.0f) + yoffset;
                            break;
                    }
                    int lineIndex = 0;
                    int count = entries.length;
                    for (int i = 0; i < count; i++) {
                        LegendEntry e = entries[i];
                        boolean drawingForm = e.form != LegendForm.NONE;
                        if (Float.isNaN(e.formSize)) {
                            formSize = defaultFormSize;
                        } else {
                            formSize = Utils.convertDpToPixel(e.formSize);
                        }
                        if (i < calculatedLabelBreakPoints.size() && ((Boolean) calculatedLabelBreakPoints.get(i)).booleanValue()) {
                            posX = originPosX3;
                            posY3 += labelLineHeight + labelLineSpacing;
                        }
                        if (posX == originPosX3 && horizontalAlignment == LegendHorizontalAlignment.CENTER && lineIndex < calculatedLineSizes.size()) {
                            if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                f3 = ((FSize) calculatedLineSizes.get(lineIndex)).width;
                            } else {
                                f3 = -((FSize) calculatedLineSizes.get(lineIndex)).width;
                            }
                            posX += f3 / 2.0f;
                            lineIndex++;
                        }
                        boolean isStacked = e.label == null;
                        if (drawingForm) {
                            if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                posX -= formSize;
                            }
                            drawForm(c, posX, posY3 + formYOffset, e, this.mLegend);
                            if (direction == LegendDirection.LEFT_TO_RIGHT) {
                                posX += formSize;
                            }
                        }
                        if (!isStacked) {
                            if (drawingForm) {
                                if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                    f2 = -formToTextSpace;
                                } else {
                                    f2 = formToTextSpace;
                                }
                                posX += f2;
                            }
                            if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                posX -= ((FSize) calculatedLabelSizes.get(i)).width;
                            }
                            drawLabel(c, posX, posY3 + labelLineHeight, e.label);
                            if (direction == LegendDirection.LEFT_TO_RIGHT) {
                                posX += ((FSize) calculatedLabelSizes.get(i)).width;
                            }
                            f = direction == LegendDirection.RIGHT_TO_LEFT ? -xEntrySpace : xEntrySpace;
                        } else {
                            f = direction == LegendDirection.RIGHT_TO_LEFT ? -stackSpace : stackSpace;
                        }
                        posX += f;
                    }
                    return;
                case VERTICAL:
                    float stack = 0.0f;
                    boolean wasStacked = false;
                    float posY4 = 0.0f;
                    switch (verticalAlignment) {
                        case TOP:
                            if (horizontalAlignment == LegendHorizontalAlignment.CENTER) {
                                posY2 = 0.0f;
                            } else {
                                posY2 = this.mViewPortHandler.contentTop();
                            }
                            posY4 = posY2 + yoffset;
                            break;
                        case BOTTOM:
                            if (horizontalAlignment == LegendHorizontalAlignment.CENTER) {
                                posY = this.mViewPortHandler.getChartHeight();
                            } else {
                                posY = this.mViewPortHandler.contentBottom();
                            }
                            posY4 = posY - (this.mLegend.mNeededHeight + yoffset);
                            break;
                        case CENTER:
                            posY4 = ((this.mViewPortHandler.getChartHeight() / 2.0f) - (this.mLegend.mNeededHeight / 2.0f)) + this.mLegend.getYOffset();
                            break;
                    }
                    for (int i2 = 0; i2 < entries.length; i2++) {
                        LegendEntry e2 = entries[i2];
                        boolean drawingForm2 = e2.form != LegendForm.NONE;
                        float formSize2 = Float.isNaN(e2.formSize) ? defaultFormSize : Utils.convertDpToPixel(e2.formSize);
                        float posX2 = originPosX3;
                        if (drawingForm2) {
                            if (direction == LegendDirection.LEFT_TO_RIGHT) {
                                posX2 += stack;
                            } else {
                                posX2 -= formSize2 - stack;
                            }
                            drawForm(c, posX2, posY4 + formYOffset, e2, this.mLegend);
                            if (direction == LegendDirection.LEFT_TO_RIGHT) {
                                posX2 += formSize2;
                            }
                        }
                        if (e2.label != null) {
                            if (drawingForm2 && !wasStacked) {
                                posX2 += direction == LegendDirection.LEFT_TO_RIGHT ? formToTextSpace : -formToTextSpace;
                            } else if (wasStacked) {
                                posX2 = originPosX3;
                            }
                            if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                posX2 -= (float) Utils.calcTextWidth(this.mLegendLabelPaint, e2.label);
                            }
                            if (!wasStacked) {
                                drawLabel(c, posX2, posY4 + labelLineHeight, e2.label);
                            } else {
                                posY4 += labelLineHeight + labelLineSpacing;
                                drawLabel(c, posX2, posY4 + labelLineHeight, e2.label);
                            }
                            posY4 += labelLineHeight + labelLineSpacing;
                            stack = 0.0f;
                        } else {
                            stack += formSize2 + stackSpace;
                            wasStacked = true;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawForm(Canvas c, float x, float y, LegendEntry entry, Legend legend) {
        float f;
        DashPathEffect formLineDashEffect;
        if (entry.formColor != 1122868 && entry.formColor != 1122867 && entry.formColor != 0) {
            int restoreCount = c.save();
            LegendForm form = entry.form;
            if (form == LegendForm.DEFAULT) {
                form = legend.getForm();
            }
            this.mLegendFormPaint.setColor(entry.formColor);
            if (Float.isNaN(entry.formSize)) {
                f = legend.getFormSize();
            } else {
                f = entry.formSize;
            }
            float formSize = Utils.convertDpToPixel(f);
            float half = formSize / 2.0f;
            switch (form) {
                case DEFAULT:
                case CIRCLE:
                    this.mLegendFormPaint.setStyle(Style.FILL);
                    c.drawCircle(x + half, y, half, this.mLegendFormPaint);
                    break;
                case SQUARE:
                    this.mLegendFormPaint.setStyle(Style.FILL);
                    c.drawRect(x, y - half, x + formSize, y + half, this.mLegendFormPaint);
                    break;
                case LINE:
                    float formLineWidth = Utils.convertDpToPixel(Float.isNaN(entry.formLineWidth) ? legend.getFormLineWidth() : entry.formLineWidth);
                    if (entry.formLineDashEffect == null) {
                        formLineDashEffect = legend.getFormLineDashEffect();
                    } else {
                        formLineDashEffect = entry.formLineDashEffect;
                    }
                    this.mLegendFormPaint.setStyle(Style.STROKE);
                    this.mLegendFormPaint.setStrokeWidth(formLineWidth);
                    this.mLegendFormPaint.setPathEffect(formLineDashEffect);
                    this.mLineFormPath.reset();
                    this.mLineFormPath.moveTo(x, y);
                    this.mLineFormPath.lineTo(x + formSize, y);
                    c.drawPath(this.mLineFormPath, this.mLegendFormPaint);
                    break;
            }
            c.restoreToCount(restoreCount);
        }
    }

    /* access modifiers changed from: protected */
    public void drawLabel(Canvas c, float x, float y, String label) {
        c.drawText(label, x, y, this.mLegendLabelPaint);
    }
}
