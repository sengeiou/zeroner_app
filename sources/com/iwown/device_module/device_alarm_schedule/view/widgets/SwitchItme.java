package com.iwown.device_module.device_alarm_schedule.view.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.device_module.device_alarm_schedule.view.widgets.ShSwitchView.OnSwitchStateChangeListener;
import com.socks.library.KLog;

public class SwitchItme extends RelativeLayout {
    private RelativeLayout container_rl;
    private float downX = 0.0f;
    private TextView mContent;
    private View mDivideLine;
    private GestureDetector mGestureDetector = null;
    private ImageView mIconImag;
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;
    /* access modifiers changed from: private */
    public OnRightImgClickListener mOnRightImgClickListener;
    /* access modifiers changed from: private */
    public OnSwitchChangedListener mOnSwitchChangedListener;
    private ImageView mRightImag;
    private ShSwitchView mSwitchBtn;
    private TextView mTitle;
    private float upX = 0.0f;

    private class MyOnGestureListener extends SimpleOnGestureListener {
        private MyOnGestureListener() {
        }

        public void onShowPress(MotionEvent e) {
            KLog.e("licl", "onShowPress");
            if (SwitchItme.this.mOnItemClickListener != null) {
                SwitchItme.this.mOnItemClickListener.onItemClick(SwitchItme.this);
            }
            super.onShowPress(e);
        }

        public boolean onSingleTapUp(MotionEvent e) {
            KLog.e("licl", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        public void onLongPress(MotionEvent e) {
            KLog.e("licl", "onLongPress");
            super.onLongPress(e);
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            KLog.e("licl", "onScroll");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            KLog.e("licl", "onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        public boolean onDown(MotionEvent e) {
            KLog.e("licl", "onDown");
            return super.onDown(e);
        }

        public boolean onDoubleTap(MotionEvent e) {
            KLog.e("licl", "onDoubleTap");
            return super.onDoubleTap(e);
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            KLog.e("licl", "onDoubleTapEvent");
            return super.onDoubleTapEvent(e);
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            KLog.e("licl", "onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public interface OnRightImgClickListener {
        void onRightImgClicked(View view);
    }

    public interface OnSwitchChangedListener {
        void onSwitchChanged(boolean z);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnSwitchChangedListener(OnSwitchChangedListener onSwitchChangedListener) {
        this.mOnSwitchChangedListener = onSwitchChangedListener;
    }

    public void setOnRightImgClickListener(OnRightImgClickListener onRightImgClickListener) {
        this.mOnRightImgClickListener = onRightImgClickListener;
    }

    public SwitchItme(Context context) {
        super(context);
    }

    public SwitchItme(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mGestureDetector = new GestureDetector(context, new MyOnGestureListener());
        initView(context, attrs);
        initEvent();
    }

    private void initEvent() {
        this.mRightImag.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (SwitchItme.this.mOnRightImgClickListener != null) {
                    SwitchItme.this.mOnRightImgClickListener.onRightImgClicked(v);
                }
            }
        });
    }

    private void initView(Context context, AttributeSet attrs) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.device_module_switch_item_layout, this);
        this.mTitle = (TextView) findViewById(R.id.title_tv);
        this.mContent = (TextView) findViewById(R.id.content_tv);
        this.mSwitchBtn = (ShSwitchView) findViewById(R.id.switch_btn);
        this.mRightImag = (ImageView) findViewById(R.id.right_imag);
        this.mIconImag = (ImageView) findViewById(R.id.icon_imag);
        this.mDivideLine = findViewById(R.id.divide_line);
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.device_module_device_setting_switch_item);
        int titleRes = a.getResourceId(R.styleable.device_module_device_setting_switch_item_device_module_title_switch_item, -1);
        int contentRes = a.getResourceId(R.styleable.device_module_device_setting_switch_item_device_module_content_switch_item, -1);
        boolean isContentVisible = a.getBoolean(R.styleable.device_module_device_setting_switch_item_device_module_is_content_visible, true);
        boolean showDivide = a.getBoolean(R.styleable.device_module_device_setting_switch_item_device_module_show_divide_line, true);
        a.recycle();
        this.mTitle.setText(titleRes);
        this.mContent.setText(contentRes);
        if (isContentVisible) {
            this.mContent.setVisibility(0);
        } else {
            this.mContent.setVisibility(8);
        }
        this.mSwitchBtn.setOnSwitchStateChangeListener(new OnSwitchStateChangeListener() {
            public void onSwitchStateChange(boolean isOn) {
                if (SwitchItme.this.mOnSwitchChangedListener != null) {
                    SwitchItme.this.mOnSwitchChangedListener.onSwitchChanged(isOn);
                }
            }
        });
        this.mDivideLine.setVisibility(showDivide ? 0 : 8);
    }

    public void setOn(boolean isOn) {
        this.mSwitchBtn.setOn(isOn);
    }

    public boolean isOn() {
        return this.mSwitchBtn.isOn();
    }

    public void setSwitchBtnCanChanged(boolean canChanged) {
        this.mSwitchBtn.setOnchange(canChanged);
    }

    public void setSwitchVisible(boolean isVisible) {
        this.mSwitchBtn.setVisibility(isVisible ? 0 : 8);
    }

    public void setDivideLineVisible(boolean isVisible) {
        this.mDivideLine.setVisibility(isVisible ? 0 : 8);
    }

    public void setRightImagVisible(boolean isVisible) {
        this.mRightImag.setVisibility(isVisible ? 0 : 8);
    }

    public void setIconImagVisible(boolean isVisible) {
        this.mIconImag.setVisibility(isVisible ? 0 : 8);
    }

    public void setIconDrawble(Drawable drawable) {
        this.mIconImag.setImageDrawable(drawable);
    }

    public void setContent(int resID) {
        this.mContent.setText(resID);
    }

    public void setContent(String content) {
        this.mContent.setText(content);
    }

    public void setCotentVisible(boolean isVisible) {
        this.mContent.setVisibility(isVisible ? 0 : 8);
    }

    public void setTitle(String title) {
        this.mTitle.setText(title);
    }

    public void setTitleSize(float sizeDP) {
        this.mTitle.setTextSize(1, sizeDP);
    }

    public void setTitleFont(Typeface font) {
        this.mTitle.setTypeface(font);
    }
}
