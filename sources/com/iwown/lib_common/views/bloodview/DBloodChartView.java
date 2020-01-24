package com.iwown.lib_common.views.bloodview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.R;
import com.socks.library.KLog;
import java.util.List;

public class DBloodChartView extends View {
    private static int top_hdbp;
    private static int top_hsbp;
    private static int top_ldbp;
    private static int top_lsdp;
    private static int top_timedp;
    private String TAG = "";
    BpCllback callback;
    private List<int[]> dataList;
    private float downX;
    private float downY;
    private boolean firstDown2Move;
    private int mTouchSlop = 10;
    private int margin = 20;
    private int marginX = 30;
    private Paint paintAxes;
    private Paint paintCoordinate;
    private Paint paintCurve;
    private Paint paintRectF;
    private Paint paintTable;
    private Paint paintValue;
    private float touchX;
    private float touchY;
    private int userIntent;
    private String[] xLabel;
    private int xPoint;
    private int xScale;
    private String[] yLabel;
    private int yPoint;
    private int yScale;

    public interface BpCllback {
        void bpData(int i, int i2, int i3, int i4, int i5);
    }

    public BpCllback getCallback() {
        return this.callback;
    }

    public void setCallback(BpCllback callback2) {
        this.callback = callback2;
    }

    public DBloodChartView(Context context, List<int[]> dataList2) {
        super(context);
        this.dataList = dataList2;
    }

    public DBloodChartView(Context context) {
        super(context);
    }

    public DBloodChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBloodChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void invalidate() {
        super.invalidate();
    }

    public void setDate(List<int[]> dataList2) {
        this.dataList = dataList2;
        invalidate();
    }

    public void init() {
        int[] Xnum = new int[25];
        this.xLabel = new String[25];
        for (int i = 0; i < 25; i++) {
            Xnum[i] = i;
            if (i == 24) {
                this.xLabel[i] = "23:59";
            } else {
                this.xLabel[i] = Xnum[i] + ":00";
            }
        }
        this.yLabel = new String[]{"0", "50", "100", "150", "200"};
        this.xPoint = this.margin + this.marginX;
        this.yPoint = getHeight() - this.margin;
        this.xScale = ((getWidth() - (this.margin * 2)) - this.marginX) / (this.xLabel.length - 1);
        this.yScale = (getHeight() - (this.margin * 2)) / (this.yLabel.length - 1);
        this.paintAxes = new Paint();
        this.paintAxes.setStyle(Style.STROKE);
        this.paintAxes.setColor(ContextCompat.getColor(getContext(), R.color.blood_chart_top_paint));
        this.paintAxes.setStrokeWidth(28.0f);
        this.paintCoordinate = new Paint();
        this.paintCoordinate.setStyle(Style.STROKE);
        this.paintCoordinate.setDither(true);
        this.paintCoordinate.setAntiAlias(true);
        this.paintCoordinate.setColor(ContextCompat.getColor(getContext(), R.color.pickerview_wheelview_textcolor_divider));
        this.paintCoordinate.setTextSize((float) DensityUtil.dip2px(getContext(), 10.0f));
        this.paintTable = new Paint();
        this.paintTable.setStyle(Style.STROKE);
        this.paintTable.setAntiAlias(true);
        this.paintTable.setDither(true);
        this.paintTable.setColor(ContextCompat.getColor(getContext(), R.color.pickerview_wheelview_textcolor_divider));
        this.paintTable.setStrokeWidth(2.0f);
        this.paintCurve = new Paint();
        this.paintCurve.setStyle(Style.STROKE);
        this.paintCurve.setDither(true);
        this.paintCurve.setAntiAlias(true);
        this.paintCurve.setStrokeWidth(3.0f);
        this.paintCurve.setColor(ContextCompat.getColor(getContext(), R.color.pickerview_wheelview_textcolor_out));
        this.paintRectF = new Paint();
        this.paintRectF.setStyle(Style.FILL);
        this.paintRectF.setDither(true);
        this.paintRectF.setAntiAlias(true);
        this.paintRectF.setStrokeWidth(3.0f);
        this.paintValue = new Paint();
        this.paintValue.setStyle(Style.STROKE);
        this.paintValue.setAntiAlias(true);
        this.paintValue.setDither(true);
        this.paintValue.setTextAlign(Align.CENTER);
        this.paintValue.setTextSize((float) DensityUtil.dip2px(getContext(), 10.0f));
    }

