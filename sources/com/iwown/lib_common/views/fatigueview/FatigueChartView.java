package com.iwown.lib_common.views.fatigueview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.views.weightview.XYPoint;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class FatigueChartView extends View {
    private FatigueDataBean centerDataBean;
    private float circle_radius;
    private int data_begin_left;
    private List<FatigueDataBean> fatigueDataBeans;
    private Paint paint_limit_line;
    private Paint paint_point;
    private Paint paint_pointHighlight;
    private Paint paint_top_bottom_line;
    private Paint paint_xy;
    private Path path_connect_line;
    private int screenCenterX;
    private List<XYPoint> yPointList;

    public FatigueChartView(Context context) {
        this(context, null);
    }

    public FatigueChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FatigueChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.yPointList = new ArrayList();
        initView(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec));
    }

    private int getSize(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        return mode == 1073741824 ? height : Math.min(200, height);
    }

    @RequiresApi(api = 21)
    public FatigueChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.yPointList = new ArrayList();
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.circle_radius = (float) DensityUtil.dip2px(getContext(), 5.0f);
        this.paint_point = new Paint();
        this.paint_point.setAntiAlias(true);
        this.paint_point.setStyle(Style.FILL);
        this.paint_point.setColor(Color.parseColor("#A9DC9B"));
        this.paint_pointHighlight = new Paint();
        this.paint_pointHighlight.setAntiAlias(true);
        this.paint_pointHighlight.setStyle(Style.FILL);
        this.paint_pointHighlight.setColor(-1);
        this.paint_top_bottom_line = new Paint();
        this.paint_top_bottom_line.setAntiAlias(true);
        this.paint_top_bottom_line.setStrokeWidth(this.circle_radius * 2.0f);
        this.paint_top_bottom_line.setStyle(Style.STROKE);
        this.paint_top_bottom_line.setColor(Color.parseColor("#53BA37"));
        this.paint_limit_line = new Paint();
        this.paint_limit_line.setAntiAlias(true);
        this.paint_limit_line.setStrokeWidth(5.0f);
        this.paint_limit_line.setStyle(Style.STROKE);
        this.paint_limit_line.setColor(Color.parseColor("#53BA37"));
        this.path_connect_line = new Path();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        KLog.e("--- " + getLeft());
        this.data_begin_left = getLeft();
        creatYTitles();
    }

    private void creatYTitles() {
        this.yPointList.clear();
        int realHeight = getRealHeight();
        for (int i = 0; i < 24; i++) {
            String text = i + "";
            float y = ((((float) i) * 1.0f) / 24.0f) * ((float) realHeight);
            if (i % 4 == 0) {
                this.yPointList.add(new XYPoint((float) 0, y, text));
            }
        }
        invalidate();
    }

    public int getRealWidth() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public int getRealHeight() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.fatigueDataBeans != null) {
            int left_size = getPaddingLeft() + getData_begin_left();
            this.path_connect_line.reset();
            int index = 0;
            for (FatigueDataBean fatiguePointBean : this.fatigueDataBeans) {
                canvas.drawLine((float) (fatiguePointBean.topPoint.x + left_size), fatiguePointBean.topPoint.y, (float) (fatiguePointBean.bottomPoint.x + left_size), fatiguePointBean.bottomPoint.y, this.paint_top_bottom_line);
                canvas.drawCircle((float) (fatiguePointBean.topPoint.x + left_size), fatiguePointBean.topPoint.y, this.circle_radius, this.paint_point);
                canvas.drawCircle((float) (fatiguePointBean.bottomPoint.x + left_size), fatiguePointBean.bottomPoint.y, this.circle_radius, this.paint_point);
                canvas.drawCircle((float) (fatiguePointBean.centerPoint.x + left_size), fatiguePointBean.centerPoint.y, this.circle_radius, this.paint_point);
                if (index == 0) {
                    this.path_connect_line.moveTo((float) (fatiguePointBean.centerPoint.x + left_size), fatiguePointBean.centerPoint.y);
                } else {
                    this.path_connect_line.lineTo((float) (fatiguePointBean.centerPoint.x + left_size), fatiguePointBean.centerPoint.y);
                }
                index++;
            }
            canvas.drawPath(this.path_connect_line, this.paint_limit_line);
            if (this.centerDataBean != null) {
                canvas.drawCircle((float) (this.centerDataBean.centerPoint.x + left_size), this.centerDataBean.centerPoint.y, this.circle_radius, this.paint_pointHighlight);
            }
        }
    }

    public void setScreenCenterX(int screenCenterX2, List<FatigueDataBean> fatigueDataBeans2) {
        this.screenCenterX = screenCenterX2;
        setDatas(fatigueDataBeans2);
    }

    public float getRightLastSscreenX() {
        return (float) ((FatigueDataBean) this.fatigueDataBeans.get(this.fatigueDataBeans.size() - 1)).centerPoint.screenX;
    }

    public int getData_begin_left() {
        return this.data_begin_left;
    }

    public void layout1(int left) {
        this.data_begin_left = left;
        invalidate();
    }

    public void setDatas(List<FatigueDataBean> fatigueDataBeans2) {
        this.fatigueDataBeans = fatigueDataBeans2;
        showUIData();
    }

    private void showUIData() {
        int block_size = getRealWidth() / 7;
        int index = 0;
        for (FatigueDataBean fatigueDataBean : this.fatigueDataBeans) {
            fatigueDataBean.calPointData(getRealHeight(), index * block_size, getLeft() + getData_begin_left() + getPaddingLeft());
            index++;
        }
        showCenterPointLine();
        invalidate();
    }

    public void showCenterPointLine() {
        float min_size = 1000.0f;
        int show_index = -1;
        int i = 0;
        for (FatigueDataBean fatigueDataBean : this.fatigueDataBeans) {
            fatigueDataBean.topPoint.screenX = getData_begin_left() + getPaddingLeft() + fatigueDataBean.topPoint.x;
            float min_size1 = (float) Math.abs(fatigueDataBean.topPoint.screenX - this.screenCenterX);
            if (min_size1 < min_size) {
                min_size = min_size1;
                show_index = i;
            }
            i++;
        }
        if (show_index >= 0) {
            this.centerDataBean = (FatigueDataBean) this.fatigueDataBeans.get(show_index);
            invalidate();
        }
    }

    public FatigueDataBean getCenterPointData() {
        float min_size = 1000.0f;
        int show_index = -1;
        int i = 0;
        for (FatigueDataBean fatigueDataBean : this.fatigueDataBeans) {
            fatigueDataBean.topPoint.screenX = getData_begin_left() + getPaddingLeft() + fatigueDataBean.topPoint.x;
            float min_size1 = (float) Math.abs(fatigueDataBean.topPoint.screenX - this.screenCenterX);
            if (min_size1 < min_size) {
                min_size = min_size1;
                show_index = i;
            }
            i++;
        }
        if (show_index < 0) {
            return null;
        }
        return (FatigueDataBean) this.fatigueDataBeans.get(show_index);
    }
}
