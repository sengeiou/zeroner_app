package com.iwown.device_module.common.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.iwown.device_module.R;
import com.iwown.device_module.device_setting.wifi_scale.dialog.AbsCustomDialog;
import com.socks.library.KLog;
import java.util.regex.Pattern;

public class EditTextDialog extends AbsCustomDialog implements OnClickListener {
    private boolean controlDismissSelf = false;
    private Button mBtnCancel;
    private Button mBtnSave;
    private int mMaxLength = 20;
    /* access modifiers changed from: private */
    public EditText mNameEdt;
    private String mTextString;
    /* access modifiers changed from: private */
    public OnTextConfirmListener onConfirmListener;
    private Handler uiHander = new Handler() {
        public void handleMessage(Message msg) {
            EditTextDialog.this.mNameEdt.setFocusable(true);
            EditTextDialog.this.mNameEdt.setFocusableInTouchMode(true);
            EditTextDialog.this.mNameEdt.requestFocus();
            ((InputMethodManager) EditTextDialog.this.mNameEdt.getContext().getSystemService("input_method")).showSoftInput(EditTextDialog.this.mNameEdt, 0);
        }
    };

    public interface OnTextConfirmListener {
        void OnCancel();

        void OnConfirm(String str);

        void OnTextChanged(String str);
    }

    public boolean isControlDismissSelf() {
        return this.controlDismissSelf;
    }

    public void setControlDismissSelf(boolean controlDismissSelf2) {
        this.controlDismissSelf = controlDismissSelf2;
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
        this.mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        this.mBtnCancel.setOnClickListener(this);
        this.mNameEdt.setFilters(new InputFilter[]{new EmojiFilter(), new LengthFilter(10)});
        this.mNameEdt.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                if (EditTextDialog.this.onConfirmListener != null) {
                    EditTextDialog.this.onConfirmListener.OnTextChanged(editable.toString().trim());
                }
            }
        });
    }

    public void initData() {
    }

    public void initListener() {
        this.mBtnSave.setOnClickListener(this);
    }

    public int getWindowAnimationsResId() {
        return R.style.device_module_BottomDialogAnim;
    }

    public int getLayoutResID() {
        return R.layout.device_module_view_edittext_dialog;
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
        int i = v.getId();
        if (i == R.id.btn_save) {
            this.mTextString = this.mNameEdt.getText().toString().trim();
            if (this.onConfirmListener != null) {
                this.onConfirmListener.OnConfirm(this.mTextString);
            }
            if (this.controlDismissSelf) {
                dismiss();
            }
        } else if (i == R.id.btn_cancel) {
            dismiss();
        }
    }

    public boolean stringFilter(String str) {
        return Pattern.compile("^[一-龥a-zA-Z0-9]+$").matcher(str).matches();
    }

    public void setCanConfirm(boolean canConfirm) {
        if (!canConfirm) {
            this.mBtnSave.setTextColor(-7829368);
            this.mBtnSave.setEnabled(false);
            this.mBtnSave.setClickable(false);
            return;
        }
        this.mBtnSave.setTextColor(-1);
        this.mBtnSave.setEnabled(true);
        this.mBtnSave.setClickable(true);
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        KLog.e("onWindowFocusChanged: " + hasWindowFocus);
        if (hasWindowFocus) {
            this.uiHander.sendEmptyMessageDelayed(0, 500);
        }
    }
}
