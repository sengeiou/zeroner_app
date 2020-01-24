package com.iwown.device_module.common.network.utils;

import android.widget.Toast;
import com.iwown.device_module.common.utils.ContextUtil;

public class ToastUtil {
    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(ContextUtil.app, text, 0);
        } else {
            mToast.setText(text);
            mToast.setDuration(0);
        }
        mToast.show();
    }

    public static void showToast(int strRes) {
        showToast(ContextUtil.app.getString(strRes));
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
