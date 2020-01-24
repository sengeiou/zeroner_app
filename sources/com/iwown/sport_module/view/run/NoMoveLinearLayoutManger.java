package com.iwown.sport_module.view.run;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class NoMoveLinearLayoutManger extends LinearLayoutManager {
    public NoMoveLinearLayoutManger(Context context) {
        super(context);
    }

    public NoMoveLinearLayoutManger(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NoMoveLinearLayoutManger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean canScrollVertically() {
        return false;
    }
}
