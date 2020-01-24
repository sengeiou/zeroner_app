package com.iwown.sport_module.gps.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.iwown.lib_common.DensityUtil;
import java.util.List;

public class GpsAnimView extends View {
    /* access modifiers changed from: private */
    public float[] mCurrentPosition = new float[2];
    private int mHeight;
    private Bitmap mLightBallBitmap;
    private Paint mLightBallPaint;
    private Paint mLinePaint;
    /* access modifiers changed from: private */
    public Path mLinePath;
    private Bitmap mStartBitmap;
    private Paint mStartPaint;
    private Point mStartPoint;
    private Rect mStartRect;
    private int mWidth;

    public interface OnTrailChangeListener {
        void onFinish();
    }

    public GpsAnimView(Context context) {
        super(context);
        initPaint();
    }

    public GpsAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public GpsAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        this.mLinePaint = new Paint();
        this.mLinePaint.setColor(Color.parseColor("#ff00ff42"));
        this.mLinePaint.setStyle(Style.STROKE);
        this.mLinePaint.setStrokeWidth(10.0f);
        this.mLinePaint.setStrokeCap(Cap.ROUND);
        this.mLinePaint.setAntiAlias(true);
        this.mLightBallPaint = new Paint();
        this.mLightBallPaint.setAntiAlias(true);
        this.mLightBallPaint.setFilterBitmap(true);
        this.mStartPaint = new Paint();
        this.mStartPaint.setAntiAlias(true);
        this.mStartPaint.setFilterBitmap(true);
        this.mLinePath = new Path();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.mLinePath, this.mLinePaint);
        if (!(this.mLightBallBitmap == null || this.mStartRect == null)) {
            int width = this.mLightBallBitmap.getWidth();
            int height = this.mLightBallBitmap.getHeight();
            RectF rect = new RectF();
            rect.left = this.mCurrentPosition[0] - ((float) width);
            rect.right = this.mCurrentPosition[0] + ((float) width);
            rect.top = this.mCurrentPosition[1] - ((float) height);
            rect.bottom = this.mCurrentPosition[1] + ((float) height);
            canvas.drawBitmap(this.mLightBallBitmap, null, rect, this.mLightBallPaint);
        }
        if (this.mStartBitmap != null && this.mStartPoint != null) {
            if (this.mStartRect == null) {
                int width2 = this.mStartBitmap.getWidth() / 2;
                int height2 = this.mStartBitmap.getHeight() / 2;
                this.mStartRect = new Rect();
                this.mStartRect.left = this.mStartPoint.x - width2;
                this.mStartRect.right = this.mStartPoint.x + width2;
                this.mStartRect.top = this.mStartPoint.y - height2;
                this.mStartRect.bottom = this.mStartPoint.y + height2;
            }
            canvas.drawBitmap(this.mStartBitmap, null, this.mStartRect, this.mStartPaint);
        }
    }

    public void drawSportLine(List<Point> mPositions, @DrawableRes int startPointResId, @DrawableRes int lightBall, OnTrailChangeListener listener) {
        if (mPositions.size() <= 1) {
            listener.onFinish();
            return;
        }
        Path path = new Path();
        for (int i = 0; i < mPositions.size(); i++) {
            if (i == 0) {
                path.moveTo((float) ((Point) mPositions.get(i)).x, (float) ((Point) mPositions.get(i)).y);
            } else {
                path.lineTo((float) ((Point) mPositions.get(i)).x, (float) ((Point) mPositions.get(i)).y);
            }
        }
        final PathMeasure pathMeasure = new PathMeasure(path, false);
        final float length = pathMeasure.getLength();
        if (length < ((float) DensityUtil.dip2px(getContext(), 16.0f))) {
            listener.onFinish();
            return;
        }
        this.mLightBallBitmap = BitmapFactory.decodeResource(getResources(), lightBall);
        this.mStartPoint = new Point(((Point) mPositions.get(0)).x, ((Point) mPositions.get(0)).y);
        this.mStartBitmap = BitmapFactory.decodeResource(getResources(), startPointResId);
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, length});
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        final List<Point> list = mPositions;
        final OnTrailChangeListener onTrailChangeListener = listener;
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                pathMeasure.getPosTan(value, GpsAnimView.this.mCurrentPosition, null);
                if (value == 0.0f) {
                    GpsAnimView.this.mLinePath.moveTo((float) ((Point) list.get(0)).x, (float) ((Point) list.get(0)).y);
                }
                pathMeasure.getSegment(0.0f, value, GpsAnimView.this.mLinePath, true);
                GpsAnimView.this.invalidate();
                if (value == length && onTrailChangeListener != null) {
                    onTrailChangeListener.onFinish();
                }
            }
        });
        animator.start();
    }
}
