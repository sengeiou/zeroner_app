package com.bigkoo.pickerview.lib;

import java.util.TimerTask;

final class InertiaTimerTask extends TimerTask {
    float a = 2.14748365E9f;
    final WheelView loopView;
    final float velocityY;

    InertiaTimerTask(WheelView loopview, float velocityY2) {
        this.loopView = loopview;
        this.velocityY = velocityY2;
    }

    public final void run() {
        if (this.a == 2.14748365E9f) {
            if (Math.abs(this.velocityY) <= 2000.0f) {
                this.a = this.velocityY;
            } else if (this.velocityY > 0.0f) {
                this.a = 2000.0f;
            } else {
                this.a = -2000.0f;
            }
        }
        if (Math.abs(this.a) < 0.0f || Math.abs(this.a) > 20.0f) {
            int i = (int) ((this.a * 10.0f) / 1000.0f);
            this.loopView.totalScrollY -= (float) i;
            if (!this.loopView.isLoop) {
                float itemHeight = this.loopView.itemHeight;
                float top = ((float) (-this.loopView.initPosition)) * itemHeight;
                float bottom = ((float) ((this.loopView.getItemsCount() - 1) - this.loopView.initPosition)) * itemHeight;
                if (((double) this.loopView.totalScrollY) - (((double) itemHeight) * 0.25d) < ((double) top)) {
                    top = this.loopView.totalScrollY + ((float) i);
                } else if (((double) this.loopView.totalScrollY) + (((double) itemHeight) * 0.25d) > ((double) bottom)) {
                    bottom = this.loopView.totalScrollY + ((float) i);
                }
                if (this.loopView.totalScrollY <= top) {
                    this.a = 40.0f;
                    this.loopView.totalScrollY = (float) ((int) top);
                } else if (this.loopView.totalScrollY >= bottom) {
                    this.loopView.totalScrollY = (float) ((int) bottom);
                    this.a = -40.0f;
                }
            }
            if (this.a < 0.0f) {
                this.a += 20.0f;
            } else {
                this.a -= 20.0f;
            }
            this.loopView.handler.sendEmptyMessage(1000);
            return;
        }
        this.loopView.cancelFuture();
        this.loopView.handler.sendEmptyMessage(2000);
    }
}
