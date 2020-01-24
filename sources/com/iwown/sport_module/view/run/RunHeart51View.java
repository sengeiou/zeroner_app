package com.iwown.sport_module.view.run;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.util.ArrayUtil;
import java.text.DecimalFormat;

public class RunHeart51View extends View {
    int circle;
    private int circleX;
    private int circleY;
    private DecimalFormat format;
    float lastRound = 270.0f;
    private int lineWid1;
    private int lineWid2;
    private int[] mMin = new int[6];
    private int mWidth;
    private int padRound;
    private Paint roundPaint;
    private int roundWidth;
    private int textPad;
    private int topPad;
    private int totalMin = 0;

    public RunHeart51View(Context context) {
        super(context);
        initPaint();
    }

    public RunHeart51View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        this.roundPaint = new Paint();
        this.roundPaint.setAntiAlias(true);
        this.roundPaint.setColor(-10000537);
        this.roundPaint.setStrokeWidth((float) DensityUtil.dip2px(getContext(), 1.0f));
        this.roundPaint.setStyle(Style.FILL);
        this.roundPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "DINCOND-BOLD.ttf"));
        this.roundPaint.setTextSize((float) DensityUtil.dip2px(getContext(), 20.0f));
        this.roundWidth = DensityUtil.dip2px(getContext(), 15.0f);
        this.topPad = DensityUtil.dip2px(getContext(), 40.0f);
        this.padRound = DensityUtil.dip2px(getContext(), 8.0f);
        this.lineWid1 = DensityUtil.dip2px(getContext(), 10.0f);
        this.lineWid2 = DensityUtil.dip2px(getContext(), 50.0f);
        this.format = new DecimalFormat("0");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = getMeasuredWidth();
    }

    private void setLastRound(float addNum) {
        this.lastRound += addNum;
        if (this.lastRound > 360.0f) {
            this.lastRound -= 360.0f;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.lastRound = 270.0f;
        RectF round1 = new RectF();
        round1.left = (float) (this.mWidth / 4);
        round1.top = (float) this.topPad;
        round1.right = (float) ((this.mWidth * 3) / 4);
        round1.bottom = (float) (((this.mWidth * 2) / 4) + this.topPad);
        this.circleX = this.mWidth / 2;
        this.circleY = (this.mWidth / 4) + this.topPad;
        this.circle = this.mWidth / 4;
        int mAngle = 0;
        if (this.totalMin == 0) {
            this.totalMin = 1;
            return;
        }
        if (this.mMin[0] > 0) {
            this.roundPaint.setColor(-11444093);
            float addAngle = (float) ((((double) (this.mMin[0] * 360)) * 1.0d) / ((double) this.totalMin));
            canvas.drawArc(round1, this.lastRound, addAngle, true, this.roundPaint);
            mAngle = (int) (((float) 0) + addAngle);
            if ((((double) this.mMin[0]) * 100.0d) / ((double) this.totalMin) > 5.0d) {
                drawPercent((float) mAngle, addAngle, this.format.format((((double) this.mMin[0]) * 100.0d) / ((double) this.totalMin)) + "%", canvas);
            }
            this.roundPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            setLastRound(addAngle);
        }
        if (this.mMin[1] > 0) {
            this.roundPaint.setColor(-16746515);
            float addAngle2 = (float) ((((double) (this.mMin[1] * 360)) * 1.0d) / ((double) this.totalMin));
            canvas.drawArc(round1, this.lastRound, addAngle2, true, this.roundPaint);
            mAngle = (int) (((float) mAngle) + addAngle2);
            if ((((double) this.mMin[1]) * 100.0d) / ((double) this.totalMin) > 5.0d) {
                drawPercent((float) mAngle, addAngle2, this.format.format((((double) this.mMin[1]) * 100.0d) / ((double) this.totalMin)) + "%", canvas);
            }
            this.roundPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            setLastRound(addAngle2);
        }
        if (this.mMin[2] > 0) {
            this.roundPaint.setColor(-14175488);
            float addAngle3 = (float) ((((double) (this.mMin[2] * 360)) * 1.0d) / ((double) this.totalMin));
            canvas.drawArc(round1, this.lastRound, addAngle3, true, this.roundPaint);
            mAngle = (int) (((float) mAngle) + addAngle3);
            if ((((double) this.mMin[2]) * 100.0d) / ((double) this.totalMin) > 5.0d) {
                drawPercent((float) mAngle, addAngle3, this.format.format((((double) this.mMin[2]) * 100.0d) / ((double) this.totalMin)) + "%", canvas);
            }
            this.roundPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            setLastRound(addAngle3);
        }
        if (this.mMin[3] > 0) {
            this.roundPaint.setColor(-23000);
            float addAngle4 = (float) ((((double) (this.mMin[3] * 360)) * 1.0d) / ((double) this.totalMin));
            canvas.drawArc(round1, this.lastRound, addAngle4, true, this.roundPaint);
            mAngle = (int) (((float) mAngle) + addAngle4);
            if ((((double) this.mMin[3]) * 100.0d) / ((double) this.totalMin) > 5.0d) {
                drawPercent((float) mAngle, addAngle4, this.format.format((((double) this.mMin[3]) * 100.0d) / ((double) this.totalMin)) + "%", canvas);
            }
            this.roundPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            setLastRound(addAngle4);
        }
        if (this.mMin[4] > 0) {
            this.roundPaint.setColor(-65407);
            float addAngle5 = (float) ((((double) (this.mMin[4] * 360)) * 1.0d) / ((double) this.totalMin));
            canvas.drawArc(round1, this.lastRound, addAngle5, true, this.roundPaint);
            mAngle = (int) (((float) mAngle) + addAngle5);
            if ((((double) this.mMin[4]) * 100.0d) / ((double) this.totalMin) > 5.0d) {
                drawPercent((float) mAngle, addAngle5, this.format.format((((double) this.mMin[4]) * 100.0d) / ((double) this.totalMin)) + "%", canvas);
            }
            this.roundPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            setLastRound(addAngle5);
        }
        if (this.mMin[5] > 0) {
            this.roundPaint.setColor(-178895);
            float addAngle6 = (float) ((((double) (this.mMin[5] * 360)) * 1.0d) / ((double) this.totalMin));
            canvas.drawArc(round1, this.lastRound, addAngle6, true, this.roundPaint);
            int mAngle2 = (int) (((float) mAngle) + addAngle6);
            if ((((double) this.mMin[5]) * 100.0d) / ((double) this.totalMin) > 5.0d) {
                drawPercent((float) mAngle2, addAngle6, this.format.format((((double) this.mMin[5]) * 100.0d) / ((double) this.totalMin)) + "%", canvas);
            }
            this.roundPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        }
        RectF round2 = new RectF();
        round2.left = (float) ((this.mWidth / 4) + this.roundWidth);
        round2.top = (float) (this.topPad + this.roundWidth);
        round2.right = (float) (((this.mWidth * 3) / 4) - this.roundWidth);
        round2.bottom = (float) ((((this.mWidth * 2) / 4) + this.topPad) - this.roundWidth);
        this.roundPaint.setColor(RunActivitySkin.RunActy_Item_BG);
        canvas.drawArc(round2, 0.0f, 360.0f, true, this.roundPaint);
    }

    private void drawPercent(float tAngle, float mAngle, String perent, Canvas canvas) {
        float angle = tAngle - (mAngle / 2.0f);
        float[] line = new float[2];
        if (angle >= 0.0f) {
            if (angle >= 0.0f && angle < 90.0f) {
                line[0] = (float) (Math.sin((3.141592653589793d * ((double) angle)) / 180.0d) * ((double) (this.circle + this.padRound)));
                line[1] = -((float) (Math.cos((3.141592653589793d * ((double) angle)) / 180.0d) * ((double) (this.circle + this.padRound))));
                float x0 = ((float) this.circleX) + line[0];
                float y0 = ((float) this.circleY) + line[1];
                float x1 = ((float) (Math.sin(0.7853981633974483d) * ((double) this.lineWid1))) + x0;
                float y1 = y0 - ((float) (Math.sin(0.7853981633974483d) * ((double) this.lineWid1)));
                canvas.drawLine(x0, y0, x1, y1, this.roundPaint);
                canvas.drawLine(x1, y1, x1 + ((float) this.lineWid2), y1, this.roundPaint);
                this.roundPaint.setTextAlign(Align.LEFT);
                canvas.drawText(perent, ((float) this.padRound) + x1, y1 - ((float) (this.padRound / 2)), this.roundPaint);
            } else if (angle < 180.0f) {
                line[0] = (float) (Math.cos((3.141592653589793d * ((double) (angle - 90.0f))) / 180.0d) * ((double) (this.circle + this.padRound)));
                line[1] = (float) (Math.sin((3.141592653589793d * ((double) (angle - 90.0f))) / 180.0d) * ((double) (this.circle + this.padRound)));
                float x02 = ((float) this.circleX) + line[0];
                float y02 = ((float) this.circleY) + line[1];
                float x12 = ((float) (Math.sin(0.7853981633974483d) * ((double) this.lineWid1))) + x02;
                float y12 = y02 + ((float) (Math.sin(0.7853981633974483d) * ((double) this.lineWid1)));
                canvas.drawLine(x02, y02, x12, y12, this.roundPaint);
                canvas.drawLine(x12, y12, x12 + ((float) this.lineWid2), y12, this.roundPaint);
                this.roundPaint.setTextAlign(Align.LEFT);
                canvas.drawText(perent, ((float) this.padRound) + x12, y12 - ((float) (this.padRound / 2)), this.roundPaint);
            } else if (angle < 270.0f) {
                line[0] = -((float) (Math.sin((3.141592653589793d * ((double) (angle - 180.0f))) / 180.0d) * ((double) (this.circle + this.padRound))));
                line[1] = (float) (Math.cos((3.141592653589793d * ((double) (angle - 180.0f))) / 180.0d) * ((double) (this.circle + this.padRound)));
                float x03 = ((float) this.circleX) + line[0];
                float y03 = ((float) this.circleY) + line[1];
                float x13 = x03 - ((float) (Math.sin(0.7853981633974483d) * ((double) this.lineWid1)));
                float y13 = y03 + ((float) (Math.sin(0.7853981633974483d) * ((double) this.lineWid1)));
                canvas.drawLine(x03, y03, x13, y13, this.roundPaint);
                canvas.drawLine(x13, y13, x13 - ((float) this.lineWid2), y13, this.roundPaint);
                this.roundPaint.setTextAlign(Align.RIGHT);
                canvas.drawText(perent, x13 - ((float) this.padRound), y13 - ((float) (this.padRound / 2)), this.roundPaint);
            } else if (angle < 360.0f) {
                line[0] = -((float) (Math.cos((3.141592653589793d * ((double) (angle - 270.0f))) / 180.0d) * ((double) (this.circle + this.padRound))));
                line[1] = -((float) (Math.sin((3.141592653589793d * ((double) (angle - 270.0f))) / 180.0d) * ((double) (this.circle + this.padRound))));
                float x04 = ((float) this.circleX) + line[0];
                float y04 = ((float) this.circleY) + line[1];
                float x14 = x04 - ((float) (Math.sin(0.7853981633974483d) * ((double) this.lineWid1)));
                float y14 = y04 - ((float) (Math.sin(0.7853981633974483d) * ((double) this.lineWid1)));
                canvas.drawLine(x04, y04, x14, y14, this.roundPaint);
                canvas.drawLine(x14, y14, x14 - ((float) this.lineWid2), y14, this.roundPaint);
                this.roundPaint.setTextAlign(Align.RIGHT);
                canvas.drawText(perent, x14 - ((float) this.padRound), y14 - ((float) (this.padRound / 2)), this.roundPaint);
            }
        }
    }

    public void setData(int totalMin2, int[] mins) {
        this.mMin = ArrayUtil.clone(mins);
        this.totalMin = totalMin2;
        postInvalidate();
    }
}
