package com.iwown.lib_common.views.fatigueview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.socks.library.KLog;
import java.util.List;

public class FatigueScrollView extends RelativeLayout {
    private float downX;
    private FatigueChartView fatigueChartView;
    private List<FatigueDataBean> fatigueDataBeans;
    private int initWeightCahrttLeft;
    private int max_hieght;
    private int max_width;
    private Paint paint_center_line;
    private Paint paint_weight_line;
    private Path path_center_line;
    private boolean showCenterLine;
    private int startOffset;

    public FatigueScrollView(Context context) {
        this(context, null);
    }

    public FatigueScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FatigueScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = 21)
    public FatigueScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((childAt instanceof FatigueChartView) && this.fatigueChartView == null) {
                this.fatigueChartView = (FatigueChartView) childAt;
                this.fatigueChartView.setScreenCenterX(this.max_width / 2, this.fatigueDataBeans);
                this.fatigueChartView.layout1((this.max_width / 2) - ((int) (((float) childAt.getLeft()) + ((FatigueChartView) childAt).getRightLastSscreenX())));
                this.fatigueChartView.showCenterPointLine();
                invalidate();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        KLog.e("onSizeChanged " + w + "  " + h);
        this.max_width = w;
        this.max_hieght = h;
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        FatigueChartView fatigueChartView2 = new FatigueChartView(context);
        LayoutParams layoutParams_weight_cahrt = new LayoutParams(-1, -1);
        layoutParams_weight_cahrt.setMargins(20, 20, 20, 20);
        layoutParams_weight_cahrt.addRule(13);
        fatigueChartView2.setLayoutParams(layoutParams_weight_cahrt);
        fatigueChartView2.setPadding(15, 15, 15, 15);
        fatigueChartView2.setBackgroundColor(Color.parseColor("#057B00"));
        addView(fatigueChartView2);
        this.paint_center_line = new Paint();
        this.paint_center_line.setAntiAlias(true);
        this.paint_center_line.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.paint_center_line.setStyle(Style.STROKE);
        this.paint_center_line.setPathEffect(new DashPathEffect(new float[]{5.0f, 10.0f}, 0.0f));
        this.paint_center_line.setStrokeWidth(5.0f);
        this.path_center_line = new Path();
        this.paint_weight_line = new Paint();
        this.paint_weight_line.setAntiAlias(true);
        this.paint_weight_line.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.paint_weight_line.setStyle(Style.STROKE);
        this.paint_weight_line.setStrokeWidth(10.0f);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                getParent().requestDisallowInterceptTouchEvent(true);
                this.downX = event.getX();
                this.initWeightCahrttLeft = this.fatigueChartView.getData_begin_left();
                invalidate();
                showCenterLine(true);
                break;
            case 1:
                showCenterLine(false);
                this.startOffset = 0;
                this.initWeightCahrttLeft = this.fatigueChartView.getData_begin_left();
                FatigueDataBean centerPointData = this.fatigueChartView.getCenterPointData();
                if (centerPointData != null) {
                    this.fatigueChartView.layout1(((this.max_width / 2) - centerPointData.topPoint.screenX) + this.initWeightCahrttLeft);
                }
                this.fatigueChartView.showCenterPointLine();
                invalidate();
                break;
            case 2:
                this.startOffset = (int) (event.getX() - this.downX);
                if (!moveWeigtChart()) {
                    return false;
                }
                break;
            case 3:
                event.getX();
                break;
        }
        return true;
    }

    private void showCenterLine(boolean showCenterLine2) {
        this.showCenterLine = showCenterLine2;
        invalidate();
    }

    private boolean moveWeigtChart() {
        int left = this.initWeightCahrttLeft + this.startOffset;
        if (left >= this.max_width / 2 || ((float) left) + this.fatigueChartView.getRightLastSscreenX() <= ((float) (this.max_width / 2))) {
            return false;
        }
        this.fatigueChartView.layout1(left);
        return true;
    }

    public void setDatas(List<FatigueDataBean> fatigueDataBeans2) {
        if (this.fatigueDataBeans != null) {
            this.fatigueDataBeans = null;
        }
        KLog.e("weightChartView " + this.fatigueChartView);
        if (this.fatigueChartView != null) {
            this.fatigueChartView.setDatas(fatigueDataBeans2);
        } else {
            this.fatigueDataBeans = fatigueDataBeans2;
        }
    }
}
