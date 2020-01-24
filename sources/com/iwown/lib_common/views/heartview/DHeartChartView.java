package com.iwown.lib_common.views.heartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.R;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DHeartChartView extends View {
    private static final long time_12_min = 720;
    private int circlePointSize;
    private int d_line_color;
    private int d_show_line_color;
    private List<DlineDataBean> datas;
    private int default_line_size;
    private float downX;
    private float downY;
    private long endTime;
    private DlineDataBean firstDline;
    private boolean firstDown2Move;
    private boolean hasLine;
    private boolean isDraw;
    private int mTouchSlop;
    private boolean manulY;
    private int marginX2YLeft;
    private int marginX2YRgiht;
    private int maxRealYValue;
    private Paint paint_cirlce;
    private Paint paint_data_line;
    private Paint paint_data_line_X;
    private Paint paint_line_y;
    private Paint paint_show_line_touch;
    private Paint paint_text;
    private Paint paint_text_z_y;
    private Path pathLine;
    private Path pathLineX;
    private Path pathLineY;
    private Path path_src;
    private long realSizeTime;
    private boolean showCorner;
    private List<ShowXData> showXDatas;
    private boolean showXText;
    private List<ShowYData> showYDatas;
    private boolean showYText;
    private long startTime;
    private int startX;
    private int startY;
    private boolean touchShowData;
    private float touchX;
    private float touchY;
    private int userIntent;
    private int x_text_color;
    private int y_size;
    private float y_size_max_show_precent;

    public static class ShowXData {
        public int realX;
        public String text;
    }

    public static class ShowYData {
        public float realY;
        public String text;

        public String toString() {
            return "ShowYData{realY=" + this.realY + ", text='" + this.text + '\'' + '}';
        }
    }

    public void setY_size(int y_size2, float y_size_max_show_precent2) {
        this.y_size = y_size2;
        this.y_size_max_show_precent = y_size_max_show_precent2;
    }

    public void setShowYText(boolean showYText2) {
        this.showYText = showYText2;
        if (showYText2) {
            this.marginX2YLeft = DensityUtil.dip2px(getContext(), 20.0f);
        } else {
            this.marginX2YLeft = 10;
        }
    }

    public DHeartChartView(Context context) {
        this(context, null);
    }

    public DHeartChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DHeartChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.showXText = true;
        this.touchShowData = false;
        this.default_line_size = 3;
        this.d_line_color = ViewCompat.MEASURED_STATE_MASK;
        this.d_show_line_color = ViewCompat.MEASURED_STATE_MASK;
        this.x_text_color = ViewCompat.MEASURED_STATE_MASK;
        this.mTouchSlop = 10;
        this.maxRealYValue = 200;
        this.showYText = true;
        this.marginX2YLeft = 30;
        this.marginX2YRgiht = 8;
        this.y_size = 8;
        this.y_size_max_show_precent = 0.65f;
        this.hasLine = false;
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DLineChartView, defStyleAttr, 0);
        this.default_line_size = attributes.getDimensionPixelOffset(R.styleable.DLineChartView_d_line_size, this.default_line_size);
        this.d_line_color = attributes.getColor(R.styleable.DLineChartView_d_chart_color, this.d_line_color);
        this.d_show_line_color = attributes.getColor(R.styleable.DLineChartView_d_show_data_color, this.d_show_line_color);
        this.x_text_color = attributes.getColor(R.styleable.DLineChartView_d_x_text_color, this.x_text_color);
        this.showXText = attributes.getBoolean(R.styleable.DLineChartView_d_show_x_text, this.showXText);
        this.touchShowData = attributes.getBoolean(R.styleable.DLineChartView_d_show_data, this.touchShowData);
        attributes.recycle();
        this.path_src = new Path();
        this.pathLine = new Path();
        this.pathLineX = new Path();
        this.pathLineY = new Path();
        this.paint_data_line = new Paint();
        this.paint_line_y = new Paint();
        this.paint_cirlce = new Paint();
        this.paint_text = new Paint();
        this.paint_text_z_y = new Paint();
        this.paint_data_line.setAntiAlias(true);
        this.paint_data_line.setColor(this.d_line_color);
        this.paint_data_line.setStyle(Style.STROKE);
        this.paint_data_line.setStrokeWidth((float) this.default_line_size);
        this.paint_data_line.setPathEffect(new CornerPathEffect(30.0f));
        this.paint_text_z_y.setAntiAlias(true);
        this.paint_text_z_y.setColor(this.d_line_color);
        this.paint_text_z_y.setStyle(Style.STROKE);
        this.paint_text_z_y.setTextSize((float) DensityUtil.dip2px(getContext(), 20.0f));
        this.paint_cirlce.setAntiAlias(true);
        this.paint_cirlce.setColor(-7829368);
        this.paint_cirlce.setStyle(Style.FILL);
        this.paint_cirlce.setStrokeWidth((float) this.default_line_size);
        this.paint_line_y = new Paint();
        this.paint_line_y.setAntiAlias(true);
        this.paint_line_y.setColor(Color.parseColor("#F5A79F"));
        this.paint_line_y.setStyle(Style.STROKE);
        this.paint_line_y.setStrokeWidth((float) this.default_line_size);
        this.paint_line_y.setPathEffect(new DashPathEffect(new float[]{5.0f, 15.0f}, 0.0f));
        this.paint_data_line_X = new Paint();
        this.paint_data_line_X.setAntiAlias(true);
        this.paint_data_line_X.setColor(this.d_show_line_color);
        this.paint_data_line_X.setStyle(Style.STROKE);
        this.paint_data_line_X.setStrokeWidth((float) this.default_line_size);
        this.paint_show_line_touch = new Paint();
        this.paint_show_line_touch.setAntiAlias(true);
        this.paint_show_line_touch.setColor(this.d_line_color);
        this.paint_show_line_touch.setStyle(Style.STROKE);
        this.paint_show_line_touch.setStrokeWidth((float) this.default_line_size);
        this.paint_show_line_touch.setPathEffect(new DashPathEffect(new float[]{8.0f, 10.0f}, 0.0f));
        this.paint_text.setAntiAlias(true);
        this.paint_text.setColor(this.x_text_color);
        this.paint_text.setTextAlign(Align.CENTER);
        this.paint_text.setTextSize((float) DensityUtil.dip2px(getContext(), 10.0f));
        this.datas = new ArrayList();
        this.startX = getPaddingLeft();
        this.startY = getPaddingTop();
        this.circlePointSize = DensityUtil.dip2px(getContext(), 2.0f);
    }

    public void showXText(boolean showXText2) {
        this.showXText = showXText2;
    }

    public void TouchShowData(boolean touchShowData2) {
        this.touchShowData = touchShowData2;
    }

    private void filterDatas() {
        Iterator<DlineDataBean> iterator = this.datas.iterator();
        boolean isBegin = false;
        while (iterator.hasNext()) {
            DlineDataBean dataBean = (DlineDataBean) iterator.next();
            if (this.realSizeTime == 0) {
                dataBean.realX = this.marginX2YLeft + this.startX;
            } else {
                dataBean.realX = this.marginX2YLeft + this.startX + ((int) (((dataBean.time - this.startTime) * ((long) getRealChartWidth())) / (this.endTime - this.startTime)));
            }
            dataBean.realY = (int) (((float) getChartBottomY()) - (((1.0f * ((float) dataBean.value)) * ((float) getRealChartHeight())) / ((float) this.maxRealYValue)));
            if (dataBean.realY <= 0 || dataBean.value < 35 || dataBean.value > 200) {
                iterator.remove();
                isBegin = false;
            } else if (!isBegin) {
                dataBean.isBegin = true;
                isBegin = true;
            }
        }
    }

    private void createYDatas(List<Integer> y_titles) {
        int ySize = this.maxRealYValue / this.y_size;
        float maxYShowSize = this.y_size_max_show_precent * ((float) getChartBottomY());
        this.pathLineX.reset();
        if (y_titles == null || y_titles.size() <= 2) {
            for (int i = 0; i <= this.maxRealYValue; i += ySize) {
                ShowYData showYData = new ShowYData();
                showYData.realY = ((float) getChartBottomY()) - (((((float) i) * 1.0f) * ((float) getRealChartHeight())) / ((float) this.maxRealYValue));
                showYData.text = i + "";
                if (showYData.realY <= maxYShowSize && !TextUtils.equals("0", showYData.text)) {
                    this.pathLineX.moveTo((float) (this.startX + this.marginX2YLeft), showYData.realY);
                    this.pathLineX.lineTo((float) (this.startX + this.marginX2YLeft + getRealChartWidth()), showYData.realY);
                }
                if (!TextUtils.equals("0", showYData.text)) {
                    this.showYDatas.add(showYData);
                }
            }
            return;
        }
        int frist_value = ((Integer) y_titles.get(0)).intValue();
        int y0 = ((Integer) y_titles.get(0)).intValue();
        int size = ((Integer) y_titles.get(1)).intValue() - y0;
        int temp = y0;
        while (true) {
            temp -= size;
            if (temp <= 5) {
                break;
            }
            y_titles.add(0, Integer.valueOf(temp));
        }
        for (int i2 = 0; i2 < y_titles.size(); i2++) {
            ShowYData showYData2 = new ShowYData();
            int value = ((Integer) y_titles.get(i2)).intValue();
            showYData2.realY = ((float) getChartBottomY()) - (((((float) value) * 1.0f) * ((float) getRealChartHeight())) / ((float) this.maxRealYValue));
            showYData2.text = value + "";
            if (value >= frist_value) {
                this.pathLineX.moveTo((float) (this.startX + this.marginX2YLeft), showYData2.realY);
                this.pathLineX.lineTo((float) (this.startX + this.marginX2YLeft + getRealChartWidth()), showYData2.realY);
            }
            KLog.e(showYData2);
            this.showYDatas.add(showYData2);
        }
    }

    private void createXDatas() {
        long marginSize = DateUtil.Hour_S_Min;
        int size = (int) ((this.endTime - this.startTime) / 3600);
        int marginSize1 = size / 4;
        if (size % 4 != 0) {
            marginSize1++;
        }
        if (marginSize1 != 0) {
            marginSize *= (long) marginSize1;
        }
        for (long start = this.startTime; start <= this.endTime; start += marginSize) {
            ShowXData showXData = new ShowXData();
            if (this.realSizeTime == 0) {
                showXData.realX = 0;
                showXData.text = DataTimeUtils.getH(1000 * start);
            } else {
                showXData.realX = this.marginX2YLeft + this.startX + ((int) (((start - this.startTime) * ((long) getRealChartWidth())) / this.realSizeTime));
                if (start == this.endTime) {
                    showXData.text = "23:59";
                } else {
                    showXData.text = DataTimeUtils.getHM(1000 * start);
                }
            }
            if (((float) showXData.realX) - this.paint_text.getTextSize() >= 0.0f) {
                this.pathLineY.moveTo((float) showXData.realX, (float) getChartBottomY());
                this.pathLineY.lineTo((float) showXData.realX, 0.0f);
                this.showXDatas.add(showXData);
            }
        }
    }

    public void setDatas(final List<DlineDataBean> datas2, final List<Integer> y_titles) {
        initData();
        if (getWidth() == 0) {
            postDelayed(new Runnable() {
                public void run() {
                    DHeartChartView.this.setDatas(datas2, y_titles);
                }
            }, 200);
            return;
        }
        this.datas = datas2;
        if (datas2 == null) {
            this.datas = new ArrayList();
        }
        postDelayed(new DHeartChartView$$Lambda$0(this, y_titles), 100);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void lambda$setDatas$0$DHeartChartView(List y_titles) {
        DateUtil dateUtil = new DateUtil();
        if (this.datas.size() > 0) {
            dateUtil = new DateUtil(((DlineDataBean) this.datas.get(0)).time, true);
        }
        dateUtil.setHour(0);
        dateUtil.setMinute(0);
        dateUtil.setSecond(0);
        this.startTime = dateUtil.getUnixTimestamp();
        dateUtil.addDay(1);
        this.endTime = dateUtil.getUnixTimestamp();
        dateUtil.addDay(-1);
        this.realSizeTime = this.endTime - this.startTime;
        if (this.startTime <= this.endTime) {
            this.showXDatas = new ArrayList();
            this.showYDatas = new ArrayList();
            if (this.showXText) {
                createXDatas();
            }
            if (this.showYText) {
                createYDatas(y_titles);
            }
            filterDatas();
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void initData() {
        if (this.showXDatas != null) {
            this.showXDatas.clear();
        }
        if (this.showYDatas != null) {
            this.showYDatas.clear();
        }
        this.startTime = 0;
        this.endTime = 0;
        this.isDraw = false;
        this.pathLineY.reset();
        this.pathLineX.reset();
    }

    private int getRealChartWidth() {
        return (int) ((((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())) - this.paint_text.getTextSize()) - ((float) this.marginX2YLeft));
    }

    private int getRealChartHeight() {
        return (int) (((float) ((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom())) - this.paint_text.getTextSize());
    }

    public boolean isDraw() {
        return this.isDraw;
    }

    private int getChartBottomY() {
        return this.startY + getRealChartHeight();
    }

    private int getChartTopY() {
        return this.startY;
    }

    private int getXTransFormValue(float touchX2) {
        int i = 0;
        for (DlineDataBean dataBean : this.datas) {
            if (Math.abs(touchX2 - ((float) dataBean.realX)) <= 10.0f) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.path_src.reset();
        this.pathLine.reset();
        this.paint_data_line.setStyle(Style.STROKE);
        this.path_src.moveTo((float) this.startX, (float) getChartBottomY());
        if (this.datas.size() > 0) {
            DlineDataBean dataBean = (DlineDataBean) this.datas.get(0);
            this.path_src.moveTo((float) dataBean.realX, (float) dataBean.realY);
        }
        for (DlineDataBean dataBean2 : this.datas) {
            if (dataBean2.realY > getChartBottomY()) {
                break;
            } else if (dataBean2.realY >= 0) {
                if (dataBean2.isBegin) {
                    this.firstDline = dataBean2;
                    this.path_src.moveTo((float) dataBean2.realX, (float) dataBean2.realY);
                } else {
                    this.hasLine = true;
                    this.path_src.lineTo((float) dataBean2.realX, (float) dataBean2.realY);
                }
            }
        }
        if (this.showXText && this.showXDatas != null) {
            for (ShowXData showXData : this.showXDatas) {
                canvas.drawText(showXData.text, (float) showXData.realX, ((float) getChartBottomY()) + this.paint_text.getTextSize() + 15.0f, this.paint_text);
            }
        }
        if (this.showYText && this.showYDatas != null) {
            for (ShowYData showYData : this.showYDatas) {
                canvas.drawText(showYData.text, (float) this.startX, showYData.realY + (this.paint_text.getTextSize() / 4.0f), this.paint_text);
            }
            canvas.drawPath(this.pathLineX, this.paint_line_y);
        }
        if (this.datas.size() > 1) {
            canvas.drawPath(this.path_src, this.paint_data_line);
        }
        if (!this.hasLine && this.firstDline != null) {
            KLog.d("no2855 心率只有一个点。。。");
            this.paint_data_line.setStyle(Style.FILL);
            canvas.drawCircle((float) this.firstDline.realX, (float) this.firstDline.realY, 5.0f, this.paint_data_line);
        }
        this.paint_data_line.setStyle(Style.STROKE);
        if (!(this.touchX == 0.0f && this.touchY == 0.0f) && this.touchX > ((float) this.startX) && this.touchX < ((float) (this.startX + this.marginX2YLeft + getRealChartWidth()))) {
            int xTransFormValue = getXTransFormValue(this.touchX);
            if (xTransFormValue != -1) {
                this.pathLine.moveTo((float) ((DlineDataBean) this.datas.get(xTransFormValue)).realX, ((float) this.startY) + this.paint_text_z_y.getTextSize());
                this.pathLine.lineTo((float) ((DlineDataBean) this.datas.get(xTransFormValue)).realX, (float) getChartBottomY());
                canvas.drawPath(this.pathLine, this.paint_show_line_touch);
                String text = ((DlineDataBean) this.datas.get(xTransFormValue)).value + "";
                int g = text.length();
                if (text.length() == 3) {
                    g = 1;
                }
                canvas.drawText(text, ((float) ((DlineDataBean) this.datas.get(xTransFormValue)).realX) - (this.paint_text_z_y.getTextSize() / ((float) g)), (((float) this.startY) + this.paint_text_z_y.getTextSize()) - 20.0f, this.paint_text_z_y);
                canvas.drawCircle((float) ((DlineDataBean) this.datas.get(xTransFormValue)).realX, (float) ((DlineDataBean) this.datas.get(xTransFormValue)).realY, 8.0f, this.paint_cirlce);
            }
        }
        canvas.drawLine((float) (this.startX + this.marginX2YLeft), (float) getChartBottomY(), (float) (this.startX + this.marginX2YLeft + getRealChartWidth()), (float) getChartBottomY(), this.paint_data_line_X);
        this.isDraw = true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.touchShowData) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case 0:
                this.touchX = event.getX();
                this.touchY = event.getY();
                this.firstDown2Move = true;
                this.downX = this.touchX;
                this.downY = this.touchY;
                getParent().requestDisallowInterceptTouchEvent(true);
                this.userIntent = 0;
                invalidate();
                return true;
            case 1:
                resetData();
                return true;
            case 2:
                if (this.firstDown2Move) {
                    float sizeY = event.getY() - this.downY;
                    if (Math.abs(event.getX() - this.downX) < ((float) this.mTouchSlop) && Math.abs(sizeY) < ((float) this.mTouchSlop)) {
                        return true;
                    }
                    this.firstDown2Move = false;
                }
                this.touchX = event.getX();
                this.touchY = event.getY();
                if (this.userIntent != 0) {
                    return true;
                }
                invalidate();
                return true;
            case 3:
                resetData();
                return true;
            default:
                return true;
        }
    }

    private void resetData() {
        this.touchX = 0.0f;
        this.touchY = 0.0f;
        this.downY = 0.0f;
        this.downX = 0.0f;
        this.firstDown2Move = false;
        invalidate();
    }

    public void setShowCorner(boolean showCorner2) {
        if (showCorner2) {
            this.paint_data_line.setPathEffect(new CornerPathEffect(30.0f));
        } else {
            this.paint_data_line.setPathEffect(null);
        }
    }
}
