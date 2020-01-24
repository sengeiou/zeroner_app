package com.iwown.my_module.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class WangWheelView extends ScrollView {
    public static final int OFF_SET_DEFAULT = 1;
    private static final int SCROLL_DIRECTION_DOWN = 1;
    private static final int SCROLL_DIRECTION_UP = 0;
    public static final String TAG = WangWheelView.class.getSimpleName();
    private Context context;
    int displayItemCount;
    int initialY;
    int itemHeight = 0;
    List<String> items;
    int newCheck = 50;
    int offset = 1;
    private OnWheelViewListener onWheelViewListener;
    Paint paint;
    private int scrollDirection = -1;
    Runnable scrollerTask;
    int[] selectedAreaBorder;
    int selectedIndex = 1;
    int viewWidth;
    private LinearLayout views;

    public static class OnWheelViewListener {
        public void onSelected(int selectedIndex, String item) {
        }
    }

    public WangWheelView(Context context2) {
        super(context2);
        init(context2);
    }

    public WangWheelView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        init(context2);
    }

    public WangWheelView(Context context2, AttributeSet attrs, int defStyle) {
        super(context2, attrs, defStyle);
        init(context2);
    }

    private List<String> getItems() {
        return this.items;
    }

    public void setItems(List<String> list) {
        if (this.items == null) {
            this.items = new ArrayList();
        }
        this.items.clear();
        this.items.addAll(list);
        for (int i = 0; i < this.offset; i++) {
            this.items.add(0, "");
            this.items.add("");
        }
        initData();
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset2) {
        this.offset = offset2;
    }

    private void init(Context context2) {
        this.context = context2;
        setVerticalScrollBarEnabled(false);
        this.views = new LinearLayout(context2);
        this.views.setOrientation(1);
        addView(this.views);
        this.scrollerTask = new Runnable() {
            public void run() {
                if (WangWheelView.this.initialY - WangWheelView.this.getScrollY() == 0) {
                    final int remainder = WangWheelView.this.initialY % WangWheelView.this.itemHeight;
                    final int divided = WangWheelView.this.initialY / WangWheelView.this.itemHeight;
                    if (remainder == 0) {
                        WangWheelView.this.selectedIndex = WangWheelView.this.offset + divided;
                        WangWheelView.this.onSeletedCallBack();
                    } else if (remainder > WangWheelView.this.itemHeight / 2) {
                        WangWheelView.this.post(new Runnable() {
                            public void run() {
                                WangWheelView.this.smoothScrollTo(0, (WangWheelView.this.initialY - remainder) + WangWheelView.this.itemHeight);
                                WangWheelView.this.selectedIndex = divided + WangWheelView.this.offset + 1;
                                WangWheelView.this.onSeletedCallBack();
                            }
                        });
                    } else {
                        WangWheelView.this.post(new Runnable() {
                            public void run() {
                                WangWheelView.this.smoothScrollTo(0, WangWheelView.this.initialY - remainder);
                                WangWheelView.this.selectedIndex = divided + WangWheelView.this.offset;
                                WangWheelView.this.onSeletedCallBack();
                            }
                        });
                    }
                } else {
                    WangWheelView.this.initialY = WangWheelView.this.getScrollY();
                    WangWheelView.this.postDelayed(WangWheelView.this.scrollerTask, (long) WangWheelView.this.newCheck);
                }
            }
        };
    }

    public void startScrollerTask() {
        this.initialY = getScrollY();
        postDelayed(this.scrollerTask, (long) this.newCheck);
    }

    private void initData() {
        this.displayItemCount = (this.offset * 2) + 1;
        this.views.removeAllViews();
        for (String item : this.items) {
            this.views.addView(createView(item));
        }
        refreshItemView(0);
    }

    private TextView createView(String item) {
        TextView tv = new TextView(this.context);
        tv.setLayoutParams(new LayoutParams(-1, -2));
        tv.setSingleLine(true);
        tv.setTextSize(2, 20.0f);
        tv.setText(item);
        tv.setGravity(17);
        int padding = dip2px(15.0f);
        tv.setPadding(padding, padding, padding, padding);
        if (this.itemHeight == 0) {
            this.itemHeight = getViewMeasuredHeight(tv);
            this.views.setLayoutParams(new LayoutParams(-1, this.itemHeight * this.displayItemCount));
            setLayoutParams(new LinearLayout.LayoutParams(((LinearLayout.LayoutParams) getLayoutParams()).width, this.itemHeight * this.displayItemCount));
        }
        return tv;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemView(t);
        if (t > oldt) {
            this.scrollDirection = 1;
        } else {
            this.scrollDirection = 0;
        }
    }

    private void refreshItemView(int y) {
        int position = (y / this.itemHeight) + this.offset;
        int remainder = y % this.itemHeight;
        int divided = y / this.itemHeight;
        if (remainder == 0) {
            position = divided + this.offset;
        } else if (remainder > this.itemHeight / 2) {
            position = this.offset + divided + 1;
        }
        int childSize = this.views.getChildCount();
        int i = 0;
        while (i < childSize) {
            TextView itemView = (TextView) this.views.getChildAt(i);
            if (itemView != null) {
                if (position == i) {
                    itemView.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    itemView.setTextColor(Color.parseColor("#7B859E"));
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public int[] obtainSelectedAreaBorder() {
        if (this.selectedAreaBorder == null) {
            this.selectedAreaBorder = new int[2];
            this.selectedAreaBorder[0] = this.itemHeight * this.offset;
            this.selectedAreaBorder[1] = this.itemHeight * (this.offset + 1);
        }
        return this.selectedAreaBorder;
    }

    public void setBackgroundDrawable(Drawable background) {
        if (this.viewWidth == 0) {
            this.viewWidth = ((Activity) this.context).getWindowManager().getDefaultDisplay().getWidth();
        }
        if (this.paint == null) {
            this.paint = new Paint();
            this.paint.setColor(Color.parseColor("#425070"));
            this.paint.setStrokeWidth((float) dip2px(1.0f));
        }
        super.setBackgroundDrawable(new Drawable() {
            public void draw(Canvas canvas) {
                canvas.drawLine((float) ((WangWheelView.this.viewWidth * 1) / 6), (float) WangWheelView.this.obtainSelectedAreaBorder()[0], (float) ((WangWheelView.this.viewWidth * 5) / 6), (float) WangWheelView.this.obtainSelectedAreaBorder()[0], WangWheelView.this.paint);
                canvas.drawLine((float) ((WangWheelView.this.viewWidth * 1) / 6), (float) WangWheelView.this.obtainSelectedAreaBorder()[1], (float) ((WangWheelView.this.viewWidth * 5) / 6), (float) WangWheelView.this.obtainSelectedAreaBorder()[1], WangWheelView.this.paint);
            }

            public void setAlpha(int alpha) {
            }

            public void setColorFilter(ColorFilter cf) {
            }

            public int getOpacity() {
                return 0;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        setBackgroundDrawable(null);
    }

    /* access modifiers changed from: private */
    public void onSeletedCallBack() {
        if (this.onWheelViewListener != null) {
            this.onWheelViewListener.onSelected(this.selectedIndex, (String) this.items.get(this.selectedIndex));
        }
    }

    public void setSeletion(int position) {
        boolean isSameIndex;
        if (this.selectedIndex == this.offset + position) {
            isSameIndex = true;
        } else {
            isSameIndex = false;
        }
        final int p = position;
        this.selectedIndex = this.offset + p;
        post(new Runnable() {
            public void run() {
                WangWheelView.this.smoothScrollTo(0, p * WangWheelView.this.itemHeight);
            }
        });
        if (isSameIndex) {
            Log.i(TAG, String.format("manual refereshItemview, position:%d,offset:%d", new Object[]{Integer.valueOf(position), Integer.valueOf(this.offset)}));
            refreshItemView(this.itemHeight * p);
        }
    }

    public String getSeletedItem() {
        return (String) this.items.get(this.selectedIndex);
    }

    public int getSeletedIndex() {
        return this.selectedIndex - this.offset;
    }

    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 1) {
            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    public OnWheelViewListener getOnWheelViewListener() {
        return this.onWheelViewListener;
    }

    public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener2) {
        this.onWheelViewListener = onWheelViewListener2;
    }

    private int dip2px(float dpValue) {
        return (int) ((dpValue * this.context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private int getViewMeasuredHeight(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
        return view.getMeasuredHeight();
    }
}
