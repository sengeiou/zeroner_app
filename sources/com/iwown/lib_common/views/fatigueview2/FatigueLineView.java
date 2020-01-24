package com.iwown.lib_common.views.fatigueview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.lib_common.DensityUtil;

public class FatigueLineView extends View {
    private int circle_radius;
    private int h;
    private boolean heightLigt;
    private float left_limit_value;
    private int max_value;
    private Paint paint_limit_line;
    private Paint paint_point;
    private Paint paint_pointHighlight;
    private Paint paint_top_bottom_line;
    private float real_l__height;
    private float real_m_height;
    private float real_m_value;
    private float real_max_height;
    private int real_max_value;
    private float real_min_height;
    private int real_min_value;
    private float real_r_height;
    private float right_limit_value;
    private int w;

    public FatigueLineView(Context context) {
        this(context, null);
    }

    public FatigueLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FatigueLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.max_value = 105;
        this.circle_radius = 15;
        initView(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = 21)
    public FatigueLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.max_value = 105;
        this.circle_radius = 15;
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

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.circle_radius = DensityUtil.dip2px(getContext(), 5.0f);
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
        this.paint_top_bottom_line.setStrokeWidth((float) (this.circle_radius * 2));
        this.paint_top_bottom_line.setStyle(Style.STROKE);
        this.paint_top_bottom_line.setColor(Color.parseColor("#53BA37"));
        this.paint_limit_line = new Paint();
        this.paint_limit_line.setAntiAlias(true);
        this.paint_limit_line.setStrokeWidth(5.0f);
        this.paint_limit_line.setStyle(Style.STROKE);
        this.paint_limit_line.setColor(Color.parseColor("#53BA37"));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        this.w = w2;
        this.h = h2;
    }

    private int getRealHeight() {
        return getHeight() - this.circle_radius;
    }

    private int getRealWidth() {
        return getWidth();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.real_min_value != -1 || this.real_max_value != -1) {
            if (this.real_min_value == this.real_max_value) {
                this.real_min_height = (1.0f - ((((float) this.real_min_value) * 1.0f) / ((float) this.max_value))) * ((float) getRealHeight());
                this.real_max_height = (1.0f - ((((float) this.real_max_value) * 1.0f) / ((float) this.max_value))) * ((float) getRealHeight());
            } else {
                this.real_min_height = ((1.0f - ((((float) this.real_min_value) * 1.0f) / ((float) this.max_value))) * ((float) getRealHeight())) - ((float) this.circle_radius);
                this.real_max_height = ((1.0f - ((((float) this.real_max_value) * 1.0f) / ((float) this.max_value))) * ((float) getRealHeight())) + ((float) this.circle_radius);
            }
            this.real_m_height = (1.0f - ((this.real_m_value * 1.0f) / ((float) this.max_value))) * ((float) getRealHeight());
            if (this.left_limit_value != -1.0f) {
                this.real_l__height = (1.0f - ((this.left_limit_value * 1.0f) / ((float) this.max_value))) * ((float) getRealHeight());
                canvas.drawLine((float) (getRealWidth() / 2), this.real_m_height, 0.0f, this.real_l__height, this.paint_limit_line);
            }
            if (this.right_limit_value != -1.0f) {
                this.real_r_height = (1.0f - ((this.right_limit_value * 1.0f) / ((float) this.max_value))) * ((float) getRealHeight());
                canvas.drawLine((float) (getRealWidth() / 2), this.real_m_height, (float) getRealWidth(), this.real_r_height, this.paint_limit_line);
            }
            canvas.drawLine((float) (getRealWidth() / 2), this.real_min_height, (float) (getRealWidth() / 2), this.real_max_height, this.paint_top_bottom_line);
            canvas.drawCircle((float) (getRealWidth() / 2), this.real_min_height, (float) this.circle_radius, this.paint_point);
            canvas.drawCircle((float) (getRealWidth() / 2), this.real_max_height, (float) this.circle_radius, this.paint_point);
            if (this.heightLigt) {
                canvas.drawCircle((float) (getRealWidth() / 2), this.real_m_height, (float) this.circle_radius, this.paint_pointHighlight);
            } else {
                canvas.drawCircle((float) (getRealWidth() / 2), this.real_m_height, (float) this.circle_radius, this.paint_point);
            }
        }
    }

    public void setFatigueDataBean(FatigueDataBean2 fatigueDataBean2) {
        this.heightLigt = fatigueDataBean2.hightLight;
        this.real_max_value = fatigueDataBean2.max_value;
        this.real_min_value = fatigueDataBean2.min_value;
        this.real_m_value = ((float) (fatigueDataBean2.min_value + fatigueDataBean2.max_value)) / 2.0f;
        this.left_limit_value = fatigueDataBean2.getLeftLimitValue();
        this.right_limit_value = fatigueDataBean2.getRightLimitValue();
        postDelayed(new Runnable() {
            public void run() {
                FatigueLineView.this.invalidate();
            }
        }, 100);
    }
}
