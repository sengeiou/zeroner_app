package com.iwown.my_module.widget;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.iwown.my_module.R;
import com.tencent.tinker.android.dx.instruction.Opcodes;

public class MyEditTex extends RelativeLayout {
    /* access modifiers changed from: private */
    public boolean hasContent;
    private Context mContext;
    /* access modifiers changed from: private */
    public EditText mEdt;
    private ImageView mImg;
    /* access modifiers changed from: private */
    public OnFocusChangedListener mOnFocusChangedListener;
    /* access modifiers changed from: private */
    public OnTextChangedListener mOnTextChangedListener;
    /* access modifiers changed from: private */
    public View mUderLine;
    /* access modifiers changed from: private */
    public int uderLineNoFocusColorRes;
    /* access modifiers changed from: private */
    public int uderLineWhenFocusColorRes;

    public interface OnFocusChangedListener {
        void onFocusChanged(View view, boolean z);
    }

    public interface OnTextChangedListener {
        void onTextChanged(CharSequence charSequence, int i, int i2, int i3);
    }

    public boolean isHasContent() {
        return this.hasContent;
    }

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.mOnTextChangedListener = onTextChangedListener;
    }

    public void setOnFocusChangedListener(OnFocusChangedListener onFocusChangedListener) {
        this.mOnFocusChangedListener = onFocusChangedListener;
    }

    public MyEditTex(Context context) {
        this(context, null);
    }

    public MyEditTex(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.hasContent = false;
        this.mContext = context;
        initView(context, attrs);
        initEvent();
    }

    private void initEvent() {
        this.mImg.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MyEditTex.this.mEdt.getInputType() == 129) {
                    MyEditTex.this.mEdt.setInputType(Opcodes.SUB_INT);
                } else {
                    MyEditTex.this.mEdt.setInputType(129);
                }
            }
        });
        this.mEdt.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    MyEditTex.this.mUderLine.setBackgroundResource(MyEditTex.this.uderLineWhenFocusColorRes);
                } else {
                    MyEditTex.this.mUderLine.setBackgroundResource(MyEditTex.this.uderLineNoFocusColorRes);
                }
                if (MyEditTex.this.mOnFocusChangedListener != null) {
                    MyEditTex.this.mOnFocusChangedListener.onFocusChanged(MyEditTex.this.mEdt, hasFocus);
                }
            }
        });
        this.mEdt.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    MyEditTex.this.hasContent = true;
                } else {
                    MyEditTex.this.hasContent = false;
                }
                if (MyEditTex.this.mOnTextChangedListener != null) {
                    MyEditTex.this.mOnTextChangedListener.onTextChanged(s, start, before, count);
                }
            }

            public void afterTextChanged(Editable s) {
                int index = MyEditTex.this.mEdt.getSelectionStart() - 1;
                if (index > 0 && MyEditTex.isEmojiCharacter(s.charAt(index))) {
                    MyEditTex.this.mEdt.getText().delete(index, index + 1);
                }
            }
        });
    }

    private void initView(Context context, AttributeSet attrs) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.my_module_my_edittext_layout, this);
        this.mImg = (ImageView) findViewById(R.id.img);
        this.mImg.setPadding(5, 5, 5, 5);
        this.mEdt = (EditText) findViewById(R.id.edt);
        this.mUderLine = findViewById(R.id.edt_under_line);
    }

    public void setEdtHint(int res) {
        SpannableString hintStr = new SpannableString(getResources().getString(res));
        hintStr.setSpan(new AbsoluteSizeSpan(12, true), 0, hintStr.length(), 33);
        this.mEdt.setHint(new SpannedString(hintStr));
    }

    public void setEdtHintColor(int hintColor) {
        this.mEdt.setHintTextColor(hintColor);
    }

    public void setEdtText(int res) {
        this.mEdt.setText(res);
    }

    public void setEdtTextColor(int color) {
        this.mEdt.setTextColor(color);
    }

    /* access modifiers changed from: private */
    public static boolean isEmojiCharacter(char codePoint) {
        return !(codePoint == 0 || codePoint == 9 || codePoint == 10 || codePoint == 13 || (codePoint >= ' ' && codePoint <= 55295)) || (codePoint >= 57344 && codePoint <= 65533) || (codePoint >= 0 && codePoint <= 65535);
    }

    public void setRightImgVisible(boolean visible) {
        if (visible) {
            this.mImg.setVisibility(0);
        } else {
            this.mImg.setVisibility(8);
        }
    }

    public void setUderLineColorWhenChg(int colorResWhenEdtFocus, int colorResEdtNoFocus) {
        this.uderLineNoFocusColorRes = colorResEdtNoFocus;
        this.uderLineWhenFocusColorRes = colorResWhenEdtFocus;
    }

    public void setEdtInputType(int inputType) {
        this.mEdt.setInputType(inputType);
    }

    public String getText() {
        return this.mEdt.getText().toString();
    }

    public void setText(String text) {
        this.mEdt.setText(text);
    }
}
