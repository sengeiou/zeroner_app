package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.iwown.lib_common.dialog.NewAbsCustomDialog;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.RunActivitySkin;

public class MyInputDialog extends NewAbsCustomDialog implements OnClickListener {
    private RelativeLayout inputLayout;
    private Context mContext;
    private LinearLayout mInputKeyLl;
    private int number;
    /* access modifiers changed from: private */
    public OnInputListener onInputListener;
    private int type;

    public interface OnInputListener {
        void onInput(int i);

        void saveGoal();
    }

    public MyInputDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.input1) {
            this.number = 1;
        } else if (i == R.id.input2) {
            this.number = 2;
        } else if (i == R.id.input3) {
            this.number = 3;
        } else if (i == R.id.input4) {
            this.number = 4;
        } else if (i == R.id.input5) {
            this.number = 5;
        } else if (i == R.id.input6) {
            this.number = 6;
        } else if (i == R.id.input7) {
            this.number = 7;
        } else if (i == R.id.input8) {
            this.number = 8;
        } else if (i == R.id.input9) {
            this.number = 9;
        } else if (i == R.id.inputp) {
            this.number = -1;
        } else if (i == R.id.input0) {
            this.number = 0;
        } else if (i == R.id.inputx) {
            this.number = -2;
        }
        callBack();
    }

    private void callBack() {
        if ((this.type != 2 || this.number != -1) && this.onInputListener != null) {
            this.onInputListener.onInput(this.number);
        }
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public void initView() {
        this.inputLayout = (RelativeLayout) findViewById(R.id.input_layout);
        this.inputLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MyInputDialog.this.onInputListener != null) {
                    MyInputDialog.this.onInputListener.saveGoal();
                }
                MyInputDialog.this.dismiss();
            }
        });
        this.mInputKeyLl = (LinearLayout) findViewById(R.id.input_ll);
        this.mInputKeyLl.setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
    }

    public void initData() {
    }

    public void initListener() {
        findViewById(R.id.input1).setOnClickListener(this);
        findViewById(R.id.input2).setOnClickListener(this);
        findViewById(R.id.input3).setOnClickListener(this);
        findViewById(R.id.input4).setOnClickListener(this);
        findViewById(R.id.input5).setOnClickListener(this);
        findViewById(R.id.input6).setOnClickListener(this);
        findViewById(R.id.input7).setOnClickListener(this);
        findViewById(R.id.input8).setOnClickListener(this);
        findViewById(R.id.input9).setOnClickListener(this);
        findViewById(R.id.inputp).setOnClickListener(this);
        findViewById(R.id.input0).setOnClickListener(this);
        findViewById(R.id.inputx).setOnClickListener(this);
    }

    public int getWindowAnimationsResId() {
        return R.style.BottomDialogAnim;
    }

    public int getLayoutResID() {
        return R.layout.sport_module_input_key_main;
    }

    public int getWidth() {
        return -1;
    }

    public int getHeight() {
        return -1;
    }

    public int getGravity() {
        return 80;
    }

    public int getBackgroundDrawableResourceId() {
        return super.getBackgroundDrawableResourceId();
    }

    public boolean getCancelable() {
        return false;
    }

    public boolean getCanceledOnTouchOutside() {
        return true;
    }

    public boolean getDimEnabled() {
        return false;
    }

    public void setInputListener(OnInputListener onInputListener2) {
        this.onInputListener = onInputListener2;
    }
}
