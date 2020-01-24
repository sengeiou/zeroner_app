package com.iwown.lib_common.fragment;

public abstract class BaseMainFragment extends SupportFragment {
    private static final long WAIT_TIME = 2000;
    private long TOUCH_TIME = 0;

    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - this.TOUCH_TIME < WAIT_TIME) {
            this._mActivity.finish();
        } else {
            this.TOUCH_TIME = System.currentTimeMillis();
        }
        return true;
    }
}
