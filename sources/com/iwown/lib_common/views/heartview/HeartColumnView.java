package com.iwown.lib_common.views.heartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.R;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HeartColumnView extends View {
    private int d_column_color1;
    private int d_column_color2;
    private int d_column_color3;
    private int d_column_color4;
    private int d_column_color5;
    private int d_column_color6;
    private int d_line_color;
    private int d_text_color;
    public List<HeartTypeValueData> heartTypeValueData;
    int[] limit_values;
    int[] limit_values_color;
    private int max_value_x;
    /* access modifiers changed from: private */
    public int max_value_y;
    private Paint paint_line_x_y;
    /* access modifiers changed from: private */
    public Paint paint_text_x;
    private Paint paint_text_x_line;
    /* access modifiers changed from: private */
    public Paint paint_text_y;
    /* access modifiers changed from: private */
    public int startX;
    /* access modifiers changed from: private */
    public int startY;
    private int textTop;
    /* access modifiers changed from: private */
    public ArrayList<YTilte> yTitles;

    public static class HeartTypeValueData {
        public int type;
        public int value;
        public int x1;
        public int x2 = -1;
        public String xText;
        public int y;

        public String toString() {
            return "HeartTypeValueData{value=" + this.value + ", type=" + this.type + ", x1=" + this.x1 + ", x2=" + this.x2 + ", xText='" + this.xText + '\'' + ", y=" + this.y + '}';
        }

        public HeartTypeValueData(int value2, int type2) {
            this.value = value2;
            this.type = type2;
        }
    }

    class YTilte {
        public String text;
        public int y;

        YTilte() {
        }
    }

    public int[] getLimit_values_color() {
        return this.limit_values_color;
    }

    public HeartColumnView(Context context) {
        this(context, null);
    }

    public HeartColumnView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartColumnView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.max_value_y = 50;
        this.max_value_x = 200;
        this.limit_values = new int[]{40, 98, 112, Opcodes.NEG_LONG, Opcodes.SUB_INT, Opcodes.MUL_FLOAT, Opcodes.SHR_INT_2ADDR};
        this.limit_values_color = new int[]{-10855846, -16749313, -10563840, -29440, -65407, -2949120};
        this.heartTypeValueData = new ArrayList();
        this.yTitles = new ArrayList<>();
        this.d_line_color = ViewCompat.MEASURED_STATE_MASK;
        this.d_text_color = -1;
        this.textTop = 5;
        initView(context, attrs, defStyleAttr);
    }

    public void setLimit_values(int[] limit_values2) {
        this.limit_values = limit_values2;
    }

    public int[] getLimit_values() {
        return this.limit_values;
    }

    @RequiresApi(api = 21)
    public HeartColumnView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.max_value_y = 50;
        this.max_value_x = 200;
        this.limit_values = new int[]{40, 98, 112, Opcodes.NEG_LONG, Opcodes.SUB_INT, Opcodes.MUL_FLOAT, Opcodes.SHR_INT_2ADDR};
        this.limit_values_color = new int[]{-10855846, -16749313, -10563840, -29440, -65407, -2949120};
        this.heartTypeValueData = new ArrayList();
        this.yTitles = new ArrayList<>();
        this.d_line_color = ViewCompat.MEASURED_STATE_MASK;
        this.d_text_color = -1;
        this.textTop = 5;
        initView(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int getSize(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        return mode == 1073741824 ? height : Math.min(200, height);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        int i = DensityUtil.dip2px(getContext(), 10.0f);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DHeartColumnView, defStyleAttr, 0);
        this.d_line_color = attributes.getColor(R.styleable.DHeartColumnView_d_line_color, this.d_line_color);
        this.d_text_color = attributes.getColor(R.styleable.DHeartColumnView_d_text_color, this.d_text_color);
        this.d_column_color1 = attributes.getColor(R.styleable.DHeartColumnView_d_column_color1, this.d_column_color1);
        this.d_column_color2 = attributes.getColor(R.styleable.DHeartColumnView_d_column_color2, this.d_column_color2);
        this.d_column_color3 = attributes.getColor(R.styleable.DHeartColumnView_d_column_color3, this.d_column_color3);
        this.d_column_color4 = attributes.getColor(R.styleable.DHeartColumnView_d_column_color4, this.d_column_color4);
        this.d_column_color5 = attributes.getColor(R.styleable.DHeartColumnView_d_column_color5, this.d_column_color5);
        this.d_column_color6 = attributes.getColor(R.styleable.DHeartColumnView_d_column_color6, this.d_column_color6);
        this.limit_values_color[0] = this.d_column_color1;
        this.limit_values_color[1] = this.d_column_color2;
        this.limit_values_color[2] = this.d_column_color3;
        this.limit_values_color[3] = this.d_column_color4;
        this.limit_values_color[4] = this.d_column_color5;
        this.limit_values_color[5] = this.d_column_color6;
        attributes.recycle();
        this.paint_text_x = new Paint();
        this.paint_text_x.setAntiAlias(true);
        this.paint_text_x.setColor(this.d_text_color);
        this.paint_text_x.setTextSize((float) i);
        this.paint_text_x.setTextAlign(Align.CENTER);
        this.paint_text_x_line = new Paint();
        this.paint_text_x_line.setAntiAlias(true);
        this.paint_text_x_line.setTextSize((float) i);
        this.paint_text_y = new Paint();
        this.paint_text_y.setAntiAlias(true);
        this.paint_text_y.setColor(this.d_text_color);
        this.paint_text_y.setTextSize((float) i);
        this.paint_text_y.setTextAlign(Align.CENTER);
        this.startX = (int) (((float) getPaddingLeft()) + (this.paint_text_y.getTextSize() * 2.0f));
        this.startY = getPaddingTop();
        this.paint_line_x_y = new Paint();
        this.paint_line_x_y.setAntiAlias(true);
        this.paint_line_x_y.setColor(this.d_line_color);
    }

    public int getRealHeight() {
        return (int) (((((float) (getHeight() - this.startY)) - this.paint_text_x.getTextSize()) - ((float) getPaddingBottom())) - ((float) getPaddingTop()));
    }

    public int getRealWidth() {
        return (((getWidth() - this.startX) - getPaddingLeft()) - getPaddingRight()) - DensityUtil.dip2px(getContext(), 30.0f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.heartTypeValueData != null && this.heartTypeValueData.size() != 0) {
            this.textTop = DensityUtil.dip2px(getContext(), 8.0f);
            int height_y = getRealHeight();
            canvas.drawLine((float) this.startX, (float) height_y, (float) getWidth(), (float) height_y, this.paint_line_x_y);
            int last_x = 0;
            String last_text = "";
            for (HeartTypeValueData data : this.heartTypeValueData) {
                if (data.x2 > -1) {
                    int paint_stroke_width = data.x2 - data.x1;
                    this.paint_text_x_line.setStrokeWidth((float) paint_stroke_width);
                    this.paint_text_x_line.setColor(this.limit_values_color[data.type - 1]);
                    canvas.drawLine((float) (data.x1 + (paint_stroke_width / 2)), (float) data.y, (float) (data.x1 + (paint_stroke_width / 2)), (float) height_y, this.paint_text_x_line);
                }
                String text = data.xText;
                canvas.drawText(text, (float) data.x1, (float) ((getHeight() - getPaddingBottom()) + this.textTop), this.paint_text_x);
                last_x = data.x1;
                last_text = text;
            }
            Canvas canvas2 = canvas;
            canvas2.drawText("(bpm)", ((float) last_x) + (((float) last_text.length()) * this.paint_text_x.getTextSize()), (float) ((getHeight() - getPaddingBottom()) + this.textTop), this.paint_text_x);
            Iterator it = this.yTitles.iterator();
            while (it.hasNext()) {
                YTilte yTilte = (YTilte) it.next();
                canvas.drawText(yTilte.text, ((float) this.startX) - this.paint_text_y.getTextSize(), ((float) yTilte.y) + (this.paint_text_y.getTextSize() / 2.0f), this.paint_text_y);
            }
        }
    }

    public void setDatas(final List<HeartTypeValueData> heartTypeValueData1) {
        this.max_value_y = 50;
        postDelayed(new Runnable() {
            public void run() {
                if (heartTypeValueData1 == null || heartTypeValueData1.size() == 0) {
                    HeartColumnView.this.invalidate();
                    return;
                }
                float size = ((float) (HeartColumnView.this.getRealWidth() - DensityUtil.dip2px(HeartColumnView.this.getContext(), 20.0f))) / 6.0f;
                int startX1 = (int) (((float) HeartColumnView.this.startX) + HeartColumnView.this.paint_text_x.getTextSize());
                for (HeartTypeValueData data : heartTypeValueData1) {
                    if (data.value > HeartColumnView.this.max_value_y) {
                        HeartColumnView.this.max_value_y = data.value;
                    }
                }
                HeartColumnView.this.max_value_y = HeartColumnView.this.max_value_y + 10;
                for (HeartTypeValueData data2 : heartTypeValueData1) {
                    if (data2.type > 7) {
                        KLog.e("type is <=7");
                    } else {
                        data2.y = (int) (((float) HeartColumnView.this.startY) + ((1.0f - ((((float) data2.value) * 1.0f) / ((float) HeartColumnView.this.max_value_y))) * ((float) HeartColumnView.this.getRealHeight())));
                        data2.xText = HeartColumnView.this.limit_values[data2.type - 1] + "";
                        data2.x1 = ((int) (((float) (data2.type - 1)) * size)) + startX1;
                        if (data2.type < 7) {
                            data2.x2 = ((int) (((float) data2.type) * size)) + startX1;
                        }
                    }
                }
                HeartColumnView.this.heartTypeValueData.clear();
                HeartColumnView.this.heartTypeValueData.addAll(heartTypeValueData1);
                HeartColumnView.this.yTitles = new ArrayList();
                float size1 = ((float) HeartColumnView.this.max_value_y) / 6.0f;
                for (int i = 0; i < HeartColumnView.this.max_value_y; i++) {
                    YTilte yTilte = new YTilte();
                    yTilte.y = (int) (((float) HeartColumnView.this.startY) + ((1.0f - (((1.0f * size1) * ((float) i)) / ((float) HeartColumnView.this.max_value_y))) * ((float) HeartColumnView.this.getRealHeight())));
                    yTilte.text = ((int) (((float) i) * size1)) + "";
                    if (((float) yTilte.y) >= HeartColumnView.this.paint_text_y.getTextSize()) {
                        HeartColumnView.this.yTitles.add(yTilte);
                    }
                }
                HeartColumnView.this.invalidate();
            }
        }, 100);
    }
}
