package com.iwown.my_module.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.iwown.my_module.R;
import java.util.regex.Pattern;

public class EditTextDialog extends AbsCustomDialog implements OnClickListener {
    private Button mBtnSave;
    private int mMaxLength = 20;
    /* access modifiers changed from: private */
    public EditText mNameEdt;
    private String mTextString;
    private OnTextConfirmListener onConfirmListener;
    private Handler uiHander = new Handler() {
        public void handleMessage(Message msg) {
            EditTextDialog.this.mNameEdt.setFocusable(true);
            EditTextDialog.this.mNameEdt.setFocusableInTouchMode(true);
            EditTextDialog.this.mNameEdt.requestFocus();
            ((InputMethodManager) EditTextDialog.this.mNameEdt.getContext().getSystemService("input_method")).showSoftInput(EditTextDialog.this.mNameEdt, 0);
        }
    };

    public interface OnTextConfirmListener {
        void OnConfirm(String str);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public EditTextDialog(Context context, String text) {
        super(context);
        this.mTextString = text;
    }

    public void initView() {
        this.mBtnSave = (Button) findViewById(R.id.btn_save);
        this.mNameEdt = (EditText) findViewById(R.id.name_edt);
        this.mBtnSave.setOnClickListener(this);
        this.mNameEdt.setFilters(new InputFilter[]{new LengthFilter(this.mMaxLength)});
    }

    public void initData() {
    }

    public void initListener() {
        this.mBtnSave.setOnClickListener(this);
    }

    public int getWindowAnimationsResId() {
        return R.style.BottomDialogAnim;
    }

    public int getLayoutResID() {
        return R.layout.my_module_view_nickname_dialog;
    }

    public int getWidth() {
        return -1;
    }

    public int getHeight() {
        return -2;
    }

    public int getGravity() {
        return 81;
    }

    public void setOnTextConfirmListener(OnTextConfirmListener onConfirmListener2) {
        this.onConfirmListener = onConfirmListener2;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            this.mTextString = this.mNameEdt.getText().toString().trim();
            if (this.onConfirmListener != null) {
                this.onConfirmListener.OnConfirm(this.mTextString);
            }
            dismiss();
        }
    }

    public boolean stringFilter(String str) {
        return Pattern.compile("^[一-龥a-zA-Z0-9]+$").matcher(str).matches();
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            this.uiHander.sendEmptyMessageDelayed(0, 1000);
        }
    }
}
