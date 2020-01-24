package com.iwown.my_module.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.my_module.R;
import com.iwown.my_module.widget.WeightPickerView;

public class WeightDialog extends AbsCustomDialog implements OnClickListener {
    private static final String TAG = "WeightDialog";
    private Button cancel_btn;
    private float mWeight;
    private Button okBtn;
    private OnWeightConfirmListener onConfirmListener;
    private WeightPickerView picker;

    public interface OnWeightConfirmListener {
        void OnWeightConfirm(float f);
    }

    public double getWeight() {
        return (double) this.mWeight;
    }

    public void setWeight(float weight) {
        this.mWeight = weight;
    }

    public WeightDialog(Context context) {
        this(context, 60.0f);
    }

    public WeightDialog(Context context, float weight) {
        super(context);
        this.mWeight = weight;
        Log.i(TAG, String.format("weight in dialog construct:%f", new Object[]{Float.valueOf(this.mWeight)}));
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ok_btn) {
            if (this.onConfirmListener != null) {
                GlobalDataUpdater.setMeasureUnit(getContext(), EnumMeasureUnit.values()[this.picker.getUnitPosition()]);
                this.onConfirmListener.OnWeightConfirm(this.picker.getWeight());
                Log.i(TAG, String.format("weight in dialog : %f", new Object[]{Float.valueOf(this.picker.getWeight())}));
            }
            dismiss();
        } else if (i == R.id.cancel_btn) {
            dismiss();
        }
    }

    public void initView() {
        this.picker = (WeightPickerView) findViewById(R.id.anything_pecker);
        this.okBtn = (Button) findViewById(R.id.ok_btn);
        this.cancel_btn = (Button) findViewById(R.id.cancel_btn);
    }

    public void initData() {
        this.picker.setWeight(this.mWeight);
        Log.i(TAG, String.format("weight in dialog to pass:%f", new Object[]{Float.valueOf(this.mWeight)}));
    }

    public void initListener() {
        this.okBtn.setOnClickListener(this);
        this.cancel_btn.setOnClickListener(this);
    }

    public int getWindowAnimationsResId() {
        return R.style.BottomDialogAnim;
    }

    public int getLayoutResID() {
        return R.layout.my_module_view_weight_dialog;
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

    public void setOnWeightConfirmListener(OnWeightConfirmListener onConfirmListener2) {
        this.onConfirmListener = onConfirmListener2;
    }
}
