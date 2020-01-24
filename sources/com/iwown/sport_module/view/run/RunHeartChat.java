package com.iwown.sport_module.view.run;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.lib_common.DensityUtil;
import java.util.ArrayList;
import java.util.List;

public class RunHeartChat extends View {
    float XWidth;
    float YWidth;
    float dis0;
    float dis1;
    private Paint dottedPaint;
    private List<Integer> heartData = new ArrayList();
    private Context mContext;
    private int mHeight;
    private int mWidth;
    private int mYHeight;
    private int maxHeart = 200;
    private int minHeart = 0;
    private int pad;
    private Paint textPaint;
    private int textSize = 12;
    private int textXHeight;
    private int textYVae;
    private float xAve;
    private int xNum = 30;
    private int[] xStr = new int[7];
    private float yAve;
    private int[] yHeart = new int[9];
    private Paint yPaint;
    private int ytxtHe;

    public RunHeartChat(Context context) {
        super(context);
        this.mContext = context;
        initPaint();
    }

    public RunHeartChat(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    public RunHeartChat(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPaint();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mHeight = getMeasuredHeight();
        this.mWidth = getMeasuredWidth();
        this.yAve = (float) ((this.mHeight - this.textXHeight) / 5);
        this.xAve = ((((float) this.mWidth) - this.YWidth) - (this.XWidth * 2.0f)) / 47.0f;
        this.mYHeight = (this.mHeight - this.textXHeight) - this.pad;
    }

    private void initPaint() {
        this.textPaint = new Paint();
        this.textPaint.setColor(-1);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setStrokeWidth(2.0f);
        this.textPaint.setTextSize((float) DensityUtil.dip2px(getContext(), (float) this.textSize));
        this.yPaint = new Paint();
        this.yPaint.setColor(-1);
        this.yPaint.setAntiAlias(true);
        this.yPaint.setStrokeWidth(2.0f);
        this.yPaint.setTextSize((float) DensityUtil.dip2px(getContext(), 12.0f));
        this.yPaint.setTextAlign(Align.RIGHT);
        this.dottedPaint = new Paint(1);
        this.dottedPaint.setStyle(Style.STROKE);
        this.dottedPaint.setColor(-2236963);
        this.dottedPaint.setStrokeWidth(2.0f);
        this.dottedPaint.setPathEffect(new DashPathEffect(new float[]{3.0f, 14.0f}, 1.0f));
        this.YWidth = this.textPaint.measureText("200");
        this.XWidth = this.textPaint.measureText("60") / 2.0f;
        this.textYVae = this.maxHeart / 5;
        this.textXHeight = DensityUtil.dip2px(getContext(), 16.0f);
        this.ytxtHe = DensityUtil.dip2px(getContext(), 4.0f);
        this.pad = DensityUtil.dip2px(getContext(), 7.0f);
        this.maxHeart = SportUtil.getMaxHeart(ModuleRouteUserInfoService.getInstance().getUserInfo(this.mContext).getAge());
        for (int i = 0; i < 8; i++) {
            this.yHeart[i] = (this.maxHeart * (10 - i)) / 10;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXYAxis(canvas);
        drawData(canvas);
    }

    private void drawXYAxis(Canvas canvas) {
        this.textPaint.setColor(-2236963);
        this.dis0 = this.YWidth + (this.textPaint.measureText("0") / 2.0f) + ((float) DensityUtil.dip2px(this.mContext, 3.0f));
        this.dis1 = ((float) this.mWidth) - this.textPaint.measureText(String.valueOf(this.xNum));
        canvas.drawLine(this.dis0, (float) (this.mHeight - this.textXHeight), this.dis1, (float) (this.mHeight - this.textXHeight), this.textPaint);
        this.textPaint.setTextAlign(Align.CENTER);
        this.textPaint.setColor(-1);
        float dis11 = ((((float) this.mWidth) - this.textPaint.measureText(String.valueOf(this.xNum))) - this.dis0) / 6.0f;
        for (int i = 0; i < this.xStr.length; i++) {
            canvas.drawText(this.xStr[i] + "", (((float) i) * dis11) + this.dis0, (float) (this.mHeight - 2), this.textPaint);
        }
        this.textPaint.setTextAlign(Align.CENTER);
        for (int i2 = 0; i2 < 9; i2++) {
            if (i2 == 8) {
                canvas.drawText(this.yHeart[i2] + "", this.YWidth, (float) ((this.mYHeight - ((this.mYHeight * this.yHeart[i2]) / this.maxHeart)) + this.pad), this.yPaint);
            } else {
                if (i2 < 7) {
                    Path mPath = new Path();
                    mPath.moveTo(this.dis0, (float) ((this.mYHeight - ((this.mYHeight * this.yHeart[i2]) / this.maxHeart)) + this.pad));
                    mPath.lineTo(((float) this.mWidth) - this.XWidth, (float) ((this.mYHeight - ((this.mYHeight * this.yHeart[i2]) / this.maxHeart)) + this.pad));
                    canvas.drawPath(mPath, this.dottedPaint);
                }
                canvas.drawText(this.yHeart[i2] + "", this.YWidth, (float) ((this.mYHeight - ((this.mYHeight * this.yHeart[i2]) / this.maxHeart)) + this.ytxtHe + this.pad), this.yPaint);
            }
        }
    }

    private void drawData(Canvas canvas) {
        this.textPaint.setStrokeWidth(4.0f);
        this.textPaint.setColor(-15631617);
        float xDis = this.dis1 - this.dis0;
        if (this.heartData != null) {
            for (int i = 0; i < this.heartData.size() - 1; i++) {
                if (!(((Integer) this.heartData.get(i)).intValue() == 0 || ((Integer) this.heartData.get(i + 1)).intValue() == 0 || ((Integer) this.heartData.get(i)).intValue() == 255 || ((Integer) this.heartData.get(i + 1)).intValue() == 255)) {
                    Canvas canvas2 = canvas;
                    canvas2.drawLine(((((float) i) * xDis) / ((float) this.xNum)) + this.dis0, (float) ((this.mYHeight - ((((Integer) this.heartData.get(i)).intValue() * this.mYHeight) / this.maxHeart)) + this.pad), ((((float) (i + 1)) * xDis) / ((float) this.xNum)) + this.dis0, (float) ((this.mYHeight - ((((Integer) this.heartData.get(i + 1)).intValue() * this.mYHeight) / this.maxHeart)) + this.pad), this.textPaint);
                }
            }
        }
    }

    public void setData(List<Integer> hearts) {
        if (hearts != null && hearts.size() > 0) {
            this.heartData.clear();
            this.heartData.addAll(hearts);
            this.xNum = hearts.size() % 6 == 0 ? hearts.size() : ((hearts.size() / 6) * 6) + 6;
            for (int i = 0; i < this.xStr.length; i++) {
                this.xStr[i] = (this.xNum * i) / 6;
            }
            postInvalidate();
        }
    }
}
