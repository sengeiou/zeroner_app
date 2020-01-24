package com.iwown.lib_common.views.sleepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.iwown.lib_common.R;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DSleepChartView extends View {
    private boolean canCanvasNoData;
    private int color1;
    private int color2;
    private int color3;
    /* access modifiers changed from: private */
    public List<SleepData> datas;
    private int defaultPadding;
    /* access modifiers changed from: private */
    public boolean isDraw;
    /* access modifiers changed from: private */
    public float lineMaxHeight;
    private float lineMaxWidth;
    /* access modifiers changed from: private */
    public float lineSize;
    private Paint line_tag;
    private int line_tag_color;
    private Paint line_x;
    /* access modifiers changed from: private */
    public List<RF> listRF1;
    /* access modifiers changed from: private */
    public List<RF> listRF2;
    /* access modifiers changed from: private */
    public List<RF> listRF3;
    private List<Float> list_tags;
    private int maxHeight;
    private int maxWidth;
    private int maxYCount;
    private float maxYWidth;
    private String no_data_text;
    private int outLineColor;
    private float outLineStrokeWidth;
    private float[] outLines;
    private RectF outRectF;
    private Paint outTextPaint;
    private float outTextSize;
    private float outYSize;
    private float paddingB;
    private float paddingL;
    private float paddingR;
    private float paddingT;
    /* access modifiers changed from: private */
    public Paint rectFPaint;
    /* access modifiers changed from: private */
    public float rectFStartX;
    private boolean setBegin;
    private boolean setEnd;
    private boolean showNoData;
    private boolean showY;
    /* access modifiers changed from: private */
    public long xBeginTime;
    /* access modifiers changed from: private */
    public long xEndTime;
    private List<String> xTitles;
    private int x_text_color;
    private List<YTitle> yTitles;

    static class RF {
        RectF rectF;
        int type;

        public RF(RectF rectF2, int type2) {
            this.rectF = rectF2;
            this.type = type2;
        }
    }

    public static class SleepData {
        public static final int type1 = 1;
        public static final int type2 = 2;
        public static final int type3 = 3;
        public long endTime;
        public long startTime;
        public int type;

        public SleepData(long startTime2) {
            this.startTime = startTime2;
            this.type = 1;
        }

        public SleepData(long startTime2, long endTime2, int type4) {
            this.startTime = startTime2;
            this.endTime = endTime2;
            this.type = type4;
        }

        public void setStartTime(long startTime2) {
            this.startTime = startTime2;
        }

        public void setEndTime(long endTime2) {
            this.endTime = endTime2;
        }

        public void setType(int type4) {
            this.type = type4;
        }

        public String toString() {
            return "SleepData{startTime=" + DataTimeUtils.getyyyyMMddHHmmss(this.startTime) + ", endTime=" + DataTimeUtils.getyyyyMMddHHmmss(this.endTime) + ", type=" + this.type + '}';
        }

        public static int getChartTypeBySleepType(int type4) {
            switch (type4) {
                case 3:
                    return 3;
                case 4:
                    return 2;
                default:
                    return 1;
            }
        }
    }

    public static class YTitle {
        float percent;
        String title;
        int type;

        public YTitle(String title2, float percent2, int type2) {
            this.title = title2;
            this.percent = percent2;
            this.type = type2;
        }
    }

    public void setNo_data_text(String no_data_text2) {
        this.no_data_text = no_data_text2;
    }

    public void destory() {
    }

    public void setColors(int sleepSkin_type_chart_tag_wake_bg, int sleepSkin_type_chart_tag_light_bg, int sleepSkin_type_chart_tag_deep_bg) {
        this.color1 = sleepSkin_type_chart_tag_wake_bg;
        this.color2 = sleepSkin_type_chart_tag_light_bg;
        this.color3 = sleepSkin_type_chart_tag_deep_bg;
    }

    public DSleepChartView(Context context) {
        this(context, null);
    }

    public DSleepChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DSleepChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.outLineStrokeWidth = 5.0f;
        this.outLineColor = -1;
        this.outTextSize = 20.0f;
        this.yTitles = new ArrayList();
        this.xTitles = new ArrayList();
        this.maxYCount = 2;
        this.outYSize = 0.0f;
        this.defaultPadding = 50;
        this.datas = new ArrayList();
        this.xBeginTime = 0;
        this.xEndTime = 0;
        this.listRF1 = new ArrayList();
        this.listRF2 = new ArrayList();
        this.listRF3 = new ArrayList();
        this.color1 = Color.parseColor("#BFC0FE");
        this.color2 = Color.parseColor("#9697FF");
        this.color3 = Color.parseColor("#4243D2");
        this.list_tags = new ArrayList();
        this.line_tag_color = Color.parseColor("#4243D2");
        this.x_text_color = ViewCompat.MEASURED_STATE_MASK;
        this.no_data_text = "";
        initView(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = 21)
    public DSleepChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.outLineStrokeWidth = 5.0f;
        this.outLineColor = -1;
        this.outTextSize = 20.0f;
        this.yTitles = new ArrayList();
        this.xTitles = new ArrayList();
        this.maxYCount = 2;
        this.outYSize = 0.0f;
        this.defaultPadding = 50;
        this.datas = new ArrayList();
        this.xBeginTime = 0;
        this.xEndTime = 0;
        this.listRF1 = new ArrayList();
        this.listRF2 = new ArrayList();
        this.listRF3 = new ArrayList();
        this.color1 = Color.parseColor("#BFC0FE");
        this.color2 = Color.parseColor("#9697FF");
        this.color3 = Color.parseColor("#4243D2");
        this.list_tags = new ArrayList();
        this.line_tag_color = Color.parseColor("#4243D2");
        this.x_text_color = ViewCompat.MEASURED_STATE_MASK;
        this.no_data_text = "";
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setColor1(int color12) {
        this.color1 = color12;
    }

    public void setColor2(int color22) {
        this.color2 = color22;
    }

    public void setColor3(int color32) {
        this.color3 = color32;
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DSleepChartView, defStyleAttr, 0);
        this.color1 = typedArray.getColor(R.styleable.DSleepChartView_d_color1, this.color1);
        this.color2 = typedArray.getColor(R.styleable.DSleepChartView_d_color2, this.color2);
        this.color3 = typedArray.getColor(R.styleable.DSleepChartView_d_color3, this.color3);
        this.x_text_color = typedArray.getColor(R.styleable.DSleepChartView_d_sleep_x_text_color, this.x_text_color);
        this.showNoData = typedArray.getBoolean(R.styleable.DSleepChartView_d_show_no_data, this.showNoData);
        this.showY = typedArray.getBoolean(R.styleable.DSleepChartView_d_show_y, this.showY);
        typedArray.recycle();
        this.paddingT = (float) getPaddingTop();
        this.paddingL = (float) getPaddingLeft();
        this.paddingR = (float) getPaddingRight();
        this.paddingB = (float) getPaddingBottom();
        this.line_tag = new Paint();
        this.line_tag.setAntiAlias(true);
        this.line_tag.setColor(this.line_tag_color);
        this.line_tag.setStrokeWidth(3.0f);
        this.line_tag.setStyle(Style.STROKE);
        this.line_x = new Paint();
        this.line_x.setAntiAlias(true);
        this.line_x.setColor(Color.parseColor("#99ffffff"));
        this.line_x.setStrokeWidth(3.0f);
        this.line_x.setStyle(Style.STROKE);
        this.paddingT += this.outLineStrokeWidth;
        this.paddingL += this.outLineStrokeWidth;
        this.paddingR += this.outLineStrokeWidth;
        this.paddingB += this.outLineStrokeWidth;
        this.outTextSize = (float) getResources().getDimensionPixelOffset(R.dimen.dp_10);
        this.outTextPaint = new Paint();
        this.outTextPaint.setAntiAlias(true);
        this.outTextPaint.setTextAlign(Align.CENTER);
        this.outTextPaint.setColor(this.x_text_color);
        this.outTextPaint.setTextSize(this.outTextSize);
        addDataY(1, 0.55f);
        addDataY(2, 0.61f);
        addDataY(3, 0.95f);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.maxYWidth = (((float) this.maxYCount) * this.outTextSize) + this.outYSize;
        this.maxHeight = h;
        this.maxWidth = w;
        this.lineMaxHeight = (((float) (h - this.defaultPadding)) - (this.outLineStrokeWidth / 2.0f)) - 10.0f;
        this.lineMaxWidth = (float) (w - this.defaultPadding);
        this.outLines = new float[]{this.maxYWidth + 0.0f, this.outLineStrokeWidth, this.maxYWidth + 0.0f, (float) (h - this.defaultPadding), this.maxYWidth + (this.outLineStrokeWidth / 2.0f), this.lineMaxHeight, this.lineMaxWidth, this.lineMaxHeight};
        this.rectFStartX = this.maxYWidth + (this.outLineStrokeWidth / 2.0f);
        this.rectFPaint = new Paint();
        this.rectFPaint.setStrokeWidth(2.0f);
        this.lineSize = this.lineMaxWidth - (this.maxYWidth + (this.outLineStrokeWidth / 2.0f));
        if (!this.isDraw) {
            calshowDatas();
        }
    }

    /* access modifiers changed from: private */
    public float getYSize(int type) {
        for (int i = 0; i < this.yTitles.size(); i++) {
            if (((YTitle) this.yTitles.get(i)).type == type) {
                return ((1.0f - ((YTitle) this.yTitles.get(i)).percent) * ((float) this.maxHeight)) + (this.outTextSize / 2.0f);
            }
        }
        return 0.0f;
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
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.showY) {
            this.outTextPaint.setTextSize(this.outTextSize);
            for (int i = 0; i < this.yTitles.size(); i++) {
                canvas.drawText(((YTitle) this.yTitles.get(i)).title, (this.maxYWidth - (((float) ((YTitle) this.yTitles.get(i)).title.length()) * this.outTextSize)) - (this.outYSize / 2.0f), ((1.0f - ((YTitle) this.yTitles.get(i)).percent) * ((float) this.maxHeight)) + this.outTextSize, this.outTextPaint);
            }
        }
        this.list_tags.clear();
        if (this.setBegin && this.setEnd && this.xTitles.size() > 0) {
            this.outTextPaint.setTextSize(this.outTextSize);
            for (int i2 = 0; i2 < this.xTitles.size(); i2++) {
                float startX = (((float) i2) / ((float) (this.xTitles.size() - 1))) * this.lineMaxWidth;
                float startY = this.lineMaxHeight + this.outTextSize + 20.0f;
                if (startX < this.maxYWidth) {
                    startX += this.maxYWidth;
                }
                if (startX > this.lineMaxWidth) {
                    startX -= (float) this.defaultPadding;
                }
                this.list_tags.add(Float.valueOf(startX));
                canvas.drawText((String) this.xTitles.get(i2), startX, startY, this.outTextPaint);
            }
        }
        for (int i3 = 0; i3 < this.listRF1.size(); i3++) {
            this.rectFPaint.setColor(this.color1);
            canvas.drawRect(((RF) this.listRF1.get(i3)).rectF, this.rectFPaint);
        }
        for (int i4 = 0; i4 < this.listRF2.size(); i4++) {
            this.rectFPaint.setColor(this.color2);
            canvas.drawRect(((RF) this.listRF2.get(i4)).rectF, this.rectFPaint);
        }
        for (int i5 = 0; i5 < this.listRF3.size(); i5++) {
            this.rectFPaint.setColor(this.color3);
            canvas.drawRect(((RF) this.listRF3.get(i5)).rectF, this.rectFPaint);
        }
        int i6 = 1;
        while (this.list_tags.size() > 2 && i6 < this.list_tags.size() - 1) {
            canvas.drawLine(((Float) this.list_tags.get(i6)).floatValue(), this.lineMaxHeight - this.outLineStrokeWidth, ((Float) this.list_tags.get(i6)).floatValue(), 3.0f + this.lineMaxHeight, this.line_tag);
            i6++;
        }
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.rectFStartX - ((float) (this.defaultPadding / 2)), this.lineMaxHeight - this.rectFPaint.getStrokeWidth(), ((float) (this.defaultPadding / 2)) + this.lineMaxWidth, this.lineMaxHeight - this.rectFPaint.getStrokeWidth(), this.line_x);
    }

    public void addDataY(int yTitle, float percentY) {
        String str = "";
        if (yTitle == 1) {
            str = "清醒";
        } else if (yTitle == 2) {
            str = "浅睡";
        } else if (yTitle == 3) {
            str = "深睡";
        }
        this.yTitles.add(new YTitle(str, percentY, yTitle));
        if (str.length() > this.maxYCount) {
            this.maxYCount = str.length();
        }
    }

    /* access modifiers changed from: private */
    public void setbeginX(long time) {
        this.xBeginTime = time;
        this.setBegin = true;
    }

    /* access modifiers changed from: private */
    public void setEndX(long time) {
        this.xEndTime = time;
        this.setEnd = true;
        float min = (float) Math.round(((float) (this.xEndTime - this.xBeginTime)) / 60000.0f);
        int kkk = (int) min;
        int interval = (kkk / 4) + (kkk % 4);
        this.xTitles.clear();
        long pre_time = 0;
        for (int i = 0; ((float) i) < min; i += interval) {
            if (i == 0) {
                this.xTitles.add(DataTimeUtils.getHM(this.xBeginTime));
                pre_time = this.xBeginTime;
            } else {
                long time1 = pre_time + ((long) (interval * 60 * 1000));
                KLog.d("DSLEEPChatview", time1 + "-----" + interval);
                this.xTitles.add(showIntegerTime(time1));
                pre_time = time1;
            }
        }
        this.xTitles.add(DataTimeUtils.getHM(time));
    }

    private String showIntegerTime(long time) {
        DateUtil dateUtil = new DateUtil(new Date(time));
        return new DateUtil(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), dateUtil.getHour(), 0).getHHmmDate();
    }

    public void updateSleepDatas(List<SleepData> lists) {
        initAction();
        if (lists == null || lists.size() == 0) {
            invalidate();
            return;
        }
        this.datas.addAll(lists);
        calshowDatas();
    }

    private void calshowDatas() {
        postDelayed(new Runnable() {
            public void run() {
                if (DSleepChartView.this.rectFStartX == 0.0f) {
                    KLog.e("rectFStartX is 0 ");
                    return;
                }
                DSleepChartView.this.isDraw = true;
                if (DSleepChartView.this.datas.size() != 0) {
                    if (DSleepChartView.this.rectFPaint == null) {
                        DSleepChartView.this.rectFPaint = new Paint();
                        DSleepChartView.this.rectFPaint.setStrokeWidth(2.0f);
                    }
                    DSleepChartView.this.setbeginX(((SleepData) DSleepChartView.this.datas.get(0)).startTime);
                    DSleepChartView.this.setEndX(((SleepData) DSleepChartView.this.datas.get(DSleepChartView.this.datas.size() - 1)).endTime);
                    for (int i = 0; i < DSleepChartView.this.datas.size(); i++) {
                        if (((SleepData) DSleepChartView.this.datas.get(i)).type == 1) {
                            DSleepChartView.this.listRF1.add(new RF(new RectF(DSleepChartView.this.rectFStartX + ((((float) (((SleepData) DSleepChartView.this.datas.get(i)).startTime - DSleepChartView.this.xBeginTime)) * DSleepChartView.this.lineSize) / ((float) (DSleepChartView.this.xEndTime - DSleepChartView.this.xBeginTime))), DSleepChartView.this.getYSize(((SleepData) DSleepChartView.this.datas.get(i)).type), DSleepChartView.this.rectFStartX + ((((float) (((SleepData) DSleepChartView.this.datas.get(i)).endTime - DSleepChartView.this.xBeginTime)) * DSleepChartView.this.lineSize) / ((float) (DSleepChartView.this.xEndTime - DSleepChartView.this.xBeginTime))), DSleepChartView.this.lineMaxHeight - DSleepChartView.this.rectFPaint.getStrokeWidth()), ((SleepData) DSleepChartView.this.datas.get(i)).type));
                        } else if (((SleepData) DSleepChartView.this.datas.get(i)).type == 2) {
                            DSleepChartView.this.listRF2.add(new RF(new RectF(DSleepChartView.this.rectFStartX + ((((float) (((SleepData) DSleepChartView.this.datas.get(i)).startTime - DSleepChartView.this.xBeginTime)) * DSleepChartView.this.lineSize) / ((float) (DSleepChartView.this.xEndTime - DSleepChartView.this.xBeginTime))), DSleepChartView.this.getYSize(((SleepData) DSleepChartView.this.datas.get(i)).type), DSleepChartView.this.rectFStartX + ((((float) (((SleepData) DSleepChartView.this.datas.get(i)).endTime - DSleepChartView.this.xBeginTime)) * DSleepChartView.this.lineSize) / ((float) (DSleepChartView.this.xEndTime - DSleepChartView.this.xBeginTime))), DSleepChartView.this.lineMaxHeight - DSleepChartView.this.rectFPaint.getStrokeWidth()), ((SleepData) DSleepChartView.this.datas.get(i)).type));
                        } else if (((SleepData) DSleepChartView.this.datas.get(i)).type == 3) {
                            DSleepChartView.this.listRF3.add(new RF(new RectF(DSleepChartView.this.rectFStartX + ((((float) (((SleepData) DSleepChartView.this.datas.get(i)).startTime - DSleepChartView.this.xBeginTime)) * DSleepChartView.this.lineSize) / ((float) (DSleepChartView.this.xEndTime - DSleepChartView.this.xBeginTime))), DSleepChartView.this.getYSize(((SleepData) DSleepChartView.this.datas.get(i)).type), DSleepChartView.this.rectFStartX + ((((float) (((SleepData) DSleepChartView.this.datas.get(i)).endTime - DSleepChartView.this.xBeginTime)) * DSleepChartView.this.lineSize) / ((float) (DSleepChartView.this.xEndTime - DSleepChartView.this.xBeginTime))), DSleepChartView.this.lineMaxHeight - DSleepChartView.this.rectFPaint.getStrokeWidth()), ((SleepData) DSleepChartView.this.datas.get(i)).type));
                        }
                    }
                    DSleepChartView.this.invalidate();
                }
            }
        }, 100);
    }

    private void initAction() {
        this.datas.clear();
        this.listRF1.clear();
        this.listRF2.clear();
        this.listRF3.clear();
        this.xTitles.clear();
        this.canCanvasNoData = true;
        this.isDraw = false;
    }
}
