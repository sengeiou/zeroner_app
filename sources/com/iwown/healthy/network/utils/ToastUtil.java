package com.iwown.healthy.network.utils;

import android.widget.Toast;
import com.iwown.healthy.MyApplicationLike;

public class ToastUtil {
    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplicationLike.getInstance().getApplicationContext(), text, 0);
        } else {
            mToast.setText(text);
            mToast.setDuration(0);
        }
        mToast.show();
    }

    public static void showToast(int strRes) {
        showToast(MyApplicationLike.getInstance().getString(strRes));
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
