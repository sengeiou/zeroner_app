package com.iwown.sport_module.view.run;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.iwown.sport_module.R;
import com.socks.library.KLog;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class DPointChartView extends View {
    static DecimalFormat decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    private int bottom_text_size;
    private int circlePointSize;
    private int d_line_color;
    private int d_show_line_color;
    /* access modifiers changed from: private */
    public List<DlineDataBean> datas;
    private int default_line_size;
    private float downX;
    private float downY;
    private long endTime;
    private boolean firstDown2Move;
    private float first_value;
    private float fourth_value;
    private boolean isColorReverse;
    private boolean isReverse;
    private boolean isShowmins;
    private int mTouchSlop;
    private int marginX2YLeft;
    private int marginX2YRgiht;
    private float maxRate;
    private int maxRealYValue;
    private int maxYValue;
    private Paint paint;
    private Paint paint_cirlce;
    private Paint paint_show_line;
    private Paint paint_show_line_x;
    private Paint paint_show_line_x1;
    private Paint paint_text;
    private Paint paint_text_line;
    private Path pathLine;
    private Path pathLineX;
    private Path path_src;
    private long realSizeTime;
    private float second_value;
    private boolean showCorner;
    private List<ShowXData> showXDatas;
    private boolean showXText;
    private List<ShowYData> showYDatas;
    private boolean showYText;
    private float standRate;
    private long startTime;
    private int startX;
    private int startY;
    private float third_value;
    private boolean touchShowData;
    private float touchX;
    private float touchY;
    private int userIntent;
    private int xTransFormValue;
    private int x_text_color;
    private int y_rate;
    private int y_start_value;

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

    public void setShowmins(boolean showmins) {
        this.isShowmins = showmins;
    }

    public void setShowYText(boolean showYText2) {
        this.showYText = showYText2;
    }

    public void setMaxRealYValue(int maxRealYValue2) {
        if (maxRealYValue2 != 0) {
            this.maxRealYValue = maxRealYValue2;
        }
        this.marginX2YLeft = Math.round((((float) (String.valueOf(maxRealYValue2).length() + 1)) * this.paint_text.getTextSize()) / 1.5f);
    }

    public void setReverse(boolean reverse) {
        this.isReverse = reverse;
    }

    public DPointChartView(Context context) {
        this(context, null);
    }

    public DPointChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DPointChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.showXText = true;
        this.touchShowData = false;
        this.default_line_size = 3;
        this.d_line_color = ViewCompat.MEASURED_STATE_MASK;
        this.d_show_line_color = -1;
        this.x_text_color = Color.parseColor("#FFFFFF");
        this.mTouchSlop = 10;
        this.maxRealYValue = 15;
        this.showYText = false;
        this.marginX2YLeft = 10;
        this.marginX2YRgiht = 8;
        this.isReverse = false;
        this.bottom_text_size = 15;
        this.y_start_value = 5;
        this.y_rate = 5;
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
        this.y_start_value = attributes.getInteger(R.styleable.DLineChartView_d_y_value, this.y_start_value);
        this.y_rate = attributes.getInteger(R.styleable.DLineChartView_d_y_rate, this.y_rate);
        attributes.recycle();
        this.path_src = new Path();
        this.pathLine = new Path();
        this.pathLineX = new Path();
        this.paint = new Paint();
        this.paint_show_line = new Paint();
        this.paint_show_line_x = new Paint();
        this.paint_cirlce = new Paint();
        this.paint_text = new Paint();
        this.paint_text_line = new Paint();
        this.paint_show_line_x1 = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setColor(this.d_line_color);
        this.paint.setStrokeCap(Cap.ROUND);
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeWidth((float) DensityUtil.dip2px(getContext(), 6.0f));
        this.paint_text_line.setAntiAlias(true);
        this.paint_text_line.setColor(-1);
        this.paint_text_line.setStyle(Style.STROKE);
        this.paint_text_line.setTextAlign(Align.CENTER);
        this.paint_text_line.setTextSize((float) DensityUtil.dip2px(getContext(), 25.0f));
        this.paint_text_line.setTypeface(FontChangeUtils.getNumberTypeFace());
        this.paint_cirlce.setAntiAlias(true);
        this.paint_cirlce.setColor(-1);
        this.paint_cirlce.setStyle(Style.STROKE);
        this.paint_cirlce.setStrokeWidth((float) DensityUtil.dip2px(getContext(), 3.0f));
        this.paint_show_line = new Paint();
        this.paint_show_line.setAntiAlias(true);
        this.paint_show_line.setColor(this.d_show_line_color);
        this.paint_show_line.setStyle(Style.STROKE);
        this.paint_show_line.setStrokeWidth((float) DensityUtil.dip2px(getContext(), 1.0f));
        this.paint_show_line.setPathEffect(new DashPathEffect(new float[]{5.0f, 15.0f}, 0.0f));
        this.paint_show_line_x = new Paint();
        this.paint_show_line_x.setAntiAlias(true);
        this.paint_show_line_x.setColor(Color.parseColor("#80FFFFFF"));
        this.paint_show_line_x.setStyle(Style.STROKE);
        this.paint_show_line_x.setStrokeWidth((float) this.default_line_size);
        this.paint_show_line_x.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 0.0f));
        this.paint_show_line_x1 = new Paint();
        this.paint_show_line_x1.setAntiAlias(true);
        this.paint_show_line_x1.setColor(Color.parseColor("#80FFFFFF"));
        this.paint_show_line_x1.setStyle(Style.STROKE);
        this.paint_show_line_x1.setStrokeWidth((float) this.default_line_size);
        this.paint_text.setAntiAlias(true);
        this.paint_text.setColor(this.x_text_color);
        this.paint_text.setTextAlign(Align.CENTER);
        this.paint_text.setTextSize((float) DensityUtil.dip2px(getContext(), 10.0f));
        this.datas = new ArrayList();
        this.startX = getPaddingLeft();
        this.startY = (int) (((float) getPaddingTop()) + this.paint_text_line.getTextSize());
        this.circlePointSize = DensityUtil.dip2px(getContext(), 6.0f);
        this.bottom_text_size = DensityUtil.dip2px(getContext(), 10.0f);
    }

    public void showXText(boolean showXText2) {
        this.showXText = showXText2;
    }

    public void TouchShowData(boolean touchShowData2) {
        this.touchShowData = touchShowData2;
    }

    public void setDatas(List<DlineDataBean> datas2) {
        setDatas(datas2, false);
    }

    public void setDatas(final List<DlineDataBean> datas2, boolean b) {
        initData();
        postDelayed(new Runnable() {
            public void run() {
                DPointChartView.this.datas = datas2;
                DPointChartView.this.invalidate();
            }
        }, 200);
    }

    public void setDatas(final List<DlineDataBean> datas2, float first_value2, float second_value2, float third_value2, float fourth_value2, boolean isReverse2) {
        initData();
        this.first_value = first_value2;
        this.second_value = second_value2;
        this.third_value = third_value2;
        this.fourth_value = fourth_value2;
        this.isColorReverse = isReverse2;
        postDelayed(new Runnable() {
            public void run() {
                DPointChartView.this.datas = datas2;
                DPointChartView.this.invalidate();
            }
        }, 200);
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
    }

    private int getRealChartWidth() {
        return (int) ((((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())) - this.paint_text.getTextSize()) - ((float) this.marginX2YLeft));
    }

    private int getRealChartHeight() {
        return (int) ((((float) ((getMeasuredHeight() - this.startY) - getPaddingBottom())) - this.paint_text.getTextSize()) - ((float) this.bottom_text_size));
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
        if (this.datas.size() == 0) {
            invalidate();
            return;
        }
        this.startTime = ((DlineDataBean) this.datas.get(0)).time;
        for (int i = 0; i < this.datas.size(); i++) {
            if (i > 0) {
                ((DlineDataBean) this.datas.get(i)).time = this.startTime + ((long) (i * 60));
            }
        }
        this.endTime = ((DlineDataBean) this.datas.get(this.datas.size() - 1)).time;
        this.realSizeTime = this.endTime - this.startTime;
        KLog.e("startTime " + DataTimeUtils.getyyyyMMddHHmmss(this.startTime * 1000));
        KLog.e("endTime " + DataTimeUtils.getyyyyMMddHHmmss(this.endTime * 1000) + "  " + this.datas.size());
        if (this.startTime <= this.endTime) {
            this.showXDatas = new ArrayList();
            this.showYDatas = new ArrayList();
            long realmin = (this.realSizeTime / 60) + 1;
            int real_size = (int) (realmin / 5);
            if (((int) (realmin % 5)) != 0) {
                real_size++;
            }
            long marginSize = (long) (real_size * 60);
            if (marginSize == 0) {
                marginSize = 60;
            }
            long realEdnTime = this.startTime + (5 * marginSize);
            KLog.e(realmin + " 每段size " + real_size + "   marginSize " + marginSize + " min " + realEdnTime);
            if (this.showXText) {
                KLog.e("realChartWidth " + getRealChartWidth() + " realEdnTime " + DataTimeUtils.getHM(1000 * realEdnTime));
                for (long start = this.startTime; start <= realEdnTime; start += marginSize) {
                    ShowXData showXData = new ShowXData();
                    showXData.realX = this.marginX2YLeft + this.startX + ((int) (((start - this.startTime) * ((long) getRealChartWidth())) / (realEdnTime - this.startTime)));
                    showXData.text = DateUtil.getMarginMin(start, this.startTime);
                    if (((float) showXData.realX) - this.paint_text.getTextSize() >= 0.0f) {
                        this.showXDatas.add(showXData);
                    }
                }
            }
            if (this.showYText) {
                int max_value = this.y_start_value + (this.y_rate * 5);
                if (this.maxRealYValue > max_value) {
                    this.maxYValue = ((this.maxRealYValue / this.y_rate) * this.y_rate) + ((this.maxRealYValue / max_value) * this.y_rate);
                } else {
                    this.maxYValue = max_value;
                }
                if (this.maxYValue < 6) {
                    this.maxYValue = 6;
                }
                int ySize = this.maxYValue / 6;
                KLog.e("DPointChartView", this.maxYValue + "-------------------");
                for (int i2 = 0; i2 < 6; i2++) {
                    int y_value = this.y_start_value + ((i2 - 1) * this.y_rate);
                    ShowYData showYData = new ShowYData();
                    if (this.isReverse) {
                        showYData.realY = ((float) this.startY) + ((((1.0f * ((float) i2)) * ((float) ySize)) * ((float) getRealChartHeight())) / ((float) this.maxYValue));
                    } else {
                        showYData.realY = ((float) getChartBottomY()) - ((((1.0f * ((float) i2)) * ((float) ySize)) * ((float) getRealChartHeight())) / ((float) this.maxYValue));
                    }
                    if (i2 == 0) {
                        showYData.text = "0";
                        this.showYDatas.add(showYData);
                    } else if (i2 == 5) {
                        this.maxRate = (1.0f * ((float) this.maxYValue)) / ((float) ySize);
                        showYData.text = this.maxYValue + "";
                        this.showYDatas.add(showYData);
                    } else {
                        this.standRate = (1.0f * ((float) y_value)) / ((float) ySize);
                        showYData.text = y_value + "";
                        this.showYDatas.add(showYData);
                    }
                }
                KLog.e("DPointChartView", "DLineChart:licl" + JsonTool.toJson(this.showYDatas));
            }
            Iterator<DlineDataBean> iterator = this.datas.iterator();
            while (iterator.hasNext()) {
                DlineDataBean dataBean = (DlineDataBean) iterator.next();
                if (this.realSizeTime == 0) {
                    dataBean.realX = this.marginX2YLeft + this.startX;
                } else {
                    dataBean.realX = this.marginX2YLeft + this.startX + ((int) (((dataBean.time - this.startTime) * ((long) getRealChartWidth())) / (realEdnTime - this.startTime)));
                }
                if (!this.isReverse) {
                    dataBean.realY = (int) (((float) getChartBottomY()) - showYInfo(dataBean.value));
                } else {
                    dataBean.realY = (int) (((float) this.startY) + showYInfo(dataBean.value));
                }
                if (dataBean.realY < 0) {
                    iterator.remove();
                }
            }
            this.path_src.reset();
            this.pathLine.reset();
            this.pathLineX.reset();
            this.path_src.moveTo((float) this.startX, (float) getChartBottomY());
            if (this.datas.size() > 1) {
                DlineDataBean dataBean2 = (DlineDataBean) this.datas.get(1);
                this.path_src.moveTo((float) dataBean2.realX, (float) dataBean2.realY);
            }
            for (int i3 = 0; i3 < this.datas.size(); i3++) {
                DlineDataBean dataBean3 = (DlineDataBean) this.datas.get(i3);
                if (dataBean3.realY > getChartBottomY()) {
                    break;
                }
                if (dataBean3.realY >= 0) {
                    setColorChoose(dataBean3.value, this.first_value, this.second_value, this.third_value, this.fourth_value, this.isColorReverse);
                    this.path_src.lineTo((float) dataBean3.realX, (float) dataBean3.realY);
                    if (dataBean3.value != 0.0f) {
                        canvas.drawPoint((float) dataBean3.realX, (float) dataBean3.realY, this.paint);
                    }
                    KLog.e("dpointcharview", dataBean3.realY + "-------------");
                }
            }
            int i4 = 0;
            if (this.showXText && this.showXDatas != null) {
                for (ShowXData showXData2 : this.showXDatas) {
                    canvas.drawText(showXData2.text, (float) showXData2.realX, ((float) getChartBottomY()) + this.paint_text.getTextSize() + ((float) this.bottom_text_size), this.paint_text);
                    i4++;
                }
            }
            if (this.showYText && this.showYDatas != null) {
                for (ShowYData showYData2 : this.showYDatas) {
                    canvas.drawText(showYData2.text, (float) ((this.marginX2YLeft / 2) + DensityUtil.dip2px(getContext(), 5.0f)), showYData2.realY + (this.paint_text.getTextSize() / 4.0f), this.paint_text);
                    if (this.isReverse) {
                        this.pathLineX.moveTo((float) (this.startX + this.marginX2YLeft), showYData2.realY);
                        this.pathLineX.lineTo((float) (this.startX + this.marginX2YLeft + getRealChartWidth()), showYData2.realY);
                    } else if (!TextUtils.equals("0", showYData2.text)) {
                        this.pathLineX.moveTo((float) (this.startX + this.marginX2YLeft), showYData2.realY);
                        this.pathLineX.lineTo((float) (this.startX + this.marginX2YLeft + getRealChartWidth()), showYData2.realY);
                    }
                }
                canvas.drawPath(this.pathLineX, this.paint_show_line_x);
            }
            if (this.datas.size() > 1) {
            }
            if (!((this.touchX == 0.0f && this.touchY == 0.0f) || this.touchX <= ((float) this.startX) || this.touchX >= ((float) (this.startX + getRealChartWidth())) || this.xTransFormValue == -1 || ((DlineDataBean) this.datas.get(this.xTransFormValue)).value == 0.0f || this.xTransFormValue == -1)) {
                this.pathLine.moveTo((float) ((DlineDataBean) this.datas.get(this.xTransFormValue)).realX, (float) this.startY);
                this.pathLine.lineTo((float) ((DlineDataBean) this.datas.get(this.xTransFormValue)).realX, (float) getChartBottomY());
                canvas.drawPath(this.pathLine, this.paint_show_line);
                if (this.isShowmins) {
                    if (this.xTransFormValue == 0) {
                        canvas.drawText(number2mins(((DlineDataBean) this.datas.get(this.xTransFormValue)).value), (float) (((DlineDataBean) this.datas.get(this.xTransFormValue)).realX + DensityUtil.dip2px(getContext(), 20.0f)), this.paint_text_line.getTextSize(), this.paint_text_line);
                    } else {
                        canvas.drawText(number2mins(((DlineDataBean) this.datas.get(this.xTransFormValue)).value), (float) ((DlineDataBean) this.datas.get(this.xTransFormValue)).realX, this.paint_text_line.getTextSize(), this.paint_text_line);
                    }
                } else if (this.xTransFormValue == 0) {
                    canvas.drawText(((DlineDataBean) this.datas.get(this.xTransFormValue)).value + "", (float) (((DlineDataBean) this.datas.get(this.xTransFormValue)).realX + DensityUtil.dip2px(getContext(), 20.0f)), this.paint_text_line.getTextSize(), this.paint_text_line);
                } else {
                    canvas.drawText(((DlineDataBean) this.datas.get(this.xTransFormValue)).value + "", (float) ((DlineDataBean) this.datas.get(this.xTransFormValue)).realX, this.paint_text_line.getTextSize(), this.paint_text_line);
                }
                canvas.drawCircle((float) ((DlineDataBean) this.datas.get(this.xTransFormValue)).realX, (float) ((DlineDataBean) this.datas.get(this.xTransFormValue)).realY, (float) DensityUtil.dip2px(getContext(), 4.0f), this.paint_cirlce);
            }
            canvas.drawLine((float) (this.startX + this.marginX2YLeft), (float) getChartBottomY(), (float) (this.startX + this.marginX2YLeft + getRealChartWidth()), (float) getChartBottomY(), this.paint_show_line_x1);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.touchX = event.getRawX();
                this.touchY = event.getRawY();
                this.firstDown2Move = true;
                this.downX = this.touchX;
                this.downY = this.touchY;
                this.xTransFormValue = getXTransFormValue(this.touchX);
                if (this.xTransFormValue != -1) {
                    invalidate();
                    break;
                }
                break;
            case 1:
                resetData();
                break;
            case 2:
                if (Math.abs(event.getRawX() - this.downX) <= Math.abs(event.getRawY() - this.downY)) {
                    KLog.e("上下滑动");
                    this.userIntent = 1;
                    break;
                } else {
                    KLog.e("左右滑动");
                    this.userIntent = 0;
                    this.touchX = event.getX();
                    this.touchY = event.getY();
                    if (this.userIntent == 0) {
                        this.xTransFormValue = getXTransFormValue(this.touchX);
                        if (this.xTransFormValue != -1) {
                            invalidate();
                            break;
                        }
                    }
                }
                break;
            case 3:
                resetData();
                break;
        }
        return true;
    }

    private void resetData() {
        this.touchX = 0.0f;
        this.touchY = 0.0f;
        this.downY = 0.0f;
        this.downX = 0.0f;
        this.firstDown2Move = false;
        this.xTransFormValue = -1;
        invalidate();
    }

    public void setShowCorner(boolean showCorner2) {
        if (showCorner2) {
            this.paint.setPathEffect(new CornerPathEffect(30.0f));
        } else {
            this.paint.setPathEffect(null);
        }
    }

    public static String number2mins(float maxY_pace) {
        try {
            String[] split1 = decimalFormat.format((double) maxY_pace).split("\\.");
            return split1[0] + "'" + ((int) ((((double) (((float) Integer.parseInt(split1[1])) * 1.0f)) / Math.pow(10.0d, (double) split1[1].length())) * 60.0d)) + "''";
        } catch (NumberFormatException e) {
            ThrowableExtension.printStackTrace(e);
            return maxY_pace + "";
        }
    }

    private void setColorChoose(float value, float first_value2, float second_value2, float third_value2, float fourth_value2, boolean isReverse2) {
        if (first_value2 != 0.0f) {
            if (!isReverse2) {
                if (value >= first_value2) {
                    this.paint.setColor(getResources().getColor(R.color.heart_column_color3));
                } else if (value >= second_value2) {
                    this.paint.setColor(getResources().getColor(R.color.heart_column_color4));
                } else if (value >= third_value2) {
                    this.paint.setColor(getResources().getColor(R.color.heart_column_color2));
                } else if (value >= fourth_value2) {
                    this.paint.setColor(getResources().getColor(R.color.heart_column_color7));
                } else {
                    this.paint.setColor(getResources().getColor(R.color.heart_column_color8));
                }
            } else if (value < first_value2) {
                this.paint.setColor(getResources().getColor(R.color.heart_column_color3));
            } else if (value < second_value2) {
                this.paint.setColor(getResources().getColor(R.color.heart_column_color4));
            } else if (value < third_value2) {
                this.paint.setColor(getResources().getColor(R.color.heart_column_color2));
            } else if (value < fourth_value2) {
                this.paint.setColor(getResources().getColor(R.color.heart_column_color7));
            } else {
                this.paint.setColor(getResources().getColor(R.color.heart_column_color8));
            }
        }
    }

    private float showYInfo(float value) {
        int aHeight = getRealChartHeight() / 6;
        int lastValue = -1;
        int i = 0;
        while (i < 5) {
            if (i == 4) {
                return ((((value - ((float) lastValue)) * 1.0f) / ((float) (this.maxYValue - lastValue))) * ((float) aHeight)) + ((float) (aHeight * 4));
            }
            int yy = this.y_start_value + (this.y_rate * i);
            if (((float) yy) < value) {
                lastValue = yy;
                i++;
            } else if (lastValue != -1) {
                return (((value - ((float) lastValue)) / ((float) (yy - lastValue))) * ((float) aHeight)) + ((float) (aHeight * i));
            } else {
                return ((1.0f * value) / ((float) yy)) * ((float) ((i * aHeight) + aHeight));
            }
        }
        return -1.0f;
    }
}
