package com.iwown.lib_common.views.weightview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.R;
import com.iwown.lib_common.network.utils.BaseUtils;
import com.socks.library.KLog;
import java.util.List;

public class WeightViewScrollView extends RelativeLayout {
    CallBack_Select_Data callBack_select_data;
    private WeightPointBean callback_centerWeightPointBean;
    private WeightPointBean centerWeightPointBean;
    private float downX;
    private double goal;
    private LineView goal_line_view;
    private int goal_weight_real_height;
    private int index_jq;
    private int initWeightCahrttLeft;
    private List<WeightShowData> jq_weights;
    private boolean left_limit;
    private int limit_size;
    private float max;
    private int max_hieght;
    private int max_width;
    private Paint paint_center_line;
    private Paint paint_weight_line;
    private Path path_center_line;
    private boolean right_limit;
    private boolean showCenterLine;
    private boolean showGoal;
    private int startOffset;
    private int start_index;
    private TextView text_margin_value;
    private TextView textview_goal;
    private String value_unit;
    private WeightChartView weightChartView;
    private List<WeightShowData> weightDatas;

    public interface CallBack_Select_Data {
        void select_data(WeightShowData weightShowData);
    }

    public void setCallBack_select_data(CallBack_Select_Data callBack_select_data2) {
        this.callBack_select_data = callBack_select_data2;
    }

    public void setShowGoal(boolean showGoal2) {
        this.showGoal = showGoal2;
    }

    public WeightViewScrollView(Context context) {
        this(context, null);
    }

