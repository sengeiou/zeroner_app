package com.iwown.device_module.device_alarm_schedule.view.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.device_module.common.adapter.CommonAdapter;
import com.iwown.device_module.common.adapter.ViewHolder;
import com.iwown.device_module.common.view.wheelView.TosAdapterView;
import com.iwown.device_module.common.view.wheelView.TosAdapterView.OnItemSelectedListener;
import com.iwown.device_module.common.view.wheelView.TosGallery;
import com.iwown.device_module.common.view.wheelView.TosGallery.OnEndFlingListener;
import com.iwown.device_module.common.view.wheelView.WheelViewPro;
import com.iwown.device_module.device_alarm_schedule.utils.WindowsUtil;
import java.util.ArrayList;
import java.util.List;

public class ShakeNumDialog extends AbsCustomDialog implements OnClickListener, OnEndFlingListener {
    /* access modifiers changed from: private */
    public static int ITEM_HEIGHT = 0;
    /* access modifiers changed from: private */
    public boolean hasChangedUnitPosition = false;
    private CommonAdapter<String> mAdapter;
    private TextView mCancel;
    private TextView mConfirm;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public int mCurrPosition;
    private OnConfirmListener mOnConfirmListener;
    private boolean mSetData;
    private WheelViewPro mShakeNum;
    private TextView mTitle;
    /* access modifiers changed from: private */
    public TextView mUnitText;
    private int max = 0;
    private int min = 0;
    /* access modifiers changed from: private */
    public List<String> numList = new ArrayList();

    public interface OnConfirmListener {
        void onConfirm(int i);
    }

    public void setMin(int min2) {
        this.min = min2;
    }

    public void setMax(int max2) {
        this.max = max2;
    }

    public ShakeNumDialog(Context context) {
        super(context);
        this.mContext = context;
        ITEM_HEIGHT = WindowsUtil.dip2px(this.mContext, 56.0f);
        this.min = 1;
        this.max = 30;
    }

    public ShakeNumDialog(Context context, int min2, int max2) {
        super(context);
        this.mContext = context;
        ITEM_HEIGHT = WindowsUtil.dip2px(this.mContext, 56.0f);
        this.min = min2;
        this.max = max2;
    }

    public int getLayoutResID() {
        return R.layout.device_module_dialog_shake_num;
    }

    public void setCurrPosition(int num) {
        this.mSetData = true;
        this.mCurrPosition = num - 1;
    }

    public void initView() {
        this.mCancel = (TextView) findViewById(R.id.cancel);
        this.mConfirm = (TextView) findViewById(R.id.confirm);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mUnitText = (TextView) findViewById(R.id.dialog_unit);
        this.mShakeNum = (WheelViewPro) findViewById(R.id.shake_num);
    }

    public void initData() {
        for (int index = this.min; index <= this.max; index++) {
            this.numList.add(String.valueOf(index));
        }
        this.mAdapter = new CommonAdapter<String>(this.mContext, this.numList, R.layout.device_module_profile_wheelview_itme_layout) {
            public void adjustConvertView(View convertView) {
                super.adjustConvertView(convertView);
                TextView tv = (TextView) convertView.findViewById(R.id.wheelview_item);
                LayoutParams params = (LayoutParams) tv.getLayoutParams();
                params.height = ShakeNumDialog.ITEM_HEIGHT;
                tv.setLayoutParams(params);
                tv.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.device_module_common_background_1));
            }

            public void convert(ViewHolder helper, int position, String item) {
                TextView tv = (TextView) helper.getConvertView().findViewById(R.id.wheelview_item);
                tv.setText((CharSequence) ShakeNumDialog.this.numList.get(position));
                if (!ShakeNumDialog.this.hasChangedUnitPosition) {
                    int transY = (int) ((tv.getTextSize() / 2.0f) - (ShakeNumDialog.this.mUnitText.getTextSize() / 2.0f));
                    ShakeNumDialog.this.mUnitText.setTranslationX((float) WindowsUtil.dip2px(this.mContext, 40.0f));
                    ShakeNumDialog.this.mUnitText.setTranslationY((float) transY);
                    ShakeNumDialog.this.hasChangedUnitPosition = true;
                }
                if (position == ShakeNumDialog.this.mCurrPosition) {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                } else {
                    tv.setTextColor(this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                }
            }
        };
        this.mShakeNum.setAdapter((SpinnerAdapter) this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
    }

    public void setTitle(int resId) {
        this.mTitle.setText(resId);
    }

    public void initListener() {
        this.mCancel.setOnClickListener(this);
        this.mConfirm.setOnClickListener(this);
        this.mShakeNum.setScrollCycle(true);
        this.mShakeNum.setOnEndFlingListener(this);
        this.mShakeNum.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
                if (position > ShakeNumDialog.this.mCurrPosition || (ShakeNumDialog.this.mCurrPosition == ShakeNumDialog.this.numList.size() - 1 && position == 0)) {
                    if (parent.getChildAt(2) != null) {
                        ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(ShakeNumDialog.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    }
                    if (parent.getChildAt(1) != null) {
                        ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(ShakeNumDialog.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    }
                    ShakeNumDialog.this.mCurrPosition = position;
                } else if (position < ShakeNumDialog.this.mCurrPosition || (ShakeNumDialog.this.mCurrPosition == 0 && position == ShakeNumDialog.this.numList.size() - 1)) {
                    ((TextView) parent.getChildAt(1).findViewById(R.id.wheelview_item)).setTextColor(ShakeNumDialog.this.mContext.getResources().getColor(R.color.device_module_profile_title_color));
                    ((TextView) parent.getChildAt(0).findViewById(R.id.wheelview_item)).setTextColor(ShakeNumDialog.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    ((TextView) parent.getChildAt(2).findViewById(R.id.wheelview_item)).setTextColor(ShakeNumDialog.this.mContext.getResources().getColor(R.color.device_module_profile_wheelviwe_text_unselected_color));
                    ShakeNumDialog.this.mCurrPosition = position;
                }
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
    }

    public void onClick(View v) {
        if (v == this.mCancel) {
            dismiss();
        } else if (v == this.mConfirm) {
            dismiss();
            if (this.mOnConfirmListener != null) {
                this.mOnConfirmListener.onConfirm(this.mCurrPosition + 1);
            }
        }
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

    public void show() {
        super.show();
        if (this.mSetData && this.mCurrPosition >= 0 && this.mCurrPosition < this.mAdapter.getCount()) {
            this.mShakeNum.setSelection(this.mCurrPosition);
            this.mSetData = false;
        }
    }

    public void onEndFling(TosGallery v) {
        this.mCurrPosition = v.getSelectedItemPosition();
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.mOnConfirmListener = onConfirmListener;
    }
}
