package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet.Mode;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class LineChartRenderer extends LineRadarRenderer {
    protected Path cubicFillPath = new Path();
    protected Path cubicPath = new Path();
    protected Canvas mBitmapCanvas;
    protected Config mBitmapConfig = Config.ARGB_8888;
    protected LineDataProvider mChart;
    protected Paint mCirclePaintInner;
    private float[] mCirclesBuffer = new float[2];
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mGenerateFilledPathBuffer = new Path();
    private HashMap<IDataSet, DataSetImageCache> mImageCaches = new HashMap<>();
    private float[] mLineBuffer = new float[4];

    private class DataSetImageCache {
        private Bitmap[] circleBitmaps;
        private Path mCirclePathBuffer;

        private DataSetImageCache() {
            this.mCirclePathBuffer = new Path();
        }

        /* access modifiers changed from: protected */
        public boolean init(ILineDataSet set) {
            int size = set.getCircleColorCount();
            if (this.circleBitmaps == null) {
                this.circleBitmaps = new Bitmap[size];
                return true;
            } else if (this.circleBitmaps.length == size) {
                return false;
            } else {
                this.circleBitmaps = new Bitmap[size];
                return true;
            }
        }

        /* access modifiers changed from: protected */
        public void fill(ILineDataSet set, boolean drawCircleHole, boolean drawTransparentCircleHole) {
            int colorCount = set.getCircleColorCount();
            float circleRadius = set.getCircleRadius();
            float circleHoleRadius = set.getCircleHoleRadius();
            for (int i = 0; i < colorCount; i++) {
                Bitmap circleBitmap = Bitmap.createBitmap((int) (((double) circleRadius) * 2.1d), (int) (((double) circleRadius) * 2.1d), Config.ARGB_4444);
                Canvas canvas = new Canvas(circleBitmap);
                this.circleBitmaps[i] = circleBitmap;
                LineChartRenderer.this.mRenderPaint.setColor(set.getCircleColor(i));
                if (drawTransparentCircleHole) {
                    this.mCirclePathBuffer.reset();
                    this.mCirclePathBuffer.addCircle(circleRadius, circleRadius, circleRadius, Direction.CW);
                    this.mCirclePathBuffer.addCircle(circleRadius, circleRadius, circleHoleRadius, Direction.CCW);
                    canvas.drawPath(this.mCirclePathBuffer, LineChartRenderer.this.mRenderPaint);
                } else {
                    canvas.drawCircle(circleRadius, circleRadius, circleRadius, LineChartRenderer.this.mRenderPaint);
                    if (drawCircleHole) {
                        canvas.drawCircle(circleRadius, circleRadius, circleHoleRadius, LineChartRenderer.this.mCirclePaintInner);
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public Bitmap getBitmap(int index) {
            return this.circleBitmaps[index % this.circleBitmaps.length];
        }
    }

    public LineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.mChart = chart;
        this.mCirclePaintInner = new Paint(1);
        this.mCirclePaintInner.setStyle(Style.FILL);
        this.mCirclePaintInner.setColor(-1);
    }

    public void initBuffers() {
    }

    public void drawData(Canvas c) {
        Bitmap drawBitmap;
        int width = (int) this.mViewPortHandler.getChartWidth();
        int height = (int) this.mViewPortHandler.getChartHeight();
        if (this.mDrawBitmap == null) {
            drawBitmap = null;
        } else {
            drawBitmap = (Bitmap) this.mDrawBitmap.get();
        }
        if (!(drawBitmap != null && drawBitmap.getWidth() == width && drawBitmap.getHeight() == height)) {
            if (width > 0 && height > 0) {
                drawBitmap = Bitmap.createBitmap(width, height, this.mBitmapConfig);
                this.mDrawBitmap = new WeakReference<>(drawBitmap);
                this.mBitmapCanvas = new Canvas(drawBitmap);
            } else {
                return;
            }
        }
        drawBitmap.eraseColor(0);
        for (ILineDataSet set : this.mChart.getLineData().getDataSets()) {
            if (set.isVisible()) {
                drawDataSet(c, set);
            }
        }
        c.drawBitmap(drawBitmap, 0.0f, 0.0f, this.mRenderPaint);
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas c, ILineDataSet dataSet) {
        if (dataSet.getEntryCount() >= 1) {
            this.mRenderPaint.setStrokeWidth(dataSet.getLineWidth());
            this.mRenderPaint.setPathEffect(dataSet.getDashPathEffect());
            switch (dataSet.getMode()) {
                case CUBIC_BEZIER:
                    drawCubicBezier(dataSet);
                    break;
                case HORIZONTAL_BEZIER:
                    drawHorizontalBezier(dataSet);
                    break;
                default:
                    drawLinear(c, dataSet);
                    break;
            }
            this.mRenderPaint.setPathEffect(null);
        }
    }

    /* access modifiers changed from: protected */
    public void drawHorizontalBezier(ILineDataSet dataSet) {
        float phaseY = this.mAnimator.getPhaseY();
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, dataSet);
        this.cubicPath.reset();
        if (this.mXBounds.range >= 1) {
            Entry cur = dataSet.getEntryForIndex(this.mXBounds.min);
            this.cubicPath.moveTo(cur.getX(), cur.getY() * phaseY);
            for (int j = this.mXBounds.min + 1; j <= this.mXBounds.range + this.mXBounds.min; j++) {
                Entry prev = cur;
                cur = dataSet.getEntryForIndex(j);
                float cpx = prev.getX() + ((cur.getX() - prev.getX()) / 2.0f);
                this.cubicPath.cubicTo(cpx, prev.getY() * phaseY, cpx, cur.getY() * phaseY, cur.getX(), cur.getY() * phaseY);
            }
        }
        if (dataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            drawCubicFill(this.mBitmapCanvas, dataSet, this.cubicFillPath, trans, this.mXBounds);
        }
        this.mRenderPaint.setColor(dataSet.getColor());
        this.mRenderPaint.setStyle(Style.STROKE);
        trans.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    /* access modifiers changed from: protected */
    public void drawCubicBezier(ILineDataSet dataSet) {
        float phaseY = this.mAnimator.getPhaseY();
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, dataSet);
        float intensity = dataSet.getCubicIntensity();
        this.cubicPath.reset();
        if (this.mXBounds.range >= 1) {
            int firstIndex = this.mXBounds.min + 1;
            int i = this.mXBounds.min + this.mXBounds.range;
            Entry prev = dataSet.getEntryForIndex(Math.max(firstIndex - 2, 0));
            Entry cur = dataSet.getEntryForIndex(Math.max(firstIndex - 1, 0));
            Entry next = cur;
            int nextIndex = -1;
            if (cur != null) {
                this.cubicPath.moveTo(cur.getX(), cur.getY() * phaseY);
                int j = this.mXBounds.min + 1;
                while (j <= this.mXBounds.range + this.mXBounds.min) {
                    Entry prevPrev = prev;
                    prev = cur;
                    cur = nextIndex == j ? next : dataSet.getEntryForIndex(j);
                    if (j + 1 < dataSet.getEntryCount()) {
                        nextIndex = j + 1;
                    } else {
                        nextIndex = j;
                    }
                    next = dataSet.getEntryForIndex(nextIndex);
                    this.cubicPath.cubicTo(prev.getX() + ((cur.getX() - prevPrev.getX()) * intensity), (prev.getY() + ((cur.getY() - prevPrev.getY()) * intensity)) * phaseY, cur.getX() - ((next.getX() - prev.getX()) * intensity), (cur.getY() - ((next.getY() - prev.getY()) * intensity)) * phaseY, cur.getX(), cur.getY() * phaseY);
                    j++;
                }
            } else {
                return;
            }
        }
        if (dataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            drawCubicFill(this.mBitmapCanvas, dataSet, this.cubicFillPath, trans, this.mXBounds);
        }
        this.mRenderPaint.setColor(dataSet.getColor());
        this.mRenderPaint.setStyle(Style.STROKE);
        trans.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    /* access modifiers changed from: protected */
    public void drawCubicFill(Canvas c, ILineDataSet dataSet, Path spline, Transformer trans, XBounds bounds) {
        float fillMin = dataSet.getFillFormatter().getFillLinePosition(dataSet, this.mChart);
        spline.lineTo(dataSet.getEntryForIndex(bounds.min + bounds.range).getX(), fillMin);
        spline.lineTo(dataSet.getEntryForIndex(bounds.min).getX(), fillMin);
        spline.close();
        trans.pathValueToPixel(spline);
        Drawable drawable = dataSet.getFillDrawable();
        if (drawable != null) {
            drawFilledPath(c, spline, drawable);
        } else {
            drawFilledPath(c, spline, dataSet.getFillColor(), dataSet.getFillAlpha());
        }
    }

    /* access modifiers changed from: protected */
    public void drawLinear(Canvas c, ILineDataSet dataSet) {
        Canvas canvas;
        int entryCount = dataSet.getEntryCount();
        boolean isDrawSteppedEnabled = dataSet.getMode() == Mode.STEPPED;
        int pointsPerEntryPair = isDrawSteppedEnabled ? 4 : 2;
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        float phaseY = this.mAnimator.getPhaseY();
        this.mRenderPaint.setStyle(Style.STROKE);
        if (dataSet.isDashedLineEnabled()) {
            canvas = this.mBitmapCanvas;
        } else {
            canvas = c;
        }
        this.mXBounds.set(this.mChart, dataSet);
        if (dataSet.isDrawFilledEnabled() && entryCount > 0) {
            drawLinearFill(c, dataSet, trans, this.mXBounds);
        }
        if (dataSet.getColors().size() > 1) {
            if (this.mLineBuffer.length <= pointsPerEntryPair * 2) {
                this.mLineBuffer = new float[(pointsPerEntryPair * 4)];
            }
            for (int j = this.mXBounds.min; j <= this.mXBounds.range + this.mXBounds.min; j++) {
                Entry e = dataSet.getEntryForIndex(j);
                if (e != null) {
                    this.mLineBuffer[0] = e.getX();
                    this.mLineBuffer[1] = e.getY() * phaseY;
                    if (j < this.mXBounds.max) {
                        Entry e2 = dataSet.getEntryForIndex(j + 1);
                        if (e2 == null) {
                            break;
                        } else if (isDrawSteppedEnabled) {
                            this.mLineBuffer[2] = e2.getX();
                            this.mLineBuffer[3] = this.mLineBuffer[1];
                            this.mLineBuffer[4] = this.mLineBuffer[2];
                            this.mLineBuffer[5] = this.mLineBuffer[3];
                            this.mLineBuffer[6] = e2.getX();
                            this.mLineBuffer[7] = e2.getY() * phaseY;
                        } else {
                            this.mLineBuffer[2] = e2.getX();
                            this.mLineBuffer[3] = e2.getY() * phaseY;
                        }
                    } else {
                        this.mLineBuffer[2] = this.mLineBuffer[0];
                        this.mLineBuffer[3] = this.mLineBuffer[1];
                    }
                    trans.pointValuesToPixel(this.mLineBuffer);
                    if (!this.mViewPortHandler.isInBoundsRight(this.mLineBuffer[0])) {
                        break;
                    } else if (this.mViewPortHandler.isInBoundsLeft(this.mLineBuffer[2]) && (this.mViewPortHandler.isInBoundsTop(this.mLineBuffer[1]) || this.mViewPortHandler.isInBoundsBottom(this.mLineBuffer[3]))) {
                        this.mRenderPaint.setColor(dataSet.getColor(j));
                        canvas.drawLines(this.mLineBuffer, 0, pointsPerEntryPair * 2, this.mRenderPaint);
                    }
                }
            }
        } else {
            if (this.mLineBuffer.length < Math.max(entryCount * pointsPerEntryPair, pointsPerEntryPair) * 2) {
                this.mLineBuffer = new float[(Math.max(entryCount * pointsPerEntryPair, pointsPerEntryPair) * 4)];
            }
            if (dataSet.getEntryForIndex(this.mXBounds.min) != null) {
                int j2 = 0;
                int x = this.mXBounds.min;
                while (x <= this.mXBounds.range + this.mXBounds.min) {
                    Entry e1 = dataSet.getEntryForIndex(x == 0 ? 0 : x - 1);
                    Entry e22 = dataSet.getEntryForIndex(x);
                    if (!(e1 == null || e22 == null)) {
                        int j3 = j2 + 1;
                        this.mLineBuffer[j2] = e1.getX();
                        int j4 = j3 + 1;
                        this.mLineBuffer[j3] = e1.getY() * phaseY;
                        if (isDrawSteppedEnabled) {
                            int j5 = j4 + 1;
                            this.mLineBuffer[j4] = e22.getX();
                            int j6 = j5 + 1;
                            this.mLineBuffer[j5] = e1.getY() * phaseY;
                            int j7 = j6 + 1;
                            this.mLineBuffer[j6] = e22.getX();
                            j4 = j7 + 1;
                            this.mLineBuffer[j7] = e1.getY() * phaseY;
                        }
                        int j8 = j4 + 1;
                        this.mLineBuffer[j4] = e22.getX();
                        j2 = j8 + 1;
                        this.mLineBuffer[j8] = e22.getY() * phaseY;
                    }
                    x++;
                }
                if (j2 > 0) {
                    trans.pointValuesToPixel(this.mLineBuffer);
                    int size = Math.max((this.mXBounds.range + 1) * pointsPerEntryPair, pointsPerEntryPair) * 2;
                    this.mRenderPaint.setColor(dataSet.getColor());
                    canvas.drawLines(this.mLineBuffer, 0, size, this.mRenderPaint);
                }
            }
        }
        this.mRenderPaint.setPathEffect(null);
    }

    /* access modifiers changed from: protected */
    public void drawLinearFill(Canvas c, ILineDataSet dataSet, Transformer trans, XBounds bounds) {
        int currentStartIndex;
        int currentEndIndex;
        Path filled = this.mGenerateFilledPathBuffer;
        int startingIndex = bounds.min;
        int endingIndex = bounds.range + bounds.min;
        int iterations = 0;
        do {
            currentStartIndex = startingIndex + (iterations * 128);
            currentEndIndex = currentStartIndex + 128;
            if (currentEndIndex > endingIndex) {
                currentEndIndex = endingIndex;
            }
            if (currentStartIndex <= currentEndIndex) {
                generateFilledPath(dataSet, currentStartIndex, currentEndIndex, filled);
                trans.pathValueToPixel(filled);
                Drawable drawable = dataSet.getFillDrawable();
                if (drawable != null) {
                    drawFilledPath(c, filled, drawable);
                } else {
                    drawFilledPath(c, filled, dataSet.getFillColor(), dataSet.getFillAlpha());
                }
            }
            iterations++;
        } while (currentStartIndex <= currentEndIndex);
    }

    private void generateFilledPath(ILineDataSet dataSet, int startIndex, int endIndex, Path outputPath) {
        float fillMin = dataSet.getFillFormatter().getFillLinePosition(dataSet, this.mChart);
        float phaseY = this.mAnimator.getPhaseY();
        boolean isDrawSteppedEnabled = dataSet.getMode() == Mode.STEPPED;
        Path filled = outputPath;
        filled.reset();
        Entry entry = dataSet.getEntryForIndex(startIndex);
        filled.moveTo(entry.getX(), fillMin);
        filled.lineTo(entry.getX(), entry.getY() * phaseY);
        Entry currentEntry = null;
        Entry previousEntry = entry;
        for (int x = startIndex + 1; x <= endIndex; x++) {
            currentEntry = dataSet.getEntryForIndex(x);
            if (isDrawSteppedEnabled) {
                filled.lineTo(currentEntry.getX(), previousEntry.getY() * phaseY);
            }
            filled.lineTo(currentEntry.getX(), currentEntry.getY() * phaseY);
            previousEntry = currentEntry;
        }
        if (currentEntry != null) {
            filled.lineTo(currentEntry.getX(), fillMin);
        }
        filled.close();
    }

    public void drawValues(Canvas c) {
        if (isDrawingValuesAllowed(this.mChart)) {
            List<ILineDataSet> dataSets = this.mChart.getLineData().getDataSets();
            for (int i = 0; i < dataSets.size(); i++) {
                ILineDataSet dataSet = (ILineDataSet) dataSets.get(i);
                if (shouldDrawValues(dataSet) && dataSet.getEntryCount() >= 1) {
                    applyValueTextStyle(dataSet);
                    Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
                    int valOffset = (int) (dataSet.getCircleRadius() * 1.75f);
                    if (!dataSet.isDrawCirclesEnabled()) {
                        valOffset /= 2;
                    }
                    this.mXBounds.set(this.mChart, dataSet);
                    float[] positions = trans.generateTransformedValuesLine(dataSet, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
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
                            Entry entry = dataSet.getEntryForIndex((j / 2) + this.mXBounds.min);
                            if (dataSet.isDrawValuesEnabled()) {
                                drawValue(c, formatter.getPointLabel(entry), x, y - ((float) valOffset), dataSet.getValueTextColor(j / 2));
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
        drawCircles(c);
    }

    /* access modifiers changed from: protected */
    public void drawCircles(Canvas c) {
        DataSetImageCache imageCache;
        this.mRenderPaint.setStyle(Style.FILL);
        float phaseY = this.mAnimator.getPhaseY();
        this.mCirclesBuffer[0] = 0.0f;
        this.mCirclesBuffer[1] = 0.0f;
        List<ILineDataSet> dataSets = this.mChart.getLineData().getDataSets();
        for (int i = 0; i < dataSets.size(); i++) {
            ILineDataSet dataSet = (ILineDataSet) dataSets.get(i);
            if (dataSet.isVisible() && dataSet.isDrawCirclesEnabled() && dataSet.getEntryCount() != 0) {
                this.mCirclePaintInner.setColor(dataSet.getCircleHoleColor());
                Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
                this.mXBounds.set(this.mChart, dataSet);
                float circleRadius = dataSet.getCircleRadius();
                float circleHoleRadius = dataSet.getCircleHoleRadius();
                boolean drawCircleHole = dataSet.isDrawCircleHoleEnabled() && circleHoleRadius < circleRadius && circleHoleRadius > 0.0f;
                boolean drawTransparentCircleHole = drawCircleHole && dataSet.getCircleHoleColor() == 1122867;
                if (this.mImageCaches.containsKey(dataSet)) {
                    imageCache = (DataSetImageCache) this.mImageCaches.get(dataSet);
                } else {
                    imageCache = new DataSetImageCache();
                    this.mImageCaches.put(dataSet, imageCache);
                }
                if (imageCache.init(dataSet)) {
                    imageCache.fill(dataSet, drawCircleHole, drawTransparentCircleHole);
                }
                int boundsRangeCount = this.mXBounds.range + this.mXBounds.min;
                for (int j = this.mXBounds.min; j <= boundsRangeCount; j++) {
                    Entry e = dataSet.getEntryForIndex(j);
                    if (e == null) {
                        break;
                    }
                    this.mCirclesBuffer[0] = e.getX();
                    this.mCirclesBuffer[1] = e.getY() * phaseY;
                    trans.pointValuesToPixel(this.mCirclesBuffer);
                    if (!this.mViewPortHandler.isInBoundsRight(this.mCirclesBuffer[0])) {
                        break;
                    }
                    if (this.mViewPortHandler.isInBoundsLeft(this.mCirclesBuffer[0]) && this.mViewPortHandler.isInBoundsY(this.mCirclesBuffer[1])) {
                        Bitmap circleBitmap = imageCache.getBitmap(j);
                        if (circleBitmap != null) {
                            c.drawBitmap(circleBitmap, this.mCirclesBuffer[0] - circleRadius, this.mCirclesBuffer[1] - circleRadius, null);
                        }
                    }
                }
            }
        }
    }

    public void drawHighlighted(Canvas c, Highlight[] indices) {
        LineData lineData = this.mChart.getLineData();
        for (Highlight high : indices) {
            ILineDataSet set = (ILineDataSet) lineData.getDataSetByIndex(high.getDataSetIndex());
            if (set != null && set.isHighlightEnabled()) {
                Entry e = set.getEntryForXValue(high.getX(), high.getY());
                if (isInBoundsX(e, set)) {
                    MPPointD pix = this.mChart.getTransformer(set.getAxisDependency()).getPixelForValues(e.getX(), e.getY() * this.mAnimator.getPhaseY());
                    high.setDraw((float) pix.x, (float) pix.y);
                    drawHighlightLines(c, (float) pix.x, (float) pix.y, set);
                }
            }
        }
    }

    public void setBitmapConfig(Config config) {
        this.mBitmapConfig = config;
        releaseBitmap();
    }

    public Config getBitmapConfig() {
        return this.mBitmapConfig;
    }

    public void releaseBitmap() {
        if (this.mBitmapCanvas != null) {
            this.mBitmapCanvas.setBitmap(null);
            this.mBitmapCanvas = null;
        }
        if (this.mDrawBitmap != null) {
            Bitmap drawBitmap = (Bitmap) this.mDrawBitmap.get();
            if (drawBitmap != null) {
                drawBitmap.recycle();
            }
            this.mDrawBitmap.clear();
            this.mDrawBitmap = null;
        }
    }
}
