package com.iwown.lib_common.views.weightview;

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
import java.util.ArrayList;
import java.util.List;

public class WeightChartView extends View {
    private int center_point_color;
    private int data_begin_left;
    private Paint paint_weight_cricle;
    private Paint paint_weight_line;
    private Paint paint_weight_x_text;
    private Path path_weight_line;
    private int point_color_unselect;
    private List<WeightShowData> real_weights;
    private int screenCenterX;
    private boolean showXText;
    private int show_index;
    private int weight_point_r_select;
    private int weight_point_r_unselect;
    private List<WeightPointBean> weights;

    public WeightChartView(Context context) {
        this(context, null);
    }

    public WeightChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeightChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.show_index = -1;
        this.weight_point_r_unselect = 10;
        this.weight_point_r_select = 10;
        this.point_color_unselect = Color.parseColor("#A9DC9B");
        this.center_point_color = -1;
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
    public WeightChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.show_index = -1;
        this.weight_point_r_unselect = 10;
        this.weight_point_r_select = 10;
        this.point_color_unselect = Color.parseColor("#A9DC9B");
        this.center_point_color = -1;
        initView(context, attrs, defStyleAttr);
    }

    public void setShowXText(boolean showXText2) {
        this.showXText = showXText2;
    }

    public void setCenterPointColor(int color) {
        this.center_point_color = color;
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.path_weight_line = new Path();
        this.paint_weight_line = new Paint();
        this.paint_weight_cricle = new Paint();
        this.paint_weight_line.setAntiAlias(true);
        this.paint_weight_line.setColor(-1);
        this.paint_weight_line.setStyle(Style.STROKE);
        this.paint_weight_line.setStrokeWidth(5.0f);
        this.paint_weight_cricle.setAntiAlias(true);
        this.paint_weight_cricle.setColor(this.center_point_color);
        this.paint_weight_cricle.setStyle(Style.FILL);
        this.weights = new ArrayList();
        setLayerType(1, null);
        this.weight_point_r_select = DensityUtil.dip2px(getContext(), 5.0f);
        this.weight_point_r_unselect = DensityUtil.dip2px(getContext(), 4.0f);
        this.paint_weight_x_text = new Paint();
        this.paint_weight_x_text.setAntiAlias(true);
        this.paint_weight_x_text.setColor(Color.parseColor("#081024"));
        this.paint_weight_x_text.setStyle(Style.STROKE);
        this.paint_weight_x_text.setTextSize((float) DensityUtil.dip2px(getContext(), 10.0f));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public int getRealWidth() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public int getRealHegiht() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    private void showDataUI(float max) {
        if (this.real_weights != null && getHeight() != 0) {
            this.weights.clear();
            int one_size = getRealWidth() / 7;
            int index = 0;
            for (WeightShowData weight : this.real_weights) {
                WeightPointBean weightPointBean = new WeightPointBean((float) (one_size * index), (1.0f - ((weight.real_weight * 1.0f) / max)) * ((float) ((getHeight() - getPaddingTop()) - getPaddingBottom())), weight.real_weight, weight.time);
                weightPointBean.weightData = weight;
                this.weights.add(weightPointBean);
                index++;
            }
            getCenterPointLine();
            invalidate();
        }
    }

    public void setDatas(List<WeightShowData> real_weights1, float max) {
        this.real_weights = real_weights1;
        showDataUI(max);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.path_weight_line.reset();
        for (int i = 0; i < this.weights.size(); i++) {
            if (this.show_index == i) {
                this.paint_weight_cricle.setColor(-1);
                canvas.drawCircle(((float) (getData_begin_left() + getPaddingLeft())) + ((WeightPointBean) this.weights.get(i)).x, ((WeightPointBean) this.weights.get(i)).y + ((float) getPaddingTop()), (float) this.weight_point_r_select, this.paint_weight_cricle);
            } else {
                this.paint_weight_cricle.setColor(this.point_color_unselect);
                canvas.drawCircle(((float) (getData_begin_left() + getPaddingLeft())) + ((WeightPointBean) this.weights.get(i)).x, ((WeightPointBean) this.weights.get(i)).y + ((float) getPaddingTop()), (float) this.weight_point_r_unselect, this.paint_weight_cricle);
            }
            if (i == 0) {
                this.path_weight_line.moveTo(((float) (getData_begin_left() + getPaddingLeft())) + ((WeightPointBean) this.weights.get(i)).x, ((WeightPointBean) this.weights.get(i)).y + ((float) getPaddingTop()));
            } else {
                this.path_weight_line.lineTo(((float) (getData_begin_left() + getPaddingLeft())) + ((WeightPointBean) this.weights.get(i)).x, ((WeightPointBean) this.weights.get(i)).y + ((float) getPaddingTop()));
                if (this.showXText && this.paint_weight_x_text != null) {
                    canvas.drawText(((WeightPointBean) this.weights.get(i)).showTime, ((WeightPointBean) this.weights.get(i)).x + ((float) (getData_begin_left() + getPaddingLeft())), ((float) getHeight()) - this.paint_weight_x_text.getTextSize(), this.paint_weight_x_text);
                }
            }
        }
        canvas.drawPath(this.path_weight_line, this.paint_weight_line);
    }

    public void setScreenCenterX(int screenCenterX2) {
        this.screenCenterX = screenCenterX2;
    }

    public WeightPointBean getCenterPointLine() {
        float min_size = 1000.0f;
        this.show_index = -1;
        int i = 0;
        for (WeightPointBean weightPointBean : this.weights) {
            weightPointBean.screenX = ((float) (getLeft() + getData_begin_left() + getPaddingLeft())) + weightPointBean.x;
            weightPointBean.screenY = ((float) (getTop() + getPaddingTop())) + weightPointBean.y;
            float min_size1 = Math.abs(weightPointBean.screenX - ((float) this.screenCenterX));
            if (min_size1 < min_size) {
                min_size = min_size1;
                this.show_index = i;
            }
            i++;
        }
        if (this.show_index < 0) {
            return null;
        }
        return (WeightPointBean) this.weights.get(this.show_index);
    }

    public float getLeftFirstSscreenX() {
        if (this.weights.size() == 0) {
            return 0.0f;
        }
        return ((WeightPointBean) this.weights.get(0)).screenX;
    }

    public float getRightLastSscreenX() {
        if (this.weights.size() == 0) {
            return 0.0f;
        }
        return ((WeightPointBean) this.weights.get(this.weights.size() - 1)).screenX;
    }

    public void updateLeft(int left) {
        this.data_begin_left = left;
    }

    public void layout1(int left) {
        this.data_begin_left = left;
        invalidate();
    }

    public int getData_begin_left() {
        return this.data_begin_left;
    }

    public int getShow_index() {
        return this.show_index;
    }
}
