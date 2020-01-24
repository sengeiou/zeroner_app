package com.iwown.my_module.dialog;

import android.content.Context;
import com.iwown.lib_common.dialog.DialogRemindStyle;
import com.iwown.my_module.R;

public class DialogRemind extends DialogRemindStyle {
    public DialogRemind(Context context) {
        super(context);
    }

    public DialogRemind(Context context, boolean isOutCancel) {
        super(context, isOutCancel);
    }

    public int getCenterTextLayout() {
        return R.layout.my_module_item_test_dialog_remind;
    }
}
