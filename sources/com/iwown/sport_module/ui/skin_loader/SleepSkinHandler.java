package com.iwown.sport_module.ui.skin_loader;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.SparseArray;

public class SleepSkinHandler {
    private static final int SleepSkin_Type_Chart_Tag_Deep_BG = 6;
    private static final int SleepSkin_Type_Chart_Tag_Light_BG = 7;
    private static final int SleepSkin_Type_Chart_Tag_Text = 9;
    private static final int SleepSkin_Type_Chart_Tag_Wake_BG = 8;
    private static final int SleepSkin_Type_History_Top_Score = 10;
    private static final int SleepSkin_Type_Time_Number = 4;
    private static final int SleepSkin_Type_Time_Title = 3;
    private static final int SleepSkin_Type_Time_Unit = 5;
    private static final int SleepSkin_Type_Top_Bottom_BG = 1;
    private static final int SleepSkin_Type_Top_TitleBar_BG = 2;
    private SparseArray<Object> cache_skins;

    private static class SleepSkinHandlerHolder {
        public static SleepSkinHandler sleepSkinHandler = new SleepSkinHandler();

        private SleepSkinHandlerHolder() {
        }
    }

    private SleepSkinHandler() {
        this.cache_skins = new SparseArray<>();
    }

    public void setskinTopBG(int topBG, int bottomBG) {
        this.cache_skins.put(1, new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{topBG, bottomBG}));
        this.cache_skins.put(2, Integer.valueOf(topBG));
    }

    public Drawable getSkinTopBG() {
        return (Drawable) this.cache_skins.get(1);
    }

    public int getSkinTitleBarBG() {
        return ((Integer) this.cache_skins.get(2)).intValue();
    }

    public static SleepSkinHandler getInstance() {
        return SleepSkinHandlerHolder.sleepSkinHandler;
    }

    public void init() {
        this.cache_skins.clear();
        setskinTopBG(-7973683, -12179598);
        this.cache_skins.put(3, Integer.valueOf(-4868163));
        this.cache_skins.put(4, Integer.valueOf(-1));
        this.cache_skins.put(5, Integer.valueOf(-1));
        this.cache_skins.put(6, Integer.valueOf(-13955245));
        this.cache_skins.put(7, Integer.valueOf(-4947457));
        this.cache_skins.put(8, Integer.valueOf(-17846));
        this.cache_skins.put(9, Integer.valueOf(-1));
        this.cache_skins.put(10, Integer.valueOf(-1));
    }

    public int getSleepSkin_Type_Time_Title() {
        return ((Integer) this.cache_skins.get(3)).intValue();
    }

    public int getSleepSkin_Type_Chart_Tag_Deep_BG() {
        return ((Integer) this.cache_skins.get(6)).intValue();
    }

    public int getSleepSkin_Type_Chart_Tag_Light_BG() {
        return ((Integer) this.cache_skins.get(7)).intValue();
    }

    public int getSleepSkin_Type_Chart_Tag_Wake_BG() {
        return ((Integer) this.cache_skins.get(8)).intValue();
    }

    public int getSleepSkin_Type_Time_Number() {
        return ((Integer) this.cache_skins.get(4)).intValue();
    }

    public int getSleepSkin_Type_Time_Unit() {
        return ((Integer) this.cache_skins.get(5)).intValue();
    }

    public int getSleepSkin_Type_Chart_Tag_Text() {
        return ((Integer) this.cache_skins.get(9)).intValue();
    }

    public int getSleepSkin_Type_History_Top_Score() {
        return ((Integer) this.cache_skins.get(10)).intValue();
    }
}