    /* access modifiers changed from: protected */
    @RequiresApi(api = 21)
    public void onDraw(Canvas canvas) {
        init();
        drawTable(canvas, this.paintTable);
        drawAxesLine(canvas, this.paintAxes);
        drawCoordinate(canvas, this.paintCoordinate);
        if (this.dataList != null) {
            Path mpath = new Path();
            String str = "";
            for (int j = 0; j < 24; j++) {
                if (((int[]) this.dataList.get(0))[j] > 0 && ((int[]) this.dataList.get(1))[j] > 0) {
                    mpath.moveTo((float) (this.xPoint + (this.xScale * j) + 25), toY(((int[]) this.dataList.get(0))[j]));
                    mpath.lineTo((float) (this.xPoint + (this.xScale * j) + 25), toY(((int[]) this.dataList.get(1))[j]));
                    canvas.drawPath(mpath, this.paintAxes);
                }
                if (((int[]) this.dataList.get(2))[j] > 0 && ((int[]) this.dataList.get(3))[j] > 0) {
                    mpath.moveTo((float) (this.xPoint + (this.xScale * j) + 25), toY(((int[]) this.dataList.get(2))[j]));
                    mpath.lineTo((float) (this.xPoint + (this.xScale * j) + 25), toY(((int[]) this.dataList.get(3))[j]));
                    canvas.drawPath(mpath, this.paintAxes);
                }
            }
            Bitmap sbpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sbpchart_2x);
            Bitmap dbpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dbpblood_chart2x);
            Bitmap sbpBitmapW = BitmapFactory.decodeResource(getResources(), R.drawable.whitesbp3x);
            Bitmap dbpBitmapW = BitmapFactory.decodeResource(getResources(), R.drawable.whitedbp3x);
            for (int i = 0; i < this.dataList.size(); i++) {
                if (i < 2) {
                    drawValue(canvas, this.paintRectF, (int[]) this.dataList.get(i), sbpBitmap);
                } else {
                    drawValue(canvas, this.paintRectF, (int[]) this.dataList.get(i), dbpBitmap);
                }
            }
            if (this.touchX == 0.0f && this.touchY == 0.0f) {
                this.callback.bpData(0, 0, 0, 0, 0);
                return;
            }
            int tochdataX = (int) Math.rint((double) (((this.touchX - ((float) this.xPoint)) / ((float) this.xScale)) + 1.0f));
            if (this.dataList == null || tochdataX >= 25 || tochdataX < 0) {
                this.callback.bpData(0, 0, 0, 0, 0);
                return;
            }
            top_hsbp = ((int[]) this.dataList.get(0))[tochdataX];
            top_lsdp = ((int[]) this.dataList.get(1))[tochdataX];
            top_hdbp = ((int[]) this.dataList.get(2))[tochdataX];
            top_ldbp = ((int[]) this.dataList.get(3))[tochdataX];
            top_timedp = tochdataX;
            if (top_hdbp == 0 && top_lsdp == 0 && top_hdbp == 0 && top_ldbp == 0) {
                this.callback.bpData(0, 0, 0, 0, 0);
            } else if (this.callback != null) {
                KLog.i("=======滑动显示数据=========" + top_hsbp + "   " + top_lsdp + "   " + top_hdbp + "   " + top_ldbp + "  " + top_timedp);
                this.callback.bpData(top_hsbp, top_lsdp, top_hdbp, top_ldbp, top_timedp);
                if (((int[]) this.dataList.get(0))[tochdataX] > 0) {
                    Canvas canvas2 = canvas;
                    Bitmap bitmap = sbpBitmapW;
                    canvas2.drawBitmap(bitmap, null, new RectF((float) (this.xPoint + (this.xScale * tochdataX) + 10), toY(((int[]) this.dataList.get(0))[tochdataX]) - 25.0f, (float) (this.xPoint + (this.xScale * tochdataX) + 40), toY(((int[]) this.dataList.get(0))[tochdataX]) + 5.0f), null);
                }
                if (((int[]) this.dataList.get(1))[tochdataX] > 0) {
                    Canvas canvas3 = canvas;
                    Bitmap bitmap2 = sbpBitmapW;
                    canvas3.drawBitmap(bitmap2, null, new RectF((float) (this.xPoint + (this.xScale * tochdataX) + 10), toY(((int[]) this.dataList.get(1))[tochdataX]) - 25.0f, (float) (this.xPoint + (this.xScale * tochdataX) + 40), toY(((int[]) this.dataList.get(1))[tochdataX]) + 5.0f), null);
                }
                if (((int[]) this.dataList.get(2))[tochdataX] > 0) {
                    Canvas canvas4 = canvas;
                    canvas4.drawBitmap(dbpBitmapW, null, new RectF((float) (this.xPoint + (this.xScale * tochdataX) + 10), toY(((int[]) this.dataList.get(2))[tochdataX]) - 25.0f, (float) (this.xPoint + (this.xScale * tochdataX) + 40), toY(((int[]) this.dataList.get(2))[tochdataX]) + 5.0f), null);
                }
                if (((int[]) this.dataList.get(3))[tochdataX] > 0) {
                    Canvas canvas5 = canvas;
                    canvas5.drawBitmap(dbpBitmapW, null, new RectF((float) (this.xPoint + (this.xScale * tochdataX) + 10), toY(((int[]) this.dataList.get(3))[tochdataX]) - 25.0f, (float) (this.xPoint + (this.xScale * tochdataX) + 40), toY(((int[]) this.dataList.get(3))[tochdataX]) + 5.0f), null);
                }
            }
        }
    }

    private void drawAxesLine(Canvas canvas, Paint paint) {
    }

    private void drawTable(Canvas canvas, Paint paint) {
        Path path = new Path();
        for (int i = 0; this.yPoint - (this.yScale * i) >= this.margin; i++) {
            int startY = (this.yPoint - (this.yScale * i)) - 15;
            int stopX = this.xPoint + ((this.xLabel.length - 1) * this.xScale) + 23;
            path.moveTo((float) (this.xPoint + 23), (float) startY);
            path.lineTo((float) stopX, (float) startY);
            canvas.drawPath(path, paint);
            paint.setPathEffect(new DashPathEffect(new float[]{8.0f, 10.0f}, 0.0f));
        }
        for (int i2 = 0; this.xScale * i2 <= getWidth() - this.margin; i2++) {
            if (i2 % 3 == 0) {
                int startX = this.xPoint + (this.xScale * i2) + 23;
                int stopY = (this.yPoint - ((this.yLabel.length - 1) * this.yScale)) - 15;
                path.moveTo((float) startX, (float) (this.yPoint - 15));
                path.lineTo((float) startX, (float) stopY);
                canvas.drawPath(path, paint);
                paint.setPathEffect(new DashPathEffect(new float[]{8.0f, 10.0f}, 0.0f));
            }
        }
    }

    private void drawCoordinate(Canvas canvas, Paint paint) {
        int offsetX;
        int offsetY;
        for (int i = 0; i <= this.xLabel.length - 1; i++) {
            if (i == 6 || i == 12 || i == 18 || i == 24) {
                paint.setTextAlign(Align.CENTER);
                canvas.drawText(this.xLabel[i], (float) (this.xPoint + (this.xScale * i)), (float) (getHeight() - (this.margin / 6)), paint);
            }
        }
        for (int i2 = 0; i2 <= this.yLabel.length - 1; i2++) {
            paint.setTextAlign(Align.LEFT);
            int startY = this.yPoint - (this.yScale * i2);
            switch (this.yLabel[i2].length()) {
                case 1:
                    offsetX = 28;
                    break;
                case 2:
                    offsetX = 20;
                    break;
                case 3:
                    offsetX = 12;
                    break;
                case 4:
                    offsetX = 5;
                    break;
                default:
                    offsetX = 0;
                    break;
            }
            if (i2 == 0) {
                offsetY = 0;
            } else {
                offsetY = this.margin / 5;
            }
            canvas.drawText(this.yLabel[i2], (float) ((this.margin / 4) + offsetX), (float) (startY + offsetY), paint);
        }
    }

    private void drawCurve(Canvas canvas, Paint paint, int[] data, int color) {
        Path path = new Path();
        for (int i = 0; i <= this.xLabel.length - 1; i++) {
            if (i == 0) {
            }
            if (i == this.xLabel.length - 1) {
            }
        }
        canvas.drawPath(path, paint);
    }

    @RequiresApi(api = 21)
    private void drawValue(Canvas canvas, Paint paint, int[] data, Bitmap bitmap) {
        for (int i = 0; i <= this.xLabel.length - 1; i++) {
            if (data[i] > 0) {
                canvas.drawBitmap(bitmap, null, new RectF((float) (this.xPoint + (this.xScale * i) + 10), toY(data[i]) - 25.0f, (float) (this.xPoint + (this.xScale * i) + 40), toY(data[i]) + 5.0f), null);
            }
        }
    }

    private float toY(int num) {
        try {
            return ((float) this.yPoint) - (((float) this.yScale) * (((float) num) / 50.0f));
        } catch (Exception e) {
            return 0.0f;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.touchX = event.getX() - 40.0f;
                this.touchY = event.getY();
                this.firstDown2Move = true;
                this.downX = this.touchX;
                this.downY = this.touchY;
                getParent().requestDisallowInterceptTouchEvent(true);
                this.userIntent = 0;
                invalidate();
                break;
            case 1:
                this.touchX = event.getX() - 40.0f;
                this.touchY = event.getY();
                break;
            case 2:
                if (this.firstDown2Move) {
                    float sizeY = event.getY() - this.downY;
                    float sizeX = event.getX() - this.downX;
                    if (Math.abs(sizeX) >= ((float) this.mTouchSlop) || Math.abs(sizeY) >= ((float) this.mTouchSlop)) {
                        if (Math.abs(sizeY) - Math.abs(sizeX) > 0.0f) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        this.firstDown2Move = false;
                    }
                }
                this.touchX = event.getX() - 40.0f;
                this.touchY = event.getY();
                if (this.userIntent == 0) {
                    invalidate();
                    break;
                }
                break;
            case 3:
                resetData();
                break;
        }
        return true;
    }

    private void resetData() {
        this.touchX = 0.0f;
        this.touchY = 0.0f;
        this.downY = 0.0f;
        this.downX = 0.0f;
        this.firstDown2Move = false;
        invalidate();
    }
}
