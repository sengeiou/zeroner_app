package com.iwown.device_module.common.view.dialog;

import android.content.Context;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.lib_common.dialog.DialogRemindStyle;

public class TipDialog extends DialogRemindStyle {
    private TextView content;
    private TextView title;

    public TipDialog(Context context) {
        super(context);
    }

    public TipDialog(Context context, boolean isOutCancel) {
        super(context, isOutCancel);
    }

    public void initView() {
        super.initView();
        this.content = (TextView) getCenter_Layout().findViewById(R.id.content_msg);
        this.title = (TextView) getCenter_Layout().findViewById(R.id.content_msg_title);
    }

    public int getCenterTextLayout() {
        return R.layout.device_module_dialog_remind;
    }

    public void setContentMsg(String text) {
        this.content.setText(text);
    }

    public void setTitleMsg(String text) {
        this.title.setText(text);
    }
}
