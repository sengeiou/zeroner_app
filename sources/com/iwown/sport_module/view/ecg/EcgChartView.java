package com.iwown.sport_module.view.ecg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.ui.ecg.bean.Filtering;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EcgChartView extends ChartView {
    private Context context;
    float[] data = new float[21750];
    private Runnable dataRun = new Runnable() {
        public void run() {
            EcgChartView.this.scrollTo(0, 0);
        }
    };
    private List<Integer> dataValue = new ArrayList();
    private float[] drawData;
    Filtering filtering = new Filtering();
    private boolean hasData;
    protected int mSGridWidth = 10;
    private int speed = 2;
    /* access modifiers changed from: private */
    public long time;

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed2) {
        this.speed = speed2;
        postInvalidate();
        if (speed2 == 2) {
            this.speed = 10;
            execTimer(90);
            return;
        }
        this.speed = 20;
        execTimer(85);
    }

    public EcgChartView(Context context2) {
        super(context2);
        this.context = context2;
    }

    public EcgChartView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        this.context = context2;
    }

    public EcgChartView(Context context2, AttributeSet attrs, int defStyleAttr) {
        super(context2, attrs, defStyleAttr);
        this.context = context2;
    }

    public EcgChartView(Context context2, Renderer renderer) {
        super(context2, renderer);
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.drawData == null) {
            getDrawData();
        }
        if (this.hasData && this.dataValue != null && this.dataValue.size() > 0) {
            DrawChart(canvas);
        }
    }

    public void DrawChart(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(-1);
        p.setStrokeWidth(3.0f);
        canvas.translate(0.0f, (float) (this.height / 2));
        canvas.drawLines(this.drawData, p);
        scrollBy(this.speed, 0);
        postInvalidateDelayed(1000);
    }

    public boolean getDrawData() {
        this.mSGridWidth = DensityUtil.dip2px(this.context, 10.0f);
        int totalData = 4350 * 5;
        this.hasData = true;
        float step = (float) DensityUtil.dip2px(this.context, 1.0f);
        this.drawData = new float[87000];
        for (int i = 0; i < totalData; i++) {
            this.drawData[i * 4] = ((float) i) * step;
            this.drawData[(i * 4) + 1] = -((this.data[i] / 0.1f) * ((float) this.mSGridWidth));
            this.drawData[(i * 4) + 2] = ((float) (i + 1)) * step;
            if (i + 1 == totalData) {
                break;
            }
            this.drawData[(i * 4) + 3] = -((this.data[i + 1] / 0.1f) * ((float) this.mSGridWidth));
        }
        return this.hasData;
    }

    private List<Integer> lvbo(List<Integer> dataList) {
        this.filtering.init();
        List<Integer> list = new ArrayList<>();
        long sum = 0;
        for (int i = 0; i < dataList.size(); i++) {
            sum += (long) ((Integer) dataList.get(i)).intValue();
        }
        float avg = (((float) sum) * 1.0f) / ((float) dataList.size());
        KLog.i("--avg--" + avg + "--sum--" + sum + "--dataList.size()----" + dataList.size());
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            list.add(Integer.valueOf(this.filtering.filteringMain((int) (((float) ((Integer) dataList.get(i2)).intValue()) - avg), true)));
        }
        return list;
    }

    public void setDataList(List<Integer> dataList) {
        int seq;
        try {
            scrollTo(0, 0);
            this.drawData = null;
            this.dataValue = new ArrayList();
            this.data = new float[21750];
            List<Integer> ecgList = new ArrayList<>();
            new ArrayList();
            if (dataList != null && dataList.size() > 750) {
                for (int i = 0; i < dataList.size(); i++) {
                    ecgList.add(dataList.get(i));
                }
                KLog.i("lv 前:" + JsonUtils.toJson(ecgList));
                List<Integer> list = lvbo(ecgList);
                if (list == null || list.size() <= 0) {
                    for (int i2 = 0; i2 < 21750; i2++) {
                        this.data[i2] = 0.0f;
                    }
                } else {
                    if (list.size() >= 21750) {
                        seq = 21750;
                    } else {
                        seq = list.size();
                    }
                    for (int i3 = 750; i3 < seq; i3++) {
                        this.dataValue.add(list.get(i3));
                    }
                    for (int i4 = 0; i4 < this.dataValue.size(); i4++) {
                        float f = (float) (((double) ((((float) ((Integer) this.dataValue.get(i4)).intValue()) / 2000.0f) * 2000.0f)) / 2000.0d);
                        if (i4 >= 21750) {
                            break;
                        }
                        this.data[i4] = 1.0f * f;
                    }
                    KLog.i("lv后：" + JsonUtils.toJson(this.data));
                }
                postInvalidate();
                if (this.speed == 2) {
                    execTimer(90);
                } else {
                    execTimer(85);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private void execTimer(final int second) {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (new DateUtil().getUnixTimestamp() - EcgChartView.this.time > ((long) second)) {
                    EcgChartView.this.scrollTo(0, 0);
                    EcgChartView.this.time = new DateUtil().getUnixTimestamp();
                }
            }
        }, 0, (long) second, TimeUnit.SECONDS);
    }
}
