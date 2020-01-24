package com.iwown.sport_module.view.ecg;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {
    private final Context con;
    public int height;
    private Renderer render;
    public int width;

    public ChartView(Context context) {
        super(context);
        this.con = context;
        this.render = new Renderer();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.con = context;
        this.render = new Renderer();
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.con = context;
        this.render = new Renderer();
    }

    public ChartView(Context context, Renderer renderer) {
        super(context);
        this.con = context;
        this.render = renderer;
    }

    public void setRender(Renderer render2) {
        this.render = render2;
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
    }
}
