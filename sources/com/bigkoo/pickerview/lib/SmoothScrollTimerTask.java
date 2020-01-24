package com.bigkoo.pickerview.lib;

import java.util.TimerTask;

final class SmoothScrollTimerTask extends TimerTask {
    final WheelView loopView;
    int offset;
    int realOffset = 0;
    int realTotalOffset = Integer.MAX_VALUE;

    SmoothScrollTimerTask(WheelView loopview, int offset2) {
        this.loopView = loopview;
        this.offset = offset2;
    }

    public final void run() {
        if (this.realTotalOffset == Integer.MAX_VALUE) {
            this.realTotalOffset = this.offset;
        }
        this.realOffset = (int) (((float) this.realTotalOffset) * 0.1f);
        if (this.realOffset == 0) {
            if (this.realTotalOffset < 0) {
                this.realOffset = -1;
            } else {
                this.realOffset = 1;
            }
        }
        if (Math.abs(this.realTotalOffset) <= 1) {
            this.loopView.cancelFuture();
            this.loopView.handler.sendEmptyMessage(3000);
            return;
        }
        this.loopView.totalScrollY += (float) this.realOffset;
        if (!this.loopView.isLoop) {
            float itemHeight = this.loopView.itemHeight;
            float bottom = ((float) ((this.loopView.getItemsCount() - 1) - this.loopView.initPosition)) * itemHeight;
            if (this.loopView.totalScrollY <= ((float) (-this.loopView.initPosition)) * itemHeight || this.loopView.totalScrollY >= bottom) {
                this.loopView.totalScrollY -= (float) this.realOffset;
                this.loopView.cancelFuture();
                this.loopView.handler.sendEmptyMessage(3000);
                return;
            }
        }
        this.loopView.handler.sendEmptyMessage(1000);
        this.realTotalOffset -= this.realOffset;
    }
}
