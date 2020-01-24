package com.iwown.my_module.dialog;

import android.content.Context;
import com.iwown.lib_common.dialog.DialogRemindStyle;
import com.iwown.my_module.R;

public class LogUploadConfirmDialog extends DialogRemindStyle {
    public LogUploadConfirmDialog(Context context) {
        super(context);
    }

    public LogUploadConfirmDialog(Context context, boolean isOutCancel) {
        super(context, isOutCancel);
    }

    public int getCenterTextLayout() {
        return R.layout.my_module_upload_ble_dialog;
    }
}
