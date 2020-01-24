package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsStatus;
import java.util.List;

public class YAxisRenderer extends AxisRenderer {
    protected Path mDrawZeroLinePath = new Path();
    protected float[] mGetTransformedPositionsBuffer = new float[2];
    protected RectF mGridClippingRect = new RectF();
    protected RectF mLimitLineClippingRect = new RectF();
    protected Path mRenderGridLinesPath = new Path();
    protected Path mRenderLimitLines = new Path();
    protected float[] mRenderLimitLinesBuffer = new float[2];
    protected YAxis mYAxis;
    protected RectF mZeroLineClippingRect = new RectF();
    protected Paint mZeroLinePaint;

    public YAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer trans) {
        super(viewPortHandler, trans, yAxis);
        this.mYAxis = yAxis;
        if (this.mViewPortHandler != null) {
            this.mAxisLabelPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0f));
            this.mZeroLinePaint = new Paint(1);
            this.mZeroLinePaint.setColor(-7829368);
            this.mZeroLinePaint.setStrokeWidth(1.0f);
            this.mZeroLinePaint.setStyle(Style.STROKE);
        }
    }

    public void renderAxisLabels(Canvas c) {
        float xPos;
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLabelsEnabled()) {
            float[] positions = getTransformedPositions();
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            float xoffset = this.mYAxis.getXOffset();
            float yoffset = (((float) Utils.calcTextHeight(this.mAxisLabelPaint, GpsStatus.IN_PROGRESS)) / 2.5f) + this.mYAxis.getYOffset();
            AxisDependency dependency = this.mYAxis.getAxisDependency();
            YAxisLabelPosition labelPosition = this.mYAxis.getLabelPosition();
            if (dependency == AxisDependency.LEFT) {
                if (labelPosition == YAxisLabelPosition.OUTSIDE_CHART) {
                    this.mAxisLabelPaint.setTextAlign(Align.RIGHT);
                    xPos = this.mViewPortHandler.offsetLeft() - xoffset;
                } else {
                    this.mAxisLabelPaint.setTextAlign(Align.LEFT);
                    xPos = this.mViewPortHandler.offsetLeft() + xoffset;
                }
            } else if (labelPosition == YAxisLabelPosition.OUTSIDE_CHART) {
                this.mAxisLabelPaint.setTextAlign(Align.LEFT);
                xPos = this.mViewPortHandler.contentRight() + xoffset;
            } else {
                this.mAxisLabelPaint.setTextAlign(Align.RIGHT);
                xPos = this.mViewPortHandler.contentRight() - xoffset;
            }
            drawYLabels(c, xPos, positions, yoffset);
        }
    }

    public void renderAxisLine(Canvas c) {
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawAxisLineEnabled()) {
            this.mAxisLinePaint.setColor(this.mYAxis.getAxisLineColor());
            this.mAxisLinePaint.setStrokeWidth(this.mYAxis.getAxisLineWidth());
            if (this.mYAxis.getAxisDependency() == AxisDependency.LEFT) {
                c.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
                return;
            }
            c.drawLine(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
        }
    }

    /* access modifiers changed from: protected */
    public void drawYLabels(Canvas c, float fixedPosition, float[] positions, float offset) {
        int to;
        int from = this.mYAxis.isDrawBottomYLabelEntryEnabled() ? 0 : 1;
        if (this.mYAxis.isDrawTopYLabelEntryEnabled()) {
            to = this.mYAxis.mEntryCount;
        } else {
            to = this.mYAxis.mEntryCount - 1;
        }
        for (int i = from; i < to; i++) {
            c.drawText(this.mYAxis.getFormattedLabel(i), fixedPosition, positions[(i * 2) + 1] + offset, this.mAxisLabelPaint);
        }
    }

    public void renderGridLines(Canvas c) {
        if (this.mYAxis.isEnabled()) {
            if (this.mYAxis.isDrawGridLinesEnabled()) {
                int clipRestoreCount = c.save();
                c.clipRect(getGridClippingRect());
                float[] positions = getTransformedPositions();
                this.mGridPaint.setColor(this.mYAxis.getGridColor());
                this.mGridPaint.setStrokeWidth(this.mYAxis.getGridLineWidth());
                this.mGridPaint.setPathEffect(this.mYAxis.getGridDashPathEffect());
                Path gridLinePath = this.mRenderGridLinesPath;
                gridLinePath.reset();
                for (int i = 0; i < positions.length; i += 2) {
                    c.drawPath(linePath(gridLinePath, i, positions), this.mGridPaint);
                    gridLinePath.reset();
                }
                c.restoreToCount(clipRestoreCount);
            }
            if (this.mYAxis.isDrawZeroLineEnabled()) {
                drawZeroLine(c);
            }
        }
    }

    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(0.0f, -this.mAxis.getGridLineWidth());
        return this.mGridClippingRect;
    }

    /* access modifiers changed from: protected */
    public Path linePath(Path p, int i, float[] positions) {
        p.moveTo(this.mViewPortHandler.offsetLeft(), positions[i + 1]);
        p.lineTo(this.mViewPortHandler.contentRight(), positions[i + 1]);
        return p;
    }

    /* access modifiers changed from: protected */
    public float[] getTransformedPositions() {
        if (this.mGetTransformedPositionsBuffer.length != this.mYAxis.mEntryCount * 2) {
            this.mGetTransformedPositionsBuffer = new float[(this.mYAxis.mEntryCount * 2)];
        }
        float[] positions = this.mGetTransformedPositionsBuffer;
        for (int i = 0; i < positions.length; i += 2) {
            positions[i + 1] = this.mYAxis.mEntries[i / 2];
        }
        this.mTrans.pointValuesToPixel(positions);
        return positions;
    }

    /* access modifiers changed from: protected */
    public void drawZeroLine(Canvas c) {
        int clipRestoreCount = c.save();
        this.mZeroLineClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mZeroLineClippingRect.inset(0.0f, -this.mYAxis.getZeroLineWidth());
        c.clipRect(this.mZeroLineClippingRect);
        MPPointD pos = this.mTrans.getPixelForValues(0.0f, 0.0f);
        this.mZeroLinePaint.setColor(this.mYAxis.getZeroLineColor());
        this.mZeroLinePaint.setStrokeWidth(this.mYAxis.getZeroLineWidth());
        Path zeroLinePath = this.mDrawZeroLinePath;
        zeroLinePath.reset();
        zeroLinePath.moveTo(this.mViewPortHandler.contentLeft(), (float) pos.y);
        zeroLinePath.lineTo(this.mViewPortHandler.contentRight(), (float) pos.y);
        c.drawPath(zeroLinePath, this.mZeroLinePaint);
        c.restoreToCount(clipRestoreCount);
    }

    public void renderLimitLines(Canvas c) {
        List<LimitLine> limitLines = this.mYAxis.getLimitLines();
        if (limitLines != null && limitLines.size() > 0) {
            float[] pts = this.mRenderLimitLinesBuffer;
            pts[0] = 0.0f;
            pts[1] = 0.0f;
            Path limitLinePath = this.mRenderLimitLines;
            limitLinePath.reset();
            for (int i = 0; i < limitLines.size(); i++) {
                LimitLine l = (LimitLine) limitLines.get(i);
                if (l.isEnabled()) {
                    int clipRestoreCount = c.save();
                    this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                    this.mLimitLineClippingRect.inset(0.0f, -l.getLineWidth());
                    c.clipRect(this.mLimitLineClippingRect);
                    this.mLimitLinePaint.setStyle(Style.STROKE);
                    this.mLimitLinePaint.setColor(l.getLineColor());
                    this.mLimitLinePaint.setStrokeWidth(l.getLineWidth());
                    this.mLimitLinePaint.setPathEffect(l.getDashPathEffect());
                    pts[1] = l.getLimit();
                    this.mTrans.pointValuesToPixel(pts);
                    limitLinePath.moveTo(this.mViewPortHandler.contentLeft(), pts[1]);
                    limitLinePath.lineTo(this.mViewPortHandler.contentRight(), pts[1]);
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
                        float labelLineHeight = (float) Utils.calcTextHeight(this.mLimitLinePaint, label);
                        float xOffset = Utils.convertDpToPixel(4.0f) + l.getXOffset();
                        float yOffset = l.getLineWidth() + labelLineHeight + l.getYOffset();
                        LimitLabelPosition position = l.getLabelPosition();
                        if (position == LimitLabelPosition.RIGHT_TOP) {
                            this.mLimitLinePaint.setTextAlign(Align.RIGHT);
                            c.drawText(label, this.mViewPortHandler.contentRight() - xOffset, (pts[1] - yOffset) + labelLineHeight, this.mLimitLinePaint);
                        } else if (position == LimitLabelPosition.RIGHT_BOTTOM) {
                            this.mLimitLinePaint.setTextAlign(Align.RIGHT);
                            c.drawText(label, this.mViewPortHandler.contentRight() - xOffset, pts[1] + yOffset, this.mLimitLinePaint);
                        } else if (position == LimitLabelPosition.LEFT_TOP) {
                            this.mLimitLinePaint.setTextAlign(Align.LEFT);
                            c.drawText(label, this.mViewPortHandler.contentLeft() + xOffset, (pts[1] - yOffset) + labelLineHeight, this.mLimitLinePaint);
                        } else {
                            this.mLimitLinePaint.setTextAlign(Align.LEFT);
                            c.drawText(label, this.mViewPortHandler.offsetLeft() + xOffset, pts[1] + yOffset, this.mLimitLinePaint);
                        }
                    }
                    c.restoreToCount(clipRestoreCount);
                }
            }
        }
    }
}
