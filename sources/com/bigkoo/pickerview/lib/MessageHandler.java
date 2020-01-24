package com.bigkoo.pickerview.lib;

import android.os.Handler;
import android.os.Message;
import com.bigkoo.pickerview.lib.WheelView.ACTION;

final class MessageHandler extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    public static final int WHAT_ITEM_SELECTED = 3000;
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    final WheelView loopview;

    MessageHandler(WheelView loopview2) {
        this.loopview = loopview2;
    }

    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case 1000:
                this.loopview.invalidate();
                return;
            case 2000:
                this.loopview.smoothScroll(ACTION.FLING);
                return;
            case 3000:
                this.loopview.onItemSelected();
                return;
            default:
                return;
        }
    }
}
