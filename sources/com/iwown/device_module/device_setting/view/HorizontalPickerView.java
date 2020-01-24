package com.iwown.device_module.device_setting.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.device_module.R;
import java.util.ArrayList;
import java.util.List;

public class HorizontalPickerView extends HorizontalScrollView implements OnClickListener {
    private static final String TAG = HorizontalPickerView.class.getSimpleName();
    private int HorizontalPickerView_selectedTextColor;
    private int HorizontalPickerView_textColor;
    private float HorizontalPickerView_textSize;
    private Context context;
    /* access modifiers changed from: private */
    public CurrentItemChangeListener currentItemChangeListener;
    /* access modifiers changed from: private */
    public LinearLayout linearLayout;
    private List<String> mData;
    private OnItemClickListener onItemClickListener;
    /* access modifiers changed from: private */
    public int position;
    /* access modifiers changed from: private */
    public int scrollX;

    public interface CurrentItemChangeListener {
        void onCurrentItemChanged(View view, int i);

        void onScrollChangedFinish(View view, int i);
    }

    public interface OnItemClickListener {
        void onClick(View view, int i);
    }

    public HorizontalPickerView(Context context2) {
        this(context2, null);
    }

    public HorizontalPickerView(Context context2, AttributeSet attrs) {
        this(context2, attrs, 0);
    }

    /* JADX INFO: finally extract failed */
    public HorizontalPickerView(Context context2, AttributeSet attrs, int defStyleAttr) {
        super(context2, attrs, defStyleAttr);
        this.mData = new ArrayList();
        this.context = context2;
        setOverScrollMode(2);
        TypedArray a = context2.obtainStyledAttributes(attrs, R.styleable.device_module_HorizontalPickerView, defStyleAttr, 0);
        try {
            this.HorizontalPickerView_textSize = a.getDimension(R.styleable.device_module_HorizontalPickerView_device_module_pv_textSize, 16.0f);
            this.HorizontalPickerView_selectedTextColor = a.getColor(R.styleable.device_module_HorizontalPickerView_device_module_pv_selectedTextColor, -16711936);
            this.HorizontalPickerView_textColor = a.getColor(R.styleable.device_module_HorizontalPickerView_device_module_pv_textColor, -7829368);
            a.recycle();
            init();
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollX2 = getScrollX();
        for (int i = 1; i < this.linearLayout.getChildCount() - 1; i++) {
            TextView textview = (TextView) this.linearLayout.getChildAt(i);
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            if (textview.getLeft() - scrollX2 > screenWidth / 2 || screenWidth / 2 >= (textview.getLeft() + textview.getWidth()) - scrollX2) {
                textview.setTextColor(this.HorizontalPickerView_textColor);
            } else {
                this.position = i - 1;
                textview.setTextColor(this.HorizontalPickerView_selectedTextColor);
                if (this.currentItemChangeListener != null) {
                    this.currentItemChangeListener.onCurrentItemChanged(textview, this.position);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 1) {
            this.scrollX = getScrollX();
            postDelayed(new Runnable() {
                public void run() {
                    if (HorizontalPickerView.this.scrollX == HorizontalPickerView.this.getScrollX()) {
                        View view = HorizontalPickerView.this.linearLayout.getChildAt(HorizontalPickerView.this.position + 1);
                        HorizontalPickerView.this.scrollto(view);
                        if (HorizontalPickerView.this.currentItemChangeListener != null) {
                            HorizontalPickerView.this.currentItemChangeListener.onScrollChangedFinish(view, HorizontalPickerView.this.position);
                            return;
                        }
                        return;
                    }
                    HorizontalPickerView.this.scrollX = HorizontalPickerView.this.getScrollX();
                    HorizontalPickerView.this.post(this);
                }
            }, 30);
        }
        return super.onTouchEvent(ev);
    }

    private void init() {
        this.linearLayout = new LinearLayout(this.context);
        this.linearLayout.setOrientation(0);
        this.linearLayout.setGravity(17);
        addView(this.linearLayout, new LayoutParams(-2, -2));
    }

    public void setData(List<String> data) {
        this.mData.clear();
        if (data != null) {
            this.mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public final void notifyDataSetChanged() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        this.linearLayout.removeAllViews();
        for (int i = 0; i < this.mData.size(); i++) {
            TextView view = createTextView("" + ((String) this.mData.get(i)));
            view.setTag(Integer.valueOf(i));
            this.linearLayout.addView(view);
            if (i == 0) {
                this.linearLayout.addView(createView(screenWidth / 2), 0);
            }
            if (i == this.mData.size() - 1) {
                this.linearLayout.addView(createView(screenWidth / 2));
            }
        }
    }

    private TextView createTextView(@NonNull String text) {
        TextView textView = new TextView(this.context);
        textView.setText(text);
        textView.setTextColor(this.HorizontalPickerView_textColor);
        textView.setTextSize(this.HorizontalPickerView_textSize);
        textView.setPadding(getResources().getDimensionPixelOffset(R.dimen.device_module_pv_horizontalpickerview_pad_left), 0, getResources().getDimensionPixelOffset(R.dimen.device_module_pv_horizontalpickerview_pad_left), 0);
        textView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        textView.setOnClickListener(this);
        return textView;
    }

    private View createView(int width) {
        View view = new View(this.context);
        view.setLayoutParams(new LinearLayout.LayoutParams(width, -2));
        return view;
    }

    public void onClick(View view) {
        scrollto(view);
        this.position = ((Integer) view.getTag()).intValue();
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onClick(view, this.position);
        }
        if (this.currentItemChangeListener != null) {
            this.currentItemChangeListener.onScrollChangedFinish(view, this.position);
        }
    }

    /* access modifiers changed from: private */
    public void scrollto(View view) {
        if (view != null) {
            int itemWidth = view.getWidth();
            smoothScrollTo(view.getLeft() - ((getResources().getDisplayMetrics().widthPixels / 2) - (itemWidth / 2)), 0);
        }
    }

    public void scrollto(final int arg) {
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (arg < HorizontalPickerView.this.linearLayout.getChildCount() - 2) {
                    HorizontalPickerView.this.scrollto(HorizontalPickerView.this.linearLayout.getChildAt(arg + 1));
                }
                HorizontalPickerView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void setCurrentItemChangeListener(CurrentItemChangeListener currentItemChangeListener2) {
        this.currentItemChangeListener = currentItemChangeListener2;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }
}
