package com.iwown.my_module.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public abstract class CommonPopupWindow {
    protected View contentView;
    /* access modifiers changed from: protected */
    public Context context;
    protected PopupWindow mInstance;

    public static class LayoutGravity {
        public static final int ALIGN_ABOVE = 2;
        public static final int ALIGN_BOTTOM = 8;
        public static final int ALIGN_LEFT = 1;
        public static final int ALIGN_RIGHT = 4;
        public static final int CENTER_HORI = 256;
        public static final int CENTER_VERT = 512;
        public static final int TO_ABOVE = 32;
        public static final int TO_BOTTOM = 128;
        public static final int TO_LEFT = 16;
        public static final int TO_RIGHT = 64;
        private int layoutGravity;

        public LayoutGravity(int gravity) {
            this.layoutGravity = gravity;
        }

        public int getLayoutGravity() {
            return this.layoutGravity;
        }

        public void setLayoutGravity(int gravity) {
            this.layoutGravity = gravity;
        }

        public void setHoriGravity(int gravity) {
            this.layoutGravity &= 682;
            this.layoutGravity |= gravity;
        }

        public void setVertGravity(int gravity) {
            this.layoutGravity &= 341;
            this.layoutGravity |= gravity;
        }

        public boolean isParamFit(int param) {
            return (this.layoutGravity & param) > 0;
        }

        public int getHoriParam() {
            for (int i = 1; i <= 256; i <<= 2) {
                if (isParamFit(i)) {
                    return i;
                }
            }
            return 1;
        }

        public int getVertParam() {
            for (int i = 2; i <= 512; i <<= 2) {
                if (isParamFit(i)) {
                    return i;
                }
            }
            return 128;
        }

        public int[] getOffset(View anchor, PopupWindow window) {
            int anchWidth = anchor.getWidth();
            int anchHeight = anchor.getHeight();
            int winWidth = window.getWidth();
            int winHeight = window.getHeight();
            View view = window.getContentView();
            if (winWidth <= 0) {
                winWidth = view.getWidth();
            }
            if (winHeight <= 0) {
                winHeight = view.getHeight();
            }
            int xoff = 0;
            int yoff = 0;
            switch (getHoriParam()) {
                case 1:
                    xoff = 0;
                    break;
                case 4:
                    xoff = anchWidth - winWidth;
                    break;
                case 16:
                    xoff = -winWidth;
                    break;
                case 64:
                    xoff = anchWidth;
                    break;
                case 256:
                    xoff = (anchWidth - winWidth) / 2;
                    break;
            }
            switch (getVertParam()) {
                case 2:
                    yoff = -anchHeight;
                    break;
                case 8:
                    yoff = -winHeight;
                    break;
                case 32:
                    yoff = (-anchHeight) - winHeight;
                    break;
                case 128:
                    yoff = 0;
                    break;
                case 512:
                    yoff = ((-winHeight) - anchHeight) / 2;
                    break;
            }
            return new int[]{xoff, yoff};
        }
    }

    /* access modifiers changed from: protected */
    public abstract void initEvent();

    /* access modifiers changed from: protected */
    public abstract void initView();

    public CommonPopupWindow(Context c, int layoutRes, int w, int h) {
        this.context = c;
        this.contentView = LayoutInflater.from(c).inflate(layoutRes, null, false);
        initView();
        initEvent();
        this.mInstance = new PopupWindow(this.contentView, w, h, true);
        initWindow();
    }

    public View getContentView() {
        return this.contentView;
    }

    public PopupWindow getPopupWindow() {
        return this.mInstance;
    }

    /* access modifiers changed from: protected */
    public void initWindow() {
        this.mInstance.setBackgroundDrawable(new ColorDrawable(0));
        this.mInstance.setOutsideTouchable(true);
        this.mInstance.setTouchable(true);
        this.mInstance.setFocusable(true);
        this.mInstance.setTouchInterceptor(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != 4) {
                    return false;
                }
                CommonPopupWindow.this.mInstance.dismiss();
                return true;
            }
        });
        this.mInstance.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
            }
        });
    }

    public void showBashOfAnchor(View anchor, LayoutGravity layoutGravity, int xmerge, int ymerge) {
        int[] offset = layoutGravity.getOffset(anchor, this.mInstance);
        this.mInstance.showAsDropDown(anchor, offset[0] + xmerge, offset[1] + ymerge);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        this.mInstance.showAsDropDown(anchor, xoff, yoff);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        this.mInstance.showAtLocation(parent, gravity, x, y);
    }

    public void dismiss() {
        if (this.mInstance != null) {
            this.mInstance.dismiss();
        }
    }
}
