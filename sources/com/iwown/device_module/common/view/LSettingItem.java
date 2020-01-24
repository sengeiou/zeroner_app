package com.iwown.device_module.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.iwown.device_module.R;

public class LSettingItem extends RelativeLayout {
    private ImageView firmware;
    private boolean mChecked;
    private ImageView mIvLeftIcon;
    private ImageView mIvRightIcon;
    private Drawable mLeftIcon;
    private int mLeftIconSzie;
    private String mLeftText;
    /* access modifiers changed from: private */
    public OnLSettingItemClick mOnLSettingItemClick;
    private Drawable mRightIcon;
    private AppCompatCheckBox mRightIcon_check;
    private SwitchCompat mRightIcon_switch;
    private FrameLayout mRightLayout;
    private int mRightStyle;
    private int mRightTextColor;
    private float mRightTextSize;
    private RelativeLayout mRootLayout;
    private int mTextColor;
    private int mTextSize;
    private TextView mTvLeftText;
    private TextView mTvRightText;
    private View mUnderLine;
    private View mView;

    public interface OnLSettingItemClick {
        void click(int i, boolean z);
    }

    public LSettingItem(Context context) {
        this(context, null);
    }

    public LSettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LSettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mRightStyle = 0;
        initView(context);
        getCustomStyle(context, attrs);
        switchRightStyle(this.mRightStyle);
        this.mRootLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LSettingItem.this.clickOn(v.getId());
            }
        });
        this.mRightIcon_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (LSettingItem.this.mOnLSettingItemClick != null) {
                    LSettingItem.this.mOnLSettingItemClick.click(buttonView.getId(), isChecked);
                }
            }
        });
        this.mRightIcon_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (LSettingItem.this.mOnLSettingItemClick != null) {
                    LSettingItem.this.mOnLSettingItemClick.click(buttonView.getId(), isChecked);
                }
            }
        });
    }

    public void setmOnLSettingItemClick(OnLSettingItemClick mOnLSettingItemClick2) {
        this.mOnLSettingItemClick = mOnLSettingItemClick2;
    }

    public void getCustomStyle(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.device_module_LSettingView);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.device_module_LSettingView_device_module_leftText) {
                this.mLeftText = a.getString(attr);
                this.mTvLeftText.setText(this.mLeftText);
            } else if (attr == R.styleable.device_module_LSettingView_device_module_leftIcon) {
                this.mLeftIcon = a.getDrawable(attr);
                if (this.mLeftIcon != null) {
                    this.mIvLeftIcon.setImageDrawable(this.mLeftIcon);
                    this.mIvLeftIcon.setVisibility(0);
                }
            } else if (attr == R.styleable.device_module_LSettingView_device_module_leftIconSize) {
                this.mLeftIconSzie = (int) a.getDimension(attr, 16.0f);
                LayoutParams layoutParams = (LayoutParams) this.mIvLeftIcon.getLayoutParams();
                layoutParams.width = this.mLeftIconSzie;
                layoutParams.height = this.mLeftIconSzie;
                this.mIvLeftIcon.setLayoutParams(layoutParams);
            } else if (attr == R.styleable.device_module_LSettingView_device_module_leftTextMarginLeft) {
                LayoutParams layoutParams2 = (LayoutParams) this.mTvLeftText.getLayoutParams();
                layoutParams2.leftMargin = (int) a.getDimension(attr, 8.0f);
                this.mTvLeftText.setLayoutParams(layoutParams2);
            } else if (attr == R.styleable.device_module_LSettingView_device_module_rightIcon) {
                this.mRightIcon = a.getDrawable(attr);
                this.mIvRightIcon.setImageDrawable(this.mRightIcon);
            } else if (attr == R.styleable.device_module_LSettingView_device_module_LtextSize) {
                this.mTvLeftText.setTextSize(a.getFloat(attr, 16.0f));
            } else if (attr == R.styleable.device_module_LSettingView_device_module_LtextColor) {
                this.mTextColor = a.getColor(attr, -3355444);
                this.mTvLeftText.setTextColor(this.mTextColor);
            } else if (attr == R.styleable.device_module_LSettingView_device_module_rightStyle) {
                this.mRightStyle = a.getInt(attr, 0);
            } else if (attr == R.styleable.device_module_LSettingView_device_module_isShowUnderLine) {
                if (!a.getBoolean(attr, true)) {
                    this.mUnderLine.setVisibility(8);
                }
            } else if (attr == R.styleable.device_module_LSettingView_device_module_isShowRightText) {
                if (a.getBoolean(attr, false)) {
                    this.mTvRightText.setVisibility(0);
                }
            } else if (attr == R.styleable.device_module_LSettingView_device_module_rightText) {
                this.mTvRightText.setText(a.getString(attr));
            } else if (attr == R.styleable.device_module_LSettingView_device_module_rightTextSize) {
                this.mRightTextSize = a.getFloat(attr, 14.0f);
                this.mTvRightText.setTextSize(this.mRightTextSize);
            } else if (attr == R.styleable.device_module_LSettingView_device_module_rightTextColor) {
                this.mRightTextColor = a.getColor(attr, -7829368);
                this.mTvRightText.setTextColor(this.mRightTextColor);
            }
        }
        a.recycle();
    }

    private void switchRightStyle(int mRightStyle2) {
        switch (mRightStyle2) {
            case 0:
                this.mIvRightIcon.setVisibility(0);
                this.mRightIcon_check.setVisibility(8);
                this.mRightIcon_switch.setVisibility(8);
                return;
            case 1:
                this.mRightLayout.setVisibility(4);
                return;
            case 2:
                this.mIvRightIcon.setVisibility(8);
                this.mRightIcon_check.setVisibility(0);
                this.mRightIcon_switch.setVisibility(8);
                return;
            case 3:
                this.mIvRightIcon.setVisibility(8);
                this.mRightIcon_check.setVisibility(8);
                this.mRightIcon_switch.setVisibility(0);
                return;
            default:
                return;
        }
    }

    private void initView(Context context) {
        this.mView = View.inflate(context, R.layout.device_module_settingitem, this);
        this.mRootLayout = (RelativeLayout) this.mView.findViewById(R.id.rootLayout);
        this.mUnderLine = this.mView.findViewById(R.id.underline);
        this.mTvLeftText = (TextView) this.mView.findViewById(R.id.tv_lefttext);
        this.mTvRightText = (TextView) this.mView.findViewById(R.id.tv_righttext);
        this.mIvLeftIcon = (ImageView) this.mView.findViewById(R.id.iv_lefticon);
        this.mIvRightIcon = (ImageView) this.mView.findViewById(R.id.iv_righticon);
        this.mRightLayout = (FrameLayout) this.mView.findViewById(R.id.rightlayout);
        this.mRightIcon_check = (AppCompatCheckBox) this.mView.findViewById(R.id.rightcheck);
        this.mRightIcon_switch = (SwitchCompat) this.mView.findViewById(R.id.rightswitch);
        this.firmware = (ImageView) this.mView.findViewById(R.id.firmware_tag);
    }

    public void clickOn(int id) {
        boolean z = true;
        switch (this.mRightStyle) {
            case 0:
            case 1:
                if (this.mOnLSettingItemClick != null) {
                    this.mOnLSettingItemClick.click(id, this.mChecked);
                    return;
                }
                return;
            case 2:
                AppCompatCheckBox appCompatCheckBox = this.mRightIcon_check;
                if (this.mRightIcon_check.isChecked()) {
                    z = false;
                }
                appCompatCheckBox.setChecked(z);
                this.mChecked = this.mRightIcon_check.isChecked();
                return;
            case 3:
                SwitchCompat switchCompat = this.mRightIcon_switch;
                if (this.mRightIcon_switch.isChecked()) {
                    z = false;
                }
                switchCompat.setChecked(z);
                this.mChecked = this.mRightIcon_check.isChecked();
                return;
            default:
                return;
        }
    }

    public RelativeLayout getmRootLayout() {
        return this.mRootLayout;
    }

    public void setLeftText(String info) {
        this.mTvLeftText.setText(info);
    }

    public void setRightText(String info) {
        this.mTvRightText.setText(info);
    }

    public void setFirmwareTag(boolean tag) {
        if (tag) {
            this.firmware.setVisibility(0);
        } else {
            this.firmware.setVisibility(8);
        }
    }

    public void setChecked(boolean checked) {
        this.mRightIcon_switch.setChecked(checked);
    }
}
