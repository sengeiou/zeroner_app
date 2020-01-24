package com.iwown.sport_module.ui.weight;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.dialog.NewAbsCustomDialog;
import com.iwown.sport_module.R;
import com.socks.library.KLog;

public class WeightMsgDialog extends NewAbsCustomDialog {
    private int imageID = R.drawable.weight_msg_top_bg;
    private ImageView iv_msg_top;
    private String msg = "";
    private String title = "";
    private TextView tv_title;
    private TextView tv_value;

    public WeightMsgDialog(Context context) {
        super(context);
    }

    public WeightMsgDialog(Context context, boolean isOutCancel) {
        super(context, isOutCancel);
    }

    public int getLayoutResID() {
        return R.layout.sport_module_dialog_msg_weight;
    }

    public void initView() {
        try {
            getWindow().getDecorView().setPadding(DensityUtil.dip2px(getContext(), 50.0f), 0, DensityUtil.dip2px(getContext(), 50.0f), 0);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            KLog.e("dialog_heart " + e.getMessage());
        }
        this.tv_value = (TextView) findViewById(R.id.tv_value);
        this.iv_msg_top = (ImageView) findViewById(R.id.iv_msg_top);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_value.setText(this.msg);
        this.tv_title.setText(this.title);
        this.iv_msg_top.setBackgroundResource(this.imageID);
    }

    public void initData() {
    }

    public void initListener() {
    }

    public void setTopBG(int imageID2) {
        this.imageID = imageID2;
        if (this.iv_msg_top != null) {
            this.iv_msg_top.setBackgroundResource(imageID2);
        }
    }

    public void setShowMsg(String title2, String msg2) {
        this.msg = msg2;
        this.title = title2;
        if (this.tv_value != null) {
            this.tv_value.setText(msg2);
            this.tv_title.setText(title2);
        }
    }
}
