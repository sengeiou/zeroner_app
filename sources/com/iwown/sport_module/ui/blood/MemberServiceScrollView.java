package com.iwown.sport_module.ui.blood;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import com.iwown.lib_common.views.bloodview.DBloodChartView;
import com.iwown.sport_module.R;

public class MemberServiceScrollView extends ScrollView {
    private View convertView;
    private boolean isScrollable = true;
    private boolean lastWheelMenuAction = false;
    private DBloodChartView mWheelMenu;

    public MemberServiceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MemberServiceScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MemberServiceScrollView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        this.convertView = getChildAt(0);
        this.mWheelMenu = null;
        if (this.convertView != null) {
            this.mWheelMenu = (DBloodChartView) this.convertView.findViewById(R.id.tv_date_choose);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.lastWheelMenuAction) {
            if (ev.getAction() != 3 && ev.getAction() != 1) {
                return false;
            }
            this.lastWheelMenuAction = false;
            this.isScrollable = true;
            return false;
        } else if (this.mWheelMenu == null || ((float) (this.mWheelMenu.getMeasuredHeight() - getScrollY())) <= ev.getY() || this.mWheelMenu.getMeasuredHeight() - getScrollY() <= getScrollY()) {
            this.isScrollable = true;
            return super.onInterceptTouchEvent(ev);
        } else {
            this.lastWheelMenuAction = true;
            this.isScrollable = false;
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.isScrollable) {
            return super.onTouchEvent(ev);
        }
        return false;
    }
}
