package com.iwown.sport_module.view.swipetoloadlayout;

public interface SwipeTrigger {
    void onComplete();

    void onMove(int i, boolean z, boolean z2);

    void onPrepare();

    void onRelease();

    void onReset();
}
