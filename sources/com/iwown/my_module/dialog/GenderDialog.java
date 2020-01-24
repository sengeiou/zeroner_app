package com.iwown.my_module.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.iwown.my_module.R;
import com.iwown.my_module.widget.GenderPickerView;

public class GenderDialog extends AbsCustomDialog implements OnClickListener {
    private static final String TAG = "GenderDialog";
    private Button btnCancel;
    private Button btnSave;
    private GenderPickerView genderPicker;
    private int mGender;
    private OnGenderConfirmListener onConfirmListener;

    public interface OnGenderConfirmListener {
        void OnConfirm(boolean z);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public GenderDialog(Context context, int gender) {
        super(context);
        this.mGender = gender;
    }

    public void initView() {
        this.btnCancel = (Button) findViewById(R.id.cancel_btn);
        this.btnSave = (Button) findViewById(R.id.ok_btn);
        this.genderPicker = (GenderPickerView) findViewById(R.id.gender_picker);
        this.genderPicker.setGender(this.mGender);
    }

    public void initData() {
    }

    public void initListener() {
        this.btnCancel.setOnClickListener(this);
        this.btnSave.setOnClickListener(this);
    }

    public int getWindowAnimationsResId() {
        return R.style.BottomDialogAnim;
    }

    public int getLayoutResID() {
        return R.layout.my_module_view_gender_dialog;
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

    public void setOnGenderConfirmListener(OnGenderConfirmListener onConfirmListener2) {
        this.onConfirmListener = onConfirmListener2;
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ok_btn) {
            if (this.onConfirmListener != null) {
                if (this.genderPicker.getGender() == 1) {
                    this.onConfirmListener.OnConfirm(true);
                } else {
                    this.onConfirmListener.OnConfirm(false);
                }
            }
            dismiss();
        } else if (i == R.id.cancel_btn) {
            dismiss();
        }
    }
}
