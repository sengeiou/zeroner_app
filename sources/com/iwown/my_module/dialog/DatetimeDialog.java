package com.iwown.my_module.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.iwown.my_module.R;
import com.iwown.my_module.widget.DatetimePickerView;

public class DatetimeDialog extends AbsCustomDialog implements OnClickListener {
    private String birthday;
    private Button cancelBtn;
    private Context mContext;
    private Button okBtn;
    private OnConfirmListener onConfirmListener;
    private DatetimePickerView picker;

    public interface OnConfirmListener {
        void OnConfirm(String str);
    }

    public DatetimeDialog(Context context, String datestr) {
        super(context);
        this.mContext = context;
        this.birthday = datestr;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initView() {
        this.picker = (DatetimePickerView) findViewById(R.id.picker);
        this.okBtn = (Button) findViewById(R.id.ok_btn);
        this.cancelBtn = (Button) findViewById(R.id.cancel_btn);
    }

    public void initData() {
        if (this.birthday != null && !this.birthday.equals("")) {
            this.picker.setBirthday(this.birthday);
        }
    }

    public void initListener() {
        this.okBtn.setOnClickListener(this);
        this.cancelBtn.setOnClickListener(this);
    }

    public int getWindowAnimationsResId() {
        return R.style.BottomDialogAnim;
    }

    public int getLayoutResID() {
        return R.layout.my_module_view_picker_dialog;
    }

    public int getWidth() {
        return -1;
    }

    public int getHeight() {
        return -2;
    }

    public int getGravity() {
        return 80;
    }

    public int getBackgroundDrawableResourceId() {
        return super.getBackgroundDrawableResourceId();
    }

    public boolean getCancelable() {
        return true;
    }

    public boolean getCanceledOnTouchOutside() {
        return true;
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener2) {
        this.onConfirmListener = onConfirmListener2;
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ok_btn) {
            if (this.onConfirmListener != null) {
                this.onConfirmListener.OnConfirm(this.picker.getBirthday());
            }
            dismiss();
        } else if (i == R.id.cancel_btn) {
            dismiss();
        }
    }
}
