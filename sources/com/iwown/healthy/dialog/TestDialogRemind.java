package com.iwown.healthy.dialog;

import android.content.Context;
import com.healthy.zeroner_pro.R;
import com.iwown.lib_common.dialog.DialogRemindStyle;

public class TestDialogRemind extends DialogRemindStyle {
    public TestDialogRemind(Context context) {
        super(context);
    }

    public TestDialogRemind(Context context, boolean isOutCancel) {
        super(context, isOutCancel);
    }

    public int getCenterTextLayout() {
        return R.layout.item_test_dialog_remind;
    }
}
