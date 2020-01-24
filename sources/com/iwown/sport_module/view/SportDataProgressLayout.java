package com.iwown.sport_module.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iwown.sport_module.R;

public class SportDataProgressLayout extends RelativeLayout {
    private TextView complete;
    private TextView complete_unit;
    private ImageView icon;
    private CircleProgressBar mCircleProgressBar;
    /* access modifiers changed from: private */
    public int mComplete_color;
    private Context mContext;
    private int mIcon_res;
    private int mProgress0_color;
    private Typeface mTypeface;
    private int mUnit;
    /* access modifiers changed from: private */
    public TextView target;

    public SportDataProgressLayout(Context context) {
        super(context);
    }

    public SportDataProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sport_module_sport_data_progress, this);
        initView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sport_module_SportDataProgressLayout);
        this.mProgress0_color = a.getColor(R.styleable.sport_module_SportDataProgressLayout_sport_module_progress0_color, -7829368);
        this.mComplete_color = a.getColor(R.styleable.sport_module_SportDataProgressLayout_sport_module_complete_color, -1);
        this.mIcon_res = a.getResourceId(R.styleable.sport_module_SportDataProgressLayout_sport_module_small_icon, -1);
        this.mUnit = a.getResourceId(R.styleable.sport_module_SportDataProgressLayout_sport_module_small_num_unit, -1);
        a.recycle();
        this.mCircleProgressBar.setProgress0_color(this.mProgress0_color);
        this.mCircleProgressBar.setComplete_color(this.mComplete_color);
        this.mCircleProgressBar.setCircle_num_color(this.mComplete_color);
        this.icon.setImageResource(this.mIcon_res);
    }

    public void setComplete(String complete_value) {
        this.complete.setText(complete_value);
        this.complete_unit.setText(this.mContext.getString(this.mUnit));
        this.complete.setTextColor(this.mComplete_color);
        this.complete_unit.setTextColor(this.mComplete_color);
    }

    public void startCircleAnim(float progress) {
        this.mCircleProgressBar.startProgressAnim(progress);
    }

    public void setTarget(String value) {
        this.target.setText(value);
        this.target.setTextColor(this.mComplete_color);
        this.target.post(new Runnable() {
            public void run() {
                SportDataProgressLayout.this.target.getPaint().setShader(new LinearGradient((float) SportDataProgressLayout.this.target.getWidth(), 0.0f, (float) SportDataProgressLayout.this.target.getWidth(), (float) SportDataProgressLayout.this.target.getHeight(), new int[]{SportDataProgressLayout.this.mComplete_color, -1}, new float[]{0.5f, 1.0f}, TileMode.CLAMP));
                SportDataProgressLayout.this.target.postInvalidate();
            }
        });
    }

    public SportDataProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("Sport-->", "onDraw");
    }

    private void initView() {
        this.target = (TextView) findViewById(R.id.target);
        this.icon = (ImageView) findViewById(R.id.icon);
        this.complete = (TextView) findViewById(R.id.complete);
        this.complete_unit = (TextView) findViewById(R.id.complete_unit);
        this.mCircleProgressBar = (CircleProgressBar) findViewById(R.id.circle_bar);
        this.mTypeface = Typeface.createFromAsset(this.mContext.getAssets(), "DINPRO-MEDIUM.ttf");
        this.target.setTypeface(this.mTypeface);
        this.complete.setTypeface(this.mTypeface);
    }
}