    public WeightViewScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeightViewScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.max = 120.0f;
        this.goal = 48.0d;
        this.showGoal = true;
        this.limit_size = 300;
        initView(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = 21)
    public WeightViewScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.max = 120.0f;
        this.goal = 48.0d;
        this.showGoal = true;
        this.limit_size = 300;
        initView(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec));
    }

    public void setGoal(double goal2, String unit, float max2) {
        this.goal = goal2;
        this.max = 1.2f * max2;
        this.value_unit = unit;
        this.textview_goal.setText(getContext().getString(R.string.weight_goal) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + goal2 + unit);
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
            if (childAt instanceof LineView) {
                this.goal_line_view = (LineView) childAt;
                LayoutParams layoutParams = this.goal_line_view.getLayoutParams();
                this.goal_weight_real_height = this.weightChartView.getPaddingTop() + 15 + ((int) (((1.0d * (((double) this.max) - this.goal)) / ((double) this.max)) * ((double) this.weightChartView.getRealHegiht())));
                KLog.e("goal_weight_real_height " + this.goal_weight_real_height + "  " + this.weightChartView.getRealHegiht());
                this.goal_line_view.layout(0, this.goal_weight_real_height, childAt.getRight(), this.goal_weight_real_height + layoutParams.height);
            } else if ((childAt instanceof WeightChartView) && this.weightChartView == null) {
                this.weightChartView = (WeightChartView) childAt;
                this.weightChartView.setScreenCenterX(this.max_width / 2);
                updateLeftRealWeightLists();
                this.centerWeightPointBean = this.weightChartView.getCenterPointLine();
                invalidate();
            }
        }
        this.textview_goal.getLeft();
        this.textview_goal.layout(this.textview_goal.getLeft(), (this.goal_weight_real_height - this.textview_goal.getHeight()) - 15, this.textview_goal.getRight(), this.goal_weight_real_height - 15);
        showMarginTextView();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.max_width = w;
        this.max_hieght = h;
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        WeightChartView weightChartView2 = new WeightChartView(context);
        RelativeLayout.LayoutParams layoutParams_weight_cahrt = new RelativeLayout.LayoutParams(-1, -1);
        int marginSize = DensityUtil.dip2px(context, 35.0f);
        int marginSize2 = DensityUtil.dip2px(context, 55.0f);
        layoutParams_weight_cahrt.setMargins(15, 15, 15, 15);
        layoutParams_weight_cahrt.addRule(13);
        weightChartView2.setLayoutParams(layoutParams_weight_cahrt);
        weightChartView2.setPadding(15, marginSize, 15, marginSize2);
        addView(weightChartView2);
        TextView textview_goal_bg = new TextView(context);
        RelativeLayout.LayoutParams layoutParams_text_view_bg = new RelativeLayout.LayoutParams(DensityUtil.dip2px(getContext(), 100.0f), -1);
        textview_goal_bg.setBackgroundResource(R.drawable.lib_common_weight_bg_gradient);
        layoutParams_text_view_bg.addRule(11);
        textview_goal_bg.setPadding(0, 0, 15, 0);
        textview_goal_bg.setLayoutParams(layoutParams_text_view_bg);
        addView(textview_goal_bg);
        this.textview_goal = new TextView(context);
        RelativeLayout.LayoutParams layoutParams_text_view = new RelativeLayout.LayoutParams(DensityUtil.dip2px(getContext(), 100.0f), -2);
        this.textview_goal.setTextSize(15.0f);
        layoutParams_text_view.addRule(11);
        this.textview_goal.setTextColor(Color.parseColor("#A9DC9B"));
        this.textview_goal.setGravity(17);
        this.textview_goal.setLayoutParams(layoutParams_text_view);
        addView(this.textview_goal);
        this.text_margin_value = new TextView(context);
        RelativeLayout.LayoutParams layoutParams_text_margin_value = new RelativeLayout.LayoutParams(-2, -2);
        this.text_margin_value.setTextSize(14.0f);
        this.text_margin_value.setBackgroundResource(R.drawable.bg_stroke_white_coners_5);
        this.text_margin_value.setTextColor(Color.parseColor("#0E8701"));
        this.text_margin_value.setGravity(17);
        this.text_margin_value.setLayoutParams(layoutParams_text_margin_value);
        addView(this.text_margin_value);
        LineView goal_line = new LineView(context);
        goal_line.setLayoutParams(new RelativeLayout.LayoutParams(-1, 5));
        goal_line.setBackgroundColor(Color.parseColor("#A9DC9B"));
        goal_line.setVisibility(8);
        addView(goal_line);
        this.paint_center_line = new Paint();
        this.paint_center_line.setAntiAlias(true);
        this.paint_center_line.setColor(-1);
        this.paint_center_line.setStyle(Style.STROKE);
        this.paint_center_line.setPathEffect(new DashPathEffect(new float[]{5.0f, 10.0f}, 0.0f));
        this.paint_center_line.setStrokeWidth(5.0f);
        this.path_center_line = new Path();
        this.paint_weight_line = new Paint();
        this.paint_weight_line.setAntiAlias(true);
        this.paint_weight_line.setColor(-1);
        this.paint_weight_line.setStyle(Style.STROKE);
        this.paint_weight_line.setStrokeWidth(10.0f);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.showCenterLine) {
            canvas.drawLine((float) (this.max_width / 2), (float) this.goal_weight_real_height, (float) (this.max_width / 2), 0.0f, this.paint_weight_line);
        }
        if (this.centerWeightPointBean != null && this.showGoal) {
            this.path_center_line.reset();
            this.path_center_line.moveTo(this.centerWeightPointBean.screenX, (float) this.goal_weight_real_height);
            this.path_center_line.lineTo(this.centerWeightPointBean.screenX, this.centerWeightPointBean.screenY);
            canvas.drawPath(this.path_center_line, this.paint_center_line);
        }
    }

    private void showMarginTextView() {
        String value;
        if (this.centerWeightPointBean == null || !this.showGoal) {
            this.text_margin_value.setVisibility(8);
        } else {
            this.text_margin_value.setVisibility(0);
            double i = BaseUtils.getDouble1((float) (((double) this.centerWeightPointBean.real_weight) - this.goal));
            String str = "";
            if (i > Utils.DOUBLE_EPSILON) {
                value = "+" + i + this.value_unit;
            } else {
                value = i + this.value_unit;
            }
            int left = (int) (this.centerWeightPointBean.screenX + 10.0f);
            int top = (int) (this.centerWeightPointBean.screenY + ((((float) this.goal_weight_real_height) - this.centerWeightPointBean.screenY) / 2.0f));
            this.text_margin_value.setText(value);
            int textsize = (int) (((double) (((float) value.length()) * this.text_margin_value.getTextSize())) / 1.5d);
            double height = ((double) this.text_margin_value.getTextSize()) * 1.4d;
            int bottom = (int) (((double) top) + height);
            if (((double) this.centerWeightPointBean.real_weight) >= this.goal) {
                if (bottom > this.goal_weight_real_height) {
                    bottom = this.goal_weight_real_height - 10;
                }
            } else if (((double) this.centerWeightPointBean.real_weight) < this.goal && bottom < this.goal_weight_real_height) {
                bottom = this.goal_weight_real_height + 10;
            }
            this.text_margin_value.layout(left, (int) (((double) bottom) - height), left + textsize, bottom);
        }
        if (this.callback_centerWeightPointBean != this.centerWeightPointBean) {
            this.callback_centerWeightPointBean = this.centerWeightPointBean;
            if (this.callBack_select_data != null && this.centerWeightPointBean != null) {
                this.callBack_select_data.select_data(this.centerWeightPointBean.weightData);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.weightDatas == null) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                getParent().requestDisallowInterceptTouchEvent(true);
                this.downX = event.getX();
                this.initWeightCahrttLeft = this.weightChartView.getData_begin_left();
                this.centerWeightPointBean = null;
                invalidate();
                break;
            case 1:
                this.centerWeightPointBean = this.weightChartView.getCenterPointLine();
                if (this.weightChartView.getShow_index() == 0) {
                    this.left_limit = true;
                }
                if (this.left_limit) {
                    updateLeftRealWeightLists();
                } else if (this.right_limit) {
                    updateRightRealWeightLists();
                } else {
                    float x = event.getX();
                    this.startOffset = 0;
                    moveSelectPoint2Center();
                    invalidate();
                }
                showMarginTextView();
                this.weightChartView.invalidate();
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
        this.left_limit = false;
        this.right_limit = false;
        int left = this.initWeightCahrttLeft + this.startOffset;
        if (left >= this.max_width / 2) {
            this.left_limit = true;
            return false;
        } else if (((float) this.startOffset) + this.weightChartView.getRightLastSscreenX() <= ((float) (this.max_width / 2))) {
            this.right_limit = true;
            return false;
        } else {
            this.weightChartView.layout1(left);
            return true;
        }
    }

    private void moveLastRight() {
        this.weightChartView.layout1((this.max_width / 2) - ((int) this.weightChartView.getRightLastSscreenX()));
        this.centerWeightPointBean = this.weightChartView.getCenterPointLine();
        invalidate();
    }

    private void updateLeftRealWeightLists() {
        if (this.start_index == 0 && this.index_jq <= this.limit_size && this.index_jq != 0) {
            KLog.e("最左边了");
            moveSelectPoint2Center();
        } else if (this.weightDatas != null) {
            this.index_jq = this.start_index + 1;
            this.start_index = this.index_jq - this.limit_size;
            if (this.start_index < 0) {
                this.start_index = 0;
            }
            try {
                KLog.d("左边截取后 显示数据 " + this.start_index + "  " + this.index_jq);
                this.jq_weights = this.weightDatas.subList(this.start_index, this.index_jq);
                this.weightChartView.updateLeft(0);
                this.weightChartView.setDatas(this.jq_weights, this.max);
                moveLastRight();
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    private void updateRightRealWeightLists() {
        if (this.start_index < this.weightDatas.size() - this.limit_size || this.index_jq > this.weightDatas.size()) {
            this.start_index = this.index_jq - 1;
            this.index_jq = this.start_index + this.limit_size;
            if (this.index_jq > this.weightDatas.size()) {
                this.index_jq = this.weightDatas.size();
            }
            KLog.e("右边截取后 显示数据 " + this.start_index + "  " + this.index_jq);
            try {
                this.jq_weights = this.weightDatas.subList(this.start_index, this.index_jq);
                this.weightChartView.updateLeft(0);
                this.weightChartView.setDatas(this.jq_weights, this.max);
                moveLastLeft();
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        } else {
            KLog.e("最右边边了");
            moveSelectPoint2Center();
        }
    }

    private void moveLastLeft() {
        int left_margin = (this.max_width / 2) - ((int) this.weightChartView.getLeftFirstSscreenX());
        KLog.e(" initWeightCahrttLeft " + this.initWeightCahrttLeft + "  lastRX " + this.weightChartView.getRightLastSscreenX() + " left_margin " + left_margin);
        this.weightChartView.layout1(left_margin);
        this.centerWeightPointBean = this.weightChartView.getCenterPointLine();
        invalidate();
    }

    private void moveSelectPoint2Center() {
        this.initWeightCahrttLeft = this.weightChartView.getData_begin_left();
        this.centerWeightPointBean = this.weightChartView.getCenterPointLine();
        if (this.centerWeightPointBean != null) {
            this.weightChartView.layout1(((int) (((float) (this.max_width / 2)) - this.centerWeightPointBean.screenX)) + this.initWeightCahrttLeft);
        }
        this.centerWeightPointBean = this.weightChartView.getCenterPointLine();
    }

    public void setDatas(List<WeightShowData> weightDatas1) {
        if (this.weightDatas != null) {
            this.weightDatas = null;
        }
        this.start_index = weightDatas1.size() - 1;
        this.index_jq = 0;
        KLog.d("weightChartView " + this.weightChartView);
        this.weightDatas = weightDatas1;
        if (weightDatas1 == null || weightDatas1.size() == 0) {
            this.goal_line_view.setVisibility(8);
        } else {
            this.goal_line_view.setVisibility(0);
        }
        if (this.weightChartView != null) {
            if (this.textview_goal != null) {
                if (!this.showGoal) {
                    this.textview_goal.setVisibility(8);
                    this.goal_line_view.setVisibility(8);
                } else {
                    this.textview_goal.setVisibility(0);
                    this.goal_line_view.setVisibility(0);
                }
            }
            updateLeftRealWeightLists();
            showMarginTextView();
        }
    }
}
