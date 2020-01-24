package com.bigkoo.pickerview.lib;

final class OnItemSelectedRunnable implements Runnable {
    final WheelView loopView;

    OnItemSelectedRunnable(WheelView loopview) {
        this.loopView = loopview;
    }

    public final void run() {
        this.loopView.onItemSelectedListener.onItemSelected(this.loopView.getCurrentItem());
    }
}
