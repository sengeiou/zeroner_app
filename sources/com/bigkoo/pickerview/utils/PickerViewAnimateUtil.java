package com.bigkoo.pickerview.utils;

import com.iwown.lib_common.R;

public class PickerViewAnimateUtil {
    private static final int INVALID = -1;

    public static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case 80:
                return isInAnimation ? R.anim.pickerview_slide_in_bottom : R.anim.pickerview_slide_out_bottom;
            default:
                return -1;
        }
    }
}
