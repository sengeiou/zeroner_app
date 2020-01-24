package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererHorizontalBarChart extends YAxisRenderer {
    protected Path mDrawZeroLinePathBuffer = new Path();
    protected float[] mRenderLimitLinesBuffer = new float[4];
    protected Path mRenderLimitLinesPathBuffer = new Path();

    public YAxisRendererHorizontalBarChart(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
        this.mLimitLinePaint.setTextAlign(Align.LEFT);
    }

    public void computeAxis(float yMin, float yMax, boolean inverted) {
        if (this.mViewPortHandler.contentHeight() > 10.0f && !this.mViewPortHandler.isFullyZoomedOutX()) {
            MPPointD p1 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
            MPPointD p2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop());
            if (!inverted) {
                yMin = (float) p1.x;
                yMax = (float) p2.x;
            } else {
                yMin = (float) p2.x;
                yMax = (float) p1.x;
            }
            MPPointD.recycleInstance(p1);
            MPPointD.recycleInstance(p2);
        }
        computeAxisValues(yMin, yMax);
    }

    public void renderAxisLabels(Canvas c) {
        float yPos;
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLabelsEnabled()) {
            float[] positions = getTransformedPositions();
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            this.mAxisLabelPaint.setTextAlign(Align.CENTER);
            float baseYOffset = Utils.convertDpToPixel(2.5f);
            float textHeight = (float) Utils.calcTextHeight(this.mAxisLabelPaint, "Q");
            AxisDependency dependency = this.mYAxis.getAxisDependency();
            YAxisLabelPosition labelPosition = this.mYAxis.getLabelPosition();
            if (dependency == AxisDependency.LEFT) {
                if (labelPosition == YAxisLabelPosition.OUTSIDE_CHART) {
                    yPos = this.mViewPortHandler.contentTop() - baseYOffset;
                } else {
                    yPos = this.mViewPortHandler.contentTop() - baseYOffset;
                }
            } else if (labelPosition == YAxisLabelPosition.OUTSIDE_CHART) {
                yPos = this.mViewPortHandler.contentBottom() + textHeight + baseYOffset;
            } else {
                yPos = this.mViewPortHandler.contentBottom() + textHeight + baseYOffset;
            }
            drawYLabels(c, yPos, positions, this.mYAxis.getYOffset());
        }
    }

    public void renderAxisLine(Canvas c) {
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawAxisLineEnabled()) {
            this.mAxisLinePaint.setColor(this.mYAxis.getAxisLineColor());
            this.mAxisLinePaint.setStrokeWidth(this.mYAxis.getAxisLineWidth());
            if (this.mYAxis.getAxisDependency() == AxisDependency.LEFT) {
                c.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mAxisLinePaint);
                return;
            }
            c.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
        }
    }

    /* access modifiers changed from: protected */
    public void drawYLabels(Canvas c, float fixedPosition, float[] positions, float offset) {
        this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
        int from = this.mYAxis.isDrawBottomYLabelEntryEnabled() ? 0 : 1;
        int to = this.mYAxis.isDrawTopYLabelEntryEnabled() ? this.mYAxis.mEntryCount : this.mYAxis.mEntryCount - 1;
        for (int i = from; i < to; i++) {
            c.drawText(this.mYAxis.getFormattedLabel(i), positions[i * 2], fixedPosition - offset, this.mAxisLabelPaint);
        }
    }

    /* access modifiers changed from: protected */
    public float[] getTransformedPositions() {
        if (this.mGetTransformedPositionsBuffer.length != this.mYAxis.mEntryCount * 2) {
            this.mGetTransformedPositionsBuffer = new float[(this.mYAxis.mEntryCount * 2)];
        }
        float[] positions = this.mGetTransformedPositionsBuffer;
        for (int i = 0; i < positions.length; i += 2) {
            positions[i] = this.mYAxis.mEntries[i / 2];
        }
        this.mTrans.pointValuesToPixel(positions);
        return positions;
    }

    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(-this.mAxis.getGridLineWidth(), 0.0f);
        return this.mGridClippingRect;
    }

    /* access modifiers changed from: protected */
    public Path linePath(Path p, int i, float[] positions) {
        p.moveTo(positions[i], this.mViewPortHandler.contentTop());
        p.lineTo(positions[i], this.mViewPortHandler.contentBottom());
        return p;
    }

    /* access modifiers changed from: protected */
    public void drawZeroLine(Canvas c) {
        int clipRestoreCount = c.save();
        this.mZeroLineClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mZeroLineClippingRect.inset(-this.mYAxis.getZeroLineWidth(), 0.0f);
        c.clipRect(this.mLimitLineClippingRect);
        MPPointD pos = this.mTrans.getPixelForValues(0.0f, 0.0f);
        this.mZeroLinePaint.setColor(this.mYAxis.getZeroLineColor());
        this.mZeroLinePaint.setStrokeWidth(this.mYAxis.getZeroLineWidth());
        Path zeroLinePath = this.mDrawZeroLinePathBuffer;
        zeroLinePath.reset();
        zeroLinePath.moveTo(((float) pos.x) - 1.0f, this.mViewPortHandler.contentTop());
        zeroLinePath.lineTo(((float) pos.x) - 1.0f, this.mViewPortHandler.contentBottom());
        c.drawPath(zeroLinePath, this.mZeroLinePaint);
        c.restoreToCount(clipRestoreCount);
    }

    public void renderLimitLines(Canvas c) {
        List<LimitLine> limitLines = this.mYAxis.getLimitLines();
        if (limitLines != null && limitLines.size() > 0) {
            float[] pts = this.mRenderLimitLinesBuffer;
            pts[0] = 0.0f;
            pts[1] = 0.0f;
            pts[2] = 0.0f;
            pts[3] = 0.0f;
            Path limitLinePath = this.mRenderLimitLinesPathBuffer;
            limitLinePath.reset();
            for (int i = 0; i < limitLines.size(); i++) {
                LimitLine l = (LimitLine) limitLines.get(i);
                if (l.isEnabled()) {
                    int clipRestoreCount = c.save();
                    this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                    this.mLimitLineClippingRect.inset(-l.getLineWidth(), 0.0f);
                    c.clipRect(this.mLimitLineClippingRect);
                    pts[0] = l.getLimit();
                    pts[2] = l.getLimit();
                    this.mTrans.pointValuesToPixel(pts);
                    pts[1] = this.mViewPortHandler.contentTop();
                    pts[3] = this.mViewPortHandler.contentBottom();
                    limitLinePath.moveTo(pts[0], pts[1]);
                    limitLinePath.lineTo(pts[2], pts[3]);
                    this.mLimitLinePaint.setStyle(Style.STROKE);
                    this.mLimitLinePaint.setColor(l.getLineColor());
                    this.mLimitLinePaint.setPathEffect(l.getDashPathEffect());
                    this.mLimitLinePaint.setStrokeWidth(l.getLineWidth());
                    c.drawPath(limitLinePath, this.mLimitLinePaint);
                    limitLinePath.reset();
                    String label = l.getLabel();
                    if (label != null && !label.equals("")) {
                        this.mLimitLinePaint.setStyle(l.getTextStyle());
                        this.mLimitLinePaint.setPathEffect(null);
                        this.mLimitLinePaint.setColor(l.getTextColor());
                        this.mLimitLinePaint.setTypeface(l.getTypeface());
                        this.mLimitLinePaint.setStrokeWidth(0.5f);
                        this.mLimitLinePaint.setTextSize(l.getTextSize());
                        float xOffset = l.getLineWidth() + l.getXOffset();
                        float yOffset = Utils.convertDpToPixel(2.0f) + l.getYOffset();
                        LimitLabelPosition position = l.getLabelPosition();
                        if (position == LimitLabelPosition.RIGHT_TOP) {
                            float labelLineHeight = (float) Utils.calcTextHeight(this.mLimitLinePaint, label);
                            this.mLimitLinePaint.setTextAlign(Align.LEFT);
                            c.drawText(label, pts[0] + xOffset, this.mViewPortHandler.contentTop() + yOffset + labelLineHeight, this.mLimitLinePaint);
                        } else if (position == LimitLabelPosition.RIGHT_BOTTOM) {
                            this.mLimitLinePaint.setTextAlign(Align.LEFT);
                            c.drawText(label, pts[0] + xOffset, this.mViewPortHandler.contentBottom() - yOffset, this.mLimitLinePaint);
                        } else if (position == LimitLabelPosition.LEFT_TOP) {
                            this.mLimitLinePaint.setTextAlign(Align.RIGHT);
                            c.drawText(label, pts[0] - xOffset, this.mViewPortHandler.contentTop() + yOffset + ((float) Utils.calcTextHeight(this.mLimitLinePaint, label)), this.mLimitLinePaint);
                        } else {
                            this.mLimitLinePaint.setTextAlign(Align.RIGHT);
                            c.drawText(label, pts[0] - xOffset, this.mViewPortHandler.contentBottom() - yOffset, this.mLimitLinePaint);
                        }
                    }
                    c.restoreToCount(clipRestoreCount);
                }
            }
        }
    }
}
