package com.iwown.device_module.device_firmware_upgrade.dialog;

import android.content.Context;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.lib_common.dialog.DialogRemindStyle;

public class UpgradeFailDialogRemind extends DialogRemindStyle {
    private TextView content;
    private TextView title;

    public UpgradeFailDialogRemind(Context context) {
        super(context);
    }

    public UpgradeFailDialogRemind(Context context, boolean isOutCancel) {
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
