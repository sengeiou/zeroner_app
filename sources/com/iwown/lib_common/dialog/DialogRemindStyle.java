package com.iwown.lib_common.dialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.R;

public abstract class DialogRemindStyle extends NewAbsCustomDialog implements OnClickListener {
    private Button bt_cancel;
    private Button bt_ok;
    private View center_Layout;
    ClickCallBack clickCallBack;
    private boolean isOnlyOneBT;
    private ImageView iv_top;

    public interface ClickCallBack {
        void onCancel();

        void onOk();
    }

    public abstract int getCenterTextLayout();

    public View getCenter_Layout() {
        return this.center_Layout;
    }

    public DialogRemindStyle(Context context) {
        super(context);
    }

    public DialogRemindStyle(Context context, boolean isOutCancel) {
        super(context, isOutCancel);
    }

    public void setClickCallBack(ClickCallBack clickCallBack2) {
        this.clickCallBack = clickCallBack2;
    }

    public int getLayoutResID() {
        return R.layout.lib_common_dialog_remind;
    }

    public void setOnlyOneBT(boolean isOnlyOneBT2) {
        this.isOnlyOneBT = isOnlyOneBT2;
    }

    public void initView() {
        try {
            LayoutParams lp = this.mWindow.getAttributes();
            lp.width = getWidth();
            lp.height = getHeight();
            lp.gravity = 48;
            onWindowAttributesChanged(lp);
            getWindow().getDecorView().setPadding(DensityUtil.dip2px(getContext(), 60.0f), DensityUtil.dip2px(getContext(), 60.0f), DensityUtil.dip2px(getContext(), 60.0f), 0);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        LinearLayout ll_main = (LinearLayout) findViewById(R.id.ll_main);
        ll_main.removeAllViews();
        this.bt_ok = (Button) findViewById(R.id.bt_ok);
        this.iv_top = (ImageView) findViewById(R.id.iv_top);
        this.bt_cancel = (Button) findViewById(R.id.bt_cancel);
        if (this.isOnlyOneBT) {
            this.bt_cancel.setVisibility(8);
        }
        this.iv_top.setBackgroundResource(R.drawable.dialog_remind_top_icon);
        this.center_Layout = View.inflate(getContext(), getCenterTextLayout(), null);
        ll_main.addView(this.center_Layout);
        this.bt_ok.setOnClickListener(this);
        this.bt_cancel.setOnClickListener(this);
    }

    public void setTopImage(int drawId) {
        this.iv_top.setBackgroundResource(drawId);
    }

    public void setBt_okText(String text) {
        this.bt_ok.setText(text);
    }

    public void setBt_cancel(String text) {
        this.bt_cancel.setText(text);
    }

    public void initData() {
    }

    public void initListener() {
    }

    public void show() {
        super.show();
        if (this.isOnlyOneBT) {
            this.bt_cancel.setVisibility(8);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.bt_ok) {
            if (this.clickCallBack != null) {
                this.clickCallBack.onOk();
            }
        } else if (view.getId() == R.id.bt_cancel && this.clickCallBack != null) {
            this.clickCallBack.onCancel();
        }
    }
}
