package com.iwown.my_module.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.iwown.my_module.R;
import com.iwown.my_module.dialog.AbsCustomDialog;

public class SendNewPwdDialog extends AbsCustomDialog implements OnClickListener {
    boolean isCancel;
    private OnConfirmButtonClickListener mOnConfirmButtonClickListener;
    private TextView ok;

    public interface OnConfirmButtonClickListener {
        void onConfirmButtonClick();
    }

    public void setOnConfirmButtonClickListener(OnConfirmButtonClickListener listener) {
        this.mOnConfirmButtonClickListener = listener;
    }

    public SendNewPwdDialog(Context context) {
        super(context);
    }

    public SendNewPwdDialog(Context context, boolean isCancel2) {
        super(context);
        this.isCancel = isCancel2;
    }

    public int getLayoutResID() {
        return R.layout.my_module_show_up_firm;
    }

    public void initView() {
        this.ok = (TextView) findViewById(R.id.update_pop_ok);
    }

    public void initData() {
    }

    public void initListener() {
        this.ok.setOnClickListener(this);
    }

    public int getWidth() {
        return -2;
    }

    public int getHeight() {
        return -2;
    }

    public int getGravity() {
        return 17;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.update_pop_ok) {
            dismiss();
            if (this.mOnConfirmButtonClickListener != null) {
                this.mOnConfirmButtonClickListener.onConfirmButtonClick();
            }
        }
    }
}
