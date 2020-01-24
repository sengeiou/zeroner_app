package com.iwown.my_module.useractivity.profile.ninegrid;

import android.content.Context;

public class ScreenTools {
    private static ScreenTools mScreenTools;
    private Context context;

    private ScreenTools(Context context2) {
        this.context = context2.getApplicationContext();
    }

    public static ScreenTools instance(Context context2) {
        if (mScreenTools == null) {
            mScreenTools = new ScreenTools(context2);
        }
        return mScreenTools;
    }

    public int dip2px(float f) {
        return (int) (0.5d + ((double) (getDensity(this.context) * f)));
    }

    public int dip2px(int i) {
        return (int) (0.5d + ((double) (getDensity(this.context) * ((float) i))));
    }

    public int get480Height(int i) {
        return (getScreenWidth() * i) / 480;
    }

    public float getDensity(Context context2) {
        return context2.getResources().getDisplayMetrics().density;
    }

    public int getScal() {
        return (getScreenWidth() * 100) / 480;
    }

    public int getScreenDensityDpi() {
        return this.context.getResources().getDisplayMetrics().densityDpi;
    }

    public int getScreenHeight() {
        return this.context.getResources().getDisplayMetrics().heightPixels;
    }

    public int getScreenWidth() {
        return this.context.getResources().getDisplayMetrics().widthPixels;
    }

    public float getXdpi() {
        return this.context.getResources().getDisplayMetrics().xdpi;
    }

    public float getYdpi() {
        return this.context.getResources().getDisplayMetrics().ydpi;
    }

    public int px2dip(float f) {
        return (int) ((((double) f) - 0.5d) / ((double) getDensity(this.context)));
    }

    public int px2dip(int i) {
        return (int) ((((double) i) - 0.5d) / ((double) getDensity(this.context)));
    }
}
