package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewCompat;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class AxisRenderer extends Renderer {
    protected AxisBase mAxis;
    protected Paint mAxisLabelPaint;
    protected Paint mAxisLinePaint;
    protected Paint mGridPaint;
    protected Paint mLimitLinePaint;
    protected Transformer mTrans;

    public abstract void renderAxisLabels(Canvas canvas);

    public abstract void renderAxisLine(Canvas canvas);

    public abstract void renderGridLines(Canvas canvas);

    public abstract void renderLimitLines(Canvas canvas);

    public AxisRenderer(ViewPortHandler viewPortHandler, Transformer trans, AxisBase axis) {
        super(viewPortHandler);
        this.mTrans = trans;
        this.mAxis = axis;
        if (this.mViewPortHandler != null) {
            this.mAxisLabelPaint = new Paint(1);
            this.mGridPaint = new Paint();
            this.mGridPaint.setColor(-7829368);
            this.mGridPaint.setStrokeWidth(1.0f);
            this.mGridPaint.setStyle(Style.STROKE);
            this.mGridPaint.setAlpha(90);
            this.mAxisLinePaint = new Paint();
            this.mAxisLinePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.mAxisLinePaint.setStrokeWidth(1.0f);
            this.mAxisLinePaint.setStyle(Style.STROKE);
            this.mLimitLinePaint = new Paint(1);
            this.mLimitLinePaint.setStyle(Style.STROKE);
        }
    }

    public Paint getPaintAxisLabels() {
        return this.mAxisLabelPaint;
    }

    public Paint getPaintGrid() {
        return this.mGridPaint;
    }

    public Paint getPaintAxisLine() {
        return this.mAxisLinePaint;
    }

    public Transformer getTransformer() {
        return this.mTrans;
    }

    public void computeAxis(float min, float max, boolean inverted) {
        if (this.mViewPortHandler != null && this.mViewPortHandler.contentWidth() > 10.0f && !this.mViewPortHandler.isFullyZoomedOutY()) {
            MPPointD p1 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
            MPPointD p2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
            if (!inverted) {
                min = (float) p2.y;
                max = (float) p1.y;
            } else {
                min = (float) p1.y;
                max = (float) p2.y;
            }
            MPPointD.recycleInstance(p1);
            MPPointD.recycleInstance(p2);
        }
        computeAxisValues(min, max);
    }

    /* access modifiers changed from: protected */
    public void computeAxisValues(float min, float max) {
        double last;
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
        int n = this.mAxis.isCenterAxisLabelsEnabled() ? 1 : 0;
        if (this.mAxis.isForceLabelsEnabled()) {
            interval = (double) (((float) range) / ((float) (labelCount - 1)));
            this.mAxis.mEntryCount = labelCount;
            if (this.mAxis.mEntries.length < labelCount) {
                this.mAxis.mEntries = new float[labelCount];
            }
            float v = min;
            for (int i = 0; i < labelCount; i++) {
                this.mAxis.mEntries[i] = v;
                v = (float) (((double) v) + interval);
            }
            n = labelCount;
        } else {
            double first = interval == Utils.DOUBLE_EPSILON ? Utils.DOUBLE_EPSILON : Math.ceil(((double) yMin) / interval) * interval;
            if (this.mAxis.isCenterAxisLabelsEnabled()) {
                first -= interval;
            }
            if (interval == Utils.DOUBLE_EPSILON) {
                last = Utils.DOUBLE_EPSILON;
            } else {
                last = Utils.nextUp(Math.floor(((double) yMax) / interval) * interval);
            }
            if (interval != Utils.DOUBLE_EPSILON) {
                for (double f = first; f <= last; f += interval) {
                    n++;
                }
            }
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
        if (this.mAxis.isCenterAxisLabelsEnabled()) {
            if (this.mAxis.mCenteredEntries.length < n) {
                this.mAxis.mCenteredEntries = new float[n];
            }
            float offset = ((float) interval) / 2.0f;
            for (int i3 = 0; i3 < n; i3++) {
                this.mAxis.mCenteredEntries[i3] = this.mAxis.mEntries[i3] + offset;
            }
        }
    }
}
