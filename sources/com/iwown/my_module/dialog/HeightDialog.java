package com.iwown.my_module.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.my_module.R;
import com.iwown.my_module.widget.HeightPickerView;
import com.socks.library.KLog;

public class HeightDialog extends AbsCustomDialog implements OnClickListener {
    private static final String TAG = "HeightDialog";
    private Button cancel_btn;
    private float currentHeight;
    private Button okBtn;
    private OnHeightConfirmListener onConfirmListener;
    private HeightPickerView picker;

    public interface OnHeightConfirmListener {
        void OnHeightConfirm(float f);
    }

    public HeightDialog(Context context) {
        this(context, 170.0f);
    }

    public HeightDialog(Context context, float height) {
        super(context);
        this.currentHeight = height;
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ok_btn) {
            if (this.onConfirmListener != null) {
                GlobalDataUpdater.setMeasureUnit(getContext(), EnumMeasureUnit.values()[this.picker.getUnitPosition()]);
                Log.i(TAG, String.format("height catch in height dialog : %f", new Object[]{Float.valueOf(this.picker.getCurrHeight())}));
                this.onConfirmListener.OnHeightConfirm(this.picker.getCurrHeight());
                KLog.i(this.picker.getUnitPosition() + "---" + EnumMeasureUnit.values()[this.picker.getUnitPosition()]);
            }
            dismiss();
        } else if (i == R.id.cancel_btn) {
            dismiss();
        }
    }

    public void initView() {
        this.picker = (HeightPickerView) findViewById(R.id.anything_pecker);
        this.okBtn = (Button) findViewById(R.id.ok_btn);
        this.cancel_btn = (Button) findViewById(R.id.cancel_btn);
    }

    public void initData() {
        this.picker.setUnitType(GlobalUserDataFetcher.getPreferredMeasureUnit(getContext()));
        this.picker.setCurrHeight(this.currentHeight);
    }

    public void initListener() {
        this.okBtn.setOnClickListener(this);
        this.cancel_btn.setOnClickListener(this);
    }

    public int getWindowAnimationsResId() {
        return R.style.BottomDialogAnim;
    }

    public int getLayoutResID() {
        return R.layout.my_module_view_height_dialog;
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

    public void setOnHeightConfirmListener(OnHeightConfirmListener onConfirmListener2) {
        this.onConfirmListener = onConfirmListener2;
    }
}
