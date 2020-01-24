package com.github.mikephil.charting.charts;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.github.mikephil.charting.animation.Easing.EasingFunction;
import com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment;
import com.github.mikephil.charting.components.Legend.LegendVerticalAlignment;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.PieRadarChartTouchListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public abstract class PieRadarChartBase<T extends ChartData<? extends IDataSet<? extends Entry>>> extends Chart<T> {
    protected float mMinOffset = 0.0f;
    private float mRawRotationAngle = 270.0f;
    protected boolean mRotateEnabled = true;
    private float mRotationAngle = 270.0f;

    public abstract int getIndexForAngle(float f);

    public abstract float getRadius();

    /* access modifiers changed from: protected */
    public abstract float getRequiredBaseOffset();

    /* access modifiers changed from: protected */
    public abstract float getRequiredLegendOffset();

    public PieRadarChartBase(Context context) {
        super(context);
    }

    public PieRadarChartBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieRadarChartBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        this.mChartTouchListener = new PieRadarChartTouchListener(this);
    }

    /* access modifiers changed from: protected */
    public void calcMinMax() {
    }

    public int getMaxVisibleCount() {
        return this.mData.getEntryCount();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mTouchEnabled || this.mChartTouchListener == null) {
            return super.onTouchEvent(event);
        }
        return this.mChartTouchListener.onTouch(this, event);
    }

    public void computeScroll() {
        if (this.mChartTouchListener instanceof PieRadarChartTouchListener) {
            ((PieRadarChartTouchListener) this.mChartTouchListener).computeScroll();
        }
    }

    public void notifyDataSetChanged() {
        if (this.mData != null) {
            calcMinMax();
            if (this.mLegend != null) {
                this.mLegendRenderer.computeLegend(this.mData);
            }
            calculateOffsets();
        }
    }

    public void calculateOffsets() {
        float bottomX;
        float legendLeft = 0.0f;
        float legendRight = 0.0f;
        float legendBottom = 0.0f;
        float legendTop = 0.0f;
        if (this.mLegend != null && this.mLegend.isEnabled() && !this.mLegend.isDrawInsideEnabled()) {
            float fullLegendWidth = Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent());
            switch (this.mLegend.getOrientation()) {
                case VERTICAL:
                    float xLegendOffset = 0.0f;
                    if (this.mLegend.getHorizontalAlignment() == LegendHorizontalAlignment.LEFT || this.mLegend.getHorizontalAlignment() == LegendHorizontalAlignment.RIGHT) {
                        if (this.mLegend.getVerticalAlignment() == LegendVerticalAlignment.CENTER) {
                            xLegendOffset = fullLegendWidth + Utils.convertDpToPixel(13.0f);
                        } else {
                            float legendWidth = fullLegendWidth + Utils.convertDpToPixel(8.0f);
                            float legendHeight = this.mLegend.mNeededHeight + this.mLegend.mTextHeightMax;
                            MPPointF center = getCenter();
                            if (this.mLegend.getHorizontalAlignment() == LegendHorizontalAlignment.RIGHT) {
                                bottomX = (((float) getWidth()) - legendWidth) + 15.0f;
                            } else {
                                bottomX = legendWidth - 15.0f;
                            }
                            float bottomY = legendHeight + 15.0f;
                            float distLegend = distanceToCenter(bottomX, bottomY);
                            MPPointF reference = getPosition(center, getRadius(), getAngleForPoint(bottomX, bottomY));
                            float distReference = distanceToCenter(reference.x, reference.y);
                            float minOffset = Utils.convertDpToPixel(5.0f);
                            if (bottomY >= center.y && ((float) getHeight()) - legendWidth > ((float) getWidth())) {
                                xLegendOffset = legendWidth;
                            } else if (distLegend < distReference) {
                                xLegendOffset = minOffset + (distReference - distLegend);
                            }
                            MPPointF.recycleInstance(center);
                            MPPointF.recycleInstance(reference);
                        }
                    }
                    switch (this.mLegend.getHorizontalAlignment()) {
                        case LEFT:
                            legendLeft = xLegendOffset;
                            break;
                        case RIGHT:
                            legendRight = xLegendOffset;
                            break;
                        case CENTER:
                            switch (this.mLegend.getVerticalAlignment()) {
                                case TOP:
                                    legendTop = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                    break;
                                case BOTTOM:
                                    legendBottom = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                    break;
                            }
                    }
                case HORIZONTAL:
                    if (this.mLegend.getVerticalAlignment() == LegendVerticalAlignment.TOP || this.mLegend.getVerticalAlignment() == LegendVerticalAlignment.BOTTOM) {
                        float yLegendOffset = Math.min(this.mLegend.mNeededHeight + getRequiredLegendOffset(), this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                        switch (this.mLegend.getVerticalAlignment()) {
                            case TOP:
                                legendTop = yLegendOffset;
                                break;
                            case BOTTOM:
                                legendBottom = yLegendOffset;
                                break;
                        }
                    }
                    break;
            }
            legendLeft += getRequiredBaseOffset();
            legendRight += getRequiredBaseOffset();
            legendTop += getRequiredBaseOffset();
            legendBottom += getRequiredBaseOffset();
        }
        float minOffset2 = Utils.convertDpToPixel(this.mMinOffset);
        if (this instanceof RadarChart) {
            XAxis x = getXAxis();
            if (x.isEnabled() && x.isDrawLabelsEnabled()) {
                minOffset2 = Math.max(minOffset2, (float) x.mLabelRotatedWidth);
            }
        }
        float legendTop2 = legendTop + getExtraTopOffset();
        float legendRight2 = legendRight + getExtraRightOffset();
        float legendBottom2 = legendBottom + getExtraBottomOffset();
        float offsetLeft = Math.max(minOffset2, legendLeft + getExtraLeftOffset());
        float offsetTop = Math.max(minOffset2, legendTop2);
        float offsetRight = Math.max(minOffset2, legendRight2);
        float offsetBottom = Math.max(minOffset2, Math.max(getRequiredBaseOffset(), legendBottom2));
        this.mViewPortHandler.restrainViewPort(offsetLeft, offsetTop, offsetRight, offsetBottom);
        if (this.mLogEnabled) {
            Log.i(Chart.LOG_TAG, "offsetLeft: " + offsetLeft + ", offsetTop: " + offsetTop + ", offsetRight: " + offsetRight + ", offsetBottom: " + offsetBottom);
        }
    }

    public float getAngleForPoint(float x, float y) {
        MPPointF c = getCenterOffsets();
        double tx = (double) (x - c.x);
        double ty = (double) (y - c.y);
        float angle = (float) Math.toDegrees(Math.acos(ty / Math.sqrt((tx * tx) + (ty * ty))));
        if (x > c.x) {
            angle = 360.0f - angle;
        }
        float angle2 = angle + 90.0f;
        if (angle2 > 360.0f) {
            angle2 -= 360.0f;
        }
        MPPointF.recycleInstance(c);
        return angle2;
    }

    public MPPointF getPosition(MPPointF center, float dist, float angle) {
        MPPointF p = MPPointF.getInstance(0.0f, 0.0f);
        getPosition(center, dist, angle, p);
        return p;
    }

    public void getPosition(MPPointF center, float dist, float angle, MPPointF outputPoint) {
        outputPoint.x = (float) (((double) center.x) + (((double) dist) * Math.cos(Math.toRadians((double) angle))));
        outputPoint.y = (float) (((double) center.y) + (((double) dist) * Math.sin(Math.toRadians((double) angle))));
    }

    public float distanceToCenter(float x, float y) {
        float xDist;
        float yDist;
        MPPointF c = getCenterOffsets();
        if (x > c.x) {
            xDist = x - c.x;
        } else {
            xDist = c.x - x;
        }
        if (y > c.y) {
            yDist = y - c.y;
        } else {
            yDist = c.y - y;
        }
        float dist = (float) Math.sqrt(Math.pow((double) xDist, 2.0d) + Math.pow((double) yDist, 2.0d));
        MPPointF.recycleInstance(c);
        return dist;
    }

    public void setRotationAngle(float angle) {
        this.mRawRotationAngle = angle;
        this.mRotationAngle = Utils.getNormalizedAngle(this.mRawRotationAngle);
    }

    public float getRawRotationAngle() {
        return this.mRawRotationAngle;
    }

    public float getRotationAngle() {
        return this.mRotationAngle;
    }

    public void setRotationEnabled(boolean enabled) {
        this.mRotateEnabled = enabled;
    }

    public boolean isRotationEnabled() {
        return this.mRotateEnabled;
    }

    public float getMinOffset() {
        return this.mMinOffset;
    }

    public void setMinOffset(float minOffset) {
        this.mMinOffset = minOffset;
    }

    public float getDiameter() {
        RectF content = this.mViewPortHandler.getContentRect();
        content.left += getExtraLeftOffset();
        content.top += getExtraTopOffset();
        content.right -= getExtraRightOffset();
        content.bottom -= getExtraBottomOffset();
        return Math.min(content.width(), content.height());
    }

    public float getYChartMax() {
        return 0.0f;
    }

    public float getYChartMin() {
        return 0.0f;
    }

    @SuppressLint({"NewApi"})
    public void spin(int durationmillis, float fromangle, float toangle, EasingFunction easing) {
        setRotationAngle(fromangle);
        ObjectAnimator spinAnimator = ObjectAnimator.ofFloat(this, "rotationAngle", new float[]{fromangle, toangle});
        spinAnimator.setDuration((long) durationmillis);
        spinAnimator.setInterpolator(easing);
        spinAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                PieRadarChartBase.this.postInvalidate();
            }
        });
        spinAnimator.start();
    }
}
