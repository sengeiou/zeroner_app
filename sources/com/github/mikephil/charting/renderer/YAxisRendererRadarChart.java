package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererRadarChart extends YAxisRenderer {
    private RadarChart mChart;
    private Path mRenderLimitLinesPathBuffer = new Path();

    public YAxisRendererRadarChart(ViewPortHandler viewPortHandler, YAxis yAxis, RadarChart chart) {
        super(viewPortHandler, yAxis, null);
        this.mChart = chart;
    }

    /* access modifiers changed from: protected */
    public void computeAxisValues(float min, float max) {
        double last;
        int n;
        float yMin = min;
        float yMax = max;
        int labelCount = this.mAxis.getLabelCount();
        double range = (double) Math.abs(yMax - yMin);
        if (labelCount == 0 || range <= Utils.DOUBLE_EPSILON || Double.isInfinite(range)) {
            this.mAxis.mEntries = new float[0];
            this.mAxis.mCenteredEntries = new float[0];
            this.mAxis.mEntryCount = 0;
            return;
        }
        double interval = (double) Utils.roundToNextSignificant(range / ((double) labelCount));
        if (this.mAxis.isGranularityEnabled() && interval < ((double) this.mAxis.getGranularity())) {
            interval = (double) this.mAxis.getGranularity();
        }
        double intervalMagnitude = (double) Utils.roundToNextSignificant(Math.pow(10.0d, (double) ((int) Math.log10(interval))));
        if (((int) (interval / intervalMagnitude)) > 5) {
            interval = Math.floor(10.0d * intervalMagnitude);
        }
        boolean centeringEnabled = this.mAxis.isCenterAxisLabelsEnabled();
        int n2 = centeringEnabled ? 1 : 0;
        if (this.mAxis.isForceLabelsEnabled()) {
            float step = ((float) range) / ((float) (labelCount - 1));
            this.mAxis.mEntryCount = labelCount;
            if (this.mAxis.mEntries.length < labelCount) {
                this.mAxis.mEntries = new float[labelCount];
            }
            float v = min;
            for (int i = 0; i < labelCount; i++) {
                this.mAxis.mEntries[i] = v;
                v += step;
            }
            n = labelCount;
        } else {
            double first = interval == Utils.DOUBLE_EPSILON ? Utils.DOUBLE_EPSILON : Math.ceil(((double) yMin) / interval) * interval;
            if (centeringEnabled) {
                first -= interval;
            }
            if (interval == Utils.DOUBLE_EPSILON) {
                last = Utils.DOUBLE_EPSILON;
            } else {
                last = Utils.nextUp(Math.floor(((double) yMax) / interval) * interval);
            }
            if (interval != Utils.DOUBLE_EPSILON) {
                for (double f = first; f <= last; f += interval) {
                    n2++;
                }
            }
            n = n2 + 1;
            this.mAxis.mEntryCount = n;
            if (this.mAxis.mEntries.length < n) {
                this.mAxis.mEntries = new float[n];
            }
            double f2 = first;
            for (int i2 = 0; i2 < n; i2++) {
                if (f2 == Utils.DOUBLE_EPSILON) {
                    f2 = Utils.DOUBLE_EPSILON;
                }
                this.mAxis.mEntries[i2] = (float) f2;
                f2 += interval;
            }
        }
        if (interval < 1.0d) {
            this.mAxis.mDecimals = (int) Math.ceil(-Math.log10(interval));
        } else {
            this.mAxis.mDecimals = 0;
        }
        if (centeringEnabled) {
            if (this.mAxis.mCenteredEntries.length < n) {
                this.mAxis.mCenteredEntries = new float[n];
            }
            float offset = (this.mAxis.mEntries[1] - this.mAxis.mEntries[0]) / 2.0f;
            for (int i3 = 0; i3 < n; i3++) {
                this.mAxis.mCenteredEntries[i3] = this.mAxis.mEntries[i3] + offset;
            }
        }
        this.mAxis.mAxisMinimum = this.mAxis.mEntries[0];
        this.mAxis.mAxisMaximum = this.mAxis.mEntries[n - 1];
        this.mAxis.mAxisRange = Math.abs(this.mAxis.mAxisMaximum - this.mAxis.mAxisMinimum);
    }

    public void renderAxisLabels(Canvas c) {
        int to;
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLabelsEnabled()) {
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            MPPointF center = this.mChart.getCenterOffsets();
            MPPointF pOut = MPPointF.getInstance(0.0f, 0.0f);
            float factor = this.mChart.getFactor();
            int from = this.mYAxis.isDrawBottomYLabelEntryEnabled() ? 0 : 1;
            if (this.mYAxis.isDrawTopYLabelEntryEnabled()) {
                to = this.mYAxis.mEntryCount;
            } else {
                to = this.mYAxis.mEntryCount - 1;
            }
            for (int j = from; j < to; j++) {
                Utils.getPosition(center, (this.mYAxis.mEntries[j] - this.mYAxis.mAxisMinimum) * factor, this.mChart.getRotationAngle(), pOut);
                c.drawText(this.mYAxis.getFormattedLabel(j), pOut.x + 10.0f, pOut.y, this.mAxisLabelPaint);
            }
            MPPointF.recycleInstance(center);
            MPPointF.recycleInstance(pOut);
        }
    }

    public void renderLimitLines(Canvas c) {
        List<LimitLine> limitLines = this.mYAxis.getLimitLines();
        if (limitLines != null) {
            float sliceangle = this.mChart.getSliceAngle();
            float factor = this.mChart.getFactor();
            MPPointF center = this.mChart.getCenterOffsets();
            MPPointF pOut = MPPointF.getInstance(0.0f, 0.0f);
            for (int i = 0; i < limitLines.size(); i++) {
                LimitLine l = (LimitLine) limitLines.get(i);
                if (l.isEnabled()) {
                    this.mLimitLinePaint.setColor(l.getLineColor());
                    this.mLimitLinePaint.setPathEffect(l.getDashPathEffect());
                    this.mLimitLinePaint.setStrokeWidth(l.getLineWidth());
                    float r = (l.getLimit() - this.mChart.getYChartMin()) * factor;
                    Path limitPath = this.mRenderLimitLinesPathBuffer;
                    limitPath.reset();
                    for (int j = 0; j < ((IRadarDataSet) ((RadarData) this.mChart.getData()).getMaxEntryCountSet()).getEntryCount(); j++) {
                        Utils.getPosition(center, r, (((float) j) * sliceangle) + this.mChart.getRotationAngle(), pOut);
                        if (j == 0) {
                            limitPath.moveTo(pOut.x, pOut.y);
                        } else {
                            limitPath.lineTo(pOut.x, pOut.y);
                        }
                    }
                    limitPath.close();
                    c.drawPath(limitPath, this.mLimitLinePaint);
                }
            }
            MPPointF.recycleInstance(center);
            MPPointF.recycleInstance(pOut);
        }
    }
}
