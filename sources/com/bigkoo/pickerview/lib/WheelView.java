package com.bigkoo.pickerview.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.bigkoo.pickerview.adapter.WheelAdapter;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.bigkoo.pickerview.model.IPickerViewData;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.lib_common.R;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WheelView extends View {
    private static final float SCALECONTENT = 1.0f;
    private static final int VELOCITYFLING = 5;
    private float CENTERCONTENTOFFSET;
    WheelAdapter adapter;
    float centerY;
    int change;
    Context context;
    private final float density;
    int dividerColor;
    private DividerType dividerType;
    private int drawCenterContentStart;
    private int drawOutContentStart;
    float firstLineY;
    private GestureDetector gestureDetector;
    int halfCircumference;
    Handler handler;
    int initPosition;
    private boolean isCenterLabel;
    boolean isLoop;
    private boolean isOptions;
    float itemHeight;
    int itemsVisible;
    private String label;
    private int leftlineColor;
    float lineSpacingMultiplier;
    ScheduledExecutorService mExecutor;
    private ScheduledFuture<?> mFuture;
    private int mGravity;
    private int mOffset;
    int maxTextHeight;
    int maxTextWidth;
    int measuredHeight;
    int measuredWidth;
    OnItemSelectedListener onItemSelectedListener;
    Paint paintCenterText;
    Paint paintIndicator;
    Paint paintLeftLine;
    Paint paintOuterText;
    int preCurrentIndex;
    private float previousY;
    int radius;
    float secondLineY;
    private int selectedItem;
    private boolean showLeftLine;
    long startTime;
    int textColorCenter;
    int textColorOut;
    int textSize;
    float totalScrollY;
    Typeface typeface;
    int widthMeasureSpec;

    public enum ACTION {
        CLICK,
        FLING,
        DAGGLE
    }

    public enum DividerType {
        FILL,
        WRAP
    }

    public void setItemsVisible(int itemsVisible2) {
        this.itemsVisible = itemsVisible2;
    }

    public void setShowLeftLine(boolean showLeftLine2) {
        this.showLeftLine = showLeftLine2;
    }

    public WheelView(Context context2) {
        this(context2, null);
    }

    public WheelView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        this.leftlineColor = -41982;
        this.isOptions = false;
        this.isCenterLabel = true;
        this.mExecutor = Executors.newSingleThreadScheduledExecutor();
        this.typeface = Typeface.MONOSPACE;
        this.textColorOut = -5723992;
        this.textColorCenter = -14013910;
        this.dividerColor = -2763307;
        this.lineSpacingMultiplier = 2.0f;
        this.itemsVisible = 5;
        this.mOffset = 0;
        this.previousY = 0.0f;
        this.startTime = 0;
        this.mGravity = 17;
        this.drawCenterContentStart = 0;
        this.drawOutContentStart = 0;
        this.textSize = getResources().getDimensionPixelSize(R.dimen.pickerview_textsize);
        this.density = getResources().getDisplayMetrics().density;
        if (this.density < 1.0f) {
            this.CENTERCONTENTOFFSET = 2.4f;
        } else if (1.0f <= this.density && this.density < 2.0f) {
            this.CENTERCONTENTOFFSET = 3.6f;
        } else if (1.0f <= this.density && this.density < 2.0f) {
            this.CENTERCONTENTOFFSET = 4.5f;
        } else if (2.0f <= this.density && this.density < 3.0f) {
            this.CENTERCONTENTOFFSET = 6.0f;
        } else if (this.density >= 3.0f) {
            this.CENTERCONTENTOFFSET = this.density * 2.5f;
        }
        if (attrs != null) {
            TypedArray a = context2.obtainStyledAttributes(attrs, R.styleable.pickerview, 0, 0);
            this.mGravity = a.getInt(R.styleable.pickerview_pickerview_gravity, 17);
            this.textColorOut = a.getColor(R.styleable.pickerview_pickerview_textColorOut, this.textColorOut);
            this.textColorCenter = a.getColor(R.styleable.pickerview_pickerview_textColorCenter, this.textColorCenter);
            this.dividerColor = a.getColor(R.styleable.pickerview_pickerview_dividerColor, this.dividerColor);
            this.textSize = a.getDimensionPixelOffset(R.styleable.pickerview_pickerview_textSize, this.textSize);
            this.lineSpacingMultiplier = a.getFloat(R.styleable.pickerview_pickerview_lineSpacingMultiplier, this.lineSpacingMultiplier);
            a.recycle();
        }
        judgeLineSpae();
        initLoopView(context2);
    }

    private void judgeLineSpae() {
        if (this.lineSpacingMultiplier < 1.2f) {
            this.lineSpacingMultiplier = 1.2f;
        } else if (this.lineSpacingMultiplier > 2.0f) {
            this.lineSpacingMultiplier = 2.0f;
        }
    }

    private void initLoopView(Context context2) {
        this.context = context2;
        this.handler = new MessageHandler(this);
        this.gestureDetector = new GestureDetector(context2, new LoopViewGestureListener(this));
        this.gestureDetector.setIsLongpressEnabled(false);
        this.isLoop = true;
        this.totalScrollY = 0.0f;
        this.initPosition = -1;
        initPaints();
    }

    private void initPaints() {
        this.paintOuterText = new Paint();
        this.paintOuterText.setColor(this.textColorOut);
        this.paintOuterText.setAntiAlias(true);
        this.paintOuterText.setTypeface(this.typeface);
        this.paintOuterText.setTextSize((float) this.textSize);
        this.paintCenterText = new Paint();
        this.paintCenterText.setColor(this.textColorCenter);
        this.paintCenterText.setAntiAlias(true);
        this.paintCenterText.setTextScaleX(1.1f);
        this.paintCenterText.setTypeface(this.typeface);
        this.paintCenterText.setTextSize((float) this.textSize);
        this.paintIndicator = new Paint();
        this.paintIndicator.setColor(this.dividerColor);
        this.paintIndicator.setAntiAlias(true);
        this.paintIndicator.setStrokeWidth(1.0f * this.density);
        this.paintLeftLine = new Paint();
        this.paintLeftLine.setColor(this.leftlineColor);
        this.paintLeftLine.setAntiAlias(true);
        this.paintLeftLine.setStrokeWidth(2.0f * this.density);
        if (VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
    }

    private void remeasure() {
        if (this.adapter != null) {
            measureTextWidthHeight();
            this.halfCircumference = (int) (this.itemHeight * ((float) (this.itemsVisible - 1)));
            this.measuredHeight = (int) (((double) (this.halfCircumference * 2)) / 3.141592653589793d);
            this.radius = (int) (((double) this.halfCircumference) / 3.141592653589793d);
            this.measuredWidth = MeasureSpec.getSize(this.widthMeasureSpec);
            this.firstLineY = (((float) this.measuredHeight) - this.itemHeight) / 2.0f;
            this.secondLineY = (((float) this.measuredHeight) + this.itemHeight) / 2.0f;
            this.centerY = (this.secondLineY - ((this.itemHeight - ((float) this.maxTextHeight)) / 2.0f)) - this.CENTERCONTENTOFFSET;
            if (this.initPosition == -1) {
                if (this.isLoop) {
                    this.initPosition = (this.adapter.getItemsCount() + 1) / 2;
                } else {
                    this.initPosition = 0;
                }
            }
            this.preCurrentIndex = this.initPosition;
        }
    }

    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < this.adapter.getItemsCount(); i++) {
            String s1 = getContentText(this.adapter.getItem(i));
            this.paintCenterText.getTextBounds(s1, 0, s1.length(), rect);
            int textWidth = rect.width();
            if (textWidth > this.maxTextWidth) {
                this.maxTextWidth = textWidth;
            }
            this.paintCenterText.getTextBounds("星期", 0, 2, rect);
            this.maxTextHeight = rect.height() + 2;
        }
        this.itemHeight = this.lineSpacingMultiplier * ((float) this.maxTextHeight);
    }

    /* access modifiers changed from: 0000 */
    public void smoothScroll(ACTION action) {
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            this.mOffset = (int) (((this.totalScrollY % this.itemHeight) + this.itemHeight) % this.itemHeight);
            if (((float) this.mOffset) > this.itemHeight / 2.0f) {
                this.mOffset = (int) (this.itemHeight - ((float) this.mOffset));
            } else {
                this.mOffset = -this.mOffset;
            }
        }
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, this.mOffset), 0, 10, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: protected */
    public final void scrollBy(float velocityY) {
        cancelFuture();
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, velocityY), 0, 5, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (this.mFuture != null && !this.mFuture.isCancelled()) {
            this.mFuture.cancel(true);
            this.mFuture = null;
        }
    }

    public final void setCyclic(boolean cyclic) {
        this.isLoop = cyclic;
    }

    public final void setTypeface(Typeface font) {
        this.typeface = font;
        this.paintOuterText.setTypeface(this.typeface);
        this.paintCenterText.setTypeface(this.typeface);
    }

    public final void setTextSize(float size) {
        if (size > 0.0f) {
            this.textSize = (int) (this.context.getResources().getDisplayMetrics().density * size);
            this.paintOuterText.setTextSize((float) this.textSize);
            this.paintCenterText.setTextSize((float) this.textSize);
        }
    }

    public final void setCurrentItem(int currentItem) {
        this.selectedItem = currentItem;
        this.initPosition = currentItem;
        this.totalScrollY = 0.0f;
        invalidate();
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener OnItemSelectedListener) {
        this.onItemSelectedListener = OnItemSelectedListener;
    }

    public final void setAdapter(WheelAdapter adapter2) {
        this.adapter = adapter2;
        remeasure();
        invalidate();
    }

    public final WheelAdapter getAdapter() {
        return this.adapter;
    }

    public final int getCurrentItem() {
        return this.selectedItem;
    }

    /* access modifiers changed from: protected */
    public final void onItemSelected() {
        if (this.onItemSelectedListener != null) {
            postDelayed(new OnItemSelectedRunnable(this), 200);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        String contentText;
        float translateY;
        float startX;
        if (this.adapter != null) {
            if (this.initPosition < 0) {
                this.initPosition = 0;
            }
            if (this.initPosition >= this.adapter.getItemsCount()) {
                this.initPosition = this.adapter.getItemsCount() - 1;
            }
            Object[] visibles = new Object[this.itemsVisible];
            this.change = (int) (this.totalScrollY / this.itemHeight);
            try {
                this.preCurrentIndex = this.initPosition + (this.change % this.adapter.getItemsCount());
            } catch (ArithmeticException e) {
                Log.e("WheelView", "出错了！adapter.getItemsCount() == 0，联动数据不匹配");
            }
            if (!this.isLoop) {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = 0;
                }
                if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                    this.preCurrentIndex = this.adapter.getItemsCount() - 1;
                }
            } else {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = this.adapter.getItemsCount() + this.preCurrentIndex;
                }
                if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                    this.preCurrentIndex -= this.adapter.getItemsCount();
                }
            }
            float itemHeightOffset = this.totalScrollY % this.itemHeight;
            for (int counter = 0; counter < this.itemsVisible; counter++) {
                int index = this.preCurrentIndex - ((this.itemsVisible / 2) - counter);
                if (this.isLoop) {
                    visibles[counter] = this.adapter.getItem(getLoopMappingIndex(index));
                } else if (index < 0) {
                    visibles[counter] = "";
                } else if (index > this.adapter.getItemsCount() - 1) {
                    visibles[counter] = "";
                } else {
                    visibles[counter] = this.adapter.getItem(index);
                }
            }
            if (this.dividerType == DividerType.WRAP) {
                if (TextUtils.isEmpty(this.label)) {
                    startX = (float) (((this.measuredWidth - this.maxTextWidth) / 2) - 12);
                } else {
                    startX = (float) (((this.measuredWidth - this.maxTextWidth) / 4) - 12);
                }
                if (startX <= 0.0f) {
                    startX = 10.0f;
                }
                float endX = ((float) this.measuredWidth) - startX;
                canvas.drawLine(startX, this.firstLineY, endX, this.firstLineY, this.paintIndicator);
                canvas.drawLine(startX, this.secondLineY, endX, this.secondLineY, this.paintIndicator);
            } else {
                canvas.drawLine(0.0f, this.firstLineY, (float) this.measuredWidth, this.firstLineY, this.paintIndicator);
                canvas.drawLine(0.0f, this.secondLineY, (float) this.measuredWidth, this.secondLineY, this.paintIndicator);
            }
            if (this.showLeftLine) {
                Log.e("WV ", this.showLeftLine + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.itemHeight);
                canvas.drawLine(0.0f, this.firstLineY + 5.0f, 0.0f, this.secondLineY - 5.0f, this.paintLeftLine);
            }
            if (!TextUtils.isEmpty(this.label) && this.isCenterLabel) {
                int drawRightContentStart = this.measuredWidth - getTextWidth(this.paintCenterText, this.label);
                canvas.drawText(this.label, ((float) drawRightContentStart) - this.CENTERCONTENTOFFSET, this.centerY, this.paintCenterText);
            }
            for (int counter2 = 0; counter2 < this.itemsVisible; counter2++) {
                canvas.save();
                double radian = (double) (((this.itemHeight * ((float) counter2)) - itemHeightOffset) / ((float) this.radius));
                float angle = (float) (90.0d - ((radian / 3.141592653589793d) * 180.0d));
                if (angle >= 90.0f || angle <= -90.0f) {
                    canvas.restore();
                } else {
                    if (!this.isCenterLabel && !TextUtils.isEmpty(this.label)) {
                        if (!TextUtils.isEmpty(getContentText(visibles[counter2]))) {
                            contentText = getContentText(visibles[counter2]) + this.label;
                            reMeasureTextSize(contentText);
                            measuredCenterContentStart(contentText);
                            measuredOutContentStart(contentText);
                            translateY = (float) ((((double) this.radius) - (Math.cos(radian) * ((double) this.radius))) - ((Math.sin(radian) * ((double) this.maxTextHeight)) / 2.0d));
                            canvas.translate(0.0f, translateY);
                            canvas.scale(1.0f, (float) Math.sin(radian));
                            if (translateY > this.firstLineY && ((float) this.maxTextHeight) + translateY >= this.firstLineY) {
                                canvas.save();
                                canvas.clipRect(0.0f, 0.0f, (float) this.measuredWidth, this.firstLineY - translateY);
                                canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                                canvas.drawText(contentText, (float) this.drawOutContentStart, (float) this.maxTextHeight, this.paintOuterText);
                                canvas.restore();
                                canvas.save();
                                canvas.clipRect(0.0f, this.firstLineY - translateY, (float) this.measuredWidth, (float) ((int) this.itemHeight));
                                canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                                canvas.drawText(contentText, (float) this.drawCenterContentStart, ((float) this.maxTextHeight) - this.CENTERCONTENTOFFSET, this.paintCenterText);
                                canvas.restore();
                            } else if (translateY > this.secondLineY && ((float) this.maxTextHeight) + translateY >= this.secondLineY) {
                                canvas.save();
                                canvas.clipRect(0.0f, 0.0f, (float) this.measuredWidth, this.secondLineY - translateY);
                                canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                                canvas.drawText(contentText, (float) this.drawCenterContentStart, ((float) this.maxTextHeight) - this.CENTERCONTENTOFFSET, this.paintCenterText);
                                canvas.restore();
                                canvas.save();
                                canvas.clipRect(0.0f, this.secondLineY - translateY, (float) this.measuredWidth, (float) ((int) this.itemHeight));
                                canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                                canvas.drawText(contentText, (float) this.drawOutContentStart, (float) this.maxTextHeight, this.paintOuterText);
                                canvas.restore();
                            } else if (translateY >= this.firstLineY || ((float) this.maxTextHeight) + translateY > this.secondLineY) {
                                canvas.save();
                                canvas.clipRect(0, 0, this.measuredWidth, (int) this.itemHeight);
                                canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                                canvas.drawText(contentText, (float) this.drawOutContentStart, (float) this.maxTextHeight, this.paintOuterText);
                                canvas.restore();
                            } else {
                                float f = (float) this.drawCenterContentStart;
                                canvas.drawText(contentText, f, ((float) this.maxTextHeight) - this.CENTERCONTENTOFFSET, this.paintCenterText);
                                this.selectedItem = this.adapter.indexOf(visibles[counter2]);
                            }
                            canvas.restore();
                            this.paintCenterText.setTextSize((float) this.textSize);
                        }
                    }
                    contentText = getContentText(visibles[counter2]);
                    reMeasureTextSize(contentText);
                    measuredCenterContentStart(contentText);
                    measuredOutContentStart(contentText);
                    translateY = (float) ((((double) this.radius) - (Math.cos(radian) * ((double) this.radius))) - ((Math.sin(radian) * ((double) this.maxTextHeight)) / 2.0d));
                    canvas.translate(0.0f, translateY);
                    canvas.scale(1.0f, (float) Math.sin(radian));
                    if (translateY > this.firstLineY) {
                    }
                    if (translateY > this.secondLineY) {
                    }
                    if (translateY >= this.firstLineY) {
                    }
                    canvas.save();
                    canvas.clipRect(0, 0, this.measuredWidth, (int) this.itemHeight);
                    canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                    canvas.drawText(contentText, (float) this.drawOutContentStart, (float) this.maxTextHeight, this.paintOuterText);
                    canvas.restore();
                    canvas.restore();
                    this.paintCenterText.setTextSize((float) this.textSize);
                }
            }
        }
    }

    private void reMeasureTextSize(String contentText) {
        Rect rect = new Rect();
        this.paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
        int size = this.textSize;
        for (int width = rect.width(); width > this.measuredWidth; width = rect.width()) {
            size--;
            this.paintCenterText.setTextSize((float) size);
            this.paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
        }
        this.paintOuterText.setTextSize((float) size);
    }

    private int getLoopMappingIndex(int index) {
        if (index < 0) {
            return getLoopMappingIndex(index + this.adapter.getItemsCount());
        }
        if (index > this.adapter.getItemsCount() - 1) {
            return getLoopMappingIndex(index - this.adapter.getItemsCount());
        }
        return index;
    }

    private String getContentText(Object item) {
        if (item == null) {
            return "";
        }
        if (item instanceof IPickerViewData) {
            return ((IPickerViewData) item).getPickerViewText();
        }
        if (!(item instanceof Integer)) {
            return item.toString();
        }
        return String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(((Integer) item).intValue())});
    }

    private void measuredCenterContentStart(String content) {
        Rect rect = new Rect();
        this.paintCenterText.getTextBounds(content, 0, content.length(), rect);
        switch (this.mGravity) {
            case 3:
                this.drawCenterContentStart = 0;
                return;
            case 5:
                this.drawCenterContentStart = (this.measuredWidth - rect.width()) - ((int) this.CENTERCONTENTOFFSET);
                return;
            case 17:
                if (this.isOptions || this.label == null || this.label.equals("") || !this.isCenterLabel) {
                    this.drawCenterContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.5d);
                    return;
                } else {
                    this.drawCenterContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.25d);
                    return;
                }
            default:
                return;
        }
    }

    private void measuredOutContentStart(String content) {
        Rect rect = new Rect();
        this.paintOuterText.getTextBounds(content, 0, content.length(), rect);
        switch (this.mGravity) {
            case 3:
                this.drawOutContentStart = 0;
                return;
            case 5:
                this.drawOutContentStart = (this.measuredWidth - rect.width()) - ((int) this.CENTERCONTENTOFFSET);
                return;
            case 17:
                if (this.isOptions || this.label == null || this.label.equals("") || !this.isCenterLabel) {
                    this.drawOutContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.5d);
                    return;
                } else {
                    this.drawOutContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.25d);
                    return;
                }
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec2, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec2;
        remeasure();
        setMeasuredDimension(this.measuredWidth, this.measuredHeight);
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = this.gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case 0:
                this.startTime = System.currentTimeMillis();
                cancelFuture();
                this.previousY = event.getRawY();
                break;
            case 2:
                float dy = this.previousY - event.getRawY();
                this.previousY = event.getRawY();
                this.totalScrollY += dy;
                if (!this.isLoop) {
                    float top = ((float) (-this.initPosition)) * this.itemHeight;
                    float bottom = ((float) ((this.adapter.getItemsCount() - 1) - this.initPosition)) * this.itemHeight;
                    if (((double) this.totalScrollY) - (((double) this.itemHeight) * 0.25d) < ((double) top)) {
                        top = this.totalScrollY - dy;
                    } else if (((double) this.totalScrollY) + (((double) this.itemHeight) * 0.25d) > ((double) bottom)) {
                        bottom = this.totalScrollY - dy;
                    }
                    if (this.totalScrollY >= top) {
                        if (this.totalScrollY > bottom) {
                            this.totalScrollY = (float) ((int) bottom);
                            break;
                        }
                    } else {
                        this.totalScrollY = (float) ((int) top);
                        break;
                    }
                }
                break;
            default:
                if (!eventConsumed) {
                    float extraOffset = ((this.totalScrollY % this.itemHeight) + this.itemHeight) % this.itemHeight;
                    this.mOffset = (int) ((((float) (((int) ((((double) (this.itemHeight / 2.0f)) + (Math.acos((double) ((((float) this.radius) - event.getY()) / ((float) this.radius))) * ((double) this.radius))) / ((double) this.itemHeight))) - (this.itemsVisible / 2))) * this.itemHeight) - extraOffset);
                    if (System.currentTimeMillis() - this.startTime <= 120) {
                        smoothScroll(ACTION.CLICK);
                        break;
                    } else {
                        smoothScroll(ACTION.DAGGLE);
                        break;
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    public int getItemsCount() {
        if (this.adapter != null) {
            return this.adapter.getItemsCount();
        }
        return 0;
    }

    public void setLabel(String label2) {
        this.label = label2;
    }

    public void isCenterLabel(Boolean isCenterLabel2) {
        this.isCenterLabel = isCenterLabel2.booleanValue();
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil((double) widths[j]);
            }
        }
        return iRet;
    }

    public void setIsOptions(boolean options) {
        this.isOptions = options;
    }

    public void setTextColorOut(int textColorOut2) {
        if (textColorOut2 != 0) {
            this.textColorOut = textColorOut2;
            this.paintOuterText.setColor(this.textColorOut);
        }
    }

    public void setTextColorCenter(int textColorCenter2) {
        if (textColorCenter2 != 0) {
            this.textColorCenter = textColorCenter2;
            this.paintCenterText.setColor(this.textColorCenter);
        }
    }

    public void setDividerColor(int dividerColor2) {
        if (dividerColor2 != 0) {
            this.dividerColor = dividerColor2;
            this.paintIndicator.setColor(this.dividerColor);
        }
    }

    public void setDividerType(DividerType dividerType2) {
        this.dividerType = dividerType2;
    }

    public void setLineSpacingMultiplier(float lineSpacingMultiplier2) {
        if (lineSpacingMultiplier2 != 0.0f) {
            this.lineSpacingMultiplier = lineSpacingMultiplier2;
            judgeLineSpae();
        }
    }
}
