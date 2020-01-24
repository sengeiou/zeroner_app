package com.iwown.sport_module.ui.sleep.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.sport_module.R;
import com.socks.library.KLog;

public class HistoryChartView extends View {
    private int bottom_height;
    private int deep;
    private float deep_height;
    private int light;
    private float light_height;
    private float max;
    private float max_target;
    private Paint paint_deep;
    private Paint paint_light;
    private Paint paint_wake;
    private float real_height_ratio;
    private int wake;
    private float wake_height;

    public HistoryChartView(Context context) {
        this(context, null);
    }

    public HistoryChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.max_target = 780.0f;
        initView(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = 21)
    public HistoryChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.max_target = 780.0f;
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.paint_deep = new Paint();
        this.paint_deep.setColor(context.getResources().getColor(R.color.sleep_color_history_deep0));
        this.paint_deep.setStrokeWidth(20.0f);
        this.paint_deep.setAntiAlias(true);
        this.paint_light = new Paint();
        this.paint_light.setColor(context.getResources().getColor(R.color.sleep_color_history_light0));
        this.paint_light.setStrokeWidth(20.0f);
        this.paint_light.setAntiAlias(true);
        this.paint_wake = new Paint();
        this.paint_wake.setColor(context.getResources().getColor(R.color.sleep_color_history_wake0));
        this.paint_wake.setStrokeWidth(20.0f);
        this.paint_wake.setAntiAlias(true);
    }

    public void setColors(int[] colos) {
        this.paint_deep.setColor(getContext().getResources().getColor(colos[0]));
        this.paint_light.setColor(getContext().getResources().getColor(colos[1]));
        this.paint_wake.setColor(getContext().getResources().getColor(colos[2]));
    }

    public void setTypeValue(int deep2, int light2, int wake2) {
        this.max = (float) (deep2 + light2 + wake2);
        this.deep = deep2;
        this.light = light2;
        this.wake = wake2;
        postDelayed(new Runnable() {
            public void run() {
                HistoryChartView.this.invalidate();
            }
        }, 100);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.bottom_height = h;
        this.paint_deep.setStrokeWidth((float) w);
        this.paint_light.setStrokeWidth((float) w);
        this.paint_wake.setStrokeWidth((float) w);
    }

    private int getSize(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        return mode == 1073741824 ? height : Math.min(200, height);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        this.real_height_ratio = (1.0f * ((float) getHeight())) / this.max_target;
        this.deep_height = ((float) this.deep) * this.real_height_ratio;
        this.light_height = ((float) this.light) * this.real_height_ratio;
        this.wake_height = ((float) this.wake) * this.real_height_ratio;
        KLog.e(this.deep_height + "  " + this.light_height + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.wake_height);
        int deep_start_y = this.bottom_height;
        int light_start_y = (int) (((float) deep_start_y) - this.deep_height);
        canvas.drawLine((float) (width / 2), (float) deep_start_y, (float) (width / 2), (float) light_start_y, this.paint_deep);
        int wake_start_y = (int) (((float) light_start_y) - this.light_height);
        canvas.drawLine((float) (width / 2), (float) light_start_y, (float) (width / 2), (float) wake_start_y, this.paint_light);
        Canvas canvas2 = canvas;
        canvas2.drawLine((float) (width / 2), (float) wake_start_y, (float) (width / 2), (float) ((int) (((float) wake_start_y) - this.wake_height)), this.paint_wake);
    }
}
