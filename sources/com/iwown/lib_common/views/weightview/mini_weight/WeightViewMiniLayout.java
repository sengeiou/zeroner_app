package com.iwown.lib_common.views.weightview.mini_weight;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.R;
import com.iwown.lib_common.views.weightview.LineDashView;
import com.iwown.lib_common.views.weightview.LineView;
import com.iwown.lib_common.views.weightview.WeightShowData;
import com.socks.library.KLog;
import java.util.List;

public class WeightViewMiniLayout extends RelativeLayout {
    private double goal;
    private LineView goal_line_view;
    private int goal_weight_real_height;
    private LineDashView lineDashView;
    private float max;
    private int max_hieght;
    private int max_width;
    private TextView textview_goal;
    private WeightChartMiniView weightChartView;
    private List<WeightShowData> weightDatas;

    public WeightViewMiniLayout(Context context) {
        this(context, null);
    }

    public WeightViewMiniLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeightViewMiniLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.goal = 48.0d;
        this.max = 120.0f;
        initView(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = 21)
    public WeightViewMiniLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.goal = 48.0d;
        this.max = 120.0f;
        initView(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec));
    }

    public void setGoal(double goal2, float max2, String unit) {
        this.goal = goal2;
        KLog.e("  " + goal2 + "  " + max2);
        this.max = 1.2f * max2;
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
            if (childAt instanceof LineDashView) {
                this.lineDashView = (LineDashView) childAt;
                int goal_weight_real_height2 = this.weightChartView.getPaddingTop() + this.weightChartView.getRealHegiht();
                this.lineDashView.layout(0, goal_weight_real_height2, childAt.getRight(), this.lineDashView.getLayoutParams().height + goal_weight_real_height2);
            } else if (childAt instanceof LineView) {
                this.goal_line_view = (LineView) childAt;
                KLog.e("LineView " + this.max);
                LayoutParams layoutParams = this.goal_line_view.getLayoutParams();
                this.goal_weight_real_height = this.weightChartView.getPaddingTop() + 15 + ((int) (((1.0d * (((double) this.max) - this.goal)) / ((double) this.max)) * ((double) this.weightChartView.getRealHegiht())));
                this.goal_line_view.layout(0, this.goal_weight_real_height, childAt.getRight(), this.goal_weight_real_height + layoutParams.height);
            } else if ((childAt instanceof WeightChartMiniView) && this.weightChartView == null) {
                this.weightChartView = (WeightChartMiniView) childAt;
                this.weightChartView.setDatas(this.weightDatas, this.max);
            }
        }
        this.textview_goal.getLeft();
        this.textview_goal.layout(this.textview_goal.getLeft(), (this.goal_weight_real_height - this.textview_goal.getHeight()) - 15, this.textview_goal.getRight(), this.goal_weight_real_height - 15);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        KLog.e("onSizeChanged " + w + "  " + h);
        this.max_width = w;
        this.max_hieght = h;
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        WeightChartMiniView weightChartView2 = new WeightChartMiniView(context);
        RelativeLayout.LayoutParams layoutParams_weight_cahrt = new RelativeLayout.LayoutParams(-1, -1);
        int paddingSize = DensityUtil.dip2px(context, 15.0f);
        layoutParams_weight_cahrt.setMargins(15, 15, 15, 15);
        layoutParams_weight_cahrt.addRule(13);
        weightChartView2.setLayoutParams(layoutParams_weight_cahrt);
        weightChartView2.setPadding(paddingSize, paddingSize, paddingSize, paddingSize);
        weightChartView2.setShowXText(true);
        weightChartView2.setCenterPointColor(Color.parseColor("#26A505"));
        addView(weightChartView2);
        this.textview_goal = new TextView(context);
        RelativeLayout.LayoutParams layoutParams_text_view = new RelativeLayout.LayoutParams(DensityUtil.dip2px(getContext(), 65.0f), -2);
        this.textview_goal.setTextSize(12.0f);
        layoutParams_text_view.addRule(11);
        this.textview_goal.setTextColor(Color.parseColor("#A9DC9B"));
        this.textview_goal.setGravity(17);
        this.textview_goal.setLayoutParams(layoutParams_text_view);
        addView(this.textview_goal);
        LineView goal_line = new LineView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, 5);
        goal_line.setBackgroundColor(Color.parseColor("#A9DC9B"));
        goal_line.setLayoutParams(layoutParams);
        addView(goal_line);
        this.lineDashView = new LineDashView(context);
        this.lineDashView.setLayoutParams(new RelativeLayout.LayoutParams(-1, 5));
        addView(this.lineDashView);
    }

    public void setDatas(List<WeightShowData> weightDatas1) {
        if (this.weightDatas != null) {
            this.weightDatas = null;
        }
        KLog.e("weightChartView " + this.weightChartView);
        if (this.weightChartView != null) {
            this.weightChartView.setDatas(weightDatas1, this.max);
        } else {
            this.weightDatas = weightDatas1;
        }
    }
}
